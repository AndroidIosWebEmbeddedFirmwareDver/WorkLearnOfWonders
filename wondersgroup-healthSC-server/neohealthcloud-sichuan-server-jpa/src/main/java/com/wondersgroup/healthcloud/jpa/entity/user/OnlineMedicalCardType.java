package com.wondersgroup.healthcloud.jpa.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Description 用户在线挂号卡记录表
 * @Author hackerWang
 * @Create 2018-04-13 下午2:55
 **/

@Data
@Entity
@Table(name = "tb_user_online_medical_cards")
public class OnlineMedicalCardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;//在线挂号卡记录唯一ID
    @Column(name = "card_type_code")
    private String card_type_code;//在线挂号卡类型编码描述

    @Column(name = "card_type_name")
    private String card_type_name;//在线挂号卡类型名称描述

    @Column(name = "update_time")
    private Timestamp updateTime;//在线挂号卡类型更新时间

    @Column(name = "create_time")
    private Timestamp createTime;//在线挂号卡类型创建时间

    @Column(name = "is_deleted")
    private int is_deleted;//是否删除，0-未删除，1-已删除


    public OnlineMedicalCardType(String card_type_code, String card_type_name) {
        this.card_type_code = card_type_code;
        this.card_type_name = card_type_name;
        this.is_deleted = 0;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.updateTime = new Timestamp(System.currentTimeMillis());
    }

    public OnlineMedicalCardType update(String card_type_code, String card_type_name) {
        this.card_type_code = card_type_code;
        this.card_type_name = card_type_name;
        this.is_deleted = 0;
        this.updateTime = new Timestamp(System.currentTimeMillis());
        return this;
    }
}

