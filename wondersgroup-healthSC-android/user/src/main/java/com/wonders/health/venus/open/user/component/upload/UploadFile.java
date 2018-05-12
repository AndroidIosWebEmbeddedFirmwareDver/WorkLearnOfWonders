package com.wonders.health.venus.open.user.component.upload;

import android.app.Activity;
import android.content.Context;

import com.qiniu.android.common.ServiceAddress;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.wonders.health.venus.open.user.entity.User;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wondersgroup.hs.healthcloud.common.entity.QiniuToken;
import com.wondersgroup.hs.healthcloud.common.http.HttpException;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zjy on 2016/3/4.
 */
public class UploadFile {
    private UserManager mUserManager;
    private Context mContext;
    private String mToken = "";
    public String  mExpires;
    public String mDomain;
    public String mImgName;
    private static UploadManager uploadManager;
    private volatile String key;
    private volatile ResponseInfo info;//上传七牛后的反馈信息；
    private volatile JSONObject resp;

    public UploadFile(Context context){
        mContext = context;
        mUserManager = UserManager.getInstance();
        if (uploadManager == null) {
            synchronized (UploadFile.class) {
                if (uploadManager == null) {
                    ServiceAddress s = new ServiceAddress("http://upwelcome.qiniu.com", Zone.zone0.up.backupIps);
                    Zone z = new Zone(s, Zone.zone0.upBackup);
                    Configuration c = new Configuration.Builder()
                            .zone(z)
                            .build();

                    uploadManager = new UploadManager(c);
                }
            }
        }
    }

    public void getTokenAndUpload(final File f,final UpCompletionHandler upCompletionHandler){
        mUserManager.getQiniuToken(new ResponseCallback<QiniuToken>() {
            @Override
            public void onStart() {
                super.onStart();
                UIUtil.showProgressBar((Activity) mContext);
            }

            @Override
            public void onSuccess(QiniuToken qiniuToken) {
                super.onSuccess(qiniuToken);
                mToken = qiniuToken.token;
                mExpires = qiniuToken.expires;
                mDomain = "http://"+qiniuToken.domain+"/";
                try {
                    uploadFile(f,upCompletionHandler);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    /**
     * 上传文件到七牛
     * @param f
     * @throws Throwable
     */
    public void uploadFile(File f,UpCompletionHandler upCompletionHandler) throws Throwable {
        final String expectKey = generateUploadPath();
        mImgName = expectKey;
        Map<String, String> params = new HashMap<String, String>();
        params.put("x:foo", "fooval");
        final UploadOptions opt = new UploadOptions(params, null, true, null, null);
        uploadManager.put(f, expectKey, mToken, upCompletionHandler, opt);
    }

    public void uploadImage(final String filePath, final ResponseCallback<String> callback) {
        final String imageName = generateUploadPath();
        mUserManager.getQiniuToken(new ResponseCallback<QiniuToken>() {
            @Override
            public void onStart() {
                super.onStart();
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            public void onSuccess(QiniuToken qiniuToken) {
                super.onSuccess(qiniuToken);
                mToken = qiniuToken.token;
                mExpires = qiniuToken.expires;
                mDomain = qiniuToken.domain;

                try {

                    uploadFile(imageName, filePath, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info != null && info.isOK()) {
                                if (callback != null) {
//                                    LogUtils.d(new StringBuffer(mDomain).append(key).toString());
                                    callback.onSuccess(new StringBuffer(mDomain).append(key).toString());
                                }
                            } else {
                                if (callback != null) {
                                    callback.onFailure(new HttpException("上传失败"));
                                    callback.onFinish();
                                }
                            }
                        }
                    });
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    callback.onFailure(new HttpException("上传失败"));
                    callback.onFinish();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (!mSuccess && callback != null) {
                    callback.onFailure(new HttpException("获取UploadToken失败"));
                }
                callback.onFinish();
            }
        });
    }
    /**
     * 上传用户头像
     * @param avatarPath
     * @param callback
     */
    public void postUserAvaTar(String avatarPath, final ResponseCallback<User> callback){
        final String uploadPath = generateUploadPath();
        UpCompletionHandler upCompletionHandler = new UpCompletionHandler() {
            @Override
            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                if(responseInfo.isOK()){
                    UserManager.getInstance().postUserAvatar(mDomain + uploadPath, callback);
                }else{
                    if(callback !=null){
                        callback.onFailure(new HttpException("上传失败"));
                        callback.onFinish();
                    }
                }
            }
        };

        getTokenAndUpload(uploadPath, avatarPath, callback, upCompletionHandler);
    }

    /**
     * 获取token
     * @param up_key
     * @param filePath
     * @param callback
     * @param upCompletionHandler
     */
    public void getTokenAndUpload(final String up_key, final String filePath, final ResponseCallback callback, final UpCompletionHandler upCompletionHandler){
        mUserManager.getQiniuToken(new ResponseCallback<QiniuToken>() {
            @Override
            public void onStart() {
                super.onStart();
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            public void onSuccess(QiniuToken qiniuToken) {
                super.onSuccess(qiniuToken);
                mToken = qiniuToken.token;
                mExpires = qiniuToken.expires;
                mDomain = qiniuToken.domain;
                try {
                    uploadFile(up_key, filePath, upCompletionHandler);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    callback.onFailure(new HttpException("上传失败"));
                    callback.onFinish();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (!mSuccess && callback != null) {
                    callback.onFailure(new HttpException("网络连接异常"));
                    callback.onFinish();
                }
            }
        });
    }

    /**
     * 上传文件到七牛
     * @param filePath
     * @throws Throwable
     */
    public void uploadFile(String expectKey, String filePath,UpCompletionHandler upCompletionHandler) throws Throwable {
        Map<String, String> params = new HashMap<String, String>();
        params.put("x:foo", "fooval");
        final UploadOptions opt = new UploadOptions(params, null, true, null, null);
        uploadManager.put(filePath, expectKey, mToken, upCompletionHandler, opt);
    }

    /**
     * 生成文件名
     * @return
     */
    public static String generateUploadPath(){
        return System.currentTimeMillis() + ".jpg";
    }
}
