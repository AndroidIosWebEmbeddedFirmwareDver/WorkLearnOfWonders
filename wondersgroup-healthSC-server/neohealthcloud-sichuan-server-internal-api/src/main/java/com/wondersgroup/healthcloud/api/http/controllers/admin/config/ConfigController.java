package com.wondersgroup.healthcloud.api.http.controllers.admin.config;

import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.jpa.entity.config.AppConfig;
import com.wondersgroup.healthcloud.services.config.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhaozhenxing on 2016/11/01.
 */
@RestController
@RequestMapping("/api/appConfig")
@Admin
public class ConfigController {

    @Autowired
    private AppConfigService appConfigService;

    /**
     * 根据关键字查询单个APP配置信息
     *
     * @param keyWord
     * @return
     */
    @Admin
    @RequestMapping(value = "/findSingleAppConfigByKeyWord", method = RequestMethod.GET)
    public JsonResponseEntity<AppConfig> findSingleAppConfigByKeyWord(@RequestParam(required = true) String keyWord) {
        JsonResponseEntity<AppConfig> result = new JsonResponseEntity<>();
        // 全局接口不需指定区级区域ID
        AppConfig appConfig = appConfigService.findSingleAppConfigByKeyWord(keyWord);
        if (appConfig != null) {
            result.setData(appConfig);
        } else {
            result.setCode(1001);
            result.setMsg("未查询到相关配置信息！");
        }
        return result;
    }

    /**
     * 查询全部离散APP配置数据
     *
     * @return
     */
    @Admin
    @RequestMapping(value = "/findAllDiscreteAppConfig", method = RequestMethod.GET)
    public JsonResponseEntity<List<AppConfig>> findAllDiscreteAppConfig() {
        JsonResponseEntity<List<AppConfig>> result = new JsonResponseEntity<>();
        List<AppConfig> appConfigs = appConfigService.findAllDiscreteAppConfig();
        if (appConfigs != null && appConfigs.size() > 0) {
            result.setData(appConfigs);
        } else {
            result.setCode(1001);
            result.setMsg("未查询到相关配置信息！");
        }
        return result;
    }

    /**
     * 保存配置信息
     *
     * @param appConfig
     * @return
     */
    @Admin
    @RequestMapping(value = "/saveAppConfig", method = RequestMethod.POST)
    public JsonResponseEntity saveAppConfig(@RequestBody AppConfig appConfig) {
        JsonResponseEntity result = new JsonResponseEntity();
        AppConfig rtnAppConfig = appConfigService.saveAndUpdateAppConfig(appConfig);
        if (rtnAppConfig != null) {
            result.setMsg("配置信息保存成功！");
        } else {
            result.setCode(1001);
            result.setMsg("配置信息保存失败！");
        }
        return result;
    }
}
