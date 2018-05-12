package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthJiuZhenListAPIEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * 门诊就诊记录
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthJiuZhenListResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    @XmlElementWrapper(name = "MedicalInfos")
    @XmlElement(name = "MedicalInfo")
    private List<MedicalInfo> medicalInfos;

    @Data
    @XmlRootElement(name = "MedicalInfo")
    public static class MedicalInfo {
        private String treatmentDate;//就诊日期20150510
        private String treatmentHospital = "未知";//就诊医院
        private String treatmentDepartment = "未知";//就诊科室
        private String treatmentType;//就诊类型
        private String diagnosticDesc;//诊断描述
        private String chiefComplaint;//主诉
        private String diagnosticInstructions;//诊断说明
        private String diagnosisName;//诊断名称
        private String doctorName;//医生姓名
        private String medicalOrgId;//医疗机构代码
        private String serialNum;//就诊流水号
        private String icd10;//诊断ICD10
    }

    public List<HealthJiuZhenListAPIEntity> coverToJiuzhenListEntity() {
        if (medicalInfos == null || medicalInfos.isEmpty()) {
            return null;
        }
        List<HealthJiuZhenListAPIEntity> list = new ArrayList<>();
        for (MedicalInfo medicalInfo : medicalInfos) {
            HealthJiuZhenListAPIEntity apiEntity = new HealthJiuZhenListAPIEntity();
            String date = medicalInfo.getTreatmentDate();
            if (date.length() >= 8 && StringUtils.isNumeric(date)) {
                date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
            }
            apiEntity.setDate(date);
            apiEntity.setDiagnose_result(medicalInfo.getDiagnosisName());
            apiEntity.setHospital_name(medicalInfo.getTreatmentHospital());
            apiEntity.setOffice_name(medicalInfo.getTreatmentDepartment());
            apiEntity.setOffice_type(medicalInfo.getTreatmentType());
            list.add(apiEntity);
        }
        return list;
    }

}
