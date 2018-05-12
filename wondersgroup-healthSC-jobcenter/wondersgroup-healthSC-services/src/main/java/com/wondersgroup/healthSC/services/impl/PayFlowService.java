package com.wondersgroup.healthSC.services.impl;

import com.wondersgroup.healthSC.common.util.DateFormatter;
import com.wondersgroup.healthSC.services.impl.dto.PayFlowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by dukuanxin on 2017/4/25.
 */
@Component
public class PayFlowService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PayFlowDTO> queryPayFlowByHosCode(String hosCode, Date startDate, Date endDate){
        String startTime = DateFormatter.format(startDate, "yyyy-MM-dd 00:00:00");
        String endTime = DateFormatter.format(endDate, "yyyy-MM-dd 23:59:59");
        String sql="SELECT t3.update_time,t3.show_order_id,cost,pos_id,hos_name,merchant_id, '成功' AS status FROM " +
                "tb_order_info t1 INNER JOIN tb_pay_hospital_merchant t2 ON t1.hos_code=t2.hospital_id " +
                "INNER JOIN tb_pay_order t3 ON t1.id=t3.subject_id WHERE t3.status='SUCCESS'AND t1.hos_code=? AND t3.update_time >=? AND t3.update_time<=?";
        Object[] parms = new Object[]{hosCode,startTime, endTime};
        return jdbcTemplate.queryForList(sql,parms,PayFlowDTO.class);
    }
}
