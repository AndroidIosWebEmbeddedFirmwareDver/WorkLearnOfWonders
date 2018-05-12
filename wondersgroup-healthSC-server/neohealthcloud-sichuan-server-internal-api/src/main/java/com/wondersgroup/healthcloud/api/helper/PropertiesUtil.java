package com.wondersgroup.healthcloud.api.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/12/2.
 */
@Component
@ConfigurationProperties(prefix = "administrator")
public class PropertiesUtil {

    public String account;//超级管理员

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
