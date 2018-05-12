package com.wondersgroup.healthcloud.services.account.dto;


import com.wondersgroup.healthcloud.jpa.entity.user.Account;

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
 * Created by zhangzhixiu on 16/2/17.
 */
public class AccountInfoAndSession {
    public final Account account;
    public final Session session;
    public final String bindId;
    public final Boolean firstLogin;

    public AccountInfoAndSession(Account account, Session session, Boolean firstLogin) {
        this.account = account;
        this.session = session;
        this.bindId = null;
        this.firstLogin = firstLogin;
    }

    public AccountInfoAndSession(Account account, Session session, String bindId, Boolean firstLogin) {
        this.account = account;
        this.session = session;
        this.bindId = bindId;
        this.firstLogin = firstLogin;
    }

    public static Boolean checkBindMobilie(Account account) {
        return account != null && account.getMobile() != null;
    }

    public static Boolean checkInfoCompleted(Account account) {
        return account != null && account.getNickname() != null && account.getBirthday() != null && account.getGender() != null;
    }
}
