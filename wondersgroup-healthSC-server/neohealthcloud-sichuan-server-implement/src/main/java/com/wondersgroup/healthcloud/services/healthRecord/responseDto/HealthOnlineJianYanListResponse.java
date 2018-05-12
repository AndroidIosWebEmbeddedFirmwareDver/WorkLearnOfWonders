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
 * 提取报告 - 门诊检验报告列表
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthOnlineJianYanListResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    @XmlElementWrapper(name = "clinicalLabs")
    @XmlElement(name = "clinicalLab")
    private List<ClinicalLabVo> clinicalLabVos;

    @Data
    @XmlRootElement(name = "clinicalLab")
    public static class ClinicalLabVo {
        private String applyTime;
        private String applyDepartment;
        private String medicalOrgId;
        private String medicalOrgName;
        private String serialNum;
        private String name;//检验标本: "血浆(肝素锂抗凝)"
        private String type;//报告单类别:"丙肝检测(抗原*抗体)+乙肝定量检测+前S1"
        private String date;//日期:"2015/11/15"
        private String rid;//报告id:"450754387|c|OP00056486402"
        @XmlElement(name = "rNo")
        private String rNo;//报告单号:"20151115G0050735"

        public String getDate() {
            date = null == date ? "" : date.replace("/", "-");
            return date.length() > 10 ? date.substring(0, 10) : date;
        }

        public String getApplyTime() {
            applyTime = null == applyTime ? "" : applyTime.replace("/", "-");
            return applyTime.length() > 10 ? applyTime.substring(0, 10) : applyTime;
        }

    }

    public List<HealthOnlineListAPIEntity> coverToApiEntity(String viewUrl) {
        List<HealthOnlineListAPIEntity> apiEntities = new ArrayList<>();
        if (null == clinicalLabVos || clinicalLabVos.isEmpty()) {
            return null;
        }
        Boolean viewBuild = false;
        if (StringUtils.isNotEmpty(viewUrl)) {
            viewBuild = true;
        }
        for (ClinicalLabVo labVo : clinicalLabVos) {
            HealthOnlineListAPIEntity apiEntity = new HealthOnlineListAPIEntity();
            String date = StringUtils.isEmpty(labVo.getDate()) ? labVo.getApplyTime() : labVo.getDate();
            String id = labVo.getMedicalOrgId() + "-|-" + labVo.getRid() + "-|-" + labVo.getRNo() + "-|-" + date;
            apiEntity.setId(id);
            if (viewBuild) {
                String hosName = "";
                try {
                    id = URLEncoder.encode(id, "UTF-8");
                    hosName = StringUtils.isNotEmpty(labVo.getMedicalOrgName()) ?
                            URLEncoder.encode(labVo.getMedicalOrgName(), "UTF-8") : "";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                apiEntity.setView_url(String.format(viewUrl, id, hosName));
            }
            //app 显示的时间 applyTime,没有显示date
            apiEntity.setDate(StringUtils.isNotEmpty(labVo.getApplyTime()) ? labVo.getApplyTime() : labVo.getDate());
            apiEntity.setDepartment_name(labVo.getApplyDepartment());
            apiEntity.setHospital_name(labVo.getMedicalOrgName());
            apiEntity.setItem_name(labVo.getType());
            apiEntities.add(apiEntity);
        }
        return apiEntities;
    }
}
