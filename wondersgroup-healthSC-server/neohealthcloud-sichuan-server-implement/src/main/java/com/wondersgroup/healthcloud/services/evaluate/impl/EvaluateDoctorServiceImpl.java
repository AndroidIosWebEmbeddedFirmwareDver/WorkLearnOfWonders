package com.wondersgroup.healthcloud.services.evaluate.impl;

import com.wondersgroup.healthcloud.exceptions.CommonException;
import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateDoctor;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Doctor;
import com.wondersgroup.healthcloud.jpa.entity.hospital.Hospital;
import com.wondersgroup.healthcloud.jpa.entity.order.OrderInfo;
import com.wondersgroup.healthcloud.jpa.repository.evaluate.EvaluateDoctorRepository;
import com.wondersgroup.healthcloud.jpa.repository.hospital.HospitalRepository;
import com.wondersgroup.healthcloud.services.evaluate.EvaluateDoctorService;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateDoctorListDTO;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateDoctorDTO;
import com.wondersgroup.healthcloud.services.evaluate.exception.ErrorEvaluateException;
import com.wondersgroup.healthcloud.services.hospital.DoctorService;
import com.wondersgroup.healthcloud.services.order.OrderInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by longshasha on 16/11/2.
 * 医生评价
 */
@Service("evaluateDoctorServiceImpl")
public class EvaluateDoctorServiceImpl implements EvaluateDoctorService {

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private EvaluateDoctorRepository evaluateDoctorRepository;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HospitalRepository hospitalRepository;

    private String evaluateSql = " select ed.id,ed.uid,u.mobile,ed.doctor_id,d.doctor_name," +
            " ed.hospital_id,h.hospital_name,ed.content," +
            " ed.star,ed.`status`,ed.is_top,date_format(ed.create_time,'%Y-%m-%d %H:%i:%s') as create_time " +
            " from tb_evaluate_doctor ed " +
            " left join tb_user_account u on ed.uid = u.id " +
            " left join tb_hospital_info h on ed.hospital_id = h.id " +
            " left join tb_doctor_info d on ed.doctor_id = d.id ";

    @Override
    public List<AppEvaluateDoctorListDTO> findValidListByDoctorId(int doctorId, int page, int pageSize) {
        String evaluateSql = " select ed.id,ed.uid,u.mobile,u.name as nickName,ed.content,ed.create_time as createTime " +
                " from tb_evaluate_doctor ed " +
                " left join tb_user_account u on ed.uid = u.id " +
                " where ed.doctor_id=? and ed.status=1 order by ed.is_top desc, ed.create_time desc " +
                " limit ?,?";
        Object[] parms = new Object[]{doctorId, (page - 1) * pageSize, pageSize + 1};
        return jt.query(evaluateSql, parms, new BeanPropertyRowMapper<>(AppEvaluateDoctorListDTO.class));
    }

    @Override
    public int countValidEvaluateDoctor(int doctorId) {
        String evaluateSql = " select count(*) as num from tb_evaluate_doctor ed " +
                " where ed.doctor_id=? and ed.status=1 ";
        Integer count = jt.queryForObject(evaluateSql, new Object[]{doctorId}, Integer.class);
        return null == count ? 0 : count;
    }

    @Override
    @Transactional
    public void publishEvaluateDoctor(EvaluateDoctor evaluateDoctor) {
        if (null == evaluateDoctor || StringUtils.isEmpty(evaluateDoctor.getOrderId())) {
            throw new CommonException(2001, "评价预约单无效");
        }
        OrderInfo orderInfo = orderInfoService.detail(evaluateDoctor.getOrderId());
        if (orderInfo == null || !orderInfo.getState().equals("4")) {
            throw new CommonException(2002, "评价预约单无效");
        }
        if (StringUtils.isEmpty(evaluateDoctor.getUid()) || !orderInfo.getUid().equals(evaluateDoctor.getUid())) {
            throw new CommonException(2004, "您不能评价该预约单");
        }
        if (orderInfo.getIsEvaluated() == 1) {
            throw new CommonException(2003, "您已评价过改预约单");
        }
        Doctor doctor = doctorService.queryByDoctorCode(orderInfo.getHosCode(), orderInfo.getDeptCode(), orderInfo.getDoctCode());
        if (null == doctor) {
            throw new CommonException(2005, "医生无效");
        }
        Hospital hospital = hospitalRepository.findByHospitalCode(doctor.getHospitalCode());
        Date now = new Date();
        evaluateDoctor.setDoctorId(doctor.getId());
        evaluateDoctor.setCreateTime(now);
        evaluateDoctor.setHospitalId(hospital.getId());
        evaluateDoctor.setUpdateTime(now);
        evaluateDoctor.setIsTop(0);
        evaluateDoctor.setStatus(0);
        evaluateDoctorRepository.save(evaluateDoctor);
        //同步更新订单评价状态
        orderInfo.setIsEvaluated(1);
        orderInfoService.saveAndUpdate(orderInfo);
    }

