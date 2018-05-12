package com.wondersgroup.healthcloud.services.sign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by jialing.yao on 2017-6-6.
 */
@Component
public class SignApiClient {
    @Value("${sign.api.url:http://www.av.com/aaaa}")
    private String API_URL;
    @Autowired
    public RestTemplate restTemplate;

    //根据医生手机号获取医生信息
    public String getDoctorInfoByPhone(Map<String, Object> input) {
        String reqUrl = API_URL + "/loginByPhone?phone={phone}";
        return doGet(reqUrl, input);
    }

    private String doGet(String reqUrl, Map<String, Object> input) {
        ResponseEntity<String> result = restTemplate.exchange(reqUrl, HttpMethod.GET, new HttpEntity<>(buildHeaders()), String.class, input);
        return result.getBody();
    }

    private String doPost(String reqUrl, String body) {
        HttpHeaders headers = buildHeaders();
        /*headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("strRequest", body);*/
        ResponseEntity<String> result = restTemplate.exchange(reqUrl, HttpMethod.POST, new HttpEntity<Object>(body, headers), String.class);
        return result.getBody();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        //headers.add("access-token", getGwToken());
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
    /*private String convertToJsonIfXml(ResponseEntity<String> result){
        HttpHeaders headers = result.getHeaders();
        MediaType mediaType=headers.getContentType();
        String body=result.getBody();
        if(mediaType.equals(MediaType.TEXT_XML) || mediaType.getSubtype().equals("xml")){
            if(StringUtils.isNotBlank(body)){
                body=body.substring(body.indexOf("{"),body.lastIndexOf("}")+1);
            }
        }
        return body;
    }*/
}
