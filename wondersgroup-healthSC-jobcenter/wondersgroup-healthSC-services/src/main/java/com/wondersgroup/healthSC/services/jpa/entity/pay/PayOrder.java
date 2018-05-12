package com.wondersgroup.healthSC.services.jpa.entity.pay;

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
        NOTPAY,
        EXPIRED,
        SUCCESS,
        REFUND,
        REFUNDSUCCESS,
        ALL
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
    private String business;
}
