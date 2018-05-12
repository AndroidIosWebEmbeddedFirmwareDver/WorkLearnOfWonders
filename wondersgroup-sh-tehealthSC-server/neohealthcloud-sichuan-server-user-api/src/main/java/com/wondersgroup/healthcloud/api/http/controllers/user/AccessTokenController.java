package com.wondersgroup.healthcloud.api.http.controllers.user;

import com.wondersgroup.healthcloud.api.http.dto.user.AccountAndSessionDTO;
import com.wondersgroup.healthcloud.common.http.annotations.IgnoreGateLog;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.SessionDTO;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoAndSession;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import com.wondersgroup.healthcloud.services.user.VerificationService;
import com.wondersgroup.healthcloud.utils.security.RSA;
import com.wondersgroup.healthcloud.utils.security.RSAKey;
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
 * Created by zhangzhixiu on 16/2/22.
 */
@RestController
@RequestMapping("/api/token")
public class AccessTokenController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private SessionUtil sessionUtil;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    @IgnoreGateLog
    public JsonResponseEntity<AccountAndSessionDTO> signin(@RequestParam String mobile,
                                                           @RequestParam String password) {
        AccountInfoAndSession result = accountService.signin(mobile, RSA.decryptByPrivateKey(password, RSAKey.privateKey));

        AccountAndSessionDTO dto = new AccountAndSessionDTO(result);
        dto.account.verificationStatus = verificationService.getVerificationLevelByAccount(result.account) + 2;

        JsonResponseEntity<AccountAndSessionDTO> response = new JsonResponseEntity<>();
        response.setData(dto);
        response.setMsg("登录成功");

        return response;
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<AccountAndSessionDTO> codeSignin(@RequestParam String mobile,
                                                               @RequestParam String code,
                                                               @RequestHeader(name = "city-code", required = false) String cityCode) {
        AccountInfoAndSession result = accountService.codeSignin(mobile, code, cityCode);

        AccountAndSessionDTO dto = new AccountAndSessionDTO(result);
        dto.account.verificationStatus = verificationService.getVerificationLevelByAccount(result.account) + 2;

        JsonResponseEntity<AccountAndSessionDTO> response = new JsonResponseEntity<>();
        response.setData(dto);
        response.setMsg("登录成功");

        return response;
    }

    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<AccountAndSessionDTO> wechatSignin(@RequestParam String token,
                                                                 @RequestParam String openid) {
        AccountInfoAndSession result = accountService.wechatSignin(token, openid);

        AccountAndSessionDTO dto = new AccountAndSessionDTO(result);
        dto.account.verificationStatus = verificationService.getVerificationLevelByAccount(result.account) + 2;

        JsonResponseEntity<AccountAndSessionDTO> response = new JsonResponseEntity<>();
        response.setData(dto);
        response.setMsg("登录成功");

        return response;
    }

    @RequestMapping(value = "/weibo", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<AccountAndSessionDTO> weiboSignin(@RequestParam String token) {
        AccountInfoAndSession result = accountService.weiboSignin(token);

        AccountAndSessionDTO dto = new AccountAndSessionDTO(result);
        dto.account.verificationStatus = verificationService.getVerificationLevelByAccount(result.account) + 2;

        JsonResponseEntity<AccountAndSessionDTO> response = new JsonResponseEntity<>();
        response.setData(dto);
        response.setMsg("登录成功");

        return response;
    }

    @RequestMapping(value = "/qq", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<AccountAndSessionDTO> qqSignin(@RequestParam String token) {
        AccountInfoAndSession result = accountService.qqSignin(token);

        AccountAndSessionDTO dto = new AccountAndSessionDTO(result);
        dto.account.verificationStatus = verificationService.getVerificationLevelByAccount(result.account) + 2;

        JsonResponseEntity<AccountAndSessionDTO> response = new JsonResponseEntity<>();
        response.setData(dto);
        response.setMsg("登录成功");

        return response;
    }

    @RequestMapping(value = "/guest", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<SessionDTO> guestSignin() {
        Session session = sessionUtil.createGuest();

        JsonResponseEntity<SessionDTO> response = new JsonResponseEntity<>();
        response.setData(new SessionDTO(session));

        return response;
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @VersionRange
    public JsonResponseEntity<SessionDTO> signout(@RequestHeader("access-token") String token) {
        sessionUtil.inactiveSession(token, false);
        Session session = sessionUtil.createGuest();

        JsonResponseEntity<SessionDTO> response = new JsonResponseEntity<>();
        response.setData(new SessionDTO(session));
        response.setMsg("退出成功");

        return response;
    }
}