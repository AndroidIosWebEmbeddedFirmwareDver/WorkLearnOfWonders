//
//  UserService.m
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UserService.h"
#import "ResponseAdapter.h"
#import "DBManager.h"
#import "TaskManager.h"

@implementation UserService


+ (UserService *)service {
    
    static dispatch_once_t onceToken;
    static UserService *service = nil;
    dispatch_once(&onceToken, ^{
        service = [[UserService alloc] init];
    });
    return service;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        
    }
    return self;
}



#pragma mark -----------------------------------------------------------------------
#pragma mark 用户接口管理
#pragma mark -----------------------------------------------------------------------

#pragma mark - 用户登录、注册管理
#pragma mark - 账号密码登录
- (void)userPWDLogin:(NSDictionary *)params complete:(void(^)(void))complete {
    [[ResponseAdapter sharedInstance] request: USER_LOGIN params: params class: [UserModel class] responseType: Response_Object method: Request_GET needLogin: NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        //保存用户登录方式
        [[UserManager manager] userSaveLoginStatus: params[@"mobile"] withLoginType: YES];
        NSString *msg = response.message ? response.message : @"登录成功";
        [MBProgressHUDHelper showHudWithText: msg];
        UserModel *model = response.data;
        model.loginName  = params[@"mobile"];
        model.isLogin    = [NSNumber numberWithBool: YES];;
        [[UserManager manager] updateLoginInfo: model];
        complete();

       
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
   
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
    }];
    
}

#pragma mark - 从服务器获取最新的用户信息
- (void)refreshLastUserInfo {
    NSDictionary *params = @{
                             @"id" : [UserManager manager].uid
                             };
    [[ResponseAdapter sharedInstance] request: USER_INFO params: params class: [UserInfoModel class] responseType: Response_Object method: Request_GET needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        UserInfoModel *info = response.data;
        [[UserManager manager] updateUserInfo: info];
        NSLog(@"用户信息获取成功%d",[UserManager manager].password_complete);
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"用户信息获取失败");
    }];
}

- (void)refreshLastUserInfoComplete:(void(^)(UserInfoModel *model))complete {
    NSDictionary *params = @{
                             @"id" : [UserManager manager].uid
                             };
    [[ResponseAdapter sharedInstance] request: USER_INFO params: params class: [UserInfoModel class] responseType: Response_Object method: Request_GET needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        UserInfoModel *info = response.data;
        [[UserManager manager] updateUserInfo: info];
        complete(info);
        NSLog(@"用户信息获取成功");
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"用户信息获取失败");
    }];
}

#pragma mark - 用户注销
- (void)userLogoff {
    
//    [[DBManager manager] clearHomeModel];
//    [[DBManager manager] clearHomeReservationModel];
//    [[DBManager manager] clearAppointmentCheckModelStorage];
    [[ResponseAdapter sharedInstance] request: USER_LOGINOFF params: nil class: [UserModel class] responseType: Response_Object method: Request_DELETE needLogin: NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        NSLog(@"%@", response.message);
//        [[UserManager manager]updateLogOffInfo:NO];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"用户注销失败，%@", error.localizedDescription);
    }];
    [[UserManager manager] updateLogOffInfo:NO];
   [MBProgressHUDHelper showHudWithText:@"退出登录成功"];

}

#pragma mark - 用户被踢
- (void)userKickLogoff {
    
    //    [[DBManager manager] clearHomeModel];
    //    [[DBManager manager] clearHomeReservationModel];
    //    [[DBManager manager] clearAppointmentCheckModelStorage];
    [[ResponseAdapter sharedInstance] request: USER_LOGINOFF params: nil class: [UserModel class] responseType: Response_Object method: Request_DELETE needLogin: NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        NSLog(@"%@", response.message);
        //        [[UserManager manager]updateLogOffInfo:NO];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"用户注销失败，%@", error.localizedDescription);
    }];
    [[UserManager manager] updateLogOffInfo:NO];
   
}

#pragma mark - 用户意见反馈
- (void)userFeedback:(NSDictionary *)params complete:(void (^)(void))complete {
    [MBProgressHUDHelper showHudIndeterminate];
//    [[ResponseAdapter sharedInstance] request: USER_FEEDBACK params: params class: nil responseType: Response_Message method: Request_POST needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        [MBProgressHUDHelper hideHud];
//        NSString *msg = response.message ? response.message : @"提交成功";
//        [MBProgressHUDHelper showHudWithText: msg];
//        complete();
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//    
//        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
//    }];

}

@end
