package com.wondersgroup.healthcloud.jpa.entity.help;

import com.wondersgroup.healthcloud.jpa.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "app_tb_helpcenter")
public class HelpCenter extends BaseEntity {

    private String title;

    private String content;

    private Integer platform;//1:微健康 2:健康双流

    private int sort;

    @Column(name = "is_visable")
    private String isVisable;
}
