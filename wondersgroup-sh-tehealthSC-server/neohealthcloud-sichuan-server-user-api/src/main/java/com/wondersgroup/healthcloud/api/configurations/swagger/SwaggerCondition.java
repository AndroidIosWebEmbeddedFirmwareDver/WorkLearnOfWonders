package com.wondersgroup.healthcloud.api.configurations.swagger;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Description SwaggerCondition 为了防止在生产环境恶意攻击api接口，建议关闭生产环境通过swagger api对接口的访问
 * @Author
 * @Create 2018-04-03 下午4:57
 **/

public class SwaggerCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !"re".equals(context.getEnvironment().getProperty("spring.profiles.active"));
    }
}

