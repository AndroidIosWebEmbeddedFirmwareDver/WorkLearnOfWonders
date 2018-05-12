package com.wondersgroup.healthcloud.push.JPush;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import com.wondersgroup.healthcloud.push.mongo.MongoTemplates;
import com.wondersgroup.healthcloud.push.util.JsonConverter;
import com.wondersgroup.healthcloud.push.util.MapHelper;
import lombok.extern.slf4j.Slf4j;
import okio.Buffer;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jialing.yao on 2017-5-10.
 */
@Slf4j
public class JPushMongoCallback implements PushCallback {
    @Autowired
    private MongoTemplates mongoTpl;

    private boolean isSuccessful = false;
    private String appKey;

    public JPushMongoCallback(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public void onSuccess(Response response) {
        try {
            log.info(appKey + " call success.");
            isSuccessful = true;
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
        jpushLog.setIsSuccessful(isSuccessful);
        jpushLog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        log.info(JsonConverter.toJson(jpushLog));
        mongoTpl.insertOne("jpush_log", Document.parse(JsonConverter.toJson(jpushLog)));
    }
}
