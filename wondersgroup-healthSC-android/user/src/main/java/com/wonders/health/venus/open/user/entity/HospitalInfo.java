package com.wonders.health.venus.open.user.entity;

import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 类描述：医院entity
 * 创建人：hhw
 * 创建时间：2015/7/13 10:47
 */
public class HospitalInfo extends BaseListResponse<HospitalInfo.Hospital> {


    public static class Hospital implements Serializable{

        /**
         * hospitalId : 2
         * hospitalCode : 450755531
         * receiveThumb : http://og3xulzx6.bkt.clouddn.com/589d85f6e72a4f978f4d7d7cc05df51b
         * hospitalPhoto : http://og3xulzx6.bkt.clouddn.com/589d85f6e72a4f978f4d7d7cc05df51b
         * hospitalName : 成都市第一人民医院
         * hospitalAddress : 成都市高新南区繁雄大道万象北路18号
         * hospitalDesc : 成都市中西医结合医院（又名成都市第一人民医院）是成都地区一所以中西医结合为特点的大型现代化综合医院，医院始建于1942年，1985年被上级命名为成都市中西医结合医院，1994年9月被评为爱婴医院，1995年4月被国家中医药管理局批准为“全国中药制剂和剂型改革基地”，1995年10月被评为中西医结合三级甲等医院，1999年被国家卫生部、国家中医药管理局共同授予“全国百佳医院”称号。
         * hospitalTel : (028)85313628 028-85311726
         * hospitalGrade : 三级甲等
         * receiveCount : 0
         * * hospitalLatitude	// 医院纬度
         * hospitalLongitude	// 医院经度
         */
        public int hospitalId;
        public String hospitalCode;
        public String receiveThumb;
        public String hospitalPhoto;
        public String hospitalName;
        public String hospitalAddress;
        public String hospitalDesc;
        public String hospitalTel;
        public String hospitalGrade;
        public String receiveCount;
        public int evaluateCount;
        public String hospitalLatitude;
        public String hospitalLongitude;
        public String concern;//0-未关注，1-已关注
        public List<EvaluateEntity.Evaluate> evaluList;
    }

}
