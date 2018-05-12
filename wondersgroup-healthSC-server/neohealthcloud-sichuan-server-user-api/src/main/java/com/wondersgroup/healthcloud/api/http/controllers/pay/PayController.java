package com.wondersgroup.healthcloud.api.http.controllers.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.pay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.servlet.ServletRequestIPAddressUtil;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;

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
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private CCBPayService ccbPayService;

    @Autowired
    private NewLianPayUtilSelector newLianPayUtilSelector;

    @Autowired
    HospitalService hospitalService;

    @GetMapping(path = "/info")
    @VersionRange
    public JsonResponseEntity<PayOrderDTO> orderInfo(@RequestParam String id) {
        JsonResponseEntity<PayOrderDTO> response = new JsonResponseEntity<>();

        PayOrder payOrder = payService.findOne(id);
        PayOrderDTO data = hospitalService.getLianPayParam(payOrder);
        response.setData(data);

        return response;
    }

    @GetMapping(path = "/key")
    @VersionRange
    public JsonResponseEntity<Map<String, String>> getKey(@RequestParam String id,
                                                          @RequestParam String channel,
                                                          @RequestParam(defaultValue = "sc") String app) {
        payService.savePayChannel(id, channel, app);
       /* JsonResponseEntity<Map<String, String>> response = new JsonResponseEntity<>();
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("key", selector.getByName(app).getKey());*/
        PayOrder payOrder = payService.findOne(id);

        PayOrderDTO payOrderDTO = new PayOrderDTO(payOrder);
        Hospital hospital = hospitalService.findByHospitalCode(payOrderDTO.getHospitalCode());
        JsonResponseEntity<Map<String, String>> response = new JsonResponseEntity<>();
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        String key = newLianPayUtilSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).getKey();
        builder.put("key", key);
        response.setData(builder.build());
        return response;
    }

    /**
     * 获取建行支付链接
     *
     * @param orderId    支付订单ID
     * @param hospitalId 医院ID
     * @param request
     * @return
     */
    @GetMapping("/ccb/url")
    @VersionRange
    public JsonResponseEntity<String> cbcPayUrl(String orderId, String hospitalId,
                                                @RequestParam(defaultValue = "sl") String app, HttpServletRequest request) {

        payService.savePayChannel(orderId, "ccbpay", app);

        JsonResponseEntity<String> responseEntity = new JsonResponseEntity<>();

        String ip = ServletRequestIPAddressUtil.parse(request);
        String payUrl = ccbPayService.generatePayUrl(orderId, hospitalId, ip);

        responseEntity.setData(payUrl);
        return responseEntity;
    }

    @GetMapping(path = "/refund")
    @VersionRange
    public String refund(@RequestParam String id) {
        PayOrder po = payService.findOne(id);
        payService.refundOrder(id, po.getAmount());
        return "success";
    }
}
