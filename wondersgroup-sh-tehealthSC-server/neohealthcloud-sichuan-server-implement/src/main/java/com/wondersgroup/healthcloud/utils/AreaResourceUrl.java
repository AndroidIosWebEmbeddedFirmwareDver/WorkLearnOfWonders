package com.wondersgroup.healthcloud.utils;

import com.wondersgroup.healthcloud.services.config.ServerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhaozhenxing on 2017/3/1.
 */
@Component
public class AreaResourceUrl {
    @Autowired
    private ServerConfigService serverConfigServiceImpl;

    public String getUrl(String type, String... cityCodes) {
        if (cityCodes != null && cityCodes.length > 0) {
            return serverConfigServiceImpl.queryApiUrl(cityCodes[0], type);
        } else {
            return serverConfigServiceImpl.queryApiUrl("510000000000", type);
        }
    }
}
