package com.wonders.health.venus.open.user.entity;
/*
 * Created by sunning on 2016/11/10.
 */

import com.wonders.health.venus.open.user.view.ObservableScrollView;

public class RegisterHeadEntity {

    public enum From {
        DOCTOR_SCHEDULE, DOCTOR_DETAIL
    }

    public ObservableScrollView scrollView;
    public String doctorHead;
    public String doctorName;
    public String doctorTitle;
    public String hospitalName;
    public String orderCount;
    public boolean isAttention;

    public String hosOrgCode;
    public String hosDeptCode;
    public String hosDoctCode;
    public String gender;
    public boolean isInit = true;


    public RegisterHeadEntity(DoctorDetailVO detailVO, From from, ObservableScrollView scrollView,boolean isAttention) {
        if(detailVO == null){
            isInit = false;
            return;
        }
        this.doctorHead = detailVO.headphoto;
        this.doctorName = detailVO.doctorName;
        this.doctorTitle = detailVO.doctorTitle;
        this.hospitalName = detailVO.headphoto;
        this.orderCount = String.valueOf(detailVO.orderCount);
        this.scrollView = scrollView;
        this.hosOrgCode = detailVO.hosOrgCode;
        this.hosDeptCode = detailVO.hosDeptCode;
        this.hosDoctCode = String.valueOf(detailVO.id);
        this.gender = detailVO.gender;
        this.from = from;
        this.isAttention = isAttention;
    }


    public RegisterHeadEntity(ScheduleInfo.DoctorInfoEntity detailVO) {
        this.doctorHead = detailVO.headphoto;
        this.doctorName = detailVO.doctorName;
        this.doctorTitle = detailVO.doctorTitle;
        this.hospitalName = detailVO.hosName;
        this.hosOrgCode = detailVO.hosOrgCode;
        this.hosDeptCode = detailVO.hosDeptCode;
        this.hosDoctCode = detailVO.hosDoctCode;
        this.gender = detailVO.gender;
        this.from = From.DOCTOR_SCHEDULE;
    }

    public RegisterHeadEntity() {
        this.doctorHead = "";
        this.doctorName = "";
        this.doctorTitle = "";
        this.hospitalName = "";
        this.orderCount = "";

        this.hosOrgCode = "";
        this.hosDeptCode = "";
        this.hosDoctCode = "";
        this.from = From.DOCTOR_SCHEDULE;
    }

    public From from;
}
