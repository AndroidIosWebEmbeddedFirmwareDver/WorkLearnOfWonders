package com.wonders.health.venus.open.user.logic;

import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.Map;

/**
 * class:  MyAppointmentManager
 * auth:  carrey
 * date: 16-11-14.
 * desc: 我的预约 manager
 */

public class MyAppointmentManager {
    private HttpTools mHttpTools;

    public MyAppointmentManager() {
        mHttpTools = new HttpTools();
    }


    /**
     * 获得预约列表
     *
     * @param status 订单类型 待就诊    2:已支付
     *               已就诊    4:履约(已取号)
     *               已取消    3:已退号
     */
    public void getAppointmentList(Map map, int status, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        if (map!=null){
            signRequest.addQueryMapParameter(map);
        }
        signRequest.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
        if (status != 0) {
            signRequest.addQueryStringParameter("state", String.valueOf(status));
        }
        mHttpTools.get(UrlConst.GET_APPOINTMENT_LIST, signRequest, callback);

    }

    /**
     * 获得预约详情
     *
     * @param orderId 订单类型
     */
    public void getAppointmentDetal(String orderId, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("orderId", orderId);
        mHttpTools.get(UrlConst.GET_APPOINTMENT_DETAL, signRequest, callback);

    }

    /**
     * 取消预约
     */
    public void cancelOrder(String orderId, ResponseCallback callback) {
        SignRequest params = new SignRequest();
        params.addBodyParameter("orderId", orderId);//预约订单号
        params.addBodyParameter("cancelObj", "1");//退号发起对象
        params.addBodyParameter("cancelReason", "1");//退号原因
        params.addBodyParameter("cancelDesc", "");//备注
        mHttpTools.post(UrlConst.CANCEL_APPOINTMENT, params, callback);

    }

    /**
     * 评价医生
     * @param orderId
     * @param content
     */
    public void evaluateDoctor(String orderId, String content, ResponseCallback callback) {
        SignRequest signRequest =new SignRequest();
//        signRequest.addBodyParameter("doctorId",doctorId);
        signRequest.addBodyParameter("orderId",orderId);
        signRequest.addBodyParameter("content",content);
        signRequest.addBodyParameter("uid",UserManager.getInstance().getUser().uid);
        signRequest.setPath(UrlConst.EVALUATE_DOCTOR);
        mHttpTools.post(signRequest,callback);


    }

}
