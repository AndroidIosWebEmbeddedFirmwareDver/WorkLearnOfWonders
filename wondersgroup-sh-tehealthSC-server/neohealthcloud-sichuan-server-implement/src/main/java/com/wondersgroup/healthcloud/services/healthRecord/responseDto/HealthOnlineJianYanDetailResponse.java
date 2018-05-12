package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author ys
 * 提取报告 - 门诊检验报告详情
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthOnlineJianYanDetailResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    @XmlElementWrapper(name = "labDetails")
    @XmlElement(name = "labDetail")
    private List<LabDetail> labDetails;

    @Data
    @XmlRootElement(name = "labDetail")
    @XmlAccessorType(XmlAccessType.FIELD)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class LabDetail {
        private String testNo;
        private String name;//检验标本: "血浆(肝素锂抗凝)"
        private String sex;
        private String age;
        private String dept;//科室
        private String area;//病区
        private String bedNo;//病床
        private String specimen;//标本名称 eq:血清
        private String inspectionDate;//送检日期 eq:2015/11/16
        private String sampleTime;//采样时 eq:2015/11/16
        private String applyDoc;//申请医生
        private String applyDate;//申请日期
        private String printDate;//打印日期
        private String audit;//审核医生

        public String getApplyDate() {
            return StringUtils.length(applyDate) >= 10 ? applyDate.substring(0, 10) : "";
        }

        public String getPrintDate() {
            printDate = StringUtils.isEmpty(printDate) ? inspectionDate : printDate;
            return StringUtils.length(printDate) >= 10 ? printDate.substring(0, 10) : "";
        }

        public String getInspectionDate() {
            return StringUtils.length(inspectionDate) >= 10 ? inspectionDate.substring(0, 10) : "";
        }

        @XmlElementWrapper(name = "inspections")
        @XmlElement(name = "inspection")
        private List<Inspection> inspection;

        @Data
        @XmlRootElement(name = "inspection")
        public static class Inspection {
            private String inspectionName;
            private String inspectionCode;
            private String result;
            private String unit;
            private String value;
            private String tips;
        }
    }
}
