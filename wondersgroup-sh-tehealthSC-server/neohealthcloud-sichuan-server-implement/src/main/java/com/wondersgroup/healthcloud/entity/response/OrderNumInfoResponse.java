package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by nick on 2016/10/29.
 *
 * @author nick
 */
@Data
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderNumInfoResponse extends BaseResponse {

    /**
     * 号源信息数据
     */
    @XmlElementWrapper(name = "List")
    @XmlElement(name = "Result")
    public List<ScheduleNumInfo> scheduleNumInfos;

}
