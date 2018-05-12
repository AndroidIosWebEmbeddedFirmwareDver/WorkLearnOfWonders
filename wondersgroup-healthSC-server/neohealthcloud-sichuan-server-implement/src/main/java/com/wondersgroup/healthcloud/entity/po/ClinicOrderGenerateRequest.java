package com.wondersgroup.healthcloud.entity.po;

import lombok.Data;

/**
 * Created by nick on 2016/11/24.
 *
 * @author nick
 */
@Data
public class ClinicOrderGenerateRequest {

    private String prescriptionNum;
    private String hospitalName;
    private String time;
    private String deptName;
    private String hospitalCode;
    private String jzlsh;
    private String price;
    private String uid;
}
