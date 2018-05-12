package com.wondersgroup.healthcloud.jpa.repository.imagetext;

import com.wondersgroup.healthcloud.jpa.entity.imagetext.GImageText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zhaozhenxing on 2016/8/28.
 */
public interface GImageTextRepository extends JpaRepository<GImageText, String> {
    @Query("select a from GImageText a where a.gadcode = ?3 and a.version = ?4")
    GImageText findGImageTextForApp(Integer gadcode, String version);
}
