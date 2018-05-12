package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nick on 2016/11/9.
 *
 * @author nick
 */
@Data
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnPaidRecordRequest {

    private String yljgdm; //医疗机构代码
    private String klx; //卡类型
    private String kh;  //卡号
    private String jzlsh;//就诊流水号
    private String cfhm;//处方号码：多个处方用逗号分隔
    private String zzmzsj;//最早门诊时间
    private String zwmzsj;//最晚门诊时间

}
