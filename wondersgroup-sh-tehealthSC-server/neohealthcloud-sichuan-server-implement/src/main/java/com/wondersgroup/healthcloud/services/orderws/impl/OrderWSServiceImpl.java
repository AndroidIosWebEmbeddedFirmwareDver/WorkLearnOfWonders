package com.wondersgroup.healthcloud.services.orderws.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wondersgroup.healthcloud.entity.request.orderws.OrderRequest;
import com.wondersgroup.healthcloud.entity.response.orderws.Response;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.jpa.entity.order.OrderInfo;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.repository.order.OrderInfoRepository;
import com.wondersgroup.healthcloud.jpa.repository.pay.PayOrderRepository;
import com.wondersgroup.healthcloud.services.hospital.DoctorService;
import com.wondersgroup.healthcloud.services.order.OrderInfoService;
import com.wondersgroup.healthcloud.services.orderws.OrderWSService;
import com.wondersgroup.healthcloud.services.pay.PayService;
import com.wondersgroup.healthcloud.utils.sms.SMS;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaozhenxing on 2016/11/7.
 */
@Service("orderWSService")
public class OrderWSServiceImpl implements OrderWSService {

    private static Logger logger = LoggerFactory.getLogger("EX");
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private SMS sms;

    @Autowired
    private SMS smssl;

