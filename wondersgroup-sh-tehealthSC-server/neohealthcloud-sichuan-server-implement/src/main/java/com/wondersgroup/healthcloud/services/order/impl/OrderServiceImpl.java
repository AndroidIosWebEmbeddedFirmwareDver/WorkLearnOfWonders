package com.wondersgroup.healthcloud.services.order.impl;

import com.wondersgroup.healthcloud.config.client.OrderClient;
import com.wondersgroup.healthcloud.entity.RequestMessageHeaderUtil;
import com.wondersgroup.healthcloud.entity.request.*;
import com.wondersgroup.healthcloud.entity.response.*;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.order.OrderService;
import com.wondersgroup.healthcloud.utils.AreaResourceUrl;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.utils.SignatureGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约订单服务
 * 用于调用区域平台WebService服务
 * Created by zhaozhenxing on 2016/11/2.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private SignatureGenerator signatureGenerator;

    @Autowired
    private RequestMessageHeaderUtil reqMesHeaderUtil;

    @Autowired
    private AreaResourceUrl areaResourceUrl;
    @Autowired
    private HospitalService hospitalService;

    @Override
    public List<QueryOrderInfoListResponse.Result> getOrderList(QueryOrderInfoListRequest.OrderInfo orderInfo, String... cityCodes) {
        QueryOrderInfoListRequest queryOrderInfoListReq = new QueryOrderInfoListRequest();
        queryOrderInfoListReq.requestMessageHeader = reqMesHeaderUtil.generator();
        queryOrderInfoListReq.orderInfo = orderInfo;
        queryOrderInfoListReq.requestMessageHeader.setSign(signatureGenerator.generateSignature(queryOrderInfoListReq));// 设置签名
        String hostialCityCode = getHospitalCityCode(orderInfo.getHosOrgCode());
        orderClient.setDefaultUri(areaResourceUrl.getUrl("1", hostialCityCode));
        return orderClient.queryOrderInfoList(JaxbUtil.convertToXml(queryOrderInfoListReq, "GBK"));
    }

    @Override
    public GetOrderDetailInfoResponse.Result getOrderDetail(GetOrderDetailInfoRequest.OrderInfo orderInfo, String... cityCodes) {
        GetOrderDetailInfoRequest getOrderDetailInfoReq = new GetOrderDetailInfoRequest();
        getOrderDetailInfoReq.requestMessageHeader = reqMesHeaderUtil.generator();
        getOrderDetailInfoReq.orderInfo = orderInfo;
        getOrderDetailInfoReq.requestMessageHeader.setSign(signatureGenerator.generateSignature(getOrderDetailInfoReq));// 设置签名
        orderClient.setDefaultUri(areaResourceUrl.getUrl("1", cityCodes));
        return orderClient.getOrderDetailInfo(JaxbUtil.convertToXml(getOrderDetailInfoReq, "GBK"));
    }

    @Override
    public SubmitOrderByUserInfoResponse submitOrder(SubmitOrderByUserInfoRequest.OrderInfo orderInfo, String... cityCodes) {
        SubmitOrderByUserInfoRequest submitOrderByUserInfoReq = new SubmitOrderByUserInfoRequest();
        submitOrderByUserInfoReq.requestMessageHeader = reqMesHeaderUtil.generator();
        submitOrderByUserInfoReq.orderInfo = orderInfo;
        submitOrderByUserInfoReq.requestMessageHeader.setSign(signatureGenerator.generateSignature(submitOrderByUserInfoReq));// 设置签名
        String hostialCityCode = getHospitalCityCode(orderInfo.getHosOrgCode());
        orderClient.setDefaultUri(areaResourceUrl.getUrl("1", hostialCityCode));
        return orderClient.submitOrderByUserInfo(JaxbUtil.convertToXml(submitOrderByUserInfoReq, "GBK"));
    }

    @Override
    public OrderCancelInfoResponse cancelOrder(OrderCancelInfoRequest.OrderInfo orderInfo, String... cityCodes) {
        OrderCancelInfoRequest orderCancelInfoReq = new OrderCancelInfoRequest();
        orderCancelInfoReq.requestMessageHeader = reqMesHeaderUtil.generator();
        orderCancelInfoReq.orderInfo = orderInfo;
        orderCancelInfoReq.requestMessageHeader.setSign(signatureGenerator.generateSignature(orderCancelInfoReq));// 设置签名
        String hostialCityCode = getHospitalCityCode(orderInfo.getHosOrgCode());
        orderClient.setDefaultUri(areaResourceUrl.getUrl("1", hostialCityCode));
        return orderClient.orderCancelInfo(JaxbUtil.convertToXml(orderCancelInfoReq, "GBK"));
    }

    @Override
    public OrderPayCompletedResponse orderPayCompleted(OrderPayCompletedRequest.OrderInfo orderInfo, String... cityCodes) {
        OrderPayCompletedRequest orderPayCompletedReq = new OrderPayCompletedRequest();
        orderPayCompletedReq.requestMessageHeader = reqMesHeaderUtil.generator();
        orderPayCompletedReq.orderInfo = orderInfo;
        orderPayCompletedReq.requestMessageHeader.setSign(signatureGenerator.generateSignature(orderPayCompletedReq));// 设置签名
        String hostialCityCode = getHospitalCityCode(cityCodes[0]);
        orderClient.setDefaultUri(areaResourceUrl.getUrl("1", hostialCityCode));
        return orderClient.orderPayCompleted(JaxbUtil.convertToXml(orderPayCompletedReq, "GBK"));
    }

    public String getHospitalCityCode(String hospitalCode) {

        Hospital hospital = hospitalService.findByHospitalCode(hospitalCode);

        return hospital.getCityCode();
    }
}
