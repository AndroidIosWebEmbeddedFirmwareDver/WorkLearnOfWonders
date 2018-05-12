package com.wondersgroup.hs.healthcloud.common.view;
/*
 * Created by sunning on 2017/6/16.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ScrollChangedWebView extends WebView{

    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ScrollChangedWebView(Context context) {
        super(context);
    }

    public ScrollChangedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollChangedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollChangedWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollChangedCallback != null){
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(OnScrollChangedCallback mOnScrollChangedCallback) {
        this.mOnScrollChangedCallback = mOnScrollChangedCallback;
    }

    public interface OnScrollChangedCallback {
        public void onScroll(int dx, int dy);
    }
}
