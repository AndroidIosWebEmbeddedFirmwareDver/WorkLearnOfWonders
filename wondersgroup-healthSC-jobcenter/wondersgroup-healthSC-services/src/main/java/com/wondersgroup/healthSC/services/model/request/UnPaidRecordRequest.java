package com.wondersgroup.healthSC.services.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nick on 2016/11/9.
 * @author nick
 */
@Data
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnPaidRecordRequest {

    private String yljgdm; //医疗机构代码
    private String klx; //卡类型
    private String kh;  //卡号

}
