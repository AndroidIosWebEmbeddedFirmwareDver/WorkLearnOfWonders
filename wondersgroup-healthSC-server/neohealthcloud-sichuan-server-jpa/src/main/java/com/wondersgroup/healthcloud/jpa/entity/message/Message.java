package com.wondersgroup.healthcloud.jpa.entity.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Entity
@Table(name = "tb_system_message")
public class Message {
    @Id
    private String id;
    @Column
    private Integer type;
    @Column
    private String title;
    @Column
    private String content;
    @Column(name = "img_url")
    private String imgUrl;
    @Column
    private String url;
    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @Column(name = "create_by")
    private String createBy;
    @Column(name = "is_show")
    private Boolean isShow;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    public Message(Boolean isShow) {
        this.isShow = isShow;
    }

    public Message() {

    }
}
