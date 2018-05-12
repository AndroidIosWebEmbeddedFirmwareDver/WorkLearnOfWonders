package com.wonders.health.venus.open.user.dao;

import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.entity.AreaEntity;
import com.wonders.health.venus.open.user.entity.HomeBannerFuncEntity;
import com.wonders.health.venus.open.user.entity.HomeNewsEntity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;

/**
 * 类：${File}
 * 创建者:carrey on 16-8-17.
 * 描述 ：
 */
public class HomeDao {

    //缓存功能区 banner
    public HomeBannerFuncEntity getBannerAndFunctionAds() {
        return PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_HOME_DATA, HomeBannerFuncEntity.class);
    }

    //保存功能区缓存
    public void saveBannerAndFunctionAds(HomeBannerFuncEntity entity) {
        PrefUtil.putJsonObject(BaseApp.getApp(), Constant.KEY_HOME_DATA, entity);
    }

    //获得新闻缓存
    public HomeNewsEntity getHomeNewsCache() {
        return PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_HOME_NEWS, HomeNewsEntity.class);
    }

    //缓存新闻问答
    public void CacheHomeNews(HomeNewsEntity entity) {
        PrefUtil.putJsonObject(BaseApp.getApp(), Constant.KEY_HOME_NEWS, entity);
    }

    public void cacheLocalPoint(AreaEntity entity) {
        PrefUtil.putJsonObject(BaseApp.getApp(), Constant.KEY_HOME_LOCAL, entity);
    }

    public AreaEntity getLocalPoint() {
        return PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_HOME_LOCAL, AreaEntity.class);
    }
}
