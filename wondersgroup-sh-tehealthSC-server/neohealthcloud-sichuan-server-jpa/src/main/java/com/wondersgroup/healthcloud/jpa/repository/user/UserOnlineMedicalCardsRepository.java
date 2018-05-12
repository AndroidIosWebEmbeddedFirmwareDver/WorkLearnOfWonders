package com.wondersgroup.healthcloud.jpa.repository.user;

import com.wondersgroup.healthcloud.jpa.entity.user.UserOnlineMedicalCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserOnlineMedicalCardsRepository extends JpaRepository<UserOnlineMedicalCards, String> {

    @Query("select a from UserOnlineMedicalCards a where a.is_deleted=?1")
    List<UserOnlineMedicalCards> findAllByIs_deleted(int is_deleted);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update tb_user_online_medical_cards entity set entity.is_deleted =?1 where entity.id = ?2 and  entity.uid = ?3", nativeQuery = true)
    public int updateLogicDelete(int is_deleted, long id, String uid);



    /**
     * 查找居民健康卡记录
     *
     * @param uid
     * @param card_type_code
     * @param is_deleted
     * @return
     */
    @Query("select a from UserOnlineMedicalCards a where a.uid=?1 and a.card_type_code=?2 and is_deleted=?3")
    List<UserOnlineMedicalCards> findAllHealthCardsByUidAndCardTypeCode(String uid,  String card_type_code, int is_deleted);



    /**
     * 查找协调卡号相同类型的记录
     *
     * @param uid
     * @param mediacl_card_no
     * @param card_type_code
     * @param is_deleted
     * @return
     */
    @Query("select a from UserOnlineMedicalCards a where a.uid=?1 and a.mediacl_card_no=?2 and a.card_type_code=?3 and is_deleted=?4")
    List<UserOnlineMedicalCards> findAllByUidAndMediaclCardNoAndCardTypeCode(String uid, String mediacl_card_no, String card_type_code, int is_deleted);


    /**
     * 查找协同一个医院内相同卡类型相同卡号的记录
     *
     * @param uid
     * @param hospital_code
     * @param mediacl_card_no
     * @param card_type_code
     * @param is_deleted
     * @return
     */
    @Query("select a from UserOnlineMedicalCards a where a.uid=?1 and a.hospital_code=?2 and  a.mediacl_card_no=?3 and a.card_type_code=?4 and is_deleted=?5")
    List<UserOnlineMedicalCards> findAllByUidAndHospitalCodeAndMediaclCardNoAndCardTypeCode(String uid, String hospital_code, String mediacl_card_no, String card_type_code, int is_deleted);



    /**
     * 统计协同一个家医院人员绑定的就诊卡数量
     *
     * @param uid
     * @param hospital_code
     * @param is_deleted
     * @return
     */
    @Query(value = "select count(*) from tb_user_online_medical_cards a where a.uid=?1 and a.hospital_code=?2 and is_deleted=?3", nativeQuery = true)
    int findMedicalCardByUidAndHospital(String uid, String hospital_code, int is_deleted);


    /**
     * 查找协同一个医院内相同卡类型相同卡号的记录条数
     * @param uid
     * @param hospital_code
     * @param mediacl_card_no
     * @param card_type_code
     * @param is_deleted
     * @return
     */
    @Query(value = "select count(*) from tb_user_online_medical_cards a where a.uid=?1 and a.hospital_code=?2 and  a.mediacl_card_no=?3 and a.card_type_code=?4 and is_deleted=?5", nativeQuery = true)
    int findCountByUidAndHospitalCodeAndMediaclCardNoAndCardTypeCode(String uid, String hospital_code, String mediacl_card_no, String card_type_code, int is_deleted);


    /**
     * 通过UID查询所有卡记录
     *
     * @param uid
     * @param is_deleted
     * @return
     */
    @Query("select a from UserOnlineMedicalCards a where a.uid=?1  and a.is_deleted=?2")
    List<UserOnlineMedicalCards> findAllByUid(String uid, int is_deleted);


    /**
     * 通过UID、hospital_code查询所有卡记录
     *
     * @param uid
     * @param hospital_code
     * @param is_deleted
     * @return
     */
    @Query("select a from UserOnlineMedicalCards a where a.uid=?1 and a.hospital_code=?2 and a.is_deleted=?3")
    List<UserOnlineMedicalCards> findAllByUidAndHospitalCode(String uid, String hospital_code, int is_deleted);


    /**
     * 通过UID、card_type_code 查询所有卡记录
     *
     * @param uid
     * @param card_type_code
     * @param is_deleted
     * @return
     */
    @Query("select a from UserOnlineMedicalCards a where a.uid=?1 and a.card_type_code=?2 and a.is_deleted=?3")
    List<UserOnlineMedicalCards> findAllByUidAndCardTypeCode(String uid, String card_type_code, int is_deleted);


    /**
     * 通过UID、hospital_code、card_type_code 查询所有卡记录
     *
     * @param uid
     * @param hospital_code
     * @param card_type_code
     * @param is_deleted
     * @return
     */
    @Query("select a from UserOnlineMedicalCards a where a.uid=?1 and a.hospital_code=?2 and a.card_type_code=?3 and a.is_deleted=?4")
    List<UserOnlineMedicalCards> findAllByUidAndHospitalCodeAndCardTypeCode(String uid, String hospital_code, String card_type_code, int is_deleted);

}