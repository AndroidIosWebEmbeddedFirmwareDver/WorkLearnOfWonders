package com.wondersgroup.healthSC.services.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
@Data
@Entity
@Table(name = "tb_department_info")
public class Department {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name="hospital_code")
    private String hospitalCode;//医院代码
    @Column(name="dept_code")
    private String deptCode;//科室代码
    @Column(name="dept_name")
    private String deptName;//科室名称
    @Column(name="upper_dept_code")
    private String upperDeptCode;//上级科室代码
    @Column(name="dept_desc")
    private String deptDesc;//科室描述
    @Column(name="is_special")
    private String isSpecial; // 是否特色科室（0：否；1：是）
    @Column(name="del_flag")
    private String delFlag; // 删除标志
    @Column(name="create_time")
    private Date createTime; // 创建时间
    @Column(name="update_time")
    private Date updateTime; // 更新时间
}
