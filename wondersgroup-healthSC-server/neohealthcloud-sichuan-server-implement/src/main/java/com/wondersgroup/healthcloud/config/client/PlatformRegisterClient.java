package com.wondersgroup.healthcloud.config.client;

import com.wondersgroup.healthcloud.entity.response.*;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.wsdl.*;
import org.apache.log4j.Logger;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * Created by fulong on 2018/4/17.
 */
public class PlatformRegisterClient extends WebServiceGatewaySupport {

    private static final Logger log = Logger.getLogger(PlatformRegisterClient.class);

    /**
     * 微健康用户平台注册
     *
     * @param xml
     */
    public RegisterOrUpdateUserInfoResponse registerOrUpdateUserInfo(String xml) {
        QName qName = new QName("http://impl.webservice.booking.icarefx.net", "xmlStr");
        RegisterOrUpdateUserInfoService req = new RegisterOrUpdateUserInfoService();
        JAXBElement<String> xmlElements = new JAXBElement(qName, String.class, xml);
        req.setXmlStr(xmlElements);
        log.info("RegisterOrUpdateUserInfoService param --> " + xml);
        RegisterOrUpdateUserInfoServiceResponse resp = (RegisterOrUpdateUserInfoServiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(req);
        JAXBElement<String> returnValue = resp.getReturn();
        log.info("RegisterOrUpdateUserInfoService return --> " + returnValue.getValue());
        return JaxbUtil.convertToJavaBean(returnValue.getValue(), RegisterOrUpdateUserInfoResponse.class);
    }

}
