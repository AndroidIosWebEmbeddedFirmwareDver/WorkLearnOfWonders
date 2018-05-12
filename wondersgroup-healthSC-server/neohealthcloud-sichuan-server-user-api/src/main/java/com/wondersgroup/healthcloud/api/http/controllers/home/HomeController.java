package com.wondersgroup.healthcloud.api.http.controllers.home;

import com.wondersgroup.healthcloud.common.appenum.ImageTextEnum;
import com.wondersgroup.healthcloud.common.http.annotations.WithoutToken;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.http.support.session.AccessToken;
import com.wondersgroup.healthcloud.common.http.support.version.VersionRange;
import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;
import com.wondersgroup.healthcloud.services.account.AccountService;
import com.wondersgroup.healthcloud.services.account.dto.Session;
import com.wondersgroup.healthcloud.services.article.ManageNewsArticleService;
import com.wondersgroup.healthcloud.services.article.dto.NewsArticleListAPIEntity;
import com.wondersgroup.healthcloud.services.imagetext.ImageTextService;
import com.wondersgroup.healthcloud.services.imagetext.dto.BasicImageTextDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ys on 2016/11/08
 */
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private ImageTextService imageTextService;

    @Autowired
    private ManageNewsArticleService manageNewsArticleService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @VersionRange
    @WithoutToken
    public JsonResponseEntity<Map<String, Object>> home(@AccessToken(required = false, guestEnabled = true) Session session,
                                                        @RequestHeader(name = "app-version") String appVersion,
                                                        @RequestParam(required = false, defaultValue = "5101") String area) {
        JsonResponseEntity<Map<String, Object>> result = new JsonResponseEntity();
        Map<String, Object> data = new HashMap();

        // 首页头部Banner
        data.put("banners", getTopBanners(5));

        // 首页功能栏
        data.put("functionIcons", getHomeIcon(appVersion, session));

        //首页功能栏下面的广告
        data.put("advertisements", getCenterAd(5));

        //首页文章
        List<NewsArticleListAPIEntity> newsArticleList = manageNewsArticleService.findArticleForFirst(area, 0, 10);
        if (newsArticleList != null && newsArticleList.size() > 0) {
            data.put("news", newsArticleList);
        }

        result.setData(data);
        return result;
    }

    /**
     * 首页banner图
     */
    private List<BasicImageTextDTO> getTopBanners(int getNum) {
        List<BasicImageTextDTO> banners = new ArrayList();
        ImageText imgTextA = new ImageText();
        imgTextA.setAdcode(ImageTextEnum.HOME_BANNER.getType());
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
     * 首页功能图标配置
     */
    private List<Map<String, Object>> getHomeIcon(String appVersion, Session session) {
        List<Map<String, Object>> functionIcons = new ArrayList();
        List<ImageText> imageTextsB = imageTextService.findGImageTextForApp(ImageTextEnum.G_HOME_FUNCTION.getType(), appVersion);
        if (imageTextsB != null && !imageTextsB.isEmpty()) {
            Map<String, Object> map;
            //schema  ?loginOrVerify=1// 0:不需要登录，1:需登录,2:需实名制
            for (ImageText imageText : imageTextsB) {
                map = new HashMap();
                map.put("hoplink", imageText.getHoplink());
                map.put("imgUrl", imageText.getImgUrl());
                map.put("mainTitle", imageText.getMainTitle());
                map.put("subTitle", imageText.getSubTitle());
                functionIcons.add(map);
            }
        }
        return functionIcons;
    }

    /**
     * 首页功能图标下面的广告
     */
    private List<BasicImageTextDTO> getCenterAd(int getNum) {
        List<BasicImageTextDTO> adImages = new ArrayList();
        ImageText imgTextC = new ImageText();
        imgTextC.setAdcode(ImageTextEnum.HOME_ADVERTISEMENT.getType());
        List<ImageText> imageTextsC = imageTextService.findImageTextByAdcodeForApp(imgTextC);
        if (imageTextsC != null && !imageTextsC.isEmpty()) {
            if (imageTextsC.size() <= getNum) {
                getNum = imageTextsC.size();
            }
            for (int i = 0; i < getNum; i++) {
                BasicImageTextDTO bit = new BasicImageTextDTO(imageTextsC.get(i));
                adImages.add(bit);
            }
        }
        return adImages;
    }

}
