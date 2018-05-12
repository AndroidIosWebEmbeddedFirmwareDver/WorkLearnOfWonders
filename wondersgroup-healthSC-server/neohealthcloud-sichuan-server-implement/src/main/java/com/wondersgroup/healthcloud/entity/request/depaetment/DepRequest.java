package com.wondersgroup.healthcloud.entity.request.depaetment;

import com.wondersgroup.healthcloud.entity.request.BaseRequest;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dukuanxin on 2016/11/4.
 */
@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepRequest extends BaseRequest {

    @XmlElement(name = "HosInfo")
    public HosInfo hosInfo;

}
