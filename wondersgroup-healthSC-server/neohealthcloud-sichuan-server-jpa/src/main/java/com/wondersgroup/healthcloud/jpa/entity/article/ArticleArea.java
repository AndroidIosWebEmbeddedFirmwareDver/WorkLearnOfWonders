package com.wondersgroup.healthcloud.jpa.entity.article;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dukuanxin on 2016/8/17.
 */
@Data
@Entity
@Table(name = "app_tb_article_area")
public class ArticleArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String category_id;
    private int article_id;
    private String main_area;
    private String spec_area;
    private String is_visable;
    private Date update_time;
}
