package com.wondersgroup.healthcloud.services.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.JsonNodeResponseWrapper;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
import com.wondersgroup.common.http.utils.JsonConverter;
import com.wondersgroup.healthcloud.services.account.exception.InvalidQQTokenException;
import com.wondersgroup.healthcloud.services.account.exception.InvalidWechatTokenException;
import com.wondersgroup.healthcloud.services.account.exception.InvalidWeiboTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
 * Created by zhangzhixiu on 16/3/4.
 */
@Component
public class ThirdPartyHttpUtil {

    @Autowired
    private HttpRequestExecutorManager manager;

    public static class User {
        public String id;
        public String nickname;
        public String gender;
        public String avatar;
    }

    public User wechatUser(String token, String openid) {
        Request request = new RequestBuilder().get().url("https://api.weixin.qq.com/sns/userinfo").params(new String[]{"access_token", token, "openid", openid}).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode node = wrapper.convertBody();
        if (node.has("errcode")) {
            throw new InvalidWechatTokenException();
        } else {
            User user = new User();
            user.id = node.get("unionid").asText();
            user.nickname = node.has("nickname") ? node.get("nickname").asText() : null;
            user.gender = node.has("gender") ? String.valueOf(node.get("gender").asInt()) : null;
            user.avatar = node.has("headimgurl") ? node.get("headimgurl").asText() : null;
            return user;
        }
    }

    public User weiboUser(String token) {
        String weiboUid = getWeiboIdBy(token);
        Request request = new RequestBuilder().get().url("https://api.weibo.com/2/users/show.json").params(new String[]{"access_token", token, "uid", weiboUid}).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode node = wrapper.convertBody();
        if (node.has("error")) {
            throw new InvalidWeiboTokenException();
        } else {
            User user = new User();
            user.id = node.get("id").asText();
            user.nickname = node.has("name") ? node.get("name").asText() : null;
            user.gender = weiboGenderConvert(node.has("gender") ? node.get("gender").asText() : null);
            user.avatar = node.has("avatar_hd") ? node.get("avatar_hd").asText() : null;
            return user;
        }
    }

    private String getWeiboIdBy(String token) {
        Request request = new RequestBuilder().post().url("https://api.weibo.com/oauth2/get_token_info").params(new String[]{"access_token", token}).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode node = wrapper.convertBody();
        if (node.has("error")) {
            throw new InvalidWeiboTokenException();
        } else {
            return node.get("uid").asText();
        }
    }

    private static String weiboGenderConvert(String gender) {
        if ("m".equals(gender)) {
            return "1";
        } else if ("f".equals(gender)) {
            return "2";
        } else {
            return null;
        }
    }

    public User qqUser(String token) {
        String openid = getQQOpenidBy(token);
        Request request = new RequestBuilder().get().url("https://graph.qq.com/user/get_user_info").params(new String[]{"access_token", token, "openid", openid, "oauth_consumer_key", "1105235517",
                "format", "json"}).build();
        JsonNodeResponseWrapper wrapper = (JsonNodeResponseWrapper) manager.newCall(request).run().as(JsonNodeResponseWrapper.class);
        JsonNode node = wrapper.convertBody();
        if (node.get("ret").asInt() != 0) {
            throw new InvalidQQTokenException();
        } else {
            User user = new User();
            user.id = openid;
            user.avatar = node.has("figureurl_qq_1") ? node.get("figureurl_qq_1").asText() : null;
            user.nickname = node.has("nickname") ? node.get("nickname").asText() : null;
            user.gender = qqGenderConverter(node.has("gender") ? node.get("gender").asText() : null);
            return user;
        }
    }

    private String getQQOpenidBy(String token) {
        Request request = new RequestBuilder().get().url("https://graph.qq.com/oauth2.0/me").params(new String[]{"access_token", token}).build();
        StringResponseWrapper wrapper = (StringResponseWrapper) manager.newCall(request).run().as(StringResponseWrapper.class);
        String body = wrapper.body();
        int startIndex = body.indexOf("{");
        int endIndex = body.indexOf("}");
        JsonNode node = JsonConverter.toJsonNode(body.substring(startIndex, endIndex + 1));
        if (node.has("error")) {
            throw new InvalidQQTokenException();
        } else {
            return node.get("openid").asText();
        }
    }

    private static String qqGenderConverter(String gender) {
        if ("男".equals(gender)) {
            return "1";
        } else {
            return "2";
        }
    }
}
