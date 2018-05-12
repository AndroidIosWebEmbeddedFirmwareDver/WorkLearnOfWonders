package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthOnlineListAPIEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * 提取报告 - 门诊检查报告列表
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthOnlineJianChaListResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    @XmlElementWrapper(name = "Exams")
    @XmlElement(name = "Exam")
    private List<Exam> exam;

    @Data
    public static class Exam {
        private String applyTime;//申请时间
        private String applyDepartment;//申请科室
        private String medicalOrgName;
        private String examType;//检查类型: "血浆(肝素锂抗凝)"
        private String examItem;//检查项目
        private String examSite;//检查部位
        private String examName;//检查名称
        private String conclusion;//检查结论
        private String examNo;//检查号
        private String medicalOrgId;//医疗机构代码
        private String serialNum;//就诊流水号

        public String getApplyTime() {
            return null == applyTime ? "" : applyTime.replace("/", "-").substring(0, 10);
        }
    }

    public List<HealthOnlineListAPIEntity> coverToApiEntity(String viewUrl) {
        List<HealthOnlineListAPIEntity> apiEntities = new ArrayList<>();
        if (null == exam || exam.isEmpty()) {
            return null;
        }
        for (Exam examRow : exam) {
            HealthOnlineListAPIEntity apiEntity = new HealthOnlineListAPIEntity();
            String id = examRow.getMedicalOrgId() + "-|-" + examRow.getExamNo() + "-|-" + examRow.getSerialNum() + "-|-" + examRow.getApplyTime();
            apiEntity.setId(id);
            String hosName = "";
            try {
                id = URLEncoder.encode(id, "UTF-8");
                hosName = StringUtils.isNotEmpty(examRow.getMedicalOrgName()) ?
                        URLEncoder.encode(examRow.getMedicalOrgName(), "UTF-8") : "";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            apiEntity.setView_url(String.format(viewUrl, id, hosName));
            apiEntity.setDate(examRow.getApplyTime());
            apiEntity.setDepartment_name(examRow.getApplyDepartment());
            apiEntity.setHospital_name(examRow.getMedicalOrgName());
            apiEntity.setItem_name(examRow.getExamName());
            apiEntities.add(apiEntity);
        }
        return apiEntities;
    }

}
