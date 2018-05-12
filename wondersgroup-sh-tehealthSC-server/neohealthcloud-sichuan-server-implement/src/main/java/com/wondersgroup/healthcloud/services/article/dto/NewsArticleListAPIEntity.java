package com.wondersgroup.healthcloud.services.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.common.utils.AppUrlH5Utils;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;
import lombok.Data;

import java.util.Date;

/**
 * Created by yanshuai on 15/6/26.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsArticleListAPIEntity {
    private String id;
    private String thumb;
    private String title;
    private String desc;
    private String pv;
    private String url;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    public NewsArticleListAPIEntity(NewsArticle article, AppUrlH5Utils appUrlH5Utils, String area) {
        this.id = String.valueOf(article.getId());
        this.title = article.getTitle();
        this.desc = article.getBrief();
        this.pv = String.valueOf(article.getPv() + article.getFake_pv());
        this.thumb = article.getThumb();
        this.date = article.getUpdate_time();
        this.url = appUrlH5Utils.buildNewsArticleView(article.getId(), area);

    }
}
