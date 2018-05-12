package com.wondersgroup.hs.healthcloud.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

/**
 * 保存和获取应用设置文件
 */
public class PrefUtil {

    public static final String GROUPS_OF_NOTIFICATION_DISABLED="groups_of_notification_disabled";//消息免打扰开关


    public static int getInt(Context context, String key) {
        int result = -1;
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences != null) {
                result = sharedPreferences.getInt(key, -1);
            }
        }
        return result;
    }

    public static int getInt(Context context, String key, int defaultValue) {
        int result = -1;
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences != null) {
                result = sharedPreferences.getInt(key, defaultValue);
            }
        }
        return result;
    }

    public static void putInt(Context context, String key, int value) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Editor edit = sharedPreferences.edit();
            edit.putInt(key, value);
            if (Build.VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        boolean result = false;
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences != null) {
                result = sharedPreferences.getBoolean(key, defaultValue);
            }
        }
        return result;
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Editor edit = sharedPreferences.edit();
            edit.putBoolean(key, value);
            if (Build.VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    /**
     * 添加公共key
     * putKey
     */
    public static void putString(Context context, String key, String value) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Editor edit = sharedPreferences.edit();
            edit.putString(key, value);
            if (Build.VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    public static void remove(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Editor edit = sharedPreferences.edit();
            edit.remove(key);
            if (Build.VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
    }

    public static void removeAllStart(Context context, String keyStart) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Map<String, ?> s = sharedPreferences.getAll();
            if (s != null && !s.isEmpty()) {
                Editor edit = sharedPreferences.edit();
                for (String key : s.keySet()) {
                    if (key.startsWith(keyStart)) {
                        edit.remove(key);
                    }
                }
                if (Build.VERSION.SDK_INT >= 9) {
                    edit.apply();
                } else {
                    edit.commit();
                }
            }
        }
    }

    /**
     * 读取公共key
     * getKey
     */
    public static String getString(Context context, String key) {
        return getString(context,key,"");
    }

    /**
     * 读取公共key(可以设置默认值)
     * getKey
     */
    public static String getString(Context context, String key,String defaultValue) {
        if (context != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences != null) {
                return sharedPreferences.getString(key, defaultValue);
            }
        }
        return null;
    }

    /**
     * 把对象转化为json并存储
     * putJsonObject
     */
    public static void putJsonObject(Context context, String key, Object object) {
        if (object != null) {
            putString(context, key, JSON.toJSONString(object));
        }
    }

    /**
     * 获取存储内容并转为对象
     * getJsonObject
     */
    public static <T> T getJsonObject(Context context, String key, Class<T> clazz) {
        String str = getString(context, key);
        if(!TextUtils.isEmpty(str)){
            return JSON.parseObject(str, clazz);
        }
        return null;
    }

    /**
     * 把对象转化为json并存储
     * putJsonArray
     */
    public static void putJsonArray(Context context, String key, Object object) {
        if (object != null) {
            putString(context, key, JSON.toJSONString(object));
        }
    }

    /**
     * 获取存储内容并转为列表对象
     * getJsonArray
     */
    public static <T> List<T> getJsonArray(Context context, String key, Class<T> clazz) {
        String str = getString(context, key);
        if(!TextUtils.isEmpty(str)){
            return JSON.parseArray(str, clazz);
        }
        return null;
    }

}
