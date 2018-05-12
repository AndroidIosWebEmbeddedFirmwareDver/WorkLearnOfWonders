package com.wonders.health.venus.open.user.entity;

import java.io.Serializable;

public class ArticleItem implements Serializable{
    public static final int TYPE_SMALL_IMG = 1;
    public static final int TYPE_LARGE_IMG = 2;

    public String id;
    public String title;
    public String desc;
    public String thumb;
    public String url;
    public String pv;
    public int img_type;
    public String article_id;
    public String date;
}
