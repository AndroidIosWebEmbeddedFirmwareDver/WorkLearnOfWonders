package com.wonders.health.venus.open.user.logic;

import com.wonders.health.venus.open.user.dao.HealthDao;
import com.wonders.health.venus.open.user.entity.ArticleTabListResponse;
import com.wonders.health.venus.open.user.entity.HealthHomeEntity;
import com.wonders.health.venus.open.user.util.UrlConst;
import com.wondersgroup.hs.healthcloud.common.logic.ResponseCallback;
import com.wondersgroup.hs.healthcloud.common.util.HttpTools;

import java.util.Map;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/11/10 11:10
 */
public class HealthManager {
    private HttpTools mHttpTools;
    private HealthDao mHealthDao;

    public HealthManager(){
        mHttpTools = new HttpTools();
        mHealthDao = new HealthDao();
    }
    /**
     * 健康页首页
     * @param callback
     */
    public void getHealthHome(ResponseCallback callback){
        SignRequest request = new SignRequest();
        mHttpTools.get(UrlConst.HEALTH_HOME, request, callback);
    }


    /**
     * 资讯标题
     * @param callback
     */
    public void getZixunTitle(ResponseCallback callback){
        SignRequest request = new SignRequest();
        mHttpTools.get(UrlConst.ZIXUN_CATEGORY, request, callback);
    }

    /**
     * 资讯列表详情
     * @param cat_id
     * @param moreParams
     * @param callback
     */
    public void getZixunList( String cat_id, Map<String, String> moreParams, ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("cat_id",cat_id);
        if (moreParams != null) {
            request.addQueryMapParameter(moreParams);
        }
        mHttpTools.get(UrlConst.ZIXUN_LIST, request, callback);
    }

    /**
     * 资讯详情
     */
    public void getZixunDetail(String id,ResponseCallback callback){
        SignRequest request = new SignRequest();
        request.addQueryStringParameter("id",id);
        mHttpTools.get(UrlConst.ZIXUN_DETAIL, request, callback);
    }

    /**
     * 获取健康页缓存banner，function
     * @return
     */
    public HealthHomeEntity getHealthHomeEntityCache(){
        return mHealthDao.getHealthHomeEntity();
    }
    /**
     * 缓存健康页banner，function
     * @param healthHomeEntity
     */
    public void saveHealthHomeEntityCache(HealthHomeEntity healthHomeEntity){
        mHealthDao.saveHealthHomeEntity(healthHomeEntity);
    }
    /**
     * 获取健康页缓存资讯标题及列表数据
     * @return
     */
    public ArticleTabListResponse getArticleTabListResponseCache(){
        return mHealthDao.getArticleTabListResponse();
    }
    /**
     * 缓存健康页资讯标题及列表数据
     * @param tabListResponse
     */
    public void saveArticleTabs(ArticleTabListResponse tabListResponse){
        mHealthDao.saveArticleTabs(tabListResponse);
    }

}
