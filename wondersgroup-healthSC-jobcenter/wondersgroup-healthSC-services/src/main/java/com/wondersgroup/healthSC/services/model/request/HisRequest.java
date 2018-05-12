package com.wondersgroup.healthSC.services.model.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nick on 2016/11/7.
 */
@Data
@XmlRootElement(name = "request")
public class HisRequest {


    private String yljgdm;//医疗机构代码

    private String klx;//卡类型  身份证 01 居民户口簿 02 护照 03 军官证 04 驾驶证 05

    private String kh;//卡号

    private String ksrq;//开始日期

    private String jsrq;//结束日期
}
