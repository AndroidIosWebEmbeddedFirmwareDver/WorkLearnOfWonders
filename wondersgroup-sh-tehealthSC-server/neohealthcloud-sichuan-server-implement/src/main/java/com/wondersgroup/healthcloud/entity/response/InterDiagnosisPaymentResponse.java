package com.wondersgroup.healthcloud.entity.response;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by nick on 2016/11/10.
 */
@Data
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class InterDiagnosisPaymentResponse {

    private String resultcode;
    private String resultmessage;
    private String yljgdm;

    @XmlElementWrapper(name = "list")
    @XmlElement(name = "item")
    private List<Item> items;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {
        private String cfhm;
        private String sjbh;
    }
}
