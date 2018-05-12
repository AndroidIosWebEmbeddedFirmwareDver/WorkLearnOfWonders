package com.wondersgroup.healthcloud.services.evaluate.impl;

import com.wondersgroup.healthcloud.jpa.entity.evaluate.EvaluateHospital;
import com.wondersgroup.healthcloud.jpa.repository.evaluate.EvaluateHospitalRepository;
import com.wondersgroup.healthcloud.services.evaluate.EvaluateHospitalService;
import com.wondersgroup.healthcloud.services.evaluate.dto.AppEvaluateHospitalListDTO;
import com.wondersgroup.healthcloud.services.evaluate.dto.EvaluateHospitalDTO;
import com.wondersgroup.healthcloud.services.evaluate.exception.ErrorEvaluateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by longshasha on 16/11/2.
 * 医院评价
 */
@Service("evaluateHospitalServiceImpl")
public class EvaluateHospitalServiceImpl implements EvaluateHospitalService {

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private EvaluateHospitalRepository evaluateHospitalRepository;

    private String evaluateSql = " select e.id,e.uid,u.mobile," +
            " e.hospital_id,h.hospital_name,e.content," +
            " e.`status`,e.is_top,date_format(e.create_time,'%Y-%m-%d %H:%i:%s') as create_time  " +
            " from tb_evaluate_hospital e " +
            " left join tb_user_account u on e.uid = u.id " +
            " left join tb_hospital_info h on e.hospital_id = h.id ";

    @Override
    public void publishEvaluateHospital(EvaluateHospital evaluateHospital) {
        Date now = new Date();
        evaluateHospital.setCreateTime(now);
        evaluateHospital.setUpdateTime(now);
        evaluateHospital.setStatus(0);
        evaluateHospitalRepository.saveAndFlush(evaluateHospital);
    }

    /**
     * 后台置顶与取消置顶
     *
     * @param isTop
     * @param id
     * @return
     */
    @Override
    public Boolean setIsTopStatusById(Integer isTop, Integer id) {
        if (isTop == 1) {
            int topCount = evaluateHospitalRepository.countTopEvaluate();
            if (topCount >= 5) {
                throw new ErrorEvaluateException("只能置顶5条数据");
            }
        }
        evaluateHospitalRepository.setIsTopById(isTop, id);
        return true;
    }

    @Override
    public List<EvaluateHospitalDTO> findEvaluateHospitalListByPager(int pageNum, int size, Map parameter) {
        String sql = evaluateSql + " where 1=1  " +
                getWhereSqlByParameter(parameter)
                + " order by e.is_top desc,e.create_time desc "
                + " LIMIT " + (pageNum - 1) * size + "," + size;
        RowMapper<EvaluateHospitalDTO> rowMapper = new EvaluateHospitalRowMapper();
        List<EvaluateHospitalDTO> evaluateHospitalDTOs = jt.query(sql, rowMapper);
        return evaluateHospitalDTOs;
    }

    @Override
    public List<AppEvaluateHospitalListDTO> findValidListByHospitalId(int hospitalId, int page, int pageSize) {
        String evaluateSql = " select e.id,e.uid, u.mobile, e.content,u.name as nickName, e.create_time as createTime" +
                " from tb_evaluate_hospital e " +
                " left join tb_user_account u on e.uid = u.id " +
                " where e.hospital_id=? and e.status=1 " +
                " ORDER BY e.is_top DESC, e.create_time DESC " +
                " LIMIT ?, ?";
        Object[] parms = new Object[]{hospitalId, (page - 1) * pageSize, pageSize + 1};
        return jt.query(evaluateSql, parms, new BeanPropertyRowMapper<>(AppEvaluateHospitalListDTO.class));
    }

    @Override
    public int countValidEvaluateHospital(int hospitalId) {
        String evaluateSql = " select count(*) as num from tb_evaluate_hospital e " +
                " where e.hospital_id=? and e.status=1 ";
        Integer count = jt.queryForObject(evaluateSql, new Object[]{hospitalId}, Integer.class);
        return null == count ? 0 : count;
    }

    @Override
    public int countEvaluateHospitalListByPager(Map parameter) {
        String sql = "select count(e.id) " +
                " from tb_evaluate_hospital e " +
                " left join tb_user_account u on e.uid = u.id " +
                " left join tb_hospital_info h on e.hospital_id = h.id " +
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
                bf.append(" and e.status = " + parameter.get("status").toString());
            }
            if (parameter.containsKey("isTop") && StringUtils.isNotBlank(parameter.get("isTop").toString())) {
                bf.append(" and e.is_top = " + parameter.get("isTop").toString());
            }
        }
        return bf.toString();
    }

    private class EvaluateHospitalRowMapper implements RowMapper<EvaluateHospitalDTO> {

        @Override
        public EvaluateHospitalDTO mapRow(ResultSet rs, int i) throws SQLException {
            EvaluateHospitalDTO evaluateHospital = new EvaluateHospitalDTO();
            evaluateHospital.setId(Integer.valueOf(rs.getString("id")));
            evaluateHospital.setUid(rs.getString("uid"));
            evaluateHospital.setMobile(rs.getString("mobile"));
            evaluateHospital.setHospitalId(Integer.valueOf(rs.getString("hospital_id")));
            evaluateHospital.setHospitalName(rs.getString("hospital_name"));
            evaluateHospital.setStatus(rs.getString("status"));
            evaluateHospital.setContent(rs.getString("content"));
            evaluateHospital.setIsTop(Integer.valueOf(rs.getString("is_top")));
            evaluateHospital.setCreateTime(rs.getString("create_time"));
            return evaluateHospital;
        }
    }
}
