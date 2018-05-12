package com.wondersgroup.healthcloud.services.wonderCloud;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.*;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.JsonNodeResponseWrapper;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by longshasha on 16/5/10.
 */
public class HttpWdUtils {
    private String url = "http://10.1.65.106:82/webopenapi/toremotecustom";//测试环境
    private String appToken = "59b30cbd-7f39-4fa7-8fda-17acabb74d86";//测试环境
    private String octopusSid = "C6B18542F8E0000118BD1E2A1C001D9E";//测试环境


    public static final String publicKey =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDGHjtLwTJP9ehWYM3Dmwg9eTX3gDAFwQMyL1edXKPOjyUucWml7O8VF8adQgLH8fM1PoZSKHGliE0rZ3q6o1jh4lkF1CLIqWRbZ4ObKM2i1w5O2VP9lMKyWTrRM/R9RWxCgwINb" +
                    "/QQmbmNLTVruh4YG1Q0QTK2dQLnIh0oANdpwIDAQAB";
    private static final String channelid = "d92618bc-ab52-4e03-989f-6f764f26a12b";
    private static final String appkey = "d9dbdfb2-bcf5-423c-b80a-2d51e8bf5d58";


    private HttpRequestExecutorManager httpRequestExecutorManager;

    public void setHttpRequestExecutorManager(HttpRequestExecutorManager httpRequestExecutorManager) {
        this.httpRequestExecutorManager = httpRequestExecutorManager;
    }

    private Map<String, String> idMap;

