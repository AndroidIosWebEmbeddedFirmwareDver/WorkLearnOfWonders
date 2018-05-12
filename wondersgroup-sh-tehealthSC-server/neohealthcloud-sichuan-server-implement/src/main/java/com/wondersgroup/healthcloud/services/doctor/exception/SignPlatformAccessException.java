package com.wondersgroup.healthcloud.services.doctor.exception;


import com.wondersgroup.healthcloud.exceptions.BaseException;

/**
 * Created by jialing.yao on 2017-6-6.
 */
public class SignPlatformAccessException extends BaseException {

    public SignPlatformAccessException(int code, String msg) {
        super(code, msg, null);
    }

    public SignPlatformAccessException() {
        super(1003, "签约平台接口访问异常.", null);
    }
}
