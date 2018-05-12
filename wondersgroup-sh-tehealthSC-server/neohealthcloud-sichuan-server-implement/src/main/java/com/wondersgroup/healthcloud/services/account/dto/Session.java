package com.wondersgroup.healthcloud.services.account.dto;

import lombok.Data;

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
 * Created by zhangzhixiu on 15/11/26.
 */
@Data
public final class Session {

    private String accessToken;
    private String userId;
    private String secret;
    private Boolean isValid;
    private Boolean isDoctor;
    private Boolean isAdmin;
    private Object user;

    public Session() {
    }

    public Session(String accessToken, Map<String, String> objectMap) {
        this.accessToken = accessToken;
        this.userId = objectMap.get(Key.id.name());
        this.secret = objectMap.get(Key.secret.name());
        this.isValid = "1".equals(objectMap.get(Key.valid.name()));
        this.isDoctor = "1".equals(objectMap.get(Key.doctor.name()));
        this.isAdmin = "1".equals(objectMap.get(Key.admin.name()));
    }

    public Boolean isGuest() {
        return userId == null;
    }

    public enum Key {
        token,
        id,
        secret,
        valid,
        doctor,
        admin,
    }


}