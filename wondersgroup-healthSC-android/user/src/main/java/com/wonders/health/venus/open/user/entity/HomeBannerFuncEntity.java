package com.wonders.health.venus.open.user.entity;

import java.util.List;

/**
 * 类：${File}
 * 创建者:carrey on 16-8-17.
 * 描述 ：
 */
public class HomeBannerFuncEntity {

    public List<FunctionEntity> functionIcons;
    public List<BannerEntity> banners;
    public TelephoneAd telephoneAd;//返回null说明广告关闭，前端不展示
    public SideAd sideAd;
    public List<ArticleItem> news;

    /**
     * 侧边广告位置标志
     */
    public static class SideAd {
        public int position;//0:左侧，1：右侧
        public String imgUrl;
        public String hoplink;
    }

    public static class TelephoneAd {
        public String imgUrl;
        public String hoplink;
        public int isOpen;//0,不显示, 1:显示
        public String status;//0，默认，1：在线弹窗
    }

    public static class FunctionEntity {
        public String imgUrl;
        public String subTitle;
        public String hoplink;
        public String mainTitle;
        public String loginOrRealName;//0:需登录,1:需实名制,2:正常
    }

    public static class BannerEntity {
        public String imgUrl;
        public String hoplink;
    }
}
