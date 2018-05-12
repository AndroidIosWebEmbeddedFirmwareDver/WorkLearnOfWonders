package com.wonders.health.venus.open.user.entity.event;

/**
 * Created by zhangjingyang on 16/1/10.
 * 显示消息红点
 */
public class MessageEvent {
    public boolean mMsgListDestroy=false;//当等于true的时候，只有在消息列表返回的时候才处理。

    public MessageEvent(boolean mMsgListDestroy) {
        this.mMsgListDestroy = mMsgListDestroy;
    }
}
