package com.wondersgroup.healthcloud.webservice.platform.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 平台注册接口
 * @author ful 2018/4/14 14:35
 */
@WebService(targetNamespace = "http://com.wondersgroup.healthcloud.webservice.platform.service")// 命名空间,一般是接口的包名倒序
public interface RegisterService {
    @WebMethod
    String userRegister(@WebParam(name = "xmlStr") String xmlStr);

}