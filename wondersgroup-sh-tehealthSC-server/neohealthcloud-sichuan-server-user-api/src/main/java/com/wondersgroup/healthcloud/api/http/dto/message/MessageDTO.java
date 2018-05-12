package com.wondersgroup.healthcloud.api.http.dto.message;

import java.text.SimpleDateFormat;

import lombok.Data;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wondersgroup.healthcloud.jpa.entity.message.Message;
import com.wondersgroup.healthcloud.jpa.entity.message.MessageUser;

@Data
@JsonInclude(value = Include.NON_NULL)
public class MessageDTO {
    private String id;
    private Integer type;
    private String title;
    private Boolean isShow;
    private String content;
    private String imgUrl;
    private String url;
    private String startTime;
    private String endTime;
    private String createBy;
    private String createDate;
    private String updateDate;

    private String registerId;
    private String messageId;
    private int state;//0 未读 1 已读

    private static final Logger log = Logger.getLogger(MessageDTO.class);

    public MessageDTO(Message message) {
        if (message != null) {
            this.content = message.getContent();
            this.id = message.getId();
            this.imgUrl = message.getImgUrl();
            this.title = message.getTitle();
            this.type = message.getType();
            this.url = message.getUrl();
            if (message.getCreateDate() != null) {
                this.createDate = new SimpleDateFormat("yyyy-M-dd").format(message.getCreateDate());
            }
        }
    }

    public MessageDTO() {

    }

}
