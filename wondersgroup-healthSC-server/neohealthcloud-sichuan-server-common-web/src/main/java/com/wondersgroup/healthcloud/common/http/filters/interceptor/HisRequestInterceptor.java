package com.wondersgroup.healthcloud.common.http.filters.interceptor;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhaozhenxing on 2016/11/7.
 */
public class HisRequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            if (hm.getBeanType().getAnnotation(Admin.class) != null || hm.getMethodAnnotation(Admin.class) != null) {
                if (request.getHeader("main-area") == null) {
                    buildResponse(response, -1000, "需要指定main-area");
                    return false;
                }
                if (request.getHeader("userId") == null) {
                    buildResponse(response, -1000, "请登录后操作!");
                    return false;
                }
            }
        }
        return true;
    }

    private void buildResponse(HttpServletResponse response, int code, String msg) {
        try {
            response.setContentType("application/xml;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(String.format("<request><code>%d</code><msg>%s</msg></request>", code, msg));
            writer.close();
        } catch (IOException e) {
            //ignore
        }
    }
}
