package com.wonders.health.venus.open.user.entity;

import java.io.Serializable;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/11/18 16:56
 */

public class PayResult implements Serializable{
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILED = 1;
    public static final int CODE_TIMEOUT = 2;
    public int code;
    public String msg;
    public String price;
    public String orderId;

    public PayResult(int code, String msg, String price) {
        this.code = code;
        this.msg = msg;
        this.price = price;
    }

    public PayResult() {
    }
}
