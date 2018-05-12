package com.wondersgroup.healthcloud.jpa.entity.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zhaozhenxing on 2016/11/01.
 */
@Data
@Entity
@Table(name = "tb_app_config")
public class AppConfig {
    @Id
    private String id;
    @Column(name = "key_word")
    private String keyWord;
    private String data;
    private Integer discrete;
    private String remark;
    @Column(name = "del_flag")
    private String delFlag;
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
