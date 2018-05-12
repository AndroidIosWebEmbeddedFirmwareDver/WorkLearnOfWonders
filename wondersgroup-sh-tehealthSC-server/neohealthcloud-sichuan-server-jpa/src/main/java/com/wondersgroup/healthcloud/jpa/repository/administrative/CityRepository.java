package com.wondersgroup.healthcloud.jpa.repository.administrative;

import com.wondersgroup.healthcloud.jpa.entity.administrative.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
public interface CityRepository extends JpaRepository<City, String> {
    @Query(value = "select a from City a where a.delFlag = '0' and a.provinceId = ?1")
    List<City> findCityByProvinceId(String provinceId);

    @Query(value = "select a from City a where a.delFlag = '0'")
    List<City> findAllCity();

    @Query(value = "select a.cityName from City a where a.cityId = ?1")
    String findNameByCityId(String cityId);
}
