package com.wondersgroup.healthcloud.jpa.entity.feedback;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "app_tb_feedback")
public class Feedback {

    @Id
    private String id;

    private String userId;

    private String mobilePhone;

    private String content;

    private Integer status = 0; // 0:未处理；1：已处理

    private Integer platform = 1;//反馈的平台[1:微健康 2:双流]

    private Date createTime;

    private Date updateTime;

}
