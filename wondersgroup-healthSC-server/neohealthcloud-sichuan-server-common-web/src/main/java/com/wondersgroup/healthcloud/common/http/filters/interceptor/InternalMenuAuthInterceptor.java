package com.wondersgroup.healthcloud.common.http.filters.interceptor;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.servlet.AdminMenuReqUriUtil;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import com.wondersgroup.healthcloud.services.permission.BasicInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by fulong on 03/05/2018.
 */
public class InternalMenuAuthInterceptor extends HandlerInterceptorAdapter {

    private BasicInfoService basicInfoService;

    private SessionUtil sessionUtil;

    public InternalMenuAuthInterceptor(BasicInfoService basicInfoService, SessionUtil sessionUtil) {
        this.basicInfoService = basicInfoService;
        this.sessionUtil = sessionUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Session session = sessionUtil.get(request.getHeader("admin-token"), true);
        String menu_href = AdminMenuReqUriUtil.reqUri_menuHref_map.get(request.getRequestURI());

        if(!StringUtils.isEmpty(menu_href)){
            if(!basicInfoService.checkUidHasPermission(session.getUserId(), menu_href)){
                buildResponse(response, 1000, "无权限访问!");
                return false;
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
