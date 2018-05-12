package com.wondersgroup.healthcloud.services.beinhospital;

import com.wondersgroup.healthcloud.entity.po.ClinicOrderGenerateRequest;
import com.wondersgroup.healthcloud.entity.po.Order;

import java.io.IOException;
import java.util.List;

/**
 * Created by nick on 2016/11/9.
 *
 * @author nick
 */
public interface InterDiagnosisPaymentService {

    List<Order> getCurrentUnPayRecord(String hospitalCode, String uid, String cityCode) throws IOException;

    Order generateClinicOrder(ClinicOrderGenerateRequest request, String cityCode) throws IOException;
}
