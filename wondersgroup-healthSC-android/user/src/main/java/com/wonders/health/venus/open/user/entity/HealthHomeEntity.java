package com.wonders.health.venus.open.user.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：sunzhenyu
 * 创建时间：2016/11/10 20:21
 */
public class HealthHomeEntity {
    /**
     * "functionIcons": [
     {
     "imgUrl": "http://img.wdjky.com/1478761003761.png",// 图标URL
     "subTitle": "aa",// 副标题
     "hoplink": "aa",// 跳转链接
     "mainTitle": "aa"// 主标题
     },
     {
     "imgUrl": "http://img.wdjky.com/1478760999790.png",
     "subTitle": "aa",
     "hoplink": "aa",
     "mainTitle": "aa"
     },
     {
     "imgUrl": "http://img.wdjky.com/1478761007703.png",
     "subTitle": "aaa",
     "hoplink": "aa",
     "mainTitle": "aa"
     }
     ],
     "banners": [
     {
     "imgUrl": "http://img.wdjky.com/1478760826998.png",// 图片URL
     "hoplink": "aa"// 跳转链接
     }
     ]
     */
    public List<Function> functionIcons = new ArrayList<>();
    public List<Banner> banners = new ArrayList<>();
    public static class Function{
        public String imgUrl;
        public String subTitle;
        public String hoplink;
        public String mainTitle;
    }
    public static class Banner{
        public String imgUrl;
        public String hoplink;
    }
}
