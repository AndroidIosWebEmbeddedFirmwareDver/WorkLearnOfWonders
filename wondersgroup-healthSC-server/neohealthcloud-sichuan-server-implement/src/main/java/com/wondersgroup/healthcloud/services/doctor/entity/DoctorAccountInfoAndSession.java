package com.wondersgroup.healthcloud.services.doctor.entity;


import com.wondersgroup.healthcloud.services.account.dto.Session;

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
public class DoctorAccountInfoAndSession {
    public final DoctorInfo doctorInfo;
    public final Session session;
    public final String bindId;
    public final Boolean firstLogin;

    public DoctorAccountInfoAndSession(DoctorInfo doctorInfo, Session session, Boolean firstLogin) {
        this.doctorInfo = doctorInfo;
        this.session = session;
        this.bindId = null;
        this.firstLogin = firstLogin;
    }

    /*public DoctorAccountInfoAndSession(DoctorAccount account, Session session, String bindId, Boolean firstLogin) {
        this.account = account;
        this.session = session;
        this.bindId = bindId;
        this.firstLogin = firstLogin;
    }*/

    /*public static Boolean checkBindMobilie(Account account) {
        return account != null && account.getMobile() != null;
    }

    public static Boolean checkInfoCompleted(Account account) {
        return account != null && account.getNickname() != null && account.getBirthday() != null && account.getGender() != null;
    }*/
}
