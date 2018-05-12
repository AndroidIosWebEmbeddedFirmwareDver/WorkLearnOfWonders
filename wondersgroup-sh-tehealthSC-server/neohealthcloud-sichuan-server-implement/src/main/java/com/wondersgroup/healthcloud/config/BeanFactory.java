package com.wondersgroup.healthcloud.config;

import com.google.common.collect.ImmutableMap;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Dispatcher;
import com.squareup.okhttp.OkHttpClient;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.healthcloud.services.wonderCloud.HttpWdUtils;
import com.wondersgroup.healthcloud.utils.sms.SMS;
import com.wondersgroup.healthcloud.utils.sms.SMSSCWondersImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p/>
 * Created by zhangzhixiu on 16/3/6.
 */
@Configuration
public class BeanFactory {

    /*@Bean
    @Profile({"de", "te"})
    public SMS sms(HttpRequestExecutorManager httpRequestExecutorManager) {
        SMSWondersImpl impl = new SMSWondersImpl("g57imnqWS2bzapbuGfYhZmMlmG3i5L", "http://10.1.67.253:8080", "jkja");
        impl.setHttpManager(httpRequestExecutorManager);
        return impl;
    }

    @Bean
    @Profile({"re", "pe1"})
    public SMS prodsms(HttpRequestExecutorManager httpRequestExecutorManager) {
        SMSWondersImpl impl = new SMSWondersImpl("MUFpJNT8WsbUZizlhVHF2Wvh84qz9J", "http://172.18.11.164:8080", "jky");
        impl.setHttpManager(httpRequestExecutorManager);
        return impl;
    }*/
    // ====四川微健康 begin====
    @Bean(name = "sms")
    @Profile({"de", "te"})
    public SMS sms(HttpRequestExecutorManager httpRequestExecutorManager) {
        SMSSCWondersImpl impl = new SMSSCWondersImpl("https://sdk2.028lk.com/sdk2/BatchSend2.aspx", "CDJS001187", "zm0513@");
        impl.setHttpManager(httpRequestExecutorManager);
        return impl;
    }

    @Bean(name = "sms")
    @Profile({"re", "pe1"})
    public SMS prodsms(HttpRequestExecutorManager httpRequestExecutorManager) {
        SMSSCWondersImpl impl = new SMSSCWondersImpl("https://sdk2.028lk.com/sdk2/BatchSend2.aspx", "CDJS001187", "zm0513@");
        impl.setHttpManager(httpRequestExecutorManager);
        return impl;
    }
    // ====四川微健康 end====

    // ====健康双流 begin====
    @Bean(name = "smssl")
    @Profile({"de", "te"})
    public SMS smssl(HttpRequestExecutorManager httpRequestExecutorManager) {
        SMSSCWondersImpl impl = new SMSSCWondersImpl("https://sdk2.028lk.com/sdk2/BatchSend2.aspx", "WONDERS001680", "wonders2017@");
        impl.setHttpManager(httpRequestExecutorManager);
        return impl;
    }

    @Bean(name = "smssl")
    @Profile({"re", "pe1"})
    public SMS prodsmssl(HttpRequestExecutorManager httpRequestExecutorManager) {
        SMSSCWondersImpl impl = new SMSSCWondersImpl("https://sdk2.028lk.com/sdk2/BatchSend2.aspx", "WONDERS001680", "wonders2017@");
        impl.setHttpManager(httpRequestExecutorManager);
        return impl;
    }
    // ====健康双流====

    @Bean
    public OkHttpClient httpClient() {
        OkHttpClient client = new OkHttpClient();
        ConnectionPool pool = new ConnectionPool(2000, 5 * 60 * 1000);
        client.setConnectionPool(pool);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(64);
        dispatcher.setMaxRequestsPerHost(16);
        client.setDispatcher(dispatcher);
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);

