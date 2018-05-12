package com.wondersgroup.healthcloud.common.appenum;

/**
 * Created by zhaozhenxing on 2016/6/12.
 */
public enum ImageTextEnum {

    HOME_BANNER("首页Banner", 0),
    G_HOME_FUNCTION("首页功能图标", 1),// 组图
    LOADING_IMAGE("启动页广告", 2),
    NAVIGATION_BAR("导航按钮", 3),
    G_SERVICE_BTN("服务功能列表", 4),// 组图
    HOME_ADVERTISEMENT("首页广告位", 5),
    HOME_FLOAT_AD("首页浮动广告", 6),
    AD_CIRCLE("医学圈", 7),
    AD_DOCTOR_DETAIL("医生详情", 8),
    AD_QA_DETAIL("问答详情", 9),
    AD_HOME("个人诊所", 10),
    G_HOME_SPECIAL_SERVICE("3.0首页特色服务", 11), // 组图

    HEALTH_BANNER("健康Banner", 12),// 组图
    G_HEALTH_FUNCTION("健康功能图标", 13);// 组图


    private String name;
    private Integer type;

    private ImageTextEnum(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public Integer getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static ImageTextEnum fromValue(Integer type) {
        if (type != null) {
            for (ImageTextEnum imageTextEnum : ImageTextEnum.values()) {
                if (imageTextEnum.type.equals(type)) {
                    return imageTextEnum;
                }
            }
        }
        return null;
    }
}
