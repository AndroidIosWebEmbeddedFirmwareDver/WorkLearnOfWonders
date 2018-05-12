package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderPayCompletedRequest extends BaseRequest {
    @XmlElement(name = "OrderInfo")
    public OrderInfo orderInfo;

    @Data
    @XmlRootElement(name = "OrderInfo")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderInfo {
        private String scheduleId;//排版id
        private String numSourceId;//号源id
        private String orderId;//系统预约单编码
        private String frontProviderOrderId;//前台服务商订单id
        private String payMode;//支付方式
        private String payType;//支付厂商类型 alipay:支付宝 tenpay:财付通
        private String payTradeNo;//支付交易号
        private String payAmount;//支付金额
    }
}