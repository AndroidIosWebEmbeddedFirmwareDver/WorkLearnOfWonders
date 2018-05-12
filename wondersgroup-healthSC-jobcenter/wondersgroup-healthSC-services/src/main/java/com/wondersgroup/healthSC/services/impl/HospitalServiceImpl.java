package com.wondersgroup.healthSC.services.impl;

import com.wondersgroup.healthSC.common.util.UploaderUtil;
import com.wondersgroup.healthSC.services.interfaces.HospitalService;
import com.wondersgroup.healthSC.services.jpa.entity.Hospital;
import com.wondersgroup.healthSC.services.jpa.repository.HospitalRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuchunliu on 2016/11/2.
 */
@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HospitalRepository hospitalRepo;

    @Autowired
    private UploaderUtil uploaderUtil;


    @Override
    public void synchronInfo() {
        String sql = "INSERT INTO tb_hospital_info(\n" +
                " hospital_code, hospital_name, hospital_address, hospital_rule,hospital_desc,\n" +
                " hospital_phone, hospital_grade, is_order_today, status, del_flag, create_time, update_time)\n" +
                " SELECT HOSORGCODE,ORGNAME,SALEAADDR,ORDER_NOTICE,ORG_DESC,\n" +
                " ORG_TEL,ORG_GRADE,ISORDERTODAY,'1','0',NOW(),NOW()\n" +
                " FROM orderweb_md_org \n" +
                " WHERE HOSORGCODE IS NOT NULL AND NOT EXISTS(SELECT id from tb_hospital_info where hospital_code = HOSORGCODE)";
        jdbcTemplate.update(sql);

        this.sysnchronPhoto();
    }

    @Override
    public void synchronInfo(String[] ids){
        for( String id : ids) {
             Hospital hospital = hospitalRepo.findOne(Integer.parseInt(id));
             if(null == hospital){
                 continue;
             }
             Map<String, Object> map = jdbcTemplate.queryForMap("select ORGNAME,SALEAADDR,ORDER_NOTICE,ORG_DESC," +
                    " ORG_TEL,ORG_GRADE,ISORDERTODAY FROM orderweb_md_org WHERE HOSORGCODE = '" + hospital.getHospitalCode() + "' ");


            hospital.setHospitalName(null == map.get("ORGNAME") ? null : map.get("ORGNAME").toString());
            hospital.setHospitalAddress(null == map.get("SALEAADDR") ? null : map.get("SALEAADDR").toString());
            hospital.setHospitalRule(null == map.get("ORDER_NOTICE") ? null : map.get("ORDER_NOTICE").toString());
            hospital.setHospitalDesc(null == map.get("ORG_DESC") ? null : map.get("ORG_DESC").toString());
            hospital.setHospitalPhone(null == map.get("ORG_TEL") ? null : map.get("ORG_TEL").toString());
            hospital.setHospitalGrade(null == map.get("ORG_GRADE")?null: map.get("ORG_GRADE").toString());
            hospital.setIsOrderToday(null == map.get("ISORDERTODAY") ? null : map.get("ISORDERTODAY").toString());
            hospital.setUpdateTime(new Date());
            hospital.setHosptialPhoto(null);
            hospitalRepo.save(hospital);
        }
        this.synchronInfo();
    }

    private void sysnchronPhoto(){
        List<Hospital> list = hospitalRepo.getNoPicHospital();
        if(null == list || 0 == list.size()){
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for(int index = 0;index < list.size();index++){
            executorService.execute(new HospitalThread(list.get(index)));
        }
        executorService.shutdown();
    }


    private class HospitalThread implements Runnable{
        private Hospital hospital;
        HospitalThread(Hospital hospital){
            this.hospital = hospital;
        }
        @Override
        public void run() {
            System.err.println(Thread.currentThread()+"正在执行"+System.currentTimeMillis());
            String sql = "select HOSPHOTO from orderweb_md_org where HOSORGCODE = '"+hospital.getHospitalCode()+"'";
            jdbcTemplate.query(sql, new AbstractLobStreamingResultSetExtractor() {
                @Override
                protected void streamData(ResultSet resultSet) throws SQLException, IOException, DataAccessException {
                    byte[] bytes = new DefaultLobHandler().getBlobAsBytes(resultSet, 1);
                    if (null != bytes && 0 != bytes.length) {
                        String path = uploaderUtil.uploadFile(bytes);
                        if (!StringUtils.isEmpty(path)) {
                            hospital.setHosptialPhoto(path);
                            hospital.setUpdateTime(new Date());
                            hospitalRepo.save(hospital);
                        }
                    }
                }
            });
        }
    }
}
