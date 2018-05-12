package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhaozhenxing on 2016/11/4.
 */
@Data
@XmlRootElement(name = "Response")
public class SubmitOrderByUserInfoResponse extends BaseResponse {

    @XmlElement(name = "Result")
    public Result result;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Result {
        private String scheduleId;//排版id
        private String numSourceId;//号源id
        private String orderId;//系统预约单编码
        private String takePassword;//取号密码
        private String visitNo;//就诊序号
        private String platformUserId;//平台用户编码
        private String platformUser;//平台登录账号
        private String platformPwd;//平台用户密码
        private String actualCost;//实际支付金额，减免后需要支付的实际金额
    }
}
