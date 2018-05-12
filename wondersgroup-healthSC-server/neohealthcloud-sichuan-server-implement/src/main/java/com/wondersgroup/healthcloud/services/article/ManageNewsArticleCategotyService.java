package com.wondersgroup.healthcloud.services.article;


import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticleCategory;

import java.util.List;

/**
 * Created by dukuanxin on 2016/8/15.
 */
public interface ManageNewsArticleCategotyService {

    public int updateNewsArticleCategory(NewsArticleCategory newsArticleCategory);

    public List<NewsArticleCategory> findAppNewsCategoryByArea(String area);

    public List<NewsArticleCategory> findNewsCategoryByArea(String area);

    public NewsArticleCategory findNewsCategory(int id);

    public List<Integer> queryCategoryBelongArticle(int articleId, String areaCode);

    //文章和分类解除关系
    public int relieveCategory(int id, String areaCode);
}
