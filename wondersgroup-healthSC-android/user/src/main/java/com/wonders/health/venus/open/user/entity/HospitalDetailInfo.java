package com.wonders.health.venus.open.user.entity;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/8/16 10:02
 */
public class HospitalDetailInfo {
    /**
     *  "hospitalId": 1,
     "hospitalCode": "450751995",
     "hospitalName": "成都市第六人民医院",
     "hospitalAddress": "",
     "hospitalDesc": "",
     "hospitalTel": "",
     "hospitalGrade": "",
     "receiveCount": 0
     */
    public String hospitalId;///医院id
    public String hospitalCode;///医院代码
    public String hospitalName;
    public String hospitalAddress;///医院地址
    public String hospitalDesc;///医院简介
    public String hospitalTel;///医院电话
    public String hospitalGrade;///医院等级
    public String receiveCount;///预约量

    public String hospitalPhoto;//医院图片
}
