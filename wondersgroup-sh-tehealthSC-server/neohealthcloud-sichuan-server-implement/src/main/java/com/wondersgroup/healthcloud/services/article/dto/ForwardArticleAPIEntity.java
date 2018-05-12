package com.wondersgroup.healthcloud.services.article.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by dukuanxin on 2016/8/26.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForwardArticleAPIEntity {
    private int id;
    private int article_id;
    private int is_visable;
    private int rank;
    private String title;
    private String start_time;
    private String end_time;
    private String status;

    public ForwardArticleAPIEntity(Map<String, Object> param) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.id = (int) param.get("id");
        this.article_id = (int) param.get("article_id");
        this.is_visable = (int) param.get("is_visable");
        this.rank = (int) param.get("rank");
        this.title = (String) param.get("title");
        this.start_time = format.format((Date) param.get("start_time"));
        this.end_time = format.format((Date) param.get("end_time"));
        Date nowDate = new Date();
        this.status = "2";
        if (nowDate.before((Date) param.get("start_time"))) {
            status = "1";
        } else if (nowDate.after((Date) param.get("end_time"))) {
            status = "3";
        }
    }

}
