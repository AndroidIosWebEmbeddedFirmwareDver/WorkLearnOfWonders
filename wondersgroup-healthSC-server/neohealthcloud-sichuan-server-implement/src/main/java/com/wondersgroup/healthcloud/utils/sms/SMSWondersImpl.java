package com.wondersgroup.healthcloud.utils.sms;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.JsonNodeResponseWrapper;

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
 * <p/>
 * Created by zhangzhixiu on 16/3/6.
 */
public class SMSWondersImpl implements SMS {

    private final String token;
    private final String url;
    private final String sid;

    private HttpRequestExecutorManager manager;

    public void setHttpManager(HttpRequestExecutorManager manager) {
        this.manager = manager;
    }

    public SMSWondersImpl(String token, String host, String sid) {
        this.url = host + "/wondersSMS/sendSMS";
        this.token = token;
        this.sid = sid;
    }

    @Override
    public void send(String mobile, String content) {
        Request request = new RequestBuilder().get().url(url).params(new String[]{"content", content, "phonelist", mobile, "taskId", "00000000000000", "token", token, "sid", sid}).build();
        JsonNodeResponseWrapper result = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode node = result.convertBody();
        if (!"0".equals(node.get("code").asText())) {
            throw new SMSFailureException();
        }
    }
}
