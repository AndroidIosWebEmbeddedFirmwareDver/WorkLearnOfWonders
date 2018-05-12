package com.wondersgroup.healthSC.services.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
@Data
@Entity
@Table(name = "tb_doctor_info")
public class Doctor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="hospital_code")
    private String hospitalCode;//医院代码
    @Column(name="dept_code")
    private String deptCode;//科室代码
    @Column(name="doctor_code")
    private String doctorCode;//医生代码
    @Column(name="doctor_name")
    private String doctorName;//医生名称
    @Column(name="gender")
    private String gender;//医生性别
    @Column(name="doctor_title")
    private String doctorTitle;//医生职称
    @Column(name="doctor_desc")
    private String doctorDesc;//医生简介
    @Column(name="expertin")
    private String expertin;//特长
    @Column(name="level")
    private String level;//预约级别：1表示专家，2表示主任医师，3表示副主任医师，4表示主治医师
    @Column(name="headphoto")
    private String headphoto;//头像
    @Column(name="del_flag")
    private String delFlag; // 删除标志
    @Column(name="create_time")
    private Date createTime; // 创建时间
    @Column(name="update_time")
    private Date updateTime; // 更新时间
}
