package com.wondersgroup.healthcloud.services.hospital.impl;

import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.entity.request.OrderInfoR;
import com.wondersgroup.healthcloud.entity.request.OrderNumInfoRequest;
import com.wondersgroup.healthcloud.entity.request.ScheduleInfoRequest;
import com.wondersgroup.healthcloud.entity.response.OrderNumInfoResponse;
import com.wondersgroup.healthcloud.entity.response.ScheduleNumInfo;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.repository.hospital.DepartmentRepository;
import com.wondersgroup.healthcloud.jpa.repository.hospital.HospitalRepository;
import com.wondersgroup.healthcloud.services.hospital.DepartmentService;
import com.wondersgroup.healthcloud.services.hospital.dto.DepartmentInfoDTO;
import com.wondersgroup.healthcloud.services.hospital.dto.DeptsMenuDTO;
import com.wondersgroup.healthcloud.services.hospital.dto.DoctorBaseInfoDTO;
import com.wondersgroup.healthcloud.services.hospital.dto.DoctorInfoDTO;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dukuanxin on 2016/11/3.
 */
@Service("departmentService")
public class DepartmentServiceImpl extends SchedulCommon implements DepartmentService {
    private static final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Department> queryDept(String hospitalCode, String topHosDeptCode) {
        List<Department> departments = null;
        if (null != topHosDeptCode) {
            departments = departmentRepository.queryDepsByUpperDeptCode(hospitalCode, topHosDeptCode);
        } else {
            departments = departmentRepository.queryTopDeps(hospitalCode);
        }
        return departments;
    }

    @Override
    public List<Department> querySpecialDept(String hospitalCode) {
        return departmentRepository.querySpecialDeps(hospitalCode);
    }

