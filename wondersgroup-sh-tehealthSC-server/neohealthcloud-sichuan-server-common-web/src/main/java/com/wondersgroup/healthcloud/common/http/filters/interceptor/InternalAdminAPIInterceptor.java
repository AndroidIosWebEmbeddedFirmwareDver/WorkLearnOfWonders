package com.wondersgroup.healthcloud.common.http.filters.interceptor;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p>
 * Created by zhangzhixiu on 27/10/2016.
 */
public class InternalAdminAPIInterceptor extends HandlerInterceptorAdapter {

    private SessionUtil sessionUtil;

    public InternalAdminAPIInterceptor(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            if (hm.getBeanType().getAnnotation(Admin.class) != null || hm.getMethodAnnotation(Admin.class) != null) {
                if (request.getHeader("admin-token") == null || request.getHeader("userId") == null) {
                    buildResponse(response, -1000, "请登录后操作!");
                    return false;
                }
                Session session = sessionUtil.get(request.getHeader("admin-token"), true);
                if (!(session.getIsAdmin() && session.getIsValid() && session.getUserId().equals(request.getHeader("userId")))) {
                    buildResponse(response, -1000, "请登录后操作!");
                    return false;
                }
            }
        }
        return true;
    }

    private void buildResponse(HttpServletResponse response, int code, String msg) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(String.format("{\"code\":%d,\"msg\":\"%s\"}", code, msg));
            writer.close();
        } catch (IOException e) {
            //ignore
        }
    }
}
