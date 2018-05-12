package com.wondersgroup.healthcloud.services.config;

import com.wondersgroup.healthcloud.jpa.entity.config.ServerConfig;

import java.util.List;

/**
 * Created by dukuanxinon 2017/2/24.
 */
public interface ServerConfigService {

    public List<ServerConfig> queryServerConfig(String areaCode);

    public void updateServerConfig(List<ServerConfig> configs);


    public List<ServerConfig> queryServerConfig();

    /**
     * @param areaCode(城市编码)
     * @param type(1-预约挂号,2-诊间支付/电子处方,3-提取报告,4-健康档案)
     * @return
     */
    public String queryApiUrl(String areaCode, String type);
}
