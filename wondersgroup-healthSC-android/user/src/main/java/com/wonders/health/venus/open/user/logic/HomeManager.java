package com.wonders.health.venus.open.user.logic;

import com.wonders.health.venus.open.user.dao.HomeDao;
import com.wonders.health.venus.open.user.entity.AreaEntity;
import com.wonders.health.venus.open.user.entity.HomeBannerFuncEntity;
import com.wonders.health.venus.open.user.entity.HomeNewsEntity;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

/**
 * 类：${File}
 * 创建者:carrey on 16-8-16.
 * 描述 ：
 */
public class HomeManager {
    private HttpTools mHttpTools;
    private HomeDao mHomeDao;


    public HomeManager() {
        mHttpTools = new HttpTools();
        mHomeDao = new HomeDao();
    }


    /**
     * 底部Tab接口
     * 导航图标 0-背景,1~4-非高亮,5~8-高亮
     */
    public void getSpecialService(String areaCode, String latitude, String longitude, ResponseCallback callBack) {
//        SignRequest signRequest = new SignRequest();
//        signRequest.addQueryStringParameter("areaCode", areaCode);
//        signRequest.addQueryStringParameter("latitude", latitude);
//        signRequest.addQueryStringParameter("longitude", longitude);
//        signRequest.addQueryStringParameter("need", true + "");//是否需要三公里范围内
//        mHttpTools.get(UrlConst.HOME_SPECSERMEASURINGPOINT, signRequest, callBack);
    }

    /**
     * 底部Tab接口
     * 导航图标 0-背景,1~4-非高亮,5~8-高亮
     */
    public void appNavigationBar(ResponseCallback callBack) {
        mHttpTools.get(UrlConst.APP_NAVIGATION_BAR, new SignRequest(), callBack);
    }

    /**
     * 获取首页数据
     * 包括banner，functions，news
     */
    public void getBannerAndFunctionAds(ResponseCallback callBack) {
        mHttpTools.get(UrlConst.HOME_BANNER_FUNCTION_ADS, new SignRequest(), callBack);
    }

    /**
     * 获取banner 广告缓存
     */
    public HomeBannerFuncEntity getBannerAndFunctionAdsCache() {
        return mHomeDao.getBannerAndFunctionAds();
    }

    /**
     * 缓存banner 广告缓存
     */
    public void saveBannerAndFunctionAds(HomeBannerFuncEntity entity) {
        mHomeDao.saveBannerAndFunctionAds(entity);
    }

    /**
     * 获得资讯 问答缓存
     */
    public HomeNewsEntity getHomeNewsCache() {
        return mHomeDao.getHomeNewsCache();
    }

    /**
     * 缓存资讯 问答
     */
    public void saveHomeNews(HomeNewsEntity entity) {
        mHomeDao.CacheHomeNews(entity);
    }

    /**
     * 缓存坐标点
     */
    public void saveLocal(AreaEntity entity) {
        mHomeDao.cacheLocalPoint(entity);
    }

    public AreaEntity getLocalPoint() {
        return mHomeDao.getLocalPoint();
    }

    /**
     * 底部Tab接口
     * 导航图标 0-背景,1~4-非高亮,5~8-高亮
     */
    public void getHomeTips(ResponseCallback callBack) {
//        mHttpTools.get(UrlConst.HOME_APPTIPS, new SignRequest(), callBack);
    }

    /**
     * 问答集锦和新闻资讯
     */
    public void getNewsAndQuestions(ResponseCallback callBack) {
//        mHttpTools.get(UrlConst.HOME_NEWSANDQUESTIONS, new SignRequest(), callBack);
    }

    /**
     * 红点
     */
    public void messageRedPoint(ResponseCallback callback) {
//        SignRequest request = new SignRequest();
//        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
//        mHttpTools.get(UrlConst.MESSAGE_PROMPT, request, callback);
    }

    /**
     * 获得首页数据
     */
    public HomeNewsEntity getHomeDataCache() {
        return mHomeDao.getHomeNewsCache();
    }

    /**
     * 缓存首页数据（banners,news,functionIcons）
     */
    public void saveHomeData(HomeNewsEntity entity) {
        mHomeDao.CacheHomeNews(entity);
    }


}
