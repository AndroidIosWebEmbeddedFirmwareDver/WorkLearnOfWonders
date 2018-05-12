package com.wondersgroup.healthcloud.api.http.dto.hospital;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import lombok.Data;

/**
 * Created by zhuchunliu on 2016/11/8.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HospitalNear {
    private Integer hospitalId;//医院Id
    private String hospitalCode;//医院code
    private String hospitalName; // 医院名称
    private String hospitalGrade; // 医院等级
    private Double hospitalLatitude; // 医院纬度
    private Double hospitalLongitude; // 医院经度
    private String receiveCount;//预约量
    private String receiveThumb;//缩略图
    private String hospitalPhoto;//医院图片
    @JsonIgnore
    private Double range;


    public HospitalNear(Hospital hospital) {
        this.hospitalId = hospital.getId();
        this.hospitalCode = hospital.getHospitalCode();
        this.hospitalName = hospital.getHospitalName();
        this.hospitalGrade = hospital.getHospitalGrade();
        this.hospitalLatitude = hospital.getHospitalLatitude();
        this.hospitalLongitude = hospital.getHosptialLongitude();
        this.receiveThumb = hospital.getHosptialPhoto();
        this.hospitalPhoto = hospital.getHosptialPhoto();
        if (hospital.getReceiveCount() < 10000) {
            this.receiveCount = hospital.getReceiveCount() + "";
        } else {
            if (hospital.getReceiveCount() % 10000 == 0) {
                this.receiveCount = hospital.getReceiveCount() / 10000 + "w";
            } else {
                this.receiveCount = hospital.getReceiveCount() / 10000 + "w+";
            }
        }

    }

    public static void main(String[] args) {
        int num = 12000;
        System.err.println(12000 / 10000);
        System.err.println(12000 % 10000);
    }
}
