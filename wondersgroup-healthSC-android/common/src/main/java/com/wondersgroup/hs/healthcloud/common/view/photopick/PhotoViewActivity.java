package com.wondersgroup.hs.healthcloud.common.view.photopick;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wondersgroup.hs.healthcloud.common.CommonActivity;
import com.wondersgroup.hs.healthcloud.common.dao.PhotoDao;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.BaseConstant;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.photopick.adapter.PhotoViewPagerAdapter;

import java.util.ArrayList;


/**
 * 预览图片
 * PhotoViewActivity
 * chenbo
 * 2015年3月16日 下午3:46:27
 *
 * @version 1.0
 */
public class PhotoViewActivity extends CommonActivity {
    public final static String EXTRA_PHOTOS = "imgUrlList";
    public final static String EXTRA_INDEX = "selectIndex";
    public final static String EXTRA_TYPE = "type";

    public final static int TYPE_VIEW = 0;
    public final static int TYPE_LOCAL = 1;

    private ViewPager mViewPager;
    private ImageButton mCtrlBtn;
    private TextView mCountText;
    private View mTitleLayout;

    private BitmapTools mBitmapTools;
    private PhotoViewPagerAdapter mPagerAdapter;
    private ArrayList<String> imgUrlList;

    @Override
    protected void initViews() {
        getWindow().setBackgroundDrawable(null);
        setContentView(com.wondersgroup.hs.healthcloud.common.R.layout.activity_photo_view);

        mCountText = (TextView) findViewById(com.wondersgroup.hs.healthcloud.common.R.id.pageStateTxt);
        mViewPager = (ViewPager) findViewById(com.wondersgroup.hs.healthcloud.common.R.id.viewpager);
        mCtrlBtn = (ImageButton) findViewById(com.wondersgroup.hs.healthcloud.common.R.id.ctrlBtn);
        mTitleLayout = findViewById(com.wondersgroup.hs.healthcloud.common.R.id.photoview_title);

        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (imgUrlList != null) {
                    mCountText.setText(String.format("%d/%d", arg0 + 1, imgUrlList.size()));
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        if (SystemUtil.isTintStatusBarAvailable(this)) {
            ((RelativeLayout.LayoutParams) mTitleLayout.getLayoutParams()).topMargin += SystemUtil.getStatusBarHeight();
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBitmapTools = new BitmapTools(this);
        imgUrlList = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        int idx = getIntent().getIntExtra(EXTRA_INDEX, 0);

        mCountText.setText(String.format("%d/%d", idx + 1, imgUrlList.size()));

        mPagerAdapter = new PhotoViewPagerAdapter(this, imgUrlList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(idx, false);

        int type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        if (type == TYPE_VIEW) {
            mCtrlBtn.setVisibility(View.VISIBLE);
        } else {
            mCtrlBtn.setVisibility(View.GONE);
        }
    }

    public void onClick(View view) {
        if (view.getId() == com.wondersgroup.hs.healthcloud.common.R.id.ctrlBtn) {
            mBitmapTools.load(imgUrlList.get(mViewPager.getCurrentItem()), new ResponseCallback<Drawable>() {
                @Override
                public void onStart() {
                    super.onStart();
                    UIUtil.showProgressBar(PhotoViewActivity.this, "图片保存中...");
                }

                @Override
                public void onSuccess(Drawable drawable) {
                    super.onSuccess(drawable);
                    try {
                        String path = BaseConstant.SAVE_PATH + "/" + System.currentTimeMillis() + ".jpg";
                        new BitmapTools(PhotoViewActivity.this).saveDrawable(drawable, path);
                        new PhotoDao(PhotoViewActivity.this).addPhoto(path);
                        UIUtil.toastShort(PhotoViewActivity.this, "图片已经成功保存到:" + path);
                    } catch (Exception e) {
                        UIUtil.toastShort(PhotoViewActivity.this, "图片保存失败");
                    }

                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    UIUtil.hideProgressBar(PhotoViewActivity.this);
                }
            });
        }
    }

    @Override
    protected boolean useSwipeBackLayout() {
        return false;
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
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

}
