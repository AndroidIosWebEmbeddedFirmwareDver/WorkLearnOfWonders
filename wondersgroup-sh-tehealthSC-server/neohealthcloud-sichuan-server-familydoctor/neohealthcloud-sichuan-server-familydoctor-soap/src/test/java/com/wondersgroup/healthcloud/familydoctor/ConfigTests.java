package com.wondersgroup.healthcloud.familydoctor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.wondersgroup.healthcloud.common.utils.JsonMapper;
import com.wondersgroup.healthcloud.familydoctor.config.RequestHeadBuilder;
import com.wondersgroup.healthcloud.familydoctor.response.Response;
import com.wondersgroup.healthcloud.familydoctor.response.sign.MyTeamNumResponse;
import com.wondersgroup.healthcloud.familydoctor.response.sign.SignTeamListResponse;
import com.wondersgroup.healthcloud.familydoctor.services.sign.GetMyTeamList;
import com.wondersgroup.healthcloud.familydoctor.services.sign.GetMyTeamListResponse;
import com.wondersgroup.healthcloud.familydoctor.services.sign.QianyueInterface;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * soap auto config test
 * Created by jialing.yao on 2017-6-22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigTests.BootstrapTests.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigTests {
    @Autowired
    private QianyueInterface signService;
    @Autowired
    private RequestHeadBuilder requestHeadBuilder;

    @Test
    //@Ignore
    public void testGetMyTeamNum() {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqData = Maps.newHashMap();
        reqData.put("idCardNo", "51025358983699");
        reqData.put("user_orgCode", "510000002122");
        String response = signService.getMyTeamNum(requestHeadBuilder.build(jsonMapper), jsonMapper.toJson(reqData));
        System.out.println("result: " + response);
        Response<MyTeamNumResponse> retData = jsonMapper.fromJson(response, new TypeReference<Response<MyTeamNumResponse>>() {
        });
        System.out.println("isSuccessFul: " + retData.isSuccessFul() + "  message: " + retData.getMessage());
        System.out.println("data: " + retData.getData().toString());
    }

    @Test
    @Ignore
    public void testGetMyTeamList() {
        JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
        Map<String, Object> reqData = Maps.newHashMap();
        reqData.put("longitude", "30.680045");
        reqData.put("latitude", "104.06786");
        reqData.put("pageNo", 1);
        reqData.put("pageSize", 10);
        String response = signService.getMyTeamList(requestHeadBuilder.build(jsonMapper), jsonMapper.toJson(reqData));
        System.out.println("result: " + response);
        Response<SignTeamListResponse> retData = jsonMapper.fromJson(response, new TypeReference<Response<SignTeamListResponse>>() {
        });
        System.out.println("isSuccessFul: " + retData.isSuccessFul() + "  message: " + retData.getMessage());
        System.out.println("data: " + retData.getData().toString());
    }

    @SpringBootApplication
    @ComponentScan("com.wondersgroup.healthcloud.familydoctor")
    static class BootstrapTests {
    }
}
