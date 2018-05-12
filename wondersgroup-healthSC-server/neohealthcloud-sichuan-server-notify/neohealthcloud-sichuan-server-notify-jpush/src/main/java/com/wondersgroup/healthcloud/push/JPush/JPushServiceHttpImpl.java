package com.wondersgroup.healthcloud.push.JPush;

import com.squareup.okhttp.*;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import lombok.extern.slf4j.Slf4j;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by jialing.yao on 2017-5-9.
 */
@Slf4j
public class JPushServiceHttpImpl implements JPushService {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;
    private String authString;
    private PushCallback pushCallback;
    private String jpushUrl = "https://api.jpush.cn/v3/push";
    private int retryCount = 3;

    public void setJpushUrl(String jpushUrl) {
        this.jpushUrl = jpushUrl;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public void buildAuthString(String appKey, String masterSecret) {
        this.authString = "Basic " + ByteString.of((appKey + ":" + masterSecret).getBytes()).base64();
    }

    public void setCallback(PushCallback callback) {
        this.pushCallback = callback;
    }

    private class PushBuilder {
        private static final String platformStr = "\"platform\":\"all\"";//"platform":"all"
        private static final String jsonKVFormat = "\"%s\":\"%s\"";
        private String receiver;//to all if null
        private String content;
        private LinkedList<String> extras = new LinkedList<>();
        private Boolean apnsProduction = true;
        private Boolean pass = false;//是否透传


        public PushBuilder sendTo(String alias) {
            this.receiver = alias;
            return this;
        }

        public PushBuilder sendAll() {
            this.receiver = null;
            return this;
        }

        public PushBuilder message(String content) {
            this.content = content;
            return this;
        }

        public PushBuilder usePass(Boolean pass) {
            this.pass = pass;
            return this;
        }

        public PushBuilder addExtra(String key, String value) {
            this.extras.add(String.format(jsonKVFormat, key, value));
            return this;
        }

        public PushBuilder extras(Map<String, String> extras) {
            this.extras.clear();
            if (extras != null) {
                for (String extra : extras.keySet()) {
                    addExtra(extra, extras.get(extra));
                }
            }
            return this;
        }

        public PushBuilder iosProduction(Boolean apnsProduction) {
            this.apnsProduction = apnsProduction;
            return this;
        }

        private String buildMessage() {
            String body;
            String extraStr = extras.isEmpty() ? "" : (",\"extras\":{" + StringUtils.join(extras, ",") + "}");
            if (!pass) {
                body = "\"notification\":{\"android\":{\"alert\":\"" + content + "\"" + extraStr + "}" +
                        ",\"ios\":{\"alert\":\"" + content + "\"" + extraStr + ",\"badge\":\"+1\"}}";
            } else {
                body = "\"message\":{\"msg_content\":\"" + content + "\"" + extraStr + "}";
            }
            return body;
        }

        public String build() {
            String audience = receiver == null ? "\"audience\":\"all\"" : String.format("\"audience\":{\"alias\":[\"%s\"]}", receiver);
            String body = buildMessage();
            return "{" + platformStr + "," + audience + "," + body + ",\"options\":{\"apns_production\":" + apnsProduction.toString() + "}}";
        }
    }

    @Retryable(value = {RemoteAccessException.class}, interceptor = "retryInterceptor")
    @Override
    public Boolean sendAll(String content, Map<String, String> extras) {
        PushBuilder pushBuilder = new PushBuilder();
        pushBuilder.sendAll().message(content).extras(extras);
        return invoke(pushBuilder.build());
    }

    @Retryable(value = {RemoteAccessException.class}, interceptor = "retryInterceptor")
    @Override
    public Boolean sendAlias(String alias, String content, Map<String, String> extras) {
        PushBuilder pushBuilder = new PushBuilder();
        pushBuilder.sendTo(alias).message(content).extras(extras);
        return invoke(pushBuilder.build());
    }

    //服务降级(3次重试失败，则直接返回false)
    @Recover
    public Boolean sendRecover(RemoteAccessException e) {
        return false;
    }

    private Boolean invoke(String requestContent) {
        Request.Builder builder = new Request.Builder();
        RequestBody body = RequestBody.create(JSON, requestContent);
        builder.url(jpushUrl);
        builder.post(body);
        builder.header("Authorization", authString);
        try {
            Response response = client.newCall(builder.build()).execute();
            Boolean success = response.code() == 200;
            if (pushCallback != null) {
                if (success) {
                    pushCallback.onSuccess(response);
                } else {
                    pushCallback.onFailure(response);
                    throw new RemoteAccessException("JPush call fail.");
                }
            }
            return success;
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }
}
