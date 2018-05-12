package com.wonders.health.venus.open.doctor;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wonders.health.venus.open.doctor.module.consultation.ChatHelper;
import com.wondersgroup.hs.healthcloud.common.CommonApp;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/18 17:28
 */
public class BaseApp extends CommonApp {

    public String mCurrentAppVersion;
    public boolean mIsFinishDownload;
    public boolean mIsDownloading;
    public boolean mFirstShowUpdateDialog;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onInit() {
        super.onInit();
        ShareSDK.initSDK(this);

        LogUtils.allowI = LogUtils.allowD = LogUtils.allowV = LogUtils.allowE = LogUtils.allowW = BuildConfig.LOG_DEBUG;
//        try {
//            SpeechUtility.createUtility(this, SpeechConstant.APPID + "=57b2ac37");
//        } catch (Exception e) {
//            LogUtils.e("bacy->" + "讯飞语音初始化失败！");
//        }
        //初始化百度地图
//        SDKInitializer.initialize(this);
        ChatHelper.getInstance().init(this);

        // HTTPS 单向认证
//        new HttpTools().configSSLSocketFactory(JkySSLSocketFactory.getSocketFactory(
//                CommonApp.getApp(), R.raw.certjky));
    }

    @Override
    public void onStart() {
        super.onStart();
//        if (PrefUtil.getBoolean(this, Constant.IS_OPEN_NOTIFY, true)) {
//            PushUtil.startPush(this);
//        }
        mFirstShowUpdateDialog = true;

        // 获取全局变量
//        AppConfigManager.getInstance().getAppConfigInfo(null);

        // 初始化user相关
//        UserManager.getInstance().init();


        //友盟
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(),
//                            PackageManager.GET_META_DATA);
//                    new Umtrack().sendMessage(BaseApp.this, appInfo.metaData.getString("UMENG_APPKEY"));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirstShowUpdateDialog = false;
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
