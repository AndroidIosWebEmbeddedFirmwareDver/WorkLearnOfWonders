package com.wonders.health.venus.open.user.module.mine;

import com.alibaba.fastjson.annotation.JSONField;
import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;

import java.io.Serializable;


public class UserCardsEntity implements Serializable {

    @JSONField(name = "id")
    public long id;//在线挂号卡记录唯一ID

    @JSONField(name = "hospital_id")
    public long hospital_id;//医院ID；如果卡类型是就诊卡则不为空

    @JSONField(name = "hospital_code")
    public String hospital_code;//医院代码；如果卡类型是就诊卡则不为空

    @JSONField(name = "hospital_name")
    public String hospital_name;//医院名称；如果卡类型是就诊卡则不为空

    @JSONField(name = "mediacl_card_no")
    public String mediacl_card_no;//在线挂号卡卡号

    @JSONField(name = "card_type_code")
    public String card_type_code;//在线挂号卡类型编码描述

    @JSONField(name = "card_type_name")
    public String card_type_name;//在线挂号卡类型名称描述

    @JSONField(name = "is_deleted")
    public int is_deleted;//是否删除，0-未删除，1-已删除


    //缓存用
    public int index;//下标
    public boolean showAddHealthCard;//显示添加健康卡
    public int showAddHospitalCardSize;//显示添加院内卡个数
    public boolean showAddHospitalCard;//显示添加院内卡
    public boolean showNoticeHospitalCard;//显示废话。。。


    @Override
    public String toString() {
        return "{"
                + "id:" + id
                + "\n,hospital_id:" + hospital_id
                + "\n,hospital_code:" + hospital_code
                + "\n,hospital_name:" + hospital_name
                + "\n,mediacl_card_no:" + mediacl_card_no
                + "\n,card_type_code:" + card_type_code
                + "\n,card_type_name:" + card_type_name
                + "\n,index:" + index
                + "\n,showAddHealthCard:" + showAddHealthCard
                + "\n,showAddHospitalCardSize:" + showAddHospitalCardSize
                + "\n,showAddHospitalCard:" + showAddHospitalCard
                + "\n,showNoticeHospitalCard:" + showNoticeHospitalCard
                +"}";

    }
}
