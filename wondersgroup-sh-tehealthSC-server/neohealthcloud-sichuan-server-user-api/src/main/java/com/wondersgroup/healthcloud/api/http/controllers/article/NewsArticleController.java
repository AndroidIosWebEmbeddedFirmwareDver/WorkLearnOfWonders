package com.wondersgroup.healthcloud.api.http.controllers.article;

import com.wondersgroup.healthcloud.api.http.dto.article.NewsCateArticleListAPIEntity;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.common.utils.AppUrlH5Utils;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticleCategory;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleCategotyService;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleService;
import com.wondersgroup.healthcloud.services.article.dto.NewsArticleListAPIEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
@RestController
@RequestMapping("/api/article")
public class NewsArticleController {

    @Resource
    private ManageNewsArticleCategotyService manageNewsArticleCategotyService;

    @Resource
    private ManageNewsArticleService manageNewsArticleServiceImpl;

    @Autowired
    private AppUrlH5Utils appUrlH5Utils;

    /**
     * 资讯列表
     *
     * @return
     */
    @WithoutToken
    @RequestMapping(value = "/articleCategoty", method = RequestMethod.GET)
    @VersionRange
    public JsonResponseEntity<List<NewsCateArticleListAPIEntity>> getArticleCategoty(@RequestParam(required = false, defaultValue = "5101") String area) {
        JsonResponseEntity<List<NewsCateArticleListAPIEntity>> response = new JsonResponseEntity<>();
        response.setData(this.getCatArticleEntityList(area));
        return response;
    }

    /**
     * 资讯文章列表
     *
     * @return
     */
    @RequestMapping(value = "/articleList", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonListResponseEntity<NewsArticleListAPIEntity> articleList(
            @RequestParam(required = false, value = "5101") String area,
            @RequestParam(required = true) String cat_id,
            @RequestParam(required = false, defaultValue = "1") String flag) {

        int pageNo = Integer.valueOf(flag);
        int pageSize = 10;
        List<NewsArticle> resourceList = this.manageNewsArticleServiceImpl.findAppShowListByCategoryId(cat_id, (pageNo - 1) * pageSize, pageSize + 1);//获取文章分类下面的文章
        List<NewsArticleListAPIEntity> list = this.getArticleEntityList(resourceList, area);//获取文章分类下面的文章
        Boolean hasMore = false;
        if (null != list && list.size() > pageSize) {
            list = list.subList(0, pageSize);
            hasMore = true;
        } else {
            flag = null;
        }
        if (hasMore) {
            flag = String.valueOf(pageNo + 1);
        }

        JsonListResponseEntity<NewsArticleListAPIEntity> response = new JsonListResponseEntity<>();
        response.setContent(list, hasMore, null, flag);
        return response;
    }

    /**
     * 获取首页资讯
     *
     * @return
     */
    @GetMapping("/homePage")
    @WithoutToken
    @VersionRange
    public JsonResponseEntity getHomePageArticle(@RequestParam(required = false, defaultValue = "5101") String area) {
        JsonResponseEntity response = new JsonResponseEntity();
        List<NewsArticleListAPIEntity> articleForFirst = manageNewsArticleServiceImpl.findArticleForFirst(area, 0, 12);
        response.setData(articleForFirst);
        return response;
    }

    /**
     * 获取资讯分类文章
     */
    private List<NewsCateArticleListAPIEntity> getCatArticleEntityList(String area) {

        List<NewsArticleCategory> resourList = this.manageNewsArticleCategotyService.findAppNewsCategoryByArea(area);

        if (null == resourList || resourList.isEmpty()) {
            return null;
        }

        List<NewsCateArticleListAPIEntity> list = new ArrayList<>();
        for (NewsArticleCategory category : resourList) {//遍历文章分类,获取分类下面的文章
            NewsCateArticleListAPIEntity cateEntity = new NewsCateArticleListAPIEntity(category);
            List<NewsArticle> resourceList = this.manageNewsArticleServiceImpl.findAppShowListByCategoryId(cateEntity.getCat_id(), 0, 12);//获取文章分类下面的文章
            List<NewsArticleListAPIEntity> articleList = this.getArticleEntityList(resourceList, area);//获取文章分类下面的文章
            /*Boolean hasMore = false;
            if (null != articleList && articleList.size() > 10){
                articleList = articleList.subList(0, 10);
                hasMore = true;
            }
            cateEntity.setMore(hasMore);
            if (hasMore){
                String flag = String.valueOf(2);
                cateEntity.setMore_params(null, flag);
            }*/
            cateEntity.setList(articleList);
            list.add(cateEntity);
        }
        return list;
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
}
