package com.wondersgroup.healthcloud.api.http.controllers.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wondersgroup.healthcloud.api.http.controllers.BaseController;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.solr.document.SolrHospital;
import com.wondersgroup.healthcloud.solr.dto.RecommendDto;
import com.wondersgroup.healthcloud.solr.service.search.RecomentSearchService;

/**
 * Created by tanxueliang on 16/11/10.
 */
@RestController
@RequestMapping("/api/search")
public class SearchController extends BaseController {

    @Autowired
    RecomentSearchService searchService;

    @WithoutToken
    @VersionRange
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public Object recommend(String wd, @RequestHeader(name = "city-code", required = false) String cityCode) {
        List<RecommendDto> resultList = new ArrayList<>();

        List<SolrHospital> hospitals = null;
        if (StringUtils.isEmpty(cityCode)) {
            hospitals = searchService.findRecoment(wd);
        } else {
            hospitals = searchService.findRecoment(wd, cityCode);
        }

        for (SolrHospital solrHospital : hospitals) {
            resultList.add(new RecommendDto(solrHospital));
        }

        return buildSuccessed(resultList);
    }


}
