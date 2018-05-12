package com.wondersgroup.healthcloud.jpa.entity.config;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Surface Book on 2017/2/24.
 */
@Data
@Entity
@Table(name = "tb_server_config")
public class ServerConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "area_name")
    private String areaName;//地区名称
    @Column(name = "area_code")
    private String areaCode;//区域代码
    @Column(name = "api_url")
    private String apiUrl;//接口地址

    private String remark;//备注

    private String type;//1-号源,2-诊间支付/电子处方,3-提取报告,4-健康档案

}
