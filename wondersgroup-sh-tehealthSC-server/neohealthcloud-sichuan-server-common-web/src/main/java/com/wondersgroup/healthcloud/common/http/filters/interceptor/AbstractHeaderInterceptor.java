package com.wondersgroup.healthcloud.common.http.filters.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;

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
 * Created by zhangzhixiu on 16/4/27.
 */
public abstract class AbstractHeaderInterceptor implements HandlerInterceptor {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractHeaderInterceptor.class);

    public static class HeaderCode {
        public String headerKey;
        int errorCode;

        HeaderCode(String headerKey, int errorCode) {
            this.headerKey = headerKey;
            this.errorCode = errorCode;
        }
    }

    protected Boolean isSandbox = false;

    public static final HeaderCode[] headers = new HeaderCode[]{
            new HeaderCode("app-version", 8),
            new HeaderCode("channel", 3),
            new HeaderCode("device", 2),
            new HeaderCode("main-area", 18),
            new HeaderCode("model", 5),
            new HeaderCode("os-version", 6),
            new HeaderCode("platform", 4),
            new HeaderCode("screen-height", 7),
            new HeaderCode("screen-width", 7),
            new HeaderCode("spec-area", 18),
            new HeaderCode("version", 1)
    };

    public static final HeaderCode[] otherHeaders = new HeaderCode[]{
            new HeaderCode("request-id", 14),
            new HeaderCode("client-request-time", 15),
            new HeaderCode("signature", 20)
    };

    protected Boolean skipHeaderCheck(HttpServletRequest request) {
        return isSandbox && "true".equals(request.getHeader("header-check-skip"));
    }
}
