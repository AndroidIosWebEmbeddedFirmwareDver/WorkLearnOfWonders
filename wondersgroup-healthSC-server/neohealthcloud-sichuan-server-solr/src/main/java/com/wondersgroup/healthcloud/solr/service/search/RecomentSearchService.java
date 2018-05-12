package com.wondersgroup.healthcloud.solr.service.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wondersgroup.healthcloud.solr.document.SolrHospital;
import com.wondersgroup.healthcloud.solr.repository.SolrHospitalRepository;
import com.wondersgroup.healthcloud.solr.utils.Segmenter;

@Service
public class RecomentSearchService {

    @Autowired
    SolrHospitalRepository solrHospitalRepository;

    public List<SolrHospital> findRecoment(String content) {
        List<SolrHospital> list = null;

        content = replace(content);
        if (StringUtils.isBlank(content)) {
            list = new ArrayList<>();
            return list;
        }

        content = content.toLowerCase();
        List<String> words = Segmenter.getWordByIK(content);
        StringBuilder sb = new StringBuilder();
        for (String string : words) {
            sb.append(string).append(" ");
        }

        list = solrHospitalRepository.findRecoment(content, sb.toString());
        return list;

    }

    public List<SolrHospital> findRecoment(String content, String cityCode) {
        List<SolrHospital> list = null;

        content = replace(content);
        if (StringUtils.isBlank(content)) {
            list = new ArrayList<>();
            return list;
        }

        content = content.toLowerCase();
        List<String> words = Segmenter.getWordByIK(content);
        StringBuilder sb = new StringBuilder();
        for (String string : words) {
            sb.append(string).append(" ");
        }

        list = solrHospitalRepository.findRecoment(content, sb.toString(), cityCode);
        return list;

    }

    private String replace(String str) {
        return str.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]", "");
    }

}
