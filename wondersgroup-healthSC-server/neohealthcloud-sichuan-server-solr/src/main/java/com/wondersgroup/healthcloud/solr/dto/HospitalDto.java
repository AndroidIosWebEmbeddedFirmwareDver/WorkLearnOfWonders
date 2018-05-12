package com.wondersgroup.healthcloud.solr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HospitalDto {

    private int id;
    private int hospitalId;
    private String hospitalCode;
    private String hospitalName;
    private String hospitalGrade;
    private String hospitalPhoto;
    private String receiveCount;
    private String receiveThumb;

    public HospitalDto(Hospital hospital) {
        this.id = hospital.getId();
        this.hospitalId = hospital.getId();
        this.hospitalCode = hospital.getHospitalCode();
        this.hospitalName = hospital.getHospitalName();
        this.hospitalGrade = hospital.getHospitalGrade();
        this.hospitalPhoto = hospital.getHosptialPhoto();
        int count = hospital.getReceiveCount();
        if (count >= 10000) {
            this.receiveCount = (count / 10000) + "w+";
        } else {
            this.receiveCount = count + "";
        }

        this.receiveThumb = hospital.getHosptialPhoto();
    }

}
