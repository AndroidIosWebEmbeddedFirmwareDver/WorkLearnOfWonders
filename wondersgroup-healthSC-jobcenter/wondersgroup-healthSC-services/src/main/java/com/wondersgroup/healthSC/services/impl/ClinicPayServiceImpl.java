package com.wondersgroup.healthSC.services.impl;

import com.wondersgroup.healthSC.services.interfaces.ClinicPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nick on 2016/11/17.
 * @author nick
 */
@Service
public class ClinicPayServiceImpl implements ClinicPayService{

    @Autowired
    private PayService payService;

    @Override
    public void handlerNotPayClinicOrder() {
        payService.handleNotPayClinicOrder();
    }
}
