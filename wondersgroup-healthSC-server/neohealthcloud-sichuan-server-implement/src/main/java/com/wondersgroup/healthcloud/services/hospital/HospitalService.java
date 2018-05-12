package com.wondersgroup.healthcloud.services.hospital;

import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.services.hospital.dto.DicCityInfoDTO;
import com.wondersgroup.healthcloud.services.pay.PayOrderDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dukuanxin on 2016/11/3.
 */
public interface HospitalService {

    List<Hospital> queryByCityCode(String cityCode, int pageNo, int pageSize);

    List<Hospital> queryAreaHospital(String cityCode, int pageNo, int pageSize);

    Hospital queryById(int id);

    /**
     * 更新医院预约量
     *
     * @param hosId
     */
    void updateHospital(int hosId);

    /**
     * 根据his医院code更新医院接诊量
     *
     * @param hospitalCode
     */
    void updateHospital(String hospitalCode);

    List<Hospital> queryNearHospital(String cityCode, Double longitude, Double latitude, String hospitalName, int range, int pageNo, int pageSize);

    List<DicCityInfoDTO> getDicAreaByUpperCode(String upperCode);

    Map<String, List<DicCityInfoDTO>> getDicAreaByUpperCodes(Set<String> upperCodes);

    /**
     * param支持
     * {
     * "cityCode":"",
     * "hospitalName":""
     * "delFlag":"" [1/0]
     * }
     */
    List<Hospital> findList(Map<String, Object> param, int page, int pageSize);

    /**
     * param支持
     * {
     * "cityCode":"",
     * "hospitalName":""
     * "delFlag":"" [1/0]
     * }
     */
    int countByMap(Map<String, Object> param);

    /**
     * map:{
     * id:
     * hospital_code:
     * hospital_name:
     * city_code:
     * }
     * city_code 不指定获取全部city
     */
    List<Map<String, Object>> getAllValidHospitalNameAndCode(String city_code);

    Hospital findByHospitalCode(String hospitalCode);

    Hospital findByHospitalId(Integer hospitalid);

    void saveHospital(Hospital hospital);

    /**
     * 获取链支付相关参数
     *
     * @param payOrder
     * @return
     */
    public PayOrderDTO getLianPayParam(PayOrder payOrder);

    void upHsopitalCiyCode(Integer id, String cityCode);

    /**
     * 通过商号查询医院信息
     *
     * @param appid
     * @return
     */
    Hospital findHospitalByAppid(String appid);
}
