package com.wondersgroup.healthSC.services.config;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.OkHttpClient;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.healthSC.services.impl.SMSSCWondersImpl;
import com.wondersgroup.healthSC.services.interfaces.SMS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

/**
 * Created by nick on 2016/11/7.
 */
@Configuration
public class BeanFactory {

    @Bean
    public OkHttpClient httpClient() {
        OkHttpClient client = new OkHttpClient();
        ConnectionPool pool = new ConnectionPool(2000, 5 * 60 * 1000);
        client.setConnectionPool(pool);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(64);
        dispatcher.setMaxRequestsPerHost(16);
        client.setDispatcher(dispatcher);
        client.setConnectTimeout(20, TimeUnit.SECONDS);
        client.setWriteTimeout(20, TimeUnit.SECONDS);
        client.setReadTimeout(20, TimeUnit.SECONDS);
        return client;
    }

    @Bean
    public HttpRequestExecutorManager httpRequestExecutorManager(OkHttpClient client) {
        return new HttpRequestExecutorManager(client);
    }

    @Bean
    @Profile({"de", "te"})
    public SMS sms(HttpRequestExecutorManager httpRequestExecutorManager) {
        SMSSCWondersImpl impl = new SMSSCWondersImpl("https://sdk2.028lk.com/sdk2/BatchSend2.aspx", "CDJS001187", "zm0513@");
        impl.setHttpManager(httpRequestExecutorManager);
        return impl;
    }

    @Bean
    @Profile({"re", "pe1"})
    public SMS prodsms(HttpRequestExecutorManager httpRequestExecutorManager) {
        SMSSCWondersImpl impl = new SMSSCWondersImpl("https://sdk2.028lk.com/sdk2/BatchSend2.aspx", "CDJS001187", "zm0513@");
        impl.setHttpManager(httpRequestExecutorManager);
        return impl;
    }
}
