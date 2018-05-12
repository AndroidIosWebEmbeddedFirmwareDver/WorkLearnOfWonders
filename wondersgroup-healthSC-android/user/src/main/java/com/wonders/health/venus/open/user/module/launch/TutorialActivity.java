package com.wonders.health.venus.open.user.module.launch;

import android.content.Context;
import android.os.Bundle;

import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.tutorial.BaseTutorialActivity;
import com.wondersgroup.hs.healthcloud.common.tutorial.DataConfig;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by wang on 2016/8/12 16:35.
 * Class Name :TutorialActivity
 */
public class TutorialActivity extends BaseTutorialActivity {
    private final int PAGE_NUM = 4;

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
        setBottomBackgroud(R.color.tutorial_bc);
        showSkipButton(false);
        setBottomBackgroud(R.color.transparent);
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
                R.color.tutorial_bc,
                R.color.tutorial_bc,
                R.color.tutorial_bc,
                R.color.tutorial_bc});

        // 图片
        dataConfig.setImageResources(new int[]{
                R.mipmap.tutorial_01,
                R.mipmap.tutorial_02,
                R.mipmap.tutorial_03,
                R.mipmap.tutorial_04});

//        // 文描标题，可以为空
//        dataConfig.setTitles(new String[]{
//                "预约挂号 线上搞定",
//                "在线支付 不用排队",
//                "热门资讯 生活帮手",
//                "服务四川 健康无忧"}
//        );
//        dataConfig.setHeadTextColors(new int[]{
//                R.color.tc1,
//                R.color.tc1,
//                R.color.tc1,
//                R.color.tc1
//        });
//
//        dataConfig.setHeadTextSizes(new int[]{
//                23,
//                23,
//                23,
//                23
//        });
//
//        //  文描内容，可以为空
//        dataConfig.setContents(new String[]{
//                "让就医更方便",
//                "让支付更快捷",
//                "让资讯更可靠",
//                "让生活更健康"});
//
//        dataConfig.setContentTextColors(new int[]{
//                R.color.tc3,
//                R.color.tc3,
//                R.color.tc3,
//                R.color.tc3
//        });
//        dataConfig.setContentTextSizes(new int[]{
//                15,
//                15,
//                15,
//                15
//        });

        return dataConfig;

    }

}
