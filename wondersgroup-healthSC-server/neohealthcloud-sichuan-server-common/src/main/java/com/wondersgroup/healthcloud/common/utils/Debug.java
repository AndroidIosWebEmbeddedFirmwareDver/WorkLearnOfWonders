package com.wondersgroup.healthcloud.common.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by zhangzhixiu on 15/9/6.
 */
@Component
public class Debug {

    private Boolean sandbox;

    public Debug(@Autowired Environment env) {
        String[] profiles = env.getActiveProfiles();
        sandbox = profiles.length != 0 && ("de".equals(env.getActiveProfiles()[0]) || "te".equals(env.getActiveProfiles()[0]));
    }

    public Boolean sandbox() {
        return sandbox;
    }
}
