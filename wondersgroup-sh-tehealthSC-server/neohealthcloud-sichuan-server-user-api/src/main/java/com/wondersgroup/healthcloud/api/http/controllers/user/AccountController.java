package com.wondersgroup.healthcloud.api.http.controllers.user;

import com.google.common.collect.ImmutableMap;
import com.wondersgroup.healthcloud.api.http.dto.user.AccountAndSessionDTO;
import com.wondersgroup.healthcloud.api.http.dto.user.AccountDTO;
import com.wondersgroup.healthcloud.common.http.annotations.IgnoreGateLog;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoAndSession;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoForm;
import com.wondersgroup.healthcloud.services.account.dto.AccountSignupForm;
import com.wondersgroup.healthcloud.services.user.VerificationService;
import com.wondersgroup.healthcloud.utils.security.RSA;
import com.wondersgroup.healthcloud.utils.security.RSAKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
 * Created by zhangzhixiu on 16/2/24.
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private VerificationService verificationService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @VersionRange
    public JsonResponseEntity<AccountDTO> info(@RequestParam String id) {
        Account account = accountService.info(id);

        JsonResponseEntity<AccountDTO> response = new JsonResponseEntity<>();
        AccountDTO accountDTO = new AccountDTO(account);

        accountDTO.verificationStatus = verificationService.getVerificationLevelByAccount(account) + 2;
        response.setData(accountDTO);

        return response;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<AccountAndSessionDTO> signup(@RequestBody String body,
                                                           @RequestHeader(name = "city-code", required = false) String cityCode) {
        JsonKeyReader reader = new JsonKeyReader(body);
        AccountSignupForm form = new AccountSignupForm();
        form.mobile = reader.readString("mobile", false);
        form.code = reader.readString("code", false);
        form.bindId = reader.readString("bind_id", true);
        form.cityCode = cityCode;

        AccountInfoAndSession result = accountService.signup(form);

        JsonResponseEntity<AccountAndSessionDTO> response = new JsonResponseEntity<>();
        response.setData(new AccountAndSessionDTO(result));
        return response;
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity<AccountDTO> updateInfo(@RequestBody String body) {
        JsonKeyReader reader = new JsonKeyReader(body);
        AccountInfoForm form = new AccountInfoForm();
        form.id = reader.readString("uid", false);
        form.avatar = reader.readString("avatar", true);
        form.gender = reader.readString("gender", true);
        form.birthday = reader.readString("birthday", true);
        form.nickname = reader.readString("nickname", true);
        form.healthCard = reader.readString("healthCard", true);
        JsonResponseEntity<AccountDTO> response = new JsonResponseEntity<>();
        if (StringUtils.isNotEmpty(form.healthCard) && !form.healthCard.matches("^[a-z0-9A-Z]+$")) {
            response.setCode(1000);
            response.setMsg("请输入正确的卡号，否则会影响后期功能，例如挂号就诊和金融支付");
            return response;
        }

        Account result = accountService.updateInfo(form);

        response.setData(new AccountDTO(result));
        response.setMsg("用户信息设置成功");
        return response;
    }


    @RequestMapping(value = "/user/password/update", method = RequestMethod.POST)
    @VersionRange
    @IgnoreGateLog
    @WithoutToken
    public JsonResponseEntity<String> updatePassword(@RequestBody String body) {
        JsonKeyReader reader = new JsonKeyReader(body);
        String uid = reader.readString("uid", false);
        String pwd = reader.readString("previous_password", true);
        String previousPassword = null;
        if (StringUtils.isNotBlank(pwd)) {
            previousPassword = RSA.decryptByPrivateKey(pwd, RSAKey.privateKey);
        }
        String newPassword = RSA.decryptByPrivateKey(reader.readString("new_password", false), RSAKey.privateKey);

        accountService.updatePassword(uid, previousPassword, newPassword);
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        response.setMsg("密码修改成功");

        return response;
    }

    @RequestMapping(value = "/user/password", method = RequestMethod.POST)
    @VersionRange
    @IgnoreGateLog
    @WithoutToken
    public JsonResponseEntity<String> resetPassword(@RequestBody String body) {
        JsonKeyReader reader = new JsonKeyReader(body);
        String mobile = reader.readString("mobile", false);
        String code = reader.readString("code", false);
        String password = RSA.decryptByPrivateKey(reader.readString("password", false), RSAKey.privateKey);

        accountService.resetPassword(mobile, password, code);
        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        response.setMsg("密码设置成功");

        return response;
    }

    @RequestMapping(value = "/user/mobile", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity<Map<String, String>> updateMobile(@RequestBody String body) {
        JsonKeyReader reader = new JsonKeyReader(body);
        String uid = reader.readString("uid", false);
        String newMobile = reader.readString("new_mobile", false);
        String code = reader.readString("code", false);

        accountService.updateMobile(uid, newMobile, code);

        JsonResponseEntity<Map<String, String>> response = new JsonResponseEntity<>();
        Map<String, String> result = ImmutableMap.of("mobile", newMobile);
        response.setData(result);

        return response;
    }
}
