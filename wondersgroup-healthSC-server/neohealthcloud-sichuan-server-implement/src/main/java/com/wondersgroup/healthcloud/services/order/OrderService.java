package com.wondersgroup.healthcloud.services.order;

import com.wondersgroup.healthcloud.entity.request.*;
import com.wondersgroup.healthcloud.entity.response.*;

import java.util.List;

/**
 * 预约订单服务
 * 用于调用区域平台WebService服务
 * Created by zhaozhenxing on 2016/11/2.
 */
public interface OrderService {
    List<QueryOrderInfoListResponse.Result> getOrderList(QueryOrderInfoListRequest.OrderInfo orderInfo, String... cityCodes);

    GetOrderDetailInfoResponse.Result getOrderDetail(GetOrderDetailInfoRequest.OrderInfo orderInfo, String... cityCodes);

    SubmitOrderByUserInfoResponse submitOrder(SubmitOrderByUserInfoRequest.OrderInfo orderInfo, String... cityCodes);

    OrderCancelInfoResponse cancelOrder(OrderCancelInfoRequest.OrderInfo orderInfo, String... cityCodes);

    OrderPayCompletedResponse orderPayCompleted(OrderPayCompletedRequest.OrderInfo orderInfo, String... cityCodes);
}
