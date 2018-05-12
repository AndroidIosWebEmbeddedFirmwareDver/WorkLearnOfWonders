package com.wondersgroup.healthcloud.common.http.exceptions.handler;

import com.google.common.base.Charsets;
import com.wondersgroup.healthcloud.common.http.exceptions.ErrorMessage;
import com.wondersgroup.healthcloud.common.http.support.misc.JsonModelAndViewBuilder;
import com.wondersgroup.healthcloud.exceptions.Exceptions;
import okio.Okio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
 * Created by zhangzhixiu on 15/11/26.
 */
public final class DefaultExceptionHandler implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger("exlog");

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            response.setStatus(400);
            MissingServletRequestParameterException msrpe = (MissingServletRequestParameterException) ex;
            return JsonModelAndViewBuilder.build(new ErrorMessage(String.format("[%s]字段不能为空", msrpe.getParameterName())));
        } else if (ex instanceof NoHandlerFoundException) {
            response.setStatus(404);
            return JsonModelAndViewBuilder.build(new ErrorMessage(404, "404NotFound"));
        } else {
            response.setStatus(500);
            logger.info("request-id=" + request.getHeader("request-id"));
            logger.info(request.getMethod() + request.getServletPath());
            logger.info(request.getQueryString());
            try {
                logger.info(Okio.buffer(Okio.source(request.getInputStream())).readString(Charsets.UTF_8));
            } catch (IOException e) {
                //ignore
            }
            logger.info(Exceptions.getStackTraceAsString(ex));
            return JsonModelAndViewBuilder.build(new ErrorMessage("内部错误"));
        }
    }
}
