package com.wonders.health.venus.open.user.logic;

import android.text.TextUtils;

import com.wonders.health.venus.open.user.dao.AppConfigDao;
import com.wonders.health.venus.open.user.dao.LocalDataDao;
import com.wonders.health.venus.open.user.entity.AppConfig;
import com.wonders.health.venus.open.user.util.Constant;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.BaseCallback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.FileUtil;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;

import java.io.File;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/11/3 9:39
 */
public class AppConfigManager {
    private static AppConfigManager sInstance;

    private HttpTools mHttpTools;
    private AppConfigDao mAppConfigDao;
    private String mOldAdPath;
    private boolean mIsLoading;

    private AppConfigManager() {
        mHttpTools = new HttpTools();
        mAppConfigDao = new AppConfigDao();
        mIsLoading = false;

        new LocalDataDao().init();
    }

    public static AppConfigManager getInstance() {
        if (sInstance == null) {
            synchronized (AppConfigManager.class) {
                if (sInstance == null) {
                    sInstance = new AppConfigManager();
                }
            }
        }
        return sInstance;
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public AppConfig.Common getAppConfig() {
        return mAppConfigDao.findAppConfig();
    }

    public AppConfig.Ads getAds() {
        return mAppConfigDao.findAds();
    }

    public AppConfig.Version getVersion() {
        return mAppConfigDao.findVersion();
    }

    public AppConfig.AppUpdate getUpdate() {
        return mAppConfigDao.findUpdate();
    }

    public AppConfig.Share getShare() {
        return mAppConfigDao.findShare();
    }

    /**
     * 获取app应用配置参数
     */
    public void getAppConfigInfo(final Callback callback) {
        SignRequest info = new SignRequest();
        mHttpTools.get(UrlConst.GET_APP_CONFIG, info, new ResponseCallback<AppConfig>() {
            @Override
            public void onSuccess(final AppConfig t) {
                super.onSuccess(t);
                mAppConfigDao.saveAppConfig(t);
                // 处理广告页信息
                handleAds(t.ads);

                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);
                if (callback != null) {
                    callback.failure();
                }
            }

            @Override
            public boolean isShowNotice() {
                return false;
            }
        });


    }

    public boolean isNeedReloadAd(AppConfig.Ads ads, String serverAdUrl) {
        if (ads == null) {
            return true;
        }
        mOldAdPath = getLocalAdsPath(ads.imgUrl);
        if (!new File(mOldAdPath).exists()) {
            return true;
        }
        if (ads.imgUrl.equals(serverAdUrl)) {
            return false;
        }
        return true;
    }

    /**
     * 本地广告图片地址
     *
     * @return
     */
    public static String getLocalAdsPath(String splash_ad) {
        if (TextUtils.isEmpty(splash_ad)) {
            return splash_ad;
        }
        return Constant.SAVE_PATH + "/" + StringUtil.stringToMD5(splash_ad) + ".jpg";
    }


    /**
     * 处理广告页
     *
     * @param ads
     */
    public void handleAds(final AppConfig.Ads ads) {
        if (ads == null) return;

        final String serverAdUrl = ads.imgUrl;
        if (TextUtils.isEmpty(serverAdUrl) || mIsLoading) return;

        if (isNeedReloadAd(getAds(), serverAdUrl)) {
            File dir = new File(Constant.SAVE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            final String adsPath = getLocalAdsPath(serverAdUrl);
            mHttpTools.download(serverAdUrl, adsPath, new SignRequest(), new BaseCallback<File>() {

                @Override
                public void onStart() {
                    super.onStart();
                    mIsLoading = true;
                }

                @Override
                public void onSuccess(File file) {
                    if (!adsPath.equals(mOldAdPath)) {
                        FileUtil.deleteFile(mOldAdPath);
                    }
                    mIsLoading = false;
                }

                @Override
                public void onFailure(Exception e) {
                    LogUtils.e(e.toString());
                    FileUtil.deleteFile(mOldAdPath);
                    mIsLoading = false;
                }

                @Override
                public void onCancelled() {
                    super.onCancelled();
                    FileUtil.deleteFile(mOldAdPath);
                    mIsLoading = false;
                }
            });
        }

    }

    public interface Callback {
        void success();

        void failure();
    }
}
