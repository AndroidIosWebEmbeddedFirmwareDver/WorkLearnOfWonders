package com.wondersgroup.healthcloud.api.http.controllers.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wondersgroup.healthcloud.api.http.controllers.BaseController;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.services.config.AppConfigService;
import com.wondersgroup.healthcloud.solr.dto.DoctorDto;
import com.wondersgroup.healthcloud.solr.dto.HospitalDto;
import com.wondersgroup.healthcloud.solr.dto.NewsArticleDto;
import com.wondersgroup.healthcloud.solr.dto.PageResult;
import com.wondersgroup.healthcloud.solr.dto.SearchResult;
import com.wondersgroup.healthcloud.solr.service.search.SolrSearchService;
import com.wondersgroup.healthcloud.utils.EmojiUtils;

/**
 * 首页搜索
 *
 * @author tanxueliang
 */
@RestController
@RequestMapping("/api/home/search")
public class HomeSearchController extends BaseController {

    @Autowired
    SolrSearchService solrSearchService;

    @Autowired
    AppConfigService appConfigService;

    /**
     * 首页搜索
     *
     * @param keyword
     * @return
     */
    @VersionRange
    @WithoutToken
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public JsonResponseEntity<SearchResult> search(String keyword) {

        keyword = EmojiUtils.cleanEmoji(keyword);

        SearchResult result = solrSearchService.search(keyword);
        return buildSuccessed(result);
    }

    /**
     * 搜索医院列表
     *
     * @param keyword
     * @param pageable
     * @return
     */
    @VersionRange
    @WithoutToken
    @RequestMapping(value = "/hospital", method = RequestMethod.GET)
    public JsonListResponseEntity<HospitalDto> searchHospital(String keyword, @RequestParam(required = false, defaultValue = "0") int flag) {

        keyword = EmojiUtils.cleanEmoji(keyword);

        Pageable pageable = new PageRequest(flag, 20);
        PageResult<HospitalDto> result = solrSearchService.searchHospital(keyword, pageable);
        return buildListEntity(result.getContent(), result.isMore(), flag);
    }

    /**
     * 搜索医生列表
     *
     * @param keyword
     * @param pageable
     * @return
     */
    @VersionRange
    @WithoutToken
    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    public JsonListResponseEntity<DoctorDto> searchDoctor(String keyword, @RequestParam(required = false, defaultValue = "0") int flag) {

        keyword = EmojiUtils.cleanEmoji(keyword);

        Pageable pageable = new PageRequest(flag, 20);
        PageResult<DoctorDto> page = solrSearchService.searchDoctor(keyword, pageable);
        return buildListEntity(page.getContent(), page.isMore(), flag);
    }

    /**
     * 搜索资讯列表
     *
     * @param keyword
     * @param pageable
     * @return
     */
    @VersionRange
    @WithoutToken
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public JsonListResponseEntity<NewsArticleDto> searchArticle(String keyword, @RequestParam(required = false, defaultValue = "0") int flag) {

        keyword = EmojiUtils.cleanEmoji(keyword);

        Pageable pageable = new PageRequest(flag, 20);
        PageResult<NewsArticleDto> result = solrSearchService.searchArticle(keyword, pageable);
        return buildListEntity(result.getContent(), result.isMore(), flag);
    }


}
