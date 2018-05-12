package com.wondersgroup.healthSC.services.model.response;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by nick on 2016/11/7.
 * @author nick
 */
@Data
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class BeInHospitalResponse {

    public String resultcode;

    public String resultmessage;

    public String yljgdm;

    @XmlElementWrapper(name = "list")
    @XmlElement(name = "item")
    public List<Item> item;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item{
        private String zyh; //住院号
        private String rysj; //入院时间
        private String cysj; //出院时间
        private String ksbm; //科室编码
        private String ksmc; //科室名称
        private String bq; //病区
        private String cw; //床位
        private String ysbm; //医生编码
        private String ysmc; //医生名称
        private String hsbm; //护士编码
        private String hsmc; //护士名称
        private String yjjye; //预交金余额
        private String cybz; //出院标志
    }
}
