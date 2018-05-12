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

//弹出首页
- (void)showLoginViewController:(BOOL)animation;
- (void)showHomeViewController:(BOOL)animation;


- (void)showTabbarControllerAtIndex:(NSInteger)index;


- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;


- (void)showADView:(NSString *)url;

//弹出升级页面
- (void)showUpdateView:(UpdateInfoModel *)updateInfo;


@end
