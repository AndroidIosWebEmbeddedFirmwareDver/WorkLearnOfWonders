package com.wondersgroup.healthcloud.jpa.entity.administrative;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
@Data
@Entity
@Table(name = "tb_app_area")
public class Area {
    @Id
    @Column(name = "area_id")
    private String areaId;
    @Column(name = "area_name")
    private String areaName;
    @Column(name = "city_id")
    private String cityId;
    @Column(name = "del_flag")
    private String delFlag;
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
