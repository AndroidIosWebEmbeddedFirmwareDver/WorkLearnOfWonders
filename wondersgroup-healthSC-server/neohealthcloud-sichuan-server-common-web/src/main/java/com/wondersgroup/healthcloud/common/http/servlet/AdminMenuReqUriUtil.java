package com.wondersgroup.healthcloud.common.http.servlet;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * java类简单作用描述
 *
 * @Author: 创建者
 * @CreateDate: 2018/5/3 17:40
 * @UpdateUser: 修改者
 * @UpdateDate: 2018/5/3 17:40
 * @UpdateRemark: 修改描述
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class AdminMenuReqUriUtil {

    public static Map<String, String> reqUri_menuHref_map;

    /**医生评价审核  后台请求URI集合**/
    static ImmutableSet<String> doctorEvaluation_uri_set = ImmutableSet.of("/admin/evaluate/doctor/list","/admin/evaluate/doctor/batchDelete","/admin/evaluate/doctor/updateStatus","/admin/evaluate/doctor/updateIsTop");
    /**底部Tab配置  后台请求URI集合**/
    static ImmutableSet<String> bottomTabConfiguration_uri_set = ImmutableSet.of("/api/imagetext/findImageTextByAdcode","/api/imagetext/saveBatchImageText");
    /**APP零散配置  后台请求URI集合**/
    static ImmutableSet<String> scatteredConfiguration_uri_set = ImmutableSet.of("/api/appConfig/findAllDiscreteAppConfig","/api/appConfig/saveAppConfig");
    /**消息系统通知配置  后台请求URI集合**/
    static ImmutableSet<String> systemNotification_uri_set = ImmutableSet.of("/message/queryList","/message/update");
    /**资讯分类  后台请求URI集合**/
    static ImmutableSet<String> informationClassify_uri_set = ImmutableSet.of("/back/article/categoryList","/back/article/categorySave");
    /**首页资讯  后台请求URI集合**/
    static ImmutableSet<String> homeInformation_uri_set = ImmutableSet.of("/back/home/article/list","/back/home/article/update");
    /**实名认证审核  后台请求URI集合**/
    static ImmutableSet<String> authentication_uri_set = ImmutableSet.of("/admin/verification/manage/list","/admin/verification/manage/verified", "/admin/verification/manage/refuse");
    /**微健康帮助中心  后台请求URI集合**/
    static ImmutableSet<String> helpCenter_uri_set = ImmutableSet.of("/api/helpCenter/find","/api/deleteHelpCenter");
    /**预约挂号列表  后台请求URI集合**/
    static ImmutableSet<String> order_uri_set = ImmutableSet.of("","");
    /**医院列表  后台请求URI集合**/
    static ImmutableSet<String> hospital_uri_set = ImmutableSet.of("/api/serverConfig/areaCode","/api/hospital/list","/api/hospital/upload");
    /**健康双流帮助中心  后台请求URI集合**/
    static ImmutableSet<String> helpCenterMore_uri_set = ImmutableSet.of("","");
    /**首页板块设置  后台请求URI集合**/
    static ImmutableSet<String> cps_homePlateSetting_uri_set = ImmutableSet.of("/api/imagetext/findGImageTextVersions","/api/imagetext/findGImageTextList");
    /**菜单管理  后台请求URI集合**/
    static ImmutableSet<String> authManage_uri_set = ImmutableSet.of("/api/menu/list","/api/menu/update","/api/menu/delete");
    /**健康功能图标设置  后台请求URI集合**/
    static ImmutableSet<String> hs_homePlateSetting_uri_set = ImmutableSet.of("/api/imagetext/findGImageTextVersions","/api/imagetext/findGImageTextList");
    /**APP升级  后台请求URI集合**/
    static ImmutableSet<String> upgrade_uri_set = ImmutableSet.of("/api/appConfig/findSingleAppConfigByKeyWord","/api/appConfig/saveAppConfig");
    /**账号管理  后台请求URI集合**/
    static ImmutableSet<String> userManage_uri_set = ImmutableSet.of("/api/basicInfo/user/list","/api/basicInfo/user/update","/api/basicInfo/user/resetPassword");
    /**文章管理  后台请求URI集合**/
    static ImmutableSet<String> articleManage_uri_set = ImmutableSet.of("/back/article/list","");
    /**健康Banner设置  后台请求URI集合**/
    static ImmutableSet<String> hs_bannerSetting_uri_set = ImmutableSet.of("/api/imagetext/saveImageText","/api/imagetext/findImageTextByAdcode");
    /**医院评价审核  后台请求URI集合**/
    static ImmutableSet<String> hospitalEvaluation_uri_set = ImmutableSet.of("/admin/evaluate/hospital/list","/admin/evaluate/hospital/batchDelete","/admin/evaluate/hospital/updateStatus","/admin/evaluate/hospital/updateIsTop");
    /**角色管理  后台请求URI集合**/
    static ImmutableSet<String> roleManage_uri_set = ImmutableSet.of("/api/basicInfo/role/list","/api/basicInfo/role/enOrDisable");
    /**开关配置  后台请求URI集合**/
    static ImmutableSet<String> switch_uri_set = ImmutableSet.of("/api/appConfig/findSingleAppConfigByKeyWord","/api/appConfig/saveAppConfig");
    /**Banner设置  后台请求URI集合**/
    static ImmutableSet<String> cps_bannerSetting_uri_set = ImmutableSet.of("/api/imagetext/findImageTextByAdcode","/api/imagetext/saveImageText");
    /**资源池  后台请求URI集合**/
    static ImmutableSet<String> areaAddressSetting_uri_set = ImmutableSet.of("/api/serverConfig/queryArea","");
    /**APP关于  后台请求URI集合**/
    static ImmutableSet<String> about_uri_set = ImmutableSet.of("/api/appConfig/findSingleAppConfigByKeyWord","/api/appConfig/saveAppConfig");

    private static Set<String> tempReqUriSet;
    static{
        tempReqUriSet = new HashSet<>();
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        build("app.doctorEvaluation", doctorEvaluation_uri_set, builder);//医生评价审核
        build("app.bs.bottomTabConfiguration", bottomTabConfiguration_uri_set, builder);//底部Tab配置
        build("app.bs.scatteredConfiguration", scatteredConfiguration_uri_set, builder);//APP零散配置
        build("app.systemNotification", systemNotification_uri_set, builder);//消息系统通知配置
        build("app.im.informationClassify", informationClassify_uri_set, builder);//资讯分类
        build("app.cps.homeInformation", homeInformation_uri_set, builder);//首页资讯
        build("app.authentication", authentication_uri_set, builder);//实名认证审核
        build("app.help.helpCenter", helpCenter_uri_set, builder);//微健康帮助中心
        build("app.order", order_uri_set, builder);//预约挂号列表
        build("app.ihd.hospital", hospital_uri_set, builder);//医院列表
        build("app.help.helpCenterMore", helpCenterMore_uri_set, builder);//健康双流帮助中心
        build("app.cps.homePlateSetting", cps_homePlateSetting_uri_set, builder);//首页板块设置
        build("app.rbac.authManage", authManage_uri_set, builder);//菜单管理
        build("app.hs.homePlateSetting", hs_homePlateSetting_uri_set, builder);//健康功能图标设置
        build("app.bs.upgrade", upgrade_uri_set, builder);//APP升级
        build("app.rbac.userManage", userManage_uri_set, builder);//账号管理
        build("app.am.articleManage", articleManage_uri_set, builder);//文章管理
        build("app.hs.bannerSetting", hs_bannerSetting_uri_set, builder);//健康Banner设置
        build("app.hospitalEvaluation", hospitalEvaluation_uri_set, builder);//医院评价审核
        build("app.rbac.roleManage", roleManage_uri_set, builder);//角色管理
        build("app.bs.switch", switch_uri_set, builder);//开关配置
        build("app.cps.bannerSetting", cps_bannerSetting_uri_set, builder);//Banner设置
        build("app.areaAddressSetting", areaAddressSetting_uri_set, builder);//资源池
        build("app.bs.about", about_uri_set, builder);//APP关于
        reqUri_menuHref_map = builder.build();
        tempReqUriSet.clear();
    }


    /**
     * 转化为 reqUri - menuHref
     * @param menu_href  功能菜单H5初始加载页面URI
     * @param reqUriSet  功能菜单后台请求的URI集合
     * @param builder
     */
    private static void build(String menu_href, ImmutableSet<String> reqUriSet, ImmutableMap.Builder<String, String> builder){
        if(reqUriSet == null || reqUriSet.size() < 1)
            return;

        if(StringUtils.isEmpty(menu_href))
            return;

        for(String reqUri : reqUriSet){
            if(!StringUtils.isEmpty(reqUri) && !tempReqUriSet.contains(reqUri)){
                builder.put(reqUri, menu_href);
                tempReqUriSet.add(reqUri);
            }
        }
    }
}
