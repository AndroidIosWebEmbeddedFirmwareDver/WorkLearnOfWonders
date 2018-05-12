package com.wondersgroup.healthcloud.services.article;

import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;
import com.wondersgroup.healthcloud.services.article.dto.NewsArticleListAPIEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by dukuanxin on 2016/8/15.
 */
public interface ManageNewsArticleService {

    public NewsArticle findArticleInfoById(int id, String area);

    public List<NewsArticle> findArticleListByIds(List<Integer> ids);

    public List<NewsArticle> findArtileListByKeys(Map<String, Object> parm);

    public int updateNewsAritile(NewsArticle article);

    public int updateNewsAritilePv(NewsArticle article);

    /**
     * 根据分类查询改分类下面的所有文章
     *
     * @param categoryId
     * @return List
     */
    public List<NewsArticle> findListByCategoryId(String categoryId, int pageNo, int pageSize);

    /**
     * 根据分类查询改分类下面的所有有效的文章
     *
     * @param categoryId
     * @return List
     */
    public List<NewsArticle> findAppShowListByCategoryId(String categoryId, int pageNo, int pageSize);


    /**
     * 根据分类查询改分类下面的所有文章数量
     *
     * @param categoryId
     * @return int
     */
    public int countArticleByCategoryId(String categoryId);

    /**
     * 追加访问量
     *
     * @param id
     * @return
     */
    public int addViewPv(Integer id);

    /**
     * 根据分类查询改分类下面的所有有效的文章
     *
     * @param word
     * @return List
     */
    public List<NewsArticle> findAppShowListByKeyword(String area, String word, int pageNo, int pageSize);

    /**
     * 搜页展示文章
     *
     * @param areaId
     * @return List
     */
    public List<NewsArticleListAPIEntity> findArticleForFirst(String areaId, int pageNo, int pageSize);

    public List<NewsArticleListAPIEntity> findCollectionArticle(String uid, int pageNo, int pageSize, String area);

    /**
     * 分页查询文章
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryArticleList(Map<String, Object> param);

    /**
     * \
     * 查询总记录数
     *
     * @param param
     * @return
     */
    public int getCount(Map param);


}
