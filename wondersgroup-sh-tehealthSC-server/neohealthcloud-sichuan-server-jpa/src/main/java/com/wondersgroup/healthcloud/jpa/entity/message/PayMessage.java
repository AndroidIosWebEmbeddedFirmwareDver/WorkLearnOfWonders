package com.wondersgroup.healthcloud.jpa.entity.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_pay_message")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayMessage {
    @Id
    private String id;
    private Integer type;

    @Column(name = "pay_type")
    private Integer payType;         //1 诊间支付，2挂号费支付

    @Column(name = "pay_status")
    private Integer payStatus;       //0 失败，1 成功

    @Column(name = "register_id")
    private String registerId;      //用户id

    @Column(name = "order_id")
    private String orderId;         //订单号

    @Column(name = "hospital_name")
    private String hospitalName;    //医院名称

    @Column(name = "patient_name")
    private String patientName;     //患者姓名

    private String department;      //科室名称

    @Column(name = "doctor_name")
    private String doctorName;      //医生姓名

    @Column(name = "clinic_type")
    private String clinicType;      //门诊类，如：-专家门诊

    @Column(name = "prescription_code")
    private String prescriptionCode;

    private Double price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "order_time")
    private Date orderTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "prescription_time")
    private Date prescriptionTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_date")
    private Date createDate;

    public PayMessage() {

    }

    public PayMessage(Integer pay_type) {
        this.payType = pay_type;
    }

    public PayMessage(String register_id) {
        this.registerId = register_id;
    }

    public PayMessage(Integer pay_type, String register_id) {
        this.payType = pay_type;
        this.registerId = register_id;
    }

}
