//
//  UserService.h
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UploadModel.h"

@interface UserService : NSObject

+ (UserService *)service;

#pragma mark - 用户登录、注册管理
//注册用户账号密码登录
- (void)userPWDLogin:(NSDictionary *)params complete:(void(^)(void))complete;

//从服务器获取最新的用户信息
- (void)refreshLastUserInfo;
- (void)refreshLastUserInfoComplete:(void(^)(UserInfoModel *model))complete;
//用户注销
- (void)userLogoff;
//用户被踢
- (void)userKickLogoff;
//用户意见反馈
- (void)userFeedback:(NSDictionary *)params complete:(void (^)(void))complete;

@end
