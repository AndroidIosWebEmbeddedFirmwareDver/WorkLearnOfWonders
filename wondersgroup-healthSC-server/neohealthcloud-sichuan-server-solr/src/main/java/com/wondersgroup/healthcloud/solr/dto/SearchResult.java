package com.wondersgroup.healthcloud.solr.dto;

import lombok.Data;

@Data
public class SearchResult {

    PageResult<HospitalDto> hospitals;

    PageResult<DoctorDto> doctors;

    PageResult<NewsArticleDto> articles;

}
