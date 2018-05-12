package com.wondersgroup.healthcloud.webservice.platform.test;

import com.wondersgroup.healthcloud.entity.RequestMessageHeaderUtil;
import com.wondersgroup.healthcloud.entity.request.RequestMessageHeader;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.utils.SignatureGenerator;
import com.wondersgroup.healthcloud.webservice.platform.request.RegisterRequest;
import com.wondersgroup.healthcloud.webservice.platform.response.RegisterResponse;
import com.wondersgroup.healthcloud.webservice.platform.service.RegisterService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * java类简单作用描述
 *
 * @Author: 创建者
 * @CreateDate: 2018/4/14 16:11
 * @UpdateUser: 修改者
 * @UpdateDate: 2018/4/14 16:11
 * @UpdateRemark: 修改描述
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class TestRegister {

/*

    @Autowired
    private RequestMessageHeaderUtil reqMesHeaderUtil;

    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(RegisterService.class);
        factoryBean.setAddress("http://172.16.113.103:8080/sichuan-webservice/services/soap/UserRegister?wsdl");

        RegisterRequest.UserInfo userInfo = new RegisterRequest.UserInfo();
        userInfo.setName("刘德华");
        userInfo.setIdcard("51232415425845784");
        userInfo.setPassword("5a695f0eff577680314dcd24e9f31753");
        userInfo.setBirthday("1989-05-03");
        userInfo.setMobile("13551236527");
        userInfo.setPid("111");

        SignatureGenerator signatureGenerator = new SignatureGenerator();
        RegisterRequest registerRequest = new RegisterRequest();
        RequestMessageHeader requestMessageHeader = new RequestMessageHeader("wdyy", "GBK", "MD5", null);
        registerRequest.requestMessageHeader = requestMessageHeader;
        registerRequest.setUserInfo(userInfo);
        registerRequest.getRequestMessageHeader().setSign(signatureGenerator.generateSignature("wdyy",registerRequest));

        RegisterService registerService = (RegisterService)factoryBean.create();
        String xml = registerService.userRegister(JaxbUtil.convertToXml(registerRequest, "GBK"));
        System.out.println("返回xml：" + xml);

        //参数转换对象
        RegisterResponse registerResponse = JaxbUtil.convertToJavaBean(xml, RegisterResponse.class);
        RegisterResponse.Result result = registerResponse.getResult();
        System.out.println("获取到微健康系统ID："+result.getUserId());


    }*/
}
