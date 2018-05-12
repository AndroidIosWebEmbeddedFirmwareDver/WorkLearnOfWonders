package com.wondersgroup.healthcloud.common.http.filters.interceptor;

import com.google.common.base.Charsets;
import com.wondersgroup.healthcloud.common.http.servlet.ServletRequestIPAddressUtil;
import okio.Okio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Created by zhangzhixiu on 8/21/16.
 */
public class InternalGateInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("gatelog");
    private static final String requestStartTimeAttributeKey = "request_start";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long start = System.currentTimeMillis();
        request.setAttribute(requestStartTimeAttributeKey, start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long end = System.currentTimeMillis();
        Long start = (Long) request.getAttribute(requestStartTimeAttributeKey);

        String method = request.getMethod();
        String URI = request.getServletPath();

        String remoteIp = ServletRequestIPAddressUtil.parse(request);

        Long durantion = end - start;

        StringBuilder sb = new StringBuilder(512);
        sb.append(method);
        sb.append(URI);
        sb.append(" ");
        sb.append(remoteIp);
        sb.append(" ");
        sb.append(durantion);
        sb.append(" ");
        sb.append(response.getStatus());
        sb.append(" ");
        sb.append(request.getQueryString());
        sb.append(" ");
        sb.append(Okio.buffer(Okio.source(request.getInputStream())).readString(Charsets.UTF_8));

        logger.info(sb.toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
