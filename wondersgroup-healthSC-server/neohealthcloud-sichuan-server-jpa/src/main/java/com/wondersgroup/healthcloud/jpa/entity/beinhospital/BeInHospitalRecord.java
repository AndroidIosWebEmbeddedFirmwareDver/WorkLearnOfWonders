package com.wondersgroup.healthcloud.jpa.entity.beinhospital;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nick on 2016/11/8.
 */
@Data
@Entity
@Table(name = "tb_be_in_hospital_record")
public class BeInHospitalRecord {

    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column
    @Id
    private String id;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "hospital_code")
    private String hospitalCode;

    @Column(name = "be_in_hospital_code")
    private String beInHospitalCode;//住院号

    @Column(name = "in_hospital_date")
    private String inHospitalDate;

    @Column(name = "out_hospital_date")
    private String outHospitalDate;

    @Column
    @JsonIgnore
    private String area;//病区

    @Column(name = "bed_num")
    private String bedNum;//床位

    @Column(name = "department_code")
    private String departmentCode;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "doctor_code")
    private String doctorCode;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "nurse_code")
    private String nurseCode;

    @Column(name = "nurse_name")
    private String nurseName;

    @Column(name = "prepaid_payment_amt")
    private double prepaidPaymentAmt; //预交金余额

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "del_flag")
    private String delFlag;

    @Column(name = "out_hospital_flag")
    private String outHospitalFlag; //出院标志 0 未出院 1 出院

    public BeInHospitalRecord() {

    }
}
