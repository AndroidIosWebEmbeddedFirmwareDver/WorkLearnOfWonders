package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by nick on 2016/11/9.
 *
 * @author nick
 */
@Data
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class UnPaidRecordResponse {

    private String resultcode;

    private String resultmessage;

    private String yljgdm;

    private String yymc;

    @XmlElementWrapper(name = "list")
    @XmlElement(name = "item")
    private List<Item> item;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {

        private String kfsj;//开放时间(yyyy-MM-dd HH:mm:dd)
        private String yjks;//一级科室名称
        private String fldm;//费用分类代码
        private String flmc;//费用名称
        private String flje;//费用金额
        private String cfhm;//处方单号
        private String jzlsh;//就诊流水号
        private String sjbh;//收据编号
    }

}