    @Override
    public Response reportOrderStatus(OrderRequest orderRequest) {
        Response orderResp = new Response();
        Response.MessageInfo messageInfo = new Response.MessageInfo();

        StringBuffer errMsg = new StringBuffer();
        List<OrderRequest.WorkInfo> workInfos = orderRequest.list;
        if (null == workInfos || workInfos.isEmpty()) {
            messageInfo.setCode("-1");
            messageInfo.setDesc("调用接口参数不能为空！");
            orderResp.setMessageInfo(messageInfo);
            return orderResp;
        }
        for (OrderRequest.WorkInfo workInfo : workInfos) {
            OrderInfo orderInfo = orderInfoRepository.findByScOrderIdAndHosCode(workInfo.getOrderId(), workInfo.getHosOrgCode());
            if (null == orderInfo) {
                errMsg.append("该订单不存在：" + workInfo.getOrderId());
                continue;
            }
            if (orderInfo.getState().equals(workInfo.getOrderStatus())) {
                errMsg.append("请勿提交重复数据：" + workInfo.getOrderId());
                continue;
            }
            PayOrder payOrder = payOrderRepository.getBySubjectId(orderInfo.getId());
            if ("3".equals(workInfo.getOrderStatus())) {//已退号
                String message = null;
                // 若预约订单为已支付,则需调用退款接口
                if ("2".equals(orderInfo.getState()) && PayOrder.Status.SUCCESS.equals(payOrder.getStatus())) {//已预约已支付,需退款
                    BigDecimal amount = new BigDecimal(orderInfo.getCost());//费用单位:分
                    try {
                        if (payOrder != null) {
                            payOrder = payService.refundOrder(payOrder.getId(), amount.multiply(new BigDecimal(100)).longValue());
                            if ("510122".equals(orderInfo.getSource())) {
                                message = String.format(String.format("退款成功！您在[健康双流]的订单%s，订单退款成功！" +
                                                "所退款项将于一个工作日内退还至您支付时所使用的账户，请您前往支付账户查看退款。" +
                                                "您也可以前往[健康双流]查看订单退款详情。如有疑问，请咨询客服400-900-9957。",
                                        orderInfo.getShowOrderId()));
                                smssl.send(orderInfo.getUserPhone(), message);
                            } else {
                                message = String.format(String.format("退款成功！您在[微健康]的订单%s，订单退款成功！" +
                                                "所退款项将于一个工作日内退还至您支付时所使用的账户，请您前往支付账户查看退款。" +
                                                "您也可以前往[微健康]查看订单退款详情。如有疑问，请咨询客服400-900-9957。",
                                        orderInfo.getShowOrderId()));
                                sms.send(orderInfo.getUserPhone(), message);
                            }
                        }
                    } catch (Exception ex) {
                        if ("510122".equals(orderInfo.getSource())) {// 健康双流
                            message = String.format(String.format("退款失败！您在[健康双流]的订单%s，订单退款失败！" +
                                            "为保障您的退款，请您前往[健康双流]查看订单详情，如有疑问，请咨询客服400-900-9957。",
                                    orderInfo.getShowOrderId()));
                            smssl.send(orderInfo.getUserPhone(), message);
                        } else {// 微健康
                            message = String.format(String.format("退款失败！您在[微健康]的订单%s，订单退款失败！" +
                                            "为保障您的退款，请您前往[微健康]查看订单详情，如有疑问，请咨询客服400-900-9957。",
                                    orderInfo.getShowOrderId()));
                            sms.send(orderInfo.getUserPhone(), message);
                        }
                    }
                } else if ("1".equals(orderInfo.getState())) {// 已预约未支付，修改预约单为只取消，订单为已完成
                    if (payOrder != null) {
                        payOrder.setStatus(PayOrder.Status.SUCCESS);
                    }
                }

                // 更新本地表预约订单状态
                orderInfo.setUpdateTime(new Date());
                orderInfo.setState("3");
                orderInfoService.saveAndUpdate(orderInfo);

                // 调用短信接口通知用户取消预约成功
                if ("510122".equals(orderInfo.getSource())) {// 健康双流
                    message = String.format(String.format("取消预约挂号成功！您在[健康双流]的预约挂号订单%s，" +
                                    "%s，坐诊医生%s，已经取消成功！您可前往[健康双流]查看订单详情。" +
                                    "如有疑问，请咨询客服400-900-9957。感谢您使用[健康双流]！",
                            orderInfo.getShowOrderId(),
                            orderInfo.getDate() + orderInfo.getTimeRnge() + orderInfo.getHosName() + orderInfo.getDeptName(),
                            orderInfo.getDoctName()));
                    smssl.send(orderInfo.getUserPhone(), message);
                } else {
                    message = String.format(String.format("取消预约挂号成功！您在[微健康]的预约挂号订单%s，" +
                                    "%s，坐诊医生%s，已经取消成功！您可前往[微健康]查看订单详情。" +
                                    "如有疑问，请咨询客服400-900-9957。感谢您使用[微健康]！",
                            orderInfo.getShowOrderId(),
                            orderInfo.getDate() + orderInfo.getTimeRnge() + orderInfo.getHosName() + orderInfo.getDeptName(),
                            orderInfo.getDoctName()));
                    sms.send(orderInfo.getUserPhone(), message);
                }
            }
            if ("4".equals(workInfo.getOrderStatus())) {// 履约
                // 履约后增加接诊量
                doctorService.updateOrderCount(orderInfo.getHosCode(), orderInfo.getDeptCode(), orderInfo.getDoctCode());

                orderInfo.setUpdateTime(new Date());
                orderInfo.setState("4");
                orderInfoRepository.save(orderInfo);
            }
            if ("6".equals(workInfo.getOrderStatus())) {// 爽约
                orderInfo.setUpdateTime(new Date());
                orderInfo.setState("6");
                orderInfoRepository.save(orderInfo);
            }

            try {
                if (payOrder != null) {
                    payOrder.setUpdateTime(new Date());
                    if (StringUtils.isNotEmpty(payOrder.getBusiness())) {
                        ObjectMapper ob = new ObjectMapper();
                        JsonNode jsonNode = ob.readTree(payOrder.getBusiness());
                        if (jsonNode != null && jsonNode.get("state") != null) {
                            ObjectNode on = ((ObjectNode) jsonNode).put("state", workInfo.getOrderStatus());
                            payOrder.setBusiness(on.toString());
                        }
                    }
                    payOrderRepository.save(payOrder);
                }
            } catch (IOException e) {
                logger.error(Exceptions.getStackTraceAsString(e));
            }
        }

        if (errMsg.length() > 0) {
            messageInfo.setCode("-1");
            messageInfo.setDesc(errMsg.toString());
            orderResp.setMessageInfo(messageInfo);
        } else {
            messageInfo.setCode("0");
            messageInfo.setDesc("用户履约成功!");
            orderResp.setMessageInfo(messageInfo);
        }
        return orderResp;
    }
}
