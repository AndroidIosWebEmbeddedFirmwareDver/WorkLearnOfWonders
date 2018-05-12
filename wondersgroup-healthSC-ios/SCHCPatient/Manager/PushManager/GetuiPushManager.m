//
//  GetuiPushManager.m
//  VaccinePatient
//
//  Created by maorenchao on 16/6/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "GetuiPushManager.h"
//#import "GeTuiSdk.h"
#import "PushManager.h"

#define LocalPushSwitch @"LocalPushSwitch"

@interface GetuiPushManager()
//<GeTuiSdkDelegate>
@property(nonatomic, strong)OprateReuslt oprateReuslt;

@end

@implementation GetuiPushManager

+ (instancetype)pushManager
{
    static GetuiPushManager *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[GetuiPushManager alloc] init];
    });
    return instance;
}

- (instancetype)init
{
    self  = [super init];
    if(self) {
        [self initLocalIsPushOn];
        
        [self bindLoginState];
    }
    return self;
}

#pragma mark - Push Opreate

- (void)bindLoginState
{
    WS(weakSelf);
    [RACObserve([UserManager manager],isLogin) subscribeNext:^(NSNumber *isLogin) {
        if (isLogin.boolValue) {
            [weakSelf bindAlias];
        }
        else
        {
            if ([UserManager manager].uid.length > 0) {
                [weakSelf setPushOn:YES Reuslt:nil];
            }
            [weakSelf unBindAlias];
        }
    }];
}

- (void)unBindAlias
{
    if ([UserManager manager].uid.length > 0) {
        NSLog(@"GeTuiSdk unbind alias:%@",[UserManager manager].uid);
//        [GeTuiSdk unbindAlias:[UserManager manager].uid];
    }
}

- (void)bindAlias
{
    if ([UserManager manager].uid.length > 0) {
        NSLog(@"GeTuiSdk bind alias:%@",[UserManager manager].uid);
//        [GeTuiSdk bindAlias:[UserManager manager].uid];
    }
}

- (void)setPushOn:(BOOL)isOn Reuslt:(OprateReuslt)reuslt
{
//    [GeTuiSdk setPushModeForOff:!isOn];
    
    [self setLocalIsPushOn:isOn];
}

- (BOOL)isPushOn
{
    return _isPushOn;
}

- (void)setLocalIsPushOn:(BOOL)isPushOn
{
    NSUserDefaults *userDefault = [NSUserDefaults standardUserDefaults];
    [userDefault setObject:isPushOn?@"YES":@"NO" forKey:LocalPushSwitch];
}

- (void)initLocalIsPushOn
{
    NSUserDefaults *userDefault = [NSUserDefaults standardUserDefaults];
    
    NSString *isOn = [userDefault objectForKey: LocalPushSwitch];
    if (isOn && [isOn isEqualToString:@"YES"]) {
        _isPushOn = YES;
    }
    else if (isOn && [isOn isEqualToString:@"NO"])
    {
        _isPushOn = NO;
    }
    else
    {
        [userDefault setObject:@"YES" forKey:LocalPushSwitch];
        _isPushOn = YES;
    }
}

#pragma mark - UIApplicationDelegate
//App入口调用  此方法中注册推送
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
//    // 通过 appId、 appKey 、appSecret 启动SDK，注：该方法需要在主线程中调用
//#if ERELEASE
//    //生产环境
//    [GeTuiSdk startSdkWithAppId:@"TmAGBW3nam86kpQsKL5CZ1" appKey:@"vKzmKpThKj8pvle6PauyI" appSecret:@"pUGZWdVLwKA2XEcHKSkGW4" delegate:self];
//#else
//    //测试环境
//    [GeTuiSdk startSdkWithAppId:@"MlJlonSwAo5xBm8YuIkww7" appKey:@"8kGYVDGFYe9tXN81tfw0n2" appSecret:@"2WZH2RIsT68ZIYITVMtKv3" delegate:self];
//#endif
//
//    [GeTuiSdk runBackgroundEnable:NO];
    // 注册APNS
    if ([[[UIDevice currentDevice] systemVersion] floatValue] < 10.0) {
        [self registerUserNotification];
    }
    
