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
#import "SCSystemModel.h"

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
    //[MBProgressHUDHelper showHudIndeterminate];
    /*
    NSDictionary * dic = @{
                           @"uid":@"3ad2d6fd8cf8462db7d50fe5ddf32d08",
                           @"token":@"0212ceaf43fc43ef9e9559837b54fa54",
                           @"key":@"0e5e04d24d174206a1cf833aefda73a1",
                           @"info":@{
                                   @"userName":@"李二狗",
                                   @"mobile":@"18721242510",
                                   },
                           
    
                           };
    [[UserManager manager] userSaveLoginStatus: params[@"mobile"] withLoginType: YES];
    
    UserModel *model = [UserModel mj_objectWithKeyValues:dic];
    model.loginName  = params[@"mobile"];
    model.isLogin    = [NSNumber numberWithBool: YES];;
    [[UserManager manager] updateLoginInfo: model];
    complete();
    */
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

#pragma mark - 动态码登录
- (void)userCodeLogin:(NSDictionary *)params complete:(void(^)(void))complete {
    [MBProgressHUDHelper showHudIndeterminate];
    [[ResponseAdapter sharedInstance] request: USER_LOGIN_CODE params: params class: [UserModel class] responseType: Response_Object method: Request_GET needLogin: NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        //保存用户登录方式
        [[UserManager manager] userSaveLoginStatus: params[@"mobile"] withLoginType: NO];
        NSString *msg = response.message ? response.message : @"登录成功";
        [MBProgressHUDHelper showHudWithText: msg];
        UserModel *model = response.data;
        model.loginName  = params[@"mobile"];
        model.isLogin    = [NSNumber numberWithBool: YES];
        [[UserManager manager] updateLoginInfo: model];
        NSLog(@"%@",[UserManager manager].uid);
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

#pragma mark - 修改用户信息
- (void)userEditInfo:(NSDictionary *)params complete:(void(^)(void))complete {
    
    //[MBProgressHUDHelper showHudIndeterminate];
    NSMutableDictionary *editInfo = [self getEditUserInfo: params];
    
    [[ResponseAdapter sharedInstance] request: USER_INFO_EDIT params: editInfo class: nil responseType: Response_Object method: Request_POST needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        [MBProgressHUDHelper hideHud];
        //更新本地用户信息
        NSDictionary * dic = response.data;
        //[[UserManager manager] updateUserInfo: info];
        if (dic.count) {
            [[UserManager manager] updateEditInfo: response.data];
        }
        

        NSString *msg = response.message ? response.message : @"用户信息设置成功";
        [MBProgressHUDHelper showHudWithText: msg];
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper hideHud];
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
    }];
}


#pragma mark - 修改用户头像
- (void)userEditIcon:(UploadModel *)image complete:(void(^)(void))complete {
    
    [MBProgressHUDHelper showHudIndeterminate];
    [[ResponseAdapter sharedInstance] upload: image token: [TaskManager manager].qnToken success:^(ResponseModel *response) {
        [MBProgressHUDHelper hideHud];
        [self userEditInfo:@{@"avatar":response.data} complete:^{
            complete();
        }];
    } failure:^(NSError *error) {
        [MBProgressHUDHelper hideHud];
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
    }];
}

#pragma mark - 组装编辑用户信息所需传的参数
- (NSMutableDictionary *)getEditUserInfo:(NSDictionary *)params {

    UserManager *manager = [UserManager manager];
    NSMutableDictionary *editInfo = [NSMutableDictionary dictionary];
    [editInfo setObject: CHECKNULL(manager.uid)      forKey: @"uid"];
    [editInfo setObject: CHECKNULL(manager.name) forKey: @"name"];
    [editInfo setObject: CHECKNULL(manager.nickname) forKey: @"nickname"];
    [editInfo setObject: CHECKNULL(manager.name) forKey: @"name"];
    [editInfo setObject: CHECKNULL(manager.mobile)   forKey: @"mobile"];
    [editInfo setObject: CHECKNULL(manager.avatar)   forKey: @"avatar"];
    [editInfo setObject: CHECKNULL(manager.birthday) forKey: @"birthday"];
    [editInfo setObject: CHECKNULL(manager.age) forKey: @"age"];
    if (manager.gender == 0 || manager.gender == 1) {
        [editInfo setObject: [NSNumber numberWithInt: manager.gender]   forKey: @"gender"];
    } 
    else {
        [editInfo setObject: [NSNumber numberWithInt: 2]   forKey: @"gender"];
    }

    for (NSString *key in [params allKeys]) {
        if ([editInfo objectForKey: key]) {
            [editInfo setObject: params[key] forKey: key];
        }
    }
    return editInfo;
}



