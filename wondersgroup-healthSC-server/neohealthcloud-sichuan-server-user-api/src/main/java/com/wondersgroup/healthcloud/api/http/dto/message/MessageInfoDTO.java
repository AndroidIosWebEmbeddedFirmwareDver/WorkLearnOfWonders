package com.wondersgroup.healthcloud.api.http.dto.message;

import lombok.Data;

@Data
public class MessageInfoDTO {
    private int type;    //1 系统消息
    private String name;    //2 支付消息
    private int count;   //未读数量
    private String content; //最新消息内容
    private String date;

    public MessageInfoDTO() {

    }

    public MessageInfoDTO(int type, String name, int count, String content, String date) {
        this.type = type;
        this.name = name;
        this.count = count;
        this.content = content;
        this.date = date;
    }

}
