package com.wondersgroup.healthcloud.entity.request.depaetment;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dukuanxin on 2016/11/4.
 */
@Data
@XmlRootElement(name = "HosInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class HosInfo {

    private String hosOrgCode;
}
