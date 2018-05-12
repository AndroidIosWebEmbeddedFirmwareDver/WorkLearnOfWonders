package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryOrderInfoListResponse extends BaseResponse {

    @XmlElementWrapper(name = "List")
    @XmlElement(name = "Result")
    public List<Result> result;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Result {
        private String orderId;
        private String scheduleId;
        private String numSourceId;
        private String orderTime;
        private String orderStatus;
        private String payMode;
        private String transactionNo;
        private String visitCost;
        private String visitNo;
        private String hosOrgCode;
        private String hosOrgName;
        private String deptCode;
        private String deptName;
        private String doctCode;
        private String doctName;
        private String visitLevelCode;
        private String visitLevel;
        private String timeRange;
        private String createTime;
        private String platformUserId;
        private String platformPatientId;
        private String patientName;
        private String patientCardType;
        private String patientCardId;
        private String patientPhone;
        private String patientSex;
        private String patientBD;
        private String scheduleDate;
    }
}