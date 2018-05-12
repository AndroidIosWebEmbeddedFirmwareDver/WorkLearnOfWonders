package com.wonders.health.venus.open.doctor.module.referral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.wonders.health.venus.open.doctor.BaseActivity;
import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.view.LikePhotoPopupwindow;
import com.wondersgroup.hs.healthcloud.common.view.PagerSlidingTabStrip;

/**
 * Created by win10 on 2017/6/6.
 */

public class ReferralServiceActivity extends BaseActivity implements View.OnClickListener {


    private ViewPager mPager;
    private MyAdapter mAdapter;
    private Button bt_apply, bt_access;
    private LikePhotoPopupwindow pop;
    public final static String REFERRAL_TYPE = "referral_type";//转诊：全部、申请中、已驳回、已转诊、已取消
    public final static String REFERRAL_TYPE_ALL = "referral_type_all";//全部
    public final static String REFERRAL_TYPE_APPLY = "referral_type_apply";//申请中
    public final static String REFERRAL_TYPE_REJET = "referral_type_reject";//已驳回
    public final static String REFERRAL_TYPE_ALREADY = "referral_type_already";//已转诊
    public final static String REFERRAL_TYPE_CANCELED = "referral_type_canceled";//已取消
    private PagerSlidingTabStrip mTabs;

    @Override
    protected void initViews() {
        setContentView(R.layout.referral_service_activity);
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.pager);
        bt_apply = (Button) findViewById(R.id.bt_apply);//申请转诊
        bt_access = (Button) findViewById(R.id.bt_access);//接入转诊
        bt_apply.setOnClickListener(this);
        bt_access.setOnClickListener(this);
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleBar.setTitle("转诊服务");

        mPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mTabs.setViewPager(mPager);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //申请转诊
            case R.id.bt_apply:
                showPop();
                break;
            case R.id.bt_access://接入转诊
                startActivity(new Intent(ReferralServiceActivity.this, InsertReferralActivity.class));

                break;
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ReferralServiceAllFragment all = new ReferralServiceAllFragment();
            Bundle bundle = new Bundle();
            if (position == 0) {
                bundle.putString(REFERRAL_TYPE, REFERRAL_TYPE_ALL);
                all.setArguments(bundle);
            } else if (position == 2) {
                bundle.putString(REFERRAL_TYPE, REFERRAL_TYPE_APPLY);
                all.setArguments(bundle);
            } else if (position == 3) {
                bundle.putString(REFERRAL_TYPE, REFERRAL_TYPE_ALREADY);
                all.setArguments(bundle);
            } else if (position == 4) {
                bundle.putString(REFERRAL_TYPE, REFERRAL_TYPE_CANCELED);
                all.setArguments(bundle);
            }
            return all;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "全部";
            } else if (position == 1) {
                return "申请中";
            } else if (position == 2) {
                return "已驳回";
            } else if (position == 3) {
                return "已转诊";
            } else if (position == 4) {
                return "已取消";
            }
            return "";
        }
    }


    /**
     * 设置弹出框
     */
    private void showPop() {
        final Intent intent = null;
        pop = new LikePhotoPopupwindow(ReferralServiceActivity.this);
        pop.setCallback(pop.new PopClickMethod() {


            @Override
            public void hospitalization() {
                //startActivity(new Intent(ReferralServiceActivity.this,"住院门诊"));
            }

            @Override
            public void door() {
                //startActivity(new Intent(ReferralServiceActivity.this,"门诊"));
            }

            @Override
            public void cancel() {
            }
        });
        pop.showPop(bt_apply);


    }

    @Override
    protected boolean useSwipeBackLayout() {
        return false;
    }

    @Override
    protected boolean needCheckLogin() {
        return false;
    }
}
