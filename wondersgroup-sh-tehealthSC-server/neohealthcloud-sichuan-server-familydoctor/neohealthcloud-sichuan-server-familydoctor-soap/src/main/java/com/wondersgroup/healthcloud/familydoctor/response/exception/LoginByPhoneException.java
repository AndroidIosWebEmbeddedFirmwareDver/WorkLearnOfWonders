package com.wondersgroup.healthcloud.familydoctor.response.exception;

import com.wondersgroup.healthcloud.exceptions.BaseException;

/**
 * Created by jialing.yao on 2017-6-26.
 */
public class LoginByPhoneException extends BaseException {

    public LoginByPhoneException(String msg) {
        super(1000, msg);
    }
}
