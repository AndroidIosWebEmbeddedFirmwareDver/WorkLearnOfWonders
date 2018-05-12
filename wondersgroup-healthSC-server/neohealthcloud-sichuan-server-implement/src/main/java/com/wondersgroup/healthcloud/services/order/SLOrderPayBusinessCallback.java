package com.wondersgroup.healthcloud.services.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.entity.request.OrderCancelInfoRequest;
import com.wondersgroup.healthcloud.entity.request.OrderPayCompletedRequest;
import com.wondersgroup.healthcloud.entity.response.OrderPayCompletedResponse;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.jpa.entity.message.PayMessage;
import com.wondersgroup.healthcloud.jpa.entity.order.OrderInfo;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.repository.pay.PayOrderRepository;
import com.wondersgroup.healthcloud.services.message.PayMessageService;
import com.wondersgroup.healthcloud.services.pay.PayBusinessCallback;
import com.wondersgroup.healthcloud.services.pay.PayService;
import com.wondersgroup.healthcloud.utils.sms.SMS;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhaozhenxing on 2016/11/9.
 */
@Service("APPOINTMENT_SL")
public class SLOrderPayBusinessCallback implements PayBusinessCallback {

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private PayMessageService payMessageService;

    @Autowired
    private SMS smssl;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private SLOrderService slOrderService;

    @Autowired
    private PayService payService;

    private final Logger logger = LoggerFactory.getLogger("exlog");

