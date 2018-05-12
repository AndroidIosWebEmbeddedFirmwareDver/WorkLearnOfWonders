package com.wondersgroup.healthcloud.jpa.entity.hospital;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_hospital_info")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String hospitalCode;// 医院代码
    private String hospitalName; // 医院名称
    private String hospitalAddress; // 医院地址
    private String hospitalRule;// 预约挂号须知
    private String hospitalDesc;// 医院简介
    private String hospitalPhone; // 联系电话
    private String hospitalGrade; // 医院等级
    private Double hospitalLatitude; // 医院纬度
    private Double hosptialLongitude; // 医院经度
    private String hosptialPhoto; // 医院头像',
    private String cityCode; // 机构所属城市的区号',
    private String isOrderToday; // 是否支持预约当天 1:支持,0:不支持
    private Integer isOpenEmails = 0;//是否开启订单流水发送给客户（1:开启,0:不开启）
    private String customEmails = "";//订单流水发送给客户的邮箱
    private String status = "1";// 启用状态 1:启用，0：未启用
    private int receiveCount;//预约量
    private String submerno;//链支付子商户号
    private String appid;
    private String appSecret;
    private String delFlag = "0"; // 删除标志 1：删除 ，0：未删除
    private Date createTime; // 创建时间
    private Date updateTime; // 更新时间

}