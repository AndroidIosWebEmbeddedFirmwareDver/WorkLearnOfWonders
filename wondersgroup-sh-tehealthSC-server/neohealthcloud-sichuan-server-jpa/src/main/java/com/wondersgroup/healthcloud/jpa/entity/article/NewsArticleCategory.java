package com.wondersgroup.healthcloud.jpa.entity.article;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dukuanxin on 2016/8/17.
 */
@Data
@Entity
@Table(name = "app_tb_article_category")
public class NewsArticleCategory implements Serializable {
    private static final long serialVersionUID = -4051859403776354544L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;// 分类ID
    private String c_name;// 分类名称
    private int rank;// 排序
    private String update_by;// 更新人
    private Date update_time;// 更新时间
    private String by_area;//创建区域
    private int is_visable;//是否禁用0-是，1-否
    @Transient
    private String belong_article;//是否属于某篇文章，0-是，1-否
}
