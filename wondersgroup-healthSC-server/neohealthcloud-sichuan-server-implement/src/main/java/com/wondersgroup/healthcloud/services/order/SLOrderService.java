package com.wondersgroup.healthcloud.services.order;

import com.wondersgroup.healthcloud.entity.request.*;
import com.wondersgroup.healthcloud.entity.response.*;

import java.util.List;

/**
 * 预约订单服务
 * 用于调用区域平台WebService服务
 * Created by zhaozhenxing on 2016/11/2.
 */
public interface SLOrderService {
    List<QueryOrderInfoListResponse.Result> getOrderList(QueryOrderInfoListRequest.OrderInfo orderInfo);

    GetOrderDetailInfoResponse.Result getOrderDetail(GetOrderDetailInfoRequest.OrderInfo orderInfo);

    SubmitOrderByUserInfoResponse submitOrder(SubmitOrderByUserInfoRequest.OrderInfo orderInfo);

    OrderCancelInfoResponse cancelOrder(OrderCancelInfoRequest.OrderInfo orderInfo);

    OrderPayCompletedResponse orderPayCompleted(OrderPayCompletedRequest.OrderInfo orderInfo);
}
