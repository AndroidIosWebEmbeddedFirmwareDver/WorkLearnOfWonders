package com.wondersgroup.healthcloud.services.user;


import com.wondersgroup.healthcloud.jpa.entity.user.OnlineMedicalCardType;

import java.util.List;

public interface OnlineMedicalCardTypeService {

    public OnlineMedicalCardType create(String card_type_code, String card_type_name) throws Exception;

    public OnlineMedicalCardType update(String card_type_code, String card_type_name) throws Exception;

    public OnlineMedicalCardType findOne(String card_type_code) throws Exception;

    public List<OnlineMedicalCardType> findAll() throws Exception;


}
