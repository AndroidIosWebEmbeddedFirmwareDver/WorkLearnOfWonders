//
//  RequestConfig.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#ifndef RequestConfig_h
#define RequestConfig_h




typedef void(^IMPLCompleteBlock) (void);            //接口调用成功的Block
typedef void(^IMPLCompleteWithResponseBlock) (id response);  //接口调用成功并且返回数据的Block
typedef void(^IMPLFailuredBlock) (NSError *error);  //接口调用失败的Block
typedef void(^IMPLDownImageBlock)(UIImage *image, NSError *error, SDImageCacheType cacheType, BOOL finished, NSURL *imageURL);  //图片下载完成的Block


#define UI_TAB_IMAGE           @"common/appNavigationBar" //底部Tab更新

#pragma mark - 用户API

#define QINIU_TOKEN             @"common/getQiniuToken"      //获取七牛Token
#define APP_CONFIG              @"common/appConfig" //全局接口
#define USER_LOGIN_GUEST        @"token/guest"              //游客登录  
#define USER_LOGIN              @"token"            //用户的登录
#define USER_LOGIN_CODE         @"token/code"       //用户动态码登录
#define USER_REGISTER           @"user/registeByCode"              //用户注册
#define USER_INFO               @"user"             //用户信息获取
#define USER_PWD_EDIT           @"user/password"    //用户密码设置
#define USER_PWD_UPDATE           @"user/password/update"    //用户更新密码
#define USER_SMS_SEND           @"sms"              //发送手机验证码
#define USER_SMS_VERIFICATION   @"sms/verification" //验证验证码
#define USER_LOGINOFF              @"token"            //用户退出登录
#define USER_INFO_EDIT              @"user/info"            //用户退出登录


#pragma mark - 健康页面

#define HealthHome_Function_URL             @"health/home"                  //功能按钮
#define HealthHome_Information_URL          @"article/articleCategoty"      //资讯标题


#pragma mark - 消息中心
#define Messages_URL                @"message/newMessage"                         //消息列表

#define SYSTEM_MESSAGE_LIST_URL     @"message/queryList"                         //系统消息列表
#define SYSTEM_MESSAGE_UPDATEBYID   @"message/update" //消息状态更新
#define PAY_MESSAGE_LIST_URL        @"message/pay/queryList"  //获取支付列表


#pragma mark - 医院 医生 相关

#define HOSPITAL_HOME_PAGE              @"queryHospitlInfo"                 // 医院主页
#define HOSPITAL_COLLECT                @"favorite/hospital/addDel"         // 医院关注操作
#define HOSPITAL_EVALUATION_LIST        @"evaluate/hospital/list"           // 医院评价列表
#define HOSPITAL_EVALUATION_POST        @"evaluate/hospital/publish"        // 发送医院评价
#define HOSPITAL_FAVORITE_LIST_URL      @"favorite/hospital/list"           // 关注医院列表
#define DOCTOR_FAVORITE_LIST_URL        @"favorite/doctor/list"             // 关注的医生列表
#define DOCTOR_FAVORITE_URL             @"favorite/doctor/addDel"           // 关注医生操作
#define DOCTOR_EVALUATE                 @"evaluate/doctor/publish"          // 评价医生
#define DOCTOR_EVALUATE_LIST_URL        @"evaluate/doctor/list"             // 医生评价列表


#define DOCTOR_DETAIL_URL            @"queryDoctorInfo"                 //医生详情

#pragma mark --我的订单－
#define MYORDER_LIST_URL           @"interDiaPayment/orders"  // 获取订单列表
#define MYORDER_CANCEL_ORDER_URL   @"bbs/comment/publish"  // 取消订单
#define MYORDER_UNPAY_RECORDS      @"interDiaPayment/unPayRecord" //诊间支付获取待缴费记录
#define MYORDER_GENERATE_ORDER     @"interDiaPayment/generateOrder"//生成诊间支付订单

#pragma mark - 中医体质辨识
#define ZYTZBSURL    @"physicalIdentify"

#pragma mark -------附近医院－－－－－－－－－
#define NEARBY_HOSPITAL_LIST    @"hospital/near/list"  //附近医院列表

#pragma mark - 实名认证
#define USER_VERIFICATION_SUBMIT    @"verification/submit"

#pragma mark - 预约相关
#define QUERYHOSPITLS           @"queryHospitls"            //医院列表查询
#define QUERYDEPS               @"queryDeps"                //一级科室查询,二级科室查询
#define QUERYDOCTORLIST         @"queryDoctorList"          //专家预约医生列表
#define QUERYSCHEDULINFO        @"querySchedulInfo"         //医生排班信息查询
#define QUERYSCHEDULBYTIME      @"querySchedulByTime"       //按日期预约医生列表查询
#define GETSYSTEMTIME           @"getSystemTime"            //获取当前系统时间

#pragma mark - 预约挂号相关
#define ORDER_SUBMITORDER       @"order/submitOrder"            //提交预约挂号
#define QUERYORDERLIST          @"order/getOrderList"           //预约挂号列表
#define QUERYORDERLISTDETAIL    @"order/getOrderDetail"         //预约挂号详情
#define ORDER_CANCEL            @"order/cancelOrder"            //取消预约挂号
#define INTER_DIA_PAYMENT_APPOINT @"interDiaPayment/appoint"    //在线支付挂号费支付

#pragma mark - 文章
#define Article_Query_Tab       @"article/queryTab"   //根据文章分类获取文章列表
#define Article_Query           @"article/query"      //根据文章应用场景获取文章列表
#define Article_Collects        @"article/collects"   //获取用户文章收藏列表

#define Article_Do_Collect      @"article/collect"    //文章收藏

#define Article_Collect_Cancel  @"article/cancle"     //文章取消收藏

#define Article_AddShare        @"article/addShare" //文章分享

#define Article_Check_Collect   @"articleFavorite/checkIsFavor"//增加文章分享数

#pragma mark -------实名认证----------
#define Verification_INFO       @"verification/submit/info" //实名认证信息提交查询


#pragma mark -------链支付----------
#define LINK_PAY_KEY       @"pay/key"   //获取链支付key
#define LINK_PAY_INFO      @"pay/info"  //支付详情

#pragma mark - 家庭医生
#define FAMILY_DOCTOR_TEAMLIST       @"family/doctor/teamList"            //家庭医生列表



#endif /* RequestConfig_h */
