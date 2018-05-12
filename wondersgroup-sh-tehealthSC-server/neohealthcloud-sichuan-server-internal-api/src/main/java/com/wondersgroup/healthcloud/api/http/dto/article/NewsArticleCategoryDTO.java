package com.wondersgroup.healthcloud.api.http.dto.article;

import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticleCategory;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by dukuanxin on 2016/8/20.
 */
@Data
public class NewsArticleCategoryDTO {
    private int id;// 分类ID
    private String c_name;// 分类名称
    private int rank;// 排序
    private int is_visable;// 是否有效(1:有效,0:无效)
    private String update_time;// 更新时间

    public NewsArticleCategoryDTO() {

    }

    public NewsArticleCategoryDTO(NewsArticleCategory category) {

        if (null == category) return;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.id = category.getId();
        this.c_name = category.getC_name();
        this.rank = category.getRank();
        this.is_visable = category.getIs_visable();
        this.update_time = category.getUpdate_time() == null ? null : format.format(category.getUpdate_time());
    }

    public static List<NewsArticleCategoryDTO> infoDTO(List<NewsArticleCategory> list) {

        List<NewsArticleCategoryDTO> infos = Lists.newArrayList();
        for (NewsArticleCategory category : list) {
            infos.add(new NewsArticleCategoryDTO(category));
        }
        return infos;
    }

}
