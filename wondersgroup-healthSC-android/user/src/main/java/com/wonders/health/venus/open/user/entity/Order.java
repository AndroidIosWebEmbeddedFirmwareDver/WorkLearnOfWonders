package com.wonders.health.venus.open.user.entity;


import com.wonders.health.venus.open.user.R;

import java.io.Serializable;


/**
 * 类描述：
 * 创建人：thh
 * 创建时间：2016/3/1 13:31
 */
public class Order implements Serializable {

    public static final String STATUS_4_NOTPAID = "1";//未支付
    public static final String STATUS_4_TREAT = "2";//未就诊
    public static final String STATUS_4_VISIT = "4";//已就诊
    public static final String STATUS_4_CANCEL = "3";//已取消d
    public static final String STATUS_4_BREAK = "6";//以爽约
    public static final String STATUS_4_INREFULD = "5";//待退费
    public String orderType;//订单类型:CLINIC是诊间支付 APPOINTMENT是预约挂号
    public String orderId;
    public String price;//元

    public Business business;
    public String payStatus;
    public String businessOrderState;
    public PayOrder pay_order;

    public String showOrderId;// 显示订单

    public int isEvaluated;// 我的预约是否已经评价0未评价 1 已评价
    /**
     * 是否已评价医生
     */
    public boolean isEvaluated(int isEvaluated) {
        return STATUS_4_VISIT.equals(businessOrderState) && 1 == isEvaluated;
    }
    /**
     * 是否可以取消
     */
    public boolean canCancel() {
        return STATUS_4_TREAT.equals(businessOrderState) || STATUS_4_NOTPAID.equals(businessOrderState);
    }


    /**
     * 我的预约状态颜色
     */
    public int getStatusColor() {
        if (STATUS_4_TREAT.equals(businessOrderState) || STATUS_4_NOTPAID.equals(businessOrderState)) {
            return R.color.stc1;
        } else if (STATUS_4_VISIT.equals(businessOrderState)) {
            return R.color.stc4;
        } else if (STATUS_4_BREAK.equals(businessOrderState)) {//爽约
            return R.color.tc_addtion;
        } else
            return R.color.tc2;
    }

    /**
     * 我的预约文描
     */
    public String getStatusString() {
        if (STATUS_4_TREAT.equals(businessOrderState) || STATUS_4_NOTPAID.equals(businessOrderState)) {
            return "待就诊";
        } else if (STATUS_4_VISIT.equals(businessOrderState)) {
            return "已就诊";
        } else if (STATUS_4_BREAK.equals(businessOrderState)) {//爽约
            return "已爽约";
        } else if (STATUS_4_INREFULD.equals(businessOrderState)) {//退号
            return "待退款";
        }else
            return "已取消 ";
    }
    public static class Business implements Serializable {
        //诊间支付
        public String prescriptionNum;//处方号
        public String hospitalName;//医院名称
        public String time;//开方时间
        public String deptName;//科室
        public String hospitalCode;
        public String receiptNo;//这个收据编号只有支付成功之后才有
        public String jzlsh;//就诊流水号

        //预约挂号
        public String doctorName;//医生名字
        //        public String hospitalName;//医院名称
//        public String deptName;//科室
        public String outDoctorLevel;//出诊级别
        public String patientName;//就诊人

        public String deptCode;//科室code
        public String doctorCode;//医生code
        public String state;// 我的预约状态


//        /**
//         * 我的预约状态颜色
//         */
//        public int getStatusColor() {
//            if (STATUS_4_TREAT.equals(b) || STATUS_4_NOTPAID.equals(state)) {
//                return R.color.stc1;
//            } else if (STATUS_4_VISIT.equals(state)) {
//                return R.color.stc4;
//            } else if (STATUS_4_BREAK.equals(state)) {//爽约
//                return R.color.tc_addtion;
//            } else
//                return R.color.tc2;
//        }
//
//        /**
//         * 我的预约文描
//         */
//        public String getStatusString() {
//            if (STATUS_4_TREAT.equals(state) || STATUS_4_NOTPAID.equals(state)) {
//                return "待就诊";
//            } else if (STATUS_4_VISIT.equals(state)) {
//                return "已就诊";
//            } else if (STATUS_4_BREAK.equals(state)) {//爽约
//                return "已爽约";
//            } else if (STATUS_4_INREFULD.equals(businessOrderState)) {//退号
//                return "待退款";
//            }else
//                return "已取消 ";
//        }

//        /**
//         * 是否可以取消
//         */
//        public boolean canCancel() {
//            return STATUS_4_TREAT.equals(state) || STATUS_4_NOTPAID.equals(state);
//        }

        /**
         * 是否可以评价医生
         */
        public boolean isCanEvaluate(int isEvaluated) {
            return STATUS_4_VISIT.equals(state) && 0 == isEvaluated;
        }

//        /**
//         * 是否已评价医生
//         */
//        public boolean isEvaluated(int isEvaluated) {
//            return STATUS_4_VISIT.equals(state) && 1 == isEvaluated;
//        }

        /**
         * 是否已经取消
         */
        public boolean isCanceled() {
            return STATUS_4_CANCEL.equals(state);
        }

    }

    public String getPayStatusText() {
        if ("ALL".equals(payStatus)) {
            return "全部";
        } else if ("NOTPAY".equals(payStatus)) {
            return "待支付";
        } else if ("SUCCESS".equals(payStatus)) {
            return "已支付";
        } else if ("REFUND".equals(payStatus)) {
            return "退款中";
        } else if ("REFUNDSUCCESS".equals(payStatus)) {
            return "已退款";
        } else if ("EXPIRED".equals(payStatus)) {
            return "已超时";
        } else if ("FAILURE".equals(payStatus)) {//重新支付
            return "待支付";
        }

        return "";
    }


}
