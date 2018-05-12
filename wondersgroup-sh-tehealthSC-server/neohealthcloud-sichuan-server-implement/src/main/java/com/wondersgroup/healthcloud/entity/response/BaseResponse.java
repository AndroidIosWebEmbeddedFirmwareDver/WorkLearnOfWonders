package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by nick on 2016/10/29.
 *
 * @author nick
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseResponse {

    @XmlElement(name = "MessageHeader")
    public ResponseMessageHeader messageHeader;
}
