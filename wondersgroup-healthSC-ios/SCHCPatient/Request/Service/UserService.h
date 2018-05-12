//
//  UserService.h
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UploadModel.h"
#import "SCTrueNameModel.h"

@interface UserService : NSObject

+ (UserService *)service;

#pragma mark - 用户登录、注册管理
//游客登录
- (void)userGestLoginComplete:(void(^)(void))complete;
//注册用户账号密码登录
- (void)userPWDLogin:(NSDictionary *)params complete:(void(^)(void))complete;

//注册用户动态码登录
- (void)userCodeLogin:(NSDictionary *)params complete:(void(^)(void))complete;

//从服务器获取最新的用户信息
- (void)refreshLastUserInfo;
- (void)refreshLastUserInfoComplete:(void(^)(UserInfoModel *model))complete;

//修改用户信息
- (void)userEditInfo:(NSDictionary *)params complete:(void(^)(void))complete;

//修改用户头像
- (void)userEditIcon:(UploadModel *)image complete:(void(^)(void))complete;

//发送验证码
- (void)userSendSMS:(NSDictionary *)params complete:(void(^)(void))complete failure:(void(^)(void))failure;

//用户验证验证码
- (void)userVerificationSMS:(NSDictionary *)params complete:(void(^)(void))complete failure:(void(^)(void))failure;

//修改用户手机号
- (void)userEditMobile:(NSDictionary *)params complete:(void (^)(void))complete;


//修改用户密码
- (void)userSetPassword:(NSDictionary *)params complete:(void (^)(void))complete;
//修改用户密码
- (void)userEditPassword:(NSDictionary *)params complete:(void (^)(void))complete;

//用户注销
- (void)userLogoff;
//用户被踢
- (void)userKickLogoff;
//用户意见反馈
- (void)userFeedback:(NSDictionary *)params complete:(void (^)(void))complete;
//用户注册
- (void)userRegister:(NSDictionary *)params complete:(void(^)(void))complete failure:(void(^)(void))failure;

//实名认证状态
- (void)requestTureNameType:(void(^)(SCTrueNameModel *tureNameModel))success failure:(void (^)(NSError *error))failure;

//更新消息未读数
- (void)updateMessageCount:(NSDictionary *)params complete:(void(^)(void))complete failure:(void(^)(void))failure;
/*

//来宾用户登录
- (void)guestLogin:(void(^)(void))complete;

//token过期，用返回的来宾用户token进行登录
- (void)loginGuestWithToken:(NSString *)token withKey:(NSString *)key;


#pragma mark - 用户信息编辑
//编辑用户头像
- (void)editAvatar:(NSDictionary *)params complete:(void (^)(void))complete;

//编辑用户昵称
- (void)editNickName:(NSDictionary *)params complete:(void (^)(void))complete;

//修改用户手机号
- (void)editMobile:(NSDictionary *)params complete:(void (^)(void))complete;

//修改用户出生年月
- (void)editBirthday:(NSDictionary *)params complete:(void (^)(void))complete;

//修改用户性别
- (void)editGender:(NSDictionary *)params complete:(void (^)(void))complete;

*/
@end
