package com.wondersgroup.healthcloud.services.pay;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.JsonNodeResponseWrapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
 * Created by zhangzhixiu on 05/11/2016.
 */
public class LianPayKeyUtil {

    String name;
    String url;
    String appId;
    String appSecret;

    private static final String prefix = "sc:pay:key:";
    private static final Long constValue = 24 * 60 * 60 * 1000L;
    private static final int seconds = 24 * 60 * 60;

    private JedisPool pool;
    private HttpRequestExecutorManager manager;

    public LianPayKeyUtil(String name, String url, String appId, String appSecret) {
        this.name = name;
        this.url = url;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String get() {
        try (Jedis jedis = pool.getResource()) {
            String redisKey = redisKey();
            if (jedis.exists(redisKey)) {
                return jedis.get(redisKey);
            } else {
                String key = fetchKey();
                jedis.setex(redisKey, seconds, key);
                return key;
            }
        }
    }

    private String redisKey() {
        return prefix + name + ":" + String.valueOf(System.currentTimeMillis() / constValue);
    }


    public String fetchKey() {
        String body = String.format("{\"appid\":\"%s\",\"appsecret\":\"%s\"}", appId, appSecret);
        Request request = new RequestBuilder().post().url(url + "/Batch/Merwork/GetWorkKey").body(body).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = wrapper.convertBody();
        if ("succ".equals(result.get("retcode").asText())) {
            return result.get("key").asText();
        } else {
            throw new RuntimeException();
        }
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public void setManager(HttpRequestExecutorManager manager) {
        this.manager = manager;
    }
}
