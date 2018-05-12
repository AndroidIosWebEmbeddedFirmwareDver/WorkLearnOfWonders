package com.wonders.health.venus.open.user.module.mine;

import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;

import java.io.Serializable;


/**
 * Created by win10 on 2018/4/14.
 */

public class CardsTypeEntity implements Serializable {
    /**
     * "id":"",//在线挂号卡类型唯一ID
     "uid":"",//用户唯一编码ID
     "hospital_code":"",//医院代码；如果卡类型是就诊卡则不为空
     "mediacl_card_no":"",//在线挂号卡卡号
     "card_type_code":"",//在线挂号卡类型编码描述
     */
    public String id;
    public String uid;
    public String hospital_code;
    public String hospital_name;
    public String mediacl_card_no;
    public String card_type_code;
}
