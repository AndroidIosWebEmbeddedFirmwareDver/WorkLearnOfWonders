package com.wondersgroup.healthSC.jobtracker;


import com.github.ltsopensource.spring.boot.annotation.EnableJobTracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * job tracker init
 *
 * Created by zhuchunliu on 2016/11/01.
 * @author zhuchunliu
 */
@SpringBootApplication
@EnableJobTracker
@ComponentScan("com.wondersgroup.healthSC")
public class JobTrackerStartup {

    public static void main(String[] args){
        SpringApplication.run(JobTrackerStartup.class, args);
    }
}
