package com.wondersgroup.healthcloud.push.JPush;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.push.util.JsonConverter;
import com.wondersgroup.healthcloud.push.util.MapHelper;
import lombok.extern.slf4j.Slf4j;
import okio.Buffer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jialing.yao on 2017-5-9.
 */
@Slf4j
public class JPushDefaultCallback implements PushCallback {
    private String appKey;

    public JPushDefaultCallback(String appKey) {
        this.appKey = appKey;
    }

    private void printLog(Response response) throws IOException {
        Request request = response.request();
        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        String rbody = buffer.readString(requestBody.contentType().charset());
        JsonNode req_body = JsonConverter.toJsonNode(rbody);
        MapHelper requestData = MapHelper.builder()
                .putObj("method", request.method()).putObj("url", request.httpUrl().toString())
                .putObj("headers", request.headers().toMultimap()).putObj("body", req_body);

        JsonNode resp_body = JsonConverter.toJsonNode(response.body().string());
        MapHelper responseData = MapHelper.builder()
                .putObj("statusCode", response.code()).putObj("headers", response.headers().toMultimap())
                .putObj("body", resp_body);

        JPushLog jpushLog = new JPushLog();
        jpushLog.setAppKey(appKey);
        jpushLog.setRequest(requestData);
        jpushLog.setResponse(responseData);
        jpushLog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        log.info(JsonConverter.toJson(jpushLog));
    }

    @Override
    public void onSuccess(Response response) {
        try {
            log.info(appKey + " call success.");
            this.printLog(response);
            //log.info(appKey + " success " + resp_body.get("msg_id").asText());
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }

    @Override
    public void onFailure(Response response) {
        try {
            log.info(appKey + " call fail.");
            this.printLog(response);
            //String body = response.body().string();
            //JsonNode node = JsonConverter.toJsonNode(body);
            //log.info(appKey + " failure " + node.get("msg_id").asText() + " code " + node.get("error").get("code").asInt() + " msg " + node.get("error").get("message").asText());
        } catch (IOException e) {
            throw Exceptions.unchecked(e);
        }
    }
}
