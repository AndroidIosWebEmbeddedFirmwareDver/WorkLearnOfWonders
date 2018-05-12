package com.wondersgroup.hs.healthcloud.common.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.nineoldandroids.view.ViewHelper;
import com.wondersgroup.hs.healthcloud.base.R;
import com.wondersgroup.hs.healthcloud.common.CommonActivity;
import com.wondersgroup.hs.healthcloud.common.util.ApiCompatibleUtil;


/**
 * 引导页基类
 * 仿google drive
 * Created by angelo on 2015/6/16.
 */
public abstract class BaseTutorialActivity extends CommonActivity {

    public static final int IMAGE_TOP = 0;
    public static final int IMAGE_CENTER = 1;
    public static final int IMAGE_BOTTOM = 2;
    public static final int BOTTOM_MULTI_WITH_LINE = 0;
    public static final int BOTTOM_SIMPLE = 1;
    public static final int BOTTOM_CUSTOM = 2;
    private int mStyle = IMAGE_TOP;
    private int mBottomStyle = BOTTOM_MULTI_WITH_LINE;
    private int mBottomBackgroud=-1;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ViewStub bottomStub;
    private LinearLayout mCircles;
    private Button mSkip;
    private View mDone;
    private ImageButton mNext;
    private RelativeLayout rlroot;
    private LinearLayout llBottom;
    private boolean showSkip = true;
    private int mBottomLayoutRes = -1; //自定义底部布局
    private DataConfig mDataConfig;
    private OnEndListener mOnEndListener;

    private final BaseSpringSystem mSpringSystem = SpringSystem.create();
    private final MySpringListener mSpringListener = new MySpringListener();
    private Spring mScaleSpring;

    /**
     * 初始化引导页显示数据， 背景、图片、文案标题、内容
     */
    protected abstract DataConfig initDataConfig();

    protected abstract void init(Bundle savedInstanceState);

