package com.wondersgroup.hs.healthcloud.common.entity.event;


import com.wondersgroup.hs.healthcloud.common.entity.BaseResponse;

/**
 * token过期的事件
 * Created by Bob on 2015/7/4.
 */
public class TokenExpiredEvent {

    public BaseResponse response;

    public TokenExpiredEvent(BaseResponse data) {
        this.response = data;
    }

}
