package com.wonders.health.venus.open.doctor.module;
/*
 * Created by sunning on 2017/6/2.
 */

import android.content.Context;
import android.os.Bundle;

import com.wonders.health.venus.open.doctor.R;
import com.wondersgroup.hs.healthcloud.common.tutorial.BaseTutorialActivity;
import com.wondersgroup.hs.healthcloud.common.tutorial.DataConfig;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TutorialActivity extends BaseTutorialActivity{
    private final int PAGE_NUM = 3;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // 展示样式，依据图片位置共三种
        setStyle(BaseTutorialActivity.IMAGE_TOP);
//        setBottomLayoutRes(R.layout.layout_tutorial_bottom);
        setBottomStyle(BOTTOM_CUSTOM);
        showSkipButton(false);
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    protected DataConfig initDataConfig() {
        DataConfig dataConfig = new DataConfig(PAGE_NUM);

        // 背景色
        dataConfig.setBackgroundColors(new int[]{
                R.color.bc1,
                R.color.bc1,
                R.color.bc1});

        // 图片
        dataConfig.setImageResources(new int[]{
                R.mipmap.tutorial_01,
                R.mipmap.tutorial_02,
                R.mipmap.tutorial_03});

        // 文描标题，可以为空
        dataConfig.setTitles(new String[]{
                "患者管理",
                "图文咨询",
                "转诊服务"});


        dataConfig.setHeadTextColors(new int[]{
                R.color.tc1,
                R.color.tc1,
                R.color.tc1
        });

        dataConfig.setHeadTextSizes(new int[]{
                23,
                23,
                23
        });

        //  文描内容，可以为空
        dataConfig.setContents(new String[]{
                "分类搜索 高效查询",
                "线上互通 及时咨询",
                "患者转诊 一站搞定"}
        );

        dataConfig.setContentTextColors(new int[]{
                R.color.tc3,
                R.color.tc3,
                R.color.tc3
        });
        dataConfig.setContentTextSizes(new int[]{
                15,
                15,
                15
        });

        return dataConfig;

    }
}
