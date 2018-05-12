package com.wondersgroup.healthcloud.jpa.entity.pay;

import lombok.Data;

import javax.persistence.*;
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
 * <p>
 * Created by zhangzhixiu on 04/11/2016.
 */
@Data
@Entity
@Table(name = "tb_pay_order")
public class PayOrder {

    public enum Status {
        NOTPAY,//待支付
        EXPIRED,//过期
        SUCCESS,//成功
        FAILURE,//失败
        REFUND,//退款中
        REFUNDSUCCESS,//退款成功
    }

    @Id
    private String id;
    private String uid;
    @Column(name = "subject_id")
    private String subjectId;
    @Column(name = "subject_type")
    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;
    private Long amount;
    private String subject;
    private String body;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String channel;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "due_time")
    private Date dueTime;
    private String business;
    @Column(name = "app_name")
    private String appName;
    @Transient
    private Integer isEvaluated;// 是否已评价
    @Column(name = "show_order_id")
    private String showOrderId;// 显示用orderID，无实意
    @Column(name = "city_code")
    private String cityCode;// 行政区域编码
}
