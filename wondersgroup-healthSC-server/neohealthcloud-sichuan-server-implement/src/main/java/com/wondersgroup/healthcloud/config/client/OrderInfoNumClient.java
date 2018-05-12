package com.wondersgroup.healthcloud.config.client;


import com.wondersgroup.healthcloud.entity.response.OrderNumInfoResponse;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.wsdl.GetOrderNumInfoService;
import com.wondersgroup.healthcloud.wsdl.GetOrderNumInfoServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * Created by dukuanxin on 2016/10/30.
 * OrderInfoNumClient
 * 调用号源信息client
 */
public class OrderInfoNumClient extends WebServiceGatewaySupport {
    private static Logger logger = LoggerFactory.getLogger(OrderInfoNumClient.class);

    public OrderNumInfoResponse getOrderNumInfoService(String xml) {

        GetOrderNumInfoService request = new GetOrderNumInfoService();
        JAXBElement<String> xmlElements = new JAXBElement(
                new QName("http://impl.webservice.booking.icarefx.net", "xmlString"), String.class, xml);
        request.setXmlString(xmlElements);
        GetOrderNumInfoServiceResponse response = (GetOrderNumInfoServiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        String value = response.getReturn().getValue();
        logger.info("query schedule request -->" + xml);
        OrderNumInfoResponse orderNumInfoResponse = JaxbUtil.convertToJavaBean(value, OrderNumInfoResponse.class);
//        logger.info("query schedule response -->"+value);
        return orderNumInfoResponse;
    }
}