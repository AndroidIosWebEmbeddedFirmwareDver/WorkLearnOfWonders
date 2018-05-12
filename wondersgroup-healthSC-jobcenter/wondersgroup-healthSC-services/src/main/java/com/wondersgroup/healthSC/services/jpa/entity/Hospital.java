package com.wondersgroup.healthSC.services.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
@Data
@Entity
@Table(name = "tb_hospital_info")
public class Hospital {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name= "hospital_code")
    private String hospitalCode;// 医院代码
    @Column(name="hospital_name")
    private String hospitalName; // 医院名称
    @Column(name="hospital_address")
    private String hospitalAddress ; // 医院地址
    @Column(name="hospital_rule")
    private String hospitalRule ;// 预约挂号须知
    @Column(name="hospital_desc")
    private String hospitalDesc;// 医院简介
    @Column(name="hospital_phone")
    private String hospitalPhone; // 联系电话
    @Column(name="hospital_grade")
    private String hospitalGrade; //医院等级
    @Column(name="hospital_latitude")
    private Double hospitalLatitude; //医院纬度
    @Column(name="hosptial_longitude")
    private Double hosptialLongitude; //医院经度
    @Column(name="hosptial_photo")
    private String hosptialPhoto; // 医院头像',
    @Column(name="city_code")
    private String cityCode; // 机构所属城市的区号',
    @Column(name="is_order_today")
    private String isOrderToday; // 是否支持预约当天 1:支持,0:不支持
    @Column(name="is_open_emails")
    private Integer isOpenEmails=0;//是否开启订单流水发送给客户（1:开启,0:不开启）
    @Column(name="custom_emails")
    private String customEmails="";//订单流水发送给客户的邮箱
    @Column(name="status")
    private String status;//启用状态
    @Column(name="del_flag")
    private String delFlag; // 删除标志
    @Column(name="create_time")
    private Date createTime; // 创建时间
    @Column(name="update_time")
    private Date updateTime; // 更新时间
}