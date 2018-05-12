package com.wondersgroup.healthcloud.services.doctor;

import com.wondersgroup.healthcloud.services.account.dto.Session;
import com.wondersgroup.healthcloud.services.doctor.entity.DoctorAccountInfoAndSession;

/**
 * Created by jialing.yao on 2017-6-7.
 */
public interface LoginService {

    /**
     * 快速登录
     *
     * @param mobile     手机号
     * @param verifyCode 校验码
     * @return 医生信息和session
     */
    DoctorAccountInfoAndSession fastLogin(String mobile, String verifyCode);

    /**
     * 退出登录
     *
     * @param token
     */
    Session logout(String token);
}
