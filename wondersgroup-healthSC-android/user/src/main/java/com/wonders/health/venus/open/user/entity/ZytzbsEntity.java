package com.wonders.health.venus.open.user.entity;

import java.io.Serializable;

/**
 * 中医体质辨识结果
 * Created by songzhen on 2015/7/1.
 */
public class ZytzbsEntity implements Serializable {

    public String physical;
    public advice advice;

    public class advice implements Serializable {
        public String dailyLife;
        public String emotion;
        public String diet;
        public String exercise;
        public String care;
    }
}
