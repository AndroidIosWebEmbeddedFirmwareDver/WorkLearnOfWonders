package com.wondersgroup.healthcloud.api.configurations;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.wondersgroup.healthcloud.common.http.exceptions.handler.DefaultExceptionHandler;
import com.wondersgroup.healthcloud.common.http.exceptions.handler.ServiceExceptionHandler;
import com.wondersgroup.healthcloud.common.http.filters.RequestWrapperFilter;
import com.wondersgroup.healthcloud.common.http.filters.interceptor.*;
import com.wondersgroup.healthcloud.common.http.support.session.AccessTokenResolver;
import com.wondersgroup.healthcloud.common.http.support.session.SessionExceptionHandler;
import com.wondersgroup.healthcloud.common.http.support.version.VersionedRequestMappingHandlerMapping;
import com.wondersgroup.healthcloud.services.account.SessionUtil;
import com.wondersgroup.healthcloud.utils.security.ReplayAttackDefender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.DispatcherType;
import java.util.List;

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
 * Created by zhangzhixiu on 15/11/19.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SessionUtil sessionService;

    @Autowired
    private ReplayAttackDefender defender;

    @Autowired
    private Environment environment;

    private String getActiveProfile() {
        String[] profiles = environment.getActiveProfiles();
        if (profiles.length != 0) {
            return profiles[0];
        } else {
            return null;
        }
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        Boolean isSandbox = "de".equals(getActiveProfile()) || "te".equals(getActiveProfile());
        RequestMappingHandlerMapping handlerMapping = new VersionedRequestMappingHandlerMapping();
        List<Object> interceptorList = Lists.newLinkedList();
        interceptorList.add(new GateInterceptor());
        if (!"de".equals(getActiveProfile())) {
            interceptorList.add(new RequestTimeInterceptor(isSandbox));
            interceptorList.add(new RequestHeaderInterceptor(isSandbox));
            interceptorList.add(new RequestReplayDefenderInterceptor(defender, isSandbox));
            interceptorList.add(new RequestAccessTokenInterceptor(sessionService, isSandbox));
            interceptorList.add(new RequestSignatureInterceptor(sessionService, isSandbox));
        }
        handlerMapping.setInterceptors(interceptorList.toArray());
        return handlerMapping;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof StringHttpMessageConverter) {
                StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charsets.UTF_8);
                stringHttpMessageConverter.setWriteAcceptCharset(false);
                converters.set(i, stringHttpMessageConverter);
                break;
            }
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AccessTokenResolver(sessionService));
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new SessionExceptionHandler());
        exceptionResolvers.add(new ServiceExceptionHandler());
        exceptionResolvers.add(new DefaultExceptionHandler());//default exception handler
    }


    @Bean
    public FilterRegistrationBean requestWrapperFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setOrder(0);
        registration.setFilter(new RequestWrapperFilter());
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }

    @Bean//etag
    public FilterRegistrationBean etagFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setOrder(1);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ShallowEtagHeaderFilter());
        return registration;
    }
}