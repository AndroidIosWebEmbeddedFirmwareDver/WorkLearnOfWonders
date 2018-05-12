package com.wonders.health.venus.open.doctor.module.consultation;

import com.hyphenate.EMCallBack;
import com.wonders.health.venus.open.doctor.logic.SignRequest;
import com.wonders.health.venus.open.doctor.logic.UserManager;
import com.wonders.health.venus.open.doctor.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/4/6 10:54
 */
public class ChatManager {
    private static ChatManager sInstance;
    private HttpTools mHttpTools;

    private ChatManager() {
        mHttpTools = new HttpTools();
    }
    public static ChatManager getInstance() {
        if (sInstance == null) {
            synchronized (ChatManager.class) {
                if (sInstance == null) {
                    sInstance = new ChatManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 环信登录
     *
     * @param talkId  环信ID
     * @param talkPwd 环信密码
     */
    public void login(final String talkId, String talkPwd, final EMCallBack callBack) {
        ChatHelper.getInstance().login(talkId, talkPwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                UserManager.getInstance().setEMLoginState(true);
                if (callBack != null) {
                    callBack.onSuccess();
                }
            }

            @Override
            public void onError(int i, String s) {
                UserManager.getInstance().setEMLoginState(false);
                if (callBack != null) {
                    callBack.onError(i,s);
                }
            }

            @Override
            public void onProgress(int i, String s) {
                if (callBack != null) {
                    callBack.onProgress(i,s);
                }
            }
        });
    }

    /**
     * 退出环信登录
     */
    public void logout() {
        try {
            ChatHelper.getInstance().logout(false, null);
        } catch (Exception e) {
        }
    }

    /**
     * 根据医生talkid获取患者信息(多个talkid以,分割)
     * @param talkIds
     * @param callback
     */
    public void getPatientInfo(String talkIds, ResponseCallback callback) {
//        SignRequest request=new SignRequest();
//        request.addQueryStringParameter("talkid",talkIds);
//        mHttpTools.get(UrlConst.GET_PATIENTINFO_BY_TALKID, request, callback);
    }

}
