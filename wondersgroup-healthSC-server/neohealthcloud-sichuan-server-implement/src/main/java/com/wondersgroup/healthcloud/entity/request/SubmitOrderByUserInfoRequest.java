package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhaozhenxing on 2016/11/4.
 */
@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubmitOrderByUserInfoRequest extends BaseRequest {
    @XmlElement(name = "OrderInfo")
    public OrderInfo orderInfo;

    @Data
    @XmlRootElement(name = "OrderInfo")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OrderInfo {
        private String scheduleId;//排版id
        private String numSourceId;//号源id
        private String payMode;//支付方式 3：窗口支持，4：在线支付
        private String payState;//
        private String cardType;//诊疗卡卡类型
        private String mediCardId;//诊疗卡卡号
        private String hosOrgCode;
        private String hosName;
        private String hosDeptCode;
        private String deptName;
        private String hosDoctCode;
        private String doctName;
        private String visitLevelCode;
        private String visitLevel;
        private String visitCost;
        private String timeRange;
        private String visitNo;
        private String takePassword;
        private String orderTime;
        private String platformUserId;
        private String userCardType;
        private String userCardId;
        private String userName;
        private String userPhone;
        private String userSex;
        private String userBD;
        private String userContAdd;
        private String callBackUrl;
    }
}

