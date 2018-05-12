package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by longshasha on 16/5/22.
 */
@Data
@XmlRootElement(name = "MessageHeader")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestMessageHeader {

    private String frontproviderId;

    private String inputCharset;

    private String signType;

    private String sign;

    public RequestMessageHeader() {
    }

    public RequestMessageHeader(String frontproviderId, String inputCharset,
                                String signType, String sign) {
        this.frontproviderId = frontproviderId;
        this.inputCharset = inputCharset;
        this.signType = signType;
        this.sign = sign;
    }
}
