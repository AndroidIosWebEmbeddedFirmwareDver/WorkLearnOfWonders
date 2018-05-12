package com.wondersgroup.hs.healthcloud.common.logic;

import android.os.Build;

import com.wondersgroup.hs.healthcloud.common.CommonActivity;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/11/9 17:44
 */

public class DialogResponseCallback<T> extends ResponseCallback<T> {
    protected CommonActivity mActivity;

    public DialogResponseCallback(CommonActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isFinish()) {
            UIUtil.showProgressBar(mActivity);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (!isFinish()) {
            UIUtil.hideProgressBar(mActivity);
        }
    }

    private boolean isFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed()) {
            return true;
        }
        return mActivity.isFinishing();
    }
}
