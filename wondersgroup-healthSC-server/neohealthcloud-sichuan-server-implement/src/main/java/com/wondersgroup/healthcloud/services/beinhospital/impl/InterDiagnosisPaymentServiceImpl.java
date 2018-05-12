package com.wondersgroup.healthcloud.services.beinhospital.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
import com.wondersgroup.common.http.utils.JsonConverter;
import com.wondersgroup.healthcloud.constant.URLEnum;
import com.wondersgroup.healthcloud.entity.po.ClinicOrderGenerateRequest;
import com.wondersgroup.healthcloud.entity.po.Order;
import com.wondersgroup.healthcloud.entity.request.UnPaidRecordRequest;
import com.wondersgroup.healthcloud.entity.response.UnPaidRecordResponse;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.entity.pay.SubjectType;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.jpa.repository.user.AccountRepository;
import com.wondersgroup.healthcloud.services.beinhospital.InterDiagnosisPaymentService;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.pay.PayOrderDTO;
import com.wondersgroup.healthcloud.services.pay.PaySchedule;
import com.wondersgroup.healthcloud.services.pay.PayService;
import com.wondersgroup.healthcloud.utils.AreaResourceUrl;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nick on 2016/11/9.
 */
@Service
public class InterDiagnosisPaymentServiceImpl implements InterDiagnosisPaymentService {

    @Autowired
    private HttpRequestExecutorManager manager;

    @Autowired
    private PayService payService;

    @Autowired
    private PaySchedule paySchedule;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AreaResourceUrl areaResourceUrl;

    @Autowired
    private HospitalService hospitalService;

    private static String SUCCESS = "0";

    private static final Logger logger = LoggerFactory.getLogger(InterDiagnosisPaymentServiceImpl.class);

