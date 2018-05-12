//
//  LoginViewModel.m
//  VaccinePatient
//
//  Created by Jam on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "LoginViewModel.h"
#import "UserService.h"
#import "UserModel.h"
#import "RSA.h"

@implementation LoginViewModel

- (id)init {
    self = [super init];
    if (self) {
      
    }
    return self;
}

#pragma mark - 用户登录
- (void)userLogin:(IMPLCompleteBlock)complete {
    
        NSDictionary *params = @{
                                 @"mobile"   : self.mobile,
                                 @"password" : [RSA rsaPassword: self.password]
                                 };
        [[UserService service] userPWDLogin: params complete:^{
            complete();
        }];

}



@end
