//
//  LoginViewModel.h
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

typedef enum {
    
    LoginViewTypePWD ,              //账号密码登录
    LoginViewTypeFirst,             //第一次安装APP注册登录
    
} LoginViewType;



@interface LoginViewModel : BaseViewModel <UserCenterIMPL>

@property (nonatomic, assign) LoginViewType viewType;

@property (nonatomic, copy) NSString *mobile;           //验证码登录手机号
@property (nonatomic, copy) NSString *password;             //密码

@end
