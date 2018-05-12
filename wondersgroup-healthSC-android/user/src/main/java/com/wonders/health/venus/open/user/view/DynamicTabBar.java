package com.wonders.health.venus.open.user.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.entity.event.MessageEvent;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.mine.LoginActivity;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.UmengClickAgent;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.greenrobot.event.EventBus;

/**
 * 类描述：底部动态Tab
 * 创建人：angelo
 * 创建时间：12/30/15 2:29 PM
 */
public class DynamicTabBar extends LinearLayout implements View.OnClickListener {

    private static final int TAB_COUNT = 4;
    private View[] mRedPoint;
    private TitleBar mTitleBar;
    private ViewPager mPager;
    private int mCurrentPosition = 0;
    private AtomicInteger mSuccessCount = new AtomicInteger(0);
    private BitmapTools mBitmapTools;

    private ImageView[] mViews = new ImageView[TAB_COUNT + 1];
    private Drawable[] mBitmaps = new Drawable[TAB_COUNT * 2 + 1];
    private int mUnreadMsgCount = 0; // 未读消息数

//    private TitleBar.ImageNumAction mMessageAction = new TitleBar.ImageNumAction(R.mipmap.ic_bar_message) {
//        @Override
//        public void performAction(View view) {
//            getContext().startActivity(new Intent(getContext(), MessageActivity.class));
//
//            UmengClickAgent.onEvent(getContext(),"JkyHomeMessage");
//
//        }
//    };

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
                (ImageView) findViewById(R.id.bg_tab_bar),
                (ImageView) findViewById(R.id.tab_home_0),
                (ImageView) findViewById(R.id.tab_home_1),
                (ImageView) findViewById(R.id.tab_home_2),
                (ImageView) findViewById(R.id.tab_home_3)};

        mRedPoint = new View[]{
                findViewById(R.id.tab_home_0_point),
                findViewById(R.id.tab_home_1_point),
                findViewById(R.id.tab_home_2_point),
                findViewById(R.id.tab_home_3_point)};

        mBitmaps = new Drawable[] {
                getResources().getDrawable(R.mipmap.tab_bg),
                getResources().getDrawable(R.mipmap.tab_0),
                getResources().getDrawable(R.mipmap.tab_1),
                getResources().getDrawable(R.mipmap.tab_2),
                getResources().getDrawable(R.mipmap.tab_3),
                getResources().getDrawable(R.mipmap.tab_0_checked),
                getResources().getDrawable(R.mipmap.tab_1_checked),
                getResources().getDrawable(R.mipmap.tab_2_checked),
                getResources().getDrawable(R.mipmap.tab_3_checked),
        };

        findViewById(R.id.tab_container_0).setOnClickListener(this);
        findViewById(R.id.tab_container_1).setOnClickListener(this);
        findViewById(R.id.tab_container_2).setOnClickListener(this);
        findViewById(R.id.tab_container_3).setOnClickListener(this);

        mBitmapTools = new BitmapTools(getContext());
    }

    public void display(List<String> list) {
        if (list.size() != TAB_COUNT * 2 + 1) {
            return;
        }
        mSuccessCount.set(0);
        for (int i = 0; i < 9; i++) {
            mBitmapTools.load(list.get(i), new WeakReferenceBitmapLoadCallback(this, i));
        }
    }


    public void toggleTab(int item) {

        if (mPager != null) {
            mPager.setCurrentItem(item, false);
        }
        mCurrentPosition = item;
        for (int i = 0; i < 4; i++) {
            mViews[i + 1].setSelected(false);
        }
        mViews[item + 1].setSelected(true);
        displayBitmap();
        umengStatistics(item);
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
            case R.id.tab_container_2:
                if (mCurrentPosition == 2) {
                    return;
                }
                processShowPage(2);
                break;
            case R.id.tab_container_3:
                if (mCurrentPosition == 3) {
                    return;
                }
                processShowPage(3);
                break;
            default:
                break;

        }
    }

    public void processShowPage(int position) {
        switch (position) {
            case 0:
                if (mTitleBar != null) {
                    mTitleBar.setVisibility(View.GONE);
                    mTitleBar.removeAllActions();

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
            case 2:
                if(UserManager.getInstance().isLogin()){
                    if (mTitleBar != null) {
                        mTitleBar.setVisibility(View.VISIBLE);
                        mTitleBar.setTitle("消息");
                        mTitleBar.setTitleBackground(0);
                        mTitleBar.removeAllActions();
                        mTitleBar.setLeftVisible(false);
                    }
                    toggleTab(2);
                }else{
                    getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case 3:
                if (mTitleBar != null) {
                    mTitleBar.setVisibility(View.GONE);
                    mTitleBar.removeAllActions();
                    mTitleBar.setLeftVisible(false);
                }
                toggleTab(3);

                //获取用户数据
                UserManager.getInstance().getUserInfo(new ResponseCallback<User>() {
                    @Override
                    public boolean isShowNotice() {
                        return false;
                    }
                });
                break;
            default:
                break;
        }
    }

    public void showRedPoint(int position) {
        mRedPoint[position].setVisibility(VISIBLE);
    }

    public void hideRedPoint(int position) {
        mRedPoint[position].setVisibility(GONE);
    }

    public void setPager(ViewPager pager) {
        mPager = pager;
    }

    public void setTitleBar(TitleBar titleBar) {
        mTitleBar = titleBar;
    }

    //    public void setMessageActionNum(int num) {
//        mTitleBar.setActionDotNum(mMessageAction, num);
//        mUnreadMsgCount = num;
//    }
//
    public static class WeakReferenceBitmapLoadCallback extends ResponseCallback<Drawable> {
        private final WeakReference<DynamicTabBar> mDynamicTabBarWeakReference;
        private int mIndex;

        public WeakReferenceBitmapLoadCallback(DynamicTabBar dynamicTabBar, int index) {
            mDynamicTabBarWeakReference = new WeakReference<>(dynamicTabBar);
            mIndex = index;
        }

        @Override
        public void onSuccess(Drawable bitmap) {
            super.onSuccess(bitmap);
            DynamicTabBar dynamicTabBar = mDynamicTabBarWeakReference.get();
            if (dynamicTabBar != null) {
                int index = dynamicTabBar.mSuccessCount.getAndIncrement();
                if (mIndex < TAB_COUNT * 2 + 1) {
                    dynamicTabBar.mBitmaps[mIndex] = bitmap;
                }
                if (index == TAB_COUNT * 2) {
                    dynamicTabBar.displayBitmap();
                    dynamicTabBar.mViews[0].setImageDrawable(dynamicTabBar.mBitmaps[0]);
                }
            }
        }
    }

    private void displayBitmap() {
        for (int i = 1; i < TAB_COUNT + 1; i++) {
            // 1->1,5    2->2,6   3->3,7   4->4,8
            mBitmapTools.display(mViews[i], mViews[i].isSelected() ? mBitmaps[TAB_COUNT + i] : mBitmaps[i]);
        }
    }

    private void umengStatistics(int item) {
        String event = "";
        switch (item) {
            case 0:
                event = "JkyTabHome";
                break;
            case 1:
                event = "JkyTabService";
                break;
            case 2:
                event = "JkyTabInformation";
                break;
            case 3:
                event = "JkyTabMy";
                break;
        }
        if (!TextUtils.isEmpty(event)) {
            UmengClickAgent.onEvent(getContext(), event);
        }
    }


}
