package com.wondersgroup.healthSC.services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

/**
 * Created by ys on 17-4-26.
 *
 */
@Configuration
public class MailConfig {

    @Autowired
    private Environment env;

    //医院报表发送定制mailSender
    @Bean(name = "mailSender")
    public JavaMailSender mailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(env.getProperty("spring.mail.username"));
        javaMailSender.setHost(env.getProperty("spring.mail.host"));
        javaMailSender.setPassword(env.getProperty("spring.mail.password"));
        javaMailSender.setPort(env.getProperty("spring.mail.port", Integer.class, 587));
        javaMailSender.setDefaultEncoding("UTF-8");
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", env.getProperty("spring.mail.host"));
        props.setProperty("mail.smtp.auth", "true");
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(env.getProperty("spring.mail.username"), env.getProperty("spring.mail.password"));
            }
        });
        javaMailSender.setSession(session);
        return javaMailSender;
    }


}
