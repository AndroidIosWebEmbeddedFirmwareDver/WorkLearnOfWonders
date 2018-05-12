package com.wonders.health.venus.open.user.logic;

import android.content.Context;

import com.wonders.health.venus.open.user.util.Constant;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.http.CallProxy;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;
import com.wondersgroup.hs.healthcloud.common.util.UrlUtil;

import java.util.Map;
import java.util.Objects;

/**
 * class: ExtractManager
 * auth: carrey
 * date:16-11-11.
 * desc :提取报告manager类
 */

public class ExtractManager {
    private HttpTools mHttpTools;

    public ExtractManager() {
        mHttpTools = new HttpTools();
    }

    /**
     * 提取报告 搜索医院
     *
     * @param key 搜索关键字
     */
    public CallProxy searchRecommend(String key, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("wd", key);
        return mHttpTools.get(UrlConst.SEARCH_RECOMMEND, signRequest, callback);

    }

    /**
     * 查询检验报告
     *
     * @param day          查询时间区间	 [1:查询今天，3:查询近3天， 7:查询近7天, 31:查询近一个月]
     * @param medicalOrgId 医疗机构代码
     */
    public void getExtractReport(Map more_params, int type, String medicalOrgId, String day, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        if (more_params != null) {
            signRequest.addQueryMapParameter(more_params);
        }
//        signRequest.addQueryStringParameter("uid", "2");//uid
        signRequest.addQueryStringParameter("uid", UserManager.getInstance().getUser().uid);//uid
        signRequest.addQueryStringParameter("medicalOrgId", medicalOrgId);//医疗机构代码
        signRequest.addQueryStringParameter("timeFlag", day);//查询时间区间

        if (type == 0) {//检验报告
            mHttpTools.get(UrlConst.INSPECTION_LIST, signRequest, callback);
        } else {//检查报告
            mHttpTools.get(UrlConst.CHECKREPORT_LIST, signRequest, callback);
        }

    }

    /**
     * 检查报告
     *
     * @param day          查询时间区间	 [1:查询今天，3:查询近3天， 7:查询近7天, 31:查询近一个月]
     * @param medicalOrgId 医疗机构代码
     */
    public void getCheckReport(int day, String medicalOrgId, ResponseCallback callback) {
        SignRequest signRequest = new SignRequest();
        signRequest.addQueryStringParameter("idc", UserManager.getInstance().getUser().idcard);//身份证
        signRequest.addQueryStringParameter("medicalOrgId", medicalOrgId);//医疗机构代码
        signRequest.addQueryStringParameter("day", String.valueOf(day));//查询时间区间

        mHttpTools.get(UrlConst.CHECKREPORT_LIST, signRequest, callback);

    }
}