#pragma mark - 发送验证码管理
//发送验证码
//mobile|手机号码|string
//type|短信类型|integer|`0`:默认, `1`:手机动态码登录, `2`:忘记密码, `3`:设置密码
- (void)userSendSMS: (NSDictionary *)params
           complete: (void(^)(void))complete
            failure: (void(^)(void))failure {
    [MBProgressHUDHelper showHudIndeterminate];
    
    [[ResponseAdapter sharedInstance] request: USER_SMS_SEND params: params class: nil responseType: Response_Message method: Request_GET needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        [MBProgressHUDHelper hideHud];
        NSString *msg = response.message ? response.message : @"发送成功";
        [MBProgressHUDHelper showHudWithText: msg];
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//        [MBProgressHUDHelper hideHud];
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
        failure();
    }];
}

//用户验证验证码
- (void)userVerificationSMS: (NSDictionary *)params
                   complete: (void(^)(void))complete
                    failure: (void(^)(void))failure {
    
    [MBProgressHUDHelper showHudIndeterminate];
    [[ResponseAdapter sharedInstance] request: USER_SMS_VERIFICATION params: params class: nil responseType: Response_Message method: Request_GET needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        [MBProgressHUDHelper hideHud];
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper hideHud];
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
        failure();
    }];
}


#pragma mark - 修改用户手机号
- (void)userEditMobile:(NSDictionary *)params complete:(void (^)(void))complete {
    [MBProgressHUDHelper showHudIndeterminate];
//    [[ResponseAdapter sharedInstance] request: USER_MOBILE_EDIT params: params class: nil responseType: Response_Message method: Request_POST needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        [MBProgressHUDHelper hideHud];
//
//        NSDictionary *info = @{@"mobile" : response.data[@"mobile"]};
//        [[UserManager manager] updateEditInfo: info];
//        
//        NSString *msg = response.message ? response.message : @"手机更换成功";
//        [MBProgressHUDHelper showHudWithText: msg];
//        complete();
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//    
//        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
//    }];
    
}

#pragma mark - 修改用户密码
- (void)userEditPassword:(NSDictionary *)params complete:(void (^)(void))complete {
    [MBProgressHUDHelper showHudIndeterminate];
    
    [[ResponseAdapter sharedInstance] request: USER_PWD_UPDATE params: params class: nil responseType: Response_Message method: Request_POST needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
       
        NSString *msg = response.message ? response.message : @"密码设置成功";
        [MBProgressHUDHelper showHudWithText: msg];
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
    
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
    }];

}

#pragma mark - 设置用户密码
- (void)userSetPassword:(NSDictionary *)params complete:(void (^)(void))complete{

    [[ResponseAdapter sharedInstance] request: USER_PWD_EDIT params: params class: nil responseType: Response_Message method: Request_POST needLogin: YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSString *msg = response.message ? response.message : @"密码设置成功";
        [MBProgressHUDHelper showHudWithText: msg];
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
    }];


}
#pragma mark - 用户注册
//用户注册
- (void)userRegister:(NSDictionary *)params complete:(void(^)(void))complete failure:(void(^)(void))failure{

    [MBProgressHUDHelper showHudIndeterminate];
    [[ResponseAdapter sharedInstance] request: USER_REGISTER params: params class: [UserModel class] responseType: Response_Object method: Request_POST needLogin: NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        UserModel *model = response.data;
        model.loginName  = params[@"mobile"];
        model.isLogin    = [NSNumber numberWithBool: YES];;
        [[UserManager manager] updateLoginInfo: model];
        NSLog(@"用户信息获取成功");
        [MBProgressHUDHelper showHudWithText: response.message];
        [Context context].relayislogin =YES;
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
        failure();
    }];


}
#pragma mark - 用户注销
- (void)userLogoff {
    
//    [[DBManager manager] clearHomeModel];
//    [[DBManager manager] clearHomeReservationModel];
//    [[DBManager manager] clearAppointmentCheckModelStorage];
    [[ResponseAdapter sharedInstance] request: USER_LOGINOFF params: nil class: [UserModel class] responseType: Response_Object method: Request_DELETE needLogin: NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        NSLog(@"%@", response.message);
        UserModel *model = response.data;
        [[UserManager manager]guestLoginUpdateInfo:model];
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
        UserModel *model = response.data;
        [[UserManager manager]guestLoginUpdateInfo:model];
        //        [[UserManager manager]updateLogOffInfo:NO];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"用户注销失败，%@", error.localizedDescription);
    }];
    [[UserManager manager] updateLogOffInfo:NO];
   
    
}

