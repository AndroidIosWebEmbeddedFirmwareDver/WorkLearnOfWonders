package com.wondersgroup.hs.healthcloud.common.util;

import android.os.Environment;

import com.wondersgroup.hs.healthcloud.common.BuildConfig;

import java.io.File;

public class BaseConstant {
    /**
     * 日志的tag前缀
     */
    public static final String LOG_PREFIX = "health_cloud->";
    
    public static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    /**
     * 本地文件保存的根目录
     */
    public static final String BASE_LOCAL_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + BuildConfig.APPLICATION_ID;
    /**
     * 设备DeviceID存储文件
     */
    public static final String DEVICE_ID_PATH = BASE_LOCAL_PATH + "/.id";
    /**
     * 保存日志文件的目录
     */
    public static final String LOG_PATH = BASE_LOCAL_PATH + "/log";
    /**
     * 保存临时文件的目录
     */
    public static final String TEMP_PATH = BASE_LOCAL_PATH + "/temp";
    /**
     * 下载的文件保存路径
     */
    public static final String SAVE_PATH = BASE_LOCAL_PATH + "/save";
    /**
     * Http加载操作类型
     */
    public static final int TYPE_INIT = 0;
    public static final int TYPE_RELOAD = 1;
    public static final int TYPE_NEXT = 2;
    
    public static final int REQUEST_CODE_PICK = 3001;
    public static final int REQUEST_CODE_TAKE_PHOTO = 3002;
    public static final int REQUEST_CODE_CROP = 3003;

    public static final String ACTION_PICK_PHOTO = ".PICK_PHOTO";
}
