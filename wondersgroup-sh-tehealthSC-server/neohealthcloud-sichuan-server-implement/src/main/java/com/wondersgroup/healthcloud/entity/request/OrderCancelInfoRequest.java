package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderCancelInfoRequest extends BaseRequest {
    @XmlElement(name = "OrderInfo")
    public OrderInfo orderInfo;

    @Data
    @XmlRootElement(name = "OrderInfo")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderInfo {
        private String orderId;
        private String hosOrgCode;
        private String numSourceId;
        private String platformUserId;
        private String takePassword;
        private String cancelObj;// 退号发起对象 1:患者;2:服务商
        private String cancelReason;// 退号原因 0:其他;1:患者主动退号
        private String cancelDesc;// 备注 只有退号原因为其他时才有用
    }
}

