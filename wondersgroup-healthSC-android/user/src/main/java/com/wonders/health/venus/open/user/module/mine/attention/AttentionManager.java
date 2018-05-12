package com.wonders.health.venus.open.user.module.mine.attention;

import com.wonders.health.venus.open.user.logic.SignRequest;
import com.wonders.health.venus.open.user.logic.UserManager;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.HashMap;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/11/11 18:17
 */
public class AttentionManager {
    private HttpTools mHttpTools;
    public AttentionManager(){
        mHttpTools = new HttpTools();
    }

    /**
     * 关注医生列表
     * @param moreParams
     * @param callback
     */
    public void getAttentionDoctorList(HashMap<String,String> moreParams, ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        mHttpTools.get(UrlConst.ATTENTION_DOCTOR, request, callback);
    }

    /**
     * 关注医院列表
     * @param moreParams
     * @param callback
     */
    public void getAttentionHospitalList(HashMap<String,String> moreParams, ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        mHttpTools.get(UrlConst.ATTENTION_HOSPITAL, request, callback);
    }

    /**
     * 关注（取消）医院
     *
     * @param hospitalId
     * @param callback
     */
    public void postHospitalFavorite(String hospitalId,ResponseCallback callback) {
        SignRequest request = new SignRequest();
        request.addBodyParameter("uid", UserManager.getInstance().getUser().uid);
        request.addBodyParameter("hospitalId", hospitalId);
        mHttpTools.post(UrlConst.FAVORITE_HOSPITAL, request, callback);
    }


}