//游客登录
- (void)userGestLoginComplete:(void(^)(void))complete;{
    
    [[ResponseAdapter sharedInstance]request:USER_LOGIN_GUEST params:nil class:[UserModel class] responseType:Response_Object method:Request_GET needLogin:NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        UserModel *model = response.data;
        [[UserManager manager]guestLoginUpdateInfo:model];
        [[UserManager manager]updateLogOffInfo:NO];
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"游客登录失败");
    }];
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

/*
//用户注册
- (void)userRegister:(NSDictionary *)params complete:(void(^)(void))complete {
    complete();
}


//来宾用户登录
- (void)guestLogin:(void(^)(void))complete {
    //未登录用户信息赋值
    [[UserManager manager] updateLogOffInfo];
    complete();
}

//token过期，用返回的来宾用户token进行登录
- (void)loginGuestWithToken:(NSString *)token withKey:(NSString *)key {
    //未登录用户信息赋值
    [[UserManager manager] updateLogOffInfo];
}

#pragma mark - 用户信息编辑
//编辑用户头像
- (void)editAvatar:(NSDictionary *)params complete:(void (^)(void))complete {
    complete();
}

//编辑用户昵称
- (void)editNickName:(NSDictionary *)params complete:(void (^)(void))complete {
    complete();
}

//修改用户手机号
- (void)editMobile:(NSDictionary *)params complete:(void (^)(void))complete {
    complete();
}

//修改用户出生年月
- (void)editBirthday:(NSDictionary *)params complete:(void (^)(void))complete {
    complete();
}

//修改用户性别
- (void)editGender:(NSDictionary *)params complete:(void (^)(void))complete {
    complete();
}
*/
#pragma mark - 实名认证状态
- (void)requestTureNameType:(void(^)(SCTrueNameModel *tureNameModel))success failure:(void (^)(NSError *error))failure {
    if (![UserManager manager].uid) {
        return;
    }
    
//    if(![[Global global] networkReachable])
//    {
//        [MBProgressHUDHelper showHudWithText:@"无网络"];
//        return;
//    }
    
    
    
    NSDictionary *dic = @{
                          @"uid" : [UserManager manager].uid
                          };
    
    [[ResponseAdapter sharedInstance] request:Verification_INFO
                                       params:dic
                                        class:[SCTrueNameModel class]
                                 responseType:Response_Object
                                       method:Request_GET
                                    needLogin:YES
                                      success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                                          SCTrueNameModel *model = response.data;
                                          [UserManager manager].verificationStatus = [model.status intValue];
                                          //[UserManager manager].name = model.name;
                                          [UserManager manager].idcard = model.idcard;
                                          success(model);
                                      } failure:^(NSURLSessionDataTask *task, NSError *error) {
                                          failure(error);
                                      }];
}

#pragma mark - 更新消息未读数
- (void)updateMessageCount:(NSDictionary *)params complete:(void(^)(void))complete failure:(void(^)(void))failure {
    
    [[ResponseAdapter sharedInstance] request:Messages_URL params: params class:[SCSystemModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel * list = response.data;
     
        
        
        for (SCSystemModel *message in list.content) {
            [UserManager manager].messageCount +=[message.messageCount integerValue];
        }
        complete();
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        failure();
    }];
    
}

@end
