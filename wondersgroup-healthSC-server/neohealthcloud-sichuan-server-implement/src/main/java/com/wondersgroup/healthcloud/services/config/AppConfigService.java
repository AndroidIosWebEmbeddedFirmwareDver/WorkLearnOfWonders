package com.wondersgroup.healthcloud.services.config;

import com.wondersgroup.healthcloud.jpa.entity.config.AppConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaozhenxing on 2016/11/01.
 */
public interface AppConfigService {

    Map<String, String> findAppConfigByKeyWords(List<String> keyWords);

    List<AppConfig> findAllDiscreteAppConfig();

    AppConfig findSingleAppConfigByKeyWord(String keyWord);

    AppConfig saveAndUpdateAppConfig(AppConfig appConfig);
}
