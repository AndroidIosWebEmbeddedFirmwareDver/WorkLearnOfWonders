package com.wondersgroup.healthcloud.jpa.repository.user;

import com.wondersgroup.healthcloud.jpa.entity.user.OnlineMedicalCardType;
import com.wondersgroup.healthcloud.jpa.entity.user.UserOnlineMedicalCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OnlineMedicalCardTypeRepository extends JpaRepository<OnlineMedicalCardType, String> {

    @Query("select a from OnlineMedicalCardType a where a.is_deleted=?1")
    List<OnlineMedicalCardType> findAllByIs_deleted(int is_deleted);


    @Query("select a from OnlineMedicalCardType a where  a.card_type_code=?1 and a.is_deleted=?2")
    OnlineMedicalCardType findOneByCardTypeCode(String card_type_code, int is_deleted);


}