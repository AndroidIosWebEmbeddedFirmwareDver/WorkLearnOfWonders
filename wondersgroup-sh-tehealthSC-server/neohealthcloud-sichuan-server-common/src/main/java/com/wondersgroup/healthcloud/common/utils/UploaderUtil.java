package com.wondersgroup.healthcloud.common.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * Created by zhuchunliu on 2016/3/9.
 */
public class UploaderUtil {
    private final static String ACCESS_KEY = "uWCBSNid5ZGbs1exNGwoV9oCfvz3embV5lfojnog";
    private final static String SECRET_KEY = "7ms0V8Lk4S1goqClOaFL9mJmoOeMOwhdl3Zfi_XV";
    public final static Integer expires = 3600;//3600秒
    public final static String domain = "http://og3xulzx6.bkt.clouddn.com/";
    public final static String bucket = "healthcloud";
    private static Auth auth = null;

    static {
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    }

    private static String[] buckets() {
        BucketManager bucketManager = new BucketManager(auth);
        try {
            return bucketManager.buckets();
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUpToken() {
        return auth.uploadToken(bucket, null, expires, null);
    }//todo

    public static String getUpTokenUEditor(String key) {
        String returnBody = "{\"url\": $(key), \"state\": \"SUCCESS\", \"name\": $(fname),\"size\": \"$(fsize)\",\"w\": \"$(imageInfo.width)\",\"h\": \"$(imageInfo.height)\"}";
        return auth.uploadToken(bucket, key, expires, new StringMap().put("returnBody", returnBody));
    }
}
