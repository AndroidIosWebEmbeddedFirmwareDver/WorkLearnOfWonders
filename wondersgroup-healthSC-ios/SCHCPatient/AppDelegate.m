//
//  AppDelegate.m
//  SCHCPatient
//
//  Created by Jam on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AppDelegate.h"
#import "DBManager.h"
#import "VCManager.h"
#import "GetuiPushManager.h"
#import <UserNotifications/UserNotifications.h>
#import "LocationManager.h"

#import "EvaluateView.h"

#import "MBProgressHUDHelper.h"

#import "ShouYinTaiAPI.h"
#import <HyphenateLite/HyphenateLite.h>


@interface AppDelegate ()<UNUserNotificationCenterDelegate>

@end

@implementation AppDelegate

#pragma mark - APP启动所需要的服务
- (void)appNeedStart {
//    if (DEVICE_VERSION_FLOAT >= 10.0) {
//        UNUserNotificationCenter *notifCenter = [UNUserNotificationCenter currentNotificationCenter];
//        [notifCenter requestAuthorizationWithOptions:UNAuthorizationOptionBadge|UNAuthorizationOptionSound|UNAuthorizationOptionAlert|UNAuthorizationOptionCarPlay completionHandler:^(BOOL granted, NSError * _Nullable error) {
//            
//            if (granted) {
//                notifCenter.delegate = self;
//            }
//        }];
//        [[UIApplication sharedApplication] registerForRemoteNotifications];
//    }
    
    
    //监听网络状态
    [[Global global] startGlobal];
    
    //初始化数据库处理
    [[DBManager manager] loadDatabase];
    
    //开启TaskService， 获取App配置
    [[TaskService service] startService];
    
    //初始化用户信息
    [[UserManager manager] loadUser];
    
    //启动百度地图
    [[LocationManager manager] startLocation];
    
    
    //友盟分析统计
    [UMTrack startUMTrackWithAppkey:UM_TRACK_KEY];
    
    
}


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
//    [[GetuiPushManager pushManager] application:application didFinishLaunchingWithOptions:launchOptions];
    
    //APP启动所需要的服务
    [self appNeedStart];
    
    //启动 VCManager 设置UI
    [[VCManager manager] application:application didFinishLaunchingWithOptions:launchOptions];
    EMError *error = [[EMClient sharedClient] loginWithUsername:@"sctest002" password:@"111111"];
    if (!error) {
        NSLog(@"登录成功");
    }
    
    return YES;
}


#pragma mark - About Push
/** 远程通知注册成功委托 */
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
//    [[GetuiPushManager pushManager] application:application didRegisterForRemoteNotificationsWithDeviceToken:deviceToken];
}

/** 远程通知注册失败委托 */
- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
//    [[GetuiPushManager pushManager] application:application didFailToRegisterForRemoteNotificationsWithError:error];
}

#pragma mark APP运行中接收到通知(推送)处理

/** APP已经接收到“远程”通知(推送) - (App运行在后台/App运行在前台) */
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
//    [[GetuiPushManager pushManager] application:application didReceiveRemoteNotification:userInfo];
}

/** APP已经接收到“远程”通知(推送) - 透传推送消息  */
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult result))completionHandler
{
//    [[GetuiPushManager pushManager] application:application didReceiveRemoteNotification:userInfo fetchCompletionHandler:completionHandler];
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void(^)())completionHandler {
//    [[GetuiPushManager pushManager] application: [UIApplication sharedApplication] didReceiveRemoteNotification:response.notification.request.content.userInfo fetchCompletionHandler: completionHandler];
    completionHandler();
}


- (void)application:(UIApplication *)application handleActionWithIdentifier:(nullable NSString *)identifier forRemoteNotification:(NSDictionary *)userInfo completionHandler:(void(^)())completionHandler
{
    
}

#pragma mark - 其它APP调回

- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url {
    return [[ShouYinTaiAPI sharedInstance] handleOpenUrl:url];
}

- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation {
    return [ShouYinTaiAPI handleOpenUrl:url];
}

- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<UIApplicationOpenURLOptionsKey,id> *)options {
    return [ShouYinTaiAPI handleOpenUrl:url];
}

#pragma mark - App前后太切换

- (void)applicationWillResignActive:(UIApplication *)application {
    
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    [[EMClient sharedClient] applicationDidEnterBackground:application];

}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    [[EMClient sharedClient] applicationWillEnterForeground:application];

}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    [[GetuiPushManager pushManager] applicationDidBecomeActive:application];
    [[NSNotificationCenter defaultCenter] postNotificationName:@"LINK_PAY_RELOAD_ORDER" object:nil];
}

- (void)applicationWillTerminate:(UIApplication *)application {

}


@end
