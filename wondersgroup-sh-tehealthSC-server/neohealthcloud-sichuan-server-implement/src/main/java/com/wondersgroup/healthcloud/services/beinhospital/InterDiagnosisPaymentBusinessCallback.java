package com.wondersgroup.healthcloud.services.beinhospital;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
import com.wondersgroup.common.http.utils.JsonConverter;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.constant.URLEnum;
import com.wondersgroup.healthcloud.entity.request.InterDisPayRequest;
import com.wondersgroup.healthcloud.entity.response.InterDiagnosisPaymentResponse;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.entity.message.PayMessage;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.message.PayMessageService;
import com.wondersgroup.healthcloud.services.pay.PayBusinessCallback;
import com.wondersgroup.healthcloud.services.pay.PayService;
import com.wondersgroup.healthcloud.utils.AreaResourceUrl;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.utils.sms.SMS;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nick on 2016/11/10.
 *
 * @author nick
 */
@Service("CLINIC")
public class InterDiagnosisPaymentBusinessCallback implements PayBusinessCallback {

    private static Logger logger = LoggerFactory.getLogger("exlog");

    @Autowired
    private HttpRequestExecutorManager manager;

    @Autowired
    private PayService payService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private SMS sms;

    @Autowired
    private SMS smssl;

    @Autowired
    private PayMessageService payMessageService;

    @Autowired
    private AreaResourceUrl areaResourceUrl;

    private static String ALIPAY = "02";

    private static String WEPAY = "01";

    private static String PAY_SUCCESS_CONTENT = "支付成功！您在[微健康]的订单%s（订单号),已经支付成功！请您前往[微健康]查看订单详情. 如有疑问,请咨询客服400-900-9957. 感谢您使用【微健康】！";

    private static String PAY_SUCCESS_CONTENT_SL = "支付成功！您在[健康双流]的订单%s（订单号),已经支付成功！请您前往[健康双流]查看订单详情. 如有疑问,请咨询客服400-900-9957. 感谢您使用【健康双流】！";

    @Override
    public Boolean onPaySuccess(String subjectId) {

        PayOrder payOrder = payService.findOneBySubjectId(subjectId);
        if (payOrder == null)
            return false;

        Account account = accountService.info(payOrder.getUid());
        boolean result = false;
        if (account != null) {
            JsonNode node = JsonConverter.toJsonNode(payOrder.getBusiness());
            InterDisPayRequest payRequest = new InterDisPayRequest();
            payRequest.setKlx("01");
            payRequest.setKh(account.getIdcard());
            payRequest.setCfhm(node.get("prescriptionNum").asText());
            payRequest.setYljgdm(node.get("hospitalCode").asText());
            payRequest.setZfje(String.valueOf(payOrder.getAmount()));
            payRequest.setZfptddh(payOrder.getSubjectId());
            payRequest.setJzlsh("null".equals(node.get("jzlsh").asText()) ? "" : node.get("jzlsh").asText());
            payRequest.setSjbh("");
            String payMethod = ALIPAY;
            if (!StringUtils.isEmpty(payOrder.getChannel())) {
                if ("alipay".equals(payOrder.getChannel())) {
                    payMethod = ALIPAY;
                } else if ("wepay".equals(payOrder.getChannel())) {
                    payMethod = WEPAY;
                }
            }
            payRequest.setZffs(payMethod);
            payRequest.setJylsh(payOrder.getSubjectId());
            payRequest.setZfsj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            String xml = JaxbUtil.convertToXml(payRequest);
            Request request = new RequestBuilder().post().url(areaResourceUrl.getUrl("2", payOrder.getCityCode()) + URLEnum.INTER_DIAGNOSIS_PAY_PATH).body(xml).build();
            logger.error(areaResourceUrl.getUrl("2", payOrder.getCityCode()) + URLEnum.INTER_DIAGNOSIS_PAY_PATH);
            StringResponseWrapper wrapper = (StringResponseWrapper) manager.newCall(request).run().as(StringResponseWrapper.class);
            String response = wrapper.convertBody();
            logger.error("click complete request --> " + xml);
            logger.error("click complete response --> " + response);
            if (!StringUtils.isEmpty(response)) {
                InterDiagnosisPaymentResponse paymentResponse = JaxbUtil.convertToJavaBean(response, InterDiagnosisPaymentResponse.class);
                if (paymentResponse != null && paymentResponse.getResultcode().equals("0")) {
                    result = true;
                    List<InterDiagnosisPaymentResponse.Item> itemList = paymentResponse.getItems();
                    payOrder.setUpdateTime(new Date());
                    String businessData = JsonConverter.toJson(((ObjectNode) node).put("receiptNo", itemList.get(0).getSjbh()));
                    payOrder.setBusiness(businessData);
                    payOrder.setStatus(PayOrder.Status.SUCCESS);
                    payService.update(payOrder);
                    messageCenter(payOrder);
                    try {
                        if (payOrder.getCityCode().equals("510122000000")) {
                            smssl.send(account.getMobile(), String.format(PAY_SUCCESS_CONTENT_SL, payOrder.getSubjectId()));
                        } else {
                            sms.send(account.getMobile(), String.format(PAY_SUCCESS_CONTENT, payOrder.getSubjectId()));
                        }
                        //                        //2018年01月17日18:01:52 fix it as a bug,it is no necessry to write after send sms success
                        //                        messageCenter(payOrder);
                    } catch (Exception e) {
                        logger.error(Exceptions.getStackTraceAsString(e));
                    }
                } else {
                    if (paymentResponse != null) {
                        logger.error(paymentResponse.getResultmessage());
                    }
                    payService.refundOrder(payOrder.getId(), payOrder.getAmount());
                }
            }
        }
        return result;
    }

    @Override
    public Boolean onRefundSuccess(String subjectId, Long amount) {
        return true;
    }

    @Override
    public Boolean onExpire(String subjectId) {
        PayOrder order = payService.findOneBySubjectId(subjectId);
        if (order == null)
            throw new RuntimeException();

        if (!order.getStatus().equals(PayOrder.Status.SUCCESS)) {
            order.setStatus(PayOrder.Status.EXPIRED);
            payService.update(order);
            return true;
        }
        return false;
    }

    private void messageCenter(PayOrder order) {
        JsonNode node = JsonConverter.toJsonNode(order.getBusiness());
        Hospital hospital = hospitalService.findByHospitalCode(node.get("hospitalCode").asText());
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date prescriptionTime = format.parse(node.get("time").asText());
            PayMessage message = new PayMessage();
            message.setCreateDate(new Date());
            message.setHospitalName(hospital == null ? node.get("hospitalName").asText() : hospital.getHospitalName());
            message.setPrescriptionCode(node.get("prescriptionNum").asText());
            message.setId(IdGen.uuid());
            message.setPrice(new BigDecimal(order.getAmount()).divide(new BigDecimal(100)).doubleValue());
            message.setOrderId(order.getSubjectId());
            message.setPayStatus(new Integer(1));
            message.setOrderTime(order.getUpdateTime());
            message.setPayType(new Integer(1));
            message.setPrescriptionTime(prescriptionTime);
            message.setRegisterId(order.getUid());
            payMessageService.save(message);
        } catch (ParseException e) {
            String error = Exceptions.getStackTraceAsString(e);
            logger.error(error);
        }
    }
}
