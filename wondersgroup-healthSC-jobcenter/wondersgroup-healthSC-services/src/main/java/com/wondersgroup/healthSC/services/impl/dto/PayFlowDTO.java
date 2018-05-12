package com.wondersgroup.healthSC.services.impl.dto;

import lombok.Data;

/**
 * Created by dukuanxin on 2017/4/25.
 */
@Data
public class PayFlowDTO {
    private String update_time;//交易时间
    private String show_order_id;//订单号
    private String cost;//金额
    private String pos_id;//柜台代码
    private String hos_name;//商户名称
    private String merchant_id;//商户代码
    private String status;//状态
}
