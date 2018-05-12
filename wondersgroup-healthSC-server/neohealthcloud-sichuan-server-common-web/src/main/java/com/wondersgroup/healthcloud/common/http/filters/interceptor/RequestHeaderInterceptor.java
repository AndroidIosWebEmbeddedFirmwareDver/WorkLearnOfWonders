package com.wondersgroup.healthcloud.common.http.filters.interceptor;

import org.springframework.web.servlet.ModelAndView;

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
 * Created by zhangzhixiu on 16/4/20.
 */
public final class RequestHeaderInterceptor extends AbstractHeaderInterceptor {

    private static final String defaultErrorMsg = "数据获取异常, 请再刷新一次";

    public RequestHeaderInterceptor(Boolean isSandbox) {
        this.isSandbox = isSandbox;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (skipHeaderCheck(request)) {
            return true;
        }

        for (HeaderCode header : headers) {
            if (!checkOne(request, header)) {
                buildResponse(response, header);
                return false;
            }
        }
        return true;
    }

    private Boolean checkOne(HttpServletRequest request, HeaderCode headerToCheck) {
        String headerValue = request.getHeader(headerToCheck.headerKey);
        return headerValue != null;
    }

    private void buildResponse(HttpServletResponse response, HeaderCode headerChecked) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(String.format("{\"code\":%d,\"msg\":\"%s\"}", headerChecked.errorCode, defaultErrorMsg));
            writer.close();
        } catch (IOException e) {
            //ignore
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
