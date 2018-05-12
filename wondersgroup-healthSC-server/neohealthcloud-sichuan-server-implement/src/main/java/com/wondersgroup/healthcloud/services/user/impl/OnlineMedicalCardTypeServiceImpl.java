package com.wondersgroup.healthcloud.services.user.impl;

import com.wondersgroup.healthcloud.jpa.entity.user.OnlineMedicalCardType;
import com.wondersgroup.healthcloud.jpa.repository.user.OnlineMedicalCardTypeRepository;
import com.wondersgroup.healthcloud.services.user.OnlineMedicalCardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description a
 * @Author
 * @Create 2018-04-13 下午3:12
 **/

@Service("onlineMedicalCardTypeServiceImpl")
@Transactional
public class OnlineMedicalCardTypeServiceImpl implements OnlineMedicalCardTypeService {

    @Autowired
    OnlineMedicalCardTypeRepository onlineMedicalCardTypeRepository;

    @Override
    public OnlineMedicalCardType create(String card_type_code, String card_type_name) throws Exception {
        if (card_type_code != null && card_type_code.length() > 0 && card_type_name != null && card_type_name.length() > 0) {
            if (onlineMedicalCardTypeRepository.findOneByCardTypeCode(card_type_code, 0) != null) {
                throw new Exception("相同编码类型的数据已经存在");
            }
            return onlineMedicalCardTypeRepository.save(new OnlineMedicalCardType(card_type_code, card_type_name));
        } else {
            throw new Exception("参数有误");
        }
    }

    @Override
    public OnlineMedicalCardType update(String card_type_code, String card_type_name) throws Exception {
        if (card_type_code != null && card_type_code.length() > 0 && card_type_name != null && card_type_name.length() > 0) {

            OnlineMedicalCardType a = onlineMedicalCardTypeRepository.findOneByCardTypeCode(card_type_code, 0);
            if (a == null) {
                throw new Exception("者相同编码类型的数据不存在");
            }
            return onlineMedicalCardTypeRepository.save(a.update(card_type_code, card_type_name));
        } else {
            throw new Exception("参数有误");
        }
    }

    @Override
    public OnlineMedicalCardType findOne(String card_type_code) throws Exception {
        if (card_type_code != null && card_type_code.length() > 0) {
            OnlineMedicalCardType a = onlineMedicalCardTypeRepository.findOneByCardTypeCode(card_type_code, 0);
            if (a == null) {
                throw new Exception("数据不存在");
            }
            return a;
        } else {
            throw new Exception("参数有误");
        }
    }

    @Override
    public List<OnlineMedicalCardType> findAll() throws Exception {
        return onlineMedicalCardTypeRepository.findAll();
    }
}
