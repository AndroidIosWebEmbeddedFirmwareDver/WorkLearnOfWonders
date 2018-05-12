package com.wondersgroup.healthcloud.api.http.controllers.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.wondersgroup.healthcloud.api.utils.CommonUtils;
import com.wondersgroup.healthcloud.common.appenum.ImageTextEnum;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.common.utils.AppUrlH5Utils;
import com.wondersgroup.healthcloud.common.utils.UploaderUtil;
import com.wondersgroup.healthcloud.jpa.entity.config.AppConfig;
import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;
import com.wondersgroup.healthcloud.services.config.AppConfigService;
import com.wondersgroup.healthcloud.services.imagetext.ImageTextService;
import com.wondersgroup.healthcloud.utils.security.RSAKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaozhenxing on 2016/11/01.
 */
@RestController
@RequestMapping("/api/common")
public class CommonController {
    private static final Logger log = Logger.getLogger(CommonController.class);

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AppUrlH5Utils appUrlH5Utils;


    @Autowired
    private ImageTextService imageTextService;

    /**
     * APP获取启动数据
     */
    @RequestMapping(value = "/appConfig", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<Map<String, Object>> appConfig(@RequestHeader(value = "platform", required = false) String platform,
                                                             @RequestHeader(value = "screen-width", required = false) String width,
                                                             @RequestHeader(value = "screen-height", required = false) String height,
                                                             @RequestHeader(value = "app-version", required = false) String appVersion) {
        JsonResponseEntity<Map<String, Object>> response = new JsonResponseEntity<>();
        Map<String, Object> data = new HashMap<>();

        List<String> keyWords = new ArrayList<>();
        keyWords.add("app.common.consumer.hotline");//客服热线
        keyWords.add("app.common.help.center");// 帮助中心
        keyWords.add("app.common.userAgreement");// 用户协议
        keyWords.add("app.common.real.name.authentication.rule");// 实名认证责任条款
        keyWords.add("app.common.real.appointment.rule");// 预约挂号规则

        //keyWords.add("app.common.intellectualPropertyAgreement");// 知识产权协议

        keyWords.add("app.common.appUpdate");// APP更新
        Map<String, String> cfgMap = appConfigService.findAppConfigByKeyWords(keyWords);

        Map<String, Object> common = new HashMap<>();
        common.put("publicKey", RSAKey.publicKey);

        if (cfgMap != null) {
            if (cfgMap.get("app.common.consumer.hotline") != null) {
                common.put("consumerHotline", cfgMap.get("app.common.consumer.hotline"));
            }
            if (cfgMap.get("app.common.help.center") != null) {
                common.put("helpCenter", appUrlH5Utils.buildBasicUrl(cfgMap.get("app.common.help.center")));
            }
            if (cfgMap.get("app.common.userAgreement") != null) {
                common.put("userAgreement", appUrlH5Utils.buildBasicUrl(cfgMap.get("app.common.userAgreement")));
            }
            if (cfgMap.get("app.common.real.name.authentication.rule") != null) {
                common.put("realNameRule", appUrlH5Utils.buildBasicUrl(cfgMap.get("app.common.real.name.authentication.rule")));
            }
            if (cfgMap.get("app.common.real.appointment.rule") != null) {
                common.put("appointmentRule", appUrlH5Utils.buildBasicUrl(cfgMap.get("app.common.real.appointment.rule")));
            }
            /*if (cfgMap.get("app.common.intellectualPropertyAgreement") != null) {
                common.put("ipa", appUrlH5Utils.buildBasicUrl(cfgMap.get("app.common.intellectualPropertyAgreement")));
            }*/

            if (cfgMap.get("app.common.appUpdate") != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode content = objectMapper.readTree(cfgMap.get("app.common.appUpdate").toString());
                    String lastVersion = content.get("lastVersion") == null ? "" : content.get("lastVersion").asText();
                    Boolean hasUpdate = CommonUtils.compareVersion(appVersion, lastVersion);
                    if (hasUpdate) {
                        Boolean forceUpdate = false;
                        String forceUpdateVersion = content.get("enforceUpdate") == null ? "" : content.get("enforceUpdate").asText();
                        if (!com.qiniu.util.StringUtils.isNullOrEmpty(forceUpdateVersion) && forceUpdateVersion.split(",").length == 2) {
                            forceUpdate = CommonUtils.compareVersion(forceUpdateVersion.split(",")[0], appVersion) && CommonUtils.compareVersion(appVersion, forceUpdateVersion.split(",")[1]);
                        }
                        String updateMsg = content.get("updateMsg") == null ? "" : content.get("updateMsg").asText();
                        String downloadUrl = content.get("downloadUrl") == null ? "" : content.get("downloadUrl").asText();
                        String iosDownloadUrl = content.get("iosDownloadUrl") == null ? "" : content.get("iosDownloadUrl").asText();

                        Map appUpdate = new HashMap();
                        appUpdate.put("hasUpdate", hasUpdate);
                        appUpdate.put("forceUpdate", forceUpdate);
                        appUpdate.put("lastVersion", lastVersion);
                        appUpdate.put("updateMsg", updateMsg);
                        appUpdate.put("androidUrl", downloadUrl);
                        appUpdate.put("iosUrl", iosDownloadUrl);
                        data.put("appUpdate", appUpdate);
                    }
                } catch (Exception ex) {
                    log.error("CommonController.appConfig Error -->" + ex.getLocalizedMessage());
                }
            }
        }
        data.put("common", common);

        response.setData(data);
        return response;
    }

    @RequestMapping(value = "/getQiniuToken", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<Map<String, Object>> qiniuConfig() {
        JsonResponseEntity<Map<String, Object>> response = new JsonResponseEntity<Map<String, Object>>();
        Map<String, Object> map = Maps.newHashMap();
        map.put("token", UploaderUtil.getUpToken());
        map.put("expires", UploaderUtil.expires);
        map.put("domain", UploaderUtil.domain);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/appNavigationBar", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity getNavigationBar() {
        JsonResponseEntity result = new JsonResponseEntity();
        ImageText imgText = new ImageText();
        imgText.setAdcode(ImageTextEnum.NAVIGATION_BAR.getType());
        List<ImageText> imageTexts = imageTextService.findImageTextByAdcodeForApp(imgText);
        if (imageTexts != null && imageTexts.size() > 0) {
            List<String> navigationBars = new ArrayList<>();
            for (ImageText imageText : imageTexts) {
                navigationBars.add(imageText.getImgUrl());
            }
            result.setData(navigationBars);
        } else {
            result.setCode(1000);
            result.setMsg("未查询到相关配置信息！");
        }
        return result;
    }

    @RequestMapping(value = "/aboutApp", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity aboutApp() {
        JsonResponseEntity result = new JsonResponseEntity();
        try {
            AppConfig appConfig = appConfigService.findSingleAppConfigByKeyWord("app.common.aboutApp");
            if (appConfig != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode aboutApp = objectMapper.readTree(appConfig.getData());
                result.setData(aboutApp);
            } else {
                result.setCode(1000);
                result.setMsg("未查询到相关配置信息！");
            }
        } catch (Exception ex) {
            log.error("CommonController.aboutApp Error -->" + ex.getLocalizedMessage());
            result.setCode(1000);
            result.setMsg("获取配置信息失败！");
        }
        return result;
    }
}
