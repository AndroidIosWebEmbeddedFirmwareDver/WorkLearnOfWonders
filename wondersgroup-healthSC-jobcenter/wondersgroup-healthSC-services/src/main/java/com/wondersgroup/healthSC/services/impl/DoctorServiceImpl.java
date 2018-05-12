package com.wondersgroup.healthSC.services.impl;

import com.wondersgroup.healthSC.common.util.UploaderUtil;
import com.wondersgroup.healthSC.services.interfaces.DoctorService;
import com.wondersgroup.healthSC.services.jpa.entity.Doctor;
import com.wondersgroup.healthSC.services.jpa.entity.Hospital;
import com.wondersgroup.healthSC.services.jpa.repository.DoctorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service("doctorService")
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private UploaderUtil uploaderUtil;

    @Override
    public void synchronInfo() {
        String sql = "INSERT INTO tb_doctor_info(\n" +
                " hospital_code,dept_code,doctor_code,doctor_name,gender,\n" +
                " doctor_title,doctor_desc, expertin, level,del_flag, create_time, update_time)\n" +
                " SELECT HOSORGCODE,HOSDEPTCODE,HOSEMPLCODE,EMPLNAME,SEX,\n" +
                " PLATFORM_PRO_NAME,DOCTOR_DESC,SPECIALTY,VISIT_LEVEL, IFNULL(ISDELETED,'0'),NOW(),NOW()\n" +
                " FROM orderweb_md_employee\n" +
                " WHERE NOT EXISTS(SELECT id FROM tb_doctor_info \n" +
                " WHERE doctor_code = HOSEMPLCODE AND dept_code = HOSDEPTCODE AND hospital_code = HOSORGCODE)";
        jdbcTemplate.update(sql);

        this.sysnchronPhoto();
    }

    @Override
    public void synchronInfo(String[] ids){
        for(String id :ids){
            Doctor doctor = doctorRepo.findOne(Integer.parseInt(id));
            if(null == doctor){
                continue;
            }
            Map<String,Object> map = jdbcTemplate.queryForMap("SELECT EMPLNAME,SEX,"+
                    "  PLATFORM_PRO_NAME,DOCTOR_DESC,SPECIALTY,VISIT_LEVEL, IFNULL(ISDELETED,'0')" +
                    "  FROM orderweb_md_employee " +
                    " where HOSEMPLCODE = '"+doctor.getDoctorCode()+"' and HOSDEPTCODE = '"+doctor.getDeptCode()+"'" +
                    " and HOSORGCODE = '"+doctor.getHospitalCode()+"'");

            doctor.setDoctorName(null == map.get("EMPLNAME")?null : map.get("EMPLNAME").toString());
            doctor.setGender(null == map.get("SEX") ? null : map.get("SEX").toString());
            doctor.setDoctorTitle(null == map.get("PLATFORM_PRO_NAME") ? null : map.get("PLATFORM_PRO_NAME").toString());
            doctor.setDoctorDesc(null == map.get("DOCTOR_DESC") ? null : map.get("DOCTOR_DESC").toString());
            doctor.setExpertin(null == map.get("SPECIALTY") ? null : map.get("SPECIALTY").toString());
            doctor.setLevel(null == map.get("VISIT_LEVEL") ? null : map.get("VISIT_LEVEL").toString());
            doctor.setDelFlag(null == map.get("ISDELETED") ? null : map.get("ISDELETED").toString());
            doctor.setUpdateTime(new Date());
            doctor.setHeadphoto(null);

            doctorRepo.save(doctor);

        }
        this.sysnchronPhoto();
    }

    private void sysnchronPhoto(){
        List<Doctor> list = doctorRepo.getNoPicDoctor();
        if(null == list || 0 == list.size()){
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for(int index = 0;index < list.size();index++){
            executorService.execute(new DoctorThread(list.get(index)));
        }
        executorService.shutdown();
    }


    private class DoctorThread implements Runnable{
        private Doctor doctor;
        DoctorThread(Doctor doctor){
            this.doctor = doctor;
        }
        @Override
        public void run() {
            System.err.println(Thread.currentThread()+"正在执行"+System.currentTimeMillis());
            String sql = "select DOCTPHOTO from orderweb_md_employee where " +
                    " HOSORGCODE = ? AND HOSDEPTCODE = ? AND HOSEMPLCODE = ? ";
            jdbcTemplate.query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1,doctor.getHospitalCode());
                    preparedStatement.setString(2,doctor.getDeptCode());
                    preparedStatement.setString(3,doctor.getDoctorCode());
                }
            },new AbstractLobStreamingResultSetExtractor(){
                @Override
                protected void streamData(ResultSet resultSet) throws SQLException, IOException, DataAccessException {
                    byte[] bytes = new DefaultLobHandler().getBlobAsBytes(resultSet, 1);
                    if(null != bytes && 0 != bytes.length){
                        String path = uploaderUtil.uploadFile(bytes);
                        if(!StringUtils.isEmpty(path)) {
                            doctor.setHeadphoto(path);
                            doctor.setUpdateTime(new Date());
                            doctorRepo.save(doctor);
                        }
                    }
                }
            });
        }
    }
}
