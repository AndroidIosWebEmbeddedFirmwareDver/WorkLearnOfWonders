package com.wondersgroup.healthcloud.solr.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.common.utils.AppUrlH5Utils;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsArticleDto {
    private String id;
    private String thumb;
    private String title;
    private String desc;
    private String pv;
    private String url;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    public NewsArticleDto(NewsArticle article, AppUrlH5Utils appUrlH5Utils, String area) {
        this.id = String.valueOf(article.getId());
        this.title = article.getTitle();
        this.desc = article.getBrief();
        this.pv = String.valueOf(article.getPv() + article.getFake_pv());
        this.thumb = article.getThumb();
        this.date = article.getUpdate_time();
        this.url = appUrlH5Utils.buildNewsArticleView(article.getId(), area);

    }
}
