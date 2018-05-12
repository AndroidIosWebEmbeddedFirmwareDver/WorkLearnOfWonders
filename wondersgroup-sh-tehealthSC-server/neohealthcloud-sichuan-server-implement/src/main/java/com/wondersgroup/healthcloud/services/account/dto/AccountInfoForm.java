package com.wondersgroup.healthcloud.services.account.dto;

import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.utils.DateFormatter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

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
 * Created by zhangzhixiu on 16/3/7.
 */
public class AccountInfoForm {
    public String id;
    public String nickname;
    public String avatar;
    public String gender;
    public String birthday;
    public String healthCard;

    public Account merge(Account account) {
        if (StringUtils.isNotBlank(nickname)) {
            account.setNickname(nickname);
        }
        if (StringUtils.isNotBlank(avatar)) {
            account.setAvatar(avatar);
        }
        if (StringUtils.isNotBlank(gender)) {
            account.setGender(gender);
        }
        if (StringUtils.isNotBlank(healthCard)) {
            account.setHealthCard(healthCard);
        }
        if (StringUtils.isNotBlank(birthday)) {
            Date date = DateFormatter.parseDate(birthday);
            if (date.getTime() > System.currentTimeMillis()) {
                throw new CommonException(1000, "出生日期不能晚于当前时间");
            }
            account.setBirthday(date);
        }

        return account;
    }
}
