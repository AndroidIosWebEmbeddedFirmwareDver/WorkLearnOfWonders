package com.wondersgroup.healthcloud.api.http.controllers.beInhospayment;

import com.wondersgroup.healthcloud.common.http.dto.JsonListResponseEntity;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.entity.po.ClinicOrderGenerateRequest;
import com.wondersgroup.healthcloud.entity.po.Order;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.beinhospital.InterDiagnosisPaymentService;
import com.wondersgroup.healthcloud.services.beinhospital.PayOrderServiceManager;
import com.wondersgroup.healthcloud.services.beinhospital.dto.MyOrder;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.pay.PayService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


/**
 * Created by nick on 2016/11/9.
 *
 * @author nick
 */
@RestController
@RequestMapping("/api/sl/interDiaPayment")
public class SLInterDiagnosisPaymentController {

    @Autowired
    private InterDiagnosisPaymentService interDiagnosisPaymentService;

    @Autowired
    private PayService payService;

    @Autowired
    private HospitalService hospitalService;

    private static Integer pageSize = 10;

    /**
     * 获取当前用户代缴费的记录
     *
     * @param hospitalCode
     * @param uid
     * @return
     */
    @RequestMapping(value = "/unPayRecord", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<Order> getUnPaidRecord(@RequestHeader(name = "city-code", defaultValue = "510122000000") String cityCode,
                                                         @RequestParam String hospitalCode,
                                                         @RequestParam String uid) throws IOException {
        JsonListResponseEntity<Order> responseEntity = new JsonListResponseEntity<>();
        List<Order> record = interDiagnosisPaymentService.getCurrentUnPayRecord(hospitalCode, uid, cityCode);
        responseEntity.setContent(record);
        return responseEntity;
    }


    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    @VersionRange
    public JsonResponseEntity<Order> generateOrder(@RequestHeader(name = "city-code", defaultValue = "510122000000") String cityCode,
                                                   @RequestBody ClinicOrderGenerateRequest request) throws IOException {

        JsonResponseEntity<Order> responseEntity = new JsonResponseEntity<>();
        Order order = interDiagnosisPaymentService.generateClinicOrder(request, cityCode);
        responseEntity.setData(order);
        return responseEntity;
    }

    /**
     * 获取用户预约订单
     *
     * @param uid
     * @param flag
     * @return
     */
    @RequestMapping(value = "/appoint", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<Order> getAppointmentOrder(@RequestHeader(name = "city-code", defaultValue = "510122000000") String cityCode,
                                                             @RequestParam String uid,
                                                             @RequestParam(required = false) String flag) {
        JsonListResponseEntity<Order> responseEntity = new JsonListResponseEntity<>();
        int start = 0;
        if (!StringUtils.isEmpty(flag)) {
            start = Integer.valueOf(flag);
        }
        payService.hospitalService = hospitalService;
        PayOrderServiceManager manager = new PayOrderServiceManager(start, pageSize, "APPOINTMENT", payService);
        MyOrder myOrder = manager.getMyOrders(uid, cityCode);
        responseEntity.setContent(myOrder.getOrders(), myOrder.isHasMore(), "", myOrder.getNextPage());
        return responseEntity;
    }

    /**
     * 之前代码写的太垃圾 重构了一下
     *
     * @param uid
     * @param status
     * @param flag
     * @return
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @VersionRange
    public JsonListResponseEntity<Order> getOrders(@RequestHeader(name = "city-code", defaultValue = "510122000000") String cityCode,
                                                   @RequestParam String uid,
                                                   @RequestParam String status,
                                                   @RequestParam(required = false) String flag) {
        JsonListResponseEntity<Order> responseEntity = new JsonListResponseEntity<>();
        int start = 0;
        if (!StringUtils.isEmpty(flag))
            start = Integer.valueOf(flag);
        PayOrder.Status orderStatus = null;
        if (!"ALL".equalsIgnoreCase(status)) {
            orderStatus = Enum.valueOf(PayOrder.Status.class, status);
        }
        payService.hospitalService = hospitalService;
        PayOrderServiceManager manager = new PayOrderServiceManager(start, pageSize, orderStatus, payService);
        MyOrder myOrder = manager.getMyOrders(uid, cityCode);
        responseEntity.setContent(myOrder.getOrders(), myOrder.isHasMore(), "", myOrder.getNextPage());
        return responseEntity;
    }
}
