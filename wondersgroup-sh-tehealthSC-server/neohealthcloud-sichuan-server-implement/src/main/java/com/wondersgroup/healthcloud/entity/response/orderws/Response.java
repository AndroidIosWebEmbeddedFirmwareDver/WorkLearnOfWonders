package com.wondersgroup.healthcloud.entity.response.orderws;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * Created by zhaozhenxing on 2016/11/7.
 */
@Data
@XmlRootElement(name = "Response")
public class Response {

    @XmlElement(name = "MessageInfo")
    private MessageInfo messageInfo;

    @Data
    public static class MessageInfo {
        private String code;
        private String desc;
    }
}
