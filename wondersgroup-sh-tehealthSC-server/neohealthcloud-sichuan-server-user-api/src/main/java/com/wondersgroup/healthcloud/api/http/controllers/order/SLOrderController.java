package com.wondersgroup.healthcloud.api.http.controllers.order;

import com.wondersgroup.healthcloud.api.http.dto.order.CancelOrderDTO;
import com.wondersgroup.healthcloud.api.http.dto.order.OrderDetailDTO;
import com.wondersgroup.healthcloud.api.http.dto.order.SubmitOrderDTO;
import com.wondersgroup.healthcloud.api.utils.CommonUtils;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.entity.po.Order;
import com.wondersgroup.healthcloud.entity.request.GetOrderDetailInfoRequest;
import com.wondersgroup.healthcloud.entity.request.OrderCancelInfoRequest;
import com.wondersgroup.healthcloud.entity.response.GetOrderDetailInfoResponse;
import com.wondersgroup.healthcloud.entity.response.OrderCancelInfoResponse;
import com.wondersgroup.healthcloud.entity.response.SubmitOrderByUserInfoResponse;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.jpa.entity.config.AppConfig;
import com.wondersgroup.healthcloud.jpa.entity.order.OrderInfo;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.entity.pay.SubjectType;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.repository.order.OrderInfoRepository;
import com.wondersgroup.healthcloud.jpa.repository.pay.PayOrderRepository;
import com.wondersgroup.healthcloud.jpa.repository.user.AccountRepository;
import com.wondersgroup.healthcloud.services.config.AppConfigService;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.order.OrderInfoService;
import com.wondersgroup.healthcloud.services.order.SLOrderService;
import com.wondersgroup.healthcloud.services.pay.PaySchedule;
import com.wondersgroup.healthcloud.services.pay.PayService;
import com.wondersgroup.healthcloud.utils.sms.SMS;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaozhenxing on 2016/11/2.
 */
@RestController
@RequestMapping("/api/sl/order")
public class SLOrderController {
    private static Logger logger = LoggerFactory.getLogger("exlog");

    @Value("${sichuan.webservice.callback.url}")
    private String scCBServiceUrl;

    @Autowired
    private SLOrderService slOrderService;// 用于调用区域平台WebService服务

    @Autowired
    private OrderInfoService orderInfoService;// 用于调用处理本地表业务逻辑

    @Autowired
    private PayService payService;

    @Autowired
    private PaySchedule paySchedule;

    @Autowired
    private SMS smssl;

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppConfigService appConfigService;

