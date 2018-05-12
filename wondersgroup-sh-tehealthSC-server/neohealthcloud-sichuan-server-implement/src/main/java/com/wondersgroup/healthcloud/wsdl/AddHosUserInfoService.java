//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.11 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.10.30 时间 03:44:21 PM CST 
//


package com.wondersgroup.healthcloud.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="xmlStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "xmlStr"
})
@XmlRootElement(name = "AddHosUserInfoService")
public class AddHosUserInfoService {

    @XmlElementRef(name = "xmlStr", namespace = "http://impl.webservice.booking.icarefx.net", type = JAXBElement.class, required = false)
    protected JAXBElement<String> xmlStr;

    /**
     * 获取xmlStr属性的值。
     *
     * @return possible object is
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public JAXBElement<String> getXmlStr() {
        return xmlStr;
    }

    /**
     * 设置xmlStr属性的值。
     *
     * @param value allowed object is
     *              {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    public void setXmlStr(JAXBElement<String> value) {
        this.xmlStr = value;
    }

}
