package com.wondersgroup.healthSC.tasktracker.startup;

import com.github.ltsopensource.spring.boot.annotation.EnableTaskTracker;
import com.wondersgroup.healthSC.tasktracker.config.BeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * jobtracker init
 *
 * Created by zhuchunliu on 2016/11/01.
 * @author zhuchunliu
 */
@SpringBootApplication(scanBasePackages = "com.wondersgroup.healthSC")
@EnableTaskTracker
public class TaskTrackerStartUp {

    public static void main(String[] args){
        SpringApplication.run(TaskTrackerStartUp.class, args);
    }
}
