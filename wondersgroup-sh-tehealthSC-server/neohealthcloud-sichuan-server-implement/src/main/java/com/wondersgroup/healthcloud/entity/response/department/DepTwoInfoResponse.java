package com.wondersgroup.healthcloud.entity.response.department;

import com.wondersgroup.healthcloud.entity.response.BaseResponse;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by dukuanxin on 2016/11/5.
 */
@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepTwoInfoResponse extends BaseResponse {

    @XmlElementWrapper(name = "List")
    @XmlElement(name = "Result")
    public List<DepTwoInfo> depTwoInfos;

}
