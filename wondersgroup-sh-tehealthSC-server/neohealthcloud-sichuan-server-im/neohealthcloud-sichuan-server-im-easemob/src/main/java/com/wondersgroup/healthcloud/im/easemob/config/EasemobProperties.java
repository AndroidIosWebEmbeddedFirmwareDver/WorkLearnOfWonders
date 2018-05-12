package com.wondersgroup.healthcloud.im.easemob.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jialing.yao on 2017-5-31.
 */
@ConfigurationProperties(
        prefix = "im.config.easemob"
)
@Data
public class EasemobProperties {
    private String url;
    private String grantType;
    private String clientID;
    private String clientSecret;
}
