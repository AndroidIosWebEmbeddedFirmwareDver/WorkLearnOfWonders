package com.wonders.health.venus.open.user.entity;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/11/3 9:22
 */
public class AppConfig {
    public Ads ads;
    public Version version;
    public Common common;
    public AppUpdate appUpdate;
    public Share share;

    public static class Common {
        public String publicKey;

        public String version_desc;//关于界面---描述
        public String consumerHotline; // 客服电话
        public String version_department;//关于界面---部门
        public String helpCenter;//帮助中心
        public String record_url;//档案url

        public String userAgreement;//注册协议
        public String ipa;//知识产权
        public String qrCode;//二维码分享链接
        public String callCentUrl;//在线客服是否显示

        public String huiDaoChannelid;
        public String huiDaoAppkey;
        public String huiDaoApiid;
        public String huiDaoSid;

        public String realNameRule;//实名认证责任条款
        public String appointmentRule; //预约挂号规则

    }

    public static class AppUpdate {
        public String updateMsg;//升级提示信息
        public String lastVersion;//升级版本号
        public String androidUrl;//升级应用下载地址
        public boolean forceUpdate;//是否强制升级
        public boolean hasUpdate;//是否版本升级
        public String packageSize;
    }

    public static class Ads {
        public int id;
        public String imgUrl; // 广告图片地址
        public String hoplink; // 跳转地址
        public int duration; // 展示时间
        public boolean isSkip; // 是否跳转
        public boolean isShow; // 是否展示
    }

    public static class Version {
        public int id;
        public String areaversion;
        public String sportversion;
        public String foodversion;
    }

    public static class Share {
        public int id;
        public String title;
        public String desc;
        public String androidUrl;
        public String thumb;
    }
}
