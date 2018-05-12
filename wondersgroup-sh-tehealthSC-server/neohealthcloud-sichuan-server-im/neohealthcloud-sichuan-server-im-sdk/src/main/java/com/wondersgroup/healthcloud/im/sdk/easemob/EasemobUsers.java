package com.wondersgroup.healthcloud.im.sdk.easemob;

import com.wondersgroup.healthcloud.im.sdk.common.ApiHttpClient;
import com.wondersgroup.healthcloud.im.sdk.exception.EasemobUnAuthException;
import com.wondersgroup.healthcloud.im.sdk.util.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.util.HashMap;
import java.util.Map;

/**
 * http://api-docs.easemob.com/#/用户体系集成
 * Created by jialing.yao on 2017-5-26.
 */
@Slf4j
public class EasemobUsers {
    private ApiHttpClient apiHttpClient;
    private String apiUrl;
    private EasemobToken easemobToken;

    public EasemobUsers(String apiUrl, ApiHttpClient apiHttpClient, EasemobToken easemobToken) {
        this.apiUrl = apiUrl;
        this.apiHttpClient = apiHttpClient;
        this.easemobToken = easemobToken;
    }

    //用户注册
    @Retryable(value = {EasemobUnAuthException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000l, multiplier = 1))
    public Boolean register(String talkid, String password, String nickname) {
        String reqUrl = apiUrl + "/users";
        Map<String, Object> body = new HashMap<>();
        body.put("username", talkid);
        body.put("password", password);
        body.put("nickname", nickname);
        ResponseEntity<String> response = apiHttpClient.doPost(easemobToken.buildHeaders(), reqUrl, JsonConverter.toJson(body));
        //400 用户已存在、用户名或密码为空、用户名不合法[见用户名规则]
        //401 未授权[无token、token错误、token过期]
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            easemobToken.refreshToken();
            throw new EasemobUnAuthException("401 未授权[无token、token错误、token过期]");
        }
        return response.getStatusCode() == HttpStatus.OK;
    }

    //用户注册 -服务降级(3次重试失败，则直接返回false)
    @Recover
    public Boolean registerRecover(EasemobUnAuthException e) {
        log.error("注册easemob用户失败.", e);
        return false;
    }
}
