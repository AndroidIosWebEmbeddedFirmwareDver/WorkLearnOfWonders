package com.wonders.health.venus.open.user.dao;

import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.entity.AppConfig;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/11/3 9:41
 */
public class AppConfigDao {
    // 保存全局变量
    public void saveAppConfig(AppConfig appConfig) {
        PrefUtil.putJsonObject(BaseApp.getApp(), Constant.KEY_APP_CONFIG, appConfig);
    }

    // 通用
    public AppConfig.Common findAppConfig() {
        AppConfig appConfig = PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_APP_CONFIG, AppConfig.class);
        if (appConfig != null) {
            return appConfig.common;
        } else {
            return null;
        }
    }

    // 广告
    public AppConfig.Ads findAds() {
        AppConfig appConfig = PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_APP_CONFIG, AppConfig.class);
        if (appConfig != null) {
            return appConfig.ads;
        } else {
            return null;
        }
    }

    // 广告
    public AppConfig.AppUpdate findUpdate() {
        AppConfig appConfig = PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_APP_CONFIG, AppConfig.class);
        if (appConfig != null) {
            return appConfig.appUpdate;
        } else {
            return null;
        }
    }


    // 数据版本
    public AppConfig.Version findVersion() {
        AppConfig appConfig = PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_APP_CONFIG, AppConfig.class);
        if (appConfig != null) {
            return appConfig.version;
        } else {
            return null;
        }
    }

    // 共享信息
    public AppConfig.Share findShare() {
        AppConfig appConfig = PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_APP_CONFIG, AppConfig.class);
        if (appConfig != null) {
            return appConfig.share;
        } else {
            return null;
        }
    }
}
