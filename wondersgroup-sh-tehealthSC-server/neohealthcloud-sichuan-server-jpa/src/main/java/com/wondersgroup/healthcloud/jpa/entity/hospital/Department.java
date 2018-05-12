package com.wondersgroup.healthcloud.jpa.entity.hospital;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_department_info")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String hospitalCode;// 医院代码
    private String deptCode;// 科室代码
    private String deptName;// 科室名称
    private String upperDeptCode;// 上级科室代码
    private String deptDesc;// 科室描述
    private String deptAddr;// 科室地址(就诊地址)
    private String isSpecial = "0"; // 是否特色科室（0：否；1：是）
    private String delFlag = "0"; // 删除标志
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间
}
