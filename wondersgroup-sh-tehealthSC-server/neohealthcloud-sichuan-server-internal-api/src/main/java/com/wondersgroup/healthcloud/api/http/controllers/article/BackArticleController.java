package com.wondersgroup.healthcloud.api.http.controllers.article;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wondersgroup.healthcloud.api.http.dto.article.NewsArticleCategoryDTO;
import com.wondersgroup.healthcloud.api.http.dto.article.NewsArticleEditDTO;
import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.article.ArticleArea;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticle;
import com.wondersgroup.healthcloud.jpa.entity.article.NewsArticleCategory;
import com.wondersgroup.healthcloud.jpa.repository.article.ArticleAreaRepository;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleCategotyService;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleService;

/**
 * Created by dukuanxin on 2016/8/12.
 */
@RestController
@RequestMapping("/back/article")
public class BackArticleController {

    @Resource
    private ManageNewsArticleCategotyService manageNewsArticleCategotyService;
    @Resource
    private ArticleAreaRepository articleAreaRepository;
    @Resource
    private ManageNewsArticleService manageNewsArticleServiceImpl;

    /**
     * 添加/编辑资讯分类
     *
     * @param articleCategory
     */
//    @Admin
    @PostMapping("/categorySave")
    public JsonResponseEntity updateArticleCategory(@RequestBody NewsArticleCategory articleCategory) {
        JsonResponseEntity response = new JsonResponseEntity();
        manageNewsArticleCategotyService.updateNewsArticleCategory(articleCategory);
        response.setMsg("成功");
        return response;
    }

    /**
     * 资讯分类列表
     *
     * @return
     */
    @GetMapping("/categoryList")
    public JsonResponseEntity categoryList(@RequestParam(required = false, defaultValue = "5101") String area) {
        JsonResponseEntity response = new JsonResponseEntity();
        List<NewsArticleCategory> newsCategory = manageNewsArticleCategotyService.findNewsCategoryByArea(area);
        List<NewsArticleCategoryDTO> newsArticleCategoryDTOs = NewsArticleCategoryDTO.infoDTO(newsCategory);
        response.setData(newsArticleCategoryDTOs);
        return response;
    }

    /**
     * 资讯分类详情
     *
     * @return
     */
    @GetMapping("/categoryInfo")
    public JsonResponseEntity categoryInfo(@RequestParam int id) {
        JsonResponseEntity response = new JsonResponseEntity();
        NewsArticleCategory newsCategory = manageNewsArticleCategotyService.findNewsCategory(id);
        NewsArticleCategoryDTO dtos = new NewsArticleCategoryDTO(newsCategory);
        response.setData(dtos);
        return response;
    }

    /**
     * 添加/编辑资讯
     *
     * @param article
     */
    @Admin
    @PostMapping("/save")
    public JsonResponseEntity updateArticle(@RequestBody NewsArticle article) {
        JsonResponseEntity response = new JsonResponseEntity();
        if (StringUtils.isEmpty(article.getThumb())) {
            response.setCode(-1);
            response.setMsg("请添加文章缩略图");
            return response;
        }
        if (article.getTitle().length() > 20) {
            response.setCode(-1);
            response.setMsg("字数过多，限制20个字");
            return response;
        }
        if (article.getBrief().length() > 30) {
            response.setCode(-1);
            response.setMsg("字数过多，限制30个字");
            return response;
        }

        String keyword = article.getKeyword();
        if (!StringUtils.isEmpty(keyword)) {
            article.setKeyword(keyword + ",");
        }

        manageNewsArticleServiceImpl.updateNewsAritile(article);
        response.setMsg("成功");
        return response;
    }

    /**
     * 区域引入资讯
     *
     * @param newsArticleEditDTO
     */
    @Admin
    @PostMapping("/areaArticleUpdate")
    public JsonResponseEntity updateArticle(@RequestBody NewsArticleEditDTO newsArticleEditDTO) {
        JsonResponseEntity response = new JsonResponseEntity();
        if (StringUtils.isEmpty(newsArticleEditDTO.getMain_area())) {
            newsArticleEditDTO.setMain_area("5101");//默认5101(四川区域代码)
        }
        if (!StringUtils.isEmpty(newsArticleEditDTO.getId())) {
            ArticleArea articleArea = articleAreaRepository.findOne(newsArticleEditDTO.getId());
            articleArea.setIs_visable(newsArticleEditDTO.getIs_visable());
            articleAreaRepository.saveAndFlush(articleArea);
            response.setMsg("成功");
            return response;
        }
        int num = manageNewsArticleCategotyService.relieveCategory(newsArticleEditDTO.getArticle_id(), newsArticleEditDTO.getMain_area());
        Date date = new Date();
        if (!StringUtils.isEmpty(newsArticleEditDTO.getCategory_ids())) {
            String categoryids[] = newsArticleEditDTO.getCategory_ids().split(",");
            for (String categoryid : categoryids) {
                ArticleArea articleArea = new ArticleArea();
                articleArea.setArticle_id(newsArticleEditDTO.getArticle_id());
                articleArea.setCategory_id(categoryid);
                articleArea.setIs_visable(newsArticleEditDTO.getIs_visable());
                articleArea.setMain_area(newsArticleEditDTO.getMain_area());
                articleArea.setUpdate_time(date);
                articleAreaRepository.saveAndFlush(articleArea);
            }
        }
        response.setMsg("成功");
        return response;
    }

    /**
     * 资讯详情
     *
     * @return
     */
    @GetMapping("/info")
    public JsonResponseEntity articleInfo(@RequestParam(required = true) Integer id, @RequestParam(required = false) String source) {
        JsonResponseEntity response = new JsonResponseEntity();

        if (!StringUtils.isEmpty(source) && "h5".equals(source)) {
            NewsArticle articleInfoById = manageNewsArticleServiceImpl.findArticleInfoById(id, "");
            int pvNum = articleInfoById.getPv() + 1;
            articleInfoById.setPv(pvNum);
            manageNewsArticleServiceImpl.updateNewsAritilePv(articleInfoById);
        }

        NewsArticle articleInfo = manageNewsArticleServiceImpl.findArticleInfoById(id, "");
        response.setData(articleInfo);
        return response;
    }

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


    /**
     * 资讯列表
     *
     * @return
     */
    @PostMapping("/list")
    public Pager articleList(@RequestBody Pager pager) {
        Map param = new HashMap();
        param.putAll(pager.getParameter());
        if (param.get("type").equals("1")) {
            if (StringUtils.isEmpty(param.get("areaCode"))) {
                param.put("areaCode", "5101");//默认5101(四川区域代码)
            }
        }
        int pageSize = pager.getSize();

        param.put("pageSize", pager.getSize());

        param.put("pageNo", pager.getNumber());
        List list = manageNewsArticleServiceImpl.queryArticleList(param);
        pager.setData(list);
        int total = manageNewsArticleServiceImpl.getCount(param);
        int totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
        pager.setTotalElements(total);
        pager.setTotalPages(totalPage);
        return pager;
    }

}
