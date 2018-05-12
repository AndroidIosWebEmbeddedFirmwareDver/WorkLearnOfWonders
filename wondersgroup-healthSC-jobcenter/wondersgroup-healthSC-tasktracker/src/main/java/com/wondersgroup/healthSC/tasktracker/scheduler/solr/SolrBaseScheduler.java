package com.wondersgroup.healthSC.tasktracker.scheduler.solr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by tanxueliang on 16/11/5.
 */
@Slf4j
public abstract class SolrBaseScheduler {

    @Autowired
    RestTemplate restTemplate;

    @Value("${internal.api.url}")
    String path;

    protected void curl(String api) {
        String url = path + api;
        log.info("SOLR URL:" + url);
        JsonNode node = restTemplate.getForObject(url, JsonNode.class);
        if (node.has("code")) {
            int code = node.get("code").asInt();
            if (code == 0) {
                log.info("调用SOLR同步程序成功");
            }
        }
    }
}
