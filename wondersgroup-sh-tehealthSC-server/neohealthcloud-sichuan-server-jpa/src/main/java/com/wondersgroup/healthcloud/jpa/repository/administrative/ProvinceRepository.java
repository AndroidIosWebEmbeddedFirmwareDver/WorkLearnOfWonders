package com.wondersgroup.healthcloud.jpa.repository.administrative;

import com.wondersgroup.healthcloud.jpa.entity.administrative.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zhaozhenxing on 2016/9/19.
 */
public interface ProvinceRepository extends JpaRepository<Province, String> {
    @Query(value = "select a from Province a where a.delFlag = '0'")
    List<Province> findAllProvince();
}
