package com.wonders.health.venus.open.user.entity;

import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;

import java.util.List;

/**
 * 类描述：挂号搜索结果
 * 创建人：Bob
 * 创建时间：2016/1/10 14:45
 */
public class RegistrationSearchListResponse extends BaseListResponse<DoctorListVO> {


    public static class HospitalResponse {
        public List<HospitalInfo.Hospital> hospitalList;
        public boolean has_more_hospital;
    }

}
