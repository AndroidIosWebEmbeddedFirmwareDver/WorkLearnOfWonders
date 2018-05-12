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

#pragma mark - 签约家庭医生
@protocol SignUpFamilyDoctorIMPL <NSObject>

@optional

/**
 *  家庭医生团队列表
 */
- (void)getFamilyDoctorTeamComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure;
/**
 *  家庭医生团队 获取更多
 */
- (void)getMoreFamilyDoctorTeamComplete:(void (^)(void))complete failure:(void (^)(NSError *error))failure;


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


#pragma mark    - 首页工作台主页接口
@protocol HomeIMPL <NSObject>

@optional

//红点提示接口(转诊服务、患者管理)
-(void)getHomeRedTip;


@end

#pragma mark    - 个人中心接口
@protocol ProfileIMPL <NSObject>

@optional

// 个人统计接口
-(void)getProfileData;


@end



#endif /* ViewModelIMPL_h */