    @Override
    public Boolean onPaySuccess(String subjectId) {
        PayOrder payOrder = payOrderRepository.getBySubjectId(subjectId);
        if (payOrder != null) {
            OrderInfo orderInfo = orderInfoService.detail(payOrder.getSubjectId());
            if (orderInfo != null) {
                // 更新本地订单为已支付状态
                orderInfo.setState("2");

                OrderPayCompletedRequest.OrderInfo info = new OrderPayCompletedRequest.OrderInfo();
                info.setScheduleId(orderInfo.getScheduleId());
                info.setNumSourceId(orderInfo.getNumSource());
                info.setOrderId(orderInfo.getScOrderId());
                info.setFrontProviderOrderId(orderInfo.getId());
                info.setPayMode("4");
                info.setPayType(payOrder.getChannel());
                info.setPayTradeNo(payOrder.getId());
                info.setPayAmount(String.valueOf(payOrder.getAmount() / 100));
                OrderPayCompletedResponse response = slOrderService.orderPayCompleted(info);
                if (response != null && response.getMessageHeader() != null && "0".equals(response.getMessageHeader().getCode())) {
                    OrderPayCompletedResponse.Result result = response.getResult();
                    if (result != null) {
                        orderInfo.setTakePassword(result.getTakePassword());
                        orderInfo.setVisitNo(result.getVisitNo());
                    }
                } else {
                    payService.refundOrder(payOrder.getId(), payOrder.getAmount());
                    return true;
                }

                orderInfo = orderInfoService.saveAndUpdate(orderInfo);

                try {
                    if (payOrder != null) {
                        payOrder.setUpdateTime(new Date());
                        if (StringUtils.isNotEmpty(payOrder.getBusiness())) {
                            ObjectMapper ob = new ObjectMapper();
                            JsonNode jsonNode = ob.readTree(payOrder.getBusiness());
                            if (jsonNode != null && jsonNode.get("state") != null) {
                                ObjectNode on = ((ObjectNode) jsonNode).put("state", "2");
                                payOrder.setBusiness(on.toString());
                            }
                        }
                        payOrder = payOrderRepository.save(payOrder);
                    }
                } catch (IOException e) {
                    logger.error(Exceptions.getStackTraceAsString(e));
                }

                String time = orderInfo.getDate();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = sdf.parse(time);
                    sdf.applyPattern("yyyy-MM-dd");
                    time = sdf.format(date) + " " + DateUtils.getWeekOfDate(date) + " " + DateUtils.getAPM(date);
                } catch (Exception ex) {
                    logger.error(Exceptions.getStackTraceAsString(ex));
                }

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ParsePosition pos = new ParsePosition(0);
                Date date = formatter.parse(orderInfo.getDate(), pos);
                String lastCancelDate = formatter.format(new DateTime(date).withTimeAtStartOfDay().toDate());

                String message = String.format("预约挂号成功！预约人：%s，预约号：%s，" +
                                "就诊时间：%s，就诊地点：%s，预约坐诊医生：%s（%s）。" +
                                "为保障您按时就诊，请您在预约时间内携带有效二代身份证或其他医疗卡前来医院就诊。" +
                                "挂号情况以医院当日信息为准，取消预约请在%s前。" +
                                "如有疑问，请咨询客服400-900-9957。",
                        orderInfo.getPatientName(),
                        orderInfo.getShowOrderId(),
                        time,
                        orderInfo.getHosName() + orderInfo.getDeptName(),
                        orderInfo.getDoctName(),
                        orderInfo.getOutpatientType(),
                        lastCancelDate);
                smssl.send(orderInfo.getUserPhone(), message);

                message = String.format("支付成功！您在[健康双流]的订单%s，订单支付成功！" +
                                "请您前往[健康双流]查看订单详情。如有疑问，请咨询客服400-900-9957。",
                        payOrder.getShowOrderId());
                smssl.send(orderInfo.getUserPhone(), message);

                try {
                    PayMessage payMessage = new PayMessage();
                    Date orderTime = null;
                    if (StringUtils.isNotEmpty(orderInfo.getDate())) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        if (orderInfo.getDate().contains(":")) {
                            format.applyPattern("yyyy-MM-dd HH:mm:ss");
                        }
                        orderTime = format.parse(orderInfo.getDate());
                    }

                    payMessage.setId(IdGen.uuid());
                    payMessage.setCreateDate(new Date());
                    payMessage.setType(0);
                    payMessage.setRegisterId(orderInfo.getUid());
                    payMessage.setHospitalName(orderInfo == null ? null : orderInfo.getHosName());
                    payMessage.setOrderId(payOrder.getShowOrderId());
                    payMessage.setPayStatus(new Integer(1));
                    payMessage.setOrderTime(orderTime);
                    payMessage.setPayType(new Integer(2));
                    payMessage.setPatientName(orderInfo.getPatientName());
                    payMessage.setDepartment(orderInfo.getDeptName());
                    payMessage.setDoctorName(orderInfo.getDoctName());
                    payMessage.setPrice(Double.valueOf(orderInfo.getCost()));
                    if (payOrder != null && StringUtils.isNotEmpty(payOrder.getBusiness())) {
                        ObjectMapper ob = new ObjectMapper();
                        JsonNode jsonNode = null;
                        try {
                            jsonNode = ob.readTree(payOrder.getBusiness());
                        } catch (IOException e) {
                            logger.error(Exceptions.getStackTraceAsString(e));
                        }
                        if (jsonNode != null) {
                            if (jsonNode.get("outDoctorLevel") != null) {
                                payMessage.setClinicType(jsonNode.get("outDoctorLevel").asText());
                            }
                        }
                    }
                    payMessageService.save(payMessage);
                } catch (ParseException e) {
                    logger.error(Exceptions.getStackTraceAsString(e));
                }
            }
        }
        return true;
    }

    @Override
    public Boolean onRefundSuccess(String subjectId, Long amount) {
        OrderInfo orderInfo = orderInfoService.detail(subjectId);
        PayOrder payOrder = payOrderRepository.getBySubjectId(subjectId);

        String time = orderInfo.getDate();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            sdf.applyPattern("yyyy-MM-dd");
            time = sdf.format(date) + " " + DateUtils.getWeekOfDate(date) + " " + DateUtils.getAPM(date);
        } catch (Exception ex) {
            logger.error(Exceptions.getStackTraceAsString(ex));
        }
        // 调用短信接口通知用户取消预约成功
        String message = String.format(String.format("取消预约挂号成功！您在[健康双流]的预约挂号订单%s，" +
                        "%s，坐诊医生%s，已经取消成功！您可前往[健康双流]查看订单详情。" +
                        "如有疑问，请咨询客服400-900-9957。感谢您使用[健康双流]！",
                orderInfo.getShowOrderId(),
                time + orderInfo.getHosName() + orderInfo.getDeptName(),
                orderInfo.getDoctName()));
        smssl.send(orderInfo.getUserPhone(), message);

        try {
            logger.info("退款成功,修改本地订单状态,订单号:" + orderInfo.getId());
            orderInfo.setState("3");
            orderInfo.setUpdateTime(new Date());
            orderInfoService.saveAndUpdate(orderInfo);
            if (payOrder != null) {
                payOrder.setUpdateTime(new Date());
                if (StringUtils.isNotEmpty(payOrder.getBusiness())) {
                    ObjectMapper ob = new ObjectMapper();
                    JsonNode jsonNode = ob.readTree(payOrder.getBusiness());
                    if (jsonNode != null && jsonNode.get("state") != null) {
                        ObjectNode on = ((ObjectNode) jsonNode).put("state", "3");
                        payOrder.setBusiness(on.toString());
                    }
                }
                payOrderRepository.save(payOrder);
            }
        } catch (IOException e) {
            logger.error(Exceptions.getStackTraceAsString(e));
        }
        // 发送退款信息
        message = String.format("退款成功！您在[健康双流]的订单%s，订单退款成功！" +
                        "所退款项将于一个工作日内退还至您支付时所使用的账户，请您前往支付账户查看退款。" +
                        "您也可以前往[健康双流]查看订单退款详情。如有疑问，请咨询客服400-900-9957。",
                payOrder.getShowOrderId());
        smssl.send(orderInfo.getUserPhone(), message);
        return true;
    }

    @Override
    public Boolean onExpire(String subjectId) {
        logger.info("start process expire ws_order " + subjectId);
        OrderInfo orderInfo = orderInfoService.detail(subjectId);

        OrderCancelInfoRequest.OrderInfo cancelInfo = new OrderCancelInfoRequest.OrderInfo();
        cancelInfo.setOrderId(orderInfo.getScOrderId());
        cancelInfo.setHosOrgCode(orderInfo.getHosCode());
        cancelInfo.setNumSourceId(orderInfo.getNumSource());
        cancelInfo.setPlatformUserId(orderInfo.getPlatformUserId());
        cancelInfo.setTakePassword(orderInfo.getTakePassword());
        cancelInfo.setCancelObj("2");// 退号发起对象 1:患者(orderInfo.get());2:服务商
        cancelInfo.setCancelReason("0");// 退号原因 0:其他(orderInfo.get());1:患者主动退号
        cancelInfo.setCancelDesc("用户未在规定时限内支付预约挂号订单");// 备注 只有退号原因为其他时才有用
        slOrderService.cancelOrder(cancelInfo);

        PayOrder payOrder = payOrderRepository.getBySubjectId(subjectId);
        logger.info("start process expire pay_order " + subjectId);
        if (payOrder != null) {
            payOrder.setStatus(PayOrder.Status.EXPIRED);
            payOrder.setUpdateTime(new Date());
            if (StringUtils.isNotEmpty(payOrder.getBusiness())) {
                ObjectMapper ob = new ObjectMapper();
                JsonNode jsonNode = null;
                try {
                    jsonNode = ob.readTree(payOrder.getBusiness());
                } catch (IOException e) {
                    logger.error("");
                }
                if (jsonNode != null && jsonNode.get("state") != null) {
                    ObjectNode on = ((ObjectNode) jsonNode).put("state", "3");
                    payOrder.setBusiness(on.toString());
                }
            }
            payOrderRepository.save(payOrder);
        }

        logger.info("start process expire order " + subjectId);
        // 更新本地订单为已支付状态
        orderInfo.setState("3");
        orderInfo.setUpdateTime(new Date());
        orderInfoService.saveAndUpdate(orderInfo);
        logger.info("save order " + orderInfo.getId());
        String time = orderInfo.getDate();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            sdf.applyPattern("yyyy-MM-dd");
            time = sdf.format(date) + " " + DateUtils.getWeekOfDate(date) + " " + DateUtils.getAPM(date);
        } catch (Exception ex) {
            logger.error(Exceptions.getStackTraceAsString(ex));
        }
        String message = String.format("支付超时！您在[健康双流]的订单%s，" +
                        "订单详情：[预约挂号]%s%s，坐诊医生%s，订单支付超时！" +
                        "如仍需就诊，请重新预约。如有疑问，请咨询客服400-900-9957。",
                payOrder.getShowOrderId(),
                time,
                orderInfo.getHosName() + orderInfo.getDeptName(),
                orderInfo.getDoctName());
        smssl.send(orderInfo.getUserPhone(), message);
        return true;
    }
}
