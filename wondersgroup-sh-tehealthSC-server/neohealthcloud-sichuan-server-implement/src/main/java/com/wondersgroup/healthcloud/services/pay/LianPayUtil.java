package com.wondersgroup.healthcloud.services.pay;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.JsonNodeResponseWrapper;
import com.wondersgroup.common.http.entity.JsonObjectResponseWrapper;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
public class LianPayUtil {

    private String name;
    private String url;
    private String appId;
    private String appSecret;

    private HttpRequestExecutorManager manager;
    private LianPayKeyUtil keyUtil;

    public static class OrderInfo {
        public String retcode;
        public String retmsg;
        public Data retdata;

        public static class Data {
            public String amount;//订单总金额
            public String appid;//商户号
            public String channel;//第三方支付渠道
            public String fundchn_journal_no;//资金通道返回流水号
            public String gen_time;//下单时间
            public String mer_aply_amt;//已退款金额
            public String mer_aply_num;//已退款笔数
            public String order_no;//订单号
            public String paytype;//发起端类型
            public String status;//订单状态
            public String txnmode;//交易方式
        }
    }

    public LianPayUtil(String name, String url, String appId, String appSecret) {
        this.name = name;
        this.url = url;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getKey() {
        return keyUtil.fetchKey();
    }

    public OrderInfo.Data getOrderInfo(String channel, String orderId, Boolean isRefund) {
        Request request = new RequestBuilder().post().url(url + "/directpay/queryPayInfo.do").body(queryRequestBody(channel, orderId, isRefund)).build();
        JsonObjectResponseWrapper<OrderInfo> wrapper = (JsonObjectResponseWrapper<OrderInfo>) manager.newCall(request).run().as(JsonObjectResponseWrapper.class);
        OrderInfo result = wrapper.withObjectType(OrderInfo.class).convertBody();
        if (result.retcode.equals("succ")) {
            return result.retdata;
        } else {
            return null;
        }
    }

    private String queryRequestBody(String channel, String orderId, Boolean isRefund) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        long timestamp = System.currentTimeMillis();
        node.put("channel", channel);
        node.put("submerno", appId);
        node.put("appid", appId);
        node.put("order_no", orderId);
        node.put("txnmode", isRefund ? "0" : "1");
        node.put("timestamp", String.valueOf(timestamp));
        node.put("sign", generateSign(null, orderId, timestamp));
        return node.toString();
    }

    public Boolean refund(String channel, String orderId, String refundId, Long amount) {
        Request request = new RequestBuilder().post().url(url + "/directpay/directRefundService.do").body(refundRequestBody(channel, orderId, refundId, amount)).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = wrapper.convertBody();
        System.out.println(result.toString());
        if ("succ".equals(result.get("retcode").asText())) {
            return true;
        } else {
            throw new RuntimeException();
        }
    }

    private String refundRequestBody(String channel, String orderId, String refundId, Long amount) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        long timestamp = System.currentTimeMillis();
        node.put("channel", channel);
        node.put("submerno", appId);
        node.put("paytype", "direct");
        node.put("appid", appId);
        node.put("order_no", orderId);
        node.put("amount", amount.toString());
        node.put("refund_no", refundId);
        node.put("refund_reason", " ");
        node.put("refund_type", "online");
        node.put("timestamp", String.valueOf(timestamp));
        node.put("sign", generateSign(amount, refundId, timestamp));
        return node.toString();
    }


    public Boolean cancelOrder(String channel, String orderId, Long amount) {
        Request request = new RequestBuilder().post().url(url + "/directpay/payOrderCancelService.do").body(cancelRequestBody(channel, orderId, amount)).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = wrapper.convertBody();
        if ("succ".equals(result.get("retcode").asText())) {
            return true;
        } else {
            throw new RuntimeException();
        }
    }

    private String cancelRequestBody(String channel, String orderId, Long amount) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        long timestamp = System.currentTimeMillis();
        node.put("channel", channel);
        node.put("submerno", appId);
        node.put("appid", appId);
        node.put("order_no", orderId);
        node.put("timestamp", String.valueOf(timestamp));
        node.put("sign", generateSign(amount, orderId, timestamp));
        return node.toString();
    }

    private String generateSign(Long amount, String id, Long timestamp) {
        List<String> strs = Lists.newLinkedList();
        strs.add("");
        if (amount != null && amount > 0) {
            strs.add(amount.toString());
        }
        strs.add(appId);
        strs.add(id);
        strs.add(timestamp.toString());
        strs.add(getKey());
        strs.add("");
        System.out.println(StringUtils.join(strs, "|"));
        return ByteString.of(StringUtils.join(strs, "|").getBytes()).md5().hex();
    }

    public String generateCallbackSign(String success, Long amount, String id) {
        List<String> strs = Lists.newLinkedList();
        strs.add("");
        strs.add(success);
        strs.add(appId);
        strs.add(id);
        strs.add(amount.toString());
        strs.add(getKey());
        strs.add("");
        System.out.println(StringUtils.join(strs, "|"));
        return ByteString.of(StringUtils.join(strs, "|").getBytes()).md5().hex();
    }

    public void setManager(HttpRequestExecutorManager manager) {
        this.manager = manager;
    }

    public void setKeyUtil(LianPayKeyUtil keyUtil) {
        this.keyUtil = keyUtil;
    }

    public static void main(String... args) {
        LianPayUtil util = new LianPayUtil("sl", "http://cashzsc.wdepay.cn:20080", "308111700000088", "hwadrqreli9hmkc0sdrdz1rcgcqajcci");
        util.manager = new HttpRequestExecutorManager(new OkHttpClient());
        util.keyUtil = new LianPayKeyUtil("sl", "http://cashzsc.wdepay.cn:20080", "308111700000088", "hwadrqreli9hmkc0sdrdz1rcgcqajcci");
        util.keyUtil.setManager(util.manager);
//        String sign = util.generateCallbackSign("true", 300L, "58e2851eb3ad4dc7ad1a4030e84a7557");
//        OrderInfo.Data orderInfo = util.getOrderInfo("wepay", "61eb4b00991c4e25b008dbe1dc5f05f0", false);
        String id = IdGen.uuid();
//        System.out.println(id);
//        System.out.println(ReflectionToStringBuilder.toString(orderInfo));
        Boolean refund = util.refund("alipay", "ee77f072937a4e73b0e918a09ed4da5c", id, 900L);
        System.out.println(refund);
//        System.out.println(sign);
//        OrderInfo.Data data = util.getOrderInfo("alipay", "wonders00002", false);
//        System.out.println(util.refund("alipay", "wonders00001", "1", 1L));
//        System.out.println(util.getKey());
//        System.out.println(util.refundRequestBody("1", "2", 100L));
    }
}
