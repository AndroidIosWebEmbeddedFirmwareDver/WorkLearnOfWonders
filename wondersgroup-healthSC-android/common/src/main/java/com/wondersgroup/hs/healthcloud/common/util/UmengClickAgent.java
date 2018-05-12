package com.wondersgroup.hs.healthcloud.common.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.Map;

/**
 * 类描述：点击事件统计基类（默认友盟友盟）
 * 创建人：tanghaihua
 * 创建时间：2015/7/26 17:14
 */
public class UmengClickAgent {
    public static boolean sIsUmengEvent = true;

    /**
     * 启动
     * @param var0
     */
    public static void start(Context var0){
        if(sIsUmengEvent){
            MobclickAgent.onResume(var0);
        }
    }

    /**
     * 停止
     * @param var0
     */
    public static void stop(Context var0){
        if(sIsUmengEvent){
            MobclickAgent.onPause(var0);
        }
    }

    /**
     * 点击事件
     * @param var0
     * @param var1
     */
    public static void onEvent(Context var0, String var1){
        if(sIsUmengEvent){
            MobclickAgent.onEvent(var0, var1);
//            LogUtils.d("=== umeng key: "+var1);
        }
    }

    /**
     * 点击事件（加参数）
     * @param var0
     * @param var1
     * @param var2
     */
    public static void onEvent(Context var0, String var1, Map<String, String> var2) {
        if(sIsUmengEvent){
            MobclickAgent.onEvent(var0, var1, var2);
        }
    }

}
