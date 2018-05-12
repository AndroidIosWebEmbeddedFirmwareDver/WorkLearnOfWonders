package com.wondersgroup.healthcloud.common.http.exceptions;

import java.util.Random;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p/>
 * Created by zhangzhixiu on 15/11/16.
 */
public class ErrorMessageSelector {

    private static final String[] messagesPool = new String[]{
            "最宽的沟渠...是健康云在用光纤...而侬却在拨号...",
            "世界上最遥远的距离就是没网络...",
            "网络信号也太差了，又拖国家平均网速的后腿了...",
            "阿拉网络好像卡住了，服务器的小伙伴正在玩命修理...",
            "阿拉网络没了...得刷新再试试",
            "请求的人太多，得再去挤挤",
            "您点击的太快了，服务器跟不上您的手速",
            "人生最无奈的事就是看着人家上着网而我却在连接中...",
            "你的网速，吓死宝宝了",
            "刷一刷，让你重回一十八",
    };

    private static final Random r = new Random();
    private static final int randomMax = messagesPool.length;

    public static String getOne() {
        return messagesPool[r.nextInt(randomMax)];
    }
}
