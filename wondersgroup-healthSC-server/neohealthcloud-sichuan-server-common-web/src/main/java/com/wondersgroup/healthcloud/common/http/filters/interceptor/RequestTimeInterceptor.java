package com.wondersgroup.healthcloud.common.http.filters.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
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
 * Created by zhangzhixiu on 16/5/4.
 */
public final class RequestTimeInterceptor extends AbstractHeaderInterceptor {

    private static final long maxTimeDiff = 10 * 60 * 1000;

    public RequestTimeInterceptor(Boolean isSandbox) {
        this.isSandbox = isSandbox;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (skipHeaderCheck(request)) {
            return true;
        }


        String timestamp = request.getHeader("client-request-time");
        if (StringUtils.isBlank(timestamp)) {
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write("{\"code\":15,\"msg\":\"数据获取异常, 请再刷新一次\"}");
                writer.close();
            } catch (IOException e) {
                //ignore
            }
            return false;
        }
        DateTime clientTime = ISODateTimeFormat.dateTime().parseDateTime(timestamp);
        String diffFromClient = request.getHeader("time-diff");
        DateTime timeToCompare = clientTime;
        if (diffFromClient != null) {
            timeToCompare = clientTime.minus(Long.valueOf(diffFromClient));
        }
        long diff = timeToCompare.getMillis() - System.currentTimeMillis();
        if (Math.abs(diff) > maxTimeDiff) {
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(String.format("{\"code\":16,\"msg\":\"时间误差过大, 请重试\", \"time_diff\":%d}", clientTime.getMillis() - System.currentTimeMillis()));
                writer.close();
            } catch (IOException e) {
                //ignore
            }
            return false;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

//
//    public static void main(String[] args) {
//        System.out.println(System.currentTimeMillis());
//    }
}
