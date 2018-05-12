package com.wondersgroup.healthcloud.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.pay.PayOrderDTO;
import lombok.Data;

import java.util.Date;

/**
 * Created by nick on 2016/11/11.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    private String orderType;
    private String orderId;
    private String price;
    private String businessOrderState;//及时性的业务订单状态数据
    private JsonNode business;
    private String payStatus;
    private PayOrderDTO pay_order;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    private Integer isEvaluated;
    private String showOrderId;

}
