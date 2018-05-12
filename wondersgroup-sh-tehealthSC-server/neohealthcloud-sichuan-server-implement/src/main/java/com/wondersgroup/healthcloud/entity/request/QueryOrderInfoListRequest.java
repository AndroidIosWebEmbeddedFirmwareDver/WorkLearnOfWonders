package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryOrderInfoListRequest extends BaseRequest {
    @XmlElement(name = "OrderInfo")
    public OrderInfo orderInfo;

    @Data
    @XmlRootElement(name = "OrderInfo")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderInfo {
        private String queryType;
        private String applyStartTime;
        private String applyEndTime;
        private String visitStartTime;
        private String visitEndTime;
        private String hosOrgCode;
        private String hosDeptCode;
        private String userCardType;
        private String userCardId;
        private String orderStatus;
        private String isValid;
        private String bindType;
        private String identifyCode;
    }
}