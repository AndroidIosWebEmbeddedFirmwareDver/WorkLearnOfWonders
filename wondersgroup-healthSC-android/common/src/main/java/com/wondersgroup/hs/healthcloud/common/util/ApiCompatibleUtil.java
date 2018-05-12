package com.wondersgroup.hs.healthcloud.common.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * api兼容工具类
 * ApiCompatibleUtil
 * tanghaihua
 * 2015年3月12日 下午5:32:38
 * @version 1.0
 */
public class ApiCompatibleUtil {
    
    @TargetApi(11)
    public static void enableStrictMode() {
        if (hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder().detectAll()
                    .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();

            if (hasHoneycomb()) {
                threadPolicyBuilder.penaltyFlashScreen();
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasGingerbreadMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean hasM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 移除Layout监听 removeLayoutListenerByApi16
     * 
     * @param observer
     * @param listener
     * @since 3.6.1
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void removeLayoutListenerByApi16(ViewTreeObserver observer, OnGlobalLayoutListener listener) {
        if (observer != null && listener != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                observer.removeGlobalOnLayoutListener(listener);
            } else {
                observer.removeOnGlobalLayoutListener(listener);
            }
        }
    }

    /**
     * 禁止回弹 setOverScrollModeByApi8
     * 
     * @param view
     * @since 3.6.1
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void setOverScrollModeByApi8(View view) {
        if (view != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
                view.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
        }
    }

//    /**
//     * 刷新媒体库
//     * refreshMediaByApi19
//     * @param mContext
//     * @param filename是我们的文件全名，包括后缀哦  
//     * @since 3.6.2
//     */
//    public static void refreshMediaByApi19(Context mContext, String filename) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));   
//        }else{
//            MediaScannerConnection.scanFile(mContext, new String[] { filename }, null,
//                    new MediaScannerConnection.OnScanCompletedListener() {
//                        public void onScanCompleted(String path, Uri uri) {
//                        }
//                    });
//        }
//    }
}
