package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetOrderDetailInfoResponse extends BaseResponse {

    @XmlElement(name = "Result")
    public Result result;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Result {
        private String orderId;
        private String numSourceId;
        private String createTime;
        private String orderTime;
        private String orderStatus;
        private String payMode;
        private String visitCost;
        private String takePassword;
        private String visitNo;
        private String hosOrgCode;
        private String hosOrgName;
        private String deptName;
        private String doctName;
        private String visitLevel;
        private String timeRange;
        private String startTime;
        private String endTime;
        private String platformUserId;
        private String platformPatientId;
        private String patientName;
        private String patientCardType;
        private String patientCardId;
        private String patientPhone;
        private String patientSex;
        private String patientBD;
    }
}