package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nick on 2016/10/29.
 *
 * @author nick
 * 可预约号源信息查询
 * <p>
 * 查询可预约号源信息，时间跨度最长一周。
 * 如果医院支持时间段展示，则会返回多条排班ID相同，
 * 号源ID不同的记录，多条记录标识时间段，并且每个时间段支持多个序号。
 */
@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderNumInfoRequest extends BaseRequest {

    @XmlElement(name = "OrderInfo")
    public OrderInfoR orderInfoR;

}
