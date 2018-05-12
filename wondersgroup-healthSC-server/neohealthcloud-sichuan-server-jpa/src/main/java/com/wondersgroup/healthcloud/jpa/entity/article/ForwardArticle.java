package com.wondersgroup.healthcloud.jpa.entity.article;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dukuanxin on 2016/8/25.
 */
@Entity
@Table(name = "app_tb_forward_article")
public class ForwardArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int article_id;
    private String main_area;
    private String spec_area;
    private int rank;
    private int is_visable;
    private Date start_time;
    private Date end_time;
    private Date create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getMain_area() {
        return main_area;
    }

    public void setMain_area(String main_area) {
        this.main_area = main_area;
    }

    public String getSpec_area() {
        return spec_area;
    }

    public void setSpec_area(String spec_area) {
        this.spec_area = spec_area;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getIs_visable() {
        return is_visable;
    }

    public void setIs_visable(int is_visable) {
        this.is_visable = is_visable;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        try {
            this.start_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(start_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        try {
            this.end_time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(end_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
