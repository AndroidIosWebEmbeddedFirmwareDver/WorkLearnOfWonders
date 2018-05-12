package com.wondersgroup.healthcloud.config.client;

import com.wondersgroup.healthcloud.entity.response.department.DepInfoResponse;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.wsdl.GetDeptInfoTopService;
import com.wondersgroup.healthcloud.wsdl.GetDeptInfoTopServiceResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * Created by dukuanxin on 2016/11/4.
 */
public class GetTopDeptInfoClient extends WebServiceGatewaySupport {

    public DepInfoResponse get(String xml) {
        GetDeptInfoTopService request = new GetDeptInfoTopService();
        JAXBElement<String> xmlElements = new JAXBElement(
                new QName("http://impl.webservice.booking.icarefx.net", "xmlString"), String.class, xml);
        request.setXmlString(xmlElements);
        GetDeptInfoTopServiceResponse response = (GetDeptInfoTopServiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        String value = response.getReturn().getValue();
        DepInfoResponse depInfos = JaxbUtil.convertToJavaBean(value, DepInfoResponse.class);
        return depInfos;
    }
}
