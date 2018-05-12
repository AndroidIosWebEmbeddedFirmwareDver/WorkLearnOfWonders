package com.wondersgroup.healthcloud.services.account;


import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoAndSession;
import com.wondersgroup.healthcloud.services.account.dto.AccountInfoForm;
import com.wondersgroup.healthcloud.services.account.dto.AccountSignupForm;

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
public interface AccountService {
    AccountInfoAndSession signin(String mobile, String password);

    AccountInfoAndSession signup(AccountSignupForm form);

    AccountInfoAndSession codeSignin(String mobile, String code, String cityCode);

    AccountInfoAndSession wechatSignin(String token, String openid);

    AccountInfoAndSession weiboSignin(String token);

    AccountInfoAndSession qqSignin(String token);

    Account updateInfo(AccountInfoForm form);

    Account info(String userId);

    Account getAccountByMobile(String mobile);

    void updateMobile(String userId, String newMobile, String code);

    void resetPassword(String mobile, String password, String code);

    void updatePassword(String userId, String previousPassword, String newPassword);

    void addUser(String mobile);

    Map<String, Account> infos(Iterable<String> userIds);

    void platFormRegister(Account account);
}
