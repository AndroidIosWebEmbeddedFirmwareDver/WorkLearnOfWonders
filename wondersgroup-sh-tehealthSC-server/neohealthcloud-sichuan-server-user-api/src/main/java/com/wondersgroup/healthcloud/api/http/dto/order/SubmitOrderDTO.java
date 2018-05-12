package com.wondersgroup.healthcloud.api.http.dto.order;

import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.entity.request.SubmitOrderByUserInfoRequest;
import com.wondersgroup.healthcloud.jpa.entity.order.OrderInfo;
import lombok.Data;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaozhenxing on 2016/11/11.
 */
@Data
public class SubmitOrderDTO {
    private String scheduleId;
    private String numSourceId;
    private String payMode;
    private String payState;
    private String cardType;//诊疗卡卡类型
    private String mediCardId;//诊疗卡卡卡号
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
    private String uid;

    public SubmitOrderByUserInfoRequest.OrderInfo toWSOrderInfo() {
        SubmitOrderByUserInfoRequest.OrderInfo orderInfo = new SubmitOrderByUserInfoRequest.OrderInfo();
        orderInfo.setScheduleId(this.scheduleId);
        orderInfo.setNumSourceId(this.numSourceId);
        orderInfo.setPayMode(this.payMode);
        orderInfo.setPayState(this.payState);
        orderInfo.setCardType(this.cardType);
        orderInfo.setMediCardId(this.mediCardId);
        orderInfo.setHosOrgCode(this.hosOrgCode);
        orderInfo.setHosName(this.hosName);
        orderInfo.setHosDeptCode(this.hosDeptCode);
        orderInfo.setDeptName(this.deptName);
        orderInfo.setHosDoctCode(this.hosDoctCode);
        orderInfo.setDoctName(this.doctName);
        orderInfo.setVisitLevelCode(this.visitLevelCode);
        orderInfo.setVisitLevel(this.visitLevel);
        orderInfo.setVisitCost(this.visitCost);
        orderInfo.setTimeRange(this.timeRange);
        orderInfo.setVisitNo(this.visitNo);
        orderInfo.setTakePassword(this.takePassword);
        orderInfo.setOrderTime(this.orderTime);
        orderInfo.setPlatformUserId(this.platformUserId);
        orderInfo.setUserCardType(this.userCardType);
        orderInfo.setUserCardId(this.userCardId);
        orderInfo.setUserName(this.userName);
        orderInfo.setUserPhone(this.userPhone);
        orderInfo.setUserSex(this.userSex);
        orderInfo.setUserBD(this.userBD);
        orderInfo.setUserContAdd(this.userContAdd);
        orderInfo.setCallBackUrl(this.callBackUrl);
        return orderInfo;
    }

    public OrderInfo toOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUid(this.uid);
        orderInfo.setTakePassword(this.takePassword);
        orderInfo.setScheduleId(this.scheduleId);
        orderInfo.setOriginalScheduleId(this.scheduleId);
        orderInfo.setNumSource(this.numSourceId);
        orderInfo.setCardType(this.userCardType);
        orderInfo.setUserPhone(this.userPhone);
        orderInfo.setPatientName(this.userName);
        orderInfo.setHosCode(this.hosOrgCode);
        orderInfo.setHosName(this.hosName);
        orderInfo.setDeptCode(this.hosDeptCode);
        orderInfo.setDeptName(this.deptName);
        orderInfo.setDoctCode(this.hosDoctCode);
        orderInfo.setDoctName(this.doctName);
        orderInfo.setOutpatientType(this.getVisitCost());
        orderInfo.setDate(this.orderTime);
        orderInfo.setMedi_card_type_code(this.getCardType());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date orderTime = formatter.parse(this.orderTime, pos);
        orderInfo.setWeek(DateUtils.getWeekOfDate(orderTime));

        orderInfo.setTimeRnge(this.timeRange);
        orderInfo.setCost(this.getVisitCost());
        orderInfo.setOriginalCost(this.getVisitCost());
        orderInfo.setVisitAddress(this.userContAdd);
        return orderInfo;
    }
}
