package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthZhuYuanListAPIEntity;
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
public class HealthZhuYuanListResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    @XmlElementWrapper(name = "hosMedicalInfos")
    @XmlElement(name = "hosMedicalInfo")
    private List<HosMedicalInfo> hosMedicalInfos;

    @Data
    @XmlRootElement(name = "hosMedicalInfo")
    public static class HosMedicalInfo {
        private String admissionDate;//入院日期 eq:2014/04/01
        private String hospitalName;//医院名称
        private String admissionDepartment;//入院科室 eq:消化内科
        private String diagosisName;//诊断名称
        private String dischargeDate;//出院日期
        private String medicalOrgId;//医疗机构代码
        private String serialNum;//就诊流水号
    }

    public List<HealthZhuYuanListAPIEntity> coverToZhuYuanListEntity() {
        if (hosMedicalInfos == null || hosMedicalInfos.isEmpty()) {
            return null;
        }
        List<HealthZhuYuanListAPIEntity> list = new ArrayList<>();
        for (HosMedicalInfo hosMedicalInfo : hosMedicalInfos) {
            HealthZhuYuanListAPIEntity apiEntity = new HealthZhuYuanListAPIEntity();
            String id = hosMedicalInfo.getMedicalOrgId() + "-|-" + hosMedicalInfo.getSerialNum();
            apiEntity.setId(id);
            apiEntity.setHospital_name(hosMedicalInfo.getHospitalName());
            apiEntity.setOffice_name(hosMedicalInfo.getAdmissionDepartment());
            String in_time = StringUtils.isEmpty(hosMedicalInfo.getAdmissionDate()) ? "未知" : hosMedicalInfo.getAdmissionDate().replace("/", "-");
            String out_time = StringUtils.isEmpty(hosMedicalInfo.getDischargeDate()) ? "未知" : hosMedicalInfo.getDischargeDate().replace("/", "-");
            apiEntity.setHospitalized_time(in_time + "～" + out_time);
            list.add(apiEntity);
        }
        return list;
    }
}
