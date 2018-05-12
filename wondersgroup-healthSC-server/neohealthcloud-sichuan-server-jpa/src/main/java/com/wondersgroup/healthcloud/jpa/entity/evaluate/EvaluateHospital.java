package com.wondersgroup.healthcloud.jpa.entity.evaluate;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by longshasha on 16/11/1.
 * 用户对医院的评价
 */
@Data
@Entity
@Table(name = "tb_evaluate_hospital")
public class EvaluateHospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uid;

    private Integer status = 0;//0:待审核,1:审核通过,2:审核不通过,

    @Column(name = "hospital_id")
    private Integer hospitalId;

    private String content;

    @Column(name = "is_top")
    private Integer isTop = 0;//1:置顶

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

}
