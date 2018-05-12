package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by longshasha on 16/5/22.
 */
@XmlRootElement(name = "MessageHeader")
@Data
public class ResponseMessageHeader {

    private String code;

    private String desc;

}