        return client;
    }

    @Bean
    public HttpRequestExecutorManager httpRequestExecutorManager(OkHttpClient client) {
        return new HttpRequestExecutorManager(client);
    }

    @Bean
    @Profile({"de", "te"})
    public HttpWdUtils httpWdUtils(HttpRequestExecutorManager manager) {
        HttpWdUtils httpWdUtils = new HttpWdUtils();
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("octopusSid", "C6B18542F8E0000118BD1E2A1C001D9E");//测试环境服务编号
        builder.put("registeApiId", "83026f57-0298-43f7-95ed-fbbc11637d72");//注册
        builder.put("checkAccountApiId", "695483696-2c25-435a-9c84-487b92723f55");//检验账号
        builder.put("loginApiId", "3eced8c8-abc8-4925-b4be-9692a487d1cd");//根据账号密码登录
        builder.put("baseInfoApiId", "b182587e-86c5-4fd5-a6ae-da64749dc60c");//获取账号基本信息
        builder.put("updatepasswordApiId", "dc2d99db-0359-4c06-a5ee-0fb1cb4f35f3");//修改密码
        builder.put("resetpasswordApiId", "4e6191c2-34c5-416f-abf6-add9d975763c5");//重置密码
        builder.put("sendCodeApiId", "efc498db-ef41-435a-9982-b182587e6cc40");//发送验证码
        builder.put("verifyCodeApiId", "3eced8c8-abc8-4925-b4be-9692b182587e");//校验验证码

        builder.put("thirdPartyBindingApiId", "d61e2f6c-35a8-4184-bec6-22a3eaa52c6d");//获取第三方用户信息
        builder.put("updateMobileApiId", "6b45fb5d-d919-45e8-8060-a8896660e060");//修改手机号
        builder.put("updateUsernameApiId", "1f46c897-2ebd-44e4-88e2-0a7caf57efc5");//修改用户名
        builder.put("wechatbindApiId", "1e65ddff-3a33-4a6b-995c-9255e5cdc5e7");//三方微信绑定接口
        builder.put("guestLoginApiId", "95f4e21a-7d91-4273-89c2-47a8fffaaa00");//匿名登录
        builder.put("fastLoginApiId", "79eef5e4-66b7-4cf6-9330-aae20d72a2a5");//快速登录
        builder.put("getSessionApiId", "5ffb2678-8657-499f-9c7e-b93d9d8d0fea");//查询session
        builder.put("logoutApiId", "958af1d5-7e78-4c96-8591-90d04cb1cd4d");//退出
        builder.put("weiboLoginApiId", "83083fa5-d3d4-4c88-a0b5-4b7618ee610e");//三方微博绑定接口
        builder.put("qqLoginApiId", "483629fa-00cf-4a68-9f53-2ef611c61f24");//三方QQ绑定接口
        builder.put("verificationSubmitApiId", "42323843-b724-42b3-bba1-f4f0ea2717e8");//实名信息提交

        builder.put("verficationSubmitInfoApiId", "11fe1cae-205b-4762-bd6b-af6deeac399d");//获取提交实名制审核用户状态信息
        builder.put("sessionExtraApiId", "e3b91188-1212-43bc-8e3b-da605aa3a957");//扩展session自定义字段

        builder.put("smyLoginApiId", "44304602-abf1-44b7-8a46-4fc9cee814e1");//三方市民云绑定接口
        builder.put("verificationChildSubmitApiId", "4c118096-b3ed-4b06-bb4e-18b3547a8974");//儿童实名信息提交

        builder.put("guangzhouLoginApiId", "2a7bc88c-a88a-4452-b80b-a2166c464520");//广州登录接口

        httpWdUtils.setIdMap(builder.build());
        httpWdUtils.setAppToken("59b30cbd-7f39-4fa7-8fda-17acabb74d86");//健康云token 用户端
        httpWdUtils.setOctopusSid("C6B18542F8E0000118BD1E2A1C001D9E");
        httpWdUtils.setUrl("http://10.1.65.106:82/webopenapi/toremotecustom");
        httpWdUtils.setHttpRequestExecutorManager(manager);
        return httpWdUtils;
    }

    @Bean
    @Profile({"re", "pe1"})
    public HttpWdUtils httpWdUtilsProduction() {
        HttpWdUtils httpWdUtils = new HttpWdUtils();
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        builder.put("registeApiId", "6357148a-43e9-45b0-81f1-f1b6a141ba2e");//注册
        builder.put("checkAccountApiId", "853e3e28-feb5-46fa-8387-7f395d27621b");//检验账号
        builder.put("loginApiId", "76c89e09-86d8-4cd5-aafa-af96bf1dca5f");//根据账号密码登录
        builder.put("baseInfoApiId", "53086fc7-7789-4b5d-969d-2f2452ee0cde");//获取账号基本信息
        builder.put("updatepasswordApiId", "cdcd6b4e-f348-462f-bf5f-4f1d48a34a1e");//修改密码
        builder.put("resetpasswordApiId", "a412c377-98ab-4bba-aad7-5d77973fe515");//重置密码
        builder.put("sendCodeApiId", "521d23b9-c68e-40ff-a587-0e1859583224");//发送验证码
        builder.put("verifyCodeApiId", "ecc6ce4c-8eec-4bcd-aa39-938f568d47dc");//校验验证码

        builder.put("thirdPartyBindingApiId", "8abb01df-ea23-486c-94de-d0ecb3ae314e");//获取第三方用户信息
        builder.put("updateMobileApiId", "e95a7895-9c55-49b2-b1d5-297526a9adf1");//修改手机号
        builder.put("updateUsernameApiId", "d0ea70dd-5387-4966-8783-2dafd3549e4d");//修改用户名
        builder.put("wechatbindApiId", "62d31dc5-2f76-4d2a-8ff9-c9e6edfe331c");//三方微信绑定接口
        builder.put("guestLoginApiId", "163e0749-d201-4f75-8d5a-641c6b8cc17a");//匿名登录
        builder.put("fastLoginApiId", "97ca35bb-0003-48e3-af09-a1666eda213d");//快速登录
        builder.put("getSessionApiId", "7f8afb9c-f2e8-4f86-bb61-14bfe1c94428");//查询session
        builder.put("logoutApiId", "529341a1-7abf-4907-9a47-508dab2dfe7e");//退出
        builder.put("weiboLoginApiId", "df738705-4011-4c03-8683-815179cb276a");//三方微博绑定接口
        builder.put("qqLoginApiId", "fdee2b5d-1c48-4761-96b2-df9689813e4d");//三方QQ绑定接口
        builder.put("verificationSubmitApiId", "778e0a9b-f0db-41d3-9e5c-6900da277ab1");//实名信息提交

        builder.put("verficationSubmitInfoApiId", "a1954c2c-f6bd-4be2-9f41-604abfba02a6");//获取提交实名制审核用户状态信息
        builder.put("sessionExtraApiId", "edb73abf-987a-4055-9e1f-19408468cc73");//扩展session自定义字段

        builder.put("smyLoginApiId", "7be12461-fc5e-4ddb-8940-7da3799ff5aa");//三方市民云绑定接口
        builder.put("verificationChildSubmitApiId", "ae83b372-317b-4482-808b-cd3fe3559634");//儿童实名信息提交

        builder.put("guangzhouLoginApiId", "59676d23-7458-4727-b8ad-5061bd6ae573");//广州登录接口

        httpWdUtils.setIdMap(builder.build());
        httpWdUtils.setAppToken("bc2b8bfd-b935-4dc9-8bff-6919bd1aff64");
        httpWdUtils.setOctopusSid("7FF8EB26-AE1F-452F-AC24-6BC61BB57433");
        httpWdUtils.setUrl("http://clientgateway.huidao168.com/webopenapi/toremotecustom");
        httpWdUtils.setHttpRequestExecutorManager(new HttpRequestExecutorManager(new OkHttpClient()));
        return httpWdUtils;
    }
}
