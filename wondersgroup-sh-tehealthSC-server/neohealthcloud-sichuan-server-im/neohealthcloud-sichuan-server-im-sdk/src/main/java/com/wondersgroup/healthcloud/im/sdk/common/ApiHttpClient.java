package com.wondersgroup.healthcloud.im.sdk.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by jialing.yao on 2017-5-26.
 */
public class ApiHttpClient {
    private RestTemplate restTemplate;

    public ApiHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> doGet(HttpHeaders headers, String reqUrl, Map<String, Object> input) {
        ResponseEntity<String> result = restTemplate.exchange(reqUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class, input);
        return result;
    }

    public ResponseEntity<String> doPost(HttpHeaders headers, String reqUrl, String body) {
        ResponseEntity<String> result = restTemplate.exchange(reqUrl, HttpMethod.POST, new HttpEntity<>(body, headers), String.class);
        return result;
    }

    public ResponseEntity<String> doPost(HttpHeaders headers, Map<String, Object> formData, String reqUrl) {
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        ResponseEntity<String> result = restTemplate.exchange(reqUrl, HttpMethod.POST, new HttpEntity<Object>(formData, headers), String.class);
        return result;
    }
    /*private HttpHeaders buildHeaders(){
        HttpHeaders headers =  new HttpHeaders();
        //headers.add("access-token", getGwToken());
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }*/
}
