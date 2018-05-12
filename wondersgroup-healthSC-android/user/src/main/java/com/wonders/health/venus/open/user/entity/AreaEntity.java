package com.wonders.health.venus.open.user.entity;

import java.io.Serializable;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/8/27 16:40
 */
public class AreaEntity implements Serializable {
    public String area_id;
    public String name;
    public String latitude;
    public String longitude;

    public AreaEntity() {

    }

    public AreaEntity(String id, String name) {
        this.area_id = id;
        this.name = name;
    }

    public AreaEntity(String id, String name, String latitude, String longitude) {
        this.area_id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        if ("上海".equals(name)) {
            return "上海市";
        }
        if ("崇明".equals(name)) {
            return "崇明县";
        }
        if ("浦东".equals(name)) {
            return "浦东新区";
        }
        return name + "区";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaEntity entity = (AreaEntity) o;

        if (area_id != null ? !area_id.equals(entity.area_id) : entity.area_id != null)
            return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;

        return true;
    }


}
