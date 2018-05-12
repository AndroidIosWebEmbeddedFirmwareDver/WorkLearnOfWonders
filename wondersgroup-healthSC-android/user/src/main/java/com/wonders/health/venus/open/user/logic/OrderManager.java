package com.wonders.health.venus.open.user.logic;

import com.wonders.health.venus.open.user.entity.Order;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.HashMap;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/11/16 15:22
 */

public class OrderManager {
    private HttpTools mHttpTools;

    public OrderManager() {
        mHttpTools = new HttpTools();
    }

    public void payOrder(String orderNo, String payType, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.setPath(UrlConst.GET_PAY_KEY);
        request.addQueryStringParameter("channel", payType);
        request.addQueryStringParameter("id", orderNo);
        mHttpTools.get(request, callback);
    }

    public void getOrder(String orderNo, ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.setPath(UrlConst.GET_PAY_INFO);
        request.addQueryStringParameter("id", orderNo);
        new HttpTools().get(request, callback);
    }

    public void testGetOrder(ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.setPath("http://10.1.64.194/sichuan-user/api/pay/testpay");

        new HttpTools().get(request, callback);
    }

    /**
     * 我的订单
     * @param status
     * @param uid
     * @param more_params
     * @param callBack
     */
    public void getOrderList(String status, String uid, HashMap<String, String> more_params, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        if (more_params != null) {
            request.addQueryMapParameter(more_params);
        }
        request.addQueryStringParameter("status", status);
        request.addQueryStringParameter("uid", uid);
        request.setPath(UrlConst.GET_ORDER_LIST);
        mHttpTools.get(request, callBack);
    }

    /**
     * 诊间支付列表
     * @param hospitalCode
     * @param uid
     * @param more_params
     * @param callBack
     */
    public void getClinicList(String hospitalCode, String uid, HashMap<String, String> more_params, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        if (more_params != null) {
            request.addQueryMapParameter(more_params);
        }
        request.addQueryStringParameter("hospitalCode", hospitalCode);
        request.addQueryStringParameter("uid", uid);
        request.setPath(UrlConst.GET_UNPAYRECORDS_LIST);
        mHttpTools.get(request, callBack);
    }

    /**
     * 生成诊间支付订单
     * @param business
     * @param uid
     * @param price
     * @param callBack
     */
    public void generateClinicOrder(Order.Business business, String uid, String price, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        if(business != null){
            request.addBodyParameter("prescriptionNum", business.prescriptionNum);
            request.addBodyParameter("hospitalName", business.hospitalName);
            request.addBodyParameter("time", business.time);
            request.addBodyParameter("deptName", business.deptName);
            request.addBodyParameter("hospitalCode", business.hospitalCode);
            request.addBodyParameter("jzlsh", business.jzlsh);
        }
        request.addBodyParameter("price", price);
        request.addBodyParameter("uid", uid);
        request.setPath(UrlConst.GENERATE_CLINIC_ORDER);
        mHttpTools.post(request, callBack);
    }

    /**
     * 在线支付挂号费支付
     * @param uid
     * @param more_params
     * @param callBack
     */
    public void getAppointList(String uid, HashMap<String, String> more_params, ResponseCallback callBack) {
        SignRequest request = new SignRequest();
        if (more_params != null) {
            request.addQueryMapParameter(more_params);
        }
        request.addQueryStringParameter("uid", uid);
        request.setPath(UrlConst.GET_APPOINT_LIST);
        mHttpTools.get(request, callBack);
    }
}
