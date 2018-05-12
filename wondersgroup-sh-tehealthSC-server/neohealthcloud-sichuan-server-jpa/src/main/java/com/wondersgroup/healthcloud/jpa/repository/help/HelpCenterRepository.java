package com.wondersgroup.healthcloud.jpa.repository.help;

import com.wondersgroup.healthcloud.jpa.entity.help.HelpCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HelpCenterRepository extends JpaRepository<HelpCenter, String> {

    List<HelpCenter> findByIsVisableOrderBySort(String isVisable);

    @Modifying
    @Transactional
    @Query("delete from HelpCenter where id in ?1")
    void batchRemoveHelpCenter(List<String> ids);

    HelpCenter findById(String id);

    Page<HelpCenter> findByPlatform(Integer platform, Pageable pageable);

}
