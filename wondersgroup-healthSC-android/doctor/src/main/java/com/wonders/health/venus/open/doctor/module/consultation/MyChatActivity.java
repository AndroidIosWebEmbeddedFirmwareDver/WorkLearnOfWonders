package com.wonders.health.venus.open.doctor.module.consultation;

import android.os.Bundle;
import android.util.Log;

import com.wonders.health.venus.open.doctor.R;


/**
 * Created by wang on 2017/5/26.
 */

public class MyChatActivity extends BaseChatActivity {
    protected MyChatFragment chatFragment;
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (chatFragment == null) {
            chatFragment = new MyChatFragment();
            //传入参数
            chatFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected boolean needCheckLogin() {
        return false;
    }
}
