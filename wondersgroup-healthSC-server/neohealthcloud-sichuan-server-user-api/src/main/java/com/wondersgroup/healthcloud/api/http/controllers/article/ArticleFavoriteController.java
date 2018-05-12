package com.wondersgroup.healthcloud.api.http.controllers.article;

import com.wondersgroup.healthcloud.api.http.dto.article.ShareH5APIDTO;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.article.ArticleFavorite;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;
import com.wondersgroup.healthcloud.services.article.ManageArticleFavoriteService;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleService;
import com.wondersgroup.healthcloud.services.article.dto.NewsArticleListAPIEntity;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1. 添加收藏
 * 2. 获取收藏列表
 * 3. 是否可以收藏判断/分享
 */
@RestController
@RequestMapping("/api/articleFavorite")
public class ArticleFavoriteController {

    private static final Logger log = Logger.getLogger(ArticleFavoriteController.class);
    private static final Integer PAGE_SIZE = 20;//每页个数20

    @Resource
    private ManageArticleFavoriteService manageArticleFavoriteService;


    @Resource
    private ManageNewsArticleService manageNewsArticleServiceImpl;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<NewsArticleListAPIEntity> articleFavoriteList(@RequestParam(required = false, defaultValue = "5101") String area,
                                                                                @RequestParam(required = false) String uid,
                                                                                @RequestParam(required = false, defaultValue = "0") String flag) {

        JsonListResponseEntity<NewsArticleListAPIEntity> body = new JsonListResponseEntity<>();
        int pageSize = 10;
        int pageNo = Integer.valueOf(flag);
        List<NewsArticleListAPIEntity> collectionArticle = manageNewsArticleServiceImpl.findCollectionArticle(uid, pageNo, pageSize, area);
        Boolean hasMore = false;
        if (null != collectionArticle && collectionArticle.size() > pageSize) {
            collectionArticle = collectionArticle.subList(0, pageSize);
            hasMore = true;
        } else {
            flag = null;
        }
        if (hasMore) {
            flag = String.valueOf(pageNo + 1);
        }
        body.setContent(collectionArticle, hasMore, null, flag);
        return body;
    }

    @VersionRange
    @RequestMapping(value = "/addDel", method = RequestMethod.POST)
    public JsonResponseEntity<String> addArticleFavorite(@RequestBody String request) {

        JsonResponseEntity<String> body = new JsonResponseEntity<>();
        JsonKeyReader reader = new JsonKeyReader(request);
        Integer article_id = reader.readInteger("id", false);
        String uid = reader.readString("uid", false);

        ArticleFavorite articleFavorite = manageArticleFavoriteService.queryByUidAndArticleId(uid, article_id);
        if (null != articleFavorite) {
            manageArticleFavoriteService.deleteArticleFavorite(articleFavorite);
            body.setMsg("删除收藏成功");
        } else {
            ArticleFavorite af = new ArticleFavorite();
            af.setUser_id(uid);
            af.setArticle_id(article_id);
            af.setUpdate_time(new Date());
            manageArticleFavoriteService.addFavorite(af);
            body.setMsg("添加收藏成功");
        }

        return body;
    }

    @RequestMapping(value = "/checkIsFavor", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity checkIsFavor(@RequestParam(required = false, defaultValue = "5101") String area,
                                           @RequestParam int id,
                                           @RequestParam(required = false) String uid) {
        JsonResponseEntity body = new JsonResponseEntity<>();

        //是否可以收藏
        Boolean is_fav = false;
        if (!StringUtils.isEmpty(uid)) {
            ArticleFavorite articleFavorite = manageArticleFavoriteService.queryByUidAndArticleId(uid, id);
            if (null != articleFavorite) {
                is_fav = true;
            }
        }

        ShareH5APIDTO h5APIDTO = new ShareH5APIDTO();

        NewsArticle article = manageNewsArticleServiceImpl.findArticleInfoById(id, area);

        if (null != article) {
            h5APIDTO.setDesc(article.getBrief());
            String thumb = "";
            if (null != article.getThumb() && !"".equals(article.getThumb())) {
                thumb = article.getThumb() + "?/1/w/200/h/200";
            }
            h5APIDTO.setThumb(thumb);
            h5APIDTO.setTitle(article.getTitle());
            h5APIDTO.setUrl(article.getUrl());
            h5APIDTO.setId(id);
        }

        Map<String, Object> data = new HashMap<>();
        if (null == h5APIDTO.getTitle()) {
            h5APIDTO = null;
        }
        data.put("is_favorite", is_fav);
        data.put("can_favorite", true);
        data.put("share", h5APIDTO);

        body.setCode(0);
        body.setData(data);

        return body;
    }

}
