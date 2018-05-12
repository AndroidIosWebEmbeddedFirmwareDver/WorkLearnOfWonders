package com.wondersgroup.healthcloud.services.config.impl;

import com.wondersgroup.healthcloud.jpa.entity.config.ServerConfig;
import com.wondersgroup.healthcloud.jpa.repository.config.ServerConfigRepository;
import com.wondersgroup.healthcloud.services.config.ServerConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dukuanxinon 2017/2/24.
 */
@Service("serverConfigServiceImpl")
public class ServerConfigServiceImpl implements ServerConfigService {

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @Override
    public List<ServerConfig> queryServerConfig(String areaCode) {

        return serverConfigRepository.queryServerConfig(areaCode);
    }

    @Override
    public void updateServerConfig(List<ServerConfig> configs) {
        for (ServerConfig config : configs)
            if (config.getId() != null) {
                ServerConfig sc = serverConfigRepository.findOne(config.getId());
                sc.setApiUrl(config.getApiUrl());
                serverConfigRepository.saveAndFlush(config);
            } else {
                serverConfigRepository.saveAndFlush(config);
            }
    }

    @Override
    public List<ServerConfig> queryServerConfig() {
        return serverConfigRepository.queryServerConfig();
    }

    @Override
    public String queryApiUrl(String areaCode, String type) {
        ServerConfig config = serverConfigRepository.queryApiUrl(areaCode, type);
        if (config == null) {
            ServerConfig defConfig = serverConfigRepository.queryApiUrl("510000000000", type);
            return defConfig.getApiUrl();
        }
        return config.getApiUrl();
    }
}
