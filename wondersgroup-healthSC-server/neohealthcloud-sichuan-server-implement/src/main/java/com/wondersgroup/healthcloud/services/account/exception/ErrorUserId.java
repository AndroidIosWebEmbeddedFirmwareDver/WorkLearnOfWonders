package com.wondersgroup.healthcloud.services.account.exception;


import com.wondersgroup.healthcloud.exceptions.BaseException;

/**
 * Created by nqz on 2016/3/7.
 */
public class ErrorUserId extends BaseException {
    public ErrorUserId() {
        super(1011, "用户信息有误！", "用户id不存在");
    }
}
