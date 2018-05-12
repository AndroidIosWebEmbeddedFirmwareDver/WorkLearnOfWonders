package com.wondersgroup.hs.healthcloud.common.entity;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/18 15:44
 */
public class BaseResponse {
    // 接口中没有带token信息
    public static final int CODE_TOKEN_EMPTY = 10;
    //在新设备登录, 原设备的session失效(不删除), 使用原token访问api抛出13错误
    // (此时服务端删除原session, 再次用原token访问则抛出12错误)
    public static final int CODE_TOKEN_EXPIRED = 12;
    public static final int CODE_TOKEN_NOT_EXIST = 13;
    public static final int CODE_SIGN_ERROR = 14;
    // 客户端时间错误
    public static final int CODE_WRONG_TIME = 16;
    public static String sTimeDiff;
    public int code;

    public String msg;

    public Object data;

    public String time_diff;

    public boolean isSuccess() {
        return code == 0;
    }

    public boolean isTokenExpired() {
        return code == CODE_TOKEN_EXPIRED || code == CODE_TOKEN_NOT_EXIST;
    }

    @Override
    public String toString() {
        return "code:" + code + ",msg:" + msg + ",data:" + data;
    }
}
