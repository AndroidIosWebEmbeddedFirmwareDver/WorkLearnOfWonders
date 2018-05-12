package com.wondersgroup.healthcloud.api.http.controllers.h5.article;

import com.wondersgroup.healthcloud.api.http.dto.article.NewsArticleCategoryDTO;
import com.wondersgroup.healthcloud.api.http.dto.article.NewsArticleEditDTO;
import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.article.ArticleArea;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticleCategory;
import com.wondersgroup.healthcloud.jpa.repository.article.ArticleAreaRepository;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleCategotyService;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dukuanxin on 2016/8/12.
 */
@RestController("H5BackArticleController")
@RequestMapping("/h5/back/article")
public class BackArticleController {

    @Resource
    private ManageNewsArticleCategotyService manageNewsArticleCategotyService;
    @Resource
    private ManageNewsArticleService manageNewsArticleServiceImpl;

    /**
     * 区域资讯详情
     *
     * @return
     */
    @GetMapping("/areaInfo")
    public JsonResponseEntity articleInfoByArea(@RequestParam(required = true) Integer id, @RequestParam(required = false, defaultValue = "5101") String areaCode) {
        JsonResponseEntity response = new JsonResponseEntity();

        NewsArticle articleInfo = manageNewsArticleServiceImpl.findArticleInfoById(id, "");
        if (articleInfo == null) {
            return response;
        }
        List<NewsArticleCategory> appNewsCategory = manageNewsArticleCategotyService.findNewsCategoryByArea(areaCode);
        List<Integer> integers = manageNewsArticleCategotyService.queryCategoryBelongArticle(id, areaCode);
        List<NewsArticleCategory> belongAreaCategory = new ArrayList<>();
        for (NewsArticleCategory category : appNewsCategory) {
            category.setBelong_article("1");
            for (Integer categoryId : integers) {
                if (categoryId == category.getId()) category.setBelong_article("0");
            }
            belongAreaCategory.add(category);
        }
        articleInfo.setCategories(belongAreaCategory);
        response.setData(articleInfo);
        return response;
    }
}
