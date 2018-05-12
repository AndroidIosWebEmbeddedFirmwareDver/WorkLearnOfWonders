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
import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.common.utils.MD5Utils;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by dukuanxin on 2017/5/22.
 */
public class NewLianPayUtil {

    private static final Logger logger = LoggerFactory.getLogger(NewLianPayUtil.class);

    private String name;
    private String url;
    private String appId;
    private String appSecret;

    private HttpRequestExecutorManager manager;
    private NewLianPayKeyUtil keyUtil;

    public static class OrderInfo {
        public String retcode;
        public String retmsg;
        public OrderInfo.Data retdata;

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

    public NewLianPayUtil(String url, String appId, String appSecret) {
        this.url = url;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getKey() {
        return keyUtil.fetchKey();
    }


    /**
     * TODO;2018年01月30日13:53:08，订单状态查询
     * 商户通过该接口，可以对订单进行查询最新订单状况(查询收银台系统记录状态)，只能查询支付交易。
     * http://XXX.XXX.XXX.XXX/directpay/queryOrderinfo.do
     *
     * @param channel
     * @param orderId
     * @param isRefund
     * @return
     */
    public OrderInfo.Data getOrdeInfo(String channel, String orderId, Boolean isRefund) {
        Request request = new RequestBuilder().post().url(url + "/directpay/queryOrderinfo.do").body(queryRequestBody(channel, orderId, isRefund)).build();
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

    /**
     * TODO;2018年01月30日13:53:08，订单信息查询(实时查询接口)
     * http://XXX.XXX.XXX.XXX/directpay/queryPayInfo.do
     *
     * @param channel
     * @param orderId
     * @param isRefund
     * @return
     */
    public OrderInfo.Data getOrderPayInfo(String channel, String orderId, Boolean isRefund) {
        Request request = new RequestBuilder().post().url(url + "/directpay/queryPayInfo.do").body(queryPayInfoRequestBody(channel, orderId, isRefund)).build();
        JsonObjectResponseWrapper<OrderInfo> wrapper = (JsonObjectResponseWrapper<OrderInfo>) manager.newCall(request).run().as(JsonObjectResponseWrapper.class);
        OrderInfo result = wrapper.withObjectType(OrderInfo.class).convertBody();
        if (result.retcode.equals("succ")) {
            return result.retdata;
        } else {
            return null;
        }
    }

    private String queryPayInfoRequestBody(String channel, String orderId, Boolean isRefund) {
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

    /**
     * TODO;2018年01月30日13:53:08，订单退款申请(直联退款)
     * http://XXX.XXX.XXX.XXX/directpay/directRefundService.do
     *
     * @param channel
     * @param orderId
     * @param refundId
     * @param amount
     * @return
     */
    public Boolean refund(String channel, String orderId, String refundId, Long amount) {
        Request request = new RequestBuilder().post().url(url + "/directpay/directRefundService.do").body(refundRequestBody(channel, orderId, refundId, amount)).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = wrapper.convertBody();
        logger.info("OrderPayCompletedService refund : " + orderId + " --> " + result.toString());
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


    /**
     * TODO;2018年01月30日13:53:08，支付订单撤销接口
     * http://XXX.XXX.XXX.XXX/directpay/payOrderCancelService.do
     *
     * @param channel
     * @param orderId
     * @param amount
     * @return
     */
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
        //logger.info(StringUtils.join(strs, "|"));
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
        //logger.info(StringUtils.join(strs, "|"));
        return ByteString.of(StringUtils.join(strs, "|").getBytes()).md5().hex();
    }

    public void setManager(HttpRequestExecutorManager manager) {
        this.manager = manager;
    }

    public void setKeyUtil(NewLianPayKeyUtil keyUtil) {
        this.keyUtil = keyUtil;
    }

    public static void main(String... args) {
        /*NewLianPayUtil util = new NewLianPayUtil("http://cash.wdepay.cn:30080", "305111700000121", "ekw1g671x54mnoodocyjrp7hs5sd8mzb");
        util.manager = new HttpRequestExecutorManager(new OkHttpClient());
        util.keyUtil = new NewLianPayKeyUtil("http://cash.wdepay.cn:30080", "305111700000121", "ekw1g671x54mnoodocyjrp7hs5sd8mzb");
        util.keyUtil.setManager(util.manager);*/

        NewLianPayUtil util = new NewLianPayUtil("http://cash.wdepay.cn:30080", "305111700000109", "yj7hhb3pzhfv0nr7fb8ypeosx5d0aee6");
        util.manager = new HttpRequestExecutorManager(new OkHttpClient());
        util.keyUtil = new NewLianPayKeyUtil("http://cash.wdepay.cn:30080", "305111700000109", "yj7hhb3pzhfv0nr7fb8ypeosx5d0aee6");
        util.keyUtil.setManager(util.manager);
//        String sign = util.generateCallbackSign("true", 300L, "58e2851eb3ad4dc7ad1a4030e84a7557");
//        OrderInfo.Data orderInfo = util.getOrderInfo("wepay", "61eb4b00991c4e25b008dbe1dc5f05f0", false);
        String id = forgeRefundId(IdGen.uuid());
//        System.out.println(id);
//        System.out.println(ReflectionToStringBuilder.toString(orderInfo));
        Boolean refund = util.refund("wepay", "0b415c7d4b1a458a8ff11dc648dab77b", id, 800L);
//        Boolean refund = util.refund("wepay", "0b415c7d4b1a458a8ff11dc648dab77b", "20170721a46ea2sc6eca41671", 400L);
        System.out.println(refund);
//        System.out.println(sign);
//        OrderInfo.Data data = util.getOrderInfo("alipay", "wonders00002", false);
//        System.out.println(util.refund("alipay", "wonders00001", "1", 1L));
//        System.out.println(util.getKey());
//        System.out.println(util.refundRequestBody("1", "2", 100L));
    }

    public static String forgeRefundId(String refundId) {
        return DateUtils.getDate() + MD5Utils.encrypt16(refundId);
    }
}
