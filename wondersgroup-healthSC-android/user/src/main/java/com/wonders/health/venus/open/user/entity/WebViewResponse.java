package com.wonders.health.venus.open.user.entity;

import com.wondersgroup.hs.healthcloud.common.entity.Share;

import java.io.Serializable;

/**
 * 类描述：网页文章分享/收藏数据
 * 创建人：tanghaihua
 * 创建时间：2015/7/13 20:45
 */
public class WebViewResponse implements Serializable {
    public boolean can_favorite;
    public boolean is_favorite;
    public Share share;
}
