package com.wondersgroup.healthcloud.services.pay;

import java.util.Date;

import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
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
 * <p/>
 * Created by zhangzhixiu on 06/11/2016.
 */
@Transactional(readOnly = true)
@Component
public class PaySchedule {

    private static final Logger logger = LoggerFactory.getLogger(PaySchedule.class);

    @Autowired
    private PayService payService;

    @Autowired
    private NewLianPayUtilSelector newSelector;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HttpRequestExecutorManager manager;

    @Value("${job.client.url}")
    private String url;

    /**
     * 为订单设定一个定时器, 在一定时间后检查订单状态,
     * 目前只支持支付成功和退款成功, 通过lts最终触发下面的方法
     *
     * @param triggerTime 触发时间
     * @param orderId     订单id
     * @param checkStatus 检查状态
     */
    @Transactional
    public Boolean setTimer(Date triggerTime, String orderId, PayOrder.Status checkStatus) {
        if (PayOrder.Status.SUCCESS.equals(checkStatus)) {
            PayOrder payOrder = payService.findOne(orderId);
            payOrder.setDueTime(triggerTime);
            payService.update(payOrder);
        }
        logger.info("call /pay/schedule/timer " + orderId);
        Request request = new RequestBuilder().post().url(url + "/pay/schedule/timer").body(String.format("{\"id\":\"%s\",\"status\":\"%s\",\"time\":\"%d\"}", orderId, checkStatus.toString(),
                triggerTime.getTime())).build();
        StringResponseWrapper wrapper = (StringResponseWrapper) manager.newCall(request).run().as(StringResponseWrapper.class);
        return "{\"code\":0}".equals(wrapper.convertBody());
    }

    /**
     * 定时任务触发的逻辑,
     * 当订单当前状态与预期状态相符时, 表示业务操作已经进行过, 则不需要执行操作,
     * 若不相符, 则通过接口直接访问链支付, 得到订单状态, 若支付成功而订单没有执行业务回调, 则通过该方法执行
     *
     * @param orderId     订单id
     * @param checkStatus 检查状态
     */
    @Transactional
    public void trigger(String orderId, PayOrder.Status checkStatus) {
        PayOrder payOrder = payService.findOne(orderId);
        PayOrderDTO payOrderDTO = hospitalService.getLianPayParam(payOrder);
        Hospital hospital = hospitalService.findByHospitalCode(payOrderDTO.getHospitalCode());
        String channel = payOrder.getChannel();
        if (checkStatus.equals(payOrder.getStatus())) {//订单当前状态与预期状态相符时, 表示业务操作已经进行过, 则不需要执行操作

        } else {
            if (channel == null || "ccbpay".equals(channel)) {
                payService.expireCallback(orderId);
                return;
            }
            if (checkStatus.equals(PayOrder.Status.SUCCESS)) {
                NewLianPayUtil.OrderInfo.Data response = newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).getOrderPayInfo(payOrder.getChannel(), orderId, false);
                if (response != null && "1".equals(response.status)) {//链支付支付成功
                    payService.paySuccessCallback(orderId, Long.valueOf(response.amount));
                } else {//过期
                    payService.expireCallback(orderId);
                }
            } else if (checkStatus.equals(PayOrder.Status.REFUNDSUCCESS)) {//todo

                NewLianPayUtil.OrderInfo.Data response = newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).getOrderPayInfo(payOrder.getChannel(), orderId, true);
                if (response != null && "1".equals(response.status)) {//链支付退款成功
                    payService.refundSuccessCallback(orderId, payOrder.getAmount());
                }

            }
        }
    }
}
