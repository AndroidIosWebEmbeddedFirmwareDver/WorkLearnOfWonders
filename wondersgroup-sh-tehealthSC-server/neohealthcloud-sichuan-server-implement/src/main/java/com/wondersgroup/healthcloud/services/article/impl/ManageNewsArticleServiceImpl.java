package com.wondersgroup.healthcloud.services.article.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wondersgroup.healthcloud.common.utils.AppUrlH5Utils;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;
import com.wondersgroup.healthcloud.jpa.repository.article.NewsArticleRepo;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleService;
import com.wondersgroup.healthcloud.services.article.dto.NewsArticleListAPIEntity;

/**
 * Created by Administrator on 2015/12/30.
 */
@Service("manageNewsArticleServiceImpl")
public class ManageNewsArticleServiceImpl implements ManageNewsArticleService {

    @Autowired
    private NewsArticleRepo newsArticleRepo;

    @Autowired
    private AppUrlH5Utils appUrlH5Utils;

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jt;


    @Override
    public NewsArticle findArticleInfoById(int id, String area) {
        NewsArticle newsArticle = newsArticleRepo.queryArticleById(id);
        if (newsArticle == null) return null;
        String url = appUrlH5Utils.buildNewsArticleView(id, area);
        newsArticle.setUrl(url);
        return newsArticle;
    }

    @Override
    public List<NewsArticle> findArticleListByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public List<NewsArticle> findArtileListByKeys(Map<String, Object> parm) {
        return null;
    }


    @Override
    public int updateNewsAritile(NewsArticle article) {

        Date date = new Date();
        article.setUpdate_time(date);
        if (StringUtils.isEmpty(article.getBy_area())) {
            article.setBy_area("5101");
        }
        return newsArticleRepo.saveAndFlush(article).getId();
    }

    @Override
    public int updateNewsAritilePv(NewsArticle article) {

        return newsArticleRepo.saveAndFlush(article).getId();
    }

    @Override
    public List<NewsArticle> findListByCategoryId(String categoryId, int pageNo, int pageSize) {

        return newsArticleRepo.queryNewsArticleByCatId(categoryId, pageNo, pageSize);
    }

    @Override
    public List<NewsArticle> findAppShowListByCategoryId(String categoryId, int pageNo, int pageSize) {
        return newsArticleRepo.queryArticleListByCatId(categoryId, pageNo, pageSize);
    }

    @Override
    public int countArticleByCategoryId(String categoryId) {
        return 0;
    }


    @Override
    public int addViewPv(Integer id) {
        return 0;
    }

    @Override
    public List<NewsArticle> findAppShowListByKeyword(String area, String word, int pageNo, int pageSize) {
        return newsArticleRepo.findAppShowListByKeyword(area, word, pageNo * pageSize, pageSize + 1);
    }

    @Override
    public List<NewsArticleListAPIEntity> findArticleForFirst(String areaId, int pageNo, int pageSize) {
        List<NewsArticle> list = newsArticleRepo.queryNewsArticleForHomePage(areaId, pageNo * pageSize, pageSize);

        return getArticleEntityList(list, areaId);
    }

    @Override
    public List<NewsArticleListAPIEntity> findCollectionArticle(String uid, int pageNo, int pageSize, String area) {

        List<NewsArticle> newsArticles = newsArticleRepo.queryCollectionNewsArticle(uid, pageNo * pageSize, pageSize + 1);

        return getArticleEntityList(newsArticles, area);
    }

    @Override
    public List<Map<String, Object>> queryArticleList(Map<String, Object> param) {

        String sql = makeSql(param, 1);
        int pageSize = (Integer) param.get("pageSize");
        int pageNo = (Integer) param.get("pageNo");
        sql += " limit " + (pageNo - 1) * pageSize + "," + pageSize;
        List<Map<String, Object>> results = this.getJt().queryForList(sql);
        return results;
    }

    @Override
    public int getCount(Map param) {
        String sql = makeSql(param, 2);
        return this.getJt().queryForObject(sql, Integer.class);
    }


    private List<NewsArticleListAPIEntity> getArticleEntityList(List<NewsArticle> resourceList, String area) {

        if (null == resourceList || resourceList.size() == 0) {
            return null;
        }
        List<NewsArticleListAPIEntity> list = new ArrayList<>();
        for (NewsArticle article : resourceList) {
            list.add(new NewsArticleListAPIEntity(article, appUrlH5Utils, area));
        }
        return list;
    }

    //组装sql
    private String makeSql(Map searchParam, int type) {
        StringBuffer sql = new StringBuffer();

        sql.append("select ");
        if (type == 2) {
            sql.append(" count(*) ");
        } else {
            if (null == searchParam.get("areaCode")) {
                sql.append(" * ");
            } else {
                sql.append(" t1.id as article_id,t2.id,t1.title,t1.keyword,t2.update_time,t2.is_visable,t3.c_name ");
            }
        }
        if (null == searchParam.get("areaCode")) {
            sql.append(" from app_tb_article where 1=1");
            Iterator it = searchParam.keySet().iterator();
            while (it.hasNext()) {

                String key = (String) it.next();

                if ("startTime".equals(key) && !"".equals(searchParam.get(key))) {
                    sql.append(" and update_time" + ">='" + searchParam.get(key) + "'");
                }
                if ("endTime".equals(key) && !"".equals(searchParam.get(key))) {
                    sql.append(" and update_time" + "<='" + searchParam.get(key) + "'");
                }
                if ("title".equals(key) && !"".equals(searchParam.get(key))) {
                    sql.append(" and title LIKE '%" + searchParam.get(key) + "%'");
                }
            }
            sql.append(" order by update_time desc");
        } else {//分区域查询文章
            sql.append(" from app_tb_article t1 left join app_tb_article_area t2 on t1.id=t2.article_id left join app_tb_article_category t3 on t2.category_id=t3.id where t2.main_area='" +
                    searchParam.get("areaCode") + "'");
            Iterator it = searchParam.keySet().iterator();
            while (it.hasNext()) {

                String key = (String) it.next();

                if ("startTime".equals(key) && !"".equals(searchParam.get(key))) {
                    sql.append(" and t2.update_time" + ">='" + searchParam.get(key) + "'");
                } else if ("endTime".equals(key) && !"".equals(searchParam.get(key))) {
                    sql.append(" and t2.update_time" + "<='" + searchParam.get(key) + "'");
                } else if ("title".equals(key) && !"".equals(searchParam.get(key))) {
                    sql.append(" and t1.title LIKE '%" + searchParam.get(key) + "%'");
                } else if ("isVisable".equals(key) && !"".equals(searchParam.get(key))) {
                    sql.append(" and t2.is_visable=" + searchParam.get(key));
                } else if ("categoryId".equals(key) && !"".equals(searchParam.get(key))) {
                    sql.append(" and t2.category_id=" + searchParam.get(key));
                }
            }
            sql.append(" order by t2.update_time desc");
        }

        return sql.toString();
    }

    private JdbcTemplate getJt() {
        if (jt == null) {
            jt = new JdbcTemplate(dataSource);
        }
        return jt;
    }
}
