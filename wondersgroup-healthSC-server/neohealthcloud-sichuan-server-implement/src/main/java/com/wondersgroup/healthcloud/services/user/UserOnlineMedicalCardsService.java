package com.wondersgroup.healthcloud.services.user;


import com.wondersgroup.healthcloud.jpa.entity.user.UserOnlineMedicalCards;

import java.util.List;

public interface UserOnlineMedicalCardsService {

    public UserOnlineMedicalCards create(UserOnlineMedicalCards input) throws Exception;

    public UserOnlineMedicalCards update(UserOnlineMedicalCards input) throws Exception;

    public UserOnlineMedicalCards updateLogicDelete(UserOnlineMedicalCards input) throws Exception;

    public UserOnlineMedicalCards findOne(UserOnlineMedicalCards input) throws Exception;

    public List<UserOnlineMedicalCards> findAllByUid(UserOnlineMedicalCards input) throws Exception;

    public List<UserOnlineMedicalCards> findAllByUidAndHospitalCode(UserOnlineMedicalCards input) throws Exception;

    public List<UserOnlineMedicalCards> findAllByUidAndCardTypeCode(UserOnlineMedicalCards input) throws Exception;

    public List<UserOnlineMedicalCards> findAllByUidAndHospitalCodeAndCardTypeCode(UserOnlineMedicalCards input) throws Exception;

    public List<UserOnlineMedicalCards> findAll() throws Exception;


}
