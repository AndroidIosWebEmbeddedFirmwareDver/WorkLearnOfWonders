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
public class DepTwoRequest extends BaseRequest {

    @XmlElement(name = "DeptInfo")
    public DeptInfoR deptInfo;

    @Data
    @XmlRootElement(name = "DeptInfo")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DeptInfoR {
        //医院代码
        public String hosOrgCode;
        //一级科室代码
        public String topHosDeptCode;
    }

}
