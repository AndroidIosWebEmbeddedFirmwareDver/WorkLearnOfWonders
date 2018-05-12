package com.wonders.health.venus.open.user.entity;

/**
 * 类描述：消息
 * 创建人：Bob
 * 创建时间：2015/7/12 17:17
 */
public class LoginInfo {
    public String uid;          // 用户唯一标识
    public String token;        // 用户有效性验证码
    public String key;          // 加密参数
    public boolean first_login;  // 是否第一次使用

    public User info;

    public User getUser() {
        User user = info;
        if (user == null) {
            user = new User();
        }
        user.uid = uid;
        user.token = token;
        user.key = key;
        user.first_login = first_login;
        return user;
    }
}
