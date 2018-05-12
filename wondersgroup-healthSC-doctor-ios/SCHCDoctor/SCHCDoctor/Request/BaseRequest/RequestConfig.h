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

#pragma mark - 首页
#define INDEX_PROMPT            @"index/prompt"            //红点提示接口(转诊服务、患者管理)

#pragma mark - 个人中心
#define MY_COUNT                @"my/count"                 //个人统计接口
#define MY_TEAM_LIST            @"my/team/list"             //团队列表接口




#pragma mark -------链支付----------
#define LINK_PAY_KEY       @"pay/key"   //获取链支付key
#define LINK_PAY_INFO      @"pay/info"  //支付详情\


#pragma mark --------患者管理--------
#define Patient_List        @"patient/list" //签约患者列表(全部/重点/贫困)
#define patient_New_Prompt  @"new/prompt"   //新增签约患者红点提示
#define Patient_New_List    @"new/list"     //新增签约患者列表

#pragma mark - 转诊
#define ReferralHomeList                @"/api/referral/list"               //转诊列表
#define ReferralHomeListState           @"/api/referral/status/prompt"      //转诊列表状态
#define ReferralHomeListDetail          @"/api/referral/detail"             //转诊详情
#define ReferralRequestOutpatient       @"/api/referral/outpatientReferral" //门诊转诊
#define ReferralGetOutpatient           @"/api/referral/referralOtpatient"  //门诊接入转诊
#define ReferralRequestInPatient        @"/api/referral/inpatientReferral"  //住院转诊
#define ReferralGetInPatient            @"/api/referral/rferralIpatient"    //住院接入转诊
#define ReferralJoin                    @"/api/referral/joinreferral/list"  //接入转诊
#define ReferralJoinDetail              @"/api/referral/joinreferral/detail"//接入转诊详情

#endif /* RequestConfig_h */
