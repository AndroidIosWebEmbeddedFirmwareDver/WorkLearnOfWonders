package com.wondersgroup.hs.healthcloud.common.http;

import android.util.Log;

import okhttp3.Call;


/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/8/4 9:28
 */
public class CallProxy {

    private Call mCall;
    private Object mTag;

    public CallProxy(Call call) {
        mCall = call;
    }

    public CallProxy setTag(Object tag) {
        mTag = tag;
        return this;
    }

    public Object getTag() {
        return mTag;
    }

    public void cancel() {
        Log.d("bacy", "cancel");
        mCall.cancel();
    }

    public boolean isCanceled() {
        return mCall.isCanceled();
    }

}
