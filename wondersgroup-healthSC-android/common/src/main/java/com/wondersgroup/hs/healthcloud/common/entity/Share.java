package com.wondersgroup.hs.healthcloud.common.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/3/10 14:36
 */
public class Share {
    public int id;
    public String thumb;
    public String title;
    @JSONField(name = "desc")
    public String brief;
    @JSONField(name = "url")
    public String shareUrl;
    public String thumb_local_path;

    @Override
    public String toString() {
        return "Share{" +
                "id=" + id +
                ", thumb='" + thumb + '\'' +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", thumb_local_path='" + thumb_local_path + '\'' +
                '}';
    }
}
