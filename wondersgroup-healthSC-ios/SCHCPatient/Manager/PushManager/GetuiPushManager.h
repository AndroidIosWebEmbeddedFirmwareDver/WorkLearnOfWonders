//
//  GetuiPushManager.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void(^OprateReuslt)(BOOL success);

@interface GetuiPushManager : NSObject
{
    BOOL _isPushOn;
}
@property(nonatomic, assign, readonly)BOOL isPushOn;

+ (instancetype)pushManager;

- (void)setPushOn:(BOOL)isOn Reuslt:(OprateReuslt)reuslt;

//App入口调用
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;

/** 远程通知注册成功委托 */
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken;

/** 远程通知注册失败委托 */
- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error;

#pragma mark - APP运行中接收到通知(推送)处理

/** APP已经接收到“远程”通知(推送) - (App运行在后台/App运行在前台) */
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo;

/** APP已经接收到“远程”通知(推送) - 透传推送消息  */
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult result))completionHandler;

#pragma mark - 前后台切换
- (void)applicationWillResignActive:(UIApplication *)application;

- (void)applicationDidEnterBackground:(UIApplication *)application;

- (void)applicationWillEnterForeground:(UIApplication *)application;

- (void)applicationDidBecomeActive:(UIApplication *)application;

- (void)applicationWillTerminate:(UIApplication *)application;

@end
