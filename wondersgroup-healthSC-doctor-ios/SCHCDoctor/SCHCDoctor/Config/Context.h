//
//  Context.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
@interface Context : NSObject

@property(nonatomic ,assign) BOOL  adcomplete;

@property(nonatomic , assign) BOOL relayislogin;

@property (nonatomic, strong) NSString *time_diff;      //接口访问时间差


/**
 *  WDContext 单例
 *
 */
+ (instancetype)context;

/**
 *  是否是第一次登录APP
 *
 */
- (BOOL)isFirstLogin;

/**
 *  导航页（介绍页）相关
 *
 *  needIntroduction    导航、介绍页面是否需要显示
 *  coverImages         导航页图片数组
 *  coverBgImages       导航页背景图片数组
 */
- (BOOL)needIntroduction;
- (NSArray *)coverImages;
- (NSArray *)coverBgImages;

/**
 *  广告页相关
 *
 *  needADView          广告页是否需要显示//服务器控制
 *  ADViewDelay         广告页需显示的时长
 *  adsShow             是否显示广告 //本地控制图片是否加载完成
 *  isShowJumpButton    是否显示跳出按钮
 *  saveAds             保存广告图
 *  removeAds           去除广告显示
 *  getAds              获取广告图片地址
 */
- (BOOL)needADView;
- (CGFloat)ADViewDelay;
- (BOOL) adsShow;
- (BOOL) isShowJumpButton;
- (void) saveAds:(NSString *)adUrl;
- (void) removeAds;
- (NSString *)getAds;


@end
