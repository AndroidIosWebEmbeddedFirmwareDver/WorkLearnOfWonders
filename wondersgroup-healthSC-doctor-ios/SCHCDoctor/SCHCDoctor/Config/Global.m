//
//  Global.m
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "Global.h"
#import "RootTabBarController.h"
#import "AFNetworkReachabilityManager.h"
#import "WDRefreshHeader.h"
#import "UrlParamUtil.h"

#import <ShareSDK/ShareSDK.h>
#import <ShareSDKConnector/ShareSDKConnector.h>
//腾讯开放平台（对应QQ和QQ空间）SDK头文件
#import <TencentOpenAPI/TencentOAuth.h>
#import <TencentOpenAPI/QQApiInterface.h>
//微信SDK头文件
#import "WXApi.h"
//新浪微博SDK头文件
#import "WeiboSDK.h"
//新浪微博SDK需要在项目Build Settings中的Other Linker Flags添加"-ObjC"


//#import "AppointmentDetailViewController.h"

@implementation Global


#pragma mark - WDGlobal单例
+ (instancetype)global {
    static Global *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[Global alloc] init];
    });
    return instance;
}

-(id)init
{
    self = [super init];
    if (self) {
        self.networkReachable = YES;
    }
    return self;
}

//APP启动Global所需
- (void)startGlobal {
    //设置设备UUID
    [self getDeviceUUID];
    
    //开启网络监听
    [self startNetWorkingMoniting];
    
    //选择服务器
    [self selectServer];
    
//    [BFRouter router].classWithH5Block = ^(NSString *url){
//        NSDictionary *parms = [UrlParamUtil getParamsFromUrl: [NSURL URLWithString:url]];
//        NSString *for_type = [parms valueForKey: @"for_type"];
//        if (for_type.length > 0 && ([for_type isEqualToString:@"new_article"] || [for_type isEqualToString:@"article"])) {
//            return @"WDArticleWebViewController";
//        }
//        return @"WDBaseWebViewController";
//    };
    /*
    [BFRouter router].canOpenURLBlock = ^(NSString *url){
        BFRouterPath *routerPath = [[BFRouter router] getRouterPathFromInnerURL:url];
        
        if (routerPath.path && [routerPath.path isEqualToString:@"reservation_detail"]) {
            
            NSDictionary *parms = [UrlParamUtil getParamsFromUrl: [NSURL URLWithString:url]];
            AppointmentDetailViewController *vc = [AppointmentDetailViewController new];
            
            if (parms[@"reservationId"]) {
                vc.viewModel.reservationId = parms[@"reservationId"];
            }
            vc.isMyAppointment = NO;
            vc.hidesBottomBarWhenPushed = YES;
            
            dispatch_async(dispatch_get_main_queue(), ^{
                [[BFRouter router].navi pushViewController:vc animated:YES];
            });
            return NO;
        }
        return YES;
    };
    */
    
    //注册ShareSDK分享
//    [self registerShareSdk];
}

#pragma mark --监听网络状态
- (void)startNetWorkingMoniting {
    
    AFNetworkReachabilityManager *manager = [AFNetworkReachabilityManager sharedManager];
    [manager setReachabilityStatusChangeBlock:^(AFNetworkReachabilityStatus status) {
        switch (status) {
            case AFNetworkReachabilityStatusUnknown:
                NSLog(@"未识别的网络");
                self.networkReachable = YES;
                break;
                
            case AFNetworkReachabilityStatusNotReachable:
                NSLog(@"不可达的网络(未连接)");
                self.networkReachable = NO;
                break;
                
            case AFNetworkReachabilityStatusReachableViaWWAN:
                NSLog(@"2G,3G,4G...的网络");
                self.networkReachable = YES;
                break;
                
            case AFNetworkReachabilityStatusReachableViaWiFi:
                NSLog(@"wifi的网络");
                self.networkReachable = YES;
                break;
            default:
                break;
        }
    }];
    //开始监听
    [manager startMonitoring];
}


#pragma mark - 选择服务器
- (void)selectServer {
    self.BASE_URL   = [NSURL URLWithString:API_BASE_URL];
    NSLog(@"BASE_URL-------->>>>>%@", self.BASE_URL);
}


#pragma mark - 注册ShareSDK分享
- (void)registerShareSdk{

    [ShareSDK registerApp: SHARE_SDK_KEY
     
          activePlatforms:@[
                            @(SSDKPlatformTypeSinaWeibo),
                            @(SSDKPlatformTypeWechat),
                            @(SSDKPlatformTypeQQ),
                            ]
                 onImport:^(SSDKPlatformType platformType)
     {
         switch (platformType)
         {
             case SSDKPlatformTypeWechat:
                 [ShareSDKConnector connectWeChat:[WXApi class]];
                 break;
             case SSDKPlatformTypeQQ:
                 [ShareSDKConnector connectQQ:[QQApiInterface class] tencentOAuthClass:[TencentOAuth class]];
                 break;
             case SSDKPlatformTypeSinaWeibo:
                 [ShareSDKConnector connectWeibo:[WeiboSDK class]];
                 break;
             default:
                 break;
         }
     }
          onConfiguration:^(SSDKPlatformType platformType, NSMutableDictionary *appInfo)
     {
         
         switch (platformType)
         {
             case SSDKPlatformTypeSinaWeibo:
                 //设置新浪微博应用信息,其中authType设置为使用SSO＋Web形式授权
                 [appInfo SSDKSetupSinaWeiboByAppKey:SINA_WEIBO_APP_KEY
                                           appSecret:SINA_WEIBO_APP_SECRET
                                         redirectUri:SINA_WEIBO_REDIRECT_RUL
                                            authType:SSDKAuthTypeBoth];
                 break;
             case SSDKPlatformTypeWechat:
                 [appInfo SSDKSetupWeChatByAppId:WECHAT_APP_ID
                                       appSecret:WECHAT_APP_SECRET];
                 break;
             case SSDKPlatformTypeQQ:
                 [appInfo SSDKSetupQQByAppId:QQ_APP_ID
                                      appKey:QQ_APP_KEY
                                    authType:SSDKAuthTypeBoth];
                 break;
                 
                 
             default:
                 break;
         }
     }];

}

- (void)getDeviceUUID {
    NSString *deviceUUID  = [[UserDefaults shareDefaults] objectForKey: @"Device_UUID"];
    if (deviceUUID) {
        deviceUUID = [[NSUUID UUID] UUIDString];
        [[UserDefaults shareDefaults] setObject: deviceUUID  forKey: @"Device_UUID"];
    }
    
    self.deviceUUID = deviceUUID;
}

#pragma mark    - 加线
- (void)addBottomLineInView:(UIView*)view withColor:(UIColor *)color {
       UIView *line = [[UIView alloc] init];
    [view addSubview:line];
    line.backgroundColor = color;
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.width.equalTo(view);
        make.height.mas_equalTo(0.5);
        make.left.bottom.equalTo(view);
    }];
}

- (void)addTopLineInView:(UIView*)view withColor:(UIColor *)color {
    UIView *line = [[UIView alloc] init];
    [view addSubview:line];
    line.backgroundColor = color;
    [line mas_makeConstraints:^(MASConstraintMaker *make) {
        make.width.equalTo(view);
        make.height.mas_equalTo(0.5);
        make.left.top.equalTo(view);
    }];
}
@end