    /**
     * 后台设置置顶与取消置顶功能
     *
     * @param isTop
     * @param id
     * @return
     */
    @Override
    public Boolean setIsTopStatusById(Integer isTop, Integer id) {
        if (isTop == 1) {
            int topCount = evaluateDoctorRepository.countTopEvaluate();
            if (topCount >= 5) {
                throw new ErrorEvaluateException("只能置顶5条数据");
            }
        }
        evaluateDoctorRepository.setIsTopById(isTop, id);
        return true;
    }

    @Override
    public List<EvaluateDoctorDTO> findEvaluateDoctorListByPager(int pageNum, int size, Map parameter) {
        String sql = evaluateSql + " where 1=1  " +
                getWhereSqlByParameter(parameter)
                + " order by ed.is_top desc,ed.create_time desc "
                + " LIMIT " + (pageNum - 1) * size + "," + size;
        RowMapper<EvaluateDoctorDTO> rowMapper = new EvaluateDoctorRowMapper();
        List<EvaluateDoctorDTO> evaluateDoctors = jt.query(sql, rowMapper);
        return evaluateDoctors;
    }

    @Override
    public int countEvaluateDoctorListByPager(Map parameter) {
        String sql = "select count(ed.id) " +
                " from tb_evaluate_doctor ed " +
                " left join tb_user_account u on ed.uid = u.id " +
                " left join tb_hospital_info h on ed.hospital_id = h.id " +
                " left join tb_doctor_info d on ed.doctor_id = d.id " +
                " where 1 = 1 " + getWhereSqlByParameter(parameter);
        Integer count = jt.queryForObject(sql, Integer.class);
        return count == null ? 0 : count;
    }

    private String getWhereSqlByParameter(Map parameter) {
        StringBuffer bf = new StringBuffer();
        if (parameter.size() > 0) {
            if (parameter.containsKey("mobile") && StringUtils.isNotBlank(parameter.get("mobile").toString())) {
                bf.append(" and u.mobile like '%" + parameter.get("mobile").toString() + "%' ");
            }
            if (parameter.containsKey("hospitalName") && StringUtils.isNotBlank(parameter.get("hospitalName").toString())) {
                bf.append(" and h.hospital_name like '%" + parameter.get("hospitalName").toString() + "%' ");
            }
            if (parameter.containsKey("status") && StringUtils.isNotBlank(parameter.get("status").toString())) {
                bf.append(" and ed.status = " + parameter.get("status").toString());
            }
            if (parameter.containsKey("isTop") && StringUtils.isNotBlank(parameter.get("isTop").toString())) {
                bf.append(" and ed.is_top = " + parameter.get("isTop").toString());
            }
            if (parameter.containsKey("doctorName") && StringUtils.isNotBlank(parameter.get("doctorName").toString())) {
                bf.append(" and d.doctor_name like '%" + parameter.get("doctorName").toString() + "%' ");
            }
            if (parameter.containsKey("star") && StringUtils.isNotBlank(parameter.get("star").toString())) {
                bf.append(" and ed.star = " + parameter.get("star").toString());
            }
        }
        return bf.toString();
    }


    private class EvaluateDoctorRowMapper implements RowMapper<EvaluateDoctorDTO> {

        @Override
        public EvaluateDoctorDTO mapRow(ResultSet rs, int i) throws SQLException {
            EvaluateDoctorDTO evaluateDoctorDTO = new EvaluateDoctorDTO();
            evaluateDoctorDTO.setId(Integer.valueOf(rs.getString("id")));
            evaluateDoctorDTO.setUid(rs.getString("uid"));
            evaluateDoctorDTO.setMobile(rs.getString("mobile"));
            evaluateDoctorDTO.setDoctorId(rs.getString("doctor_id"));
            evaluateDoctorDTO.setDoctorName(rs.getString("doctor_name"));
            evaluateDoctorDTO.setHospitalId(Integer.valueOf(rs.getString("hospital_id")));
            evaluateDoctorDTO.setHospitalName(rs.getString("hospital_name"));
            evaluateDoctorDTO.setStatus(rs.getString("status"));
            evaluateDoctorDTO.setContent(rs.getString("content"));
            evaluateDoctorDTO.setStar(Integer.valueOf(rs.getString("star")));
            evaluateDoctorDTO.setIsTop(Integer.valueOf(rs.getString("is_top")));
            evaluateDoctorDTO.setCreateTime(rs.getString("create_time"));
            return evaluateDoctorDTO;
        }
    }
}
