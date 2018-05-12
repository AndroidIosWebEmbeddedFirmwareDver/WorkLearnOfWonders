package com.wondersgroup.healthcloud.services.pay.dto;

/**
 * Created by limenghua on 2017/1/12.
 */

public class CCBOrder {
    private String MERCHANTID;// 商户代码
    private String POSID;        // 商户柜台代码
    private String BRANCHID;        // 分行代码
    private String ORDERID;        // 定单号,生成的   UUID
    private String PAYMENT;        // 付款金额 1.00
    private String CURCODE;        // 币种
    private String TXCODE;        // 交易码
    private String REMARK1;        // 备注
    private String REMARK2;        // 备注
    private String TYPE;        // 	接口类型 0- 非钓鱼接口 1- 防钓鱼接口 目前该字段以银行开关为准，如果有该字段则需要传送以下字段。
    private String GATEWAY;        // 网关类型
    private String CLIENTIP;        // 客户端IP
    private String REGINFO;        // 注册信息
    private String PROINFO;        // 商品信息
    private String REFERER;        // 商户域名
    private String INSTALLNUM;        // 分期期数 信用卡支付分期期数， 一般为3、6、12等，必须为大于1的整数，当分期期数为空或无该字段上送时，则视为普通的网上支付。当分期期数为空或无该字段上送时，该字段不参与MAC校验，否则参与MAC校验。
    private String SMERID;// 			二级商户代码
    private String SMERNAME;// 		二级商户名称
    private String SMERTYPEID;// 		二级商户类别代码
    private String SMERTYPE;// 		二级商户类别名称
    private String TRADECODE;// 		交易类型代码
    private String TRADENAME;// 		交易类型名称
    private String SMEPROTYPE;// 		商品类别代码
    private String PRONAME;// 		商品类别名称
    private String THIRDAPPINFO;// 		客户端标识 商户客户端的intent-filter/schema，格式如下：comccbpay+商户代码(即MERCHANTID字段值)
    // +商户自定义的标示app的字符串商户自定义的标示app的字符串，只能为字母或数字。示例：comccbpay105320148140002alipay当该字段有值时参与MAC校验，否则不参与MAC校验。
    private String TIMEOUT;// 			订单超时时间
    private String ISSINSCODE;// 			银行代码 打开了“跨行付商户端模式”开关的商户方可上送该字段，若上送了该字段则直接跳转该字段代表的银行界面，具体见附录1。当该字段有值时参与MAC校验，否则不参与MAC校验。该字段仅对PC跨行生效，手机跨行无需上送该字段
    private String MAC;// MAC校验域 采用标准MD5算法，由商户实现

    public String getMERCHANTID() {
        return MERCHANTID;
    }

    public void setMERCHANTID(String MERCHANTID) {
        this.MERCHANTID = MERCHANTID;
    }

    public String getPOSID() {
        return POSID;
    }

    public void setPOSID(String POSID) {
        this.POSID = POSID;
    }

    public String getBRANCHID() {
        return BRANCHID;
    }

    public void setBRANCHID(String BRANCHID) {
        this.BRANCHID = BRANCHID;
    }

    public String getORDERID() {
        return ORDERID;
    }

    public void setORDERID(String ORDERID) {
        this.ORDERID = ORDERID;
    }

    public String getPAYMENT() {
        return PAYMENT;
    }

    public void setPAYMENT(String PAYMENT) {
        this.PAYMENT = PAYMENT;
    }

    public String getCURCODE() {
        return CURCODE;
    }

    public void setCURCODE(String CURCODE) {
        this.CURCODE = CURCODE;
    }

    public String getTXCODE() {
        return TXCODE;
    }

    public void setTXCODE(String TXCODE) {
        this.TXCODE = TXCODE;
    }

    public String getREMARK1() {
        return REMARK1;
    }

    public void setREMARK1(String REMARK1) {
        this.REMARK1 = REMARK1;
    }

    public String getREMARK2() {
        return REMARK2;
    }

    public void setREMARK2(String REMARK2) {
        this.REMARK2 = REMARK2;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getGATEWAY() {
        return GATEWAY;
    }

    public void setGATEWAY(String GATEWAY) {
        this.GATEWAY = GATEWAY;
    }

    public String getCLIENTIP() {
        return CLIENTIP;
    }

    public void setCLIENTIP(String CLIENTIP) {
        this.CLIENTIP = CLIENTIP;
    }

    public String getREGINFO() {
        return REGINFO;
    }

    public void setREGINFO(String REGINFO) {
        this.REGINFO = REGINFO;
    }

    public String getPROINFO() {
        return PROINFO;
    }

    public void setPROINFO(String PROINFO) {
        this.PROINFO = PROINFO;
    }

    public String getREFERER() {
        return REFERER;
    }

    public void setREFERER(String REFERER) {
        this.REFERER = REFERER;
    }

    public String getINSTALLNUM() {
        return INSTALLNUM;
    }

    public void setINSTALLNUM(String INSTALLNUM) {
        this.INSTALLNUM = INSTALLNUM;
    }

    public String getSMERID() {
        return SMERID;
    }

    public void setSMERID(String SMERID) {
        this.SMERID = SMERID;
    }

    public String getSMERNAME() {
        return SMERNAME;
    }

    public void setSMERNAME(String SMERNAME) {
        this.SMERNAME = SMERNAME;
    }

    public String getSMERTYPEID() {
        return SMERTYPEID;
    }

    public void setSMERTYPEID(String SMERTYPEID) {
        this.SMERTYPEID = SMERTYPEID;
    }

    public String getSMERTYPE() {
        return SMERTYPE;
    }

    public void setSMERTYPE(String SMERTYPE) {
        this.SMERTYPE = SMERTYPE;
    }

    public String getTRADECODE() {
        return TRADECODE;
    }

    public void setTRADECODE(String TRADECODE) {
        this.TRADECODE = TRADECODE;
    }

    public String getTRADENAME() {
        return TRADENAME;
    }

    public void setTRADENAME(String TRADENAME) {
        this.TRADENAME = TRADENAME;
    }

    public String getSMEPROTYPE() {
        return SMEPROTYPE;
    }

    public void setSMEPROTYPE(String SMEPROTYPE) {
        this.SMEPROTYPE = SMEPROTYPE;
    }

    public String getPRONAME() {
        return PRONAME;
    }

    public void setPRONAME(String PRONAME) {
        this.PRONAME = PRONAME;
    }

    public String getTHIRDAPPINFO() {
        return THIRDAPPINFO;
    }

    public void setTHIRDAPPINFO(String THIRDAPPINFO) {
        this.THIRDAPPINFO = THIRDAPPINFO;
    }

    public String getTIMEOUT() {
        return TIMEOUT;
    }

    public void setTIMEOUT(String TIMEOUT) {
        this.TIMEOUT = TIMEOUT;
    }

    public String getISSINSCODE() {
        return ISSINSCODE;
    }

    public void setISSINSCODE(String ISSINSCODE) {
        this.ISSINSCODE = ISSINSCODE;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
