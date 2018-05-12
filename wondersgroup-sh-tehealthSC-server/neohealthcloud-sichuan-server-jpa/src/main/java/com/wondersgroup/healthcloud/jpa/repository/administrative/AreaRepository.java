package com.wondersgroup.healthcloud.jpa.repository.administrative;

import com.wondersgroup.healthcloud.jpa.entity.administrative.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
public interface AreaRepository extends JpaRepository<Area, String> {
    @Query(value = "select a from Area a where a.delFlag = '0' and a.cityId = ?1")
    List<Area> findAreaByCityId(String cityId);

    @Query(value = "select a.areaName from Area a where a.areaId = ?1")
    String findNameByAreaId(String areaId);
}
