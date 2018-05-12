package com.wondersgroup.healthcloud.entity.response.department;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dukuanxin on 2016/11/4.
 */
@XmlRootElement(name = "Result")
@Data
public class DepInfo {
    //医院代码
    private String hosOrgCode;
    //医院名称
    private String hosName;
    //科室代码
    private String hosDeptCode;
    //科室名称
    private String deptName;
    //科室简介
    private String deptDesc;
}
