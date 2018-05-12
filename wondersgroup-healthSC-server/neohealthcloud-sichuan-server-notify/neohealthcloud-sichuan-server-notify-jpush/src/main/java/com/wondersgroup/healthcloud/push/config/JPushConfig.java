package com.wondersgroup.healthcloud.push.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.OkHttpClient;
import com.wondersgroup.healthcloud.push.JPush.*;
import com.wondersgroup.healthcloud.push.config.annotation.EnableJPush;
import com.wondersgroup.healthcloud.push.mongo.MongoTemplates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * 极光推送 配置
 * Created by jialing.yao on 2017-5-9.
 */
@Configuration
@ConditionalOnBean(annotation = EnableJPush.class)
public class JPushConfig {
    @Value("${push.jpush.url}")
    private String jpushUrl;
    @Value("${push.jpush.appkey}")
    private String appKey;
    @Value("${push.jpush.masterSecret}")
    private String masterSecret;

    @Bean
    public OkHttpClient httpClient() {
        OkHttpClient client = new OkHttpClient();
        ConnectionPool pool = new ConnectionPool(200, 5 * 60 * 1000);
        client.setConnectionPool(pool);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(200);
        dispatcher.setMaxRequestsPerHost(20);
        client.setDispatcher(dispatcher);
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
        return client;
    }

    //推送后，日志输出到本地
    @Bean(name = "jPushCallback")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "push.jpush", name = "callbackType", havingValue = "default", matchIfMissing = true)
    public PushCallback jpushDefaultCallback() {
        return new JPushDefaultCallback(appKey);
    }

    //推送后，日志输出到mongo
    @Bean(name = "jPushCallback")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "push.jpush", name = "callbackType", havingValue = "mongo", matchIfMissing = false)
    public PushCallback jpushMongoCallback() {
        return new JPushMongoCallback(appKey);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "push.jpush", name = "callbackType", havingValue = "mongo", matchIfMissing = false)
    class MongoConfig {
        @Value("${spring.data.mongodb.uri}")
        private String mongoClientURI;
        private String dbName;

        @Bean
        public MongoClient mongoClient() {
            MongoClientURI uri = new MongoClientURI(mongoClientURI);
            this.dbName = uri.getDatabase();
            MongoClient mongoClient = new MongoClient(uri);
            return mongoClient;
        }

        @Bean(name = "mongoTpl")
        public MongoTemplates mongoTemplate(MongoClient mongoClient) {
            MongoTemplates mongoTemplate = new MongoTemplates(mongoClient, dbName);
            return mongoTemplate;
        }
    }


    //加入失败重试机制
    @Configuration
    @ConditionalOnProperty(prefix = "push.jpush.retry", name = {"times", "delay"}, matchIfMissing = false)
    @EnableRetry
    public class RetryConfig {
        @Value("${push.jpush.retry.delay}")
        private Long delayExpression;

        @Value("${push.jpush.retry.times}")
        private int maxAttempts;

        @Bean
        @ConditionalOnMissingBean(name = "retryInterceptor")
        public RetryOperationsInterceptor retryInterceptor() {
            return RetryInterceptorBuilder
                    .stateless()
                    .backOffOptions(0L, 1D, delayExpression)
                    .maxAttempts(maxAttempts).build();
        }
    }

    @Bean
    public JPushService jpushService(OkHttpClient client, PushCallback jPushCallback) {
        JPushServiceHttpImpl service = new JPushServiceHttpImpl();
        service.setJpushUrl(jpushUrl);
        service.buildAuthString(appKey, masterSecret);
        service.setClient(client);
        //service.setCallback(new JPushDefaultCallback(appKey));
        service.setCallback(jPushCallback);
        return service;
    }
}
