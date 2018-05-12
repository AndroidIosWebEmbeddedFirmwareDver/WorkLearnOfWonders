package com.wondersgroup.healthcloud.services.pay;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wondersgroup.healthcloud.common.utils.MD5Utils;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.entity.pay.SLHospitalCCBInfo;
import com.wondersgroup.healthcloud.jpa.repository.pay.SLHospitalCCBInfoRepository;
import com.wondersgroup.healthcloud.services.pay.dto.CCBCallbackDto;

import CCBSign.RSASig;

@Service
@Transactional(readOnly = true)
public class CCBPayService {

    private static final String CCB_BANK_URL = "https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain";

    @Autowired
    SLHospitalCCBInfoRepository hospitalCCBInfoRepository;

    @Autowired
    PayService payService;

    public String generatePayUrl(String orderId, String hospitalId, String clientIp) {
        SLHospitalCCBInfo hospitalCCBInfo = hospitalCCBInfoRepository.findByHospitalId(hospitalId);
        PayOrder payOrder = payService.findOne(orderId);

        String merchantId = hospitalCCBInfo.getMerchantId();
        String posId = hospitalCCBInfo.getPosId();
        String branchId = "510000000";
        String bankOrderId = payOrder.getShowOrderId();
        String payment = getPayment(payOrder.getAmount());
        String curcode = "01";
        String txcode = "520100";
        String remark1 = hospitalId; // 医院ID，支付回调时使用
        String remark2 = orderId; // 订单ID，支付回调时使用
        String type = "1";
        String gateway = "";
        String pub = getPub(hospitalCCBInfo.getPub());
        String reginfo = StringEscapeUtils.escapeEcmaScript(hospitalCCBInfo.getHospitalName());
        String proinfo = StringEscapeUtils.escapeEcmaScript(payOrder.getSubject());

        String temp = String.format(
                "MERCHANTID=%s&POSID=%s&BRANCHID=%s&ORDERID=%s&PAYMENT=%s&CURCODE=%s&TXCODE=%s&REMARK1=%s&REMARK2=%s&TYPE=%s&PUB=%s&GATEWAY=%s&CLIENTIP=%s&REGINFO=%s&PROINFO=%s&REFERER=",
                merchantId, posId, branchId, bankOrderId, payment, curcode, txcode, remark1, remark2, type, pub,
                gateway, clientIp, reginfo, proinfo);

        String mac = MD5Utils.md5(temp);

        String url = CCB_BANK_URL + "?" + temp.replace("&PUB=" + pub, "") + "&MAC=" + mac;
        return url;
    }

    public boolean verifySigature(CCBCallbackDto dto) {
        String src = String.format(
                "POSID=%s&BRANCHID=%s&ORDERID=%s&PAYMENT=%s&CURCODE=%s&REMARK1=%s&REMARK2=%s&ACC_TYPE=%s&SUCCESS=%s&TYPE=%s&REFERER=%s&CLIENTIP=%s&ACCDATE=%s",
                dto.getPOSID(), dto.getBRANCHID(), dto.getORDERID(), dto.getPAYMENT(), dto.getCURCODE(),
                dto.getREMARK1(), dto.getREMARK2(), dto.getACC_TYPE(), dto.getSUCCESS(), dto.getTYPE(),
                dto.getREFERER(), dto.getCLIENTIP(), dto.getACCDATE());

        // ACC_TYPE 仅服务器通知中有此字段，页面通知无此字段
        if (StringUtils.isEmpty(dto.getACC_TYPE())) {
            src = src.replace("&ACC_TYPE=null", "");
        }
        if (StringUtils.isEmpty(dto.getACCDATE())) {
            src = src.replace("&ACCDATE=null", "");
        }

        String hospitalId = dto.getREMARK1(); // 医院ID
        SLHospitalCCBInfo hospitalCCBInfo = hospitalCCBInfoRepository.findByHospitalId(hospitalId);

        RSASig rsaSig = new RSASig();
        rsaSig.setPublicKey(hospitalCCBInfo.getPub());
        boolean flag = rsaSig.verifySigature(dto.getSIGN(), src);

        return flag && "Y".equals(dto.getSUCCESS());
    }

    private String getPayment(Long amount) {
        BigDecimal decimal = new BigDecimal(amount);
        return decimal.divide(new BigDecimal(100)).toString();
    }

    /**
     * 取公钥后30位
     *
     * @param pub 公钥
     * @return
     */
    private String getPub(String pub) {
        return pub.substring(pub.length() - 30);
    }

}
