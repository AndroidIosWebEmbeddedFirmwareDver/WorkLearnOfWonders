package com.wonders.health.venus.open.doctor.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.BuildConfig;
import com.wonders.health.venus.open.doctor.entity.AccountChangeEvent;
import com.wonders.health.venus.open.doctor.logic.SignRequest;
import com.wondersgroup.hs.healthcloud.common.WebViewFragment;
import com.wondersgroup.hs.healthcloud.common.http.BasicNameValuePair;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.LogUtils;
import com.wondersgroup.hs.healthcloud.common.util.SchemeUtil;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 类描述：公共webview
 * 创建人：Bob
 * 创建时间：2015/7/9 16:21
 */
public class WebViewActivity extends BaseActivity {
    public final static String EXTRA_URL = WebViewFragment.EXTRA_URL;
 //   public final static String EXTRA_SHOW_BOTTOM_BAR = WebViewFragment.EXTRA_SHOW_BOTTOM_BAR;
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_SHOW_TITLE = "is_show_title";
    public final static String EXTRA_NEED_AUTH = "isToken";
    public final static String EXTRA_NAV_BAR_COLOR = "nav_bar_color";
    public final static String EXTRA_NEED_GESTURE_CHECK = "need_gesture_check";
    public final static String EXTRA_RIGHT_BUTTON_TITLE = "right_button_title";
    public final static String EXTRA_RIGHT_BUTTON_URL = "right_button_url";

    public final static String ARTICLE_ID = "id";
    public final static String FOR_TYPE = "for_type";

    protected WebViewFragment mWebViewFragment;
    private ImageView mCollectView;

    private Map<String, String> mQueryMaps;

    private String mUrl;
    private String mTitle;
    private boolean mShowBottomBar;
    private boolean mShowTitle;
    private boolean mCacheEnable;

    private String mId;
    private String mForType;
    private boolean mNeedAuth;

    private boolean mIsFavor;
    private boolean mNeedGestureCheck;
    private IntentFilter mFilter;
    private BroadcastReceiver mBatInfoReceiver;
    private String mRightButtonTitle;
    private String mRightButtonUrl;
//    private WebViewResponse mArticleShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mShowTitle = getIntent().getBooleanExtra(EXTRA_SHOW_TITLE, true);
        mNeedGestureCheck =getIntent().getBooleanExtra(EXTRA_NEED_GESTURE_CHECK ,false);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mWebViewFragment = new WebViewFragment();
        ft.replace(com.wondersgroup.hs.healthcloud.common.R.id.content, mWebViewFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
     //   mShowBottomBar = getIntent().getBooleanExtra(EXTRA_SHOW_BOTTOM_BAR, false);
        mNeedAuth = getIntent().getBooleanExtra(EXTRA_NEED_AUTH, false);
        mCacheEnable = getIntent().getBooleanExtra(WebViewFragment.EXTRA_CACHE_ABLE, true);
        mRightButtonTitle = getIntent().getStringExtra(EXTRA_RIGHT_BUTTON_TITLE);
        mRightButtonUrl = getIntent().getStringExtra(EXTRA_RIGHT_BUTTON_URL);

        if (TextUtils.isEmpty(mUrl) && !URLUtil.isNetworkUrl(mUrl)) {
            finish();
            return;
        }
        mWebViewFragment.setCacheEnable(mCacheEnable);

        Uri uri = Uri.parse(mUrl);
        if (uri.isOpaque()) {
            finish();
            return;
        }
        mQueryMaps = new HashMap<>();
        // 夜间模式判断
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            if (mQueryMaps.isEmpty()) {
                mUrl += "?isnight=true";
            } else {
                mUrl += "&isnight=true";
            }
        }
        Set<String> paramSet = SchemeUtil.getQueryParameterNames(uri);
        if (paramSet != null) {
            Iterator<String> iterator = paramSet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                mQueryMaps.put(key, uri.getQueryParameter(key));
            }
        }

        LogUtils.d("bacy" + mUrl);

        if (!mNeedAuth) {
            mNeedAuth = "1".equals(mQueryMaps.get(EXTRA_NEED_AUTH));
        }

//        // 如果是眼防url。带上token
        if (!mNeedAuth) {
            mNeedAuth = mUrl.contains(BuildConfig.H5_IP);
        }

        // 根据h5请求参数设置本地nav背景色
        if (mQueryMaps.containsKey(EXTRA_NAV_BAR_COLOR)) {
            if (mTitleBar != null) {
                try {
                    // 容错处理
                    String colorStr = mQueryMaps.get(EXTRA_NAV_BAR_COLOR);
                    if (!colorStr.startsWith("#")) {
                        colorStr = "#" + colorStr;
                    }
                    int color = Color.parseColor(colorStr);
                    mTitleBar.setBackgroundColor(color);
                    mWebViewFragment.setProgressColor(color);
                } catch (Exception e) {

                }
            }
        }

        mId = uri.getQueryParameter(ARTICLE_ID);
        mForType = uri.getQueryParameter(FOR_TYPE);

        if (mNeedAuth) {
            setHeaders(mUrl);
            mWebViewFragment.addUrlFilters(new WebViewFragment.UrlFilter() {
                @Override
                public boolean accept(String url) {
                    setHeaders(url);
                    return true;
                }
            });
        }

        mWebViewFragment.loadUrl(mUrl);
   //     mWebViewFragment.showBottomBar(mShowBottomBar);
        mWebViewFragment.setPageResponseCallback(new ResponseCallback<String>() {

            @Override
            public void onStart() {
                super.onStart();
//                loadData();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

        });

        if (mTitleBar != null) {
            if (TextUtils.isEmpty(mTitle)) {
                mWebViewFragment.setTitleResponseCallback(new ResponseCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        super.onSuccess(s);
                        mTitle = s;
                        mTitleBar.setTitle(s);
                    }
                });
            } else {
                mTitleBar.setTitle(mTitle);
            }
            //显示右边的按钮
            if (!TextUtils.isEmpty(mRightButtonTitle)) {
                mTitleBar.addAction(new TitleBar.TextAction(mRightButtonTitle) {
                    @Override
                    public void performAction(View view) {
                        if (!TextUtils.isEmpty(mRightButtonUrl)) {
                            new WebViewActivity.Builder(WebViewActivity.this).setUrl(mRightButtonUrl).create();
                        }
                    }
                });
            }
        }

    }

