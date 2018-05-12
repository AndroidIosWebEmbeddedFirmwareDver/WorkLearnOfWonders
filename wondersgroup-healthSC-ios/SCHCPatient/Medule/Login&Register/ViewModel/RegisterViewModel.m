//
//  RegisterViewModel.m
//  SCHCPatient
//
//  Created by Jam on 2016/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "RegisterViewModel.h"

@implementation RegisterViewModel

- (id)init {
    self = [super init];
    if (self) {
        self.enableRegister = NO;
        self.enableSend     = NO;
        [self bindRegisterViewModel];
    }
    return self;
}

- (void)bindRegisterViewModel {
    
    RAC(self, enableRegister) = [RACSignal
                                 combineLatest:@[ RACObserve(self, mobile), RACObserve(self, code)]
                                 reduce:^(NSString *mobile, NSString *code) {
                                     return @(([RegexKit validateMobile: mobile] && [code length] > 0));
                                 }];

    //监听发送按钮是否能够使用
    RAC(self, enableSend) = [RACSignal
                             combineLatest:@[ RACObserve(self, mobile)]
                             reduce:^(NSString *mobile) {
                                 return @([RegexKit validateMobile: mobile]);
                             }];

}

//发送验证码

- (void)getVerifyCode:(void(^)(void))complete failure:(void (^)(void))failure{
    
    
    
    
}
//手机号注册
- (void)registerUser:(void (^)(void))complete failure:(void (^)(void))failure{
    

}

- (void)checkVerifyCode:(void(^)(void))complete failure:(void (^)(void))failure{
    
    
}
@end
