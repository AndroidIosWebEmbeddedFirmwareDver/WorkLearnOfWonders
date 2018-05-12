package com.wondersgroup.healthcloud.jpa.entity.article;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "app_tb_article_favorite")
public class ArticleFavorite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int article_id;
    private String user_id;
    private Date update_time;

}
