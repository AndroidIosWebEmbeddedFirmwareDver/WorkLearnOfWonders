package com.wondersgroup.healthcloud.api.http.dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.entity.response.GetOrderDetailInfoResponse;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by zhaozhenxing on 2016/11/18.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailDTO {
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
    private Integer isEvaluated;
    private String showOrderId;
    private String channel;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(GetOrderDetailInfoResponse.Result result) {
        this.orderId = result.getOrderId();
        this.numSourceId = result.getNumSourceId();
        this.createTime = result.getCreateTime();
        this.orderTime = result.getOrderTime();
        this.orderStatus = result.getOrderStatus();
        this.payMode = result.getPayMode();
        try {
            BigDecimal price = new BigDecimal(result.getVisitCost());
            this.visitCost = price.setScale(2).toString();
        } catch (Exception ex) {
            this.visitCost = "" + Double.valueOf(result.getVisitCost());
        }
        this.takePassword = result.getTakePassword();
        this.visitNo = result.getVisitNo();
        this.hosOrgCode = result.getHosOrgCode();
        this.hosOrgName = result.getHosOrgName();
        this.deptName = result.getDeptName();
        this.doctName = result.getDoctName();
        this.visitLevel = result.getVisitLevel();
        this.timeRange = result.getTimeRange();
        this.startTime = result.getStartTime();
        this.endTime = result.getEndTime();
        this.platformUserId = result.getPlatformUserId();
        this.platformPatientId = result.getPlatformPatientId();
        this.patientName = result.getPatientName();
        this.patientCardType = result.getPatientCardType();
        this.patientCardId = result.getPatientCardId();
        this.patientPhone = result.getPatientPhone();
        this.patientSex = result.getPatientSex();
        this.patientBD = result.getPatientBD();
    }
}
