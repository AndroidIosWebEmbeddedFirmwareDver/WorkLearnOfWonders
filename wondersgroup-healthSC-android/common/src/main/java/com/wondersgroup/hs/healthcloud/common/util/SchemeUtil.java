package com.wondersgroup.hs.healthcloud.common.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.wondersgroup.hs.healthcloud.common.R;
import com.wondersgroup.hs.healthcloud.common.WebViewFragment;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Created by angelo on 6/15/15.
 */
public final class SchemeUtil {

    /**
     * 根据服务器给过来的url启动Activity
     *
     * @param context
     * @param url
     */
    public static void startActivity(Context context, String url) {
        try {
            context.startActivity(getSchemeIntent(context, url));
        } catch (ActivityNotFoundException e) {
            context.startActivity(getIntent(context, R.string.path_main));
            LogUtils.e("bacy->" + e);
        }
    }

    /**
     * 获取最终scheme跳转intent
     * @param context
     * @param url
     * @return
     */
    public static Intent getSchemeIntent(Context context, String url) {
        Intent intent = null;
        final String scheme = context.getString(R.string.scheme);
        if (!TextUtils.isEmpty(url)) {
            if (url.contains(scheme)) {
                // 跳转内部页面
                String pureScheme = url.substring(url.indexOf(scheme));
                intent = getIntent(pureScheme);
            } else if (URLUtil.isNetworkUrl(url)){
                intent = getIntent(context, R.string.path_webview);
                intent.putExtra(WebViewFragment.EXTRA_URL, url);
            } else {
                intent = getIntent(url);
            }
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        } else {
            intent = getIntent(context, R.string.path_main);
        }
        return intent;
    }

    /**
     * 根据uri的string值获取Intent
     */
    public static Intent getIntent(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        return intent;
    }

    /**
     * 获取Intent
     * 建议应用本身的跳转都统一通过scheme来跳
     */
    public static Intent getIntent(Context context, int path) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getUri(context, path)));
        return intent;
    }


    /**
     * 获取Uri
     * 应用包名做为scheme名称
     */
    public static String getUri(Context context, int path) {
        StringBuilder builder = new StringBuilder();
        builder.append(getHost(context));
        builder.append(context.getString(path));

        return builder.toString();
    }

    public static String getHost(Context context) {
        final String scheme = context.getString(R.string.scheme);
        StringBuilder builder = new StringBuilder(scheme);
        builder.append("://");
        builder.append(getAppName(context));
        return builder.toString();
    }

    public static String getAppName(Context context) {
        return context.getString(R.string.host);
    }

    /**
     * 获取参数传输方式
     *
     * @param activity
     * @return
     */
    public static boolean isBundle(Activity activity) {
        return !activity.getIntent().getExtras().isEmpty();
    }

    /**
     * 去评分 （应用市场）
     *
     * @param context
     */
    public static void toScore(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "您的手机没有安装应用市场", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 去搜索  (应用市场)
     *
     * @param context
     * @param packageName
     */
    public static void toSearhApp(Context context, String packageName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://search?q=" + packageName));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    /**
     * 打电话
     *
     * @param context
     * @param phone
     */
    public static void callPhone(Context context, String phone) {
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent1);
    }

    public static Set<String> getQueryParameterNames(Uri uri) {
        if (uri.isOpaque()) {
            throw new UnsupportedOperationException("This isn't a hierarchical URI.");
        }

        String query = uri.getEncodedQuery();
        if (query == null) {
            return Collections.emptySet();
        }

        Set<String> names = new LinkedHashSet<String>();
        int start = 0;
        do {
            int next = query.indexOf('&', start);
            int end = (next == -1) ? query.length() : next;

            int separator = query.indexOf('=', start);
            if (separator > end || separator == -1) {
                separator = end;
            }

            String name = query.substring(start, separator);
            names.add(Uri.decode(name));

            // Move start to end of name.
            start = end + 1;
        } while (start < query.length());

        return Collections.unmodifiableSet(names);
    }

    /**
     * 使用默认系统安装
     *
     * @param context 上下文
     * @param url     apk路径
     */
    public static void install(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(url)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
