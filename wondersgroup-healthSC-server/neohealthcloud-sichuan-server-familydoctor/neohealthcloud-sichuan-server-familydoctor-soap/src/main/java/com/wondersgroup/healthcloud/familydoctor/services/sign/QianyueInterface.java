
package com.wondersgroup.healthcloud.familydoctor.services.sign;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 */
@WebService(name = "QianyueInterface", targetNamespace = "http://webservice.fjzl.wondersgroup.com/")
@XmlSeeAlso({
        ObjectFactory.class
})
public interface QianyueInterface {


    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPackDetail", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.GetPackDetail")
    @ResponseWrapper(localName = "getPackDetailResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".GetPackDetailResponse")
    public String getPackDetail(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMyTeamList", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.GetMyTeamList")
    @ResponseWrapper(localName = "getMyTeamListResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".GetMyTeamListResponse")
    public String getMyTeamList(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getContractResident", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".GetContractResident")
    @ResponseWrapper(localName = "getContractResidentResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".GetContractResidentResponse")
    public String getContractResident(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "makeContract", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.MakeContract")
    @ResponseWrapper(localName = "makeContractResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".MakeContractResponse")
    public String makeContract(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMemberDetail", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.GetMemberDetail")
    @ResponseWrapper(localName = "getMemberDetailResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".GetMemberDetailResponse")
    public String getMemberDetail(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPackList", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.GetPackList")
    @ResponseWrapper(localName = "getPackListResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".GetPackListResponse")
    public String getPackList(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "loginByPhone", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.LoginByPhone")
    @ResponseWrapper(localName = "loginByPhoneResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".LoginByPhoneResponse")
    public String loginByPhone(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getMyTeamNum", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.GetMyTeamNum")
    @ResponseWrapper(localName = "getMyTeamNumResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign" +
            ".GetMyTeamNumResponse")
    public String getMyTeamNum(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

    /**
     * @param arg1
     * @param arg0
     * @return returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getTeamDetail", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.GetTeamDetail")
    @ResponseWrapper(localName = "getTeamDetailResponse", targetNamespace = "http://webservice.fjzl.wondersgroup.com/", className = "com.wondersgroup.healthcloud.familydoctor.services.sign.GetTeamDetailResponse")
    public String getTeamDetail(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    String arg1);

}
