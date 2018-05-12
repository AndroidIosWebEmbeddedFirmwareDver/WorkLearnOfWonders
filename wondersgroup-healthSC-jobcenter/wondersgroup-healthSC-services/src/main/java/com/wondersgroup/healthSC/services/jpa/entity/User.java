package com.wondersgroup.healthSC.services.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by nick on 2016/11/7.
 * @author nick
 * 用户
 */
@Data
@Entity
@Table(name = "tb_user_account")
public class User {

    @Id
    @Column
    private String id;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String mobile;

    @Column
    private String avatar;

    @Column
    private String gender;

    @Column
    private Date birthday;

    @Column
    private String name;

    @Column
    private String idcard;

    @Column(name = "verification_level")
    private int verificationLevel;

    @Column(name = "join_time")
    private Date joinTime;

}