    public void setIdMap(Map<String, String> idMap) {
        this.idMap = idMap;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public void setOctopusSid(String octopusSid) {
        this.octopusSid = octopusSid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    /**
     * 1.1账号注册 根据手机号注册
     *
     * @param mobile
     * @param password
     * @return httpWdUtils.registe(" 15639763509 ", " 123456 ");
     * {"tagid":"0231000001","userid":"ff808081546052c1015499d295660017","code":201,"msg":"注册成功","success":true,"session_token":"2982d4032f7248f984cf638b7e975217"}
     */
    public JsonNode registe(String mobile, String password) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("registeApiId")};
        String[] form = new String[]{"mobile", mobile, "password", password, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    public JsonNode registeByUsername(String username, String password) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("registeApiId")};
        String[] form = new String[]{"username", username, "password", password, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.2 检验账号是否可用 account可为手机号、用户名、邮箱
     *
     * @param account
     * @return {"code":214,"success":true,"msg":"手机可使用"}
     * {"code":413,"msg":"手机已被使用","success":false}
     */
    public JsonNode checkAccount(String account) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("checkAccountApiId")};
        String[] form = new String[]{"account", account, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.3 根据账号密码登录
     *
     * @param account
     * @param password
     * @return {"code":202,"user":{"userid":"3a24cb5b173e4d6597f62a95d04e3340","username":"jky15639763552",
     * "mobile":"15639763552","email":null,"type":"healthcloud","name":null,"idcard":null,"sscard":null,
     * "tagid":"0051000012","sessionToken":"ab831db309e442699072a20032443195","realvalid":0},"msg":"登录成功",
     * "success":true,"session_token":"ab831db309e442699072a20032443195"}
     */
    public JsonNode login(String account, String password) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("loginApiId")};
        String[] form = new String[]{"account", account, "password", password, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.4 获取用户信息(根据手机、用户名、邮箱)
     * account : 15639763552
     *
     * @return
     */
    public JsonNode basicInfo(String account) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("baseInfoApiId")};
        String[] form = new String[]{"account", account, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.5 修改密码
     *
     * @param account
     * @param oldPsd
     * @param newPsd
     * @return {"code":404,"message":"Api not found"}
     */
    public JsonNode updatePassword(String account, String oldPsd, String newPsd) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("updatepasswordApiId")};
        String[] form = new String[]{"account", account, "oldpsd", oldPsd, "newpsd", newPsd, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.6 重置密码
     *
     * @param account
     * @param newPsd
     * @return
     */
    public JsonNode resetPassword(String account, String newPsd) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("resetpasswordApiId")};
        String[] form = new String[]{"account", account, "newpsd", newPsd, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.8 发送短信验证码
     *
     * @param mobile  手机号
     * @param message 短信内容格式，以":code"作为验证码的占位符
     * @return
     */
    public JsonNode sendCode(String mobile, String message) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("sendCodeApiId")};
        String[] form = new String[]{"mobile", mobile, "message", message, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.9 校验验证码
     *
     * @param mobile
     * @param code    验证码
     * @param onlyOne 验证码是否具有一次性
     * @return
     */
    public JsonNode verifyCode(String mobile, String code, Boolean onlyOne) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("verifyCodeApiId")};
        String[] form = new String[]{"mobile", mobile, "code", code, "onlyOne", String.valueOf(onlyOne), "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.14 获取第三方用户信息
     *
     * @param userId
     * @return
     */
    public JsonNode thirdPartyBinding(String userId) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("thirdPartyBindingApiId")};
        String[] form = new String[]{"userid", userId, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.15修改手机号
     *
     * @param account 手机号、邮箱、用户名、userId
     * @param mobile
     * @return
     */
    public JsonNode updateMobile(String account, String mobile) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("updateMobileApiId")};
        String[] form = new String[]{"account", account, "mobile", mobile, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.16 修改用户名
     *
     * @param account
     * @param username 首字母开头，字母和数字组合，长度6-40位
     *                 1.	如果用户不存在用户名，才可以对用户的用户名进行修改
     * @return
     */
    public JsonNode updateUserName(String account, String username) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("updateUsernameApiId")};
        String[] form = new String[]{"account", account, "username", username, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.17 三方微信绑定接口
     *
     * @param wechatToken
     * @param openId
     * @return
     */
    public JsonNode wechatLogin(String wechatToken, String openId) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("wechatbindApiId")};
        String[] form = new String[]{"access_token", wechatToken, "openid", openId, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }


    /**
     * 1.18匿名登录
     *
     * @return
     */
    public JsonNode guestLogin() {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("guestLoginApiId")};
        String[] form = new String[]{"token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);

        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.19快速登录
     *
     * @param mobile
     * @param code
     * @param onceCode 是否验证后失效,默认为“是”
     * @return
     */
    public JsonNode fastLogin(String mobile, String code, Boolean onceCode) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("fastLoginApiId")};
        String[] form = new String[]{"mobile", mobile, "code", code, "isInvalid", String.valueOf(onceCode), "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.20 查询session
     *
     * @param session
     * @return {"data":{"source":"healthcloud","userid":"3a24cb5b173e4d6597f62a95d04e3340","isValid":"true"},"code":221,"msg":"session查询成功","success":true}
     */
    public JsonNode getSession(String session) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("getSessionApiId")};
        String[] form = new String[]{"session_token", session, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.21 退出登录
     *
     * @param account 账号
     * @return
     */
    public JsonNode logout(String account) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("logoutApiId")};
        String[] form = new String[]{"account", account, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }


    /**
     * 1.22 三方微博绑定接口
     *
     * @param weiboToken
     * @return
     */
    public JsonNode weiboLogin(String weiboToken) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("weiboLoginApiId")};
        String[] form = new String[]{"access_token", weiboToken, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.23 三方QQ绑定接口
     *
     * @param qqToken
     * @return
     */
    public JsonNode qqLogin(String qqToken) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("qqLoginApiId")};
        String[] form = new String[]{"access_token", qqToken, "token", appToken};

        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.24 实名认证提交
     *
     * @param userId
     * @param name
     * @param idCard
     * @param contentType
     * @param photo
     * @return
     */
    public JsonNode verificationSubmit(String userId, String name, String idCard, String contentType, byte[] photo) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("verificationSubmitApiId")};

        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);


        String[] form = new String[]{"name", name, "idCardNum", idCard, "userid", userId, "token", appToken};

        for (int i = 0; i < form.length; i += 2) {
            multipartBuilder.addFormDataPart(form[i], form[i + 1]);
        }

        multipartBuilder.addFormDataPart("file", "file", RequestBody.create(MediaType.parse(contentType), photo));

        RequestBody requestBody = multipartBuilder.build();

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        for (int i = 0; i < header.length / 2; ++i) {
            if (header[i * 2 + 1] != null) {
                builder.addHeader(header[i * 2], header[i * 2 + 1]);
            }
        }

        builder.post(requestBody);
        Request request = builder.build();

        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.26	获取提交实名制审核用户状态信息
     *
     * @param userId
     * @return
     */
    public JsonNode verficationSubmitInfo(String userId) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("verficationSubmitInfoApiId")};
        String[] form = new String[]{"account", userId, "token", appToken};
        Request request = new RequestBuilder().get().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    /**
     * 1.27	扩展session自定义字段
     *
     * @param session
     * @param key
     * @return
     */
    public JsonNode addSessionExtra(String session, String key, String userType) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("sessionExtraApiId")};
        String[] form = new String[]{"session_token", session, "key", key, "type", userType, "token", appToken};
        Request request = new RequestBuilder().post().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }


    /**
     * 三方市民云绑定接口
     *
     * @param smyToken
     * @param username 平台用户名
     * @return
     */
    public JsonNode smyLogin(String smyToken, String username) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("smyLoginApiId")};
        String[] form = new String[]{"access_token", smyToken, "username", username, "token", appToken};
        Request request = new RequestBuilder().post().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }


    /**
     * 儿童实名认证提交
     *
     * @param childUserid   儿童云账号用户Id
     * @param parentUserid  监护人云账号Id
     * @param name          儿童真实姓名
     * @param mobile        监护人的手机号
     * @param idcard        儿童身份证号
     * @param contentType
     * @param idCardFile    户口本(儿童身份信息页照片)
     * @param birthCertFile 出生证明(照片)
     * @return
     */
    public JsonNode verificationChildSubmit(String childUserid, String name, String mobile, String idcard, String parentUserid,
                                            String contentType, byte[] idCardFile, byte[] birthCertFile) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("verificationChildSubmitApiId")};

        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);


        String[] form = new String[]{"name", name, "mobile", mobile, "idCardNum", idcard, "childUserid", childUserid,
                "parentUserid", parentUserid, "token", appToken};

        for (int i = 0; i < form.length; i += 2) {
            multipartBuilder.addFormDataPart(form[i], form[i + 1]);
        }

        multipartBuilder.addFormDataPart("idCardFile", "idCardFile", RequestBody.create(MediaType.parse(contentType), idCardFile));
        multipartBuilder.addFormDataPart("birthCertFile", "birthCertFile", RequestBody.create(MediaType.parse(contentType), birthCertFile));

        RequestBody requestBody = multipartBuilder.build();

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        for (int i = 0; i < header.length / 2; ++i) {
            if (header[i * 2 + 1] != null) {
                builder.addHeader(header[i * 2], header[i * 2 + 1]);
            }
        }

        builder.post(requestBody);
        Request request = builder.build();

        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }

    public JsonNode guangzhouLogin(String token) {
        String[] header = new String[]{"octopus_channelid", channelid, "octopus_appkey", appkey,
                "octopus_sid", octopusSid,
                "octopus_apiid", idMap.get("guangzhouLoginApiId")};

        String[] form = new String[]{"access_token", token, "token", appToken};

        Request request = new RequestBuilder().post().url(url).params(form).headers(header).build();
        JsonNodeResponseWrapper response = (JsonNodeResponseWrapper) httpRequestExecutorManager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode result = response.convertBody();
        return result;
    }


    public static void main(String[] args) {
        Map<String, String> idMap = new HashMap<>();
        idMap.put("octopusSid", "C6B18542F8E0000118BD1E2A1C001D9E");//测试环境服务编号
        idMap.put("registeApiId", "83026f57-0298-43f7-95ed-fbbc11637d72");//注册
        idMap.put("checkAccountApiId", "695483696-2c25-435a-9c84-487b92723f55");//检验账号
        idMap.put("loginApiId", "3eced8c8-abc8-4925-b4be-9692a487d1cd");//根据账号密码登录
        idMap.put("baseInfoApiId", "b182587e-86c5-4fd5-a6ae-da64749dc60c");//获取账号基本信息
        idMap.put("updatepasswordApiId", "dc2d99db-0359-4c06-a5ee-0fb1cb4f35f3");//修改密码
        idMap.put("resetpasswordApiId", "4e6191c2-34c5-416f-abf6-add9d975763c5");//重置密码
        idMap.put("sendCodeApiId", "efc498db-ef41-435a-9982-b182587e6cc40");//发送验证码
        idMap.put("verifyCodeApiId", "3eced8c8-abc8-4925-b4be-9692b182587e");//校验验证码

        idMap.put("thirdPartyBindingApiId", "d61e2f6c-35a8-4184-bec6-22a3eaa52c6d");//获取第三方用户信息
        idMap.put("updateMobileApiId", "6b45fb5d-d919-45e8-8060-a8896660e060");//修改手机号
        idMap.put("updateUsernameApiId", "1f46c897-2ebd-44e4-88e2-0a7caf57efc5");//修改用户名
        idMap.put("wechatbindApiId", "1e65ddff-3a33-4a6b-995c-9255e5cdc5e7");//三方微信绑定接口
        idMap.put("guestLoginApiId", "95f4e21a-7d91-4273-89c2-47a8fffaaa00");//匿名登录
        idMap.put("fastLoginApiId", "79eef5e4-66b7-4cf6-9330-aae20d72a2a5");//快速登录
        idMap.put("getSessionApiId", "5ffb2678-8657-499f-9c7e-b93d9d8d0fea");//查询session
        idMap.put("logoutApiId", "958af1d5-7e78-4c96-8591-90d04cb1cd4d");//退出
        idMap.put("weiboLoginApiId", "83083fa5-d3d4-4c88-a0b5-4b7618ee610e");//三方微博绑定接口
        idMap.put("qqLoginApiId", "483629fa-00cf-4a68-9f53-2ef611c61f24");//三方QQ绑定接口
        idMap.put("verificationSubmitApiId", "42323843-b724-42b3-bba1-f4f0ea2717e8");//实名信息提交

        idMap.put("verficationSubmitInfoApiId", "11fe1cae-205b-4762-bd6b-af6deeac399d");//获取提交实名制审核用户状态信息
        idMap.put("sessionExtraApiId", "e3b91188-1212-43bc-8e3b-da605aa3a957");//扩展session自定义字段

        idMap.put("smyLoginApiId", "44304602-abf1-44b7-8a46-4fc9cee814e1");//三方市民云绑定接口
        idMap.put("verificationChildSubmitApiId", "4c118096-b3ed-4b06-bb4e-18b3547a8974");//儿童实名信息提交

        idMap.put("guangzhouLoginApiId", "2a7bc88c-a88a-4452-b80b-a2166c464520");

        HttpWdUtils httpWdUtils = new HttpWdUtils();
        httpWdUtils.setAppToken("59b30cbd-7f39-4fa7-8fda-17acabb74d86");
        httpWdUtils.setOctopusSid("C6B18542F8E0000118BD1E2A1C001D9E");

        //正式
//        httpWdUtils.setAppToken("bc2b8bfd-b935-4dc9-8bff-6919bd1aff64");
//        httpWdUtils.setOctopusSid("7FF8EB26-AE1F-452F-AC24-6BC61BB57433");
//        httpWdUtils.setUrl("http://clientgateway.huidao168.com/webopenapi/toremotecustom");
//        idMap.put("baseInfoApiId", "53086fc7-7789-4b5d-969d-2f2452ee0cde");//获取账号基本信息
//        idMap.put("verficationSubmitInfoApiId", "a1954c2c-f6bd-4be2-9f41-604abfba02a6");//获取提交实名制审核用户状态信息
//        idMap.put("updateMobileApiId", "e95a7895-9c55-49b2-b1d5-297526a9adf1");//修改手机号
//
//        idMap.put("resetpasswordApiId", "a412c377-98ab-4bba-aad7-5d77973fe515");//重置密码
//        idMap.put("smyLoginApiId", "7be12461-fc5e-4ddb-8940-7da3799ff5aa");//三方市民云绑定接口
//        idMap.put("verificationChildSubmitApiId", "ae83b372-317b-4482-808b-cd3fe3559634");//儿童实名信息提交


        httpWdUtils.setIdMap(idMap);

        httpWdUtils.setHttpRequestExecutorManager(new HttpRequestExecutorManager(new OkHttpClient()));

//        httpWdUtils.basicInfo("2c928bb0549fe6850154f1456c82061a");
//        httpWdUtils.basicInfo("18886869999");

//        try {
//            String password = RSAUtil.encryptByPublicKey("123456", publicKey);
//            httpWdUtils.registe("15639763508",password);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        httpWdUtils.checkAccount("15639763508");

        //123456
//        String psd = "Uj95afYI6wedng49hbJXhnqiuRd5EZRtbtE+ZfdvpHwByyA895hrLwC+lRrQoY0r/5enL/9DXBWalIwHKw5IdUqZ3EcxSg/v/fTyZxgapwk4o6OEXbzBZVMbAsNU8F5pidmdPQLqAGbgcJrunUDtxFwymKS+A0SdXkKoPZ5Qdow=";
        //1234567
//        String psd = "cCmAfDziWZbxoKjUGYzCWMXgBHyZ8ilpPFtbrkKAgsen2V2cQ1bqHU0DN79UPoZlXYnQlxo6bRq/elDNQr5Ih4eKp86cU7TxomFAeC4UJIhk9/TDGae8k7qivAkQMypZVpS0ZvQitE4zhq35pD9S0LAfv2/YsqoY/udUtRrNT+w=";
//
//        httpWdUtils.login("15639763552",psd);

        /*//123456
        String oldPsd = "Uj95afYI6wedng49hbJXhnqiuRd5EZRtbtE+ZfdvpHwByyA895hrLwC+lRrQoY0r/5enL/9DXBWalIwHKw5IdUqZ3EcxSg/v/fTyZxgapwk4o6OEXbzBZVMbAsNU8F5pidmdPQLqAGbgcJrunUDtxFwymKS+A0SdXkKoPZ5Qdow=";
        //1234567
        String newPsd = "cCmAfDziWZbxoKjUGYzCWMXgBHyZ8ilpPFtbrkKAgsen2V2cQ1bqHU0DN79UPoZlXYnQlxo6bRq/elDNQr5Ih4eKp86cU7TxomFAeC4UJIhk9/TDGae8k7qivAkQMypZVpS0ZvQitE4zhq35pD9S0LAfv2/YsqoY/udUtRrNT+w=";
        httpWdUtils.updatePassword("15639763509",oldPsd,newPsd);*/

        //1234567
//        String newPsd = "cCmAfDziWZbxoKjUGYzCWMXgBHyZ8ilpPFtbrkKAgsen2V2cQ1bqHU0DN79UPoZlXYnQlxo6bRq/elDNQr5Ih4eKp86cU7TxomFAeC4UJIhk9/TDGae8k7qivAkQMypZVpS0ZvQitE4zhq35pD9S0LAfv2/YsqoY/udUtRrNT
// +w=";

//        try {
//            String password = RSAUtil.encryptByPublicKey("1234567", publicKey);
//            httpWdUtils.resetPassword("15639763552", password);
//        } catch (Exception e) {
//
//        }


        /*String message = "【健康长宁】验证码:code,用于测试。";
        httpWdUtils.sendCode("15639763552",message);*/

//        httpWdUtils.verifyCode("15639763552","354735",false);

//        httpWdUtils.thirdPartyBinding("8a81c1fb572797db01573c37b4080060");

//        httpWdUtils.updateMobile("2c928bb15791e7470157ada2ed8602ff","18918725136");

//        JsonNode jsonNode = httpWdUtils.updateMobile("2c928bb153f19ea10154f145744d1209", "13918912554");


//        httpWdUtils.updateUserName("8a81c1fb5755c5c1015755cc81cb0002","longshaertongceshi");

//        httpWdUtils.guestLogin();
//        httpWdUtils.fastLogin("15639763552","189178",false);
//        {"tagid":"0051000012","userid":"3a24cb5b173e4d6597f62a95d04e3340","code":202,"msg":"登录成功","success":true,"session_token":"f5a280c7315f480c94da78069530b9e3"}

//        httpWdUtils.getSession("f7b0f2e63fad449a920a2abcd96d09c0");
//        httpWdUtils.logout("15639763552");

//        httpWdUtils.verficationSubmitInfo("2c928bb15774ac7101577508b6540036");
//        httpWdUtils.verficationSubmitInfo("ff80808154177829015417bbe1970020");

        //匿名
//        httpWdUtils.verficationSubmitInfo("2c928bb153f19ea101562a176fe52f40");


//        String key = IdGen.uuid();
//        httpWdUtils.addSessionExtra("f5a280c7315f480c94da78069530b9e3",key);
//        {"code":220,"msg":"session自定义数据添加成功","success":true}

//        httpWdUtils.smyLogin("b926db07-7a8e-4101-9807-37bd14e76439","eshimin73762403");//测试
//        httpWdUtils.smyLogin("4e633b91-3c77-41b3-bf1d-d6b8436dc9a3","eshimin16459301");


//        httpWdUtils.guangzhouLogin("56b428b180cd48b49e8dea8be9a33d42");
//        httpWdUtils.guangzhouLogin("TGC-80-W1degYtKg4byg75ybdspgzEaYrapgfZsjlkFSxASmIPGDEdE8t");

    }


}
