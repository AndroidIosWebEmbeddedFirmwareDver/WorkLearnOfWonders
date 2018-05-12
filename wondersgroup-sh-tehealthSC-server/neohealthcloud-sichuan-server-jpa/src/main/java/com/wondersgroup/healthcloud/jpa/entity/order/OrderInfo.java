package com.wondersgroup.healthcloud.jpa.entity.order;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhaozhenxing on 2016/11/07.
 */

@Data
@Entity
@Table(name = "tb_order_info")
public class OrderInfo {
    @Id
    @Column(name = "id")
    private String id;// 本地订单ID
    @Column(name = "sc_order_id")
    private String scOrderId;// 区域平台订单ID
    @Column(name = "uid")
    private String uid;// 用户ID
    @Column(name = "take_password")
    private String takePassword;// 用户ID
    @Column(name = "schedule_id")
    private String scheduleId;// 排班id
    @Column(name = "num_source")
    private String numSource;// 号源id
    @Column(name = "card_type")
    private String cardType;// 证件类型(01:身份证，02：02：居民户口簿，03：护照，04：军官证（士兵证），05：驾驶执照，06：港澳居民来往内地通行证，07：台湾居民来往内地通行证，99：其他)
    @Column(name = "user_phone")
    private String userPhone;// 手机号
    @Column(name = "patient_name")
    private String patientName;// 就诊人
    @Column(name = "hos_code")
    private String hosCode;// 医院代码
    @Column(name = "hos_name")
    private String hosName;// 医院名称
    @Column(name = "dept_code")
    private String deptCode;// 科室代码
    @Column(name = "dept_name")
    private String deptName;// 科室名称
    @Column(name = "doct_code")
    private String doctCode;// 医生代码
    @Column(name = "doct_name")
    private String doctName;// 医生姓名
    @Column(name = "outpatient_type")
    private String outpatientType;// 门诊类型
    @Column(name = "date")
    private String date;// 日期（格式：yyyy-MM-dd）
    @Column(name = "week")
    private String week;// 周几
    @Column(name = "time_rnge")
    private String timeRnge;// 就诊时段(1:上午,2:下午,3:晚上)
    @Column(name = "cost")
    private String cost;// 需要支付的价格，减免后的价格
    @Column(name = "original_cost")
    private String originalCost;//原价
    @Column(name = "visit_address")
    private String visitAddress;// 就诊地址
    @Column(name = "state")
    private String state;// 订单状态 1:已预约;2:已支付;3:已退号;4:履约(已取号);5:待退费;6:爽约
    @Column(name = "is_evaluated")
    private Integer isEvaluated = 0;//是否已评价[0:未评价1:已评价]
    @Column(name = "del_flag")
    private String delFlag;// 删除标志 0：不删除 1：已删除
    @Column(name = "create_time")
    private Date createTime;// 创建时间
    @Column(name = "update_time")
    private Date updateTime;// 更新时间
    @Column(name = "show_order_id")
    private String showOrderId;// 显示订单ID
    @Column(name = "platform_user_id")
    private String platformUserId;// 平台用户ID
    @Column(name = "medi_card_id")
    private String mediCardId;// 诊疗卡卡号

    @Column(name = "medi_card_type_name")
    private String medi_card_type_name;// '诊疗卡类型名称'
    @Column(name = "medi_card_type_code")
    private String medi_card_type_code;// '诊疗卡类型编码'

    @Column(name = "visit_no")
    private String visitNo;// 就诊序号
    @Column(name = "original_schedule_id")
    private String originalScheduleId;// 原排班ID
    @Column(name = "city_code")
    private String cityCode;// 行政区域编码
    @Column(name = "source")
    private String source;// 订单来源 区分健康双流和微健康


}