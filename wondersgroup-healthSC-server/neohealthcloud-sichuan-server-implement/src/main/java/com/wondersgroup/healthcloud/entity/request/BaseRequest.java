package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by longshasha on 16/5/23.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseRequest {
    @XmlElement(name = "MessageHeader")
    public RequestMessageHeader requestMessageHeader;
}
