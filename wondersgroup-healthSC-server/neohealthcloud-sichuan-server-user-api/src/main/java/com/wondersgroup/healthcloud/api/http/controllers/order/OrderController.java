package com.wondersgroup.healthcloud.api.http.controllers.order;

import com.wondersgroup.healthcloud.api.http.dto.order.CancelOrderDTO;
import com.wondersgroup.healthcloud.api.http.dto.order.OrderDetailDTO;
import com.wondersgroup.healthcloud.api.http.dto.order.SubmitOrderDTO;
import com.wondersgroup.healthcloud.api.utils.CommonUtils;
import com.wondersgroup.healthcloud.api.utils.MeDateUtil;
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
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
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
import com.wondersgroup.healthcloud.services.order.OrderService;
import com.wondersgroup.healthcloud.services.pay.*;
import com.wondersgroup.healthcloud.utils.sms.SMS;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhaozhenxing on 2016/11/2.
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger("exlog");

    @Value("${sichuan.webservice.callback.url}")
    private String scCBServiceUrl;

    @Autowired
    private OrderService orderService;// 用于调用区域平台WebService服务

    @Autowired
    private OrderInfoService orderInfoService;// 用于调用处理本地表业务逻辑

    @Autowired
    private PayService payService;

    @Autowired
    private PaySchedule paySchedule;

    @Autowired
    private SMS sms;

    @Autowired
    private PayOrderRepository payOrderRepository;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    OrderInfoRepository orderInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppConfigService appConfigService;


    @Autowired
    private NewLianPayUtilSelector newSelector;

    /**
     * TODO;2018年01月30日12:59:45，之前的逻辑太垃圾了，1.新增每次查询订单主动去链支付匹配订单支付状态的逻辑，2.优化查询性能
     * <p>
     * 获取预约订单列表
     * 调用本地服务
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getOrderList/withCheckState", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity
    getOrderListWithCheckState(@RequestParam(required = true) String uid,
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
        List<Order> list = orderInfoService.list(orderInfo, pageNo, pageSize);
        //TODO;先检测并处理
        toCheckStateAndUpdateSelef(list);
        //TODO;再查询并返回
        list = orderInfoService.list(orderInfo, pageNo, pageSize);
        Boolean hasMore = false;
        if (null != list && list.size() > 10) {
            list = list.subList(0, 10);
            hasMore = true;
        }
        result.setContent(list, hasMore, null, hasMore ? (pageNo + 1) + "" : pageNo + "");
        return result;
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
            String cityCode = hospitalService.findByHospitalCode(info.getHosCode()).getCityCode();
            GetOrderDetailInfoRequest.OrderInfo orderInfo = new GetOrderDetailInfoRequest.OrderInfo();
            orderInfo.setOrderId(info.getScOrderId());
            GetOrderDetailInfoResponse.Result wsRtn = orderService.getOrderDetail(orderInfo, cityCode);
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
     * <p>
     * 获取预约订单列表
     * 调用本地服务
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity getOrderList(@RequestParam(required = true) String uid,
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
     * 提交预约订单
     * 调用区域平台服务及本地服务
     *
     * @return
     */
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity submitOrder(@RequestBody SubmitOrderDTO orderInfo) {
        JsonResponseEntity result = new JsonResponseEntity();

        if (orderInfo == null || StringUtils.isEmpty(orderInfo.getUid())) {
            result.setCode(1000);
            result.setMsg("用户信息缺失");
            return result;
        } else {
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

        Hospital hospital = hospitalService.findByHospitalCode(orderInfo.getHosOrgCode());
        if (hospital.getAppid() == null || hospital.getAppSecret() == null) {
            result.setCode(1000);
            result.setMsg("未开通商户号，无法进行预约");
            return result;
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
        SubmitOrderByUserInfoResponse wsResp = orderService.submitOrder(orderInfo.toWSOrderInfo());
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
                if (StringUtils.isNotEmpty(wsResult.getActualCost())) {
                    info.setCost(wsResult.getActualCost());
                }
                info.setScOrderId(wsResult.getOrderId());
                info.setScheduleId(wsResult.getScheduleId());// 排班ID替换为区域平台返回的排班ID
                info.setPlatformUserId(wsResult.getPlatformUserId());
                info.setState("1");
                info.setCreateTime(new Date());
                info.setDelFlag("0");// 删除标志 0：不删除 1：已删除
                info.setSource("5101");// 微健康-行政区域编码-成都市
                info.setMediCardId(orderInfo.getMediCardId());
                info.setMedi_card_type_code(orderInfo.getCardType());
                info.setMedi_card_type_name(getMediCardType(orderInfo.getCardType()));

                int flag = 0;// 步骤标志
                try {
                    flag = 1;
                    info = orderInfoService.saveAndUpdate(info);
                    result.setCode(0);
                    result.setMsg("预约挂号成功！");
                    flag = 2;
                    // 本地订单生成后,生成支付订单
                    BigDecimal amount = new BigDecimal(info.getCost());//费用单位:分
                    String tmpStr = generateOrderBusinessData(orderInfo);
                    PayOrder payOrder = payService.generatePayOrder(amount.multiply(new BigDecimal(100)).longValue(), SubjectType.APPOINTMENT, info.getId().toString(), "微健康预约挂号订单", "微健康预约挂号订单",
                            "微健康预约挂号订单", info.getUid(), tmpStr, showOrderId.replace("yy", "yp"));
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
                    orderService.cancelOrder(cancelOrder);
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
        OrderCancelInfoResponse wsResponse;
        boolean callFailed = false;
        if (orderInfo != null) {
            String msg = orderInfoService.cancelValidation(orderInfo);
            if (StringUtils.isNotEmpty(msg)) {
                result.setCode(1000);
                result.setMsg(msg);
                return result;
            }

            String message = null;
            // 调用区域平台接口取消预约订单
            OrderCancelInfoRequest.OrderInfo wsOrderInfo = info.toWSOrderInfo();
            wsOrderInfo.setOrderId(orderInfo.getScOrderId());
            wsOrderInfo.setHosOrgCode(orderInfo.getHosCode());
            wsOrderInfo.setNumSourceId(orderInfo.getNumSource());
            wsOrderInfo.setTakePassword(orderInfo.getTakePassword());
            wsOrderInfo.setPlatformUserId(orderInfo.getPlatformUserId());
            wsResponse = orderService.cancelOrder(wsOrderInfo);
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
                        message = String.format(String.format("退款失败！您在[微健康]的订单%s，订单退款失败！" +
                                        "为保障您的退款，请您前往[微健康]查看订单详情，如有疑问，请咨询客服400-900-9957。",
                                payOrder.getShowOrderId()));
                        sms.send(orderInfo.getUserPhone(), message);
                        logger.error(Exceptions.getStackTraceAsString(ex));
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
                result.setMsg(wsResponse.getMessageHeader().getDesc());
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
                wsResponse = orderService.cancelOrder(wsOrderInfo);

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
                            message = String.format(String.format("退款失败！您在[微健康]的订单%s，订单退款失败！" +
                                            "为保障您的退款，请您前往[微健康]查看订单详情，如有疑问，请咨询客服400-900-9957。",
                                    payOrder.getShowOrderId()));
                            sms.send(orderInfo.getUserPhone(), message);
                            logger.error(Exceptions.getStackTraceAsString(ex));
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
                        message = String.format(String.format("退款失败！您在[微健康]的订单%s，订单退款失败！" +
                                        "为保障您的退款，请您前往[微健康]查看订单详情，如有疑问，请咨询客服400-900-9957。",
                                payOrder.getShowOrderId()));
                        sms.send(orderInfo.getUserPhone(), message);
                        logger.error(Exceptions.getStackTraceAsString(ex));
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


    /**
     * 获取就诊卡描述
     *
     * @param cardType 就诊卡类型（01院内就诊卡 02居民健康卡）
     * @return 就诊卡描述
     */
    private String getMediCardType(String cardType) {
        return "01".equals(cardType) ? "院内就诊卡" : "居民健康卡";
    }

}