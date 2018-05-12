package com.wondersgroup.healthcloud.services.pay.impl;

import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.services.pay.CCBOrderService;
import com.wondersgroup.healthcloud.services.pay.dto.CCBOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * Created by limenghua on 2017/1/12.
 */
@Service
public class CCBOrderServiceImpl implements CCBOrderService {

    private final String PRE_CCB_ORDER_URL = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain";
    private static final Logger logger = LoggerFactory.getLogger(CCBOrderServiceImpl.class);

    @Override
    public String getCCBUrl(CCBOrder ccbOrder) {
        StringBuffer urlStrB = new StringBuffer(PRE_CCB_ORDER_URL);
        urlStrB.append("?");

        if (ccbOrder == null) {
            throw new RuntimeException("订单不可为空");
        }

        Field[] fileds = ccbOrder.getClass().getDeclaredFields();
        for (Field f : fileds) {
            f.setAccessible(true);
            // 获取值
            try {
                Method method = ccbOrder.getClass().getMethod("get" + f.getName());
                String value = (String) method.invoke(ccbOrder);

                urlStrB.append(f.getName());
                urlStrB.append("=");
                urlStrB.append(value == null ? "" : value);
                urlStrB.append("&");
            } catch (NoSuchMethodException e) {
                logger.error("", e);
            } catch (IllegalAccessException e) {
                logger.error("", e);
            } catch (InvocationTargetException e) {
                logger.error("", e);
            }// end try
        }// end for

        String result = urlStrB.toString().substring(0, urlStrB.length() - 1);
        return result;
    }// end method

    @Override
    public String getCCBUrl(int payment) {
        CCBOrder order = new CCBOrder();
        order.setMERCHANTID("105320148140002");
        order.setPOSID("100001135");
        order.setBRANCHID("320000000");
        order.setORDERID(IdGen.uuid());

        DecimalFormat df = new DecimalFormat("#.##");
        String temp = df.format(Double.parseDouble(String.valueOf(payment)) / 100);
        // 单位：元
        order.setPAYMENT(temp);
        order.setCURCODE("01");
        order.setTXCODE("520100");
        order.setREMARK1("");
        order.setREMARK2("");
        order.setTYPE("1");
        order.setGATEWAY("");
        order.setCLIENTIP("128.128.80.125");
        order.setREGINFO("xiaofeixia");
        order.setPROINFO("digital");
        order.setREFERER("");
        order.setINSTALLNUM("3");
        order.setSMERID("111");
        order.setSMERNAME("%u5DE5%u827A%u7F8E%u672F%u5546%u5E97");
        order.setSMERTYPEID("112");
        order.setSMERTYPE("%u5BBE%u9986%u9910%u5A31%u7C7B");
        order.setTRADECODE("001");
        order.setTRADENAME("%u6D88%u8D39");
        order.setSMEPROTYPE("1");
        order.setPRONAME("%u5DE5%u827A%u54C1");
        order.setTHIRDAPPINFO("comccbpay105320148140002alipay");
        order.setTIMEOUT("20161028101226");
        order.setISSINSCODE("ICBC");
        order.setMAC("b2a1adfc9f9a44b57731440e31710740");
        String result = this.getCCBUrl(order);
        return result;
    }// end method
}
