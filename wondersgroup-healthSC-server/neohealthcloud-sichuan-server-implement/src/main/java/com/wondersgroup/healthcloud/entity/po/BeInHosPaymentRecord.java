package com.wondersgroup.healthcloud.entity.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wondersgroup.healthcloud.entity.response.BeInHosPayRecordDetailResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by nick on 2016/11/8.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BeInHosPaymentRecord {

    private String hospitalCode;
    private String paymentDate;
    private double paymentAmt;
    private String paymentMethod; // 01 微信 02 支付宝
    private String receiptNum; //单据编号

    public BeInHosPaymentRecord() {

    }

    public BeInHosPaymentRecord(BeInHosPayRecordDetailResponse.Item item, String hospitalCode) {
        this.hospitalCode = hospitalCode;
        this.paymentDate = item.getJfrq();
        this.paymentAmt = StringUtils.isEmpty(item.getJfje()) ? 0.00 : Double.valueOf(item.getJfje()) / 100;
        this.paymentMethod = item.getZffs();
    }
}
