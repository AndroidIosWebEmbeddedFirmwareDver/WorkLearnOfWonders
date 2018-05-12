package com.wondersgroup.healthcloud.services.beinhospital;

import com.wondersgroup.healthcloud.services.beinhospital.dto.MyOrder;

/**
 * Created by nick on 2016/11/14.
 */
public interface PayOrderService {
    MyOrder getMyOrders(String uid);

    MyOrder getMyOrders(String uid, String cityCode);
}
