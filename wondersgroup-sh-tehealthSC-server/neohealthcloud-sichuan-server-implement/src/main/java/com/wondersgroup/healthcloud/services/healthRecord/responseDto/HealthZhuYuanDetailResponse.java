package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthZhuYuanDetailAPIEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author ys
 * 住院就诊小结
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthZhuYuanDetailResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    private HosDischargeAbstractVo hosDischargeAbstractVo;

    @Data
    @XmlRootElement(name = "hosDischargeAbstractVo")
    public static class HosDischargeAbstractVo {
        private Object id;
        private String department;//科室代码
        private String departmentName;//科室名称
        private String bedNo;//床号
        private String name;//患者姓名
        private String sex;//性别
        private String age;
        private String admissionTime;//入院时间
        private String dischargeTime;//出院时间
        private String hospitalDay;//住院天数
        private String chiefDoctor;//主治医生姓名
        private String hospitalDoctor;//住院医生姓名
        private String clinicDiagnosis;//门诊诊断
        private String admissionDiagnosis = "无";//入院诊断
        private String admissionSign = "无";//入院体征
        private String dischargeDiagnosis = "无";//出院诊断
        private String inspectConsultation = "无";//检查会诊
        private String treatmentProcess;//诊疗过程

        private String complication = "无";//合并症
        private String dischargeStatus = "无";//出院情况
        private String dischargeOrder = "无";//出院医嘱
        private String treatmentOutcome = "无";//治疗结果
    }

    public HealthZhuYuanDetailAPIEntity coverToZhuYuanDetailEntity() {
        if (hosDischargeAbstractVo == null) {
            return null;
        }
        HealthZhuYuanDetailAPIEntity apiEntity = new HealthZhuYuanDetailAPIEntity();
        apiEntity.setAge(hosDischargeAbstractVo.getAge());
        apiEntity.setName(hosDischargeAbstractVo.getName());
        apiEntity.setDepartmentName(hosDischargeAbstractVo.getDepartmentName());
        String in_time = coverDateShow(hosDischargeAbstractVo.getAdmissionTime());
        String out_time = coverDateShow(hosDischargeAbstractVo.getDischargeTime());
        apiEntity.setHospitalized_time(in_time + "～" + out_time);
        apiEntity.setAdmissionDiagnosis(hosDischargeAbstractVo.getAdmissionDiagnosis());
        apiEntity.setDischargeDiagnosis(hosDischargeAbstractVo.getDischargeDiagnosis());
        apiEntity.setDischargeOrder(hosDischargeAbstractVo.getDischargeOrder());
        apiEntity.setTreatmentOutcome(hosDischargeAbstractVo.getTreatmentOutcome());
        return apiEntity;
    }

    private String coverDateShow(String date) {
        if (StringUtils.isEmpty(date)) {
            return "未知";
        }
        String coverDate;
        if (date.length() >= 8 && StringUtils.isNumeric(date)) {
            coverDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
        } else {
            coverDate = date;
        }
        return coverDate;
    }
}
