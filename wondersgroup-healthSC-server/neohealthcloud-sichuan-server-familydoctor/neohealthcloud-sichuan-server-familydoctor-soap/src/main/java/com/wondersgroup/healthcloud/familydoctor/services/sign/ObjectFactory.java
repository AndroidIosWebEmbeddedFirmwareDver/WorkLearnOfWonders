
package com.wondersgroup.healthcloud.familydoctor.services.sign;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.wondersgroup.healthcloud.familydoctor.services.sign package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetMyTeamNumResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getMyTeamNumResponse");
    private final static QName _GetMyTeamListResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getMyTeamListResponse");
    private final static QName _MakeContract_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "makeContract");
    private final static QName _GetContractResidentResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getContractResidentResponse");
    private final static QName _MakeContractResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "makeContractResponse");
    private final static QName _GetMyTeamList_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getMyTeamList");
    private final static QName _GetContractResident_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getContractResident");
    private final static QName _GetPackList_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getPackList");
    private final static QName _GetPackDetail_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getPackDetail");
    private final static QName _LoginByPhone_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "loginByPhone");
    private final static QName _GetTeamDetail_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getTeamDetail");
    private final static QName _GetMemberDetailResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getMemberDetailResponse");
    private final static QName _GetPackDetailResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getPackDetailResponse");
    private final static QName _GetMyTeamNum_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getMyTeamNum");
    private final static QName _GetPackListResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getPackListResponse");
    private final static QName _GetMemberDetail_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getMemberDetail");
    private final static QName _LoginByPhoneResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "loginByPhoneResponse");
    private final static QName _GetTeamDetailResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "getTeamDetailResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wondersgroup.healthcloud.familydoctor.services.sign
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPackListResponse }
     */
    public GetPackListResponse createGetPackListResponse() {
        return new GetPackListResponse();
    }

    /**
     * Create an instance of {@link GetMyTeamNum }
     */
    public GetMyTeamNum createGetMyTeamNum() {
        return new GetMyTeamNum();
    }

    /**
     * Create an instance of {@link GetMemberDetailResponse }
     */
    public GetMemberDetailResponse createGetMemberDetailResponse() {
        return new GetMemberDetailResponse();
    }

    /**
     * Create an instance of {@link GetPackDetailResponse }
     */
    public GetPackDetailResponse createGetPackDetailResponse() {
        return new GetPackDetailResponse();
    }

    /**
     * Create an instance of {@link GetTeamDetail }
     */
    public GetTeamDetail createGetTeamDetail() {
        return new GetTeamDetail();
    }

    /**
     * Create an instance of {@link GetTeamDetailResponse }
     */
    public GetTeamDetailResponse createGetTeamDetailResponse() {
        return new GetTeamDetailResponse();
    }

    /**
     * Create an instance of {@link LoginByPhoneResponse }
     */
    public LoginByPhoneResponse createLoginByPhoneResponse() {
        return new LoginByPhoneResponse();
    }

    /**
     * Create an instance of {@link GetMemberDetail }
     */
    public GetMemberDetail createGetMemberDetail() {
        return new GetMemberDetail();
    }

    /**
     * Create an instance of {@link GetMyTeamList }
     */
    public GetMyTeamList createGetMyTeamList() {
        return new GetMyTeamList();
    }

    /**
     * Create an instance of {@link MakeContractResponse }
     */
    public MakeContractResponse createMakeContractResponse() {
        return new MakeContractResponse();
    }

    /**
     * Create an instance of {@link GetContractResidentResponse }
     */
    public GetContractResidentResponse createGetContractResidentResponse() {
        return new GetContractResidentResponse();
    }

    /**
     * Create an instance of {@link MakeContract }
     */
    public MakeContract createMakeContract() {
        return new MakeContract();
    }

    /**
     * Create an instance of {@link GetMyTeamListResponse }
     */
    public GetMyTeamListResponse createGetMyTeamListResponse() {
        return new GetMyTeamListResponse();
    }

    /**
     * Create an instance of {@link GetMyTeamNumResponse }
     */
    public GetMyTeamNumResponse createGetMyTeamNumResponse() {
        return new GetMyTeamNumResponse();
    }

    /**
     * Create an instance of {@link LoginByPhone }
     */
    public LoginByPhone createLoginByPhone() {
        return new LoginByPhone();
    }

    /**
     * Create an instance of {@link GetPackDetail }
     */
    public GetPackDetail createGetPackDetail() {
        return new GetPackDetail();
    }

    /**
     * Create an instance of {@link GetPackList }
     */
    public GetPackList createGetPackList() {
        return new GetPackList();
    }

    /**
     * Create an instance of {@link GetContractResident }
     */
    public GetContractResident createGetContractResident() {
        return new GetContractResident();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMyTeamNumResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getMyTeamNumResponse")
    public JAXBElement<GetMyTeamNumResponse> createGetMyTeamNumResponse(GetMyTeamNumResponse value) {
        return new JAXBElement<GetMyTeamNumResponse>(_GetMyTeamNumResponse_QNAME, GetMyTeamNumResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMyTeamListResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getMyTeamListResponse")
    public JAXBElement<GetMyTeamListResponse> createGetMyTeamListResponse(GetMyTeamListResponse value) {
        return new JAXBElement<GetMyTeamListResponse>(_GetMyTeamListResponse_QNAME, GetMyTeamListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeContract }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "makeContract")
    public JAXBElement<MakeContract> createMakeContract(MakeContract value) {
        return new JAXBElement<MakeContract>(_MakeContract_QNAME, MakeContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractResidentResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getContractResidentResponse")
    public JAXBElement<GetContractResidentResponse> createGetContractResidentResponse(GetContractResidentResponse value) {
        return new JAXBElement<GetContractResidentResponse>(_GetContractResidentResponse_QNAME, GetContractResidentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeContractResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "makeContractResponse")
    public JAXBElement<MakeContractResponse> createMakeContractResponse(MakeContractResponse value) {
        return new JAXBElement<MakeContractResponse>(_MakeContractResponse_QNAME, MakeContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMyTeamList }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getMyTeamList")
    public JAXBElement<GetMyTeamList> createGetMyTeamList(GetMyTeamList value) {
        return new JAXBElement<GetMyTeamList>(_GetMyTeamList_QNAME, GetMyTeamList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractResident }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getContractResident")
    public JAXBElement<GetContractResident> createGetContractResident(GetContractResident value) {
        return new JAXBElement<GetContractResident>(_GetContractResident_QNAME, GetContractResident.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPackList }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getPackList")
    public JAXBElement<GetPackList> createGetPackList(GetPackList value) {
        return new JAXBElement<GetPackList>(_GetPackList_QNAME, GetPackList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPackDetail }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getPackDetail")
    public JAXBElement<GetPackDetail> createGetPackDetail(GetPackDetail value) {
        return new JAXBElement<GetPackDetail>(_GetPackDetail_QNAME, GetPackDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginByPhone }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "loginByPhone")
    public JAXBElement<LoginByPhone> createLoginByPhone(LoginByPhone value) {
        return new JAXBElement<LoginByPhone>(_LoginByPhone_QNAME, LoginByPhone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeamDetail }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getTeamDetail")
    public JAXBElement<GetTeamDetail> createGetTeamDetail(GetTeamDetail value) {
        return new JAXBElement<GetTeamDetail>(_GetTeamDetail_QNAME, GetTeamDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMemberDetailResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getMemberDetailResponse")
    public JAXBElement<GetMemberDetailResponse> createGetMemberDetailResponse(GetMemberDetailResponse value) {
        return new JAXBElement<GetMemberDetailResponse>(_GetMemberDetailResponse_QNAME, GetMemberDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPackDetailResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getPackDetailResponse")
    public JAXBElement<GetPackDetailResponse> createGetPackDetailResponse(GetPackDetailResponse value) {
        return new JAXBElement<GetPackDetailResponse>(_GetPackDetailResponse_QNAME, GetPackDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMyTeamNum }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getMyTeamNum")
    public JAXBElement<GetMyTeamNum> createGetMyTeamNum(GetMyTeamNum value) {
        return new JAXBElement<GetMyTeamNum>(_GetMyTeamNum_QNAME, GetMyTeamNum.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPackListResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getPackListResponse")
    public JAXBElement<GetPackListResponse> createGetPackListResponse(GetPackListResponse value) {
        return new JAXBElement<GetPackListResponse>(_GetPackListResponse_QNAME, GetPackListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMemberDetail }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getMemberDetail")
    public JAXBElement<GetMemberDetail> createGetMemberDetail(GetMemberDetail value) {
        return new JAXBElement<GetMemberDetail>(_GetMemberDetail_QNAME, GetMemberDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginByPhoneResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "loginByPhoneResponse")
    public JAXBElement<LoginByPhoneResponse> createLoginByPhoneResponse(LoginByPhoneResponse value) {
        return new JAXBElement<LoginByPhoneResponse>(_LoginByPhoneResponse_QNAME, LoginByPhoneResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTeamDetailResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "getTeamDetailResponse")
    public JAXBElement<GetTeamDetailResponse> createGetTeamDetailResponse(GetTeamDetailResponse value) {
        return new JAXBElement<GetTeamDetailResponse>(_GetTeamDetailResponse_QNAME, GetTeamDetailResponse.class, null, value);
    }

}
