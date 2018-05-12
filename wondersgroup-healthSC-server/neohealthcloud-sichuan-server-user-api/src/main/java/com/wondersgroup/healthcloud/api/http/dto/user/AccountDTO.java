package com.wondersgroup.healthcloud.api.http.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.utils.DateFormatter;
import com.wondersgroup.healthcloud.utils.IdcardUtils;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    public String id;
    public String nickname;
    public String mobile;
    public String avatar;
    public String gender;
    public String birthday;
    public String age;
    public String idcard;
    public String name;
    @JsonProperty("password_complete")
    public Boolean passwordComplete;
    public String healthCard;//居民健康卡
    public Integer verificationStatus;//0-未提交,1-认证失败,2-审核中,3-认证成功
    public String talkId; //环信id
    public String talkPwd; //环信pwd
    public String signFamilyTeamId;//签约的家庭团队id 为空表示没有签约

    public AccountDTO() {

    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.nickname = account.getNickname();
        this.mobile = account.getMobile();
        this.avatar = account.getAvatar();
        this.gender = account.getGender();
        this.birthday = DateFormatter.dateFormat(account.getBirthday());
        if (account.getBirthday() != null) {
            this.age = String.valueOf(IdcardUtils.getAgeByBirthday(account.getBirthday()));
        }
        this.idcard = account.getIdcard();
        this.name = account.getName();
        this.passwordComplete = account.getPassword() != null;
        this.healthCard = account.getHealthCard();
        this.talkId = account.getTalkid() == null ? "" : account.getTalkid();
        this.talkPwd = account.getTalkpwd() == null ? "" : account.getTalkpwd();
    }
}
