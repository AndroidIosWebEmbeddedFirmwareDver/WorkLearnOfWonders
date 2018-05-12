package com.wonders.health.venus.open.user.logic;

import android.text.TextUtils;

import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.FinalResponseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.HashMap;

/**
 * 搜索manager
 * Created by songzhen on 2016/11/9.
 */
public class SearchManager {
    private HttpTools mHttpTools;
    public SearchManager(){
        mHttpTools = new HttpTools();
    }

    //关联搜索
    public void searchTipList(String key,ResponseCallback callback){

//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("keyword",key);
//        mHttpTools.get(request,callback);
    }

    //首页搜索
    public void homeSearch(String key,ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("keyword",key);
        mHttpTools.get(UrlConst.SEARCH_HOME,request,callback);
    }

    //搜索医院列表
    public void SearchHospitalList(HashMap<String, String> moreParams,String key,FinalResponseCallback callback){
        SignRequest request = new SignRequest();
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        request.addQueryStringParameter("keyword",key);
        mHttpTools.get(UrlConst.SEARCH_HOSPITALLIST,request,callback);
    }

    //搜索医生列表
    public void SearchDoctorList(HashMap<String, String> moreParams,String key,FinalResponseCallback callback){
        SignRequest request = new SignRequest();
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        request.addQueryStringParameter("keyword", key);
        mHttpTools.get(UrlConst.SEARCH_DOCTORLIST,request, callback);
    }

    //搜索资讯列表
    public void SearchArticleList(HashMap<String, String> moreParams,String key,FinalResponseCallback callback){
        SignRequest request = new SignRequest();
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        request.addQueryStringParameter("keyword", key);
        mHttpTools.get(UrlConst.SEARCH_ARTICLELIST,request, callback);
    }

    //附近医院列表
    public void nearbyHospital(HashMap<String, String> moreParams,String longitude,String latitude,String hospitalName,String cityCode, FinalResponseCallback callback){
        SignRequest request = new SignRequest();
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        request.addQueryStringParameter("longitude", longitude);
        request.addQueryStringParameter("latitude", latitude);
        request.addQueryStringParameter("hospitalName", hospitalName);
        request.addQueryStringParameter("cityCode", TextUtils.isEmpty(cityCode)?"510100000000":cityCode);
        mHttpTools.get(UrlConst.NEARBY_HOSPITALLIST, request, callback);
    }
}
