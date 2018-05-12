package com.wondersgroup.healthcloud.jpa.entity.favorite;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dukuanxin on 2016/11/10.
 */
@Data
@Entity
@Table(name = "app_tb_favorite_doctor")
public class FavoriteDoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer docId;

    private String userId;

    private Date updateTime;

}