//    private void loadData() {
//        if ("article".equals(mForType)) {
//            if (mQueryMaps == null) return;
//
//            UserManager.getInstance().checkIsFavor(mQueryMaps, new ResponseCallback<WebViewResponse>() {
//                @Override
//                public void onSuccess(WebViewResponse t) {
//                    super.onSuccess(t);
//                    if (t != null) {
//                        mArticleShare = t;
//                        mIsFavor = t.is_favorite;
//                        mTitleBar.removeAllActions();
//                        if (t.share != null) {
//                            mTitleBar.addAction(new TitleBar.ImageAction(R.mipmap.ic_share) {
//                                @Override
//                                public void performAction(View view) {
//                                    if (mArticleShare.share != null) {
//                                        ShareManager shareManager = new ShareManager();
//                                        shareManager.showShareDialog(WebViewActivity.this, mArticleShare.share, null);
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }
//
//                @Override
//                public boolean isShowNotice() {
//                    return false;
//                }
//            });
//        }
//
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initData(null);
    }
    private void setHeaders(String url) {
        SignRequest request = new SignRequest();
        request.setPath(url);
        ArrayList<BasicNameValuePair> items = request.getSignHeaders();
        HashMap<String, String> map = new HashMap<>();
        if (items != null) {
            for (BasicNameValuePair item : items) {
                map.put(item.getName(), item.getValue());
            }
        }
        mWebViewFragment.setHeaders(map);
    }


    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(mWebViewFragment.currentUrl)) {
            // 如果连接带finish=true参数，点击返回，直接退出浏览器
            if (mWebViewFragment.currentUrl.toLowerCase().contains("finish=true")) {
                super.onBackPressed();
                return;
            }
        }
        if (!mWebViewFragment.handleGoBack()) {
            super.onBackPressed();
        }
    }

    @Override
    protected boolean isShowTitleBar() {
        return mShowTitle;
    }

    public static class Builder {
        private Intent intent;
        private Context context;

        public Builder(Context context) {
            this.context = context;
            intent = new Intent();
        }

        public Builder setTitle(String title) {
            intent.putExtra(WebViewActivity.EXTRA_TITLE, title);
            return this;
        }

        public Builder setHasTitle(boolean hasTitle) {
            intent.putExtra(WebViewActivity.EXTRA_SHOW_TITLE, hasTitle);
            return this;
        }

       /* public Builder setHasBottomBar(boolean hasBar) {
            intent.putExtra(WebViewActivity.EXTRA_SHOW_BOTTOM_BAR, hasBar);
            return this;
        }*/

        /**
         * 显示右边按钮
         * @param title 名字
         * @param url 用来跳转的url
         * @return
         */
        public Builder setShowRightButton(String title, String url) {
            intent.putExtra(WebViewActivity.EXTRA_RIGHT_BUTTON_TITLE, title);
            intent.putExtra(WebViewActivity.EXTRA_RIGHT_BUTTON_URL, url);
            return this;
        }

        public Builder setUrl(String url) {
            intent.putExtra(WebViewActivity.EXTRA_URL, url);
            return this;
        }

        public Builder setNeedAuth(boolean needAuth) {
            intent.putExtra(WebViewActivity.EXTRA_NEED_AUTH, needAuth);
            return this;
        }

        public Builder setNeedGestureCheck(boolean needGestureCheck) {
            intent.putExtra(WebViewActivity.EXTRA_NEED_GESTURE_CHECK, needGestureCheck);
            return this;
        }

        public Builder setCacheEnable(boolean cacheEnable) {
            intent.putExtra(WebViewFragment.EXTRA_CACHE_ABLE, cacheEnable);
            return this;
        }

        public Builder setGestureChecked(boolean gestureChecked) {
            intent.putExtra("gesture_checked", gestureChecked);
            return this;
        }

        public Intent getIntent() {
            return intent;
        }

        public void create() {
            intent.setClass(context, WebViewActivity.class);
            if (context instanceof BaseActivity) {
                ((BaseActivity) context).startActivity(intent, true);
            }else{
                context.startActivity(intent);
            }
        }

    }
    // 如果用户登录成功了，退出登录界面
    public void onEvent(AccountChangeEvent event) {
//        super.onEvent(event);
        setHeaders(mWebViewFragment.currentUrl);
        mWebViewFragment.reload();
//        loadData();
    }
}
