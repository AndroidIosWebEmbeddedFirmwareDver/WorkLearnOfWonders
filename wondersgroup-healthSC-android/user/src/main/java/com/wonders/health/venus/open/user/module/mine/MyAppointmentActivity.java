package com.wonders.health.venus.open.user.module.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.view.PagerSlidingTabStrip;

import java.util.HashMap;

/**
 * class MyAppointmentActivity
 * auth carrey
 * 16-11-7.
 */

public class MyAppointmentActivity extends BaseActivity {
    private static final String EXTRA_TAG = "tag";
    public static final String EXTRA_POS = "position";

    private HashMap<Integer, String> mTags;
    private int mCurrentPosition = 0;


    private PagerSlidingTabStrip mSlidingTabStrip;
    private ViewPager mViewPager;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_my_appointment);
        mTitleBar.setTitle("我的预约");
        mTitleBar.setTitleColor(R.color.bc0);

        mSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTags = (HashMap<Integer, String>) savedInstanceState.getSerializable(EXTRA_TAG);
            mCurrentPosition = savedInstanceState.getInt(EXTRA_POS);
        } else {
            mTags = new HashMap<Integer, String>(2);
            mCurrentPosition = getIntent().getIntExtra(EXTRA_POS, 0);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                String tag = mTags.get(position);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    fragment = new MyAppointmentFragment();
                    Bundle bundle = new Bundle();
                    switch (position) {
                        default:
                        case 0: //全部
                            bundle.putInt("status", 0);
                            break;
                        case 1: //待就诊
                            bundle.putInt("status", 2);
                            break;
                        case 2: //已就诊
                            bundle.putInt("status", 4);
                            break;
                        case 3://已取消
                            bundle.putInt("status", 3);
                            break;
                    }
                    fragment.setArguments(bundle);
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    default:
                    case 0:
                        return "全部";
                    case 1:
                        return "待就诊";
                    case 2:
                        return "已就诊";
                    case 3:
                        return "已取消";
                }
            }

            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                mTags.put(position, fragment.getTag());
                return fragment;
            }
        });

        mSlidingTabStrip.setViewPager(mViewPager);
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
