package com.wondersgroup.healthcloud.webservice.platform.service.impl;

import com.wondersgroup.healthcloud.entity.request.RequestMessageHeader;
import com.wondersgroup.healthcloud.entity.response.ResponseMessageHeader;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.utils.JaxbUtil;
import com.wondersgroup.healthcloud.utils.SignatureGenerator;
import com.wondersgroup.healthcloud.webservice.platform.request.RegisterRequest;
import com.wondersgroup.healthcloud.webservice.platform.response.RegisterResponse;
import com.wondersgroup.healthcloud.webservice.platform.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 * 平台注册接口
 *
 * @author ful 2018/4/14 14:35
 */
@WebService(serviceName = "RegisterService"//服务名
        , targetNamespace = "com.wondersgroup.healthcloud.webservice.platform.service"//报名倒叙，并且和接口定义保持一致
        , endpointInterface = "com.wondersgroup.healthcloud.webservice.platform.service.RegisterService")//包名
@Component
public class RegisterServicesImpl implements RegisterService {
    private static final Logger log = Logger.getLogger(RegisterServicesImpl.class);

    @Autowired
    private AccountService accountService;

    @Value("${area.platform.privateKey}")
    private String privateKey;

    private static final String RESULT_CODE_SUCC = "0";
    private static final String RESULT_CODE_ERROR = "-1";

    @Override
    public String userRegister(String xmlStr) {
        RegisterResponse registerResponse = new RegisterResponse();
        RegisterRequest registerRequest = JaxbUtil.convertToJavaBean(xmlStr, RegisterRequest.class);
        RegisterRequest.UserInfo userInfo = registerRequest.getUserInfo();

        //接口参数验证
        ResponseMessageHeader responseMessageHeader = checkReParms(registerRequest);
        if(RESULT_CODE_ERROR.equals(responseMessageHeader.getCode())){
            registerResponse.setMessageHeader(responseMessageHeader);
            return JaxbUtil.convertToXml(registerResponse, "GBK");
        }

        //本地业务处理
        RegisterResponse.Result result = new RegisterResponse.Result();
        Account account = userInfo.toAccount();
        accountService.platFormRegister(account);
        result.setPlatformUserId(userInfo.getPid());
        result.setUserId(account.getId());
        result.setUserPwd(account.getPassword());
        registerResponse.setResult(result);

        responseMessageHeader.setCode(RESULT_CODE_SUCC);
        responseMessageHeader.setDesc("微健康注册成功！");
        registerResponse.setMessageHeader(responseMessageHeader);
        return JaxbUtil.convertToXml(registerResponse, "GBK");
    }

    /**
     * 接口参数验证
     * @param registerRequest
     * @return
     */
    private ResponseMessageHeader checkReParms(RegisterRequest registerRequest) {
        ResponseMessageHeader responseMessageHeader = new ResponseMessageHeader();
        RequestMessageHeader requestMessageHeader = registerRequest.getRequestMessageHeader();
        RegisterRequest.UserInfo userInfo = registerRequest.getUserInfo();

        //验证签名
        //平台要求不做签名
        /*SignatureGenerator signatureGenerator = new SignatureGenerator();
        String localSign = signatureGenerator.generateSignature(privateKey,registerRequest);
        if(StringUtils.isEmpty(localSign) || !localSign.equals(requestMessageHeader.getSign())){
            responseMessageHeader.setCode(RESULT_CODE_ERROR);
            responseMessageHeader.setDesc("签名验证错误！");
            return responseMessageHeader;
        }*/

        //参数验证
        if (StringUtils.isEmpty(userInfo.getMobile())){
            responseMessageHeader.setCode(RESULT_CODE_ERROR);
            responseMessageHeader.setDesc("注册手机号不能为空！");
            return responseMessageHeader;
        }
        if (StringUtils.isEmpty(userInfo.getPassword())){
            responseMessageHeader.setCode(RESULT_CODE_ERROR);
            responseMessageHeader.setDesc("注册密码不能为空！");
            return responseMessageHeader;
        }
        if(accountService.getAccountByMobile(userInfo.getMobile()) != null){
            responseMessageHeader.setCode(RESULT_CODE_ERROR);
            responseMessageHeader.setDesc("手机号已在微健康注册！");
            return responseMessageHeader;
        }

        return responseMessageHeader;
    }
}
