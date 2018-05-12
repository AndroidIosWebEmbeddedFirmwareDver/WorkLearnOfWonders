package com.wondersgroup.healthcloud.jpa.repository.imagetext;

import com.wondersgroup.healthcloud.jpa.entity.imagetext.ImageText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zhaozhenxing on 2016/6/12.
 */
public interface ImageTextRepository extends JpaRepository<ImageText, String>, JpaSpecificationExecutor<ImageText> {

    @Query("select a from ImageText a where a.gid = ?1 order by a.sequence")
    List<ImageText> findByGid(String gid);

    @Query("select a from ImageText a where a.gid = ?1 and a.delFlag = '0' order by a.sequence")
    List<ImageText> findByGidForApp(String gid);
}
