//
//  UIUtility.h
//  VaccinePatient
//
//  Created by maorenchao on 16/6/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UIUtility : NSObject
/**
 *  生成GIF Header
 */
+ (MJRefreshGifHeader *)headerRefreshTarget:(id)target action:(SEL)action;

/**
 *  生成 加载更多的Footer
 */
+ (MJRefreshAutoNormalFooter *)footerMoreTarget:(id)target action:(SEL)action;
@end
