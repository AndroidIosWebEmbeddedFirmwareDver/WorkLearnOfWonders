
package com.wondersgroup.healthcloud.familydoctor.services.referral;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.wondersgroup.healthcloud.familydoctor.services.referral package.
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

    private final static QName _OutpatientListResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "outpatientListResponse");
    private final static QName _OutpatientDetail_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "outpatientDetail");
    private final static QName _OutpatientApplyResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "outpatientApplyResponse");
    private final static QName _InpatientDetailResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "inpatientDetailResponse");
    private final static QName _InpatientListResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "inpatientListResponse");
    private final static QName _OutpatientDetailResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "outpatientDetailResponse");
    private final static QName _InpatientApply_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "inpatientApply");
    private final static QName _InpatientList_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "inpatientList");
    private final static QName _OutpatientList_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "outpatientList");
    private final static QName _InpatientApplyResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "inpatientApplyResponse");
    private final static QName _OutpatientApply_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "outpatientApply");
    private final static QName _OutpatientAction_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "outpatientAction");
    private final static QName _OutpatientActionResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "outpatientActionResponse");
    private final static QName _InpatientAction_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "inpatientAction");
    private final static QName _InpatientActionResponse_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "inpatientActionResponse");
    private final static QName _InpatientDetail_QNAME = new QName("http://webservice.fjzl.wondersgroup.com/", "inpatientDetail");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.wondersgroup.healthcloud.familydoctor.services.referral
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InpatientAction }
     */
    public InpatientAction createInpatientAction() {
        return new InpatientAction();
    }

    /**
     * Create an instance of {@link OutpatientActionResponse }
     */
    public OutpatientActionResponse createOutpatientActionResponse() {
        return new OutpatientActionResponse();
    }

    /**
     * Create an instance of {@link OutpatientAction }
     */
    public OutpatientAction createOutpatientAction() {
        return new OutpatientAction();
    }

    /**
     * Create an instance of {@link InpatientApplyResponse }
     */
    public InpatientApplyResponse createInpatientApplyResponse() {
        return new InpatientApplyResponse();
    }

    /**
     * Create an instance of {@link OutpatientApply }
     */
    public OutpatientApply createOutpatientApply() {
        return new OutpatientApply();
    }

    /**
     * Create an instance of {@link OutpatientList }
     */
    public OutpatientList createOutpatientList() {
        return new OutpatientList();
    }

    /**
     * Create an instance of {@link InpatientList }
     */
    public InpatientList createInpatientList() {
        return new InpatientList();
    }

    /**
     * Create an instance of {@link InpatientDetail }
     */
    public InpatientDetail createInpatientDetail() {
        return new InpatientDetail();
    }

    /**
     * Create an instance of {@link InpatientActionResponse }
     */
    public InpatientActionResponse createInpatientActionResponse() {
        return new InpatientActionResponse();
    }

    /**
     * Create an instance of {@link InpatientDetailResponse }
     */
    public InpatientDetailResponse createInpatientDetailResponse() {
        return new InpatientDetailResponse();
    }

    /**
     * Create an instance of {@link InpatientListResponse }
     */
    public InpatientListResponse createInpatientListResponse() {
        return new InpatientListResponse();
    }

    /**
     * Create an instance of {@link OutpatientApplyResponse }
     */
    public OutpatientApplyResponse createOutpatientApplyResponse() {
        return new OutpatientApplyResponse();
    }

    /**
     * Create an instance of {@link OutpatientDetail }
     */
    public OutpatientDetail createOutpatientDetail() {
        return new OutpatientDetail();
    }

    /**
     * Create an instance of {@link OutpatientListResponse }
     */
    public OutpatientListResponse createOutpatientListResponse() {
        return new OutpatientListResponse();
    }

    /**
     * Create an instance of {@link InpatientApply }
     */
    public InpatientApply createInpatientApply() {
        return new InpatientApply();
    }

    /**
     * Create an instance of {@link OutpatientDetailResponse }
     */
    public OutpatientDetailResponse createOutpatientDetailResponse() {
        return new OutpatientDetailResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutpatientListResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "outpatientListResponse")
    public JAXBElement<OutpatientListResponse> createOutpatientListResponse(OutpatientListResponse value) {
        return new JAXBElement<OutpatientListResponse>(_OutpatientListResponse_QNAME, OutpatientListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutpatientDetail }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "outpatientDetail")
    public JAXBElement<OutpatientDetail> createOutpatientDetail(OutpatientDetail value) {
        return new JAXBElement<OutpatientDetail>(_OutpatientDetail_QNAME, OutpatientDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutpatientApplyResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "outpatientApplyResponse")
    public JAXBElement<OutpatientApplyResponse> createOutpatientApplyResponse(OutpatientApplyResponse value) {
        return new JAXBElement<OutpatientApplyResponse>(_OutpatientApplyResponse_QNAME, OutpatientApplyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InpatientDetailResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "inpatientDetailResponse")
    public JAXBElement<InpatientDetailResponse> createInpatientDetailResponse(InpatientDetailResponse value) {
        return new JAXBElement<InpatientDetailResponse>(_InpatientDetailResponse_QNAME, InpatientDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InpatientListResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "inpatientListResponse")
    public JAXBElement<InpatientListResponse> createInpatientListResponse(InpatientListResponse value) {
        return new JAXBElement<InpatientListResponse>(_InpatientListResponse_QNAME, InpatientListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutpatientDetailResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "outpatientDetailResponse")
    public JAXBElement<OutpatientDetailResponse> createOutpatientDetailResponse(OutpatientDetailResponse value) {
        return new JAXBElement<OutpatientDetailResponse>(_OutpatientDetailResponse_QNAME, OutpatientDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InpatientApply }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "inpatientApply")
    public JAXBElement<InpatientApply> createInpatientApply(InpatientApply value) {
        return new JAXBElement<InpatientApply>(_InpatientApply_QNAME, InpatientApply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InpatientList }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "inpatientList")
    public JAXBElement<InpatientList> createInpatientList(InpatientList value) {
        return new JAXBElement<InpatientList>(_InpatientList_QNAME, InpatientList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutpatientList }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "outpatientList")
    public JAXBElement<OutpatientList> createOutpatientList(OutpatientList value) {
        return new JAXBElement<OutpatientList>(_OutpatientList_QNAME, OutpatientList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InpatientApplyResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "inpatientApplyResponse")
    public JAXBElement<InpatientApplyResponse> createInpatientApplyResponse(InpatientApplyResponse value) {
        return new JAXBElement<InpatientApplyResponse>(_InpatientApplyResponse_QNAME, InpatientApplyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutpatientApply }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "outpatientApply")
    public JAXBElement<OutpatientApply> createOutpatientApply(OutpatientApply value) {
        return new JAXBElement<OutpatientApply>(_OutpatientApply_QNAME, OutpatientApply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutpatientAction }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "outpatientAction")
    public JAXBElement<OutpatientAction> createOutpatientAction(OutpatientAction value) {
        return new JAXBElement<OutpatientAction>(_OutpatientAction_QNAME, OutpatientAction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OutpatientActionResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "outpatientActionResponse")
    public JAXBElement<OutpatientActionResponse> createOutpatientActionResponse(OutpatientActionResponse value) {
        return new JAXBElement<OutpatientActionResponse>(_OutpatientActionResponse_QNAME, OutpatientActionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InpatientAction }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "inpatientAction")
    public JAXBElement<InpatientAction> createInpatientAction(InpatientAction value) {
        return new JAXBElement<InpatientAction>(_InpatientAction_QNAME, InpatientAction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InpatientActionResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "inpatientActionResponse")
    public JAXBElement<InpatientActionResponse> createInpatientActionResponse(InpatientActionResponse value) {
        return new JAXBElement<InpatientActionResponse>(_InpatientActionResponse_QNAME, InpatientActionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InpatientDetail }{@code >}}
     */
    @XmlElementDecl(namespace = "http://webservice.fjzl.wondersgroup.com/", name = "inpatientDetail")
    public JAXBElement<InpatientDetail> createInpatientDetail(InpatientDetail value) {
        return new JAXBElement<InpatientDetail>(_InpatientDetail_QNAME, InpatientDetail.class, null, value);
    }

}
