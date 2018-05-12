package com.wondersgroup.healthcloud.api.http.dto.order;

import com.wondersgroup.healthcloud.entity.request.OrderCancelInfoRequest;
import lombok.Data;

/**
 * Created by zhaozhenxing on 2016/11/9.
 */
@Data
public class CancelOrderDTO {
    private String orderId;// 本地预约订单ID
    private String cancelObj;// 退号发起对象 1:患者;2:服务商
    private String cancelReason;// 退号原因 0:其他;1:患者主动退号
    private String cancelDesc;// 备注 只有退号原因为其他时才有用

    public OrderCancelInfoRequest.OrderInfo toWSOrderInfo() {
        OrderCancelInfoRequest.OrderInfo orderInfo = new OrderCancelInfoRequest.OrderInfo();
        orderInfo.setCancelObj(this.cancelObj);
        orderInfo.setCancelReason(this.cancelReason);
        orderInfo.setCancelDesc(this.cancelDesc);
        return orderInfo;
    }
}