    /**
     * 获取预约订单列表
     * 调用本地服务
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity getOrderList(@RequestHeader(name = "city-code", defaultValue = "510122000000") String cityCode,
                                               @RequestParam(required = true) String uid,
                                               @RequestParam(required = false) String state,
                                               @RequestParam(name = "flag", required = false, defaultValue = "1") Integer pageNo) {
        int pageSize = 11;
        JsonListResponseEntity result = new JsonListResponseEntity();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUid(uid);
        if (StringUtils.isNotEmpty(state)) {
            state = "'" + state + "'";
        }
        orderInfo.setState(state);
        orderInfo.setCityCode(cityCode);
        List<Order> list = orderInfoService.list(orderInfo, pageNo, pageSize);
        Boolean hasMore = false;
        if (null != list && list.size() > 10) {
            list = list.subList(0, 10);
            hasMore = true;
        }
        result.setContent(list, hasMore, null, hasMore ? (pageNo + 1) + "" : pageNo + "");
        return result;
    }

    /**
     * 获取预约订单详情
     * 调用区域平台服务
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
    @VersionRange
    public JsonResponseEntity getOrderDetail(@RequestParam(required = true) String orderId) {
        JsonResponseEntity result = new JsonResponseEntity();
        OrderInfo info = orderInfoRepository.findOne(orderId);
        PayOrder payOrder = payOrderRepository.getBySubjectId(orderId);
        if (info != null) {
            GetOrderDetailInfoRequest.OrderInfo orderInfo = new GetOrderDetailInfoRequest.OrderInfo();
            orderInfo.setOrderId(info.getScOrderId());
            GetOrderDetailInfoResponse.Result wsRtn = slOrderService.getOrderDetail(orderInfo);
            if (wsRtn != null) {
                wsRtn.setOrderId(info.getId());
                OrderDetailDTO detailDTO = new OrderDetailDTO(wsRtn);
                detailDTO.setOrderTime(DateUtils.customAppTimeShowForDateStr(wsRtn.getOrderTime(), wsRtn.getTimeRange()));
                detailDTO.setIsEvaluated(info.getIsEvaluated());
                detailDTO.setOrderStatus(info.getState());
                detailDTO.setShowOrderId(info.getShowOrderId());
                detailDTO.setChannel(payOrder.getChannel());
                result.setData(detailDTO);
            } else {
                result.setCode(1000);
                result.setMsg("区域平台未查询到相关订单信息");
            }
        } else {
            result.setCode(1000);
            result.setMsg("订单信息不存在");
        }

        return result;
    }

    /**
     * 提交预约订单前  检测判断
     * 1. 根据用户是否预约过该医院， 没有则进行提示
     */
    @RequestMapping(value = "/beforeSubmit", method = RequestMethod.GET)
    @VersionRange
    public JsonResponseEntity beforeSubmit(@RequestParam String uid, @RequestParam String hosOrgCode) {
        JsonResponseEntity entity = new JsonResponseEntity();
        Map<String, Object> info = new HashMap<>();
        String orderId = orderInfoRepository.hasOrderInfoForUserInHospital(uid, hosOrgCode);
        int hasOrder = StringUtils.isEmpty(orderId) ? 0 : 1;
        info.put("hasOrder", hasOrder);
        entity.setData(info);
        return entity;
    }


