//
//  UIView+Layer.m
//  VaccinePatient
//
//  Created by ZJW on 16/5/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UIView+Layer.h"

@implementation UIView (Layer)

-(void)setCornerRadius:(CGFloat)radius {
    self.layer.cornerRadius = radius;
    self.layer.masksToBounds = YES;
}

-(void)setborderWithColor:(UIColor *)color withWidth:(CGFloat)width{
    self.layer.borderColor = color.CGColor;
    self.layer.borderWidth = width;
}


@end
