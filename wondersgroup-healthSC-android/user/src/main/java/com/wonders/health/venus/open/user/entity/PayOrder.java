package com.wonders.health.venus.open.user.entity;


import com.wonders.health.venus.open.user.component.pay.PayOrderInfo;

import java.io.Serializable;

/**
 * 类描述：
 * 创建人：thh
 * 创建时间：2016/3/1 13:31
 */
public class PayOrder extends Order implements Serializable {
    public String id;
    public String showOrderId;
    public String orderId;
    public String subject;
    public String body;
    public String description;
    public long amount; // 单位：分
    public String status;
    public long updateTime;
    public String time;//开方时间
    public String hospitalName;
    public int time_left;
    //新增子商户号 2017/4/14
    public String submerno;
    //链支付appid  2017/6/13
    public String appid;


    public PayOrderInfo getPayInfo() {
        PayOrderInfo info = new PayOrderInfo();
        info.i = amount;
        info.goodsTitle = subject;
        info.goodsDesc = body;
        info.orderTitle = id;
        info.orderDesc = description;
        info.appid=appid;
        info.submerno = submerno;
        return info;
    }
}
