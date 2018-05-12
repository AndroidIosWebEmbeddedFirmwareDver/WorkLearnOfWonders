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

- (void)bindLoginViewModel {
    /*
    RAC(self, enableLogin) = [RACSignal
                              combineLatest:@[ RACObserve(self, passwordMobile), RACObserve(self, password)]
                              reduce:^(NSString *mobile, NSString *pwd) {
                                  return @(([RegexKit validateMobile: mobile] && [pwd length] > 0));
                              }];

    RAC(self, enableCodeLogin) = [RACSignal
                                  combineLatest:@[ RACObserve(self, codeMobile), RACObserve(self, code)]
                                  reduce:^(NSString *mobile, NSString *code) {
                                      return @(([RegexKit validateMobile: mobile] && [RegexKit validateDynamicCode: code]));
                                  }];

    //监听发送按钮是否能够使用
    RAC(self, enableSend) = [RACSignal
                             combineLatest:@[ RACObserve(self, codeMobile)]
                             reduce:^(NSString *mobile) {
                                 return @([RegexKit validateMobile: mobile]);
                            }];
*/
    
}

#pragma mark - 根据页面类型载入页面需要显示数据
- (void)loadShowViewData {

    self.inputLeaderImage1  = @"登录页logo";
    self.inputPlaceholder1  = @"手机号码";

    switch (self.viewType) {
        case LoginViewTypePWD: {
            self.headIcon           = @"登录页logo";
            self.headTip            = @"login_slogan";
            
            self.inputLeaderImage2  = @"icon_yanzhengma";
            self.inputPlaceholder2  = @"请输入密码";
            
            self.submitTitle        = @"登录";
        }
            break;
        case LoginViewTypeCode: {
            self.headIcon           = @"登录页logo";
            self.headTip            = @"家有宝贝初养成";
            
            
            self.inputLeaderImage2  = @"icon_yanzhengma";
            self.inputPlaceholder2  = @"请输入验证码";
            
            self.submitTitle        = @"登录";
        }
            break;
        case LoginViewTypeForget: {
            self.headIcon           = @"登录发送信息img";
            self.headTip            = @"请填写可用手机号，轻松登录app";
            self.submitTitle        = @"下一步";
        }
            break;
        case LoginViewTypeFirst: {
            self.headIcon           = @"登录发送信息img";
            self.headTip            = @"请填写可用手机号，轻松登录app";
            self.submitTitle        = @"下一步";
        }
            break;
        default:
            break;
    }
}

#pragma mark - 用户登录
- (void)userLogin:(IMPLCompleteBlock)complete {
    
    if (self.viewType == LoginViewTypePWD) {

        NSDictionary *params = @{
                                 @"mobile"   : self.mobile,
                                 @"password" : [RSA rsaPassword: self.password]
                                 };
        [[UserService service] userPWDLogin: params complete:^{
            complete();
        }];

    }
    else if (self.viewType == LoginViewTypeCode){

        NSDictionary *params = @{
                                 @"mobile"  : self.mobile,
                                 @"code"    : self.code
                                 };
        [[UserService service] userCodeLogin: params complete:^{
            
            complete();
        }];
    }
}



@end
