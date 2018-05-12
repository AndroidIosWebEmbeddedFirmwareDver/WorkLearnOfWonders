//
//  VCManager.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SplashView.h"
#import "UpdateInfoModel.h"

@interface VCManager : NSObject
+ (instancetype)manager;

- (void)showLaunch: (void (^) (SplashView *splashView))complete;

- (void)loadRootViewController;

//弹出登录页面
//- (void)showLoginViewController:(BOOL)animation;
//模态弹出登录页面
- (void)presentLoginViewController:(BOOL)animation;

//弹出首页
- (void)showHomeViewController:(BOOL)animation;

//Tabbar根据指定下标跳转
- (void)showTabbarControllerAtIndex:(NSInteger)index;


- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;


- (void)showADView:(NSString *)url;

//弹出升级页面
- (void)showUpdateView:(UpdateInfoModel *)updateInfo;


@end
