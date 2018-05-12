package com.wonders.health.venus.open.user.module.home.extractreport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ExtractHospitalEntity;
import com.wonders.health.venus.open.user.module.home.HomeFragment;
import com.wondersgroup.hs.healthcloud.common.view.PagerSlidingTabStrip;

import java.util.HashMap;

/**
 * Created by wang on 2016/11/10.
 */

public class ExtractReportActivity extends BaseActivity {
    public static final String KEY_DAY = "day";
    public static final String KEY_HOSPITAL = "hospital";


    private static final String EXTRA_TAG = "tag";
    public static final String EXTRA_POS = "position";

    private HashMap<Integer, String> mTags;
    private int mCurrentPosition = 0;

    private PagerSlidingTabStrip mTabs;
    private ViewPager mPager;


    public String day;
    public ExtractHospitalEntity mHospitalEntity;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_extract_report);
        mTitleBar.setTitle("提取报告");
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(KEY_DAY)) {
                day = bundle.getString(KEY_DAY);
                bundle.remove(KEY_DAY);
            }
            if (bundle.containsKey(KEY_HOSPITAL)) {
                mHospitalEntity = (ExtractHospitalEntity) bundle.getSerializable(KEY_HOSPITAL);
                bundle.remove(KEY_HOSPITAL);
            }
        }
        if ("".equals(day)) {

            finish();


        }
        if (mHospitalEntity == null) {

            finish();
        }


        if (savedInstanceState != null) {
            mTags = (HashMap<Integer, String>) savedInstanceState.getSerializable(EXTRA_TAG);
            mCurrentPosition = savedInstanceState.getInt(EXTRA_POS);
        } else {
            mTags = new HashMap<Integer, String>(2);
            mCurrentPosition = getIntent().getIntExtra(EXTRA_POS, 0);
        }

        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                String tag = mTags.get(position);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new ExtractReportFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("type",position);
                    bundle1.putSerializable(KEY_HOSPITAL,mHospitalEntity);
                    bundle1.putString(KEY_DAY,day);
                    fragment.setArguments(bundle1);
                }

                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "检验报告";
                } else if (position == 1) {
                    return "检查报告";
                }
                return "";
            }

            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                mTags.put(position, fragment.getTag());
                return fragment;
            }
        });
        mTabs.setViewPager(mPager);
        mPager.setCurrentItem(mCurrentPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_TAG, mTags);
        mCurrentPosition = mPager.getCurrentItem();
        outState.putInt(EXTRA_POS, mCurrentPosition);
    }
}
