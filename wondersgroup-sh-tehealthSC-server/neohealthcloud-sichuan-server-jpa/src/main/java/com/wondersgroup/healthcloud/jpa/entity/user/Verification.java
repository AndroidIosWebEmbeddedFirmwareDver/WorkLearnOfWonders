package com.wondersgroup.healthcloud.jpa.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by longshasha on 16/11/4.
 * 用户的是实名认证信息
 */
@Data
@Entity
@Table(name = "tb_user_verification")
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uid;
    private String name;
    private String idcard;
    private String photo;

    @Column(name = "verification_level")
    private Integer verificationLevel = 0;//实名认证级别 -1 拒绝，0未认证，1已认证

    @Column(name = "refusalReason")
    private String refusal_reason;//实名认证拒绝填写原因

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
