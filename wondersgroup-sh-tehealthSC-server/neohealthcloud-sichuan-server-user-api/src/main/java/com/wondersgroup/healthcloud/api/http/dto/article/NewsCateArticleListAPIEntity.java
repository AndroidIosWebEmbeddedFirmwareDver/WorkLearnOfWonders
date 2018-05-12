package com.wondersgroup.healthcloud.api.http.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticleCategory;
import com.wondersgroup.healthcloud.services.article.dto.NewsArticleListAPIEntity;

import java.util.List;

/**
 * Created by yanshuai on 15/6/26.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsCateArticleListAPIEntity {
    private String cat_id;
    private String cat_name;
    private Boolean more;
    private MoreParams more_params;
    private List<NewsArticleListAPIEntity> list;

    public NewsCateArticleListAPIEntity(NewsArticleCategory category) {
        this.cat_id = String.valueOf(category.getId());
        this.cat_name = category.getC_name();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class MoreParams {
        public String order;
        public String flag;

        public MoreParams(String order, String flag) {
            this.order = order;
            this.flag = flag;
        }
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public List<NewsArticleListAPIEntity> getList() {
        return list;
    }

    public void setList(List<NewsArticleListAPIEntity> list) {
        this.list = list;
    }

    public Boolean getMore() {
        return more;
    }

    public void setMore(Boolean more) {
        this.more = more;
    }

    public MoreParams getMore_params() {
        return more_params;
    }

    public void setMore_params(MoreParams more_params) {
        this.more_params = more_params;
    }

    public void setMore_params(String order, String flag) {
        this.more_params = new MoreParams(order, flag);
    }

}
