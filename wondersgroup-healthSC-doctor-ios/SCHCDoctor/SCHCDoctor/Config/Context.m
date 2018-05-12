//
//  Context.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "Context.h"
#import "UserDefaults.h"
#import "DBManager.h"
//#import "ManagerAreaDataDao.h"
#define ProductVersionKey     @"ProductVersionKey"
#define FirstLoginKey         @"FirstLoginKey"

@interface Context() {
    
    UserDefaults *defaults;
}


@end


@implementation Context

#pragma mark - WDContext单例
+ (instancetype)context {
    static Context *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[Context alloc] init];
    });
    return instance;
}

#pragma mark - 初始化
- (instancetype)init {
    self = [super init];
    if (self) {
        self.time_diff = nil;
        defaults = [UserDefaults shareDefaults];
    }
    return self;
}

#pragma mark - 是否是第一次登录APP

- (BOOL)isFirstLogin {
    NSString *firstLoginKey = [defaults objectForKey: FirstLoginKey];
    if (!firstLoginKey) {
        [defaults setObject: @"FirstLogin" forKey: FirstLoginKey];
        return YES;
    }
    return NO;
}


#pragma mark - 导航页（介绍页）相关
#pragma mark -

#pragma mark   导航、介绍页面是否需要显示
- (BOOL)needIntroduction {
    NSString *productVersion = [defaults objectForKey: ProductVersionKey];
    if (!productVersion || ![productVersion isEqualToString: APP_VERSION]) {
        [defaults setObject: APP_VERSION forKey: ProductVersionKey];
        return YES;
    }
    return NO;
}

#pragma mark   导航页图片数组
- (NSArray *)coverImages {
    return @[@"introduce1", @"introduce2", @"introduce3"];
}
#pragma mark   导航页背景图片数组
- (NSArray *)coverBgImages {
    return @[@"", @"", @""];
}

#pragma mark - 广告页相关
#pragma mark -

#pragma mark   广告页是否需要显示
- (BOOL)needADView {
    return [[TaskManager manager].appConfig.advertisement.display boolValue] && [[SDImageCache  sharedImageCache] imageFromDiskCacheForKey: [[Context context] getAds]];
}
#pragma mark   广告页需显示的时长
- (CGFloat)ADViewDelay {
    return [[TaskManager manager].appConfig.advertisement.duration floatValue]/1000.0;
}

#pragma mark 判断广告开关显示机会
-(BOOL) adsShow
{
    NSString * adcomplete = [defaults objectForKey:@"adsImageURL"];
    
    if([adcomplete isEqualToString:@""] || adcomplete==nil || adcomplete.length==0)
    {
        return NO;
    }
    return YES;
}
#pragma mark - 是否有跳过按钮
- (BOOL) isShowJumpButton
{
    return  [[TaskManager manager].appConfig.advertisement.skip boolValue];
}

- (void) saveAds:(NSString *)adUrl {
    [defaults setObject: adUrl forKey: @"adsImageURL"];
}

#pragma mark - 去除广告显示
- (void)removeAds {
    [defaults removeObjectForKey:@"adsImageURL"];
}
#pragma mark - 获取广告图片地址
- (NSString *)getAds {
    return [defaults objectForKey: @"adsImageURL"];
}

@end
