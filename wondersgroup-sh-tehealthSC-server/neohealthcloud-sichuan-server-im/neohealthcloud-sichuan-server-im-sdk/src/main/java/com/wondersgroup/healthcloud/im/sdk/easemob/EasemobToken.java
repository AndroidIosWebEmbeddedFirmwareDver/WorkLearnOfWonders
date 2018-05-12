package com.wondersgroup.healthcloud.im.sdk.easemob;

import com.wondersgroup.healthcloud.im.sdk.common.ApiHttpClient;
import com.wondersgroup.healthcloud.im.sdk.util.JsonConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * http://api-docs.easemob.com/#/获取token
 * Created by jialing.yao on 2017-5-31.
 */
public class EasemobToken {
    private static ConcurrentHashMap<String, String> tokenCache = new ConcurrentHashMap<>();
    private ApiHttpClient apiHttpClient;
    private String apiUrl;
    private String grantType;
    private String clientID;
    private String clientSecret;

    public EasemobToken(String apiUrl, ApiHttpClient apiHttpClient) {
        this.apiUrl = apiUrl;
        this.apiHttpClient = apiHttpClient;
    }

    public EasemobToken buildToken(String grantType, String clientID, String clientSecret) {
        this.grantType = grantType;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        return this;
    }

    public HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + getToken());
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public String getToken() {
        Object token = tokenCache.get("access_token");
        return token == null ? "" : token.toString();
    }

    public String refreshToken() {
        String reqUrl = apiUrl + "/token";
        Map<String, Object> body = new HashMap<>();
        body.put("grant_type", grantType);
        body.put("client_id", clientID);
        body.put("client_secret", clientSecret);
        ResponseEntity<String> response = apiHttpClient.doPost(new HttpHeaders(), reqUrl, JsonConverter.toJson(body));
        String respBody = response.getBody();
        Map<String, Object> reMap = JsonConverter.toObject(respBody, Map.class);
        String token = String.valueOf(reMap.get("access_token"));
        tokenCache.put("access_token", token);
        return token;
    }
}
