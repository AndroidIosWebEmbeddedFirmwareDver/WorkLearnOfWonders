package com.wondersgroup.healthcloud.im.easemob.config;

import com.wondersgroup.healthcloud.im.easemob.users.EasemobUserService;
import com.wondersgroup.healthcloud.im.sdk.common.ApiHttpClient;
import com.wondersgroup.healthcloud.im.sdk.easemob.EasemobToken;
import com.wondersgroup.healthcloud.im.sdk.easemob.EasemobUsers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jialing.yao on 2017-5-31.
 */
@Configuration
@EnableConfigurationProperties({EasemobProperties.class})
public class EasemobAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
            } else {
                messageConverters.add(converter);
            }
        }
        restTemplate.setMessageConverters(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiHttpClient apiHttpClient(RestTemplate restTemplate) {
        ApiHttpClient apiHttpClient = new ApiHttpClient(restTemplate);
        return apiHttpClient;
    }

    @Bean
    @ConditionalOnMissingBean
    public EasemobToken easemobToken(EasemobProperties easemobProperties, ApiHttpClient apiHttpClient) {
        String apiUrl = easemobProperties.getUrl();
        String grantType = easemobProperties.getGrantType();
        String clientID = easemobProperties.getClientID();
        String clientSecret = easemobProperties.getClientSecret();
        EasemobToken easemobToken = new EasemobToken(apiUrl, apiHttpClient);
        return easemobToken.buildToken(grantType, clientID, clientSecret);
    }

    @Bean
    @ConditionalOnMissingBean
    public EasemobUsers easemobUsers(EasemobProperties easemobProperties, ApiHttpClient apiHttpClient, EasemobToken easemobToken) {
        String apiUrl = easemobProperties.getUrl();
        EasemobUsers easemobUsers = new EasemobUsers(apiUrl, apiHttpClient, easemobToken);
        return easemobUsers;
    }

    @Bean
    @ConditionalOnMissingBean
    public EasemobUserService easemobUserService(EasemobUsers easemobUsers) {
        EasemobUserService easemobUserService = new EasemobUserService(easemobUsers);
        return easemobUserService;
    }
}
