package com.wondersgroup.healthcloud.services.familyDoctor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.wondersgroup.healthcloud.common.utils.JsonMapper;
import com.wondersgroup.healthcloud.familydoctor.response.Response;
import com.wondersgroup.healthcloud.familydoctor.response.sign.MyTeamNumResponse;
import com.wondersgroup.healthcloud.familydoctor.services.sign.QianyueImplService;
import com.wondersgroup.healthcloud.familydoctor.services.sign.QianyueInterface;
import com.wondersgroup.healthcloud.services.familyDoctor.utils.FamilyDoctorUtil;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 签约测试
 * Created by jialing.yao on 2017-6-22.
 */
public class SignTests {
    private static String SIGN_WSDL_URL = "http://218.89.178.119:8088/fjzl/webservice/qianyue?wsdl";
    private static String REFERRAL_WSDL_URL = "http://218.89.178.119:8088/fjzl/webservice/zhuanzhen?wsdl";
    private static QianyueInterface signService;

    static {
        URL wsdlURL = null;
        try {
            wsdlURL = new URL(SIGN_WSDL_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        QianyueImplService signImplService = new QianyueImplService(wsdlURL);
        signService = signImplService.getQianyueImplPort();
    }

    /**
     * 获取团队列表
     */
    @Test
    public void testGetNearbyTeamList() throws Exception {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqData = Maps.newHashMap();
        reqData.put("longitude", 30.680045);
        reqData.put("latitude", "104.06786");
        reqData.put("distance", 303);
        reqData.put("pageNo", 1);
        reqData.put("pageSize", 10);
        reqData.put("jmxxsfzh", "512501197203035172");
        String response = signService.getMyTeamList(FamilyDoctorUtil.getRequestHeaderForJson(), jsonMapper.toJson(reqData));
        System.out.println("result: " + response);
        Response<MyTeamNumResponse> retData = jsonMapper.fromJson(response, new TypeReference<Response<MyTeamNumResponse>>() {
        });
        System.out.println("isSuccessFul: " + retData.isSuccessFul() + "  message: " + retData.getMessage());
        System.out.println("data: " + retData.getData().toString());
    }

    /**
     * 我的团队列表
     */
    @Test
    public void testGetMyTeamList() throws Exception {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqData = Maps.newHashMap();
        reqData.put("idCardNo", "51025358983699");
        reqData.put("orgCode", "877ff477h441");
        reqData.put("pageNo", 1);
        reqData.put("pageSize", 10);
        String response = signService.getMyTeamList(FamilyDoctorUtil.getRequestHeaderForJson(), jsonMapper.toJson(reqData));
        System.out.println("result: " + response);
        Response<MyTeamNumResponse> retData = jsonMapper.fromJson(response, new TypeReference<Response<MyTeamNumResponse>>() {
        });
        System.out.println("isSuccessFul: " + retData.isSuccessFul() + "  message: " + retData.getMessage());
        System.out.println("data: " + retData.getData().toString());
    }

    /**
     * 个人统计接口（团队签约数、我所在团队数）
     */
    @Test
    public void testGetMyTeamNum() throws Exception {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqData = Maps.newHashMap();
        reqData.put("idCardNo", "51025358983699");
        reqData.put("user_orgCode", "510000002122");
        String response = signService.getMyTeamNum(FamilyDoctorUtil.getRequestHeaderForJson(), jsonMapper.toJson(reqData));
        System.out.println("result: " + response);
        Response<MyTeamNumResponse> retData = jsonMapper.fromJson(response, new TypeReference<Response<MyTeamNumResponse>>() {
        });
        System.out.println("isSuccessFul: " + retData.isSuccessFul() + "  message: " + retData.getMessage());
        System.out.println("data: " + retData.getData().toString());
    }
}
