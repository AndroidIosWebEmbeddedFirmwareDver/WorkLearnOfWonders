package com.wondersgroup.healthcloud.api.http.dto.hospital;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateDoctorDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by dukuanxin on 2016/11/5.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO {

    private Integer id;
    private String hosOrgCode; // 医院代码
    private String hosName;//医院名称
    private String hosDeptCode;// 科室代码
    private String deptName;//科室名称
    private String hosDoctCode;// 医生代码
    private String doctorName;// 医生名称
    private String gender;// 医生性别
    private String doctorTitle;// 医生职称
    private String doctorDesc;// 医生简介
    private String expertin;// 特长
    private String level;// 预约级别：1表示专家，2表示主任医师，3表示副主任医师，4表示主治医师
    private String headphoto;// 头像

}
