package com.wondersgroup.healthcloud.services.healthRecord.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.services.healthRecord.dto.HealthJianYanListAPIEntity;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * 门诊检验报告列表S
 */
@Data
@XmlRootElement(name = "res")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthJianYanListResponse implements Serializable {

    public String resultCode;
    public String resultDesc;
    public Integer totalPage = 0;

    @XmlElementWrapper(name = "clinicalLabs")
    @XmlElement(name = "clinicalLab")
    private List<ClinicalLab> clinicalLabs;

    @Data
    @XmlRootElement(name = "clinicalLab")
    public static class ClinicalLab {
        private String name;//检验标本: "血浆(肝素锂抗凝)"
        private String type;//报告单类别:"丙肝检测(抗原*抗体)+乙肝定量检测+前S1"
        private String date;//日期:"2015/11/15"
        private String rid;//报告id:"450754387|c|OP00056486402"
        @XmlElement(name = "rNo")
        private String rNo;//报告单号:"20151115G0050735"
        private String orgName;//机构名称:"市三医院"
        private String department;//科室
    }

    public List<HealthJianYanListAPIEntity> coverToApiEntity() {
        List<HealthJianYanListAPIEntity> apiEntities = new ArrayList<>();
        if (null == clinicalLabs || clinicalLabs.isEmpty()) {
            return null;
        }
        for (ClinicalLab labVo : clinicalLabs) {
            HealthJianYanListAPIEntity apiEntity = new HealthJianYanListAPIEntity();
            String id = labVo.getRid() + "-|-" + labVo.getRNo() + "-|-" + labVo.getDate();
            apiEntity.setId(id);
            apiEntity.setDate(coverDateShow(labVo.getDate()));
            apiEntity.setDepartment_name(labVo.getDepartment());
            apiEntity.setHospital_name(labVo.getOrgName());
            apiEntity.setItem_name(labVo.getType());
            apiEntities.add(apiEntity);
        }
        return apiEntities;
    }

    private String coverDateShow(String date) {
        return null == date ? "" : date.replace("/", "-");
    }
}
