package com.wondersgroup.healthcloud.services.prescription.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by tanxueliang on 16/11/7.
 */
@Data
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestDto {

    private String yljgdm; //医疗机构代码

    private String klx; //卡类型

    private String kh; //卡号

    private String jzlsh; //就诊流水号

    private String cfhm; //处方号码

    private String zzkfsj; //最早开方时间

    private String zwkfsj; //最晚开方时间

}
