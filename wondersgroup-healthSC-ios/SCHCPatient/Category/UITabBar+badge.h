//
//  UITabBar+badge.h
//  HCPatient
//
//  Created by Jam on 15/7/22.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UITabBar (badge)

- (void)showBadgeOnItemIndex:(int)index;
- (void)hideBadgeOnItemIndex:(int)index;

@end
