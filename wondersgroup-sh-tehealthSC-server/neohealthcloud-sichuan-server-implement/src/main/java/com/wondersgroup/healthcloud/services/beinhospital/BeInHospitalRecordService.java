package com.wondersgroup.healthcloud.services.beinhospital;

import com.wondersgroup.healthcloud.entity.po.BeInHosPaymentRecord;
import com.wondersgroup.healthcloud.jpa.entity.beinhospital.BeInHospitalRecord;

import java.util.List;

/**
 * Created by nick on 2016/11/8.
 */
public interface BeInHospitalRecordService {

    List<BeInHospitalRecord> getRecordsByIdCard(String idCard, String hospitalCode, int start, int end);

    int countByIdCard(String idCard, String hospitalCode);

    List<BeInHosPaymentRecord> queryBeInHosPrePaidRecord(String idCard, String hospitalCode, String beInHospitalCode);
}
