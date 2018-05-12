package com.wonders.health.venus.open.user.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.component.update.VersionUpdateManager;
import com.wonders.health.venus.open.user.entity.AppConfig;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.entity.event.AccountChangeEvent;
import com.wonders.health.venus.open.user.logic.AppConfigManager;
import com.wonders.health.venus.open.user.logic.SignRequest;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;
import com.wondersgroup.hs.healthcloud.common.util.NetworkUtil;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.io.File;

/**
 * 类描述：设置Activity
 * 创建人：hhw
 * 创建时间：2016/11/7 11:14
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout mRlVersionUpdate;
    private TextView mTvVersion;
    private TextView mTvBuildInfo;
    private TextView mTv_new_version_icon;
    private ImageView mIvVersionUpdateArrow;
    private View mBtnLogout;


    private boolean mIsClickable = true;//升级按钮是否可以点击
    private TextView mTvWanDaVersion;
    private TextView mTv_suprise;

    private int delay = 1000;
    private long lastTime = 0;
    private int clickCount = 0;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_setting);

        mTitleBar.setTitle("设置");
        mRlVersionUpdate = (RelativeLayout) findViewById(R.id.rl_version_update);
        mTvVersion = (TextView) findViewById(R.id.tv_version);
        mTvBuildInfo = (TextView) findViewById(R.id.tv_build_info);
        mTv_new_version_icon = (TextView) findViewById(R.id.tv_new_version_icon);
        mTvWanDaVersion = (TextView) findViewById(R.id.tv_wanda_version);
        mTv_suprise = (TextView) findViewById(R.id.tv_suprise);
        mIvVersionUpdateArrow = (ImageView) findViewById(R.id.iv_version_update_arrow);
        mBtnLogout = findViewById(R.id.btn_logout);

        mRlVersionUpdate.setOnClickListener(this);
//        mTvWanDaVersion.setOnClickListener(this);
        mBtnLogout.setVisibility(UserManager.getInstance().isLogin() ? View.VISIBLE : View.GONE);
        mBtnLogout.setOnClickListener(this);
        findViewById(R.id.tv_account_setting).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTvWanDaVersion.setText(getString(R.string.app_name) + "\nv" + SystemUtil.getVersionName(SettingActivity.this) + "版");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(((BaseApp) (BaseApp.getApp())).mCurrentAppVersion) || ((BaseApp) (BaseApp.getApp())).mCurrentAppVersion.equals(SystemUtil.getVersionName(this))) {
            mTvVersion.setVisibility(View.VISIBLE);
            mTv_new_version_icon.setVisibility(View.GONE);
            mTvVersion.setText("已是最新版本   v" + SystemUtil.getVersionName(this));
            mIvVersionUpdateArrow.setVisibility(View.GONE);

        } else {
            mTvVersion.setVisibility(View.GONE);
            mTv_new_version_icon.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 更新版本
     * updateVersion
     *
     * @since 1.0
     */
    public void updateVersion() {
        if (!NetworkUtil.isNetworkAvailable(this)) {
            UIUtil.toastShort(getApplicationContext(), R.string.notice_network_error);
            return;
        }
        VersionUpdateManager task = null;
        if (task == null) {
            task = new VersionUpdateManager(this);
        }
        String url = "";
        if (AppConfigManager.getInstance().getUpdate() != null && !TextUtils.isEmpty(AppConfigManager.getInstance().getUpdate().androidUrl)) {
            url = AppConfigManager.getInstance().getUpdate().androidUrl;
        }
        File downLoadFile = new File(task.getLocalDownloadUrl(url));
        if (((BaseApp) BaseApp.getApp()).mIsFinishDownload && downLoadFile.exists()) {
            task.startInstallNewVersion(url);
            return;
        }
        if (((BaseApp) BaseApp.getApp()).mIsDownloading) {
            UIUtil.toastShort(getApplicationContext(), R.string.version_downloading);
        } else {
            if (mIsClickable) {
                task.setListener(new VersionUpdateManager.OnDialogDismissListener() {

                    @Override
                    public void dismiss() {
                        mIsClickable = true;
                    }
                });
                checkVersionUpdate(task, true);
                mIsClickable = false;
            }
        }
    }

    /**
     * 检查版本更新
     * checkVersionUpdate
     *
     * @since 1.0
     */
    public void checkVersionUpdate(final VersionUpdateManager task, final boolean isHandUpdate) {
        SignRequest info = new SignRequest();
        info.setPath(UrlConst.GET_APP_CONFIG);
        new HttpTools().get(info, new ResponseCallback<AppConfig>() {

            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar(SettingActivity.this);
            }

            @Override
            public void onSuccess(AppConfig appConfig) {
                super.onSuccess(appConfig);
                if (appConfig == null) return;
                task.ShowUpdateDialog(appConfig.appUpdate, isHandUpdate);
            }

            @Override
            public boolean isShowNotice() {
                return false;
            }

            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                UIUtil.hideProgressBar(SettingActivity.this);
                mIsClickable = true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                UIUtil.showConfirm(this, "您确定要退出登录？", new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        UserManager.getInstance().logout(new ResponseCallback<User>() {
                            @Override
                            public void onStart() {
                                super.onStart();
                                UIUtil.showProgressBar(SettingActivity.this);
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                UIUtil.hideProgressBar(SettingActivity.this);
                                UIUtil.toastShort(getApplicationContext(), "退出登录成功");
                                if (!isFinishing()) {
                                    finish();
                                }
                            }

                            @Override
                            public boolean isShowNotice() {
                                return false;
                            }
                        });
                    }
                });
                break;
            case R.id.tv_wanda_version:
                if (mTvBuildInfo.getVisibility() == View.VISIBLE) {
                    return;
                }
                if ((System.currentTimeMillis() - lastTime) < delay) {
                    clickCount++;
                    if (clickCount == 5) {
                        //             mTvBuildInfo.setText("Build信息：" + DateUtil.convert2long(BuildConfig.BUILD_DATE, "yyyy-MM-dd HH:mm:ss"));
                        mTvBuildInfo.setVisibility(View.VISIBLE);
                    } else if (clickCount > 2) {
                        UIUtil.toastShort(this, "再点击" + (5 - clickCount) + "次将显示build信息");
                    }

                } else {
                    clickCount = 1;
                }

                lastTime = System.currentTimeMillis();
                break;
            case R.id.rl_version_update://版本更新
                updateVersion();
                break;
            case R.id.tv_account_setting:
                startActivity(new Intent(this, AccountSettingActivity.class));
                break;
        }
    }

    public void onEvent(AccountChangeEvent event) {
        if (mBtnLogout != null) {
            mBtnLogout.setVisibility(UserManager.getInstance().isLogin() ? View.VISIBLE : View.GONE);
        }
    }
}
