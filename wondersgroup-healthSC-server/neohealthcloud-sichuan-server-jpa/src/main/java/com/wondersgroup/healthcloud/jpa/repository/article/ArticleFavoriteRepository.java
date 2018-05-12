package com.wondersgroup.healthcloud.jpa.repository.article;

import com.wondersgroup.healthcloud.jpa.entity.article.ArticleFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dukuanxin on 2016/8/18.
 */
public interface ArticleFavoriteRepository extends JpaRepository<ArticleFavorite, String> {
    @Query(nativeQuery = true, value = "select * from app_tb_article_favorite where user_id = ?1")
    List<ArticleFavorite> queryByUid(String uid);

    @Query(nativeQuery = true, value = "select * from app_tb_article_favorite where user_id = ?1 and article_id=?2")
    List<ArticleFavorite> queryByUidAndArticleId(String uid, int articleId);

}
