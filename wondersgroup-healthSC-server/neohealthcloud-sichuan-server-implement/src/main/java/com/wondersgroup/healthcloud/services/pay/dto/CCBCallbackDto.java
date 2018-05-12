package com.wondersgroup.healthcloud.services.pay.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CCBCallbackDto {

    private String POSID;
    private String BRANCHID;
    private String ORDERID;
    private String PAYMENT;
    private String CURCODE;
    private String REMARK1;
    private String REMARK2;
    private String ACC_TYPE; //仅服务器通知中有此字段，页面通知无此字段
    private String SUCCESS; //成功－Y，失败－N
    private String TYPE; //1- 防钓鱼接口
    private String REFERER; //跳转到网银之前的系统 Referer，因为长度不可控，目前截取前100位
    private String CLIENTIP; //客户在网银系统中的IP
    private String ACCDATE; //系统记账日期,商户主管在商户后台设置返回记账日期时增加该字段，字段格式为YYYYMMDD（如20100907）未勾选时无此字段
    private String USRMSG; //支付账户信息,目前暂时返回空，该字段不参与验签运算
    private String SIGN; //数字签名

    public Long getAmount() {
        return new BigDecimal(PAYMENT).multiply(new BigDecimal(100)).longValue();
    }
}
