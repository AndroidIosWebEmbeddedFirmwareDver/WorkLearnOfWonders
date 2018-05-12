//
//  UIView+Layer.h
//  VaccinePatient
//
//  Created by ZJW on 16/5/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (Layer)

/**
 *  设置圆角
 *
 *  @param radius 圆角度数
 */
-(void)setCornerRadius:(CGFloat)radius;

/**
 *  设置边框
 *
 *  @param color 边框颜色
 *  @param width 边框线粗细
 */
-(void)setborderWithColor:(UIColor *)color withWidth:(CGFloat)width;

@end