    @Override
    public List<Order> getCurrentUnPayRecord(String hospitalCode, String uid, String cityCode) throws IOException {
        UnPaidRecordRequest request = new UnPaidRecordRequest();
        Account account = accountRepository.findOne(uid);
        if (account != null) {
            request.setYljgdm(hospitalCode);
            request.setKh(account.getIdcard());
            request.setKlx("01");
            //his的SB规则没值也要把节点传过去
            request.setJzlsh("");
            request.setCfhm("");
            request.setZzmzsj("");
            request.setZwmzsj("");

            String xml = JaxbUtil.convertToXml(request);
            Request queryRequest = null;
            logger.info("click query url --> " + areaResourceUrl.getUrl("2", cityCode) + URLEnum.INTER_DIAGNOSIS_PAYMENT_QUERY_PATH);
            logger.info("click query request --> " + xml);
            queryRequest = new RequestBuilder().post().url(areaResourceUrl.getUrl("2", cityCode) + URLEnum.INTER_DIAGNOSIS_PAYMENT_QUERY_PATH).body(xml).build();
            StringResponseWrapper wrapper = (StringResponseWrapper) manager.newCall(queryRequest).run().as(StringResponseWrapper.class);
            String returnXml = wrapper.convertBody();
            logger.info("click query response --> " + returnXml);
            List<Order> records = Lists.newArrayList();
            if (!StringUtils.isEmpty(returnXml)) {
                UnPaidRecordResponse response = JaxbUtil.convertToJavaBean(returnXml, UnPaidRecordResponse.class);
                if (response != null && SUCCESS.equals(response.getResultcode())) {
                    ObjectMapper mapper = new ObjectMapper();
                    for (UnPaidRecordResponse.Item item : response.getItem()) {
                        Order order = new Order();
                        if (!StringUtils.isEmpty(item.getFlje())) {
                            try {
                                BigDecimal price = new BigDecimal(item.getFlje()).divide(new BigDecimal(100));
                                order.setPrice(price.setScale(2).toString());
                            } catch (Exception ex) {
//                                ex.printStackTrace();
                                order.setPrice("" + Double.valueOf(item.getFlje()) / 100);
                            }
                        } else {
                            order.setPrice("0.00");
                        }

                        String businessStr = generateOrderBusinessData(item.getCfhm(), response.getYymc(), item.getKfsj(),
                                item.getYjks(), response.getYljgdm(), item.getJzlsh());
                        order.setBusiness(mapper.readTree(businessStr));
                        order.setPayStatus(PayOrder.Status.NOTPAY.toString());
                        order.setOrderType(SubjectType.CLINIC.toString());
                        order.setOrderTime(new Date());
                        records.add(order);
                    }
                    return records;
                }
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Order generateClinicOrder(ClinicOrderGenerateRequest request, String cityCode) throws IOException {
        String businessData = generateOrderBusinessData(request.getPrescriptionNum(), request.getHospitalName(), request.getTime(),
                request.getDeptName(), request.getHospitalCode(), request.getJzlsh());
        List<PayOrder> payOrders = payService.getPayOrdersByPrescriptionNum(businessData, cityCode);
        Order order = null;
        ObjectMapper mapper = new ObjectMapper();
        PayOrder payOrder;
        if (CollectionUtils.isEmpty(payOrders)) {
            BigDecimal price = new BigDecimal(request.getPrice());
            Long amount = (price.multiply(new BigDecimal(100))).longValue();
            payOrder = generatePayOrder(amount, orderIdGenerator(), request.getUid(), businessData, cityCode);
        } else {
            payOrder = payOrders.get(0);
            if (payOrder.getStatus().equals(PayOrder.Status.FAILURE) || payOrder.getStatus().equals(PayOrder.Status.REFUNDSUCCESS)) {
                payOrder.setStatus(PayOrder.Status.NOTPAY);
                payService.deleteOrder(payOrder.getId());
                payOrder = generatePayOrder(payOrder.getAmount(), payOrder.getSubjectId(), payOrder.getUid(), payOrder.getBusiness(), cityCode);
            }
        }
        if (payOrder != null) {
            order = new Order();
            order.setOrderId(payOrder.getSubjectId());
            order.setPayStatus(payOrder.getStatus().toString());
            order.setOrderTime(payOrder.getUpdateTime());
            order.setOrderType(payOrder.getSubjectType().toString());
            order.setBusiness(mapper.readTree(payOrder.getBusiness()));
            PayOrderDTO PayParam = hospitalService.getLianPayParam(payOrder);
            order.setPay_order(PayParam);
            try {
                BigDecimal price = new BigDecimal(payOrder.getAmount()).divide(new BigDecimal(100));
                order.setPrice(price.setScale(2).toString());
            } catch (Exception ex) {
                logger.error(Exceptions.getStackTraceAsString(ex));
                order.setPrice("" + Double.valueOf(payOrder.getAmount()) / 100);
            }
        }

        return order;
    }

    /**
     * 生成订单之后就触发第二天凌晨过期任务
     *
     * @param amount
     * @param businessOrderId
     * @param businessData
     * @param uid
     * @return
     */
    @Transactional
    private PayOrder generatePayOrder(Long amount, String businessOrderId, String uid, String businessData, String... cityCode) {

        PayOrder order = null;
        if (cityCode != null && cityCode.length > 0 && "510122000000".equals(cityCode[0])) {
            order = payService.generatePayOrder(amount, SubjectType.CLINIC_SL, businessOrderId, "健康双流诊间支付", null, "健康双流诊间支付", uid, businessData, cityCode);
        } else {
            order = payService.generatePayOrder(amount, SubjectType.CLINIC, businessOrderId, "微健康诊间支付", null, "微健康诊间支付", uid, businessData, cityCode);
        }

        DateTime dateTime = new DateTime(new Date());
        dateTime = dateTime.plusDays(1).withTime(0, 0, 0, 0);
        paySchedule.setTimer(dateTime.toDate(), order.getId(), PayOrder.Status.SUCCESS);
        return order;
    }

    /**
     * 按规则生产订单编号
     *
     * @return
     */
    private String orderIdGenerator() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(new Date());
        StringBuilder builder = new StringBuilder();
        builder.append("yp").append(time);
        int randomNum = (int) (Math.random() * 9000) + 1000;
        builder.append(randomNum);
        return builder.toString();
    }

    private String generateOrderBusinessData(String prescriptionNum, String hospitalName, String time, String deptName, String hospitalCode, String jzlsh) {
        return String.format("{\"prescriptionNum\":\"%s\",\"hospitalName\":\"%s\",\"time\":\"%s\",\"deptName\":\"%s\",\"hospitalCode\":\"%s\",\"jzlsh\":\"%s\"}",
                prescriptionNum, hospitalName, time, deptName, hospitalCode, jzlsh);
    }

    public static void main(String[] args) {
        BigDecimal price = new BigDecimal("10.03000");
        Long amount = (price.multiply(new BigDecimal(100))).longValue();
        System.out.println("amount " + amount);
    }
}
