package com.wondersgroup.hs.healthcloud.common.logic;

import android.util.Log;

import com.wondersgroup.hs.healthcloud.common.CommonApp;
import com.wondersgroup.hs.healthcloud.common.entity.BaseResponse;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.util.DialogUtils;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.List;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/20 14:10
 */
public class ResponseCallback<T> extends BaseCallback<T> {
    public boolean mSuccess;

    @Override
    public void onSuccess(T t) {
        mSuccess = true;
    }

    @Override
    public void onSuccess(List<T> list) {
        super.onSuccess(list);
        mSuccess = true;
    }

    @Override
    public void onFailure(Exception e) {
        mSuccess = false;
        if (isShowNotice()) {
            if (e instanceof HttpException) {
//                UIUtil.toastShort(CommonApp.getApp(), e.getMessage());
                DialogUtils.showNormalMessage(CommonApp.getApp(), null != e.getMessage() ? e.getMessage() : "");

            } else {
//                UIUtil.toastShort(CommonApp.getApp(), ERR_MSG);
                DialogUtils.showNormalMessage(CommonApp.getApp(), ERR_MSG);

            }
        }
    }

    @Override
    public void onResponse(BaseResponse response) {
        super.onResponse(response);
        if (isShowNotice() && response.isSuccess()) {
            UIUtil.toastShort(CommonApp.getApp(), response.msg);
        }
    }

    public boolean isShowNotice() {
        return true;
    }
}
