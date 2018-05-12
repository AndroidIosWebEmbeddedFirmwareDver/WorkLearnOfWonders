package com.wondersgroup.healthcloud.jpa.entity.hospital;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_doctor_info")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String doctorCode;// 医生代码
    private String hospitalCode; // 医院代码
    private String deptCode;// 科室代码
    private String doctorName;// 医生名称
    private String gender;// 医生性别
    private String doctorTitle;// 医生职称
    private String doctorDesc;// 医生简介
    private String expertin;// 特长
    private String level;// 预约级别：1表示专家，2表示主任医师，3表示副主任医师，4表示主治医师
    private int orderCount; //接诊量
    private String headphoto;// 头像
    private String delFlag = "0"; // 删除标志
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
    @Transient
    private int isFull;//医生号源是否约满

}
