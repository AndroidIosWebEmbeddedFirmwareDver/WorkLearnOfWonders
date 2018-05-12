package com.wondersgroup.healthcloud.services.hospital.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Department;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateDoctorListDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by ys on 2017/04/15.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentInfoDTO {

    private Integer id;
    //医院代码
    private String hosOrgCode;
    //医院名称
    private String hosName;
    //科室代码
    private String deptCode;
    //科室名称
    private String deptName;
    //科室简介
    private String deptDesc;
    //科室地址(就诊地址)
    private String deptAddr;

    private String isSpecial = "0"; // 是否特色科室（0：否；1：是）

    private String delFlag = "0"; // 删除标志

    private String upperDeptCode;

    private String upperDeptName;

    public DepartmentInfoDTO() {
    }

    public DepartmentInfoDTO(Department department) {
        this.id = department.getId();
        this.hosOrgCode = department.getHospitalCode();
        this.deptCode = department.getDeptCode();
        this.upperDeptCode = department.getUpperDeptCode();
        this.deptName = department.getDeptName();
        this.deptDesc = department.getDeptDesc();
        this.deptAddr = department.getDeptAddr();
        this.isSpecial = department.getIsSpecial() == null ? "0" : department.getIsSpecial();
        this.delFlag = department.getDelFlag() == null ? "0" : department.getDelFlag();
    }

    public void mergeUpperDeptInfo(Department upperDeptInfo) {
        if (null == upperDeptInfo) {
            return;
        }
        this.upperDeptCode = upperDeptInfo.getDeptCode();
        this.upperDeptName = upperDeptInfo.getDeptName();
    }
}
