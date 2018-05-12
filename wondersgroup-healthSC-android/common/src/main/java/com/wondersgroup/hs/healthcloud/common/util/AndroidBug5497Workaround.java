package com.wondersgroup.hs.healthcloud.common.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.wondersgroup.hs.healthcloud.common.CommonActivity;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2015/8/21 14:19
 */
public class AndroidBug5497Workaround {
    private boolean isKeybordShow;
    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity (Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean isTintStatusBarEnable;

    private AndroidBug5497Workaround(final Activity activity) {
        mChildOfContent = ((FrameLayout) activity.findViewById(android.R.id.content)).getChildAt(0);
        isTintStatusBarEnable = SystemUtil.isTintStatusBarAvailable(activity);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                // 无语
                if (activity instanceof CommonActivity) {
                    if (((CommonActivity) activity).mIsResume || isKeybordShow) {
                        possiblyResizeChildOfContent();
                    }
                } else {
                    possiblyResizeChildOfContent();
                }
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = ((ViewGroup)(mChildOfContent.getParent())).getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
                isKeybordShow = true;
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became
                isKeybordShow = false;
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        if (isTintStatusBarEnable) {
            return r.bottom;
        } else {
            return r.bottom - r.top;
        }
    }
}
