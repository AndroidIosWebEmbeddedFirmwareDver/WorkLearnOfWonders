package com.wondersgroup.healthcloud.webservice.platform.request;

import com.wondersgroup.healthcloud.common.utils.DateUtils;
import com.wondersgroup.healthcloud.entity.request.BaseRequest;
import com.wondersgroup.healthcloud.jpa.entity.user.Account;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.ParseException;

/**
 * 平台注册系统接口请求参数
 */
@Data
@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisterRequest extends BaseRequest {

    @XmlElement(name = "UserInfo")
    public UserInfo userInfo;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UserInfo {
        private String name;//姓名
        private String idcard;//身份证号
        private String mobile;//电话
        private String password;//密码
        private String gender;//性别
        private String birthday;//出生日期
        private String pid;//平台注册ID

        public Account toAccount(){
            Account account = new Account();
            account.setName(this.name);
            account.setIdcard(this.idcard);
            account.setMobile(this.mobile);
            account.setPassword(this.password);
            account.setGender(this.gender);
            account.setVerificationLevel(0);//未实名认证
            account.setOrigin("9");//平台注册标记
            account.setPlatformUserId(this.pid);
            try {
                account.setBirthday(DateUtils.sdf_day.parse(this.birthday));
            } catch (ParseException e) {
            }
            return account;
        }
    }
}
