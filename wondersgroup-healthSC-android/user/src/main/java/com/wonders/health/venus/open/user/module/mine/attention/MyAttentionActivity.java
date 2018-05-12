package com.wonders.health.venus.open.user.module.mine.attention;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.view.PagerSlidingTabStrip;

import java.util.HashMap;

/**
 * Created by szy
 */
public class MyAttentionActivity extends BaseActivity {
    private static final String EXTRA_TAG = "tag";
    public static final String EXTRA_POS = "position";

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private HashMap<Integer, String> mTags;
    private int mCurrentPosition = 0;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_my_attention);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mTitleBar.setTitle("我的关注");


        if (savedInstanceState != null) {

            mTags = (HashMap<Integer, String>) savedInstanceState.getSerializable(EXTRA_TAG);
            mCurrentPosition = savedInstanceState.getInt(EXTRA_POS);
        } else {
            mTags = new HashMap<Integer, String>(2);
            mCurrentPosition = getIntent().getIntExtra(EXTRA_POS, 0);
        }
        setAdapter();
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        if (savedInstanceState != null) {

            mTags = (HashMap<Integer, String>) savedInstanceState.getSerializable(EXTRA_TAG);
            mCurrentPosition = savedInstanceState.getInt(EXTRA_POS);
        } else {
            mTags = new HashMap<Integer, String>(2);
            mCurrentPosition = getIntent().getIntExtra(EXTRA_POS, 0);
        }
        setAdapter();
        mPagerSlidingTabStrip.setViewPager(mViewPager);

    }

    private void setAdapter() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                String tag = mTags.get(position);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    switch (position) {
                        default:
                        case 0: //医生
                            fragment = new MyAttentionDoctorFragment();
                            break;
                        case 1: //医院
                            fragment = new MyAttentionHospitalFragment();
                            break;
                    }
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "关注的医生";
                } else {
                    return "关注的医院";
                }
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                mTags.put(position, fragment.getTag());
                return fragment;
            }
        });
        mViewPager.setCurrentItem(mCurrentPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_TAG, mTags);
        mCurrentPosition = mViewPager.getCurrentItem();
        outState.putInt(EXTRA_POS, mCurrentPosition);
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
