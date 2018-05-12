package com.wonders.health.venus.open.doctor.module.mine.h5;
/*
 * Created by sunning on 2017/6/16.
 */

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.module.WebViewActivity;
import com.wondersgroup.hs.healthcloud.common.WebViewFragment;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.view.ScrollChangedWebView;

import butterknife.BindView;

public class DoctorTeamDetailActivity extends WebViewActivity {

    @BindView(R.id.ll_title)
    public View mTitle;

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean useSwipeBackLayout() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }


    @Override
    protected boolean isStatusBarDarkMode() {
        super.isStatusBarDarkMode();
        return false;
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_doctor_team_detail);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mWebViewFragment = new WebViewFragment();
        ft.replace(R.id.web_view_content, mWebViewFragment);
        ft.commitAllowingStateLoss();
        if (SystemUtil.isTintStatusBarAvailable(this)) {
            mTitle.setPadding(0, SystemUtil.getStatusBarHeight(), 0, 0);
        }
        mTitle.post(new Runnable() {
            @Override
            public void run() {
                mWebViewFragment.setScrollChangeCallback(new ScrollChangedWebView.OnScrollChangedCallback() {
                    @Override
                    public void onScroll(int dx, int dy) {
                        if (dy < 0 && mTitle.getVisibility() == View.VISIBLE) {
                            AlphaAnimation animation = new AlphaAnimation(1, 0);
                            animation.setDuration(300);
                            mTitle.clearAnimation();
                            mTitle.startAnimation(animation);
                            mTitle.setVisibility(View.GONE);
                        } else if (dy >= 0 && mTitle.getVisibility() == View.GONE) {
                            AlphaAnimation animation = new AlphaAnimation(0, 1);
                            animation.setDuration(300);
                            mTitle.clearAnimation();
                            mTitle.startAnimation(animation);
                            mTitle.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }
        });
    }
}
