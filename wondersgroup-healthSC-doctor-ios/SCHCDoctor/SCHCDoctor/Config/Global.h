//
//  Global.h
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SplashView.h"
#import "MJRefresh.h"
#import "WDRefreshHeader.h"

@interface Global : NSObject
<UITabBarControllerDelegate>

@property (nonatomic ,strong) NSURL *BASE_URL;
//@property (nonatomic ,strong) NSURL *QINIU_URL;


/**
 *  网络判断是否有网
 */
@property (nonatomic, assign)BOOL networkReachable;
@property (nonatomic, assign)BOOL hasNewmessage;

@property (nonatomic, copy) NSString *deviceUUID;

@property (nonatomic,assign)BOOL needUpdateVerson;

/**
 *  WDGlobal 单例
 *
 */
+ (instancetype)global;

/**
 * APP启动Global所需
 *
 */
- (void)startGlobal;


/**
 *  服务器选择
 *
 */
- (void)selectServer;


/**
 *  加顶部线
 *
 *  @param view  view
 *  @param color color
 */
-(void)addBottomLineInView:(UIView*)view withColor:(UIColor *)color ;


/**
 *  加顶部线
 *
 *  @param view  view
 *  @param color color
 */
-(void)addTopLineInView:(UIView*)view withColor:(UIColor *)color;

@end
