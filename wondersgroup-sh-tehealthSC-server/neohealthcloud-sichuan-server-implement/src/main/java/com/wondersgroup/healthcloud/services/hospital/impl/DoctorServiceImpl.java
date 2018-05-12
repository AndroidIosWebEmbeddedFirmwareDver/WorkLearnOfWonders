package com.wondersgroup.healthcloud.services.hospital.impl;

import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.repository.hospital.DepartmentRepository;
import com.wondersgroup.healthcloud.jpa.repository.hospital.DoctorRepository;
import com.wondersgroup.healthcloud.jpa.repository.hospital.HospitalRepository;
import com.wondersgroup.healthcloud.services.hospital.DoctorService;
import com.wondersgroup.healthcloud.services.hospital.dto.DoctorBaseInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dukuanxin on 2016/11/7.
 */
@Service("doctorService")
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Doctor> queryByHospitalCode(String hospitalCode, String deptCode, int pageNo, int pageSize) {
        return doctorRepository.queryByHospitalCode(hospitalCode, deptCode, pageNo, pageSize);
    }

    @Override
    public Doctor queryByDoctorCode(String hospitalCode, String deptCode, String doctorCode) {
        return doctorRepository.queryByDoctorCode(hospitalCode, deptCode, doctorCode);
    }

    @Override
    public Doctor getInfoById(Integer doctorId) {
        return doctorRepository.findOne(doctorId);
    }

    @Override
    public void updateOrderCount(int doctorId) {
        Doctor doc = doctorRepository.findOne(doctorId);
        doc.setOrderCount(doc.getOrderCount() + 1);
        doctorRepository.saveAndFlush(doc);
    }

    @Override
    public void updateOrderCount(String hospitalCode, String deptCode, String doctorCode) {
        Doctor doctor = doctorRepository.queryByDoctorCode(hospitalCode, deptCode, doctorCode);
        if (doctor != null) {
            doctor.setOrderCount(doctor.getOrderCount() + 1);
            doctorRepository.save(doctor);
        }
    }

    @Override
    public Doctor synchronDoctor(String hospitalCode, String deptCode, String doctorCode) {
        Doctor target = doctorRepository.queryByDoctorCode(hospitalCode, deptCode, doctorCode);
        if (null != target) {
            return target;
        }
        List<Doctor> doctors = doctorRepository.queryByDoctorCode(hospitalCode, doctorCode);
        if (doctors.size() > 0) {
            target = doctors.get(0);
            target.setId(null);
            target.setCreateTime(new Date());
            target.setUpdateTime(new Date());
            target.setDeptCode(deptCode);
            target = doctorRepository.save(target);
        }
        return target;
    }

    @Override
    public List<DoctorBaseInfoDTO> findList(Map<String, Object> param, int page, int pageSize) {
        StringBuffer sb = new StringBuffer("select doc.*,dept.dept_name from tb_doctor_info doc  ");
        sb.append(" left join tb_department_info dept on doc.dept_code=dept.dept_code and doc.hospital_code=dept.hospital_code ");
        sb.append(" where 1=1 ");
        List<Object> elementType = new ArrayList<>();
        if (param.containsKey("doctorName")) {
            sb.append(" and doc.doctor_name like ?");
            elementType.add("%" + param.get("doctorName").toString() + "%");
        }
        if (param.containsKey("hospitalCode")) {
            sb.append(" and doc.hospital_code=?");
            elementType.add(param.get("hospitalCode").toString());
        }
        if (param.containsKey("deptCode")) {
            sb.append(" and doc.dept_code=?");
            elementType.add(param.get("deptCode").toString());
        }
        if (param.containsKey("delFlag")) {
            sb.append(" and doc.del_flag=?");
            elementType.add(param.get("delFlag").toString());
        }
        sb.append(" order by doc.del_flag asc, doc.id desc ");
        sb.append(" limit ?,? ");
        elementType.add((page - 1) * pageSize);
        elementType.add(pageSize);
        List<DoctorBaseInfoDTO> list = jdbcTemplate.query(sb.toString(), elementType.toArray(), new BeanPropertyRowMapper<>(DoctorBaseInfoDTO.class));
        return list;
    }

    @Override
    public int countByMap(Map<String, Object> param) {
        StringBuffer sb = new StringBuffer("select count(*) from tb_doctor_info doc where 1=1 ");
        List<Object> elementType = new ArrayList<>();
        if (param.containsKey("doctorName")) {
            sb.append(" and doc.doctor_name like ?");
            elementType.add("%" + param.get("doctorName").toString() + "%");
        }
        if (param.containsKey("hospitalCode")) {
            sb.append(" and doc.hospital_code=?");
            elementType.add(param.get("hospitalCode").toString());
        }
        if (param.containsKey("deptCode")) {
            sb.append(" and doc.dept_code=?");
            elementType.add(param.get("deptCode").toString());
        }
        if (param.containsKey("delFlag")) {
            sb.append(" and doc.del_flag=?");
            elementType.add(param.get("delFlag").toString());
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), elementType.toArray(), Integer.class);
        return null == count ? 0 : count;
    }

    @Override
    @Transactional
    public void saveDoctor(Doctor doctor) throws CommonException {
        if (StringUtils.isEmpty(doctor.getHospitalCode())) {
            throw new CommonException(2001, "医生所属医院编码不能为空");
        }
        if (StringUtils.isEmpty(doctor.getDoctorCode())) {
            throw new CommonException(2002, "医生编码不能为空");
        }
        if (StringUtils.isEmpty(doctor.getDeptCode())) {
            throw new CommonException(2002, "医生所属科室不能为空");
        }
        Hospital hospital = hospitalRepository.findByHospitalCode(doctor.getHospitalCode());
        if (null == hospital) {
            throw new CommonException(2003, "医生所属医院无效");
        }
        Department department = departmentRepository.selectByHospitalCodeAndDeptCode(doctor.getHospitalCode(), doctor.getDeptCode());
        if (null == department) {
            throw new CommonException(2004, "医生所属科室无效");
        }
        Doctor oldDoctor = doctorRepository.findDoctorInfo(doctor.getHospitalCode(), doctor.getDeptCode(), doctor.getDoctorCode());
        Date nowDate = new Date();
        if (null != oldDoctor) {
            if (StringUtils.isEmpty(doctor.getHeadphoto())) {
                doctor.setHeadphoto(oldDoctor.getHeadphoto());
            }
            doctor.setId(oldDoctor.getId());
            doctor.setOrderCount(oldDoctor.getOrderCount());
            doctor.setCreateTime(oldDoctor.getCreateTime());
        } else {
            doctor.setCreateTime(nowDate);
        }
        doctor.setUpdateTime(nowDate);
        doctorRepository.save(doctor);
    }

    @Override
    public void delete(Integer hosId, Integer depId, Integer docId) {
        if (hosId != null) {
            hospitalRepository.delete(hosId);
        }
        if (depId != null) {
            departmentRepository.delete(depId);
        }
        if (docId != null) {
            doctorRepository.delete(docId);
        }
    }
}
