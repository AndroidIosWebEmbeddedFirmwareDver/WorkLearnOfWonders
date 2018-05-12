package com.wonders.health.venus.open.user.module.consultation;

import android.os.Bundle;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wondersgroup.hs.healthcloud.common.entity.event.EmConnectionConflictEvent;
import com.wondersgroup.hs.healthcloud.common.util.PermissionType;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

/**
 * Created by wang on 2017/5/26.
 */

public class BaseChatActivity extends BaseActivity {
    @Override
    protected boolean isShowTitleBar() {
        return false;
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }


    @Override
    protected void initViews() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        super.initData(savedInstanceState);
    }

    public void onEvent(EmConnectionConflictEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UIUtil.toastShort(BaseChatActivity.this, BaseChatActivity.this.getResources().getString(R.string.account_login_in_other_deivce));
            }
        });
        finish();
    }

    @Override
    protected PermissionType[] applyPermission() {
        return new PermissionType[]{PermissionType.WRITE_EXTERNAL_STORAGE};
    }
}
