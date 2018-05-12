//
//  DefineConfig.h
//  SCHCPatient
//
//  Created by Jam on 2016/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AppDelegate.h"

#import "NSString+StringNull.h"
#ifndef DefineConfig_h
#define DefineConfig_h

#define ERROR_DOMAIN            @"com.wonders.health.venus.open.user"
//推送开关
#define WDPUSHKEY               @"WDAppPush"
//Request的Header Keys(带TOKEN)
#define REQUEST_HEADERS_TOKEN   @[@"access-token", @"app-version", @"channel", @"device", @"model"     , @"os-version", @"platform"     , @"screen-height", @"screen-width", @"version"]
//Request的Header Keys(不带TOKEN)
#define REQUEST_HEADERS         @[@"app-version" , @"channel"    , @"device" , @"model" , @"os-version", @"platform"  , @"screen-height", @"screen-width" , @"version"]


#pragma mark    - UserDefaults
#define LoginStatusType             @"LoginStatusType"
#define GuestUserToken              @"GuestUserToken"
#define GuestUserKey                @"GuestUserKey"
#define UserOldPwd              @"UserOldPwd"

#pragma mark - UI
#define SCREEN_BOUNDS           [[UIScreen mainScreen] bounds]                          //屏幕尺寸
#define SCREEN_HEIGHT           SCREEN_BOUNDS.size.height                               //屏幕高度
#define SCREEN_HEIGHT_STR       [NSString stringWithFormat: @"%d", (int)SCREEN_HEIGHT]  //屏幕高度 String
#define SCREEN_WIDTH            SCREEN_BOUNDS.size.width                                //屏幕宽度
#define SCREEN_WIDTH_STR        [NSString stringWithFormat: @"%d", (int)SCREEN_WIDTH]   //屏幕宽度 String
/**
 *  传入iPhone6尺寸的frame，转化为各分辨率的尺寸
 */
//缩小
#define AdaptiveFrameWidth(width) width*(SCREEN_WIDTH/375.0)
#define AdaptiveFrameHeight(height) height*(SCREEN_HEIGHT/667.0)
//放大
#define AdaptiveFrameWidth2(width) width/(SCREEN_WIDTH/375.0)
#define AdaptiveFrameHeight2(height) height/(SCREEN_HEIGHT/667.0)


#pragma mark - 系统信息定义
#define APP                     ((AppDelegate*)[[UIApplication sharedApplication] delegate])                        //AppDelegate 的指针
#define APP_VERSION             [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"] //APP版本
#define APP_BUILD_VERSION       [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleVersion"]            //APPbuild版本
#define APP_USERDEFAULTS        [NSUserDefaults standardUserDefaults]                                               //APP的UserDefaults
#define DEVICE_MODEL            [[UIDevice currentDevice] model]                                                    //设备型号
#define DEVICE_VERSION          [[UIDevice currentDevice] systemVersion]                                            //设备系统版本
#define DEVICE_VERSION_FLOAT    [[[UIDevice currentDevice] systemVersion] floatValue]                               //设备系统版本Float类型

#pragma mark - 判断定义
#define SCREEN_MAX_LENGTH (MAX(SCREEN_WIDTH, SCREEN_HEIGHT))
#define SCREEN_MIN_LENGTH (MIN(SCREEN_WIDTH, SCREEN_HEIGHT))
#define IS_IPAD                 (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) //是否是ipad
#define IS_IPHONE               (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) //是否是iPhone
#define IS_IPHONE_4_OR_LESS     (IS_IPHONE && SCREEN_MAX_LENGTH < 568.0)            //iPhone4、4S以下的设备
#define IS_IPHONE_5             (IS_IPHONE && SCREEN_MAX_LENGTH == 568.0)           //iPhone5、5S
#define IS_IPHONE_5_OR_LESS     (IS_IPHONE_5 || IS_IPHONE_4_OR_LESS)                //iPhone5、5S以下的设备
#define IS_IPHONE_6             (IS_IPHONE && SCREEN_MAX_LENGTH == 667.0)           //iPhone6、6S
#define IS_IPHONE_6P            (IS_IPHONE && SCREEN_MAX_LENGTH == 736.0)           //iPhone6P、6PS
#define IS_RETINA               ([[UIScreen mainScreen] scale] >= 2.0)      //是否是Retina屏幕
#define IS_IOS7                 (DEVICE_VERSION_FLOAT >= 7 && DEVICE_VERSION_FLOAT < 8)   //IOS 7系统
#define IS_IOS8                 (DEVICE_VERSION_FLOAT >= 8 && DEVICE_VERSION_FLOAT < 9)   //IOS 8系统
#define IS_IOS9                 (DEVICE_VERSION_FLOAT >= 9)                               //IOS 9系统
#define IS_IOS7_OR_LATER        (DEVICE_VERSION_FLOAT >= 7)                               //IOS 7以及以上的系统
#define IS_IOS8_OR_LATER        (DEVICE_VERSION_FLOAT >= 8)                               //IOS 8以及以上的系统
#define IS_IOS9_OR_LATER        (DEVICE_VERSION_FLOAT >= 9)                               //IOS 9以及以上的系统
#define IS_IOS10_OR_LATER        (DEVICE_VERSION_FLOAT >= 10)                               //IOS 10以及以上的系统
#define  StringOrNUll(str) [NSString StringNull:str]
#pragma mark - 通用方法定义

#define ALERT(title,msg) [[[UIAlertView alloc] initWithTitle:title message:msg delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil] show]

//日志输出

#ifndef __OPTIMIZE__
#define NSLog(format, ...) printf("[%s] %s [第%d行] %s\n", __TIME__, __FUNCTION__, __LINE__, [[NSString stringWithFormat:format, ## __VA_ARGS__] UTF8String])
#else
#define NSLog(...) {}
#endif




//公钥
#define PUBKEY @"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoHgm9p0TUE/Ss1QBLUi/agj18CXflkeefZIh6d/ZhHANDgvsvpXehmU3yvvNLD4t3i3UkhBlA7UKINtxJW/CzycoOPRcRa7wh+3rd237I+FHtbq48IkJQ5uCqtGB2mt9uRES3VK9bSqQL8qIxhpR2sdFwojBgBQt0L6nskFSPv90ldJXTHlqZDanWyBsdT+CJy8ZBddAN6kNWEFmEclC8Xp+SECV0Cys0m760GVTh9pJ7zxJyVgAMMlhl1wRvP4LnBqAO+xtI1OxaXv/5Xre665u9YmxEd+Wbz/MWy8Gzb78CCdq3XB/og9Hhl+oJTjwYwYiCMmYrNHtV6JtdDRqLQIDAQAB"


#pragma mark - Frameworks
#import <objc/runtime.h>


//Masnory  弱引用
#define WS(weakSelf)  __weak __typeof(&*self)weakSelf = self;


#pragma mark - 通用方法定义
//判断是否为NULL
#define ISNULL(value) (((NSNull *)value == [NSNull null]) || (value == nil) || (((NSString *)value).length==0))
#define CHECKNULL(value) (((NSNull *)value == [NSNull null]) || (value == nil) || (((NSString *)value).length==0) ? @"" : value)


#pragma mark    - NSNotificationCenter

#define COLLECTEDRELOADDATA                         @"COLLECTEDRELOADDATA"                          //收藏刷新

#define LOCATION_WARING_SETTING                     @"LOCATION_WARING_SETTING"                   //首页定位弹框

#pragma mark    - UserDefaults


#endif /* DefineConfig_h */
