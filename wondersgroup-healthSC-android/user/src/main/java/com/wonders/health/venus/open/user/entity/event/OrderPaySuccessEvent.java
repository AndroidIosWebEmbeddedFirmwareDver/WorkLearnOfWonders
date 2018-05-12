package com.wonders.health.venus.open.user.entity.event;

/**
 * 类描述：订单支付成功事件
 * 创建人：Bob
 * 创建时间：2016/11/22 10:37
 */

public class OrderPaySuccessEvent {
    public String orderId;

    public OrderPaySuccessEvent(String orderId) {
        this.orderId = orderId;
    }
}
