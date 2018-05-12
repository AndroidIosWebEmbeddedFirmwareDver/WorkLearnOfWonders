package com.wondersgroup.healthcloud.services.user.impl;

import com.wondersgroup.healthcloud.jpa.entity.user.OnlineMedicalCardTypes;
import com.wondersgroup.healthcloud.jpa.entity.user.UserOnlineMedicalCards;
import com.wondersgroup.healthcloud.jpa.repository.user.UserOnlineMedicalCardsRepository;
import com.wondersgroup.healthcloud.services.user.UserOnlineMedicalCardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description a
 * @Author
 * @Create 2018-04-13 下午3:54
 **/

@Service("userOnlineMedicalCardsServiceImpl")
@Transactional

public class UserOnlineMedicalCardsServiceImpl implements UserOnlineMedicalCardsService {

    @Autowired
    UserOnlineMedicalCardsRepository userOnlineMedicalCardsRepository;


    @Override
    public UserOnlineMedicalCards create(UserOnlineMedicalCards input) throws Exception {
        if (input == null) {
            throw new Exception();
        }
        input.checkCreateData();
        //TODO;特殊逻辑处理
        //居民健康卡
        if (input.getCard_type_code().equals(OnlineMedicalCardTypes.RESIDENTHEALTHCARD)) {
            List<UserOnlineMedicalCards> a = userOnlineMedicalCardsRepository.findAllHealthCardsByUidAndCardTypeCode(input.getUid(), input.getCard_type_code(), 0);
            if (a != null && a.size() >= 1) {
                throw new Exception("居民健康卡只能绑定一张");
            }
        }
        //院内就诊卡
        else if (input.getCard_type_code().equals(OnlineMedicalCardTypes.HOSPITALMEDICALCARD)) {
//            List<UserOnlineMedicalCards> a = userOnlineMedicalCardsRepository.findAllByUidAndHospitalCodeAndMediaclCardNoAndCardTypeCode(input.getUid(), input.getHospital_code(), input
//                    .getMediacl_card_no(), input
//                    .getCard_type_code(), 0);
//            if (a != null && a.size() >= 1) {
//                throw new Exception("同一家医院相同卡号的院内就诊卡只能绑定一张");
//            } else {
                if(userOnlineMedicalCardsRepository.findMedicalCardByUidAndHospital(input.getUid(), input.getHospital_code(), 0) > 0){
                    throw new Exception("同一家医院最多只能绑定一张就诊卡");
                }

                List<UserOnlineMedicalCards> medicalCards = userOnlineMedicalCardsRepository.findAllByUidAndCardTypeCode(input.getUid(), input.getCard_type_code(), 0);
                if (medicalCards != null && medicalCards.size() >= 5) {
                    throw new Exception("院内就诊卡最多只能绑定五张");
                }
//            }
        }
        return userOnlineMedicalCardsRepository.save(input);
    }

    @Override
    public UserOnlineMedicalCards update(UserOnlineMedicalCards input) throws Exception {
        if (input == null) {
            throw new Exception();
        }
        input.checkupdateData();
        return userOnlineMedicalCardsRepository.save(input);
    }

    @Override
    public UserOnlineMedicalCards updateLogicDelete(UserOnlineMedicalCards input) throws Exception {
        if (input == null) {
            throw new Exception();
        }
        input.checkDeleteData();
//        userOnlineMedicalCardsRepository.updateLogicDelete(input.getIs_deleted(), input.getId(), input.getUid());
        userOnlineMedicalCardsRepository.updateLogicDelete(1, input.getId(), input.getUid());
        return input;
    }

    @Override
    public UserOnlineMedicalCards findOne(UserOnlineMedicalCards input) throws Exception {
        return null;
    }

    @Override
    public List<UserOnlineMedicalCards> findAllByUid(UserOnlineMedicalCards input) throws Exception {
        if (input == null) {
            throw new Exception("参数错误");
        }
        input.checkFindAllByUid();
        return userOnlineMedicalCardsRepository.findAllByUid(input.getUid(), 0);
    }

    @Override
    public List<UserOnlineMedicalCards> findAllByUidAndHospitalCode(UserOnlineMedicalCards input) throws Exception {
        if (input == null) {
            throw new Exception("参数错误");
        }
        input.checkFindAllByUidAndHospitalCode();
        return userOnlineMedicalCardsRepository.findAllByUidAndHospitalCode(input.getUid(), input.getHospital_code(), 0);
    }

    @Override
    public List<UserOnlineMedicalCards> findAllByUidAndCardTypeCode(UserOnlineMedicalCards input) throws Exception {
        if (input == null) {
            throw new Exception("参数错误");
        }
        input.checkFindAllByUidAndCardTypeCode();
        return userOnlineMedicalCardsRepository.findAllByUidAndCardTypeCode(input.getUid(), input.getCard_type_code(), 0);
    }

    @Override
    public List<UserOnlineMedicalCards> findAllByUidAndHospitalCodeAndCardTypeCode(UserOnlineMedicalCards input) throws Exception {
        if (input == null) {
            throw new Exception("参数错误");
        }
        input.checkFindAllByUidAndHospitalCodeAndCardTypeCode();
        if (input.getCard_type_code().equals(OnlineMedicalCardTypes.RESIDENTHEALTHCARD)) {
            return userOnlineMedicalCardsRepository.findAllByUidAndCardTypeCode(input.getUid(), input.getCard_type_code(), 0);
        }
        return userOnlineMedicalCardsRepository.findAllByUidAndHospitalCodeAndCardTypeCode(input.getUid(), input.getHospital_code(), input.getCard_type_code(), 0);
    }

    @Override
    public List<UserOnlineMedicalCards> findAll() throws Exception {
        return null;
    }
}
