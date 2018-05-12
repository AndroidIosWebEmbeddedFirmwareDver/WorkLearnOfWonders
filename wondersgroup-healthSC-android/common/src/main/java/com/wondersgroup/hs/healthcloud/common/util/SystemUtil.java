package com.wondersgroup.hs.healthcloud.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class SystemUtil {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";

    private static String LOG_TAG = "SystemUtil";
    private static String sUserAgent;
    private static String sDeviceId;
    private static int sVersionCode = -1;
    private static String sVersionName;
    private static String sNavBarOverride;
    private static boolean sHasMIUI6;

    static {
        // Android allows a system property to override the presence of the navigation bar.
        // Used by the emulator.
        // See https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
                String miUIVersion = (String) m.invoke(null, "ro.miui.ui.version.name");
                sHasMIUI6 = "V6".equals(miUIVersion) || "V7".equals(miUIVersion) || "V8".equals(miUIVersion)
                        || "V9".equals(miUIVersion);
            } catch (Throwable e) {
                sNavBarOverride = null;
            }
        }
    }

    /**
     * 获取应用Android系统版本 getOSVersion
     *
     * @param
     * @return
     * @since 1.0
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备操作系统版本号 getCurrentOsVersionCode
     *
     * @return
     * @since 1.0
     */
    public static int getCurrentOsVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备型号 getPhoneModel
     *
     * @param
     * @return
     * @since 1.0
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取设备的分辨率 getDeviceMetrics
     *
     * @param
     * @return
     * @since 1.0
     */
    public static String getDeviceMetrics() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels + "x" + metrics.heightPixels;
    }

    /**
     * 获取设备密度 getDeviceDpi
     *
     * @param
     * @return
     * @since 1.0
     */
    public static String getDensityDpi() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.densityDpi + "";
    }


    /**
     * 获取设备屏幕宽度 getScreenWidth
     *
     * @param
     * @return
     * @since 1.0
     */
    public static int getScreenWidth() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取设备屏幕高度 getScreenHeight
     *
     * @param
     * @return
     * @since 1.0
     */
    public static int getScreenHeight() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * 计算actionbar高度
     * getActionBarHeight
     *
     * @param activity
     * @return
     * @since 3.5
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static float getActionBarHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TypedArray actionbarSizeTypedArray = activity
                    .obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
            float a = actionbarSizeTypedArray.getDimension(0, 0);
            actionbarSizeTypedArray.recycle();
            return a;
        }
        return 0;
    }

    /**
     * 计算状态栏高度高度
     * getStatusBarHeight
     * @return
     */
    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 计算页面高度
     * measureActivityHeight
     *
     * @param activity
     * @return
     * @since 1.0
     */
    public static int getContentHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.bottom - frame.top;
        return statusBarHeight;
    }

    @TargetApi(14)
    public static int getNavigationBarHeight(Context context) {
        Resources res = context.getResources();
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavBar(context)) {
                String key;
                if (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    key = NAV_BAR_HEIGHT_RES_NAME;
                } else {
                    key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
                }
                return getInternalDimensionSize(res, key);
            }
        }
        return result;
    }

    @TargetApi(14)
    private static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag (see static block)
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    public static boolean isTintStatusBarAvailable(Activity activity) {
        boolean isTintStatusBarAvailable = false;
        if (ApiCompatibleUtil.hasKitKat()) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            if (ApiCompatibleUtil.hasLollipop()) {
                // check window flags
                int bits = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                if ((winParams.flags & bits) == bits && (win.getDecorView().getSystemUiVisibility() & uiOptions) == uiOptions) {
                    isTintStatusBarAvailable = true;
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                // check theme attrs
                int[] attrs = {android.R.attr.windowTranslucentStatus};
                TypedArray a = activity.obtainStyledAttributes(attrs);
                try {
                    isTintStatusBarAvailable = a.getBoolean(0, false);
                } finally {
                    a.recycle();
                }

                // check window flags
                int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                if ((winParams.flags & bits) == bits) {
                    isTintStatusBarAvailable = true;
                }
            }
        }
        return isTintStatusBarAvailable;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTintStatusBarAvailable(Activity activity, boolean darkMode) {
        Window window = activity.getWindow();
        if (ApiCompatibleUtil.hasLollipop()) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (sHasMIUI6) {
                if (darkMode) {
                    setStatusBarDarkMode(activity, darkMode);
                }
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (ApiCompatibleUtil.hasM()) {
                int vis = window.getDecorView().getSystemUiVisibility();
                if (darkMode) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                window.getDecorView().setSystemUiVisibility(vis);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                if (darkMode) {
                    window.setStatusBarColor(Color.parseColor("#88888888"));
                } else {
                    window.setStatusBarColor(Color.TRANSPARENT);
                }
            }

        } else if (ApiCompatibleUtil.hasKitKat()) {
            // 4.4上如果白色title，使用沉浸效果不太好
            if (!darkMode | sHasMIUI6) {
                //透明状态栏
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //透明导航栏
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            if (sHasMIUI6) {
                setStatusBarDarkMode(activity, darkMode);
            }
        }
    }

    // only useful on miui system
    private static void setStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取设备号 getIMEI
     *
     * @param context
     * @return
     * @since 1.0
     */
    public static String getIMEI(Context context) {
        if (TextUtils.isEmpty(sDeviceId)) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                sDeviceId = tm.getDeviceId();
            } catch (SecurityException e) {
            }
            // 如果系统取不到imei，本地生成一个uuid作为唯一标识
            if (TextUtils.isEmpty(sDeviceId) || sDeviceId.startsWith("0000000") || sDeviceId.startsWith("11111111")) {
                sDeviceId = FileUtil.readStr(BaseConstant.DEVICE_ID_PATH);
                if (TextUtils.isEmpty(sDeviceId)) {
                    sDeviceId = UUID.randomUUID().toString();
                    FileUtil.writeStr(BaseConstant.DEVICE_ID_PATH, sDeviceId);
                }
            }
        }
        return sDeviceId;
    }

    /**
     * 获取并设置UserAgent getDefaultUserAgent
     *
     * @param context
     * @return
     * @since 1.0
     */
    public static String getDefaultUserAgent(Context context) {

        if (TextUtils.isEmpty(sUserAgent)) {
            sUserAgent = System.getProperty("http.agent", "") + getApplicationPackageName(context) + "/" + getVersionName(context);
        }
        return sUserAgent;
    }

    /**
     * 获取应用VersionCode getVersionCode
     *
     * @param context
     * @return
     * @since 1.0
     */
    public static int getVersionCode(Context context) {
        if (sVersionCode == -1) {
            try {
                String name = getApplicationPackageName(context);
                sVersionCode = context.getPackageManager().getPackageInfo(name, 0).versionCode;
            } catch (NameNotFoundException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        return sVersionCode;
    }

    /**
     * 获取应用程序版本号 getVersionName
     *
     * @param context
     * @return
     * @since 1.0
     */
    public static String getVersionName(Context context) {
        if (TextUtils.isEmpty(sVersionName)) {
            try {
                String name = getApplicationPackageName(context);
                sVersionName = context.getPackageManager().getPackageInfo(name, 0).versionName;
            } catch (NameNotFoundException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        return sVersionName;
    }

    /**
     * 获取应用包名称 getApplicationPackageName
     *
     * @param context
     * @return
     * @since 1.0
     */
    public static String getApplicationPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取Application的META-DATA
     * @param context
     * @return
     */
    public static ApplicationInfo getApplicationMetaData(Context context) {
        ApplicationInfo info = null;
        try {
            info=context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return info;
    }

    public static String getApplicationName(Context context){
        PackageInfo pkg = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            pkg = packageManager.getPackageInfo(getApplicationPackageName(context), 0);
            String appName = pkg.applicationInfo.loadLabel(packageManager).toString();
            return appName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否程序运行在后台
     * @param context
     * @return
     */
    public static  boolean isRunningForeground (Context context)
    {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName()))
        {
            return true ;
        }
        return false ;
    }
}
