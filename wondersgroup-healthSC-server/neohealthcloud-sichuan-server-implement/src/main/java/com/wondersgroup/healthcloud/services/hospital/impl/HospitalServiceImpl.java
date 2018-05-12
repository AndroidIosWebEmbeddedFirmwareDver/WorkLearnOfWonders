package com.wondersgroup.healthcloud.services.hospital.impl;

import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.entity.pay.PayOrder;
import com.wondersgroup.healthcloud.jpa.repository.hospital.HospitalRepository;
import com.wondersgroup.healthcloud.services.hospital.HospitalService;
import com.wondersgroup.healthcloud.services.hospital.dto.DicCityInfoDTO;
import com.wondersgroup.healthcloud.services.pay.PayOrderDTO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dukuanxin on 2016/11/3.
 */
@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Hospital> queryByCityCode(String cityCode, int pageNo, int pageSize) {
        return hospitalRepository.queryByHospitalCode(getCityCode(cityCode), pageNo, pageSize);
    }

    @Override
    public List<Hospital> queryAreaHospital(String cityCode, int pageNo, int pageSize) {
        return hospitalRepository.queryAreaHospital(getCityCode(cityCode), pageNo, pageSize);
    }

    /**
     * @param longitude    经度
     * @param latitude     纬度
     * @param hospitalName 医院名称
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override

    public List<Hospital> queryNearHospital(String cityCode, Double longitude, Double latitude, String hospitalName, int range, int pageNo, int pageSize) {
        if (!StringUtils.isEmpty(hospitalName)) {
            return hospitalRepository.findByName("%" + hospitalName + "%", (pageNo - 1) * pageSize, pageSize + 1);
        } else {
            if (null == longitude || null == latitude) {//只查询成都
                String sql = "select code  from t_dic_area where upper_code = '" + cityCode + "' ";
                List<String> area = jdbcTemplate.query(sql, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getString("code");
                    }
                });

                area.add(cityCode);
                return hospitalRepository.findByCity(area, (pageNo - 1) * pageSize, pageSize + 1);
            } else {
                return hospitalRepository.findByArea(longitude - range / 111.000, longitude + range / 111.000, latitude - range / 111.000,
                        latitude + range / 111.000);
            }
        }
    }

    @Override
    public Hospital queryById(int id) {
        return hospitalRepository.findOne(id);
    }

    @Override
    public List<Map<String, Object>> getAllValidHospitalNameAndCode(String city_code) {
        String sql = "select t.id, t.hospital_code,t.hospital_name,t.city_code from tb_hospital_info t where status = '1' and del_flag = '0'";
        if (StringUtils.isNotEmpty(city_code)) {
            city_code = city_code.replace(" ", "");//防注入
            sql += " and city_code='" + city_code + "'";
        }
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public void updateHospital(int hosId) {
        Hospital hos = hospitalRepository.findOne(hosId);
        hos.setReceiveCount(hos.getReceiveCount() + 1);
        hospitalRepository.saveAndFlush(hos);
    }

    @Override
    public void updateHospital(String hospitalCode) {
        Hospital hospital = hospitalRepository.findByHospitalCodeAndDelFlag(hospitalCode, "0");
        if (hospital != null) {
            hospital.setReceiveCount(hospital.getReceiveCount() + 1);
            hospitalRepository.saveAndFlush(hospital);
        }
    }

    @Override
    public List<DicCityInfoDTO> getDicAreaByUpperCode(String upperCode) {
        Map<String, List<DicCityInfoDTO>> mapList = this.getDicAreaByUpperCodes(Collections.singleton(upperCode));
        return null != mapList && mapList.containsKey(upperCode) ? mapList.get(upperCode) : null;
    }

    @Override
    public Map<String, List<DicCityInfoDTO>> getDicAreaByUpperCodes(Set<String> codes) {
        Map<String, List<DicCityInfoDTO>> result = new LinkedHashMap<>();
        StringBuffer where = new StringBuffer("select upper_code as upperCityCode, code as cityCode ,explain_memo as cityName,`level` from t_dic_area where 1=1 ");
        if (null == codes || codes.isEmpty()) {
            where.append(" AND upper_code=''");
        } else {
            StringBuffer codeStr = new StringBuffer();
            for (String code : codes) {
                codeStr.append(",'" + code + "'");
            }
            where.append(" AND upper_code in (" + codeStr.substring(1) + ")");
        }
        where.append("order by code asc");
        List<DicCityInfoDTO> cityInfoDTOs = jdbcTemplate.query(where.toString(), new BeanPropertyRowMapper<>(DicCityInfoDTO.class));
        if (cityInfoDTOs != null) {
            for (DicCityInfoDTO infoDTO : cityInfoDTOs) {
                List<DicCityInfoDTO> list;
                if (result.containsKey(infoDTO.getUpperCityCode())) {
                    list = result.get(infoDTO.getUpperCityCode());
                } else {
                    list = new ArrayList<>();
                }
                list.add(infoDTO);
                result.put(infoDTO.getUpperCityCode(), list);
            }
        }
        return result;
    }

    @Override
    public List<Hospital> findList(Map<String, Object> param, int page, int pageSize) {
        StringBuffer sb = new StringBuffer("select hos.* from tb_hospital_info hos where 1=1 ");
        List<Object> elementType = new ArrayList<>();
        if (param.containsKey("cityCode")) {
            sb.append(" and hos.city_code=?");
            elementType.add(param.get("cityCode").toString());
        }
        if (param.containsKey("hospitalName")) {
            sb.append(" and hos.hospital_name like ?");
            elementType.add("%" + param.get("hospitalName").toString() + "%");
        }
        if (param.containsKey("delFlag")) {
            sb.append(" and hos.del_flag=?");
            elementType.add(param.get("delFlag").toString());
        }
        sb.append(" order by del_flag asc, hos.id desc ");
        sb.append(" limit ?,?");
        elementType.add((page - 1) * pageSize);
        elementType.add(pageSize);
        List<Hospital> list = jdbcTemplate.query(sb.toString(), elementType.toArray(), new BeanPropertyRowMapper<>(Hospital.class));
        return list;
    }

    @Override
    public int countByMap(Map<String, Object> param) {
        StringBuffer sb = new StringBuffer("select count(*) from tb_hospital_info hos where 1=1 ");
        List<Object> elementType = new ArrayList<>();
        if (param.containsKey("cityCode")) {
            sb.append(" and hos.city_code=?");
            elementType.add(param.get("cityCode").toString());
        }
        if (param.containsKey("hospitalName")) {
            sb.append(" and hos.hospital_name like ?");
            elementType.add("%" + param.get("hospitalName").toString() + "%");
        }
        if (param.containsKey("delFlag")) {
            sb.append(" and hos.del_flag=?");
            elementType.add(param.get("delFlag").toString());
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), elementType.toArray(), Integer.class);
        return null == count ? 0 : count;
    }

    @Override
    public Hospital findByHospitalCode(String hospitalCode) {
        return hospitalRepository.findByHospitalCodeAndDelFlag(hospitalCode, "0");
    }

    @Override
    public Hospital findByHospitalId(Integer hospitalid) {
        return hospitalRepository.findOne(hospitalid);
    }

    @Override
    @Transactional
    public void saveHospital(Hospital hospital) {
        if (StringUtils.isEmpty(hospital.getHospitalCode())) {
            throw new CommonException(2001, "医院编码不能为空");
        }
        Hospital oldHospital = hospitalRepository.findByHospitalCode(hospital.getHospitalCode());
        Date nowDate = new Date();
        if (null != oldHospital) {
            hospital.setId(oldHospital.getId());
            if (StringUtils.isEmpty(hospital.getHospitalPhone())) {
                hospital.setHospitalPhone(oldHospital.getHospitalPhone());
            }
            hospital.setReceiveCount(oldHospital.getReceiveCount());
            hospital.setCreateTime(oldHospital.getCreateTime());
        } else {
            hospital.setCreateTime(nowDate);
            if (StringUtils.isEmpty(hospital.getCityCode()) || StringUtils.isEmpty(hospital.getHospitalName())) {
                throw new CommonException(2002, "非空字段不能为空");
            }
        }
        hospital.setCustomEmails(coverTextEmail(hospital.getCustomEmails()));
        if (StringUtils.isEmpty(hospital.getCustomEmails())) {
            hospital.setIsOpenEmails(0);
        }
        hospital.setUpdateTime(nowDate);
        hospitalRepository.save(hospital);
    }

    /**
     * 把前端传递过来的text格式个email列表验证后拼接
     *
     * @param oldEmailStr
     * @return newEmailsStr
     */
    private String coverTextEmail(String oldEmailStr) {
        StringBuffer sb = new StringBuffer();
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(check);
        if (StringUtils.isNotEmpty(oldEmailStr)) {
            oldEmailStr = oldEmailStr.replace("\n+", "\n");
            oldEmailStr = oldEmailStr.replace(" +", "");
            //格式校验与处理
            String[] emailsOriginalArr = oldEmailStr.split("\n|\t|,|;");
            for (String emailTmp : emailsOriginalArr) {
                if (pattern.matcher(emailTmp).matches()) {
                    sb.append("," + emailTmp);
                }
            }
        }
        return sb.length() > 0 ? sb.substring(1) : "";
    }

    @Override
    public PayOrderDTO getLianPayParam(PayOrder payOrder) {
        PayOrderDTO payOrderDTO = new PayOrderDTO(payOrder);
        if (payOrderDTO.getHospitalCode() != null) {
            Hospital hospital = hospitalRepository.findByHospitalCode(payOrderDTO.getHospitalCode());
            payOrderDTO.setSubmerno(hospital.getAppid());
            payOrderDTO.setAppid(hospital.getAppid());
        }
        return payOrderDTO;
    }

    @Override
    public void upHsopitalCiyCode(Integer id, String cityCode) {
        Hospital hospital = hospitalRepository.findOne(id);
        hospital.setCityCode(cityCode);
        hospitalRepository.saveAndFlush(hospital);
    }

    @Override
    public Hospital findHospitalByAppid(String appid) {

        List<Hospital> hospitals = hospitalRepository.findByAppid(appid);

        if (hospitals != null && hospitals.size() > 0) {
            return hospitalRepository.findByAppid(appid).get(0);
        } else {
            return null;
        }
    }


    private List<String> getCityCode(String cityCode) {
        String sql = "select code  from t_dic_area where upper_code = '" + cityCode + "' ";
        List<String> area = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("code");
            }
        });

        area.add(cityCode);
        return area;
    }
}
