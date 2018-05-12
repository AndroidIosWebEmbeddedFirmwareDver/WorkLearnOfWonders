//
//  SCUserViewModel.m
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCUserViewModel.h"
#import "UserService.h"
#import "RSA.h"
@implementation SCUserViewModel


//发送验证码

- (void)getVerifyCode:(void(^)(void))complete failure:(void (^)(void))failure{
    
    NSDictionary *params = @{
                             @"mobile"   : self.phone,
                             @"type" : @(self.codeType),
                             };
    
    [[UserService service]userSendSMS:params complete:^{
        complete();
    } failure:^{
        
    }];
    
    
}

// 重置密码

- (void)resetPassword:(void (^)(void))complete failure:(void (^)(void))failure{
    NSString * oldPwd =  [[UserManager manager]getUserPwd];
    
    NSMutableDictionary * params = [NSMutableDictionary dictionaryWithCapacity:3];
    [params setObject: StringOrNUll([UserManager manager].uid)  forKey: @"uid" ];
    [params setObject:  [RSA rsaPassword: self.password]forKey:@"new_password" ];
    if ([oldPwd length]) {
        [params setObject:oldPwd forKey:@"previous_password" ];
    }
     NSLog(@"－密码+++ －－%@－－－",[[UserManager manager]getUserPwd]);
    [[UserService service]userEditPassword:params complete:^{
        [[UserManager manager]saveUserPwd:[RSA rsaPassword: self.password]];
        complete();
    }];
}
// 设置密码
- (void)setPassword:(void (^)(void))complete failure:(void (^)(void))failure{
    NSMutableDictionary * params = [NSMutableDictionary dictionaryWithCapacity:3];
     [params setObject: self.phone  forKey: @"mobile"];
     [params setObject:  [RSA rsaPassword: self.password]forKey:@"password" ];
    if ([self.code length]) {
        [params setObject:self.code forKey:@"code" ];
    }

    [[UserService service]userSetPassword:params complete:^{
        [[UserManager manager]saveUserPwd:[RSA rsaPassword: self.password]];
        complete();
    }];

}
- (void)checkVerifyCode:(void(^)(void))complete failure:(void (^)(void))failure{
    NSDictionary *params = @{
                             @"mobile"   : self.phone,
                             @"code" : self.code
                             };
    [[UserService service]userVerificationSMS:params complete:^{
        complete();
    } failure:^{
        
    }];
    
}
//手机号注册
- (void)registerUser:(void (^)(void))complete failure:(void (^)(void))failure{
    
    [[UserService service]userRegister:nil complete:^{
        
    } failure:^{
        
    }];
}
@end
