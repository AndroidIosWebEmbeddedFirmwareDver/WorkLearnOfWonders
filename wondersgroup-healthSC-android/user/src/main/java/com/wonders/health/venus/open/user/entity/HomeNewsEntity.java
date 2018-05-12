package com.wonders.health.venus.open.user.entity;

import java.util.List;

/**
 * 类：${File}
 * 创建者:carrey on 16-8-17.
 * 描述 ：
 */
public class HomeNewsEntity {
    public List<ArticleItem> news;
    public List<Question> questions;

    public static class Question {
        public String ask_content;
        public String comment_count;
        public String age;
        public int gender;
        public String asker_name;
        public String asker_time;
        public String id;
    }
}
