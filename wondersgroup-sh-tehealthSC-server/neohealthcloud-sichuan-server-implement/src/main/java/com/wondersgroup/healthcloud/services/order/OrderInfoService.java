package com.wondersgroup.healthcloud.services.order;

import com.wondersgroup.healthcloud.entity.po.Order;
import com.wondersgroup.healthcloud.jpa.entity.order.OrderInfo;

import java.util.List;

/**
 * 预约订单服务
 * 用于调用处理本地表业务逻辑
 * Created by zhaozhenxing on 2016/11/07.
 */

public interface OrderInfoService {
    List<Order> list(OrderInfo orderInfo, int pageNo, int pageSize);// 获取预约单列表

    OrderInfo detail(String id);// 获取预约单详情

    OrderInfo saveAndUpdate(OrderInfo orderInfo);// 保存预约单

    String validation(OrderInfo orderInfo);// 提交预约单验证

    String cancelValidation(OrderInfo orderInfo);// 取消预约单验证
}