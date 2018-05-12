package com.wondersgroup.healthcloud.services.article.impl;

import com.wondersgroup.healthcloud.jpa.entity.article.ArticleFavorite;
import com.wondersgroup.healthcloud.jpa.repository.article.ArticleFavoriteRepository;
import com.wondersgroup.healthcloud.services.article.ManageArticleFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("manageArticleFavoriteService")
public class ManageArticleFavoriteServiceImpl implements ManageArticleFavoriteService {

    @Autowired
    private ArticleFavoriteRepository articleFavoriteRepository;

    @Override
    public Integer addFavorite(ArticleFavorite favorite) {
        return articleFavoriteRepository.saveAndFlush(favorite).getId();
    }

    @Override
    public Integer updateFavorite(ArticleFavorite favorite) {
        return null;
    }

    @Override
    public Integer deleteFavorite(int id) {
        return null;
    }

    @Override
    public List<ArticleFavorite> queryArticleFavoriteByUserId(Map<String, Object> parm) {
        return null;
    }

    @Override
    public ArticleFavorite queryArticleFavoriteById(int id) {
        return null;
    }

    @Override
    public List<ArticleFavorite> queryAllArticleFavListByUserId(String uid) {
        return articleFavoriteRepository.queryByUid(uid);
    }

    @Override
    public List<Integer> getUserArticleFavIds(String uid) {
        return null;
    }

    @Override
    public List<Integer> getUserArticleFavIds(String uid, Integer type) {
        return null;
    }

    @Override
    public int getCountOfFavoriteList(String uid) {
        return 0;
    }

    @Override
    public ArticleFavorite queryArticleFavoriteByObj(ArticleFavorite favorite) {
        return null;
    }

    @Override
    public ArticleFavorite queryArticleFavoriteByArticleId(int articleId) {
        return null;
    }

    @Override
    public ArticleFavorite queryByUidAndArticleId(String uid, int articleId) {
        List<ArticleFavorite> articleFavorites = articleFavoriteRepository.queryByUidAndArticleId(uid, articleId);
        if (articleFavorites.size() > 0) {
            return articleFavoriteRepository.queryByUidAndArticleId(uid, articleId).get(0);
        } else {
            return null;
        }
    }

    @Override
    public void deleteArticleFavorite(ArticleFavorite articleFavorite) {
        articleFavoriteRepository.delete(articleFavorite);
    }
}
