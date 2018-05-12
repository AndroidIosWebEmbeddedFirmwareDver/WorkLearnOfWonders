package com.wondersgroup.healthcloud.familydoctor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.wondersgroup.healthcloud.common.utils.JsonMapper;
import com.wondersgroup.healthcloud.familydoctor.response.CommonListResponse;
import com.wondersgroup.healthcloud.familydoctor.response.Response;
import com.wondersgroup.healthcloud.familydoctor.response.exception.GetContractResidentException;
import com.wondersgroup.healthcloud.familydoctor.response.exception.LoginByPhoneException;
import com.wondersgroup.healthcloud.familydoctor.response.sign.ContractResidentResponse;
import com.wondersgroup.healthcloud.familydoctor.response.sign.LoginByPhoneResponse;
import com.wondersgroup.healthcloud.familydoctor.response.sign.MyTeamNumResponse;
import com.wondersgroup.healthcloud.familydoctor.services.sign.QianyueImplService;
import com.wondersgroup.healthcloud.familydoctor.services.sign.QianyueInterface;
import org.junit.Ignore;
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
    private static Map<String, Object> header = Maps.newHashMap();

    static {
        URL wsdlURL = null;
        try {
            wsdlURL = new URL(SIGN_WSDL_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        QianyueImplService signImplService = new QianyueImplService(wsdlURL);
        signService = signImplService.getQianyueImplPort();
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

    /**
     * 个人统计接口（团队签约数、我所在团队数）
     */
    @Test
    public void testGetMyTeamList() throws Exception {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> request = Maps.newHashMap();
        request.put("zcjgdm", "注册厂商代码");
        request.put("zcjgmc", "注册厂商名称");
        request.put("bdjgdm", "510000002122");
        request.put("bdjgmc", "绑定机构名称");
        request.put("bdyydm", "绑定应用代码");
        request.put("bdyymc", "绑定应用名称");
        request.put("jkdm", "CDJTYSQY0002");
        request.put("jkmc", "接口名称");
        request.put("username", "admin");
        request.put("password", "111111");
        request.put("bdczxtdm", "绑定操作系统代码");
        request.put("bdczxtmc", "绑定操作系统名称");

        Map<String, Object> reqData = Maps.newHashMap();
        reqData.put("idCardNo", "51025358983699");
        reqData.put("user_orgCode", "510000002122");
        String response = signService.getMyTeamNum(jsonMapper.toJson(request), jsonMapper.toJson(reqData));
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
        String response = signService.getMyTeamNum(jsonMapper.toJson(header), jsonMapper.toJson(reqData));
        System.out.println("result: " + response);
        Response<MyTeamNumResponse> retData = jsonMapper.fromJson(response, new TypeReference<Response<MyTeamNumResponse>>() {
        });
        System.out.println("isSuccessFul: " + retData.isSuccessFul() + "  message: " + retData.getMessage());
        System.out.println("data: " + retData.getData().toString());
    }

    /**
     * 根据医生手机号获取医生信息接口
     *
     * @throws Exception
     */
    @Test
    @Ignore
    public void testLoginByPhone() throws Exception {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqData = Maps.newHashMap();
        reqData.put("phone", "13816207362");
        String response = signService.loginByPhone(jsonMapper.toJson(header), jsonMapper.toJson(reqData));
        Response<LoginByPhoneResponse> retData = jsonMapper.fromJson(response, new TypeReference<Response<LoginByPhoneResponse>>() {
        });
        System.out.println("isSuccessFul: " + retData.isSuccessFul() + "  message: " + retData.getMessage());
        if (!retData.isSuccessFul()) {
            throw new LoginByPhoneException("response code:" + retData.getResultCode() + " message:" + retData.getMessage());
        }
        System.out.println("data: " + retData.getData().toString());
    }

    /**
     * 签约患者列表(全部/重点/贫困)
     *
     * @throws Exception
     */
    @Test
    @Ignore
    public void testGetContractResident() throws Exception {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqData = Maps.newHashMap();
        reqData.put("user_orgCode", "877ff477h441");//登录医生所在医疗机构代码
        reqData.put("dlyhsfzh", "512501197203035172");//登录医生的身份证号码
        reqData.put("name", "");//居民姓名
        reqData.put("sfzdrk", "0");//是否重点人口
        reqData.put("sfpkrk", "0");//是否贫困人口
        reqData.put("pageNo", 1);
        reqData.put("pageSize", 10);
        String response = signService.getContractResident(jsonMapper.toJson(header), jsonMapper.toJson(reqData));
        Response<CommonListResponse<ContractResidentResponse>> retData = jsonMapper.fromJson(response, new TypeReference<Response<CommonListResponse<ContractResidentResponse>>>() {
        });
        System.out.println("isSuccessFul: " + retData.isSuccessFul() + "  message: " + retData.getMessage());
        if (!retData.isSuccessFul()) {
            throw new GetContractResidentException("response code:" + retData.getResultCode() + " message:" + retData.getMessage());
        }
        System.out.println("data: " + retData.getData().toString());
    }
}