    @Override
    protected void initViews() {
        if (!ApiCompatibleUtil.hasKitKat()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_tutorial);
        init(null);
        initBottom();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new CrossFadePageTransformer());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if (position == mDataConfig.getPageNum() - 1) {
                    if (mBottomStyle == BOTTOM_CUSTOM) {
                        findViewById(R.id.bottom_container).setVisibility(View.GONE);
                        mScaleSpring.setEndValue(1);
                    } else {
                        mSkip.setVisibility(View.INVISIBLE);
                        mNext.setVisibility(View.GONE);
                    }
                    mDone.setVisibility(View.VISIBLE);

                } else if (position < mDataConfig.getPageNum() - 1) {
                    if (mBottomStyle == BOTTOM_CUSTOM) {
                        findViewById(R.id.bottom_container).setVisibility(View.VISIBLE);
                        mScaleSpring.setEndValue(0);
                    } else {
                        if (showSkip) {
                            mSkip.setVisibility(View.VISIBLE);
                        }
                        mNext.setVisibility(View.VISIBLE);
                    }
                    mDone.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mScaleSpring = mSpringSystem.createSpring();
        buildCircles();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected boolean isValidate() {
        mDataConfig = initDataConfig();
        if (mDataConfig == null && mDataConfig.getPageNum() <= 0) {
            return false;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mScaleSpring != null) {
            mScaleSpring.addListener(mSpringListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mScaleSpring != null) {
            mScaleSpring.removeListener(mSpringListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPager != null) {
            mPager.clearOnPageChangeListeners();
        }
    }

    /**
     * 设置是否显示skip按钮
     *
     * @param showButton
     */
    public void showSkipButton(boolean showButton) {
        this.showSkip = showButton;
    }

    /**
     * 设置展现形式
     *
     * @param style
     */
    public void setStyle(int style) {
        this.mStyle = style;
    }

    /**
     * 设置底部形式
     *
     * @param bottomStyle
     */
    public void setBottomStyle(int bottomStyle) {
        this.mBottomStyle = bottomStyle;
    }

    //设置底部背景色

    public void setBottomBackgroud(int bottomBackgroud){
        this.mBottomBackgroud =bottomBackgroud;
    }

    private void buildCircles() {
        mCircles = LinearLayout.class.cast(findViewById(R.id.circles));
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);

        for (int i = 0; i < mDataConfig.getPageNum(); i++) {
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.mipmap.ic_swipe_indicator_white_18dp);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            mCircles.addView(circle);
        }

        setIndicator(0);
    }

    private void initBottom() {
        bottomStub = (ViewStub) findViewById(R.id.bottom_stub);
        switch (mBottomStyle) {
            case BOTTOM_MULTI_WITH_LINE:
                bottomStub.setLayoutResource(R.layout.tutorial_bottom_1);
                break;
            case BOTTOM_SIMPLE:
                bottomStub.setLayoutResource(R.layout.tutorial_bottom_2);
                break;
            case BOTTOM_CUSTOM:
                if (mBottomLayoutRes != -1) {
                    bottomStub.setLayoutResource(mBottomLayoutRes);
                } else {
                    bottomStub.setLayoutResource(R.layout.tutorial_bottom_3);
                }
                break;
            default:
                break;
        }
        bottomStub.inflate();

        llBottom=LinearLayout.class.cast(findViewById(R.id.ll_bottom));
        if(mBottomBackgroud!=-1){
            llBottom.setBackgroundColor(getResources().getColor(mBottomBackgroud));
            bottomStub.setBackgroundColor(getResources().getColor(mBottomBackgroud));
        }
        mSkip = Button.class.cast(findViewById(R.id.skip));
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTutorial();
            }
        });

        mNext = ImageButton.class.cast(findViewById(R.id.next));
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
            }
        });

        mDone = findViewById(R.id.done);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTutorial();
            }
        });

        if (!showSkip) {
            mSkip.setVisibility(View.INVISIBLE);
        }
    }

    private void setIndicator(int index) {
        if (index < mDataConfig.getPageNum()) {
            for (int i = 0; i < mDataConfig.getPageNum(); i++) {
                ImageView circle = (ImageView) mCircles.getChildAt(i);
                if (i == index) {
                    circle.setImageResource(R.mipmap.ic_swipe_indicator_white_select);
//                    circle.setColorFilter(getResources().getColor(mDataConfig.getIndicatorSelectColor()));//tutorial_indicator_selected
                } else {
                    circle.setImageResource(R.mipmap.ic_swipe_indicator_white_18dp);
//                    circle.setColorFilter(getResources().getColor(mDataConfig.getIndicatorColor()));//android.R.color.transparent
                }
            }
        }
    }

    protected void endTutorial() {
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            return;
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void setBottomLayoutRes(int bottomLayoutRes) {
        mBottomLayoutRes = bottomLayoutRes;
    }
    public void setOnEndListener(OnEndListener onEndListener) {
        mOnEndListener = onEndListener;
    }
    private class MySpringListener extends SimpleSpringListener {
        @Override
        public void onSpringUpdate(Spring spring) {
            // On each update of the spring value, we adjust the scale of the image view to match the
            // springs new value. We use the SpringUtil linear interpolation function mapValueFromRangeToRange
            // to translate the spring's 0 to 1 scale to a 100% to 50% scale range and apply that to the View
            // with setScaleX/Y. Note that rendering is an implementation detail of the application and not
            // Rebound itself. If you need Gingerbread compatibility consider using NineOldAndroids to update
            // your view properties in a backwards compatible manner.
//            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0.5);
//            mDone.setScaleX(mappedValue);
//            mDone.setScaleY(mappedValue);
//
            double value = spring.getCurrentValue();

            // Map the spring to the feedback bar position so that its hidden off screen and bounces in on tap.
            float barPosition =
                    (float) SpringUtil.mapValueFromRangeToRange(value, 0, 1, mDone.getHeight(), 0);
            mDone.setTranslationY(barPosition);
        }

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

           TutorialFragment fragment = TutorialFragment.newInstance(mStyle);
            if (mDataConfig.getBackgroundColors() != null && mDataConfig.getBackgroundColors().length > 0) {
                fragment.setBackgroundColor(mDataConfig.getBackgroundColors()[position]);
            }
            if (mDataConfig.getTitles() != null && mDataConfig.getTitles().length > 0) {
                fragment.setHeadText(mDataConfig.getTitles()[position]);
            }
            if (mDataConfig.getContents() != null && mDataConfig.getContents().length > 0) {
                fragment.setContentText(mDataConfig.getContents()[position]);
            }
            if (mDataConfig.getImageResources() != null && mDataConfig.getImageResources().length > 0) {
                fragment.setImageResource(mDataConfig.getImageResources()[position]);
            }
            if (mDataConfig.getHeadTextColors() != null && mDataConfig.getHeadTextColors().length > 0) {
                fragment.setHeadTextColor(mDataConfig.getHeadTextColors()[position]);
            }
            if (mDataConfig.getContentTextColors() != null && mDataConfig.getContentTextColors().length > 0) {
                fragment.setContentTextColor(mDataConfig.getContentTextColors()[position]);
            }
            if (mDataConfig.getHeadTextSizes() != null && mDataConfig.getHeadTextSizes().length > 0) {
                fragment.setHeadTextSize(mDataConfig.getHeadTextSizes()[position]);
            }
            if (mDataConfig.getContentTextSizes() != null && mDataConfig.getContentTextSizes().length > 0) {
                fragment.setContentTextSize(mDataConfig.getContentTextSizes()[position]);
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return mDataConfig.getPageNum();
        }


    }

    public class CrossFadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page.findViewById(R.id.tutorial_container);
            View text_head = page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View welcomeImage = page.findViewById(R.id.welcome_img);


//            if (0 <= position && position < 1) {
//                ViewHelper.setTranslationX(page, pageWidth * -position);
//            }
//            if (-1 < position && position <= 0) {
//                ViewHelper.setTranslationX(page, pageWidth * -position);
//            }
            if (position > -1 && position < 1) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }


//            if (position <= -1.0f || position >= 1.0f) {
//                return;
//            } else if (position == 0.0f) {
//                return;
//            } else {
            if (backgroundView != null) {
                ViewHelper.setAlpha(backgroundView, 1.0f - Math.abs(position));
            }

            if (text_head != null) {
                ViewHelper.setTranslationX(text_head, pageWidth * position);
                ViewHelper.setAlpha(text_head, 1.0f - Math.abs(position));
            }

            if (text_content != null) {
                ViewHelper.setTranslationX(text_content, pageWidth * position);
                ViewHelper.setAlpha(text_content, 1.0f - Math.abs(position));
            }

            if (welcomeImage != null) {
                ViewHelper.setTranslationX(welcomeImage, (float) (pageWidth / 2 * position));
                ViewHelper.setAlpha(welcomeImage, 1.0f - Math.abs(position));
            }

//            }


        }
    }
    public interface OnEndListener {
        void onEnd();
    }

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }


    @Override
    protected boolean isStatusBarDarkMode() {
        return false;
    }
}