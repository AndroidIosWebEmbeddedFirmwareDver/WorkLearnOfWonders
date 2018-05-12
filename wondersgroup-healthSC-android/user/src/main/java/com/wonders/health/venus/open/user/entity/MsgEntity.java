package com.wonders.health.venus.open.user.entity;

import com.wondersgroup.hs.healthcloud.common.entity.BaseListResponse;

/**
 * 消息entity
 * Created by zhangjingyang on 2016/11/8.
 */

public class MsgEntity extends BaseListResponse<MsgEntity.message> {

    public static class message {

        public String createDate;
        public String date;
        public String messageId;
        public String state;
        public String imgUrl;
        public String url;
        public String isShow;

        //系统消息
        public String title;
        public String content;
        public String type;//类型  1 系统消息，2 支付消息 //如果是支付消息：类型 1 待支付 2 待就诊 3 待评价
        public String name;//未读消息
        public int count;//未读消息


        //支付消息
        public String id;// "asdkjhkjakshd",//消息id
        public String orderId;// "1",//订单id
        public String hospitalName;// "1",//医院名称
        public String patientName;// "1",//患者姓名
        public String department;// "1",//科室
        public String doctorName;// "1",//医生姓名
        public String price;// 0,//价格
        public String payStatus;// 1,//0 支付失败， 1 支付成功
        public String payType;// 支付类型  1 诊间支付，2挂号费支付
        public String payTypeName;// "诊间支付",//支付类型名称
        public String clinicType;// "专家门诊",//门诊类型
        public String prescriptionCode;// "123123123",//处方号码
        public String prescriptionTime;// "2016-11-07 16;39"null"",//开方时间
        public String orderTime;// "2016-11-07 周一 上午",//订单时间
        public String registerId;// "1",//用户id
    }
}
