package com.wondersgroup.healthcloud.api.http.controllers.hospital;

import com.wondersgroup.healthcloud.api.http.dto.hospital.DeptInfoDTO;
import com.wondersgroup.healthcloud.api.http.dto.hospital.DoctorDTO;
import com.wondersgroup.healthcloud.api.http.dto.hospital.HospitalDTO;
import com.wondersgroup.healthcloud.api.http.dto.hospital.ScheduleDTO;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.entity.response.ScheduleNumInfo;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.evaluate.EvaluateDoctorService;
import com.wondersgroup.healthcloud.services.evaluate.EvaluateHospitalService;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateDoctorListDTO;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateHospitalListDTO;
import com.wondersgroup.healthcloud.services.favorite.FavoriteService;
import com.wondersgroup.healthcloud.services.hospital.DepartmentService;
import com.wondersgroup.healthcloud.services.hospital.DoctorService;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.hospital.dto.DoctorInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by dukuanxin on 2016/11/3.
 */
@RestController
@RequestMapping("/api")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private EvaluateDoctorService evaluateDoctorServiceImpl;
    @Autowired
    private EvaluateHospitalService evaluateHospitalServiceImpl;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/queryHospitls")
    @VersionRange
    public JsonListResponseEntity<HospitalDTO> queryHospitls(@RequestParam String cityCode, @RequestParam(required = false, defaultValue = "0") String flag) {
        JsonListResponseEntity<HospitalDTO> response = new JsonListResponseEntity<>();
        int pageNo = Integer.valueOf(flag);
        int pageSize = 10;
        List<Hospital> hospitals = hospitalService.queryByCityCode(cityCode, pageNo * pageSize, pageSize + 1);
        List<HospitalDTO> hospitalApis = getHospitals(hospitals);
        Boolean hasMore = false;
        if (hospitals != null && hospitals.size() > 10) {
            hospitalApis = hospitalApis.subList(0, 10);
            hasMore = true;
        } else {
            flag = null;
        }
        if (hasMore) {
            flag = String.valueOf(pageNo + 1);
        }
        response.setContent(hospitalApis, hasMore, null, flag);
        return response;
    }

    @VersionRange
    @WithoutToken
    @GetMapping("/queryHospitlInfo")
    public JsonResponseEntity<HospitalDTO> queryHospitlInfo(@RequestParam int hosId, @RequestParam(required = false) String userId) {
        JsonResponseEntity<HospitalDTO> response = new JsonResponseEntity<>();
        Hospital hospital = hospitalService.queryById(hosId);
        HospitalDTO hosInfo = new HospitalDTO(hospital);
        List<AppEvaluateHospitalListDTO> validList = evaluateHospitalServiceImpl.findValidListByHospitalId(hosId, 1, 4);
        if (validList.size() > 0) {
            hosInfo.setEvaluList(validList);
            hosInfo.setEvaluateCount(evaluateHospitalServiceImpl.countValidEvaluateHospital(hosId));
        } else {
            hosInfo.setEvaluateCount(0);
        }
        if (!StringUtils.isEmpty(userId)) {//检查用户是否关注
            if (favoriteService.isFavorHospital(userId, hosId)) {
                hosInfo.setConcern(1);
            } else {
                hosInfo.setConcern(0);
            }
        }
        response.setData(hosInfo);
        return response;
    }

    /**
     * hosDeptCode 不传，查询一级科室
     * hosDeptCode : -9 查询特色科室
     * hosDeptCode: n 查询n科室下面的二级科室
     */
    @GetMapping("/queryDeps")
    @VersionRange
    public JsonResponseEntity queryDeps(@RequestParam String hospitalCode, @RequestParam(required = false) String hosDeptCode, @RequestParam(required = false) String area) {
        JsonResponseEntity response = new JsonResponseEntity();
        List<Department> depInfos;
        if (null != hosDeptCode && hosDeptCode.equals("-9")) {
            depInfos = departmentService.querySpecialDept(hospitalCode);
        } else {
            depInfos = departmentService.queryDept(hospitalCode, hosDeptCode);
        }
        List<DeptInfoDTO> deptInfoDTOs = DeptInfoDTO.infoDTO(depInfos);
        //查询一级科室的时候，判断是否有特色科室，有就把特色科室放到第一个
        if (null == hosDeptCode) {
            List<Department> specialDepts = departmentService.querySpecialDept(hospitalCode);
            if (null != specialDepts && !specialDepts.isEmpty()) {
                DeptInfoDTO specialDto = new DeptInfoDTO();
                specialDto.setDeptName("特色科室");
                specialDto.setHosDeptCode("-9");
                specialDto.setHosOrgCode(hosDeptCode);
                deptInfoDTOs.add(0, specialDto);
            }
        }
        response.setData(deptInfoDTOs);
        return response;
    }

    @GetMapping("/queryDoctorList")
    @VersionRange
    public JsonListResponseEntity<DoctorInfoDTO> queryDoctors(@RequestParam(required = false) String uid, @RequestParam String hospitalCode, @RequestParam String hosDeptCode, @RequestParam(required = false) String area) {
        JsonListResponseEntity<DoctorInfoDTO> response = new JsonListResponseEntity<>();
        //号源信息查询不分页
        List<DoctorInfoDTO> doctorInfo = departmentService.queryScheduleList(uid, hospitalCode, hosDeptCode, area);
        response.setContent(doctorInfo, false, null, null);
        return response;
    }

    @GetMapping("/querySchedulByTime")
    @VersionRange
    public JsonListResponseEntity querySchedulByTime(@RequestParam(required = false) String uid, @RequestParam String time, @RequestParam String hospitalCode, @RequestParam String hosDeptCode, @RequestParam(required = false) String area) {
        JsonListResponseEntity response = new JsonListResponseEntity();
        List<ScheduleNumInfo> scheduleNumInfos = departmentService.queryScheduleNumInfo(time, hospitalCode, hosDeptCode, area);
        //号源信息查询不分页
        List<DoctorInfoDTO> scheduleByTime = departmentService.getScheduleByTime(uid, scheduleNumInfos);
      /*  if(scheduleNumInfos.size()>10){
            scheduleNumInfos.subList(pageNo*pageSize,(pageNo+1)*pageSize);
            scheduleByTime = departmentService.getScheduleByTime(scheduleNumInfos);
            hasMore = true;
        }else{
            scheduleByTime = departmentService.getScheduleByTime(scheduleNumInfos);
            flag = null;
        }
        if (hasMore){
            flag = String.valueOf(pageNo+1);
        }*/

        response.setContent(scheduleByTime, false, null, null);
        return response;
    }

    @GetMapping("/querySchedulInfo")
    @VersionRange
    public JsonResponseEntity querySchedulInfo(@RequestParam(required = false) String uid, @RequestParam String hospitalCode, @RequestParam String hosDeptCode, @RequestParam String hosDoctCode, @RequestParam(required = false) String area) {
        JsonResponseEntity response = new JsonResponseEntity();

        Doctor doctor = doctorService.queryByDoctorCode(hospitalCode, hosDeptCode, hosDoctCode);
        if (null == doctor) {
            response.setMsg("未获取到医生信息");
            return response;
        }

        Map data = new HashMap();
        List<ScheduleNumInfo> scheduleNumInfos = departmentService.queryScheduleNumInfo(null, hospitalCode, hosDeptCode, hosDoctCode, area);
        if (null == scheduleNumInfos || scheduleNumInfos.size() == 0) {
            response.setMsg("该医生接下来一周没有排班信息");
            return response;
        }

        int concern = 0;//关注医生
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(uid) && doctorService.checkFavoritedDoctor(uid, doctor.getId())){
            concern = 1;
        }
        DoctorDTO doctorInfo = getDoctorInfo(doctor, scheduleNumInfos);
        List<ScheduleDTO> scheduleDTOs = ScheduleDTO.infoDTO(scheduleNumInfos);
        String systemTime = DateUtils.sdf_day.format(new Date());
        String week = DateUtils.getWeekOfDate(new Date());

        data.put("week", week);
        data.put("concern", concern);
        data.put("systemTime", systemTime);
        data.put("doctorInfo", doctorInfo);
        data.put("schedule", scheduleDTOs);
        response.setData(data);
        return response;
    }

    @VersionRange
    @WithoutToken
    @GetMapping("/queryDoctorInfo")
    public JsonResponseEntity queryDoctorInfo(@RequestParam String hospitalCode, @RequestParam String hosDeptCode, @RequestParam String hosDoctCode, @RequestParam(required = false) String userId) {

        JsonResponseEntity response = new JsonResponseEntity();
        Doctor doctor = doctorService.queryByDoctorCode(hospitalCode, hosDeptCode, hosDoctCode);
        Hospital hospital = hospitalService.findByHospitalCode(hospitalCode);
        if (doctor != null) {
            DoctorInfoDTO doctorInfo = new DoctorInfoDTO(doctor);
            doctorInfo.setDoctorDesc(doctor.getDoctorDesc());
            if (null != hospital) doctorInfo.setHosName(hospital.getHospitalName());
            int docotrId = doctor.getId();
            List<AppEvaluateDoctorListDTO> validList = evaluateDoctorServiceImpl.findValidListByDoctorId(docotrId, 1, 4);
            if (validList.size() > 0) {
                doctorInfo.setEvaluList(validList);
                doctorInfo.setEvaluateCount(evaluateDoctorServiceImpl.countValidEvaluateDoctor(docotrId));
            } else {
                doctorInfo.setEvaluateCount(0);
            }
            if (!StringUtils.isEmpty(userId)) {//检查用户是否关注
                if (favoriteService.isFavorDoctor(userId, docotrId)) {
                    doctorInfo.setConcern(1);
                } else {
                    doctorInfo.setConcern(0);
                }
            }
            response.setData(doctorInfo);
        }
        return response;
    }

    @GetMapping("/getSystemTime")
    @VersionRange
    public JsonResponseEntity getSystemTime() {
        JsonResponseEntity response = new JsonResponseEntity();
        String systemTime = DateUtils.sdf_day.format(new Date());
        String week = DateUtils.getWeekOfDate(new Date());
        Map map = new HashMap();
        map.put("date", systemTime);
        map.put("week", week);
        response.setData(map);
        return response;
    }

    List<HospitalDTO> getHospitals(List<Hospital> hospitals) {
        if (null == hospitals || hospitals.size() == 0) {
            return null;
        }
        List<HospitalDTO> hospitalDTOs = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            hospitalDTOs.add(new HospitalDTO(hospital));
        }
        return hospitalDTOs;
    }

    public DoctorDTO getDoctorInfo(Doctor doctor, List<ScheduleNumInfo> scheduleNumInfos) {
        ScheduleNumInfo scheduleNumInfo = scheduleNumInfos.get(0);
        DoctorDTO doctorInfo = new DoctorDTO();
        doctorInfo.setHosOrgCode(doctor.getHospitalCode());
        doctorInfo.setHosDeptCode(doctor.getDeptCode());
        doctorInfo.setHosDoctCode(doctor.getDoctorCode());
        doctorInfo.setHosName(scheduleNumInfo.getHosName());
        doctorInfo.setDeptName(scheduleNumInfo.getDeptName());
        doctorInfo.setDoctorName(doctor.getDoctorName());
        doctorInfo.setDoctorTitle(doctor.getDoctorTitle());
        doctorInfo.setHeadphoto(doctor.getHeadphoto());
        doctorInfo.setGender(doctor.getGender());
        return doctorInfo;
    }

}
