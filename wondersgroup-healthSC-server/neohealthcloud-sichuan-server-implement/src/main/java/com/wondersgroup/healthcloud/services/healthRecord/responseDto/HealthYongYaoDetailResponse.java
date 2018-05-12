package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.services.healthRecord.contant.HealthRecordConstant;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthYongYaoMedicineInfoAPIEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * 住院就诊记录
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthYongYaoDetailResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    @XmlElementWrapper(name = "Prescriptions")
    @XmlElement(name = "Prescription")
    private List<Prescription> prescriptions;

    @Data
    public static class Prescription {
        private String drugName;//药品名称
        private String drugUse;//药品用法
        private String grantDose;//发药量
        private String frequencyUse;//用药频次
        private String dosage;//用药量
        private String drugSpecification;//药品规格
    }

    public List<HealthYongYaoMedicineInfoAPIEntity> coverToMedicineListApiEntity() {
        if (null == prescriptions || prescriptions.isEmpty()) {
            return null;
        }
        List<HealthYongYaoMedicineInfoAPIEntity> apiEntities = new ArrayList<>();
        for (Prescription prescription : prescriptions) {

            HealthYongYaoMedicineInfoAPIEntity apiEntity = new HealthYongYaoMedicineInfoAPIEntity();
            apiEntity.setMedicine_name(prescription.getDrugName());
            apiEntity.setMedicine_num(prescription.getGrantDose());
            apiEntity.setDay_use_num(HealthRecordConstant.coverDayUsedNum(prescription.getFrequencyUse()));
            apiEntity.setPer_use_num(prescription.getDosage());
            apiEntity.setSpecification(prescription.getDrugSpecification());
            String user_type = HealthRecordConstant.coverMedicineUseType(prescription.getDrugUse());
            apiEntity.setUse_type(StringUtils.isEmpty(user_type) ? "-" : user_type);
            apiEntities.add(apiEntity);
        }
        return apiEntities;
    }

}
