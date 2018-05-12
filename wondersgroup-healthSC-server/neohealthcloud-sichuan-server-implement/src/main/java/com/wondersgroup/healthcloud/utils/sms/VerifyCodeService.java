package com.wondersgroup.healthcloud.utils.sms;

import com.wondersgroup.healthcloud.common.utils.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p/>
 * Created by zhangzhixiu on 16/3/6.
 */
@Component
public class VerifyCodeService {

    private VerifyCodeChecker checker;
    private Debug debug;

    public void sendCode(String mobile, String format, String cityCode) {
        checker.sendVerifyCode(mobile, format, cityCode);
    }

    public Boolean check(String mobile, String code) {
        if (sandboxCodeCheck(code)) {
            return true;
        }
        return checker.checkVerifyCode(mobile, code, true);
    }

    public Boolean tempCheck(String mobile, String code) {
        if (sandboxCodeCheck(code)) {
            return true;
        }
        return checker.checkVerifyCode(mobile, code, false);
    }

    private Boolean sandboxCodeCheck(String code) {
        return debug.sandbox() && "888888".equals(code);
    }

    @Autowired
    public void setChecker(VerifyCodeChecker checker) {
        this.checker = checker;
    }

    @Autowired
    public void setDebug(Debug debug) {
        this.debug = debug;
    }
}
