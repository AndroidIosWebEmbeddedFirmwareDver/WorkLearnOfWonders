package com.wonders.health.venus.open.user.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 中医体质辨识问题
 * Created by songzhen on 2016/8/15.
 */
public class Question {
    String title;
    List<String> options;

    public Question() {

    }

    public Question(String title, List<String> options) {
        this.title = title;
        if (options != null) {
            this.options = options;
        } else {
            this.options = new ArrayList<String>();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
