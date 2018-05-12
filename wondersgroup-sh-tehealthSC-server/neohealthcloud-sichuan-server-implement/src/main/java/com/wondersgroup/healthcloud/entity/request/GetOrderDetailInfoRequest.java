package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetOrderDetailInfoRequest extends BaseRequest {
    @XmlElement(name = "OrderInfo")
    public OrderInfo orderInfo;

    @Data
    @XmlRootElement(name = "OrderInfo")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderInfo {
        private String orderId;
    }
}

