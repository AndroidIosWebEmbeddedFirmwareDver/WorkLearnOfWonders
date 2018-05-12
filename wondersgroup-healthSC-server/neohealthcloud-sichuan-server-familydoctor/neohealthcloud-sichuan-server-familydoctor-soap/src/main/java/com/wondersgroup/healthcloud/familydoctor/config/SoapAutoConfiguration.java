package com.wondersgroup.healthcloud.familydoctor.config;

import com.wondersgroup.healthcloud.familydoctor.services.referral.ZhuanzhenImplService;
import com.wondersgroup.healthcloud.familydoctor.services.referral.ZhuanzhenInterface;
import com.wondersgroup.healthcloud.familydoctor.services.sign.QianyueImplService;
import com.wondersgroup.healthcloud.familydoctor.services.sign.QianyueInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jialing.yao on 2017-6-22.
 */
@Configuration
public class SoapAutoConfiguration {
//    @Value("${familydoctor.sign.wsdl.url}")
//    private String SIGN_WSDL_URL;
//    @Value("${familydoctor.referral.wsdl.url}")
//    private String REFERRAL_WSDL_URL;

    @Bean
    @ConditionalOnMissingBean
    public RequestHeadBuilder.RequestHead requestHead() {
        return new RequestHeadBuilder.RequestHead();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestHeadBuilder requestHeadBuilder(RequestHeadBuilder.RequestHead requestHead) {
        return new RequestHeadBuilder(requestHead);//封装请求头
    }

    //签约
    @Bean
    @ConditionalOnMissingBean
    public QianyueInterface signService() throws MalformedURLException {
//        URL wsdlURL = new URL(SIGN_WSDL_URL);
//        QianyueImplService signImplService = new QianyueImplService(wsdlURL);
        QianyueImplService signImplService = new QianyueImplService();
        return signImplService.getQianyueImplPort();
    }

    //转诊
    @Bean
    @ConditionalOnMissingBean
    public ZhuanzhenInterface referralService() throws MalformedURLException {
//        URL wsdlURL = new URL(REFERRAL_WSDL_URL);
//        ZhuanzhenImplService referralImplService = new ZhuanzhenImplService(wsdlURL);
        ZhuanzhenImplService referralImplService = new ZhuanzhenImplService();
        return referralImplService.getZhuanzhenImplPort();
    }
}
