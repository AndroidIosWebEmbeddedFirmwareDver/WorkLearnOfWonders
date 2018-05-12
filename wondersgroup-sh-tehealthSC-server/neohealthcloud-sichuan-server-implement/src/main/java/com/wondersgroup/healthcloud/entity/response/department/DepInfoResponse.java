package com.wondersgroup.healthcloud.entity.response.department;

import com.wondersgroup.healthcloud.entity.response.BaseResponse;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by dukuanxin on 2016/11/4.
 */
@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepInfoResponse extends BaseResponse {

    /**
     * 科室信息数据
     */
    @XmlElementWrapper(name = "List")
    @XmlElement(name = "Result")
    public List<DepInfo> depInfos;
}
