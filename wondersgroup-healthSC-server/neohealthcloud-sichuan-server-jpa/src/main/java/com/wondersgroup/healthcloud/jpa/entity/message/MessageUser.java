package com.wondersgroup.healthcloud.jpa.entity.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_message_user")
public class MessageUser {
    @Id
    private String id;
    @Column(name = "register_id")
    private String registerId;
    @Column(name = "message_id")
    private String messageId;
    @Column
    private Integer state;
    @Column
    private Integer type;        //1 系统消息，2 支付消息',
    @Column(name = "create_date")
    private Date createDate;
}
