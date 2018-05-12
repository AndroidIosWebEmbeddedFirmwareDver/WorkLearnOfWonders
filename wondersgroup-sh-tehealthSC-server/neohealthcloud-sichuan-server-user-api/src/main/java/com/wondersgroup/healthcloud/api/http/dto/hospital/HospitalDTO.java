package com.wondersgroup.healthcloud.api.http.dto.hospital;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateHospital;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateHospitalListDTO;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateHospitalDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by dukuanxin on 2016/11/4.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HospitalDTO {

    private int hospitalId;
    private String hospitalCode;//医院code
    private String receiveThumb;//缩略图
    private String hospitalPhoto;
    private String hospitalName;
    private String hospitalAddress;
    private String hospitalDesc;
    private String hospitalTel;
    private String hospitalGrade;//等级
    private String receiveCount;//预约量
    private Integer evaluateCount;//评价量
    private Integer concern;//是否关注0-未关注，1-已关注
    private List<AppEvaluateHospitalListDTO> evaluList;//医院评价

    public HospitalDTO(String code) {

    }

    public HospitalDTO(Hospital hospital) {
        this.hospitalId = hospital.getId();
        this.hospitalCode = hospital.getHospitalCode();
        this.receiveThumb = hospital.getHosptialPhoto();
        this.hospitalPhoto = hospital.getHosptialPhoto();
        this.hospitalName = hospital.getHospitalName();
        this.hospitalAddress = hospital.getHospitalAddress();
        this.hospitalDesc = hospital.getHospitalDesc();
        this.hospitalTel = hospital.getHospitalPhone();
        this.hospitalGrade = hospital.getHospitalGrade();
        if (hospital.getReceiveCount() >= 10000) {
            this.receiveCount = hospital.getReceiveCount() / 10000 + "w+";
        } else {
            this.receiveCount = hospital.getReceiveCount() + "";
        }
    }
}
