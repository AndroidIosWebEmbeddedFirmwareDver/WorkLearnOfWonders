package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthJianYanDetailAPIEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * 门诊检验报告列表
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthJianYanDetailResponse implements Serializable {

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
        private String specimen;//标本名称 eq:血清
        private String inspectionDate;//送检日期 eq:2015/11/16
        private String sampleTime;//采样时 eq:2015/11/16
        private String applyDoc;//申请医生
        private String applyDate;//申请日期
        private String printDate;//打印日期
        private String audit;//审核医生

        @XmlElementWrapper(name = "inspections")
        @XmlElement(name = "inspection")
        private List<Inspection> inspections;

        @Data
        @XmlRootElement(name = "inspection")
        public static class Inspection {
            private String inspectionName;
            private String inspectionCode;
            private String result;
            private String unit;
            private String value;
        }
    }

    public List<HealthJianYanDetailAPIEntity> coverToApiEntity() {
        if (null == labDetails || labDetails.isEmpty()) {
            return null;
        }
        LabDetail labDetailInfo = labDetails.get(0);
        if (labDetailInfo.getInspections() == null || labDetailInfo.getInspections().isEmpty()) {
            return null;
        }
        List<HealthJianYanDetailAPIEntity> apiEntities = new ArrayList<>();
        for (LabDetail.Inspection insTmp : labDetailInfo.getInspections()) {
            HealthJianYanDetailAPIEntity apiEntity = new HealthJianYanDetailAPIEntity();
            apiEntity.setItem_name(insTmp.getInspectionName());
            apiEntity.setItem_value(insTmp.getResult());
            String unit = insTmp.getUnit() == null || insTmp.getUnit().equals("-") ? "" : insTmp.getUnit();
            apiEntity.setRefer_value(insTmp.getValue() + unit);
            apiEntities.add(apiEntity);
        }
        return apiEntities;
    }

}
