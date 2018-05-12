package com.wonders.health.venus.open.user.entity;

import com.wonders.health.venus.open.user.module.home.registration.response.DoctorListResponse;

/**
 * 搜索结果，通用类
 * Created by songzhen on 2016/11/9.
 */
public class SearchResultInfo {
    public static final int TYPE_HOSPITAL = 1;
    public static final int TYPE_DOCTOR = 2;
    public static final int TYPE_ARTICLE = 3;
    public int type;
    public String title;
    public HospitalInfo hospitals;
    public DoctorListResponse doctors;
    public SearchArticleData articles;
    public String loadmore;
}
