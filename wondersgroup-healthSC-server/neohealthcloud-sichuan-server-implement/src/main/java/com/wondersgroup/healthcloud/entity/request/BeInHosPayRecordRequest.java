package com.wondersgroup.healthcloud.entity.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nick on 2016/11/8.
 *
 * @author nick
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class BeInHosPayRecordRequest {

    private String yljgdm; //医疗机构代码
    private String klx; //卡类型 01 身份证
    private String kh; //卡号
    private String zyh; //住院号
}
