package com.wondersgroup.healthSC.services.impl;

import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.StringResponseWrapper;
import com.wondersgroup.healthSC.services.constant.URLEnum;
import com.wondersgroup.healthSC.services.interfaces.BeInHospitalRecordsService;
import com.wondersgroup.healthSC.services.jpa.entity.BeInHospitalRecord;
import com.wondersgroup.healthSC.services.jpa.entity.Hospital;
import com.wondersgroup.healthSC.services.jpa.entity.User;
import com.wondersgroup.healthSC.services.jpa.repository.BeInHospitalRecordRepository;
import com.wondersgroup.healthSC.services.jpa.repository.HospitalRepository;
import com.wondersgroup.healthSC.services.jpa.repository.UserRepository;
import com.wondersgroup.healthSC.services.model.request.HisRequest;
import com.wondersgroup.healthSC.services.model.response.BeInHospitalResponse;
import com.wondersgroup.healthSC.services.utils.JaxbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nick on 2016/11/7.
 * @author nick
 */
//@Service
public class BeInHospitalRecordsServiceImpl implements BeInHospitalRecordsService{

    @Autowired
    private HttpRequestExecutorManager manager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BeInHospitalRecordRepository beInHospitalRecordRepository;

    @Value("${his.pay.url}")
    private String baseUrl;

    private static Integer VERIFICATION = 1;

    private static Integer hospital_pageSize = 50;
    private static Integer user_pageSize = 1000;

    private static Integer hospital_poolSize = 20;
    private static Integer user_poolSize = 20;

    private ExecutorService hospital_executorService = Executors.newFixedThreadPool(hospital_poolSize);
    private ExecutorService user_executorService = Executors.newFixedThreadPool(user_poolSize);

    @Override
    public void handleBeInHospitalRecords() {
        List<Hospital> hospitalList = hospitalRepository.getAllHospitals();
        if(!CollectionUtils.isEmpty(hospitalList)){
            int page = (hospitalList.size() / hospital_pageSize) + 1;
            for(int i=0;i<page;i++){
                int from = i * hospital_pageSize;
                int to = (i + 1) * hospital_pageSize;
                if(to>hospitalList.size())
                    to = hospitalList.size();
                List<Hospital> subHospitals = new ArrayList<>(hospitalList.subList(from, to));
                hospital_executorService.submit(new BeInHospitalTask(subHospitals));
            }
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCurrentDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        return format.format(currentDate);
    }

    class BeInHospitalTask implements Runnable{
        List<Hospital> taskHospitalList;

        BeInHospitalTask(List<Hospital> taskHospitalList){
            this.taskHospitalList = taskHospitalList;
        }

        @Override
        public void run() {
            int verificationNum = userRepository.countByVerificationLevel(VERIFICATION);
            int userPage = (verificationNum / user_pageSize) + 1;
            String startDay = getCurrentDate(), endDay = getCurrentDate();
            for(Hospital hospital: taskHospitalList){
                String hospitalCode = hospital.getHospitalCode();
                for(int i=0; i<userPage; i++){
                    int from = i * user_pageSize;
                    int to = (i+1) * user_pageSize;
                    if(to > verificationNum){
                        to = verificationNum;
                    }
                    List<User> userList = userRepository.getUserByVerificationLevel(VERIFICATION, from, to);
                    if(!CollectionUtils.isEmpty(userList)){
                        user_executorService.submit(new UserTask(userList, hospitalCode, startDay, endDay));
                    }
                }
            }
        }
    }

    class UserTask implements Runnable{

        List<User> userList;
        String hospitalCode;
        String startDay;
        String endDay;

        public UserTask(List<User> userList, String hospitalCode, String startDay, String endDay){
            this.userList = userList;
            this.hospitalCode = hospitalCode;
            this.startDay = startDay;
            this.endDay = endDay;
        }

        @Override
        public void run() {
            for(User user: userList){
                HisRequest hisRequest = new HisRequest();
                hisRequest.setYljgdm(hospitalCode);
                hisRequest.setKh(user.getIdcard());
                hisRequest.setKlx("01");
                hisRequest.setKsrq(startDay);
                hisRequest.setJsrq(endDay);
                try {
                    BeInHospitalResponse response = raiseXMLRequest(hisRequest);
                    if(response!=null && response.getResultcode().equals("0")){
                        List<BeInHospitalResponse.Item> items = response.getItem();
                        if(!CollectionUtils.isEmpty(items)){
                            for(BeInHospitalResponse.Item item: items){
                                BeInHospitalRecord record = new BeInHospitalRecord(item, user.getIdcard(),hospitalCode);
                                BeInHospitalRecord another = beInHospitalRecordRepository.getByIdCard(record.getIdCard());
                                if(another!=null){
                                    record.setId(another.getId());
                                }
                                beInHospitalRecordRepository.save(record);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private BeInHospitalResponse raiseXMLRequest(HisRequest hisRequest) throws Exception{
            String xml = JaxbUtil.convertToXml(hisRequest);
            Request request = new RequestBuilder().post().url(baseUrl + URLEnum.BE_IN_HOSPITAL_QUERY_PATH).
                    body(xml).build();
            StringResponseWrapper wrapper = (StringResponseWrapper) manager.newCall(request).run().as(StringResponseWrapper.class);
            String response = wrapper.convertBody();
            BeInHospitalResponse beInHospitalResponse = JaxbUtil.converyToJavaBean(response, BeInHospitalResponse.class);
            return beInHospitalResponse;
        }
    }
}
