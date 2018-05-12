package com.wondersgroup.hs.healthcloud.common.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Webview的一些功能封装
 * Created by Bob on 2015/7/9.
 */
public class WebViewUtil {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    public static void initSettings(WebView webview) {
        final WebSettings webSettings = webview.getSettings();
        final Context context = webview.getContext();
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String databasePath = webview.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(databasePath);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(databasePath);
        webSettings.setBuiltInZoomControls(true);
        if (ApiCompatibleUtil.hasHoneycomb()) {
            webSettings.setDisplayZoomControls(false);
        }
        // 宽视野，进来的时候显示所有页面元素
        // webSettings.setUseWideViewPort(true);
        // webSettings.setLoadWithOverviewMode(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(true);
        // 安全
        webSettings.setSavePassword(false);
//         webSettings.setSupportZoom(support);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        // Android webview 从Lollipop(5.0)开始webview默认不允许混合模式，https当中不能加载http资源，需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webview.requestFocus();
        webview.requestFocusFromTouch();
        // 监听下载
        webview.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                }
            }
        });
    }

    /**
     * 清除浏览器的cookie缓存
     * @param context
     */
    public static void clearWebCache(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cm = CookieManager.getInstance();
        cm.removeSessionCookie();
        cm.removeAllCookie();
        CookieSyncManager.getInstance().startSync();
    }

    /**
     * 同步一下cookie
     */
    public static void synCookies(Context context, String url, String cookies) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, cookies);//指定要修改的cookies
        CookieSyncManager.getInstance().sync();
    }
}
