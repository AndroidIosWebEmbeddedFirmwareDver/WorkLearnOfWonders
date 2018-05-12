package com.wondersgroup.hs.healthcloud.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wondersgroup.hs.healthcloud.common.util.AndroidBug5497Workaround;
import com.wondersgroup.hs.healthcloud.common.util.ApiCompatibleUtil;
import com.wondersgroup.hs.healthcloud.common.util.PermissionType;
import com.wondersgroup.hs.healthcloud.common.util.RequestPermissionAuthorize;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.util.UrlUtil;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;
import com.wondersgroup.hs.healthcloud.common.view.swipeback.SwipeBackActivityHelper;

import java.util.Set;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Activity的基类
 */
public abstract class CommonActivity extends AppCompatActivity {
    protected TitleBar mTitleBar;
    protected View mStatusBarTintView;
    private FrameLayout mContentLayout;
    private RelativeLayout mBaseLayout;
    public boolean mIsResume;
    protected Bundle savedInstanceState;

    private SwipeBackActivityHelper mHelper;
    public RequestPermissionAuthorize permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        // 竖屏固定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        PermissionType[] type = applyPermission();
        if (type != null && type.length != 0 && (permission = RequestPermissionAuthorize.apply(this, type)) != null) {
            return;
        }
        init();
    }

    protected PermissionType[] applyPermission() {
        return null;
    }

    public void init() {
        CommonApp.getApp().start();
        // app启动

        if (isValidate()) {
            // 开启滑动关闭界面
            if (useSwipeBackLayout()) {
                mHelper = new SwipeBackActivityHelper(this);
                mHelper.onActivityCreate();
            }
            convertDataToBundle();
            if (useTintStatusBar()) {
                SystemUtil.setTintStatusBarAvailable(this, isStatusBarDarkMode());
            }
            // 初始化头部
            initTopView();
            // 初始化布局
            initViews();
            // 初始化数据
            initData(savedInstanceState);
        } else {
            finish();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mHelper != null) {
            mHelper.onPostCreate();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        convertDataToBundle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResume = false;
    }

    @Override
    protected void onDestroy() {
        UIUtil.hideProgressBar(this);
        super.onDestroy();
    }

    public void startActivity(Intent intent, boolean useDefaultFlag) {
        if (useDefaultFlag) {
            super.startActivity(intent);
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.startActivity(intent);
    }


    /**
     * 把data的数据转换为bundle的数据
     */
    protected void convertDataToBundle() {
        Uri uri = getIntent().getData();
        if (uri != null && Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            Set<String> params = UrlUtil.getQueryParameterNames(uri);
            if (params != null) {
                for (String key : params) {
                    String value = uri.getQueryParameter(key);
                    if ("true".equals(value) || "false".equals(value)) {
                        getIntent().putExtra(key, Boolean.parseBoolean(value));
                    } else {
                        getIntent().putExtra(key, value);
                    }
                }
            }
        }
    }

    /**
     * 初始化头部
     */
    private void initTopView() {
        if (isUseCustomContent()) {
            // 最外层布局
            mBaseLayout = new RelativeLayout(this);
            if (isShowTitleBar()) {
                initTitleBar();
                // 填入View
                mBaseLayout.addView(mTitleBar);
            } else if (isShowTintStatusBar() && SystemUtil.isTintStatusBarAvailable(this)) {
                initTintStatusBar();
                // 填入View
                mBaseLayout.addView(mStatusBarTintView);
            }
            // 内容布局
            mContentLayout = new FrameLayout(this);
            mContentLayout.setId(R.id.content);
//            mContentLayout.setPadding(0, 0, 0, ApiCompatibleUtil.hasLollipop() ? SystemUtil.getNavigationBarHeight(this) : 0);
            RelativeLayout.LayoutParams layoutParamsContent = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParamsContent.addRule(RelativeLayout.BELOW, R.id.titlebar);
            mBaseLayout.addView(mContentLayout, layoutParamsContent);

            // 设置ContentView
            setContentView(mBaseLayout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            if (SystemUtil.isTintStatusBarAvailable(this)) {
                if ((getWindow().getAttributes().softInputMode & WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) != 0) {
                    AndroidBug5497Workaround.assistActivity(this);
                }
            }
        }
    }

    /**
     * 用指定的View填充主界面(默认有标题)
     *
     * @param contentView 指定的View
     */
    public void setContentView(View contentView) {
        if (isUseCustomContent()) {
            mContentLayout.removeAllViews();
            mContentLayout.addView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        } else {
            super.setContentView(contentView);
        }
    }

    /**
     * 用指定资源ID表示的View填充主界面(默认有标题)
     *
     * @param resId 指定的View的资源ID
     */
    public void setContentView(int resId) {
        setContentView(LayoutInflater.from(this).inflate(resId, null));
        bindUI();
    }

    private void bindUI() {
        if(useButterKnife()){
            ButterKnife.bind(this);
        }
    }

    /**
     * 获得内容布局
     * getContentView
     */
    public View getContentView() {
        if (isShowTitleBar()) {
            return mContentLayout;
        } else {
            return getWindow().getDecorView();
        }
    }

    public ViewGroup getBaseView() {
        if (isUseCustomContent()) {
            return mBaseLayout;
        } else {
            return (ViewGroup) getWindow().getDecorView();
        }
    }

    protected boolean useSwipeBackLayout() {
        return false;
    }

    protected boolean useButterKnife() {
        return true;
    }

    public boolean isSwipeBackEnable() {
        return false;
    }

    public void setSwipeBackEnable(boolean isSwipeBackEnable) {
        if (mHelper != null) {
            mHelper.getSwipeBackLayout().setEnableGesture(isSwipeBackEnable);
        }
    }

    /**
     * 是否使用默认titlebar
     * useTitleBar
     */
    protected boolean isShowTitleBar() {
        return true;
    }

    /**
     * 是否使用默认的statusbar
     *
     * @return
     */
    protected boolean isShowTintStatusBar() {
        return true;
    }

    /**
     * 是否使用沉浸式状态栏
     *
     * @return
     */
    protected boolean useTintStatusBar() {
        return true;
    }

    protected boolean isValidate() {
        return true;
    }

    // 跟布局是否使用自定义布局
    private boolean isUseCustomContent() {
        return isShowTitleBar() || (useTintStatusBar() && ApiCompatibleUtil.hasKitKat());
    }

    /**
     * 是否使用半透明沉浸状态栏
     * @return true 半透明 false 全透明
     */
    protected boolean isStatusBarDarkMode() {
        return false;
    }

    protected void initTitleBar() {
        // 主标题栏
        mTitleBar = new TitleBar(this);
        mTitleBar.setId(R.id.titlebar);
        mTitleBar.setBackgroundResource(R.color.colorPrimary);
        mTitleBar.setTitleColor(getResources().getColor(R.color.colorTitleBar));
        mTitleBar.setActionTextColor(getResources().getColor(R.color.colorActionText));
        mTitleBar.setLeftImageResource(R.mipmap.ic_back);
        mTitleBar.setImmersive(SystemUtil.isTintStatusBarAvailable(this));
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initTintStatusBar() {
        mStatusBarTintView = new View(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, SystemUtil.getStatusBarHeight());
        mStatusBarTintView.setLayoutParams(params);
        mStatusBarTintView.setId(R.id.titlebar);
    }

    /**
     * 初始化view initViews
     */
    protected abstract void initViews();

    /**
     * 初始化数据 initData
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permission != null) {
            if (permission.permissionResult(requestCode, permissions, grantResults)) {
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void setPermission(RequestPermissionAuthorize permission) {
        this.permission = permission;
    }

    public RequestPermissionAuthorize getPermission() {
        return permission;
    }
}
