package com.wonders.health.venus.open.user.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.component.update.VersionUpdateManager;
import com.wonders.health.venus.open.user.entity.MsgEntity;
import com.wonders.health.venus.open.user.entity.event.AccountChangeEvent;
import com.wonders.health.venus.open.user.entity.event.MessageEvent;
import com.wonders.health.venus.open.user.logic.AppConfigManager;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.module.health.HealthFragment;
import com.wonders.health.venus.open.user.module.home.HomeFragment;
import com.wonders.health.venus.open.user.module.launch.TutorialActivity;
import com.wonders.health.venus.open.user.module.mine.MyCenterFragment;
import com.wonders.health.venus.open.user.module.msg.MessageFragment;
import com.wonders.health.venus.open.user.util.Constant;
import com.wonders.health.venus.open.user.view.DynamicTabBar;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.ExitHelper;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String EXTRA_TAG = "tag";
    public static final String EXTRA_POS = "position";
    private int mCurrentPosition = 0;
    private HashMap<Integer, String> mTags;

    private ViewPager mPager;
    public DynamicTabBar mDynamicTabBar;


    private ExitHelper.TwicePressHolder mExitHelper;

    private BitmapTools mBitmapTools;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            String stringExtra = intent.getStringExtra(EXTRA_POS);
            if (!TextUtils.isEmpty(stringExtra)) {
                mCurrentPosition = Integer.parseInt(stringExtra);
                if (mDynamicTabBar != null) {
                    mDynamicTabBar.processShowPage(mCurrentPosition);
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void initViews() {
        checkShowTutorial();
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager_main);
        mTitleBar.setLeftVisible(false);

        mTitleBar.setVisibility(View.GONE);

        initDynamicTabBar();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (((BaseApp) BaseApp.getApp()).mFirstShowUpdateDialog) {
                //检查版本更新
                new VersionUpdateManager(this).ShowUpdateDialog(AppConfigManager.getInstance().getUpdate(), false);
                ((BaseApp) BaseApp.getApp()).mFirstShowUpdateDialog = false;
            }
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBitmapTools = new BitmapTools(this);
        if (savedInstanceState != null) {

            mTags = (HashMap<Integer, String>) savedInstanceState.getSerializable(EXTRA_TAG);
            mCurrentPosition = savedInstanceState.getInt(EXTRA_POS);
        } else {
            mTags = new HashMap<>(4);
            mCurrentPosition = getIntent().getIntExtra(EXTRA_POS, 0);
        }

        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                String tag = mTags.get(position);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
                if (fragment == null) {
                    switch (position) {
                        case 0:
                            fragment = new HomeFragment();
                            break;
                        case 1:
                            fragment = new HealthFragment();
                            break;
                        case 2:
                            fragment = new MessageFragment();
                            break;
                        case 3:
                            fragment = new MyCenterFragment();
                            break;
                        default:
                            break;
                    }
                }

                return fragment;
            }

            public int getCount() {
                return 4;
            }

            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                mTags.put(position, fragment.getTag());
                return fragment;
            }
        });
        // 双击退出
        mExitHelper = new ExitHelper.TwicePressHolder(new ExitHelper.IExitInterface() {

            @Override
            public void showExitTip() {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void exit() {
                finish();
                BaseApp.getApp().stop();
            }
        }, 3000);

        mDynamicTabBar.processShowPage(mCurrentPosition);
    }

    private void initDynamicTabBar() {
        mDynamicTabBar = new DynamicTabBar(this);
        mDynamicTabBar.setPager(mPager);
        mDynamicTabBar.setTitleBar(mTitleBar);
        final ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        rootView.addView(mDynamicTabBar, lp);

        mDynamicTabBar.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mPager.getLayoutParams();
                if (mDynamicTabBar.getHeight() > 0) {
                    //设置首页底部tab页
                    layoutParams.bottomMargin = mDynamicTabBar.findViewById(R.id.bg_tab_bar).getHeight();
                } else {
                    layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.tab_host_height);
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mExitHelper.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_TAG, mTags);
        mCurrentPosition = mPager.getCurrentItem();
        outState.putInt(EXTRA_POS, mCurrentPosition);
    }

    /**
     * 检查是否开启引导页
     */
    private void checkShowTutorial() {
        int oldVersionCode = PrefUtil.getInt(this, Constant.KEY_VERSION_CODE);
        int currentVersionCode = SystemUtil.getVersionCode(this);
        if (currentVersionCode > oldVersionCode) {
            startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            PrefUtil.putInt(this, Constant.KEY_VERSION_CODE, currentVersionCode);
        }
    }

    @Override
    protected boolean useSwipeBackLayout() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }

    public void onEvent(AccountChangeEvent event) {
        if(!event.isLogin){
            mDynamicTabBar.hideRedPoint(2);
            if (mPager.getCurrentItem() != 0) {
                mDynamicTabBar.processShowPage(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    public void onEvent(MessageEvent event) {
        if(!event.mMsgListDestroy) {
            showRedDot();
        }
    }

    /**
     * 显示消息菜单的红点
     */
    public void showRedDot(){
        if(UserManager.getInstance().isLogin()) {
            UserManager.getInstance().getNewMsg(new ResponseCallback<MsgEntity>() {
                @Override
                public void onSuccess(MsgEntity msgNewEntity) {
                    super.onSuccess(msgNewEntity);
                    ArrayList<MsgEntity.message> msgs = (ArrayList) msgNewEntity.getList();
                    for (MsgEntity.message msg : msgs) {
                        if (msg.count > 0) {
                            mDynamicTabBar.showRedPoint(2);
                            return;
                        }
                    }
                    mDynamicTabBar.hideRedPoint(2);
                }

                @Override
                public boolean isShowNotice() {
                    return false;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
            showRedDot();
    }
}
