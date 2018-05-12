package com.wondersgroup.healthcloud.familydoctor;

import com.google.common.collect.Maps;
import com.wondersgroup.healthcloud.common.utils.JsonMapper;
import com.wondersgroup.healthcloud.familydoctor.services.referral.ZhuanzhenImplService;
import com.wondersgroup.healthcloud.familydoctor.services.referral.ZhuanzhenInterface;
import org.junit.Ignore;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 转诊测试
 * Created by jialing.yao on 2017-6-22.
 */
public class ReferralTests {
    /*private static String SIGN_WSDL_URL="http://218.89.178.119:8088/fjzl/webservice/qianyue?wsdl";
    private static String REFERRAL_WSDL_URL="http://218.89.178.119:8088/fjzl/webservice/zhuanzhen?wsdl";
    private static ZhuanzhenInterface referralService;
    private static Map<String,Object> header = Maps.newHashMap();
    static {
        URL wsdlURL = null;
        try {
            wsdlURL = new URL(SIGN_WSDL_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ZhuanzhenImplService referralImplService=new ZhuanzhenImplService(wsdlURL);
        referralService=referralImplService.getZhuanzhenImplPort();
        header.put("zcjgdm", "注册厂商代码");
        header.put("zcjgmc", "注册厂商名称");
        header.put("bdjgdm", "510000002122");
        header.put("bdjgmc", "绑定机构名称");
        header.put("bdyydm", "绑定应用代码");
        header.put("bdyymc", "绑定应用名称");
        header.put("jkdm", "接口代码");
        header.put("jkmc", "接口名称");
        header.put("username", "admin");
        header.put("password", "111111");
        header.put("bdczxtdm", "绑定操作系统代码");
        header.put("bdczxtmc", "绑定操作系统名称");
    }
    *//**
     * 转诊列表(全部/申请中/已驳回/已转诊/已取消)
     *//*
    @Test
    @Ignore
    public void testOutpatientList() throws Exception{
        JsonMapper jsonMapper= JsonMapper.nonDefaultMapper();
        Map<String,Object> reqData=Maps.newHashMap();
        reqData.put("mzzz_orgCode","877ff477h441");//转诊申请机构代码
        reqData.put("mzzz_yssfzh","512501197203035172");//转诊医生身份证号码
        reqData.put("mzzz_hzsfzh","");//转诊患者身份证号码
        reqData.put("mzzz_hzxm","");//转诊患者姓名
        reqData.put("mzzz_state","");//转诊申请单状态(全部/申请中/已驳回/已转诊/已取消)
        reqData.put("pageNo",1);
        reqData.put("pageSize",10);
        //TODO WSDL中无此接口
        //String request=referralService.getContractResident(jsonMapper.toJson(header), jsonMapper.toJson(reqData));

    }
    *//**
     * 转诊详情(申请中/已驳回/已转诊/已取消)
     *//*
    @Test
    @Ignore
    public void testOutpatientDetail() throws Exception{
        JsonMapper jsonMapper= JsonMapper.nonDefaultMapper();
        Map<String,Object> reqData=Maps.newHashMap();
        reqData.put("mzzz_orgCode","877ff477h441");//???接口中缺少字段
        reqData.put("referral_code","877ff477h441");
        //接口返回数据有很大问题
    }
    *//**
     * 门诊接入转诊
     *//*
    @Test
    @Ignore
    public void testOutpatientReceive() throws Exception{
        JsonMapper jsonMapper= JsonMapper.nonDefaultMapper();
        Map<String,Object> reqData=Maps.newHashMap();
        reqData.put("mzzz_orgCode","877ff477h441");//???接口中缺少字段
        reqData.put("referral_code","877ff477h441");
        //mzzz_user 操作用户身份证 ????
        //mzzz_user_name 操作用户名  ????
    }*/
}
