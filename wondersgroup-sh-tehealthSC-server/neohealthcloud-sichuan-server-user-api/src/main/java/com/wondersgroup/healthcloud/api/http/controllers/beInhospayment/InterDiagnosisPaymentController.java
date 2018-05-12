package com.wondersgroup.healthcloud.api.http.controllers.beInhospayment;

import com.wondersgroup.healthcloud.api.utils.MeDateUtil;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.entity.po.ClinicOrderGenerateRequest;
import com.wondersgroup.healthcloud.entity.po.Order;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.beinhospital.InterDiagnosisPaymentService;
import com.wondersgroup.healthcloud.services.beinhospital.PayOrderServiceManager;
import com.wondersgroup.healthcloud.services.beinhospital.dto.MyOrder;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.pay.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by nick on 2016/11/9.
 *
 * @author nick
 */
@RestController
@RequestMapping("/api/interDiaPayment")
public class InterDiagnosisPaymentController {
    //TODO;2018年01月30日15:05:50
    private static final Logger logger = LoggerFactory.getLogger(InterDiagnosisPaymentController.class);

    @Autowired
    private InterDiagnosisPaymentService interDiagnosisPaymentService;

    @Autowired
    private PayService payService;

    @Autowired
    private HospitalService hospitalService;


    @Autowired
    private NewLianPayUtilSelector newSelector;

    private static Integer pageSize = 10;

