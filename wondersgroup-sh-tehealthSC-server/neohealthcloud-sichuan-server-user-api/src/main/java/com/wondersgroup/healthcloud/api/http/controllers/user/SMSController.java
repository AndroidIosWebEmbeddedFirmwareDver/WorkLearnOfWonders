package com.wondersgroup.healthcloud.api.http.controllers.user;

import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.utils.CityCodeNameConverter;
import com.wondersgroup.healthcloud.utils.sms.VerifyCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * Created by zhangzhixiu on 16/3/4.
 */
@RestController
@RequestMapping("/api")
public class SMSController {

    @Autowired
    private VerifyCodeService service;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/sms", method = RequestMethod.GET)
    @WithoutToken
    @VersionRange
    public JsonResponseEntity<String> sendSMS(@RequestParam String mobile,
                                              @RequestParam(name = "type", defaultValue = "0") String type,
                                              @RequestHeader(name = "city-code", required = false) String cityCode) {
        if (mobile.length() != 11 || !mobile.startsWith("1") || !StringUtils.isNumeric(mobile)) {
            throw new CommonException(1000, "手机号码格式不正确, 请重新输入");
        }
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        if ("1".equals(type)) {
            service.sendCode(mobile, "%s，为您的[" + CityCodeNameConverter.getName(cityCode) + "]登录验证码，请在15分钟内完成输入。如非您本人操作，请忽略。详询客服400-900-9957.[万达健康]", cityCode);
        } else if ("2".equals(type)) {
            Account account = accountService.getAccountByMobile(mobile);
            if (account == null) {
                throw new CommonException(1005, "该用户不存在");
            }
            service.sendCode(mobile, "忘记密码：%s，为您的[" + CityCodeNameConverter.getName(cityCode) + "]重置密码的验证码，请在15分钟内完成输入。如非您本人操作，请忽略。详询客服400-900-9957.[万达健康]", cityCode);
        } else if ("3".equals(type)) {
            Account account = accountService.getAccountByMobile(mobile);
            if (account == null) {
                throw new CommonException(1005, "该用户不存在");
            }
            service.sendCode(mobile, "%s，为您的[" + CityCodeNameConverter.getName(cityCode) + "]设置密码的验证码，请在15分钟内完成输入。如非您本人操作，请忽略。详询客服400-900-9957.[万达健康]", cityCode);
        } else {
            service.sendCode(mobile, "%s", cityCode);
        }
        response.setMsg("发送成功");
        return response;
    }

    @RequestMapping(value = "/sms/verification", method = RequestMethod.GET)
    @WithoutToken
    @VersionRange
    public JsonResponseEntity<String> verify(@RequestParam String mobile,
                                             @RequestParam String code) {
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        Boolean result = service.tempCheck(mobile, code);
        if (result) {
            response.setMsg("验证成功");
        } else {
            response.setCode(1000);
            response.setMsg("验证码错误");
        }
        return response;
    }
}
