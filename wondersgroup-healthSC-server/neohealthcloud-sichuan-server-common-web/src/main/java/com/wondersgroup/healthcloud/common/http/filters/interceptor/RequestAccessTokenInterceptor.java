package com.wondersgroup.healthcloud.common.http.filters.interceptor;

import com.wondersgroup.common.http.utils.JsonConverter;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.exceptions.ErrorMessageSelector;
import com.wondersgroup.healthcloud.common.http.servlet.ServletAttributeCacheUtil;
import com.wondersgroup.healthcloud.common.http.support.misc.SessionDTO;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
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
 * <p/>
 * Created by zhangzhixiu on 16/2/22.
 */
public final class RequestAccessTokenInterceptor extends AbstractHeaderInterceptor {

    private static final String accesstokenHeader = "access-token";

    private SessionUtil sessionUtil;

    public RequestAccessTokenInterceptor(SessionUtil sessionUtil, Boolean isSandbox) {
        this.isSandbox = isSandbox;
        this.sessionUtil = sessionUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (skipHeaderCheck(request)) {
            return true;
        }

        int code;
        String message;
        String token = request.getHeader(accesstokenHeader);
        if (StringUtils.isBlank(token)) {
            code = 10;
            message = ErrorMessageSelector.getOne();
        } else {//token!=null
            Session session = ServletAttributeCacheUtil.getSession(request, sessionUtil);
            if (session == null) {
                code = 12;
                message = "登录凭证过期, 请重新登录";
            } else if (!session.getIsValid()) {
                code = 13;
                message = "账户在其他设备登录, 请重新登录";
            } else if (session.isGuest()) {
                code = 1000;
                message = "请登录";
            } else {
                return true;
            }
        }

        boolean excluded = false;
        if (o instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) o;
            excluded = hm.getMethodAnnotation(WithoutToken.class) != null;
        }

        if (!excluded || code == 12 || code == 13) {
            buildGuestResponseBody(response, code, message);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void buildGuestResponseBody(HttpServletResponse response, int code, String msg) {//return a guest token when token check failed.
        try {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            JsonResponseEntity<SessionDTO> result = new JsonResponseEntity<>();
            result.setCode(code);
            result.setMsg(msg);
            result.setData(new SessionDTO(sessionUtil.createGuest()));
            writer.write(JsonConverter.toJson(result));
            writer.close();
        } catch (IOException e) {
            //ignore
        }
    }
}