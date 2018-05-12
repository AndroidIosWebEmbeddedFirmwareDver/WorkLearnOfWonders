package com.wonders.health.venus.open.doctor.entity;

import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;

/**
 * 医生评价
 * Created by zhangjingyang on 2016/11/8.
 */

public class EvaluateEntity extends BaseListResponse<EvaluateEntity.Evaluate> {

    public static class Evaluate {
        public String id;
        public String createTime;
        public String nickName;
        public String content;
        public String uid;
    }
}
