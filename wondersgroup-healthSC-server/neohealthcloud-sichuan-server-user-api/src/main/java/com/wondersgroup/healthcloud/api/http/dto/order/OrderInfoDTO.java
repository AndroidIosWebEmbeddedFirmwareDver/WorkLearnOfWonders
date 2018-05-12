package com.wondersgroup.healthcloud.api.http.dto.order;

import com.wondersgroup.healthcloud.entity.request.SubmitOrderByUserInfoRequest;
import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/11/8.
 */
@Data
public class OrderInfoDTO {

    private String scheduleId;
    private String numSourceId;
    private String payMode;
    private String payState;
    private String mediCardId;
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
