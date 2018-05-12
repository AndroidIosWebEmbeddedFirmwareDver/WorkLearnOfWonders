package com.wondersgroup.healthcloud.services.article.impl;

import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticleCategory;
import com.wondersgroup.healthcloud.jpa.repository.article.NewsArticleCategoryRepo;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleCategotyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 */
@Service("manageNewsArticleCategotyServiceImpl")
public class ManageNewsArticleCategotyServiceImpl implements ManageNewsArticleCategotyService {

    @Autowired
    private NewsArticleCategoryRepo newsArticleCategoryRepo;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jt;

    @Override
    public int updateNewsArticleCategory(NewsArticleCategory newsArticleCategory) {
        Date date = new Date();
        newsArticleCategory.setUpdate_time(date);
        if (StringUtils.isEmpty(newsArticleCategory.getBy_area())) {
            newsArticleCategory.setBy_area("5101");
        }
        return newsArticleCategoryRepo.saveAndFlush(newsArticleCategory).getId();
    }

    @Override
    public List<NewsArticleCategory> findAppNewsCategoryByArea(String area) {

        return newsArticleCategoryRepo.queryAppNewsArticleCategory(area);
    }

    @Override
    public List<NewsArticleCategory> findNewsCategoryByArea(String area) {
        return newsArticleCategoryRepo.findNewsCategoryByArea(area);
    }

    @Override
    public NewsArticleCategory findNewsCategory(int id) {

        return newsArticleCategoryRepo.ArticleCategoryById(id);
    }

    @Override
    public List<Integer> queryCategoryBelongArticle(int articleId, String areaCode) {
        String sql = "SELECT DISTINCT(t1.id) FROM app_tb_article_category t1 LEFT JOIN app_tb_article_area t2 ON t2.category_id=t1.id" +
                " WHERE t2.article_id=" + articleId + " AND t2.main_area='" + areaCode + "'";
        return getJt().queryForList(sql, Integer.class);
    }


    @Override
    public int relieveCategory(int id, String areaCode) {

        String sql = "DELETE FROM app_tb_article_area WHERE article_id=" + id + " AND main_area='" + areaCode + "'";

        return getJt().update(sql);
    }

    private JdbcTemplate getJt() {
        if (jt == null) {
            jt = new JdbcTemplate(dataSource);
        }
        return jt;
    }
}
