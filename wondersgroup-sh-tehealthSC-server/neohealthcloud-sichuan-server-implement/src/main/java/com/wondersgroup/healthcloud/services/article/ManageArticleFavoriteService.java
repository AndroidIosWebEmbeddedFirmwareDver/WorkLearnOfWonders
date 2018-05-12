package com.wondersgroup.healthcloud.services.article;


import com.wondersgroup.healthcloud.jpa.entity.article.ArticleFavorite;

import java.util.List;
import java.util.Map;

public interface ManageArticleFavoriteService {

    public Integer addFavorite(ArticleFavorite favorite);

    public Integer updateFavorite(ArticleFavorite favorite);

    public Integer deleteFavorite(int id);

    public List<ArticleFavorite> queryArticleFavoriteByUserId(Map<String, Object> parm);

    public ArticleFavorite queryArticleFavoriteById(int id);

    public List<ArticleFavorite> queryAllArticleFavListByUserId(String uid);

    /**
     * 默认获取的是慢病的文章
     *
     * @param uid
     * @return
     */
    public List<Integer> getUserArticleFavIds(String uid);

    public List<Integer> getUserArticleFavIds(String uid, Integer type);

    public int getCountOfFavoriteList(String uid);

    public ArticleFavorite queryArticleFavoriteByObj(ArticleFavorite favorite);

    public ArticleFavorite queryArticleFavoriteByArticleId(int articleId);

    public ArticleFavorite queryByUidAndArticleId(String uid, int articleId);

    public void deleteArticleFavorite(ArticleFavorite articleFavorite);
}
