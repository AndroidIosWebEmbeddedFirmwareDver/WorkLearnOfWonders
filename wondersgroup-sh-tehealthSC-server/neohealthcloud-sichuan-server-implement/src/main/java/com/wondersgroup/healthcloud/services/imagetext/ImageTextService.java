package com.wondersgroup.healthcloud.services.imagetext;

import com.wondersgroup.healthcloud.jpa.entity.imagetext.GImageText;
import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaozhenxing on 2016/6/12.
 */
public interface ImageTextService {

    ImageText findImageTextById(String id);

    ImageText saveImageText(ImageText imageText);

    int saveBatchImageText(List<ImageText> imageTextList);

    List<ImageText> findImageTextByAdcodeForApp(ImageText imageText);

    public int countImageTextByAdcode(Map params);

    List<ImageText> findImageTextByAdcode(Integer pageNum, Integer pageSize, Map params);

    // 根据区域,广告位获取版本列表
    List<String> findGImageTextVersions(Integer gadcode);

    public int countGImageTextList(Map params);

    // 根据区域,广告位获取广告组列表
    List<GImageText> findGImageTextList(Integer pageNum, Integer pageSize, Map params);

    // 根据广告组ID查询广告组详情
    GImageText findGImageTextById(String gid);

    // 保存广告组数据
    boolean saveGImageText(GImageText gImageText);

    // 查询组广告供APP使用
    List<ImageText> findGImageTextForApp(Integer gadcode, String appVersion);
}
