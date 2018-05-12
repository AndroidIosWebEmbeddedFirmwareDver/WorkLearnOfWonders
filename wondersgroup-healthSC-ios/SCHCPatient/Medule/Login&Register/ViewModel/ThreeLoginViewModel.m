//
//  ThreeLoginViewModel.m
//  SCHCPatient
//
//  Created by 杜凯 on 2016/11/7.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ThreeLoginViewModel.h"
#import "UserService.h"

static NSString *weiboLoginURL      = @"token/thirdparty/weibo";//微博登录的url
static NSString *weixinLoginURL     = @"token/thirdparty/wechat";//微信登录的url
static NSString *qqLoginURL         = @"token/thirdparty/qq";   //qq登录的URL
@implementation ThreeLoginViewModel
- (void)thirdpartyLogin: (NSDictionary *)params type:(SSDKPlatformType)type
                Success: (void (^)(void))success
                failure: (void (^)(NSError *error))failure{

    NSString *url = weiboLoginURL;
    switch (type) {
        case SSDKPlatformTypeSinaWeibo:
            url = weiboLoginURL;
            break;
        case SSDKPlatformTypeWechat:
            url = weixinLoginURL;
            break;
        case SSDKPlatformTypeQQ:
            url = qqLoginURL;
            break;
            
        default:
            break;
    }
    [self.adapter request:url
                   params:params
                    class:[UserModel class]
             responseType:Response_Object
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      UserModel *model = response.data;
                      model.isLogin    = [NSNumber numberWithBool: YES];
                      [[UserManager manager] updateLoginInfo: model];
                      success();
                     
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      
                  }];

    

}
@end