    @Override
    public List<ScheduleNumInfo> queryScheduleNumInfo(String time, String hosOrgCode, String hosDeptCode, String hosDoctCode, String area) {
        OrderNumInfoRequest request = new OrderNumInfoRequest();
        request.requestMessageHeader = reqMesHeaderUtil.generator();
        OrderInfoR orderInfoR = new OrderInfoR();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (time == null) {
            Date start = DateUtils.addDay(new Date(), 1);
            Date end = DateUtils.addDay(new Date(), 7);
            orderInfoR.setStartTime(sdf.format(start));
            orderInfoR.setEndTime(sdf.format(end));
        } else {
            orderInfoR.setStartTime(time);
            orderInfoR.setEndTime(time);
        }

        orderInfoR.setHosOrgCode(hosOrgCode);
        orderInfoR.setHosDeptCode(hosDeptCode);
        orderInfoR.setHosDoctCode(hosDoctCode);
        request.orderInfoR = orderInfoR;
        String sign = signatureGenerator.generateSignature(request);
        request.requestMessageHeader.setSign(sign);
        OrderNumInfoResponse response;

        String hostialCityCode = getHostialCityCode(hosOrgCode);//根据医院编码反查城市编码
        String url = serverConfigServiceImpl.queryApiUrl(hostialCityCode, "1");
        orderInfoNumClient.setDefaultUri(url);
        response = orderInfoNumClient.getOrderNumInfoService(JaxbUtil.convertToXml(request, "GBK"));


        if (response != null) {
            List<ScheduleNumInfo> list = new ArrayList<>();
            List<ScheduleNumInfo> scheduleNumInfos = response.getScheduleNumInfos();
            for (ScheduleNumInfo scheduleNumInfo : scheduleNumInfos) {
                if (scheduleNumInfo.getTimeRange().equals("1") || scheduleNumInfo.getTimeRange().equals("3")) {//app只展示上午和下午的排班
                    if (scheduleNumInfo.getTimeRange().equals("3"))
                        scheduleNumInfo.setTimeRange("2");//2本来代表下午，平台后来更改为3，为防止App显示出错，故服务段转化一下
                    list.add(scheduleNumInfo);
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public List<ScheduleNumInfo> queryScheduleNumInfo(String time, String hosOrgCode, String hosDeptCode, String area) {
        ScheduleInfoRequest request = new ScheduleInfoRequest();
        request.requestMessageHeader = reqMesHeaderUtil.generator();
        ScheduleInfoRequest.OrderInfoR orderInfoR = new ScheduleInfoRequest.OrderInfoR();
        orderInfoR.setStartTime(time);
        orderInfoR.setEndTime(time);
        orderInfoR.setHosOrgCode(hosOrgCode);
        orderInfoR.setHosDeptCode(hosDeptCode);
        request.orderInfoR = orderInfoR;
        String sign = signatureGenerator.generateSignature(request);
        request.requestMessageHeader.setSign(sign);
        OrderNumInfoResponse response;

        String hostialCityCode = getHostialCityCode(hosOrgCode);//根据医院编码反查城市编码
        String url = serverConfigServiceImpl.queryApiUrl(hostialCityCode, "1");
        orderInfoNumClient.setDefaultUri(url);
        response = orderInfoNumClient.getOrderNumInfoService(JaxbUtil.convertToXml(request, "GBK"));

        if (response != null) {
            return response.getScheduleNumInfos();
        }
        return null;
    }

    @Override
    public List<DoctorInfoDTO> getDoctorInfo(List<Doctor> doctors, String area) {
        long current = new Date().getTime();
        List<DoctorInfoDTO> list = new ArrayList<>();

        try {
            for (Doctor doctor : doctors) {
                long current2 = new Date().getTime();
                List<ScheduleNumInfo> scheduleNumInfos = queryScheduleNumInfo(null, doctor.getHospitalCode(), doctor.getDeptCode(), doctor.getDoctorCode(), area);
                String time = (new Date().getTime() - current2) + "";
                log.info(time);
                for (ScheduleNumInfo scheduleInfo : scheduleNumInfos) {
                    if ((scheduleInfo.getReserveOrderNum() - scheduleInfo.getOrderedNum()) > 0) {
                        doctor.setIsFull(1);
                        break;
                    }
                }
                list.add(new DoctorInfoDTO(doctor));
            }
        } catch (Exception e) {
            log.info(Exceptions.getStackTraceAsString(e));
            return list;
        }
        String time = (new Date().getTime() - current) + "";
        log.info(time);
        return list;
    }

    @Override
    public List<DoctorInfoDTO> getScheduleByTime(String uid, List<ScheduleNumInfo> scheduleNumInfos) {
        List<DoctorInfoDTO> list = new ArrayList<>();
        try {
            for (ScheduleNumInfo scheduleNumInfo : scheduleNumInfos) {
                Doctor doctor = doctorService.synchronDoctor(scheduleNumInfo.getHosOrgCode(), scheduleNumInfo.getHosDeptCode(), scheduleNumInfo.getHosDoctCode());
                if (null == doctor) continue;
                if (scheduleNumInfo.getTimeRange().equals("1") || scheduleNumInfo.getTimeRange().equals("3")) {//app只展示上午和下午的排班
                    if (scheduleNumInfo.getTimeRange().equals("3"))
                        scheduleNumInfo.setTimeRange("2");//2本来代表下午，平台后来更改为3，为防止App显示出错，故服务段转化一下
                    DoctorInfoDTO doctorInfoDTO = new DoctorInfoDTO(doctor);
                    doctorInfoDTO.setHosName(scheduleNumInfo.getHosName());
                    doctorInfoDTO.setDeptName(scheduleNumInfo.getDeptName());
                    DoctorInfoDTO.Schedule schedule = new DoctorInfoDTO.Schedule();
                    schedule.setScheduleId(scheduleNumInfo.getScheduleId());
                    schedule.setVisitLevel(scheduleNumInfo.getVisitLevel());

                    int numSource = scheduleNumInfo.getReserveOrderNum() - scheduleNumInfo.getOrderedNum();//可预约号源数
                    if (numSource > 0) {//避免his上传的已预约的号源数大于号源总数导致可预约数为负数
                        schedule.setNumSource(numSource);
                    }

                    schedule.setVisitCost(scheduleNumInfo.getVisitCost());
                    schedule.setTimeRange(scheduleNumInfo.getTimeRange());
                    schedule.setScheduleDate(scheduleNumInfo.getScheduleDate());
                    if (!StringUtils.isEmpty(schedule.getScheduleDate())) {
                        schedule.weekDay = DateUtils.getWeek(schedule.getScheduleDate());
                    }
                    if (scheduleNumInfo.getStartTime() != null) {
                        schedule.startTime = scheduleNumInfo.getStartTime().substring(11, 19);
                    }
                    if (scheduleNumInfo.getEndTime() != null) {
                        schedule.endTime = scheduleNumInfo.getEndTime().substring(11, 19);
                    }
                    if (schedule.getNumSource() > 0) {
                        doctorInfoDTO.setIsFull(1);
                    }
                    if (StringUtils.isNotEmpty(uid) && doctorService.checkFavoritedDoctor(uid, doctor.getId())){
                        doctorInfoDTO.setConcern(1);
                    }
                    doctorInfoDTO.setSchedule(schedule);
                    list.add(doctorInfoDTO);
                }

                sortDocList(list);
            }
        } catch (Exception e) {
            log.info(Exceptions.getStackTraceAsString(e));
            return list;
        }
        return list;
    }



    @Override
    public List<DoctorInfoDTO> queryScheduleList(String uid, String hospitalCode, String hosDeptCode, String area) {
        long current = new Date().getTime();
        List<ScheduleNumInfo> scheduleNumInfos = queryAllSchedule(hospitalCode, hosDeptCode, area);
        if (null == scheduleNumInfos)
            return null;

        Map<String, ScheduleNumInfo> map = new HashMap<>();
        String key;
        for (ScheduleNumInfo scheduleNumInfo : scheduleNumInfos) {
            if (scheduleNumInfo.getTimeRange().equals("1") || scheduleNumInfo.getTimeRange().equals("3")) {//app只展示上午和下午的排班
                key = scheduleNumInfo.getHosOrgCode() + scheduleNumInfo.getHosDeptCode() + scheduleNumInfo.getHosDoctCode();
                if (map.get(key) == null) {
                    map.put(key, scheduleNumInfo);
                } else {
                    scheduleNumInfo.setOrderedNum(scheduleNumInfo.getOrderedNum() + map.get(key).getOrderedNum());//已预约数
                    scheduleNumInfo.setReserveOrderNum(scheduleNumInfo.getReserveOrderNum() + map.get(key).getReserveOrderNum());//可预约总数
                    map.put(key, scheduleNumInfo);
                }
            }
        }

        Doctor tempDoctor;
        DoctorInfoDTO tempDoctorInfoDTO;
        List<DoctorInfoDTO> data = new ArrayList<>();
        for (ScheduleNumInfo info : map.values()) {
            tempDoctor = doctorService.synchronDoctor(info.getHosOrgCode(), info.getHosDeptCode(), info.getHosDoctCode());
            if (tempDoctor == null)
                continue;//本地查询不到医生信息不展示其排班信息
            if (info.getReserveOrderNum() > info.getOrderedNum()) //医生未约满
                tempDoctor.setIsFull(1);

            tempDoctorInfoDTO = new DoctorInfoDTO(tempDoctor);
            if (StringUtils.isNotEmpty(uid) && doctorService.checkFavoritedDoctor(uid, tempDoctor.getId())){ //医生已关注
                tempDoctorInfoDTO.setConcern(1);
            }
            data.add(tempDoctorInfoDTO);
        }


        sortDocList(data);
        String time = (new Date().getTime() - current) + "";
        log.info(time);
        return data;
    }

    private void sortDocList(List<DoctorInfoDTO> data){
        Collections.sort(data,new Comparator<DoctorInfoDTO>() { //排序
            @Override
            public int compare(DoctorInfoDTO o1, DoctorInfoDTO o2) {
                if(o1.getConcern() != o2.getConcern()){
                    return o2.getConcern() - o1.getConcern(); //关注优先
                }
                return o2.getIsFull() - o1.getIsFull(); //未约满优先
            }
        });
    }

    private List<ScheduleNumInfo> queryAllSchedule(String hosOrgCode, String hosDeptCode, String area) {
        ScheduleInfoRequest request = new ScheduleInfoRequest();
        request.requestMessageHeader = reqMesHeaderUtil.generator();
        ScheduleInfoRequest.OrderInfoR orderInfoR = new ScheduleInfoRequest.OrderInfoR();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = DateUtils.addDay(new Date(), 1);
        Date end = DateUtils.addDay(new Date(), 7);
        orderInfoR.setStartTime(sdf.format(start));
        orderInfoR.setEndTime(sdf.format(end));
        orderInfoR.setHosOrgCode(hosOrgCode);
        orderInfoR.setHosDeptCode(hosDeptCode);
        request.orderInfoR = orderInfoR;
        String sign = signatureGenerator.generateSignature(request);
        request.requestMessageHeader.setSign(sign);
        String requestStr = JaxbUtil.convertToXml(request, "GBK");
        log.info(requestStr);
        OrderNumInfoResponse response;

        String hostialCityCode = getHostialCityCode(hosOrgCode);//根据医院编码反查城市编码
        String url = serverConfigServiceImpl.queryApiUrl(hostialCityCode, "1");
        orderInfoNumClient.setDefaultUri(url);

        response = orderInfoNumClient.getOrderNumInfoService(requestStr);

        if (response != null) {
            return response.getScheduleNumInfos();
        }
        return null;
    }

    @Override
    @Transactional
    public void saveDepartment(Department department) {
        if (StringUtils.isEmpty(department.getHospitalCode())) {
            throw new CommonException(2001, "科室对应医院编码不能为空");
        }
        if (StringUtils.isEmpty(department.getDeptCode())) {
            throw new CommonException(2002, "科室编码不能为空");
        }
        if (StringUtils.isEmpty(department.getDeptName())) {
            throw new CommonException(2021, "科室名称不能为空");
        }
        Hospital hospital = hospitalRepository.findByHospitalCode(department.getHospitalCode());
        if (null == hospital) {
            throw new CommonException(2003, "医院编码无效");
        }
        if (!department.getUpperDeptCode().equals("-1")) {
            Department upperDeptInfo = departmentRepository.selectByHospitalCodeAndDeptCode(department.getHospitalCode(), department.getUpperDeptCode());
            if (null == upperDeptInfo) {
                throw new CommonException(2004, "科室父编码无效");
            }
        } else {
            //一级科室不能设置为特色科室
            department.setIsSpecial("0");
        }
        Department oldDept = departmentRepository.selectByHospitalCodeAndDeptCode(department.getHospitalCode(), department.getDeptCode());
        Date nowDate = new Date();
        if (null != oldDept) {
            department.setId(oldDept.getId());
            department.setCreateTime(oldDept.getCreateTime());
        } else {
            department.setCreateTime(nowDate);
        }
        department.setUpdateTime(nowDate);
        departmentRepository.save(department);
    }

    @Override
    public void delDeptsByIds(String hospitalCode, Integer[] ids) {
        if (StringUtils.isEmpty(hospitalCode) || null == ids || ids.length == 0) {
            return;
        }
        List<Department> departments = departmentRepository.findAll(Arrays.asList(ids));
        if (null == departments || departments.isEmpty()) {
            return;
        }
        Set<Integer> vaildIds = new HashSet<>();
        for (Department department : departments) {
            if (department.getHospitalCode().equals(hospitalCode)) {
                vaildIds.add(department.getId());
            }
        }
        if (!vaildIds.isEmpty()) {
            departmentRepository.deleteByIds(vaildIds);
        }
    }

    @Override
    public List<DepartmentInfoDTO> getAllDepartmentList(String hospitalCode, int page, int pageSize) {
        List<Department> departments = departmentRepository.queryByHospitalCode(hospitalCode, (page - 1) * pageSize, pageSize);
            if (null == departments || departments.isEmpty()) {
            return null;
        }
        Set<String> upperDeptCode = new HashSet<>();
        for (Department department : departments) {
            upperDeptCode.add(department.getUpperDeptCode());
        }
        List<Department> upperDepartments = departmentRepository.selectByHospitalCodeAndDeptCodes(hospitalCode, upperDeptCode);
        Map<String, Department> upperDeptMap = new HashMap<>();
        if (null != upperDepartments && !upperDepartments.isEmpty()) {
            for (Department department : upperDepartments) {
                upperDeptMap.put(department.getDeptCode(), department);
            }
        }
        List<DepartmentInfoDTO> infoDTOs = new ArrayList<>();
        for (Department department : departments) {
            DepartmentInfoDTO infoDTO = new DepartmentInfoDTO(department);
            Department upperDept = upperDeptMap.containsKey(department.getUpperDeptCode()) ? upperDeptMap.get(department.getUpperDeptCode()) : null;
            infoDTO.mergeUpperDeptInfo(upperDept);
            infoDTOs.add(infoDTO);
        }
        return infoDTOs;
    }

    @Override
    public List<DepartmentInfoDTO> getDepartmentList(Map parms, int page, int pageSize) {
        StringBuffer sb = new StringBuffer("select * from tb_department_info where 1 = 1");
        List<Object> elementType = new ArrayList<>();
        if (null != parms.get("hospitalCode")) {
            sb.append(" and hospital_code = ?");
            elementType.add(parms.get("hospitalCode").toString());
        }
        if (null != parms.get("deptCode")) {
            sb.append(" and dept_code = ?");
            elementType.add(parms.get("deptCode").toString());
        }
        if (null != parms.get("deptName")) {
            sb.append(" and dept_name like ?");
            elementType.add("%" + parms.get("deptName").toString() + "%");
        }
        sb.append("  order by del_flag asc, dept_code asc ");
        sb.append(" limit ?,? ");
        elementType.add((page - 1) * pageSize);
        elementType.add(pageSize);
        List<Department> departments = jdbcTemplate.query(sb.toString(), elementType.toArray(), new BeanPropertyRowMapper<>(Department.class));

        if (null == departments || departments.isEmpty()) {
            return null;
        }
        Set<String> upperDeptCode = new HashSet<>();
        for (Department department : departments) {
            upperDeptCode.add(department.getUpperDeptCode());
        }
        List<Department> upperDepartments = departmentRepository.selectByHospitalCodeAndDeptCodes(parms.get("hospitalCode").toString(), upperDeptCode);
        Map<String, Department> upperDeptMap = new HashMap<>();
        if (null != upperDepartments && !upperDepartments.isEmpty()) {
            for (Department department : upperDepartments) {
                upperDeptMap.put(department.getDeptCode(), department);
            }
        }
        List<DepartmentInfoDTO> infoDTOs = new ArrayList<>();
        for (Department department : departments) {
            DepartmentInfoDTO infoDTO = new DepartmentInfoDTO(department);
            Department upperDept = upperDeptMap.containsKey(department.getUpperDeptCode()) ? upperDeptMap.get(department.getUpperDeptCode()) : null;
            infoDTO.mergeUpperDeptInfo(upperDept);
            infoDTOs.add(infoDTO);
        }
        return infoDTOs;
    }

    @Override
    public DeptsMenuDTO getDeptsMenu(String hospitalCode) {
        DeptsMenuDTO menuDTO = new DeptsMenuDTO();
        List<Department> departments = departmentRepository.selectByHospitalCode(hospitalCode);
        if (departments == null) {
            return null;
        }
        List<DeptsMenuDTO.DeptCodeName> firstDepts = new ArrayList<>();
        Map<String, List<DeptsMenuDTO.DeptCodeName>> secondDepts = new HashMap<>();
        for (Department department : departments) {
            if (department.getUpperDeptCode().equals("-1")) {
                firstDepts.add(new DeptsMenuDTO.DeptCodeName(department));
            } else {
                List<DeptsMenuDTO.DeptCodeName> secondDeptList = new ArrayList<>();
                if (secondDepts.containsKey(department.getUpperDeptCode())) {
                    secondDeptList = secondDepts.get(department.getUpperDeptCode());
                }
                secondDeptList.add(new DeptsMenuDTO.DeptCodeName(department));
                secondDepts.put(department.getUpperDeptCode(), secondDeptList);
            }
        }
        if (firstDepts.isEmpty()) {
            firstDepts.add(new DeptsMenuDTO.DeptCodeName("1", "科室信息"));
        }
        menuDTO.setFirstDepts(firstDepts);
        menuDTO.setSecondDepts(secondDepts);
        return menuDTO;
    }

    @Override
    public List<Department> getTopDepts(String hospitalCode) {
        return departmentRepository.queryAllTopDeps(hospitalCode);
    }

    @Override
    public List<Department> getSecondDepts(String hospitalCode) {
        return departmentRepository.queryAllSecondDepts(hospitalCode);
    }

    @Override
    public List<DepartmentInfoDTO> getDepartmentList(String hospitalCode, String upperDeptCode, int page, int pageSize) {
        return null;
    }

    @Override
    public int countDepartment(String hospitalCode) {
        return 0;
    }

    @Override
    public int countDepartment(String hospitalCode, String upperDeptCode) {
        return 0;
    }
}
