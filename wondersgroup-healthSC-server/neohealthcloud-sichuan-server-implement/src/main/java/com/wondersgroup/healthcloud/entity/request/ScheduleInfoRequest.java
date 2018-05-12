package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dukuanxin on 2016/11/9.
 */
@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleInfoRequest extends BaseRequest {

    @XmlElement(name = "OrderInfo")
    public OrderInfoR orderInfoR;

    @Data
    @XmlRootElement(name = "OrderInfoR")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderInfoR {

        private String startTime;
        private String endTime;
        private String hosOrgCode;
        private String hosDeptCode;

    }

}