    /**
     * 提交预约订单
     * 调用区域平台服务及本地服务
     *
     * @return
     */
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity submitOrder(@RequestHeader(name = "city-code", defaultValue = "510122000000") String cityCode,
                                          @RequestBody SubmitOrderDTO orderInfo) {
        JsonResponseEntity result = new JsonResponseEntity();

        if (orderInfo == null || StringUtils.isEmpty(orderInfo.getUid()) || StringUtils.isEmpty(orderInfo.getMediCardId())) {
            result.setCode(1000);
            result.setMsg("用户信息缺失");
            return result;
        } else {
            if (!orderInfo.getMediCardId().matches("^[a-z0-9A-Z]+$")) {
                result.setCode(1000);
                result.setMsg("请输入正确的诊疗卡卡号");
                return result;
            }
            Account account = accountRepository.findOne(orderInfo.getUid());
            if (account != null && account.verified()) {
                orderInfo.setUserName(account.getName());
                orderInfo.setUserCardId(account.getIdcard());
            } else {
                result.setCode(1000);
                result.setMsg("用户信息不存在或未实名认证");
                return result;
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date orderTime = formatter.parse(orderInfo.getOrderTime(), pos);
        DateTime orderDateTime = new DateTime(orderTime);
        DateTime beginTime = new DateTime(new Date()).plusDays(1).withTimeAtStartOfDay();
        DateTime endTime = new DateTime(new Date()).plusDays(8).withTimeAtStartOfDay();
        if (orderDateTime.isBefore(beginTime.getMillis()) || orderDateTime.isAfter(endTime.getMillis())) {
            result.setCode(1000);
            result.setMsg("预约日期超出可预约范围！");
            return result;
        }
        String lastCancelDate = formatter.format(new DateTime(orderTime).withTimeAtStartOfDay().toDate());

        // 验证预约挂号
        String msg = orderInfoService.validation(orderInfo.toOrderInfo());
        if (msg != null) {
            result.setCode(1000);
            result.setMsg(msg);
            return result;
        }

        Map<String, Object> map = new HashMap<>();
        orderInfo.setCallBackUrl(scCBServiceUrl);
        // 调用区域平台接口提交预约订单
        SubmitOrderByUserInfoResponse wsResp = slOrderService.submitOrder(orderInfo.toWSOrderInfo());
        if (wsResp == null) {
            result.setCode(1000);
            result.setMsg("调用区域平台接口异常!");
            return result;
        }
        // 区县接口调用成功后保存本地数据
        if (CommonUtils.isWSSuccess(wsResp)) {
            SubmitOrderByUserInfoResponse.Result wsResult = wsResp.result;
            if (wsResult != null) {
                formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String showOrderId = "yy" + formatter.format(new Date()) + ((int) (Math.random() * 9000 + 1000));
                OrderInfo info = orderInfo.toOrderInfo();
                info.setId(IdGen.uuid());
                info.setShowOrderId(showOrderId);
                info.setScOrderId(wsResult.getOrderId());
                info.setScheduleId(wsResult.getScheduleId());// 排班ID替换为区域平台返回的排班ID
                info.setPlatformUserId(wsResult.getPlatformUserId());
                info.setState("1");
                info.setCreateTime(new Date());
                info.setDelFlag("0");// 删除标志 0：不删除 1：已删除
                info.setCityCode(cityCode);
                info.setSource("510122");// 健康双流-行政区域编码-成都市-双流区
                info.setMediCardId(orderInfo.getMediCardId());
                int flag = 0;// 步骤标志
                try {
                    flag = 1;
                    info = orderInfoService.saveAndUpdate(info);
                    result.setCode(0);
                    result.setMsg("预约挂号成功！");
                    flag = 2;
                    // 本地订单生成后,生成支付订单
                    BigDecimal amount = new BigDecimal(orderInfo.getVisitCost());//费用单位:分
                    String tmpStr = generateOrderBusinessData(orderInfo);
                    PayOrder payOrder = payService.generatePayOrderSL(amount.multiply(new BigDecimal(100)).longValue(), SubjectType.APPOINTMENT_SL, info.getId().toString(), "健康双流预约挂号订单",
                            "健康双流预约挂号订单", "健康双流预约挂号订单", info.getUid(), tmpStr, showOrderId.replace("yy", "yp"), cityCode);
                    DateTime dateTime = new DateTime(new Date());
                    dateTime = dateTime.plusMinutes(15);
                    logger.error("paySchedule result " + paySchedule.setTimer(dateTime.toDate(), payOrder.getId(), PayOrder.Status.SUCCESS));
                    map.put("payOrderId", payOrder.getId());
                    map.put("orderId", info.getId());
                    map.put("showOrderId", payOrder.getShowOrderId());
                    flag = 3;
                } catch (Exception ex) {
                    // 保存本地表失败调用取消预约接口释放号源
                    logger.error("OrderController.submitOrder -->" + Exceptions.getStackTraceAsString(ex));
                    result.setCode(1000);
                    switch (flag) {
                        case 1:// 保存本地预约订单失败
                            result.setMsg("APP保存预约订单失败！");
                            break;
                        case 2:// 生成预约支付订单失败
                            result.setMsg("APP生成预约支付订单失败！");
                            info.setDelFlag("1");
                            info.setState("3");
                            orderInfoService.saveAndUpdate(info);
                            break;
                    }
                    OrderCancelInfoRequest.OrderInfo cancelOrder = new OrderCancelInfoRequest.OrderInfo();
                    cancelOrder.setHosOrgCode(orderInfo.getHosOrgCode());
                    cancelOrder.setCancelDesc("APP保存预约订单失败");
                    cancelOrder.setCancelObj("2");
                    cancelOrder.setCancelReason("0");
                    cancelOrder.setNumSourceId(wsResult.getNumSourceId());
                    cancelOrder.setOrderId(wsResult.getOrderId());
                    cancelOrder.setPlatformUserId(wsResult.getPlatformUserId());
                    cancelOrder.setTakePassword(wsResult.getTakePassword());
                    slOrderService.cancelOrder(cancelOrder);
                }
                if (flag == 3) {// 预约成功,发送短信提醒
                    try {
                        hospitalService.updateHospital(orderInfo.getHosOrgCode());
                    } catch (Exception ex) {
                        logger.error("更新医院/医生预约量失败 --> " + Exceptions.getStackTraceAsString(ex));
                    }

                    result.setData(map);
                }
            }
        } else {
            logger.error(wsResp.getMessageHeader().getDesc());
            result.setCode(1000);
            result.setMsg("调用区域平台预约接口失败!");
            try {
                AppConfig appConfig = appConfigService.findSingleAppConfigByKeyWord("app.common.webser.errmsg.switch");
                if (appConfig != null && "1".equals(appConfig.getData())) {// 预约挂号显示区域接口错误提示开关 0-显示统一错误信息,1-显示区域接口错误信息
                    result.setMsg(wsResp.getMessageHeader().getDesc());
                }
            } catch (Exception ex) {
                logger.error(Exceptions.getStackTraceAsString(ex));
            }
        }
        return result;
    }

    /**
     * 取消预约订单
     * 调用区域平台服务及本地服务
     *
     * @return
     */
    @RequestMapping(value = "/oldcancelOrder", method = RequestMethod.POST)
    @VersionRange


    public JsonResponseEntity cancelOrder(@RequestBody CancelOrderDTO info) {
        JsonResponseEntity result = new JsonResponseEntity();
        OrderInfo orderInfo = orderInfoService.detail(info.getOrderId());
        OrderCancelInfoResponse wsResponse = null;
        boolean callFailed = false;
        if (orderInfo != null) {
            String msg = orderInfoService.cancelValidation(orderInfo);
            if (StringUtils.isNotEmpty(msg)) {
                result.setCode(1000);
                result.setMsg(msg);
                return result;
            }
            //TODO;2018年03月07日10:46:31，判断订单状态为2则调用先医院退号再退款
            String message = null;
            // 调用区域平台接口取消预约订单
            OrderCancelInfoRequest.OrderInfo wsOrderInfo = info.toWSOrderInfo();
            wsOrderInfo.setOrderId(orderInfo.getScOrderId());
            wsOrderInfo.setHosOrgCode(orderInfo.getHosCode());
            wsOrderInfo.setNumSourceId(orderInfo.getNumSource());
            wsOrderInfo.setTakePassword(orderInfo.getTakePassword());
            wsOrderInfo.setPlatformUserId(orderInfo.getPlatformUserId());
            wsResponse = slOrderService.cancelOrder(wsOrderInfo);

            if (CommonUtils.isWSSuccess(wsResponse)) {
                callFailed = true;
                String orderState = orderInfo.getState();
                // 若预约订单为已支付,则需调用退款接口
                PayOrder payOrder = payOrderRepository.getBySubjectId(orderInfo.getId());
                if (payOrder != null && "2".equals(orderState) && PayOrder.Status.SUCCESS.equals(payOrder.getStatus())) {//已预约已支付,需退款
                    BigDecimal amount = new BigDecimal(orderInfo.getCost());//费用单位:分
                    try {
                        payOrder = payService.refundOrder(payOrder.getId(), amount.multiply(new BigDecimal(100)).longValue());
                        DateTime dateTime = new DateTime(new Date());
                        dateTime = dateTime.plusMinutes(15);
                        logger.error("paySchedule result " + paySchedule.setTimer(dateTime.toDate(), payOrder.getId(), PayOrder.Status.REFUNDSUCCESS));
                        // 更新本地表预约订单状态
                        orderInfo.setUpdateTime(new Date());
                        orderInfo.setState("3");
                        orderInfoService.saveAndUpdate(orderInfo);
                        result.setMsg("取消预约挂号成功！");
                        return result;
                    } catch (Exception ex) {

                        message = String.format(String.format("退款失败！您在[健康双流]的订单%s，订单退款失败！" +
                                        "为保障您的退款，请您前往[健康双流]查看订单详情，如有疑问，请咨询客服400-900-9957。",
                                payOrder.getShowOrderId()));
                        smssl.send(orderInfo.getUserPhone(), message);
                    }
                }
            }

        } else {
            result.setCode(1000);
            result.setMsg("订单信息不存在！");
            return result;
        }
        result.setCode(1000);
        result.setMsg("取消预约挂号失败！");
        if (!callFailed) {
            AppConfig appConfig = appConfigService.findSingleAppConfigByKeyWord("app.common.webser.errmsg.switch");
            if (appConfig != null && "1".equals(appConfig.getData())) {// 预约挂号显示区域接口错误提示开关 0-显示统一错误信息,1-显示区域接口错误信息
                result.setMsg(wsResponse != null ? wsResponse.getMessageHeader().getDesc() : "");
            }
        }
        return result;
    }

    /**
     * 取消预约订单
     * 调用区域平台服务及本地服务
     * TODO;2018年03月07日10:56:05，新接口
     *
     * @return
     */
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity newcancelOrder(@RequestBody CancelOrderDTO info) {
        JsonResponseEntity result = new JsonResponseEntity();
        OrderInfo orderInfo = orderInfoService.detail(info.getOrderId());
        OrderCancelInfoResponse wsResponse = null;
        boolean callFailed = false;
        if (orderInfo != null) {
            String msg = orderInfoService.cancelValidation(orderInfo);
            if (StringUtils.isNotEmpty(msg)) {
                result.setCode(1000);
                result.setMsg(msg);
                return result;
            }
            //TODO;2018年03月07日10:46:31，判断订单状态为2则调用先医院退号再退款
            String message = null;
            if (orderInfo.getState() != null && orderInfo.getState().equals("2")) {

                // 调用区域平台接口取消预约订单
                OrderCancelInfoRequest.OrderInfo wsOrderInfo = info.toWSOrderInfo();
                wsOrderInfo.setOrderId(orderInfo.getScOrderId());
                wsOrderInfo.setHosOrgCode(orderInfo.getHosCode());
                wsOrderInfo.setNumSourceId(orderInfo.getNumSource());
                wsOrderInfo.setTakePassword(orderInfo.getTakePassword());
                wsOrderInfo.setPlatformUserId(orderInfo.getPlatformUserId());
                wsResponse = slOrderService.cancelOrder(wsOrderInfo);

                if (CommonUtils.isWSSuccess(wsResponse)) {
                    //医院退号成功，更新业务数据库
                    orderInfo.setUpdateTime(new Date());
                    orderInfo.setState("5");
                    orderInfoService.saveAndUpdate(orderInfo);

                    callFailed = true;
                    String orderState = orderInfo.getState();
                    // 若预约订单为已支付,则需调用退款接口
                    PayOrder payOrder = payOrderRepository.getBySubjectId(orderInfo.getId());

                    if (payOrder != null && PayOrder.Status.SUCCESS.equals(payOrder.getStatus())) {//已预约已支付,需退款

                        BigDecimal amount = new BigDecimal(orderInfo.getCost());//费用单位:分
                        try {
                            payOrder = payService.refundOrder(payOrder.getId(), amount.multiply(new BigDecimal(100)).longValue());
                            DateTime dateTime = new DateTime(new Date());
                            dateTime = dateTime.plusMinutes(15);
                            logger.error("paySchedule result " + paySchedule.setTimer(dateTime.toDate(), payOrder.getId(), PayOrder.Status.REFUNDSUCCESS));
                            // 更新本地表预约订单状态
                            orderInfo.setUpdateTime(new Date());
                            orderInfo.setState("3");
                            orderInfoService.saveAndUpdate(orderInfo);
                            result.setMsg("取消预约挂号成功！");
                            return result;
                        } catch (Exception ex) {
                            message = String.format(String.format("退款失败！您在[健康双流]的订单%s，订单退款失败！" +
                                            "为保障您的退款，请您前往[健康双流]查看订单详情，如有疑问，请咨询客服400-900-9957。",
                                    payOrder.getShowOrderId()));
                            smssl.send(orderInfo.getUserPhone(), message);
                        }
                    }
                }
            }
            //判断订单状态为5则直接退款
            else if (orderInfo.getState() != null && orderInfo.getState().equals("5")) {

                callFailed = true;
                String orderState = orderInfo.getState();
                // 若预约订单为已支付,则需调用退款接口
                PayOrder payOrder = payOrderRepository.getBySubjectId(orderInfo.getId());
                if (payOrder != null && PayOrder.Status.SUCCESS.equals(payOrder.getStatus())) {//已预约已支付,需退款
                    BigDecimal amount = new BigDecimal(orderInfo.getCost());//费用单位:分
                    try {
                        payOrder = payService.refundOrder(payOrder.getId(), amount.multiply(new BigDecimal(100)).longValue());

                        DateTime dateTime = new DateTime(new Date());
                        dateTime = dateTime.plusMinutes(15);
                        logger.error("paySchedule result " + paySchedule.setTimer(dateTime.toDate(), payOrder.getId(), PayOrder.Status.REFUNDSUCCESS));
                        // 更新本地表预约订单状态
                        orderInfo.setUpdateTime(new Date());
                        orderInfo.setState("3");
                        orderInfoService.saveAndUpdate(orderInfo);
                        result.setMsg("取消预约挂号成功！");
                        return result;
                    } catch (Exception ex) {
                        message = String.format(String.format("退款失败！您在[健康双流]的订单%s，订单退款失败！" +
                                        "为保障您的退款，请您前往[健康双流]查看订单详情，如有疑问，请咨询客服400-900-9957。",
                                payOrder.getShowOrderId()));
                        smssl.send(orderInfo.getUserPhone(), message);
                    }
                }
            }

        } else {
            result.setCode(1000);
            result.setMsg("订单信息不存在！");
            return result;
        }
        result.setCode(1000);
        result.setMsg("取消预约挂号失败！");
        if (!callFailed) {
            AppConfig appConfig = appConfigService.findSingleAppConfigByKeyWord("app.common.webser.errmsg.switch");
            if (appConfig != null && "1".equals(appConfig.getData())) {// 预约挂号显示区域接口错误提示开关 0-显示统一错误信息,1-显示区域接口错误信息
                result.setMsg(wsResponse != null ? wsResponse.getMessageHeader().getDesc() : "");
            }
        }
        return result;
    }


    private String generateOrderBusinessData(SubmitOrderDTO info) {
        String time = info.getOrderTime();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            sdf.applyPattern("yyyy-MM-dd");
            time = sdf.format(date) + " " + DateUtils.getWeekOfDate(date) + " " + DateUtils.getAPM(date);
        } catch (Exception ex) {
            logger.error(Exceptions.getStackTraceAsString(ex));
        }
        return String.format("{\n" +
                        "    \"doctorName\": \"%s\",\n" +
                        "    \"doctorCode\": \"%s\",\n" +
                        "    \"hospitalName\": \"%s\",\n" +
                        "    \"hospitalCode\": \"%s\",\n" +
                        "    \"deptName\": \"%s\",\n" +
                        "    \"deptCode\": \"%s\",\n" +
                        "    \"state\": \"1\",\n" +
                        "    \"outDoctorLevel\":\"%s\",\n" +
                        "    \"patientName\":\"%s\",\n" +
                        "    \"time\":\"" + time + "\"\n" +
                        "}",
                info.getDoctName(),
                info.getHosDoctCode(),
                info.getHosName(),
                info.getHosOrgCode(),
                info.getDeptName(),
                info.getHosDeptCode(),
                info.getVisitLevel(),
                info.getUserName(),
                info.getOrderTime() + info.getTimeRange());
    }


}