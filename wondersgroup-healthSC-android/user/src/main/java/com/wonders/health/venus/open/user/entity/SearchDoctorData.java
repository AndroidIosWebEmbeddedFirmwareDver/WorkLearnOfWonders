package com.wonders.health.venus.open.user.entity;

import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;

import java.io.Serializable;

/**
 * 搜索结果--医生列表
 * Created by songzhen on 2016/11/9.
 */
public class SearchDoctorData extends BaseListResponse<SearchDoctorData.SearchDoctor> implements Serializable{

    public static class SearchDoctor{

        /**
         * id : 16560
         * hospitalCode : 450753341
         * deptCode : 100705
         * doctorCode : 53
         * doctorName : 李艳(内三)
         * doctorTile : 副主任医师
         * expertin : 心血管疾病，脑血管疾病，小儿脑科
         * orderCount : 17
         * hospitalName : 成都市第七人民医院
         * deptName : 内科门诊
         * headphoto : http://og3xulzx6.bkt.clouddn.com/589d85f6e72a4f978f4d7d7cc05df51b
         */

        public int id;
        public String hospitalCode;
        public String deptCode;
        public String doctorCode;
        public String doctorName;
        public String doctorTile;
        public String expertin;
        public int orderCount;
        public String hospitalName;
        public String deptName;
        public String headphoto;
    }
}
