package com.wonders.health.venus.open.user.entity;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.logic.SignRequest;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.http.RequestParams;

/**
 * class:  MyAppointmentDetalEntity
 * auth:  carrey
 * date: 16-11-14.
 * desc:
 */

public class MyAppointmentDetalEntity {

    public static final String STATUS_4_NOTPAID = "1";//未支付
    public static final String STATUS_4_TREAT = "2";//未就诊
    public static final String STATUS_4_VISIT = "4";//已就诊
    public static final String STATUS_4_CANCEL = "3";//已取消
    public static final String STATUS_4_INREFULD = "5";//待退费
    public static final String STATUS_4_BREAK = "6";//以爽约
    /**
     * orderId : 222
     * numSourceId :
     * createTime : 2016-11-11 12:01:47
     * orderTime : 2015-11-05
     * orderStatus : 1
     * payMode : 3
     * visitCost : 1
     * takePassword : null
     * visitNo : 2
     * hosOrgCode : 450755531
     * hosOrgName : 成都市第一人民医院
     * deptName : 专家门诊
     * doctName : 周继明
     * visitLevel : 医师
     * timeRange : 上午
     * startTime :
     * endTime :
     * platformUserId : 2379
     * platformPatientId : 2379
     * patientName : 马国平
     * patientCardType : 01
     * patientCardId : 513701198011133110
     * patientPhone : 18818139008
     * patientSex : 1
     * patientBD :
     */

    public String orderId;
    public String numSourceId;
    public String createTime;
    public String orderTime;
    public String orderStatus;
    public String payMode;
    public String visitCost;
    public String takePassword;
    public String visitNo;
    public String hosOrgCode;
    public String hosOrgName;
    public String deptName;
    public String doctName;
    public String visitLevel;
    public String timeRange;
    public String startTime;
    public String endTime;
    public String platformUserId;
    public String platformPatientId;
    public String patientName;
    public String patientCardType;
    public String patientCardId;
    public String patientPhone;
    public String patientSex;
    public String patientBD;

    public int isEvaluated;
    public String showOrderId;

    /**
     * 我的预约状态颜色
     */
    public int getStatusColor() {
        if (STATUS_4_TREAT.equals(orderStatus) || STATUS_4_NOTPAID.equals(orderStatus)) {
            return R.color.stc1;
        } else if (STATUS_4_VISIT.equals(orderStatus)) {
            return R.color.stc4;
        } else if (STATUS_4_BREAK.equals(orderStatus)) {//爽约
            return R.color.tc_addtion;
        } else
            return R.color.tc2;
    }

    public String getStatusString() {
        if (STATUS_4_TREAT.equals(orderStatus) || STATUS_4_NOTPAID.equals(orderStatus)) {
            return "待就诊";
        } else if (STATUS_4_VISIT.equals(orderStatus)) {
            return "已就诊";
        } else if (STATUS_4_BREAK.equals(orderStatus)) {//爽约
            return "已爽约";
        }else if(STATUS_4_INREFULD.equals(orderStatus)){
            return "待退费";
        } else
            return "已取消 ";
    }

    public boolean canCancel() {
        return STATUS_4_TREAT.equals(orderStatus) || STATUS_4_NOTPAID.equals(orderStatus);
    }

    public boolean isCanEvaluate() {
        return STATUS_4_VISIT.equals(orderStatus) && 0 == isEvaluated;
    }
    /**
     * 是否已评价医生
     */
    public boolean isEvaluated() {
        return STATUS_4_VISIT.equals(orderStatus) && 1 == isEvaluated;
    }
    public boolean isCanceled() {
        return STATUS_4_CANCEL.equals(orderStatus);
    }

    /**
     * | orderId        | 预约订单号   | Y | - |
     * | hosOrgCode     | 医院代码     | Y | - |
     * | numSourceId    | 号源ID       | N | - |
     * | platformUserId | 平台用户编码 | Y | - |
     * | takePassword   | 取号密码     | N | - |
     * | cancelObj      | 退号发起对象 | Y | 1:患者,2:服务商 |
     * | cancelReason   | 退号原因     | Y | 0:其他,1:患者主动退号 |
     * | cancelDesc     | 备注         | N | 只有退号原因为其他时才有用 |
     */
    public RequestParams buildCancelOrderParams() {
        SignRequest params = new SignRequest();
        params.addBodyParameter("orderId", orderId);//预约订单号
        params.addBodyParameter("hosOrgCode", hosOrgCode);//医院代码

        params.addBodyParameter("numSourceId", "");//号源ID
        params.addBodyParameter("platformUserId",
                UserManager.getInstance().getUser().uid);//平台用户编码 uid

        params.addBodyParameter("takePassword", takePassword);//取号密码
        params.addBodyParameter("cancelObj", "1");//退号发起对象
        params.addBodyParameter("cancelReason", "1");//退号原因
        params.addBodyParameter("cancelDesc", "");//备注
        return params;
    }
}
