package com.wondersgroup.healthcloud.solr.dto;

import com.wondersgroup.healthcloud.solr.document.SolrHospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tanxueliang on 16/11/10.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendDto {

    private String hospitalId;

    private String hospitalCode;

    private String hospitalName;

    public RecommendDto(SolrHospital hospital) {
        this.hospitalId = hospital.getId() + "";
        this.hospitalCode = hospital.getHospitalCode();
        this.hospitalName = hospital.getHospitalName();
    }
}
