package com.wonders.health.venus.open.doctor.module.registration;
/*
 * Created by sunning on 2016/11/8.
 * 科室名称
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.view.PagerSlidingTabStrip;

import java.util.HashMap;

import static com.wonders.health.venus.open.doctor.module.registration.DepartmentSelectActivity.HOS_DEPT_NAME;


public class RegistrationDoctorListActivity extends UnifyFinishActivity {

    public static final String EXTRA_TAG = "tag";
    public static final String EXTRA_POS = "position";

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mTabStrip;
    private HashMap<Integer, String> mTags;
    private int mCurrentPosition = 0;

    @Override
    protected void initViews() {
        setContentView(R.layout.register_doctor_list);
        mViewPager = (ViewPager) findViewById(R.id.department_name_view_pager);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.department_name_tabs);
    }

    private void initAdapter() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                String tag = mTags.get(position);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    switch (position) {
                        default:
                        case 0: //按专家预约
                            fragment = new DoctorListBySpecialistFragment();
                            break;
                        case 1: //按日期预约
                            fragment = new DoctorListByDateFragment();
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
                    return "按专家预约";
                } else {
                    return "按日期预约";
                }
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                mTags.put(position, fragment.getTag());
                return fragment;
            }
        });
    }

    public static boolean verification(Context context,String uri) {
        boolean result = "3".equals(UserManager.getInstance().getUser().verificationStatus);
        if (!result) {
//            Intent intent = new Intent(context, AuthChooseActivity.class);
//            intent.putExtra(AuthChooseActivity.WAY_FROM, 1);
//            intent.putExtra(AuthChooseActivity.EXTRA_URL,uri);
//            context.startActivity(intent);
        }
        return result;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        String deptName = getIntent().getStringExtra(HOS_DEPT_NAME);
        mTitleBar.setTitle(deptName);
        if (savedInstanceState != null) {
            mTags = (HashMap<Integer, String>) savedInstanceState.getSerializable(EXTRA_TAG);
            mCurrentPosition = savedInstanceState.getInt(EXTRA_POS);
        } else {
            mTags = new HashMap<>(2);
            mCurrentPosition = getIntent().getIntExtra(EXTRA_POS, 0);
        }
        initAdapter();
        mTabStrip.setViewPager(mViewPager);
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_TAG, mTags);
        mCurrentPosition = mViewPager.getCurrentItem();
        outState.putInt(EXTRA_POS, mCurrentPosition);
    }
}
