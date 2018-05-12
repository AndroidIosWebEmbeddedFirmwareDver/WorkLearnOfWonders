package com.wondersgroup.healthSC.services.model.response;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by nick on 2016/11/9.
 * @author nick
 */
@Data
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnPaidRecordResponse {

    private String resultcode;

    private String resultmessage;

    private String yljgdm;

    private String cfhm;//处方号码 逗号分隔

    private String zfje; //自费金额

    private String ybje; //医保金额

    private String hjje; //总金额

    @XmlElementWrapper(name = "list")
    @XmlElement(name = "item")
    private List<Item> item;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Item{
        private String fldm;//费用分类代码
        private String flmc;//费用名称
        private String flje;//费用金额
    }

}
