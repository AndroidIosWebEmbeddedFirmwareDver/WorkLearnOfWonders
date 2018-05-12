package com.wonders.health.venus.open.doctor.logic;

import android.text.TextUtils;

import com.wonders.health.venus.open.doctor.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.Callback;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;

import java.util.Map;

/**
 * Created by wang on 2017/6/7.
 */

public class SignManager {

    private HttpTools mHttpTools;

    public SignManager(){
        mHttpTools=new HttpTools();
    }

    /**
     * 签约患者列表
     * @param moreParams
     * @param patientTag
     * @param callback
     */
    public void queryPatientList(Map<String, String> moreParams, String patientTag, ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("orgCode",UserManager.getInstance().getUser().orgCode);
        request.addQueryStringParameter("doctorIdCard",UserManager.getInstance().getUser().idCardNo);

        if(!TextUtils.isEmpty(patientTag)){
            request.addQueryStringParameter("patientTag",patientTag);
        }

        request.addQueryMapParameter(moreParams);
        request.setPath(UrlConst.GET_SIGN_PATIENT_LIST);
        mHttpTools.get(request, callback);
    }

}
