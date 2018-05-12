package com.wonders.health.venus.open.doctor.module;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;
import com.wondersgroup.hs.healthcloud.common.util.ApiCompatibleUtil;
import com.wondersgroup.hs.healthcloud.common.util.PermissionType;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/11/2 16:54
 */
public class SplashActivity extends BaseActivity {

    private static final int SETTING_NETWORK = 10;
    private static final int DELAY_TIME = 1000;
    private static final int DEFAULT_SHOW_TIME = 2000;
    private View mSplashView;
    private View mCompanyInfoView;
    private ImageView mAdView;
//    private AppConfig.Ads mAds;
    private boolean hasShownAd = false;
    private Dialog mNetworkAvailableDialog;
    private final Handler mHandler = new Handler();
//    private final Runnable mForwardRunnable = new ForwardRunnable(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        if (ApiCompatibleUtil.hasKitKat()) {
            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

//        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_splash);
        mSplashView = findViewById(R.id.splashView);
        mSplashView.setBackgroundResource(R.drawable.splashscreen);
//        mCompanyInfoView = findViewById(R.id.company_info);
        mAdView = (ImageView) findViewById(R.id.adView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        process();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mHandler != null) {
//            mHandler.removeCallbacks(mForwardRunnable);
//        }
    }

    public void process() {

//        if (AppConfigManager.getInstance().getAppConfig() == null) {
//            if (NetworkUtil.isNetworkAvailable(this)) {
//
//                AppConfigManager.getInstance().getAppConfigInfo(new AppConfigManager.Callback() {
//                    @Override
//                    public void success() {
//                        forward(DELAY_TIME - 500);
//                    }
//
//                    @Override
//                    public void failure() {
//                        UIUtil.toastShort(SplashActivity.this, "网络请求失败，正在重试...");
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (!isFinishing()) {
//                                    process();
//                                }
//                            }
//                        }, DELAY_TIME);
//                    }
//                });
//            } else {
//
//                mNetworkAvailableDialog = UIUtil.showConfirm(this, "温馨提示", "网络连接异常，请检查网络配置", "设置", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        startActivityForResult(new Intent(Settings.ACTION_SETTINGS), SETTING_NETWORK);
//
//                    }
//                }, "退出", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        finish();
//                    }
//                });
//            }
//
//        } else {
//            forward(DELAY_TIME);
//        }
//        forward(DELAY_TIME);

    }

//    public void showAd() {
//
//        if (mAds == null) return;
//
//        final AlphaAnimation animation = (AlphaAnimation) AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mSplashView.setVisibility(View.GONE);
//                mCompanyInfoView.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//
//        if (mAds.isSkip) {
//            Button btnAd = (Button) findViewById(R.id.btnAd);
//            if (SystemUtil.isTintStatusBarAvailable(this)) {
//                ((FrameLayout.LayoutParams) btnAd.getLayoutParams()).topMargin += SystemUtil.getStatusBarHeight();
//            }
//            btnAd.setVisibility(View.VISIBLE);
//            btnAd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    UmengClickAgent.onEvent(SplashActivity.this, "JkyADIgnore");
//                    if (mHandler != null) {
//                        mHandler.removeCallbacks(mForwardRunnable);
//                    }
//                    skipToHomeActivity();
//                }
//            });
//        }
//        if (!TextUtils.isEmpty(mAds.hoplink)) {
//            mAdView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mHandler != null) {
//                        mHandler.removeCallbacks(mForwardRunnable);
//                    }
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    SchemeUtil.startActivity(SplashActivity.this, mAds.hoplink);
//                    finish();
//                }
//            });
//        }
//
//        final String imgPath = AppConfigManager.getLocalAdsPath(AppConfigManager.getInstance().getAds().imgUrl);
//        new BitmapTools(this).load(imgPath,new ResponseCallback<Drawable>(){
//            @Override
//            public void onSuccess(Drawable bitmap) {
//                super.onSuccess(bitmap);
//                mCompanyInfoView.startAnimation(animation);
//                mSplashView.startAnimation(animation);
//                mAdView.setVisibility(View.VISIBLE);
//                mAdView.setImageDrawable(bitmap);
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                super.onFailure(e);
//                skipToHomeActivity();
//                FileUtil.deleteFile(imgPath);
//                new PhotoDao(SplashActivity.this).deletePhoto(imgPath);
//
//            }
//        });
//
//        if (mAds.duration > 0) {
//            forward(mAds.duration);
//        } else {
//            forward(DEFAULT_SHOW_TIME);
//        }
//    }

//    public void forward(int sleepTime) {
//        mHandler.postDelayed(mForwardRunnable, sleepTime);
//    }


//    private static class ForwardRunnable implements Runnable {
//        private final WeakReference<SplashActivity> mActivity;
//
//        public ForwardRunnable(SplashActivity activity) {
//            mActivity = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void run() {
//            SplashActivity activity = mActivity.get();
//            // 市民云如果正在登陆，暂时不要跳转页面
//            if (activity != null) {
//                if (PrefUtil.getInt(activity, Constant.KEY_VERSION_CODE) == -1) {
//                    activity.skipToHomeActivity();
//                    return;
//                }
//                if (activity.isNeedShowAd() && !activity.hasShownAd) {
//                    activity.hasShownAd = true;
//                    activity.showAd();
//                } else {
//                    activity.skipToHomeActivity();
//                }
//            }
//        }
//    }

    public void skipToHomeActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();

            }
        }, 200);
    }


//    public boolean isNeedShowAd() {
//        mAds = AppConfigManager.getInstance().getAds();
//        if (mAds == null || !mAds.isShow || AppConfigManager.getInstance().isLoading()) {
//            return false;
//        }
//        return !TextUtils.isEmpty(mAds.imgUrl) && new File(AppConfigManager.getLocalAdsPath(mAds.imgUrl)).exists();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SETTING_NETWORK:
                if (mNetworkAvailableDialog != null)
                    mNetworkAvailableDialog.dismiss();
                process();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    protected PermissionType[] applyPermission() {
        return new PermissionType[]{PermissionType.WRITE_EXTERNAL_STORAGE};
    }
    @Override
    protected boolean useSwipeBackLayout() {
        return false;
    }

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }


    @Override
    protected boolean isStatusBarDarkMode() {
        return false;
    }
}