    /**
     * 获取当前用户代缴费的记录
     *
     * @param hospitalCode
     * @param uid
     * @return
     */
    @RequestMapping(value = "/unPayRecord", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<Order> getUnPaidRecord(@RequestParam String hospitalCode,
                                                         @RequestParam String uid) throws IOException {
        JsonListResponseEntity<Order> responseEntity = new JsonListResponseEntity<>();
        List<Order> record = interDiagnosisPaymentService.getCurrentUnPayRecord(hospitalCode, uid, getCityCode(hospitalCode));
        responseEntity.setContent(record);
        return responseEntity;
    }


    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity<Order> generateOrder(@RequestBody ClinicOrderGenerateRequest request) throws IOException {

        JsonResponseEntity<Order> responseEntity = new JsonResponseEntity<>();
        String cityCode = hospitalService.findByHospitalCode(request.getHospitalCode()).getCityCode();
        Hospital hospital = hospitalService.findByHospitalCode(request.getHospitalCode());

        if (hospital.getAppid() == null || hospital.getAppSecret() == null) {
            responseEntity.setCode(1000);
            responseEntity.setMsg("未开通商户号，无法生产诊间支付订单");
            return responseEntity;
        }

        Order order = interDiagnosisPaymentService.generateClinicOrder(request, cityCode);
        responseEntity.setData(order);
        return responseEntity;
    }

    /**
     * 获取用户预约订单
     *
     * @param uid
     * @param flag
     * @return
     */
    @RequestMapping(value = "/appoint", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<Order> getAppointmentOrder(@RequestParam String uid,
                                                             @RequestParam(required = false) String flag) {
        JsonListResponseEntity<Order> responseEntity = new JsonListResponseEntity<>();
        int start = 0;
        if (!StringUtils.isEmpty(flag)) {
            start = Integer.valueOf(flag);
        }
        payService.hospitalService = hospitalService;
        PayOrderServiceManager manager = new PayOrderServiceManager(start, pageSize, "APPOINTMENT", payService);
        MyOrder myOrder = manager.getMyOrders(uid);
        responseEntity.setContent(myOrder.getOrders(), myOrder.isHasMore(), "", myOrder.getNextPage());
        return responseEntity;
    }

    /**
     * TODO;2018年01月30日12:59:45，之前的逻辑太垃圾了，1.新增每次查询订单主动去链支付匹配订单支付状态的逻辑，2.优化查询性能
     *
     * @param uid
     * @param status
     * @param flag
     * @return
     */
    @RequestMapping(value = "/orders/withCheckState", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<Order>
    getOrdersWithCheckState(@RequestParam String uid, @RequestParam String status, @RequestParam(required = false) String flag) {
        JsonListResponseEntity<Order> responseEntity = new JsonListResponseEntity<>();
        int start = 0;
        if (!StringUtils.isEmpty(flag))
            start = Integer.valueOf(flag);
        PayOrder.Status orderStatus = null;
        if (!"ALL".equalsIgnoreCase(status)) {
            orderStatus = Enum.valueOf(PayOrder.Status.class, status);
        }
        payService.hospitalService = hospitalService;
        PayOrderServiceManager manager = new PayOrderServiceManager(start, pageSize, orderStatus, payService);
        MyOrder myOrder = manager.getMyOrders(uid);
        //TODO;先检测并处理
        toCheckStateAndUpdateSelef(myOrder.getOrders());
        //TODO;再查询并返回
        myOrder = manager.getMyOrders(uid);
        responseEntity.setContent(myOrder.getOrders(), myOrder.isHasMore(), "", myOrder.getNextPage());
        return responseEntity;
    }


    /**
     * TODO;2018年01月30日13:05:12，1.新增每次查询订单主动去链支付匹配订单支付状态的逻辑，
     *
     * @param orders
     * @return
     */
    private List<Order> toCheckStateAndUpdateSelef(List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            //每一个订单，都去链支付查询一遍状态，如果链支付返回状态与本地状态不符，则以链支付为准
            for (Order order : orders) {
                PayOrder.Status checkedStatus = trigger(order.getPay_order().getId(), order.getPay_order().getStatus());
                order.setPayStatus(checkedStatus.toString());
                order.getPay_order().setStatus(checkedStatus);
            }
        }
        return orders;
    }

    @Transactional
    public PayOrder.Status trigger(String orderId, PayOrder.Status checkStatus) {
        logger.info("fetch--> orderId --> " + orderId + "      checkStatus  --> " + checkStatus.toString());
        PayOrder payOrder = payService.findOne(orderId);
        PayOrderDTO payOrderDTO = hospitalService.getLianPayParam(payOrder);
        Hospital hospital = hospitalService.findByHospitalCode(payOrderDTO.getHospitalCode());
        String channel = payOrder.getChannel();
        try {
            if (channel == null || "ccbpay".equals(channel)) {
                payService.expireCallback(orderId);
                return checkStatus;
            }
            if (checkStatus.equals(PayOrder.Status.NOTPAY)) {
                NewLianPayUtil.OrderInfo.Data response = newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).getOrderPayInfo(payOrder.getChannel(), orderId, false);
                logger.info("fetch--> response --> " + (response != null ? response : "response==null"));
                if (response != null && "1".equals(response.status)) {//链支付支付成功
                    payService.paySuccessCallback(orderId, Long.valueOf(response.amount));
                    return PayOrder.Status.SUCCESS;
                } else {
                    //TODO;这里做一个15分钟超时的判断，超过则设置超时，未超过则返回checkStatus
                    if (MeDateUtil.diffSecond(payOrder.getCreateTime(), new Date()) > 15 * 60) {
                        payService.expireCallback(orderId);
                        return PayOrder.Status.EXPIRED;
                    } else {
                        return checkStatus;
                    }
                }
            } else if (checkStatus.equals(PayOrder.Status.SUCCESS)) {
                NewLianPayUtil.OrderInfo.Data response = newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).getOrderPayInfo(payOrder.getChannel(), orderId, false);
                logger.info("fetch--> response --> " + (response != null ? response : "response==null"));
                if (response != null && "1".equals(response.status)) {//链支付支付成功
                    payService.paySuccessCallback(orderId, Long.valueOf(response.amount));
                    return PayOrder.Status.SUCCESS;
                } else {
                    //TODO;查询下是否已经退款了
                    response = newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).getOrderPayInfo(payOrder.getChannel(), orderId, true);
                    logger.info("fetch--> response --> " + (response != null ? response : "response==null"));
                    if (response != null && "1".equals(response.status)) {//链支付退款成功
                        payService.refundSuccessCallback(orderId, payOrder.getAmount());
                        return PayOrder.Status.REFUNDSUCCESS;
                    } else {
                        return PayOrder.Status.NOTPAY;
                    }
                }
            } else if (checkStatus.equals(PayOrder.Status.REFUND) || checkStatus.equals(PayOrder.Status.REFUNDSUCCESS)) {
                NewLianPayUtil.OrderInfo.Data response = newSelector.getNewLianPayUtil(hospital.getAppid(), hospital.getAppSecret()).getOrderPayInfo(payOrder.getChannel(), orderId, true);
                logger.info("fetch--> response --> " + (response != null ? response : "response==null"));
                if (response != null && "1".equals(response.status)) {//链支付退款成功
                    payService.refundSuccessCallback(orderId, payOrder.getAmount());
                    return PayOrder.Status.REFUNDSUCCESS;
                } else {
                    return PayOrder.Status.REFUND;
                }
            } else {
                return checkStatus;
            }
        } catch (Exception e) {
            logger.error("fetch--> Exception --> " + e != null && e.getMessage() != null ? e.getMessage() : "");
            return checkStatus;
        }

    }

    /**
     * 之前代码写的太垃圾 重构了一下
     *
     * @param uid
     * @param status
     * @param flag
     * @return
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<Order> getOrders(@RequestParam String uid,
                                                   @RequestParam String status,
                                                   @RequestParam(required = false) String flag) {
        JsonListResponseEntity<Order> responseEntity = new JsonListResponseEntity<>();
        int start = 0;
        if (!StringUtils.isEmpty(flag))
            start = Integer.valueOf(flag);
        PayOrder.Status orderStatus = null;
        if (!"ALL".equalsIgnoreCase(status)) {
            orderStatus = Enum.valueOf(PayOrder.Status.class, status);
        }
        payService.hospitalService = hospitalService;
        PayOrderServiceManager manager = new PayOrderServiceManager(start, pageSize, orderStatus, payService);
        MyOrder myOrder = manager.getMyOrders(uid);
        responseEntity.setContent(myOrder.getOrders(), myOrder.isHasMore(), "", myOrder.getNextPage());
        return responseEntity;
    }

    /**
     * 根据医院 查询所属区域
     */
    private String getCityCode(String hospitalCode) {
        Hospital hospital = hospitalService.findByHospitalCode(hospitalCode);
        if (hospital != null && StringUtils.isNotEmpty(hospital.getCityCode())) {
            return hospital.getCityCode();
        }
        return "510000000000";
    }
}
