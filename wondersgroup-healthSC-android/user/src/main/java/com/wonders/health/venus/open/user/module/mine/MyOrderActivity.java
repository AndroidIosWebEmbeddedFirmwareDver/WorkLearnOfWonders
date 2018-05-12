package com.wonders.health.venus.open.user.module.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.view.PagerSlidingTabStrip;

/**
 * 类描述：我的订单
 * 创建人：tanghaihua
 * 创建时间：11/7/16 8:06 PM
 */
public class MyOrderActivity extends BaseActivity {

    private PagerSlidingTabStrip mTabs;
    private ViewPager mPager;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_myorder);
        mTabs = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        mPager = (ViewPager)findViewById(R.id.pager);

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
        mTitleBar.setTitle("我的订单");
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
            MyOrderFragment myOrderFragment = new MyOrderFragment();
            Bundle bundle = new Bundle();
            if (position == 0) {
                bundle.putSerializable(MyOrderFragment.EXTRA_ORDER_STATUS, MyOrderFragment.ORDER_STATUS.ALL);
                myOrderFragment.setArguments(bundle);
            } else if (position == 1) {
                bundle.putSerializable(MyOrderFragment.EXTRA_ORDER_STATUS, MyOrderFragment.ORDER_STATUS.NOTPAY);
                myOrderFragment.setArguments(bundle);
            } else if (position == 2) {
                bundle.putSerializable(MyOrderFragment.EXTRA_ORDER_STATUS, MyOrderFragment.ORDER_STATUS.SUCCESS);
                myOrderFragment.setArguments(bundle);
            } else if (position == 3) {
                bundle.putSerializable(MyOrderFragment.EXTRA_ORDER_STATUS, MyOrderFragment.ORDER_STATUS.REFUNDSUCCESS);
                myOrderFragment.setArguments(bundle);
            } else if (position == 4) {
                bundle.putSerializable(MyOrderFragment.EXTRA_ORDER_STATUS, MyOrderFragment.ORDER_STATUS.EXPIRED);
                myOrderFragment.setArguments(bundle);
            }
            return myOrderFragment;
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
                return "待支付";
            } else if (position == 2) {
                return "已支付";
            } else if (position == 3) {
                return "已退款";
            } else if (position == 4) {
                return "已超时";
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
