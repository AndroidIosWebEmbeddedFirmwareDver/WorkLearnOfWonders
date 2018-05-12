package com.wondersgroup.healthSC.jobclient.startup;

import com.github.ltsopensource.spring.boot.annotation.EnableJobClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhuchunliu on 2016/11/01.
 * @author zhuchunliu
 */
@EnableAutoConfiguration
@Configuration
@EnableJobClient
@ComponentScan(value = "com.wondersgroup.healthSC")
public class JobClientStartUp {

    public static void main(String[] args){
        SpringApplication.run(JobClientStartUp.class);
    }
}
