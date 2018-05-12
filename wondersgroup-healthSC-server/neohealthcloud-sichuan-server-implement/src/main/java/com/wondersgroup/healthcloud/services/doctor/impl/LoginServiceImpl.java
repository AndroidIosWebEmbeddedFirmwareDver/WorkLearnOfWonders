package com.wondersgroup.healthcloud.services.doctor.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.im.easemob.users.EasemobAccount;
import com.wondersgroup.healthcloud.im.easemob.users.EasemobUserService;
import com.wondersgroup.healthcloud.jpa.entity.doctor.DoctorAccount;
import com.wondersgroup.healthcloud.jpa.repository.doctor.DoctorAccountRepository;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import com.wondersgroup.healthcloud.services.doctor.LoginService;
import com.wondersgroup.healthcloud.services.doctor.entity.DoctorAccountInfoAndSession;
import com.wondersgroup.healthcloud.services.doctor.entity.DoctorInfo;
import com.wondersgroup.healthcloud.services.doctor.entity.SignPlatformDoctorInfo;
import com.wondersgroup.healthcloud.services.doctor.exception.FastLoginException;
import com.wondersgroup.healthcloud.services.doctor.exception.SignPlatformAccessException;
import com.wondersgroup.healthcloud.services.sign.SignApiClient;
import com.wondersgroup.healthcloud.services.sign.SignResponse;
import com.wondersgroup.healthcloud.utils.JsonConverter;
import com.wondersgroup.healthcloud.utils.sms.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jialing.yao on 2017-6-6.
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SignApiClient signApiClient;
    @Autowired
    private DoctorAccountRepository doctorAccountRepository;
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private EasemobUserService easemobUserService;
    @Autowired
    private SessionUtil sessionUtil;

    @Override
    public DoctorAccountInfoAndSession fastLogin(String mobile, String verifyCode) {
        //校验验证码
        Boolean isAvaliable = verifyCodeService.tempCheck(mobile, verifyCode);
        if (!isAvaliable) {
            throw new FastLoginException(1002, "验证码错误.");
        }
        //获取签约医生信息
        SignResponse<SignPlatformDoctorInfo> signResponse = this.getDoctorInfoFromSignPlatform(mobile);
        SignPlatformDoctorInfo signDoctorInfo = signResponse.getData();
        //获取本地医生信息
        DoctorAccount localDoctorInfo = doctorAccountRepository.findDoctorByMobile(mobile);
        if (localDoctorInfo == null) {
            //保存在本地
            localDoctorInfo = this.saveLocalDB(signDoctorInfo, localDoctorInfo);
        }
        //合并医生信息
        DoctorInfo doctorInfo = new DoctorInfo();
        BeanUtils.copyProperties(signDoctorInfo, doctorInfo);
        BeanUtils.copyProperties(localDoctorInfo, doctorInfo);
        //创建session
        return createSessionByAccount(doctorInfo, false);
    }

    @Override
    public Session logout(String token) {
        sessionUtil.inactiveSession(token, false);
        Session session = sessionUtil.createGuest();
        return session;
    }

    private DoctorAccount saveLocalDB(SignPlatformDoctorInfo signDoctorInfo, DoctorAccount localDoctorInfo) {
        //签约平台医生信息，只落地部分数据到本地库中
        localDoctorInfo.setId(IdGen.uuid());
        localDoctorInfo.setMobile(signDoctorInfo.getMobile());
        localDoctorInfo.setName(signDoctorInfo.getName());
        localDoctorInfo.setCreateDate(new Date());
        localDoctorInfo.setUpdateDate(new Date());
        //注册医生环信账号
        this.registerEasemob(localDoctorInfo);
        return doctorAccountRepository.save(localDoctorInfo);
    }

    private SignResponse<SignPlatformDoctorInfo> getDoctorInfoFromSignPlatform(String mobile) {
        SignResponse<SignPlatformDoctorInfo> signResponse;
        try {
            Map<String, Object> input = new HashMap<>();
            input.put("phone", mobile);
            String response = signApiClient.getDoctorInfoByPhone(input);
            signResponse = JsonConverter.toObject(response, new TypeReference<SignResponse<SignPlatformDoctorInfo>>() {
            });
            //TODO 签约平台接口，手机号无效或者错误才会返回失败(待讨论)？
            if (!signResponse.isSuccessFul()) {
                throw new SignPlatformAccessException(1004, "请确认该手机号是否为签约平台医生手机号.");
            }
        } catch (Exception e) {
            log.error("签约平台->根据医生手机号获取医生信息接口异常.", e);
            throw new SignPlatformAccessException();
        }
        return signResponse;
    }

    //注册环信
    private void registerEasemob(DoctorAccount doctorAccount) {
        if (StringUtils.isBlank(doctorAccount.getTalkid())) {
            EasemobAccount easemobAccount = easemobUserService.createDoctorAccount();
            if (easemobAccount != null) {
                doctorAccount.setTalkid(easemobAccount.id);
                doctorAccount.setTalkpwd(easemobAccount.pwd);
            }
        }
    }

    //创建session
    private DoctorAccountInfoAndSession createSessionByAccount(DoctorInfo account, Boolean firstLogin) {
        Session session = sessionUtil.createDoctor(account.getUserID());
        return new DoctorAccountInfoAndSession(account, session, firstLogin);
    }
}
