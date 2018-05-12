package com.wonders.health.venus.open.doctor.module.patient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wonders.health.venus.open.doctor.BaseActivity;

import com.wonders.health.venus.open.doctor.R;
import com.wondersgroup.hs.healthcloud.common.view.PagerSlidingTabStrip;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;

import butterknife.BindView;

/**
 * Created by wang on 2017/6/5.
 */

public class SignPatientActivity extends BaseActivity {

    public final static String SIGN_TYPE="sign_type";//签约类型：全部、重点、贫困
    public final static String SIGN_TYPE_ALL="null";//全部
    public final static String SIGN_TYPE_IMP="0";//重点
    public final static String SIGN_TYPE_POOR="1";//贫困

    @BindView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @BindView(R.id.pager)
    ViewPager mPager;
    @Override
    protected void initViews() {
        setContentView(R.layout.activity_sign_patient);
        mTabs.setIsWithBadge(true);
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
        mTitleBar.setTitle("签约患者");
        mTitleBar.addAction(new TitleBar.ImageAction(R.mipmap.icon_add_patient) {
            @Override
            public void performAction(View view) {

            }
        });
        mPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTabs.setViewPager(mPager);
        loadData();

    }

    private void loadData(){
        //TODO 获取是否显示红点的接口数据
        if(true){
            isShowRedPoint();
        }
    }
    private void isShowRedPoint(){
        //在第几个tab上显示红点
        mTabs.setBadgeVisibility(1,true);
    }



    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SignPatientFragment signPatientFragment = new SignPatientFragment();
            Bundle bundle = new Bundle();
            if(position==0){
                bundle.putString(SIGN_TYPE,SIGN_TYPE_ALL);
                signPatientFragment.setArguments(bundle);
            }else if(position==1){
                bundle.putString(SIGN_TYPE,SIGN_TYPE_IMP);
                signPatientFragment.setArguments(bundle);
            }else if(position==2){
                bundle.putString(SIGN_TYPE,SIGN_TYPE_POOR);
                signPatientFragment.setArguments(bundle);
            }
            return signPatientFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){
                return "全部";
            }else if(position==1){
                return "重点";
            }else if(position==2){
                return "贫困";
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
        return false;
    }
}
