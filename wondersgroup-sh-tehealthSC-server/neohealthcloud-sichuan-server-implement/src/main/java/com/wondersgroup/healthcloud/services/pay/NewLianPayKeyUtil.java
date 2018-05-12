package com.wondersgroup.healthcloud.services.pay;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.JsonNodeResponseWrapper;
import com.wondersgroup.healthcloud.constant.URLEnum;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.services.beinhospital.impl.InterDiagnosisPaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by dukuanxin on 05/22/2017.
 */
public class NewLianPayKeyUtil {

    private static final Logger logger = LoggerFactory.getLogger(NewLianPayKeyUtil.class);

    String url;
    String appId;
    String appSecret;

    private static final String prefix = ":pay:key:";
    private static final Long constValue = 24 * 60 * 60 * 1000L;
    private static final int seconds = 24 * 60 * 60;

    private JedisPool pool;
    private HttpRequestExecutorManager manager;

    public NewLianPayKeyUtil(String url, String appId, String appSecret) {
        this.url = url;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String get() {
        try (Jedis jedis = pool.getResource()) {
            String redisKey = redisKey(appId);
            if (jedis.exists(redisKey)) {
                return jedis.get(redisKey);
            } else {
                String key = fetchKey();
                jedis.setex(redisKey, seconds, key);
                return key;
            }
        }
    }

    private String redisKey(String appId) {
        return appId + prefix + String.valueOf(System.currentTimeMillis() / constValue);
    }


    public String fetchKey() {
        String body = String.format("{\"appid\":\"%s\",\"appsecret\":\"%s\"}", appId, appSecret);
        logger.info("fetchKey --> " + url + "/Batch/Merwork/GetWorkKey");
        logger.info("request body --> " + body);
        Request request = new RequestBuilder().post().url(url + "/Batch/Merwork/GetWorkKey").body(body).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = wrapper.convertBody();
        logger.info("return body --> " + result.toString());
        if ("succ".equals(result.get("retcode").asText())) {
            return result.get("key").asText();
        } else {
            throw new CommonException(1000, "");
        }
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public void setManager(HttpRequestExecutorManager manager) {
        this.manager = manager;
    }
}
