package com.wondersgroup.healthcloud.services.help.impl;

import com.wondersgroup.healthcloud.jpa.entity.help.HelpCenter;
import com.wondersgroup.healthcloud.jpa.repository.help.HelpCenterRepository;
import com.wondersgroup.healthcloud.services.help.HelpCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpCenterServiceImpl implements HelpCenterService {

    @Autowired
    private HelpCenterRepository helpCenterRepository;

    @Override
    public List<HelpCenter> findByIsVisable(String isVisable) {
        return helpCenterRepository.findByIsVisableOrderBySort(isVisable);
    }

    @Override
    public Page<HelpCenter> findAll(Pageable pageable) {
        return helpCenterRepository.findAll(pageable);
    }

    @Override
    public void saveHelpCenter(HelpCenter helpCenter) {
        helpCenterRepository.saveAndFlush(helpCenter);
    }

    @Override
    public void batchRemoveHelpCenter(List<String> ids) {
        helpCenterRepository.batchRemoveHelpCenter(ids);
    }

    @Override
    public HelpCenter findById(String id) {
        return helpCenterRepository.findById(id);
    }
}
