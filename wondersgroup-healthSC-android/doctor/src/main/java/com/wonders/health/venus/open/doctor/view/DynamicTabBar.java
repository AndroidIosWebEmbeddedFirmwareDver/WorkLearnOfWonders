package com.wonders.health.venus.open.doctor.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.doctor.R;
import com.wonders.health.venus.open.doctor.entity.event.MessageEvent;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;

import de.greenrobot.event.EventBus;

/**
 * 类描述：底部动态Tab
 * 创建人：angelo
 * 创建时间：12/30/15 2:29 PM
 */
public class DynamicTabBar extends LinearLayout implements View.OnClickListener {

    private static final int TAB_COUNT = 2;
    private TitleBar mTitleBar;
    private ViewPager mPager;
    private int mCurrentPosition = 0;
    private BitmapTools mBitmapTools;

    private ImageView[] mViews = new ImageView[TAB_COUNT];
    private TextView[] mTextViews = new TextView[TAB_COUNT];
    private Drawable[] mBitmaps = new Drawable[TAB_COUNT * 2];

    public DynamicTabBar(Context context) {
        super(context);
        init();
    }

    public DynamicTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.dynamic_tab_bar, this);
        mViews = new ImageView[]{
                (ImageView) findViewById(R.id.tab_home_0),
                (ImageView) findViewById(R.id.tab_home_1)};

        mTextViews = new TextView[]{
                (TextView) findViewById(R.id.tab_home_0_desc),
                (TextView) findViewById(R.id.tab_home_1_desc)};


        mBitmaps = new Drawable[]{
                getResources().getDrawable(R.mipmap.ic_tab0),
                getResources().getDrawable(R.mipmap.ic_tab0_selected),
                getResources().getDrawable(R.mipmap.ic_tab1),
                getResources().getDrawable(R.mipmap.ic_tab1_selected),
        };

        findViewById(R.id.tab_container_0).setOnClickListener(this);
        findViewById(R.id.tab_container_1).setOnClickListener(this);

        mBitmapTools = new BitmapTools(getContext());
    }


    public void toggleTab(int item) {
        if (mPager != null) {
            mPager.setCurrentItem(item, false);
        }
        mCurrentPosition = item;
        for (int i = 0; i < 2; i++) {
            mViews[i].setSelected(false);
            mTextViews[i].setSelected(false);
        }
        mViews[item].setSelected(true);
        mTextViews[item].setSelected(true);
        displayBitmap();
    }


    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new MessageEvent(false));
        switch (v.getId()) {
            case R.id.tab_container_0:
                if (mCurrentPosition == 0) {
                    return;
                }
                processShowPage(0);
                break;
            case R.id.tab_container_1:
                if (mCurrentPosition == 1) {
                    return;
                }
                processShowPage(1);
                break;
            default:
                break;

        }
    }

    public void processShowPage(int position) {
        switch (position) {
            case 0:
                if (mTitleBar != null) {
                    mTitleBar.setVisibility(View.VISIBLE);
                    mTitleBar.setTitle("我的工作台");
                    mTitleBar.setTitleBackground(0);
                    mTitleBar.removeAllActions();
                    mTitleBar.setLeftVisible(false);
                }
                toggleTab(0);
                break;
            case 1:
                if (mTitleBar != null) {
                    mTitleBar.setVisibility(View.GONE);
                    mTitleBar.removeAllActions();
                    mTitleBar.setLeftVisible(false);
                }
                toggleTab(1);
                break;
            default:
                break;
        }
    }

    public void setPager(ViewPager pager) {
        mPager = pager;
    }

    public void setTitleBar(TitleBar titleBar) {
        mTitleBar = titleBar;
    }


    private void displayBitmap() {
        for (int i = 0; i < TAB_COUNT; i++) {
            mBitmapTools.display(mViews[i], mViews[i].isSelected() ? mBitmaps[i * TAB_COUNT + 1] : mBitmaps[i * TAB_COUNT]);
            mTextViews[i].setTextColor(ContextCompat.getColor(getContext(), mTextViews[i].isSelected() ? R.color.tc5 : R.color.tc4));
        }
    }
}
