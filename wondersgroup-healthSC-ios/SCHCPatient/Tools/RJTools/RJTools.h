//
//  RJTools.h
//  CommonFrameDemo
//
//  Created by Po on 2017/4/11.
//  Copyright © 2017年 Po. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MJRefresh.h"
@interface RJTools : NSObject


//MJRefresh helper
+ (MJRefreshStateHeader *)getRefreshHeader:(void(^)(void))headerBlock;
+ (MJRefreshBackStateFooter *)getRefreshFooter:(void(^)(void))footerBlock;

// common weidge
+ (UIView *)getBackButtonWithEvent:(void(^)(UIButton * button))eventBlock;

@end
