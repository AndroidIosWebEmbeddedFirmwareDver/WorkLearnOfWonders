package com.wondersgroup.healthcloud.solr.dto;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDto {

    private int id;
    private String hosOrgCode;
    private String hosDeptCode;
    private String hosDoctCode;
    private String doctorName;
    private String doctorTitle;
    private String expertin;
    private int orderCount;
    private String hosName;
    private String deptName;
    private String headphoto;
    private String gender; // 1:男; 2:女

    public String getDeptName() {
        if (deptName == null) {
            deptName = "";
        }
        return deptName;
    }

    public String getGender() {
        if (StringUtils.isBlank(gender)) {
            gender = "1";
        } else if ("男".equals(gender)) {
            gender = "1";
        } else if ("女".equals(gender)) {
            gender = "2";
        }
        return gender;
    }

}
