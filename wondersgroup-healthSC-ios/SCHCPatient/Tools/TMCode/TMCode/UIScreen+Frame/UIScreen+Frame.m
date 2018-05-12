//
//  UIScreen+Frame.m
//  VaccinePatient
//
//  Created by maorenchao on 16/5/29.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UIScreen+Frame.h"

@implementation UIScreen(UIScreen_Frame)
+ (CGFloat)width
{
    return [UIScreen mainScreen].bounds.size.width;
}
+ (CGFloat)height
{
    return [UIScreen mainScreen].bounds.size.height;
}

@end
