package com.wonders.health.venus.open.user.logic;

import android.content.Context;
import android.text.TextUtils;

import com.wonders.health.venus.open.user.BaseApp;
import com.wonders.health.venus.open.user.entity.AreaEntity;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.util.PrefUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/8/27 16:40
 */
public class AreaManager {
    public static final String AREA_CODE_CHENGDU = "510100000000";
    public static final String AREA_NAME_CHENGDU = "成都市";
    private static List<AreaEntity> areas = new ArrayList<>();

    private static AreaManager INSTANCE;
    private String specArea;
    private AreaEntity cacheArea;

    public static AreaManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AreaManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AreaManager();
                }
            }
        }
        return INSTANCE;
    }

    private AreaManager() {
        INSTANCE = this;
    }

    static {
        areas.clear();
        ////////
        areas.add(new AreaEntity("510100000000", "成都市"));
        areas.add(new AreaEntity("510300000000", "自贡市"));
        areas.add(new AreaEntity("510400000000", "攀枝花市"));
        areas.add(new AreaEntity("510500000000", "泸州市"));
        areas.add(new AreaEntity("510600000000", "德阳市"));
        areas.add(new AreaEntity("510700000000", "绵阳市"));
        areas.add(new AreaEntity("510800000000", "广元市"));
        areas.add(new AreaEntity("510900000000", "遂宁市"));
        areas.add(new AreaEntity("511000000000", "内江市"));
        areas.add(new AreaEntity("511100000000", "乐山市"));
        areas.add(new AreaEntity("511300000000", "南充市"));
        areas.add(new AreaEntity("511400000000", "眉山市"));
        areas.add(new AreaEntity("511500000000", "宜宾市"));
        areas.add(new AreaEntity("511600000000", "广安市"));
        areas.add(new AreaEntity("511700000000", "达州市"));
        areas.add(new AreaEntity("511800000000", "雅安市"));
        areas.add(new AreaEntity("511900000000", "巴中市"));
        areas.add(new AreaEntity("512000000000", "资阳市"));
        areas.add(new AreaEntity("513200000000", "阿坝藏族羌族自治州"));
        areas.add(new AreaEntity("513300000000", "甘孜藏族自治州"));
        areas.add(new AreaEntity("513400000000", "凉山彝族自治州"));
    }

    public String getSpecArea() {
        if (TextUtils.isEmpty(specArea)) {
            AreaEntity point = getLocalPoint();
            if (point != null) {
                specArea = point.area_id;
            }
        }
        return specArea;
    }

    public void setSpecArea(String specArea) {
        this.specArea = specArea;
    }

    /**
     * 缓存坐标点
     */
    public void saveLocal(AreaEntity entity) {
        specArea = entity.area_id;
        PrefUtil.putJsonObject(BaseApp.getApp(), Constant.KEY_HOME_LOCAL, entity);
    }


    public AreaEntity getLocalPoint() {
        return PrefUtil.getJsonObject(BaseApp.getApp(), Constant.KEY_HOME_LOCAL, AreaEntity.class);
    }

    public List<AreaEntity> getAreas() {
        List<AreaEntity> list = new ArrayList<>();
        list.addAll(areas);
        return list;
    }

    public ArrayList<String> getAreaNames() {
        ArrayList<String> areaNames = new ArrayList<String>();
        for (AreaEntity entity : areas) {
            areaNames.add(entity.name);
        }
        return areaNames;
    }

    public String getAreaCodeByName(String areaName) {
        if(!TextUtils.isEmpty(areaName)){
            for (AreaEntity entity : areas) {
                if (entity.name.equals(areaName)) {
                    return entity.area_id;
                }
            }
        }
        return AREA_CODE_CHENGDU;
    }

    /**
     * 获取最近选取的地区
     *
     * @param context
     * @return
     */
    public List<AreaEntity> getRecentSelect(Context context) {
        return PrefUtil.getJsonArray(context, Constant.KEY_QUICK_AREA_SELECT, AreaEntity.class);
    }

    /**
     * 更新最近选择地区
     */
    public void updateRecentSelect(Context context, AreaEntity entity) {
        if (entity == null || TextUtils.isEmpty(entity.area_id) || TextUtils.isEmpty(entity.name)) {
            return;
        }
        List<AreaEntity> list = getRecentSelect(context);
        if (list == null) {
            list = new ArrayList<>();
        }
        int exit_position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (entity.area_id.equals(list.get(i).area_id) && entity.name.equals(list.get(i).name)) {
                exit_position = i;
                break;
            }
        }
        if (exit_position != -1) {
            list.remove(exit_position);
        }
        list.add(0, entity);
        if (list.size() > 3) { //最近选择只记录3条
            list.remove(list.size() - 1);
        }
        PrefUtil.putJsonArray(context, Constant.KEY_QUICK_AREA_SELECT, list);
    }

    /**
     * 获取除了最近选择的地区
     *
     * @return
     */
    public List<AreaEntity> getAreaExceptRecentSelect(Context context) {
        List<AreaEntity> source = getAreas();
        List<AreaEntity> target = new ArrayList<>();
        List<AreaEntity> recent_select = getRecentSelect(context);
        if (recent_select == null || recent_select.isEmpty()) {
            source.remove(0);
            return source;
        }
        for (AreaEntity entity : source) {
            boolean exist = false;
            for (AreaEntity recent_entity : recent_select) {
                if (entity.area_id.equals(recent_entity.area_id) && entity.name.equals(recent_entity.name)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                target.add(entity);
            }
        }
        if (target.contains(areas.get(0))) {
            target.remove(areas.get(0));
        }
        return target;
    }

    /**
     * 是否需要弹出允许定位框
     *
     * @param context
     */
    public boolean needShowAllowLocationDialog(Context context) {
        return PrefUtil.getBoolean(context, Constant.KEY_NEED_SHOW_LOCATION_DAILOG, true);
    }

    /**
     * 设置是否需要弹出定位框
     *
     * @param context
     * @param need_show
     */
    public void setNeedShowLocationDialog(Context context, boolean need_show) {
        PrefUtil.putBoolean(context, Constant.KEY_NEED_SHOW_LOCATION_DAILOG, need_show);
    }

    /**
     * 是否允许定位
     *
     * @param context
     * @return
     */
    public boolean isAllowLocation(Context context) {
        return PrefUtil.getBoolean(context, Constant.KEY_ALLOW_LOCATION, true);
    }

    /**
     * 设置（不）允许定位
     *
     * @param context
     * @param allow
     */
    public void setAllowLocation(Context context, boolean allow) {
        PrefUtil.putBoolean(context, Constant.KEY_ALLOW_LOCATION, allow);
    }
}
