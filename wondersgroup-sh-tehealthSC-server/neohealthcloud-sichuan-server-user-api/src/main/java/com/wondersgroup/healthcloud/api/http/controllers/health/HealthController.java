package com.wondersgroup.healthcloud.api.http.controllers.health;

import com.wondersgroup.healthcloud.common.appenum.ImageTextEnum;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.config.AppConfig;
import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;
import com.wondersgroup.healthcloud.services.config.AppConfigService;
import com.wondersgroup.healthcloud.services.imagetext.ImageTextService;
import com.wondersgroup.healthcloud.services.imagetext.dto.BasicImageTextDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaozhenxing on 2016/11/10.
 */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired
    private ImageTextService imageTextService;

    @Autowired
    private AppConfigService appConfigService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<Map<String, Object>> home(@RequestHeader(required = true, name = "app-version") String appVersion,
                                                        @RequestParam(required = false, defaultValue = "5101") String area) {

        JsonResponseEntity<Map<String, Object>> result = new JsonResponseEntity();
        Map<String, Object> data = new HashMap();

        // 健康页头部Banner
        data.put("banners", getTopBanners(5));

        // 健康页功能栏
        data.put("functionIcons", getHomeIcon(appVersion));

        result.setData(data);
        return result;
    }

    /**
     * 健康页头部Banner
     */
    private List<BasicImageTextDTO> getTopBanners(int getNum) {
        List<BasicImageTextDTO> banners = new ArrayList();
        ImageText imgTextA = new ImageText();
        imgTextA.setAdcode(ImageTextEnum.HEALTH_BANNER.getType());
        List<ImageText> imageTextsA = imageTextService.findImageTextByAdcodeForApp(imgTextA);
        if (imageTextsA != null && !imageTextsA.isEmpty()) {
            if (imageTextsA.size() < getNum) {
                getNum = imageTextsA.size();
            }
            for (int i = 0; i < getNum; i++) {
                BasicImageTextDTO bit = new BasicImageTextDTO(imageTextsA.get(i));
                banners.add(bit);
            }
        }
        return banners;
    }

    /**
     * 健康页功能栏
     */
    private List<Map<String, Object>> getHomeIcon(String appVersion) {
        List<Map<String, Object>> functionIcons = new ArrayList();
        List<ImageText> imageTextsB = imageTextService.findGImageTextForApp(ImageTextEnum.G_HEALTH_FUNCTION.getType(), appVersion);
        if (imageTextsB != null && !imageTextsB.isEmpty()) {
            Map<String, Object> map;
            for (ImageText imageText : imageTextsB) {
                map = new HashMap();
                AppConfig config = appConfigService.findSingleAppConfigByKeyWord("app.common.health.function.switch");
                if (config != null && "0".equals(config.getData()) && "3".equals(imageText.getSequence())) {
                    // 健康页功能图标是否开通开关(0-未开通,1-已开通)
                    // 未开通状态下第三个图标的图片不显示，由APP固定
                } else {
                    map.put("imgUrl", imageText.getImgUrl());
                }
                map.put("hoplink", imageText.getHoplink());
                map.put("mainTitle", imageText.getMainTitle());
                map.put("subTitle", imageText.getSubTitle());
                functionIcons.add(map);
            }
        }
        return functionIcons;
    }

}
