package com.wondersgroup.healthcloud.services.wonderCloud;

/**
 * Created by longshasha on 16/5/11.
 */
public enum AccessTokenStatus {
    TOKEN_OK(0, "OK"),
    TOKEN_MISSING(10, "缺少令牌"),
    TOKEN_EXPIRED(11, "令牌过期"),
    TOKEN_INVALID(12, "无效令牌"),
    TOKEN_CONFLICT(13, "账户在其他设备登录, 令牌无效");


    private final int code;
    private final String description;

    AccessTokenStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
