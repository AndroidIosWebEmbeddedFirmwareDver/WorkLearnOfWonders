package com.wondersgroup.healthcloud.services.orderws;

import com.wondersgroup.healthcloud.entity.request.orderws.OrderRequest;
import com.wondersgroup.healthcloud.entity.response.orderws.Response;

/**
 * Created by zhaozhenxing on 2016/11/7.
 */
public interface OrderWSService {

    Response reportOrderStatus(OrderRequest orderRequest);
}
