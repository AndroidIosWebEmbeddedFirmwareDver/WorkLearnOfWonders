package com.wondersgroup.healthcloud.webservice.platform.configuration;

import com.wondersgroup.healthcloud.webservice.platform.service.RegisterService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private RegisterService registerService;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, registerService);
        endpoint.publish("/soap/UserRegister");


        return endpoint;
    }
}