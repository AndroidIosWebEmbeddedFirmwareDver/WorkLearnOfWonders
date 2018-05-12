package com.wondersgroup.healthcloud.services.pay;

import com.wondersgroup.healthcloud.services.pay.impl.CCBOrderServiceImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by limenghua on 2017/1/12.
 */
public class CCBOrderServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(CCBOrderServiceImplTest.class);
    private CCBOrderService ccbOrderService = new CCBOrderServiceImpl();

//    @Test
//    public void testGetCCBUrl1() throws Exception {
//        CCBOrder order = new CCBOrder();
//        order.setMERCHANTID("105320148140002");
//        order.setPOSID("100001135");
//        order.setBRANCHID("320000000");
//        order.setORDERID("88487");
//        order.setPAYMENT("0.01");
//        order.setCURCODE("01");
//        order.setTXCODE("520100");
//        order.setREMARK1("");
//        order.setREMARK2("");
//        order.setTYPE("1");
//        order.setGATEWAY("");
//        order.setCLIENTIP("128.128.80.125");
//        order.setREGINFO("xiaofeixia");
//        order.setPROINFO("digital");
//        order.setREFERER("");
//        order.setINSTALLNUM("3");
//        order.setSMERID("111");
//        order.setSMERNAME("%u5DE5%u827A%u7F8E%u672F%u5546%u5E97");
//        order.setSMERTYPEID("112");
//        order.setSMERTYPE("%u5BBE%u9986%u9910%u5A31%u7C7B");
//        order.setTRADECODE("001");
//        order.setTRADENAME("%u6D88%u8D39");
//        order.setSMEPROTYPE("1");
//        order.setPRONAME("%u5DE5%u827A%u54C1");
//        order.setTHIRDAPPINFO("comccbpay105320148140002alipay");
//        order.setTIMEOUT("20161028101226");
//        order.setISSINSCODE("ICBC");
//        order.setMAC("b2a1adfc9f9a44b57731440e31710740");
//        String url = ccbOrderService.getCCBUrl(order);
//        logger.info(url);
//    }

    @Test
    public void testGetCCBUrl() {
        String url = ccbOrderService.getCCBUrl(1);
        logger.info(url);
    }
}