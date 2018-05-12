package com.wondersgroup.healthcloud.api.http.controllers.lianpay;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.pay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p>
 * Created by zhangzhixiu on 04/11/2016.
 */
@RestController
@RequestMapping(path = "/api/pay")
public class InternalPayController {

    public static class PayCallback {
        public String success;
        public String trade_state;
        public String trade_msg;
        public Data data;

        public static class Data {
            public String app_id;
            public String submerno;
            public String channel;
            public String paytype;
            public String order_no;
            public String pre_order_no;
            public String description;
            public String amount;
            public String timestamp;
            public String transaction_id;
            public String sign;
        }
    }

    @Autowired
    private PayService payService;

    @Autowired
    private PaySchedule paySchedule;

    /*  @Autowired
    private LianPayUtilSelector selector;*/

    @Autowired
    private NewLianPayUtilSelector newSelector;

    @Autowired
    private LianPayFallback lianPayFallback;

    @Autowired
    private HospitalService hospitalService;

    @PostMapping(path = "/callback/pay")
    public String payCallback(@RequestBody PayCallback body) {
        Hospital hospital = hospitalService.findHospitalByAppid(body.data.app_id);
        String sign = newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).generateCallbackSign(body.success, Long.valueOf(body.data.amount), body.data.order_no);
        //String sign = selector.getById(body.data.app_id).generateCallbackSign(body.success, Long.valueOf(body.data.amount), body.data.order_no);
        if (!sign.equals(body.data.sign)) {
            return "failure";
        }
        try {
            payService.paySuccessCallback(body.data.order_no, String.valueOf(body.data.channel), Long.valueOf(body.data.amount));
            return "success";
        } catch (Exception e) {
            Boolean needToRefund = lianPayFallback.markOnFailure(body.data.order_no);
            if (needToRefund) {
                payService.refundOrderOnFailure(body.data.order_no);
            }
            throw new RuntimeException(e);
        }
    }


    @PostMapping(path = "/callback/refund")
    public String refundCallback(@RequestBody PayCallback body) {
        Hospital hospital = hospitalService.findHospitalByAppid(body.data.app_id);
        String sign = newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).generateCallbackSign(body.success, Long.valueOf(body.data.amount), body.data.order_no);
        if (!sign.equals(body.data.sign)) {
            return "failure";
        }
        payService.refundSuccessCallback(body.data.order_no, Long.valueOf(body.data.amount));
        return "success";
    }

    @PostMapping(path = "/timer")
    public String timerTrigger(@RequestParam String id,
                               @RequestParam PayOrder.Status status) {
        paySchedule.trigger(id, status);
        return "success";
    }
}
