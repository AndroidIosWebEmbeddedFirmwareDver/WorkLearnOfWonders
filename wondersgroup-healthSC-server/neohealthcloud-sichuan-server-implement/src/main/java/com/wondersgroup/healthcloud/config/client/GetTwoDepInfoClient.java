package com.wondersgroup.healthcloud.config.client;

import com.wondersgroup.healthcloud.entity.response.department.DepTwoInfoResponse;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.wsdl.GetDeptInfoTwoService;
import com.wondersgroup.healthcloud.wsdl.GetDeptInfoTwoServiceResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * Created by dukuanxin on 2016/11/4.
 */
public class GetTwoDepInfoClient extends WebServiceGatewaySupport {

    public DepTwoInfoResponse get(String xml) {
        GetDeptInfoTwoService request = new GetDeptInfoTwoService();
        JAXBElement<String> xmlElements = new JAXBElement(
                new QName("http://impl.webservice.booking.icarefx.net", "xmlString"), String.class, xml);
        request.setXmlString(xmlElements);
        GetDeptInfoTwoServiceResponse response = (GetDeptInfoTwoServiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        String value = response.getReturn().getValue();
        DepTwoInfoResponse depInfos = JaxbUtil.convertToJavaBean(value, DepTwoInfoResponse.class);
        return depInfos;
    }

}
