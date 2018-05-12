package com.wondersgroup.healthcloud.services.user.exception;


import com.wondersgroup.healthcloud.exceptions.BaseException;

/**
 * Created by nqz on 2016/3/7.
 */
public class VerificationManageException extends BaseException {

    public VerificationManageException() {
        super(2002, "该实名认证信息不能重复审核！", "该实名认证信息不能重复审核");
    }

    public VerificationManageException(String msg) {
        super(2002, msg, msg);
    }
}
