package com.wondersgroup.healthcloud.entity.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.entity.response.UnPaidRecordResponse;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.pay.PayOrderDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by nick on 2016/11/9.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InterDisPaymentRecord {

    public InterDisPaymentRecord() {

    }

    public InterDisPaymentRecord(UnPaidRecordResponse.Item item, UnPaidRecordResponse response,
                                 String orderId, String hospitalName, PayOrder payOrder) {

        this.hospitalCode = response.getYljgdm();
        this.prescriptionNum = item.getCfhm();
        this.totalAmt = StringUtils.isEmpty(item.getFlje()) ? 0.0 : Double.valueOf(item.getFlje()) / 100;
        this.orderId = orderId;
        this.hospitalName = hospitalName;
        this.status = payOrder.getStatus();
        this.departmentName = item.getYjks();
        this.time = item.getKfsj();
        this.pay_order = new PayOrderDTO(payOrder);
    }

    private String hospitalName;

    private String hospitalCode;

    private String prescriptionNum;//处方号码 逗号分隔

    private double totalAmt; //总金额

    private String orderId; //订单号

    private String time;//开单时间

    private String departmentName;

    private PayOrder.Status status;

    private PayOrderDTO pay_order;

}
