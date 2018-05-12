package com.wondersgroup.healthcloud.config.client;

import com.wondersgroup.healthcloud.entity.response.GetOrderDetailInfoResponse;
import com.wondersgroup.healthcloud.entity.response.OrderCancelInfoResponse;
import com.wondersgroup.healthcloud.entity.response.OrderPayCompletedResponse;
import com.wondersgroup.healthcloud.entity.response.QueryOrderInfoListResponse;
import com.wondersgroup.healthcloud.entity.response.SubmitOrderByUserInfoResponse;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.wsdl.*;
import org.apache.log4j.Logger;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created by zhaozhenxing on 2016/11/3.
 */
public class OrderClient extends WebServiceGatewaySupport {

    private static final Logger log = Logger.getLogger(OrderClient.class);

    /**
     * 提交预约订单
     *
     * @param xml
     */
    public SubmitOrderByUserInfoResponse submitOrderByUserInfo(String xml) {
        QName qName = new QName("http://impl.webservice.booking.icarefx.net", "xmlString");
        SubmitOrderByUserInfoService req = new SubmitOrderByUserInfoService();
        JAXBElement<String> xmlElements = new JAXBElement(qName, String.class, xml);
        req.setXmlString(xmlElements);
        log.info("SubmitOrderByUserInfoService param --> " + xml);
        SubmitOrderByUserInfoServiceResponse resp = (SubmitOrderByUserInfoServiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(req);
        JAXBElement<String> returnValue = resp.getReturn();
        log.info("SubmitOrderByUserInfoService return --> " + returnValue.getValue());
        return JaxbUtil.convertToJavaBean(returnValue.getValue(), SubmitOrderByUserInfoResponse.class);
    }

    /**
     * 支付完成确认
     *
     * @param xml
     * @return
     */
    //  OrderPayCompletedService
    public OrderPayCompletedResponse orderPayCompleted(String xml) {
        QName qName = new QName("http://impl.webservice.booking.icarefx.net", "xmlString");
        OrderPayCompletedService req = new OrderPayCompletedService();
        JAXBElement<String> xmlElements = new JAXBElement(qName, String.class, xml);
        req.setXmlString(xmlElements);
        log.info("OrderPayCompletedService param --> " + xml);
        OrderPayCompletedServiceResponse resp = (OrderPayCompletedServiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(req);
        JAXBElement<String> returnValue = resp.getReturn();
        log.info("OrderPayCompletedService return --> " + returnValue.getValue());
        return JaxbUtil.convertToJavaBean(returnValue.getValue(), OrderPayCompletedResponse.class);
    }

    /**
     * 预约退号
     *
     * @param xml
     * @return
     */
    public OrderCancelInfoResponse orderCancelInfo(String xml) {
        QName qName = new QName("http://impl.webservice.booking.icarefx.net", "xmlString");
        OrderCancelInfoService req = new OrderCancelInfoService();
        JAXBElement<String> xmlElements = new JAXBElement(qName, String.class, xml);
        log.info("OrderCancelInfoService param --> " + xml);
        req.setXmlString(xmlElements);
        OrderCancelInfoServiceResponse resp = (OrderCancelInfoServiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(req);
        JAXBElement<String> returnValue = resp.getReturn();
        log.info("OrderCancelInfoService return --> " + returnValue.getValue());
        return JaxbUtil.convertToJavaBean(returnValue.getValue(), OrderCancelInfoResponse.class);
    }

    /**
     * 预约单详情查询
     *
     * @param xml
     * @return
     */
    public GetOrderDetailInfoResponse.Result getOrderDetailInfo(String xml) {
        QName qName = new QName("http://impl.webservice.booking.icarefx.net", "xmlString");
        GetOrderDetailInfoService req = new GetOrderDetailInfoService();
        JAXBElement<String> xmlElements = new JAXBElement(qName, String.class, xml);
        req.setXmlString(xmlElements);
        log.info("GetOrderDetailInfoService param --> " + xml);
        GetOrderDetailInfoServiceResponse resp = (GetOrderDetailInfoServiceResponse) getWebServiceTemplate().marshalSendAndReceive(req);
        JAXBElement<String> returnValue = resp.getReturn();
        log.info("GetOrderDetailInfoService return --> " + returnValue.getValue());
        GetOrderDetailInfoResponse getOrderDetailInfoResp = JaxbUtil.convertToJavaBean(returnValue.getValue(), GetOrderDetailInfoResponse.class);
        return getOrderDetailInfoResp.getResult();
    }

    /**
     * 查询预约单列表
     *
     * @param xml
     * @return
     */
    public List<QueryOrderInfoListResponse.Result> queryOrderInfoList(String xml) {
        QName qName = new QName("http://impl.webservice.booking.icarefx.net", "xmlStr");
        QueryOrderInfoListService req = new QueryOrderInfoListService();
        JAXBElement<String> xmlElements = new JAXBElement(qName, String.class, xml);
        req.setXmlStr(xmlElements);
        log.info("QueryOrderInfoListService param --> " + xml);
        QueryOrderInfoListServiceResponse resp = (QueryOrderInfoListServiceResponse) getWebServiceTemplate().marshalSendAndReceive(req);
        JAXBElement<String> returnValue = resp.getReturn();
        log.info("QueryOrderInfoListService return --> " + returnValue.getValue());
        QueryOrderInfoListResponse response = JaxbUtil.convertToJavaBean(returnValue.getValue(), QueryOrderInfoListResponse.class);
        return response.getResult();
    }
}
