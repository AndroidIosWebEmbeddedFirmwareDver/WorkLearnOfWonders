package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthYongYaoListAPIEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * 处方列表
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthYongYaoListResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    @XmlElementWrapper(name = "PrescriptionRecords")
    @XmlElement(name = "PrescriptionRecord")
    private List<PrescriptionRecord> prescriptionRecord;

    @Data
    @XmlRootElement(name = "PrescriptionRecord")
    public static class PrescriptionRecord {
        private String prescriptionTypes;//处方大类
        private String prescriptionType;//处方类型
        private String prescriptionAmount;//处方金额
        private String prescribeDept;//就诊科室名称
        private String orgName;//医疗机构名称
        private String docName;//医生姓名
        private String patientName;//患者姓名
        private String prescriptionId;//处方id
        @XmlElement(name = "Date")
        private String Date;//开方日期
    }

    public List<HealthYongYaoListAPIEntity> coverToApiEntity() {
        if (null == prescriptionRecord || prescriptionRecord.isEmpty()) {
            return null;
        }
        List<HealthYongYaoListAPIEntity> apiEntities = new ArrayList<>();
        for (PrescriptionRecord record : prescriptionRecord) {
            HealthYongYaoListAPIEntity apiEntity = new HealthYongYaoListAPIEntity();
            apiEntity.setId(record.getPrescriptionId());
            apiEntity.setHospital_name(record.getOrgName());
            apiEntity.setPrint_time(coverDateShow(record.getDate()));
            apiEntity.setDepartment_name(record.getPrescribeDept());
            String amount = StringUtils.isEmpty(record.getPrescriptionAmount()) ? "未知" : record.getPrescriptionAmount().trim();
            apiEntity.setPrescription_amount(amount);
            apiEntities.add(apiEntity);
        }
        return apiEntities;
    }

    private String coverDateShow(String date) {
        return null == date ? "" : date.replace("/", "-");
    }
}

