package com.wonders.health.venus.open.user.entity;
/*
 * Created by sunning on 2016/11/14.
 * 支付
 */

import com.wonders.health.venus.open.user.logic.UserManager;

public class PayInfo {

    /**
     * scheduleId	排班ID	Y
     * numSourceId	号源ID	N
     * payMode	支付方式	Y	默认:1,1:第三方支付,2:诊疗卡/健康卡支付,3:窗口支付,4:银行卡支付
     * payState	支付状态	Y	默认:2,1:已付费,2:未付费
     * mediCardId	诊疗卡卡号	N
     * hosOrgCode	医院代码	N
     * hosName	医院名称	N
     * hosDeptCode	科室代码	N
     * deptName	科室名称	N
     * hosDoctCode	医生代码	N
     * doctName	医生名称	N
     * visitLevelCode	出诊级别编码	N
     * visitLevel	出诊级别	N
     * visitCost	出诊费用	Y
     * timeRange	出诊时段	N
     * visitNo	就诊序号	N
     * takePassword	取号密码	N
     * orderTime	预约时间	Y
     * platformUserId	平台用户代码	N
     * uid	用户ID	Y
     * userCardType	用户证件类型	Y
     *
     * 01：居民身份证
     02：居民户口簿
     03：护照
     04：军官证（士兵证）
     05：驾驶执照
     06：港澳居民来往内地通行证
     07：台湾居民来往内地通行证
     99：其他
     *
     * userCardId	用户证件号码	Y
     * userName	用户名	Y
     * userPhone	用户电话号码	Y
     0：未知的性别
     1：男性
     2：女性
     5：女性改（变）为男性
     6：男性改（变）为女性
     9：未说明的性别
     * userSex	用户性别	N
     * userBD	用户生日	N
     */

    public String scheduleId;
    //默认:1,1:第三方支付,2:诊疗卡/健康卡支付,3:窗口支付,4:银行卡支付
    public int payMode = 4;
    //默认:2,1:已付费,2:未付费
    public int payState = 2;
    public String orderTime; //YYYY-MM-DD HH24:MI:SS
    public String uid;
    public String userCardType = "01";
    public String userCardId;
    public String userName;
    public String userPhone;
    public String numSourceId;
    public String mediCardId;
    public String hosOrgCode;
    public String hosName;
    public String hosDeptCode;
    public String deptName;
    public String hosDoctCode;
    public String doctName;
    public String visitLevelCode;
    public String visitLevel;
    public String visitCost;
    public String timeRange;
    public String visitNo;
    public String takePassword;
    public String platformUserId;
    public String userSex;
    public String userBD;

    public PayInfo(String scheduleId, int payMode, int payState, String orderTime, String uid, String userCardType, String userCardId, String userName, String userPhone) {
        this.scheduleId = scheduleId;
        this.payMode = payMode;
        this.payState = payState;
        this.orderTime = orderTime;
        this.uid = uid;
        this.userCardType = userCardType;
        this.userCardId = userCardId;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public PayInfo(DoctorListVO scheduleInfo) {
        this(scheduleInfo.schedule.scheduleId, scheduleInfo.schedule.scheduleDate + " " + scheduleInfo.schedule.startTime,
                scheduleInfo.schedule.visitCost, scheduleInfo.schedule.visitLevel, scheduleInfo.deptName,
                scheduleInfo.hosDeptCode, scheduleInfo.hosName, scheduleInfo.hosOrgCode,scheduleInfo.doctorName,scheduleInfo.hosDoctCode);
    }

    public PayInfo(String scheduleId, String orderTime, String visitCost, String visitLevel, String deptName, String hosDeptCode,String hosName,String hosOrgCode,String doctorName,String doctorCode) {
        this.scheduleId = scheduleId;
        this.orderTime = orderTime;
        this.visitCost = visitCost;
        this.visitLevel = visitLevel;
        this.deptName = deptName;
        this.hosDeptCode = hosDeptCode;
        this.hosOrgCode = hosOrgCode;
        this.hosName = hosName;
        this.doctName = doctorName;
//        this.visitLevelCode = visitLevelCode;
        this.hosDoctCode = doctorCode;
        User user = UserManager.getInstance().getUser();
        this.uid = user.uid;
        this.userCardId = user.idcard;
        this.userName = user.name;
        this.userPhone = user.mobile;
    }

}
