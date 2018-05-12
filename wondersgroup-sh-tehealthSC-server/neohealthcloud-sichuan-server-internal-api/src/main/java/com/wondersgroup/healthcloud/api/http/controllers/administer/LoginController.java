package com.wondersgroup.healthcloud.api.http.controllers.administer;

import com.wondersgroup.healthcloud.api.http.dto.login.LoginConfigure;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonKeyReader;
import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.permission.User;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import com.wondersgroup.healthcloud.services.permission.BasicInfoService;
import com.wondersgroup.healthcloud.services.permission.dto.MenuDTO;
import com.wondersgroup.healthcloud.utils.CityCodeNameConverter;
import com.wondersgroup.healthcloud.utils.security.RSA;
import com.wondersgroup.healthcloud.utils.security.RSAKey;
import com.wondersgroup.healthcloud.utils.sms.VerifyCodeService;
import okio.ByteString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaozhenxing on 2016/8/10.
 */
@RestController
@RequestMapping(value = "/api")
public class LoginController {

    @Autowired
    private BasicInfoService basicInfoService;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private LoginConfigure loginConfigure;

    @Autowired
    private VerifyCodeService service;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResponseEntity logout(@RequestHeader("admin-token") String token) {
        sessionUtil.inactiveSession(token, true);
        JsonResponseEntity result = new JsonResponseEntity();
        result.setMsg("账号退出成功！");
        return result;
    }

    /**
     * 登录获取系统参数
     * @return
     */
    @RequestMapping(value = "/loginConfigure", method = RequestMethod.GET)
    public JsonResponseEntity loginConfigure() {
        JsonResponseEntity result = new JsonResponseEntity();
        result.setData(loginConfigure);
        return result;
    }

    /**
     * 向管理员发送短信验证码
     * @return
     */
    @RequestMapping(value = "/sendSMSToSuper", method = RequestMethod.POST)
    public JsonResponseEntity sendSMSToSuper(@RequestBody String body) {
        JsonKeyReader reader = new JsonKeyReader(body);
        String loginname = reader.readString("loginname", false);
        String password = RSA.decryptByPrivateKey(reader.readString("password", false), RSAKey.adminPrivateKey);
        User loginUser = basicInfoService.checkLoginUser(loginname, password);
        if(loginUser == null){
            return new JsonResponseEntity(1000, "用户名或密码错误");
        }

        if (loginConfigure.getSuperPhone().length() != 11 || !loginConfigure.getSuperPhone().startsWith("1")
                || !StringUtils.isNumeric(loginConfigure.getSuperPhone())) {
            return new JsonResponseEntity(1000, "超级管理员联系电话配置错误");
        }

        JsonResponseEntity<String> response = new JsonResponseEntity<>();
        service.sendCode(loginConfigure.getSuperPhone(), "%s，为用户【"+loginUser.getUsername()+"】当前后台系统登录验证码，请您及时告知对方，并在15分钟内让其完成输入。详询客服400-900-9957.[万达健康]", null);
        response.setMsg("短信发送成功,请您联系超级管理员<"+loginConfigure.getSuperPhone()+">获取短信验证码，并在15分钟内完成输入");
        return response;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponseEntity login(@RequestBody String body) {
        JsonResponseEntity result = new JsonResponseEntity();

        JsonKeyReader reader = new JsonKeyReader(body);
        String loginname = reader.readString("loginname", false);
        String password = RSA.decryptByPrivateKey(reader.readString("password", false), RSAKey.adminPrivateKey);

        //短信验证码验证
        if(loginConfigure.isSMSVerification()){
            String code = reader.readString("textmessage", false);
            if(StringUtils.isEmpty(code))
                return new JsonResponseEntity(1000, "短信验证码不能为空");
            if(!service.tempCheck(loginConfigure.getSuperPhone(), code))
                return new JsonResponseEntity(1000, "短信验证码错误");
        }

        Session session = basicInfoService.adminLogin(loginname, password);
        User user = (User) session.getUser();

        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", session.getAccessToken());
        if (user.getSpecArea() != null) {
            map.put("specArea", user.getSpecArea());
        }
        map.put("userId", user.getUserId());
        map.put("loginName", user.getLoginname());
        map.put("userName", user.getUsername());
        MenuDTO menu = basicInfoService.findUserMunuPermission(user.getUserId());
        if (menu != null) {
            map.put("menu", menu);
        }
        result.setData(map);
        return result;
    }

}
