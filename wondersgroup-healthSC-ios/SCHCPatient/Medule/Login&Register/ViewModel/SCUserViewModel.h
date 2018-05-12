//
//  SCUserViewModel.h
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
typedef NS_ENUM(NSUInteger, VerifyCodeType) {
    VerifyCodeTypeDeafult = 0,  // 默认
    VerifyCodeTypePhoneLogin = 1,  //手机动态码
     VerifyCodeTypeForgetPassword = 2,  //忘记密码
    /*
     .-' _..`.
     /  .'_.'.'
     | .' (.)`.
     ;'   ,_   `.
     .--.__________.'    ;  `.;-'
     |  ./               /
     |  |               /
     `..'`-._  _____, ..'
     / | |     | |\ \
     / /| |     | | \ \
     / / | |     | |  \ \
     /_/  |_|     |_|   \_\
     |__\  |__\    |__\  |__\
     */
    VerifyCodeTypeSetPassword = 3,  //设置密码
};
@interface SCUserViewModel : BaseViewModel<UserCenterIMPL>

//用户登录账户
@property (nonatomic, copy) NSString * phone;
//密码
@property (nonatomic, copy) NSString *password;


@property (nonatomic, copy) NSString *confirmPassword;
//验证码
@property (nonatomic, copy) NSString *code;

@property (nonatomic, assign) VerifyCodeType codeType;

@end
