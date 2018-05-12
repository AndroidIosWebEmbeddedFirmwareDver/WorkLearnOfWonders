//
//  ViewModelIMPL.h
//  VaccinePatient
//
//  Created by Jam on 16/5/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#ifndef ViewModelIMPL_h
#define ViewModelIMPL_h

#import "SDImageCache.h"
#import "WDRegistrationPayModel.h"

typedef void(^IMPLCompleteBlock) (void);            //接口调用成功的Block
typedef void(^IMPLCompleteWithResponseBlock) (id response);  //接口调用成功并且返回数据的Block
typedef void(^IMPLFailuredBlock) (NSError *error);  //接口调用失败的Block
typedef void(^IMPLDownImageBlock)(UIImage *image, NSError *error, SDImageCacheType cacheType, BOOL finished, NSURL *imageURL);  //图片下载完成的Block


#pragma mark - 服务接口API 各种公共接口、开关、后台下载等
@protocol CommonIMPL <NSObject>

@optional

/**
 *  获取七牛token
 *
 */
-(void)getQiNiuToken:(void(^)(void))complete;

/**
 *  全局接口
 *
 */
-(void)getCommonConfig:(void(^)(void))complete;


/**
 *  下载图片接口
 *
 */
- (void)downloadImage: (NSString *)imageUrl
             complete: (IMPLCompleteBlock)complete
              failure: (IMPLFailuredBlock)failure;
//- (void)downloadImage:(NSString *)imageUrl complete:(DownImageBlock)complete;

/**
 *  获取别名
 *
 */
- (void)getAliasWithClientId: (NSString *)clientId
                    complete: (IMPLCompleteWithResponseBlock)complete
                     failure: (IMPLFailuredBlock)failure;

@end


#pragma mark    - 用户中心接口
@protocol UserCenterIMPL <NSObject>
@optional
//游客登录接口- (void)guestLogin:(void(^)(void))complete failure:(void (^)(NSError * error))failure;
//用户登录
-(void)userLogin: (IMPLCompleteBlock)complete;



//发送验证码

- (void)getVerifyCode:(void(^)(void))complete failure:(void (^)(void))failure;

//验证验证码

- (void)checkVerifyCode:(void(^)(void))complete failure:(void (^)(void))failure;

//手机号注册
- (void)registerUser:(void (^)(void))complete failure:(void (^)(void))failure;

// 重置密码

- (void)resetPassword:(void (^)(void))complete failure:(void (^)(void))failure;
// 设置密码
- (void)setPassword:(void (^)(void))complete failure:(void (^)(void))failure;
@end


#pragma mark - 医生 接口

@protocol DoctorIMPL <NSObject>

@optional
//获取医生详情
- (void)getDoctorDetail:(void (^)(void))success failure:(void (^)(void))failure;

// 关注(取消关注)
- (void)postFavoriteDoctor:(void (^)(NSString *))success failure:(void (^)(NSString *))failure;

//获取评价列表
- (void)getJudgeList:(void (^)(void))success failure:(void (^)(void))failure;
- (void)getMoreJudgeList:(void (^)(void))success failure:(void (^)(void))failure;

@end

#pragma mark - 健康接口
@protocol HealthHomeIMPL <NSObject>

@optional

//健康首页功能按钮
- (void)getHealthHomeFunction:(void (^)(void))success
                      failure:(void(^)(void))failure;

//健康首页文章列表
- (void)getHealthHomeInformationList:(void (^)(void))success
                             failure:(void (^)(void))failure;

@end

#pragma mark - 关注接口

@protocol MyAttentionIMPL <NSObject>

@optional

//获取关注医生列表
- (void)getAttentionDoctorList:(void (^)(void))success
                       failure:(void (^)(void))failure;
//获取更多关注医生列表
- (void)getMoreAttentionDoctorList:(void (^)(void))success
                       failure:(void (^)(void))failure;


//获取关注医院列表
- (void)getAttentionHospitalList:(void (^)(void))success
                         failure:(void (^)(void))failure;
//获取更多关注医院列表
- (void)getMoreAttentionHospitalList:(void (^)(void))success
                         failure:(void (^)(void))failure;

@end

#pragma mark    - 预约挂号接口
@protocol AppointmetIMPL <NSObject>

@optional

//获取预约医院一级科室列表
- (void)getNoodleDepartmentlList: (void(^)(void))success
                         failure: (void(^)(void))failure;

//获取预约医院二级科室列表
- (void)getChildreDepartmentlList: (void(^)(void))success
                          failure: (void(^)(void))failure;

//按专家获取医生
-(void)getAppointmentDoctorWithName;
//按专家获取医生更多
-(void)getAppointmentDoctorMoreWithName;

//按日期获取医生
-(void)getAppointmentDoctorWithDate;
//按日期获取医生更多
-(void)getAppointmentDoctorMoreWithDate;

//获取日期
-(void)getAppointmentDate;

//提交预约信息详情
-(void)submitAppointmentDetail: (void(^)(WDRegistrationPayModel *))success
                       failure: (void(^)(void))failure;

//获取预约医生排班详情
//-(void)getDoctorScheduling;
- (void)getDoctorScheduling:(void (^)(void))success failure:(void (^)(NSError *error))failure;


@end

#pragma mark    - 我的预约IMPL
@protocol MyAppointmentIMPL <NSObject>

@optional

//获取预约列表
-(void)getMyAppointment;
//获取预约列表更多
-(void)getMyAppointmentMore;



@end


#pragma mark - 我的订单IMPL
@protocol MyOrderIMPL <NSObject>

@optional
/**
 *  我的订单列表
 */
- (void)getMyOrderListComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure;
/**
 *  我的订单列表 获取更多
 */
- (void)getMoreMyOrderListComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure;

/**
 *  取消订单
 */
- (void)cancelOrderComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure;


@end


#pragma mark - 签约家庭医生
@protocol SignUpFamilyDoctorIMPL <NSObject>

@optional

//获取签约数
-(void)getSignUpNumber;

/**
 *  家庭医生团队列表
 */
- (void)getFamilyDoctorTeamComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure;
/**
 *  家庭医生团队 获取更多
 */
- (void)getMoreFamilyDoctorTeamComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure;


@end



#endif /* ViewModelIMPL_h */
