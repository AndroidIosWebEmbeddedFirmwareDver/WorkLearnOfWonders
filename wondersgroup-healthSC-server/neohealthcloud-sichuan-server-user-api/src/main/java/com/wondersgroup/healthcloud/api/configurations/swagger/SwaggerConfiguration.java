package com.wondersgroup.healthcloud.api.configurations.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @Description
 * @Author
 * @Create 2018-04-03 下午4:55
 **/


@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfiguration {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wondersgroup.healthcloud.api"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("user-api")
                .description("user-api")
                .termsOfServiceUrl("localhost:8080")
                .license("Apache license")
                .contact("developer@mail.com")
                .version("1.0")
                .build();
    }
}
