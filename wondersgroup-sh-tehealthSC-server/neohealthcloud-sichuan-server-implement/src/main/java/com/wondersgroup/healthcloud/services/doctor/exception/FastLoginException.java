package com.wondersgroup.healthcloud.services.doctor.exception;


import com.wondersgroup.healthcloud.exceptions.BaseException;

/**
 * Created by jialing.yao on 2017-6-6.
 */
public class FastLoginException extends BaseException {

    public FastLoginException(int code, String msg) {
        super(code, msg, null);
    }

    public FastLoginException(String msg) {
        super(1000, msg, null);
    }
}