//    NSDictionary *push = [launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey];
//    
//    if (push) {
//        NSString *content = [NSString stringWithFormat:@"a%@",push];
//        ALERT(@"推送数据接收 from main",content);
//    }
    
    
    return YES;
}

/** 远程通知注册成功委托 */
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
    NSString *token = [[deviceToken description] stringByTrimmingCharactersInSet:[NSCharacterSet characterSetWithCharactersInString:@"<>"]];
    token = [token stringByReplacingOccurrencesOfString:@" " withString:@""];
    NSLog(@"\n>>>[DeviceToken Success]:%@\n\n", token);
    
    // [3]:向个推服务器注册deviceToken
//    [GeTuiSdk registerDeviceToken:token];
}

/** 远程通知注册失败委托 */
- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
    
}

#pragma mark APP运行中接收到通知(推送)处理

/** APP已经接收到“远程”通知(推送) - (App运行在后台/App运行在前台) */
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
    application.applicationIconBadgeNumber = 0;
}

/** APP已经接收到“远程”通知(推送) - 透传推送消息  */
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult result))completionHandler
{
//    {
//        aps =     {
//            alert = "\U517b\U6a59";
//            badge = 1;
//            category = "$\U7531\U5ba2\U6237\U7aef\U5b9a\U4e49";
//            "content-available" = 1;
//            sound = default;
//        };
//        content = {"title":"59-使用别名对单个用户进行推送-TransmissionTemplate-ISO-Android","content":"养橙","page":"http://www.homeszone.cn"};
    NSString *contentStr = [userInfo objectForKey:@"content"];
    if (contentStr.length > 0) {
        NSDictionary *contentDic = [contentStr mj_JSONObject];
        if (contentDic) {
            PushModel *push = [PushModel mj_objectWithKeyValues:contentDic];
            if (push) {
                [[PushManager manager] addPushModel:push];
            }
        }
    }
    [[PushManager manager] handelPushFromBackGround:(application.applicationState != UIApplicationStateActive)];
    
    completionHandler(UIBackgroundFetchResultNewData);
}

#pragma mark - 前后台切换
- (void)applicationWillResignActive:(UIApplication *)application
{

}

- (void)applicationDidEnterBackground:(UIApplication *)application
{

}

- (void)applicationWillEnterForeground:(UIApplication *)application
{

}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    application.applicationIconBadgeNumber = 0;
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    
}

#pragma mark - 注册用户通知
/** 注册用户通知 */
- (void)registerUserNotification {
    
    /*
     注册通知(推送)
     申请App需要接受来自服务商提供推送消息
     */    
    
    // 判读系统版本是否是“iOS 8.0”以上
    if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 8.0 ||
        [UIApplication instancesRespondToSelector:@selector(registerUserNotificationSettings:)]) {
        
        // 定义用户通知类型(Remote.远程 - Badge.标记 Alert.提示 Sound.声音)
        UIUserNotificationType types = UIUserNotificationTypeAlert | UIUserNotificationTypeBadge | UIUserNotificationTypeSound;
        
        // 定义用户通知设置
        UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:types categories:nil];
        
        // 注册用户通知 - 根据用户通知设置
        [[UIApplication sharedApplication] registerUserNotificationSettings:settings];
        [[UIApplication sharedApplication] registerForRemoteNotifications];
    }
//    else { // iOS8.0 以前远程推送设置方式
//        // 定义远程通知类型(Remote.远程 - Badge.标记 Alert.提示 Sound.声音)
//        UIRemoteNotificationType myTypes = UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeAlert | UIRemoteNotificationTypeSound;
//        
//        // 注册远程通知 -根据远程通知类型
//        [[UIApplication sharedApplication] registerForRemoteNotificationTypes:myTypes];
//    }
}


#pragma mark - GeTuiSdkDelegate

