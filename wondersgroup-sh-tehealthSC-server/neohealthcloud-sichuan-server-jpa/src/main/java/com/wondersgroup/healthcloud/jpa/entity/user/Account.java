package com.wondersgroup.healthcloud.jpa.entity.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
 * Created by zhangzhixiu on 15/11/25.
 */
@Data
@Entity
@Table(name = "tb_user_account")
public class Account {

    @Id
    private String id;
    private String password;
    private String nickname;
    private String mobile;
    private String avatar;
    private String gender;
    private Date birthday;
    private String name;
    private String idcard;
    private String talkid; //环信id
    private String talkpwd; //环信pwd
    private String origin;//注册来源，9为平台注册同步注册、其他为微健康系统注册

    @Column(name = "platform_user_id")
    private String platformUserId;

    @Column(name = "sign_family_teamid")
    private String signFamilyTeamId;//签约的家庭医生团队的id

    @Column(name = "sign_time")
    private Date signTime;//签约时间

    @Column(name = "verification_level")
    private int verificationLevel;

    @Column(name = "join_time")
    private Date joinTime;

    @Column(name = "health_card")
    private String healthCard;


    public Boolean verified() {
        return verificationLevel != 0;
    }
}