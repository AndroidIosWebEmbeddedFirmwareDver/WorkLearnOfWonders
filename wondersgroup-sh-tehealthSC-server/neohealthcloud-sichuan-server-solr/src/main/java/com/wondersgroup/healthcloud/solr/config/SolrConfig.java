package com.wondersgroup.healthcloud.solr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = {
        "com.wondersgroup.healthcloud.solr"}, schemaCreationSupport = true, multicoreSupport = true)
public class SolrConfig {

}