/** SDK启动成功返回cid */
- (void)GeTuiSdkDidRegisterClient:(NSString *)clientId {
    // [4-EXT-1]: 个推SDK已注册，返回clientId
    NSLog(@"\n>>>[GeTuiSdk RegisterClient]:%@\n\n", clientId);
    
    //根据clientid从服务器端获取推送别名
//    [[TaskService service] getAliasWithClientId:clientId complete:^(id response) {
//        
//    } failure:^(NSError *error) {
//        
//    }];
    
    [self bindAlias];
}

/** SDK遇到错误回调 */
- (void)GeTuiSdkDidOccurError:(NSError *)error {
    // [EXT]:个推错误报告，集成步骤发生的任何错误都在这里通知，如果集成后，无法正常收到消息，查看这里的通知。
    NSLog(@"\n>>>[GexinSdk error]:%@\n\n", [error localizedDescription]);
}

/** SDK收到透传消息回调 */
- (void)GeTuiSdkDidReceivePayloadData:(NSData *)payloadData andTaskId:(NSString *)taskId andMsgId:(NSString *)msgId andOffLine:(BOOL)offLine fromGtAppId:(NSString *)appId {
    
    if (offLine) {
        return;
    }
    // [4]: 收到个推消息 {"for_type":"openPage","title":"33-使用别名对单个用户进行推送-TransmissionTemplate-ISO-Android","content":"养橙","page":"http://www.homeszone.cn"}
    NSString *payloadMsg = nil;
    if (payloadData) {
        payloadMsg = [[NSString alloc] initWithBytes:payloadData.bytes length:payloadData.length encoding:NSUTF8StringEncoding];
    }
    if (payloadMsg) {
        NSDictionary *contentDic = [payloadMsg mj_JSONObject];
        if (contentDic) {
            PushModel *push = [PushModel mj_objectWithKeyValues:contentDic];
            if (push) {
                [[PushManager manager] addPushModel:push];
            }
        }
    }
//    if([[UIApplication sharedApplication] applicationState] == UIApplicationStateActive) {
//        [[PushManager manager] handelPushFromBackGround: YES];
//    }
    [[PushManager manager] handelPushFromBackGround:([[UIApplication sharedApplication] applicationState]  != UIApplicationStateActive)];

//    [[PushManager manager] handelPushFromBackGround:(application.applicationState != UIApplicationStateActive)];
    
    NSString *msg = [NSString stringWithFormat:@"taskId=%@,messageId:%@,payloadMsg:%@%@", taskId, msgId, payloadMsg, offLine ? @"<离线消息>" : @""];
    NSLog(@"\n>>>[GexinSdk ReceivePayload]:%@\n\n", msg);
}

///** SDK收到sendMessage消息回调 */
//- (void)GeTuiSdkDidSendMessage:(NSString *)messageId result:(int)result {
//    // [4-EXT]:发送上行消息结果反馈
//    NSString *msg = [NSString stringWithFormat:@"sendmessage=%@,result=%d", messageId, result];
//    NSLog(@"\n>>>[GexinSdk DidSendMessage]:%@\n\n", msg);
//}
//
///** SDK运行状态通知 */
//- (void)GeTuiSDkDidNotifySdkState:(SdkStatus)aStatus {
//    // [EXT]:通知SDK运行状态
//    NSLog(@"\n>>>[GexinSdk SdkState]:%u\n\n", aStatus);
//}
//
///** SDK设置推送模式回调 */
//- (void)GeTuiSdkDidSetPushMode:(BOOL)isModeOff error:(NSError *)error {
//    if (error) {
//        NSLog(@"\n>>>[GexinSdk SetModeOff Error]:%@\n\n", [error localizedDescription]);
//        if (self.oprateReuslt) {
//            self.oprateReuslt(NO);
//        }
//        return;
//    }
//    if (self.oprateReuslt) {
//        [self setLocalIsPushOn:!isModeOff];
//        _isPushOn = !isModeOff;
//        self.oprateReuslt(YES);
//    }
//    NSLog(@"\n>>>[GexinSdk SetModeOff]:%@\n\n", isModeOff ? @"开启" : @"关闭");
//}


@end
