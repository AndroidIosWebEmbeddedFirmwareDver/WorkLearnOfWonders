package com.wonders.health.venus.open.user.module.health;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.view.ObservableScrollView;
import com.wondersgroup.hs.healthcloud.common.util.BitmapTools;
import com.wondersgroup.hs.healthcloud.common.util.SystemUtil;

/**
 * Created by zjy on 2016/11/8.
 */

public class HospitalDescActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_hospital_img;
    private TextView txt_hospital_name;
    private TextView txt_hospital_level;
    private TextView txt_desc;
    private View mTitle,title_bar_line;
    private ObservableScrollView mScrollView;
    private TextView mTv_title;
    private ImageView mImg_back;

    @Override
    protected void initViews() {
        setContentView(R.layout.layout_hospital_desc);
        img_hospital_img = (ImageView) findViewById(R.id.img_hospital_img);
        txt_hospital_name = (TextView) findViewById(R.id.txt_hospital_name);
        txt_hospital_level = (TextView) findViewById(R.id.txt_hospital_level);
        txt_desc = (TextView) findViewById(R.id.txt_desc);
        mTitle = findViewById(R.id.ll_title);
        mImg_back = (ImageView) findViewById(R.id.img_back);
        mTv_title = (TextView) findViewById(R.id.txt_title);
        title_bar_line = findViewById(R.id.title_bar_line);
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);

        mTitle.setOnClickListener(this);
        mImg_back.setOnClickListener(this);
        mScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mScrollView.setOnScrollListener(new ObservableScrollView.OnScrollListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y < 0 && mTitle.getVisibility() == View.VISIBLE) {
                    AlphaAnimation animation = new AlphaAnimation(1, 0);
                    animation.setDuration(300);
                    mTitle.clearAnimation();
                    mTitle.startAnimation(animation);
                    mTitle.setVisibility(View.GONE);
                } else if (y >= 0 && mTitle.getVisibility() == View.GONE) {
                    AlphaAnimation animation = new AlphaAnimation(0, 1);
                    animation.setDuration(300);
                    mTitle.clearAnimation();
                    mTitle.startAnimation(animation);
                    mTitle.setVisibility(View.VISIBLE);
                }
                changeTitleStyle(y);
            }
        });
        if (SystemUtil.isTintStatusBarAvailable(this)) {
            mTitle.setPadding(0, SystemUtil.getStatusBarHeight(), 0, 0);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        new BitmapTools(HospitalDescActivity.this).display(img_hospital_img, getIntent().getStringExtra("img"), R.mipmap.bg_hospital);
        txt_hospital_name.setText(getIntent().getStringExtra("name") + "");
        txt_hospital_level.setText(getIntent().getStringExtra("level") + "");
        txt_desc.setText("      " + getIntent().getStringExtra("desc"));

    }
    
    private void changeTitleStyle(int y){
        final ColorDrawable drawable = new ColorDrawable(getResources().getColor(R.color.bc1));
        drawable.setAlpha(0);
        mTitle.setBackgroundDrawable(drawable);
        int titleBarheight = getResources().getDimensionPixelSize(com.wondersgroup.hs.healthcloud.common.R.dimen.actionbar_height);
        if (mTitle != null) {
            float delta = (float) Math.abs(y) / titleBarheight;
            delta = delta > 1 ? 1 : delta;
            int alpha = 10 + (int) (delta * 245);
            String alphaStr = Integer.toHexString(alpha);
            alphaStr = alphaStr.length() == 1 ? "0" + alphaStr : alphaStr;
            if (alpha > 20) {
                mTv_title.setTextColor(Color.parseColor("#" + alphaStr + "666666"));
                title_bar_line.setVisibility(View.VISIBLE);
                title_bar_line.setBackgroundColor(getResources().getColor(R.color.divider_color));
                mImg_back.setImageResource(R.mipmap.ic_back);
            } else {
                mTv_title.setTextColor(Color.parseColor("#ffffffff"));
                title_bar_line.setVisibility(View.GONE);
                //title_bar_line.setBackgroundColor(getResources().getColor(R.color.bc1 ));
                mImg_back.setImageResource(R.mipmap.ic_back_light);
            }
            drawable.setAlpha((int) (delta * 255));
            mTitle.setBackgroundDrawable(drawable);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean isShowTintStatusBar() {
        return false;
    }
}
