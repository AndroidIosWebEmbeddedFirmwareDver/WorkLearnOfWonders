//
//  UIView+BorderColor.h
//  SCHCPatient
//
//  Created by Joseph Gao on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (BorderColor)

-(void)addTopBorderWithColor:(UIColor *)color borderWidth:(CGFloat)borderWidth;
-(void)addLeftBorderWithColor:(UIColor *)color borderWidth:(CGFloat) borderWidth;
-(void)addBottomBorderWithColor:(UIColor *)color borderWidth:(CGFloat)borderWidth;
-(void)addRightBorderWithColor:(UIColor *)color borderWidth:(CGFloat)borderWidth;

@end
