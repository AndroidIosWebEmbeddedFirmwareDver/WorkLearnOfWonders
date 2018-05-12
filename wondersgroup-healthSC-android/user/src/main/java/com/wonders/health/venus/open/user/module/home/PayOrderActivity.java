package com.wonders.health.venus.open.user.module.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.PagerSlidingTabStrip;

/**
 * 类描述：在线支付
 * 创建人：tanghaihua
 * 创建时间：11/7/16 8:06 PM
 */
public class PayOrderActivity extends BaseActivity {

    private PagerSlidingTabStrip mTabs;
    private ViewPager mPager;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_payorder);
        mTabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        mPager = (ViewPager)findViewById(R.id.pager);
        mTabs.setTabWidth(50);
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    UIUtil.hideSoftInput(PayOrderActivity.this);
                }else if(position == 1){
                    UIUtil.showSoftInput(PayOrderActivity.this);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleBar.setTitle("在线支付");
        loadData();
    }

    private void loadData() {
        mPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTabs.setViewPager(mPager);
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter( FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new PayAppointFragment();
            } else if (position == 1) {
                return new PayClinicFragment();
            }else if(position==2){
                return new PayHospitalFragment();
            }
            return new PayAppointFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "挂号费支付";
            } else if (position == 1) {
                return "诊间支付";
            }else if(position==2){
                return "住院金预缴";
            }
            return "";
        }
    }

    @Override
    protected boolean useSwipeBackLayout() {
        return false;
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
