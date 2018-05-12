package com.wondersgroup.healthcloud.api.http.controllers.doctor;

import com.wondersgroup.healthcloud.api.http.dto.doctor.DoctorAccountAndSessionDTO;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.misc.SessionDTO;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import com.wondersgroup.healthcloud.services.doctor.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jialing.yao on 2017-6-6.
 */
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @WithoutToken
    @RequestMapping(value = "/fastLogin", method = RequestMethod.GET)
    @VersionRange
    public JsonResponseEntity<DoctorAccountAndSessionDTO> fastLogin(
            @RequestParam String mobile,
            @RequestParam String code) {
        JsonResponseEntity<DoctorAccountAndSessionDTO> body = new JsonResponseEntity<>();
        body.setData(new DoctorAccountAndSessionDTO(loginService.fastLogin(mobile, code)));
        body.setMsg("登录成功");
        return body;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    @VersionRange
    public JsonResponseEntity<SessionDTO> logout(@RequestHeader("access-token") String token) {
        Session session = loginService.logout(token);
        JsonResponseEntity<SessionDTO> response = new JsonResponseEntity<>();
        response.setData(new SessionDTO(session));
        response.setMsg("退出登录成功");
        return response;
    }
}
