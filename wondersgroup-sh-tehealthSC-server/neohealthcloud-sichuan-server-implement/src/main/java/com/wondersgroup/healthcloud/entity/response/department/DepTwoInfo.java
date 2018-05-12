package com.wondersgroup.healthcloud.entity.response.department;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dukuanxin on 2016/11/5.
 */
@Data
@XmlRootElement(name = "Result")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepTwoInfo {
    //医院代码
    public String hosOrgCode;
    //医院名称
    public String hosName;
    //上级科室代码
    public String topHosDeptCode;
    //科室代码
    public String hosDeptCode;
    //科室名称
    public String deptName;
    //科室简介
    public String deptDesc;
}

