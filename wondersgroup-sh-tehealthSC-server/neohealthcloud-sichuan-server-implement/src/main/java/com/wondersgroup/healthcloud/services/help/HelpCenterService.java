package com.wondersgroup.healthcloud.services.help;

import com.wondersgroup.healthcloud.jpa.entity.help.HelpCenter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface HelpCenterService {

    List<HelpCenter> findByIsVisable(String isVisable);

    Page<HelpCenter> findAll(Pageable pageable);

    void saveHelpCenter(HelpCenter helpCenter);

    void batchRemoveHelpCenter(List<String> ids);

    HelpCenter findById(String id);

}
