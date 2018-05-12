package com.wonders.health.venus.open.doctor.entity.event;
/*
 * Created by sunning on 2017/6/16.
 */

import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;

public class DoctorItemListEntity extends BaseListResponse<DoctorItemListEntity.DoctorItem> {


    public static class DoctorItem {
        /**
         * teamID :
         * teamName :
         * teamLogo :
         * teamAddress :
         */

        public String teamID;
        public String teamName;
        public String teamLogo;
        public String teamAddress;
    }
}
