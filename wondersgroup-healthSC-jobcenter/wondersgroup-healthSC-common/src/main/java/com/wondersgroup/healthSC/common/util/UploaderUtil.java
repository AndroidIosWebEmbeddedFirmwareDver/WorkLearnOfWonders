package com.wondersgroup.healthSC.common.util;


import com.qiniu.common.QiniuException;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


/**
 * Created by zhuchunliu on 2016/3/9.
 */
public class UploaderUtil {

    private static final Logger logger = LoggerFactory.getLogger(UploaderUtil.class);

    private String domain = null;
    private String bucket = null;
    private Auth auth = null;
    private UploadManager uploadManager = null;

    public UploaderUtil(){

    }
    public UploaderUtil(String access_key,String secret_key,String bucket,String domain){
        this.auth = Auth.create(access_key, secret_key);
        this.uploadManager = new UploadManager();
        this.bucket = bucket;
        this.domain = domain;
    }


    private String getUpToken() {
        return this.auth.uploadToken(this.bucket);
    }

    public String uploadFile(byte[] bytes){
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            this.uploadManager.put(bytes,name,getUpToken());
        } catch (QiniuException ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage(),ex);
            return null;
        }

        return this.domain+name;
    }

}
