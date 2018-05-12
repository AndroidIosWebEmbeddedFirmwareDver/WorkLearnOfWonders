package com.wondersgroup.healthSC.tasktracker.config;

import com.wondersgroup.healthSC.common.util.UploaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhuchunliu on 2016/11/4.
 */
@Configuration
public class BeanConfig {

    @Autowired
    private Environment environment;

    @Bean
    public UploaderUtil getUploaderUtil(){
        return new UploaderUtil(
                environment.getProperty("qiniu.access_key"),
                environment.getProperty("qiniu.secret_key"),
                environment.getProperty("qiniu.bucket"),
                environment.getProperty("qiniu.domain"));
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
