package com.wondersgroup.healthcloud.config;

import com.wondersgroup.healthcloud.config.client.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.config.annotation.WsConfigurationSupport;


/**
 * Created by nick on 2016/10/29.
 *
 * @author nick
 * this is web services configuration
 */
@Configuration
public class WebServicesConfig extends WsConfigurationSupport {

    @Autowired
    private Environment environment;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.wondersgroup.healthcloud.wsdl");
        return marshaller;
    }

    @Bean
    public OrderInfoNumClient orderInfoNumClient(Jaxb2Marshaller marshaller) {
        return (OrderInfoNumClient) init(new OrderInfoNumClient(), marshaller);
    }

    /**
     * 一级科室
     *
     * @param marshaller
     * @return
     */
    @Bean
    public GetTopDeptInfoClient getTopDeptInfoClient(Jaxb2Marshaller marshaller) {
        return (GetTopDeptInfoClient) init(new GetTopDeptInfoClient(), marshaller);
    }

    /**
     * 二级科室
     *
     * @param marshaller
     * @return
     */
    @Bean
    public GetTwoDepInfoClient getTwoDepInfoClient(Jaxb2Marshaller marshaller) {
        return (GetTwoDepInfoClient) init(new GetTwoDepInfoClient(), marshaller);
    }

    @Bean
    public OrderClient orderClient(Jaxb2Marshaller marshaller) {
        return (OrderClient) init(new OrderClient(), marshaller);
    }

    @Bean
    public PlatformRegisterClient platformRegisterClient(Jaxb2Marshaller marshaller) {
        return (PlatformRegisterClient) init(new PlatformRegisterClient(), marshaller);
    }

    private WebServiceGatewaySupport init(WebServiceGatewaySupport wsgs, Jaxb2Marshaller marshaller) {
        wsgs.setDefaultUri(environment.getProperty("area.platform.url"));
        wsgs.setMarshaller(marshaller);
        wsgs.setUnmarshaller(marshaller);
        return wsgs;
    }

    // ====兼容双流 begin====
    @Bean
    public OrderInfoNumClient orderInfoNumClientSL(Jaxb2Marshaller marshaller) {
        return (OrderInfoNumClient) initsl(new OrderInfoNumClient(), marshaller);
    }

    /**
     * 一级科室
     *
     * @param marshaller
     * @return
     */
    @Bean
    public GetTopDeptInfoClient getTopDeptInfoClientSL(Jaxb2Marshaller marshaller) {
        return (GetTopDeptInfoClient) initsl(new GetTopDeptInfoClient(), marshaller);
    }

    /**
     * 二级科室
     *
     * @param marshaller
     * @return
     */
    @Bean
    public GetTwoDepInfoClient getTwoDepInfoClientSL(Jaxb2Marshaller marshaller) {
        return (GetTwoDepInfoClient) initsl(new GetTwoDepInfoClient(), marshaller);
    }

    @Bean
    public OrderClient orderClientSL(Jaxb2Marshaller marshaller) {
        return (OrderClient) initsl(new OrderClient(), marshaller);
    }

    private WebServiceGatewaySupport initsl(WebServiceGatewaySupport wsgs, Jaxb2Marshaller marshaller) {
        wsgs.setDefaultUri(environment.getProperty("area.platform.url.sl"));
        wsgs.setMarshaller(marshaller);
        wsgs.setUnmarshaller(marshaller);
        return wsgs;
    }
    // ====兼容双流 end====
}
