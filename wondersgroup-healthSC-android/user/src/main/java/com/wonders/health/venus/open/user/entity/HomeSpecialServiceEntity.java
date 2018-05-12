package com.wonders.health.venus.open.user.entity;

import java.util.List;

/**
 * 类：${File}
 * 创建者:carrey on 16-9-1.
 * 描述 ：
 */
public class HomeSpecialServiceEntity {


    public List<SpecialService> specialService;

//    public MeasurePointEntity measuringPoint;

    public static class SpecialService {

        public String imgUrl;// 图片地址
        public String mainTitle;// 主标题
        public String subTitle;// 副标题
        public String hoplink;// 跳转链接
        public int loginOrRealName;//0:需登录,1:需实名制,2:正常
    }


}
