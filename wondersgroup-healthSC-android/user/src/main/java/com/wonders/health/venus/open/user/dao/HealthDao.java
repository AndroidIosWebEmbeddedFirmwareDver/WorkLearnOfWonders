package com.wonders.health.venus.open.user.dao;

import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.entity.ArticleTabListResponse;
import com.wonders.health.venus.open.user.entity.HealthHomeEntity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/11/11 10:49
 */
public class HealthDao {
    /**
     * 获取健康页缓存banner，function
     * @return
     */
    public HealthHomeEntity getHealthHomeEntity(){
        return PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_HEALTH_DATA, HealthHomeEntity.class);
    }

    /**
     * 缓存健康页banner，function
     * @param healthHomeEntity
     */
    public void saveHealthHomeEntity(HealthHomeEntity healthHomeEntity){
        PrefUtil.putJsonObject(BaseApp.getApp(), Constant.KEY_HEALTH_DATA, healthHomeEntity);
    }

    /**
     * 获取健康页缓存资讯标题及列表数据
     * @return
     */
    public ArticleTabListResponse getArticleTabListResponse(){
        return PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_HEALTH_ARTICLE_DATA, ArticleTabListResponse.class);
    }

    /**
     * 缓存健康页资讯标题及列表数据
     * @param tabListResponse
     */
    public void saveArticleTabs(ArticleTabListResponse tabListResponse){
        PrefUtil.putJsonArray(BaseApp.getApp(), Constant.KEY_HEALTH_ARTICLE_DATA, tabListResponse);
    }
}
