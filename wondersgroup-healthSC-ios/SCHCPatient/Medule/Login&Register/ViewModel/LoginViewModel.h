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
    LoginViewTypeCode,              //验证码登录
    LoginViewTypeForget,            //忘记密码登录
    LoginViewTypeFirst,             //第一次安装APP注册登录
    
} LoginViewType;



@interface LoginViewModel : BaseViewModel <UserCenterIMPL>

@property (nonatomic, assign) LoginViewType viewType;



@property (nonatomic, copy) NSString *mobile;           //验证码登录手机号
@property (nonatomic, copy) NSString *password;             //密码


@property (nonatomic, copy) NSString *code;                 //验证码

@property (nonatomic, copy) NSString *headIcon;             //标题图片
@property (nonatomic, copy) NSString *headTip;              //标题文字

@property (nonatomic, copy) NSString *inputPlaceholder1;    //输入框placeholder
@property (nonatomic, copy) NSString *inputPlaceholder2;    //密码、验证码输入框placeholder

@property (nonatomic, copy) NSString *inputLeaderImage1;    //输入框标题图片
@property (nonatomic, copy) NSString *inputLeaderImage2;    //密码、验证码输入框标题图片

@property (nonatomic, copy) NSString *submitTitle;          //提交按钮文案


@property (nonatomic, assign) BOOL enableLogin;

@property (nonatomic,assign)BOOL enableChange;
@property (nonatomic, assign) BOOL enableCodeLogin;

@property (nonatomic, assign) BOOL enableSend;              //是否能发送验证码


//根据页面类型载入页面需要显示数据
- (void)loadShowViewData;
//绑定RAC
- (void)bindLoginViewModel;

@end
