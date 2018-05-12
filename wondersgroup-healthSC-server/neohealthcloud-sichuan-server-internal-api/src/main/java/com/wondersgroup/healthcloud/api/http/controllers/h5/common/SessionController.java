package com.wondersgroup.healthcloud.api.http.controllers.h5.common;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by longshasha on 16/5/31.
 */
@RestController("H5SessionController")
public class SessionController {

    @Autowired
    private SessionUtil sessionUtil;

    @GetMapping("/h5/internal/session")
    public JsonResponseEntity<Session> get(@RequestParam String token) {
        JsonResponseEntity<Session> response = new JsonResponseEntity<>();
        Session session = sessionUtil.get(token, false);
        if (session == null) {
            response.setCode(1001);
        } else {
            if (session.getIsValid()) {
                response.setData(session);
            } else {
                response.setCode(1001);
            }
        }
        return response;
    }
}
