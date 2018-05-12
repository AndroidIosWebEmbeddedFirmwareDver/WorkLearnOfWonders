package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nick on 2016/10/29.
 *
 * @author nick
 * 排班表的安排请求
 */
@Data
@XmlRootElement(name = "OrderInfoR")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfoR {

    private String startTime;
    private String endTime;
    private String hosOrgCode;
    private String hosDeptCode;
    private String hosDoctCode;
}
