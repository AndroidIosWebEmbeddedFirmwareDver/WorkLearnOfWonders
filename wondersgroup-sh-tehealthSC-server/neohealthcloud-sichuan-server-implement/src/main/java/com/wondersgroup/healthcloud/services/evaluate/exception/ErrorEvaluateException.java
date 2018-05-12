package com.wondersgroup.healthcloud.services.evaluate.exception;


import com.wondersgroup.healthcloud.exceptions.BaseException;

/**
 * Created by nqz on 2016/3/7.
 */
public class ErrorEvaluateException extends BaseException {

    public ErrorEvaluateException(String msg) {
        super(3002, msg, msg);
    }
}
