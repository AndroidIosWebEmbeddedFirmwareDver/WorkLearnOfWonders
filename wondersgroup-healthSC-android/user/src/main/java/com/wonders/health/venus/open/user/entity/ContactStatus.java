package com.wonders.health.venus.open.user.entity;

/**
 * Created by sunning on 16/1/6.
 * 就诊人增删改查状态
 */
public enum ContactStatus {
    CREATE("添加就诊人","提交"), RETRIEVE("详情","删除");

    private String title;
    private String rightBtnText;

    public String getTitle() {
        return title;
    }

    public String getRightBtnText() {
        return rightBtnText;
    }

    ContactStatus(String title, String rightBtnText) {
        this.title = title;
        this.rightBtnText = rightBtnText;
    }
}
