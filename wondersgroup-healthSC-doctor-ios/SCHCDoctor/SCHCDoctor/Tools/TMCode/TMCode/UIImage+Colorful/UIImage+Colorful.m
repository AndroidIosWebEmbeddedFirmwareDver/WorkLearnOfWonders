//
//  UIImage+Colorful.m
//  HCPatient
//
//  Created by 寻梦者 on 15/5/14.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import "UIImage+Colorful.h"

@implementation UIImage (Colorful)
+ (UIImage *)imageWithColor:(UIColor *)color {
    CGRect rect = CGRectMake(0.0f, 0.0f, 1.0f, 1.0f);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    
    CGContextSetFillColorWithColor(context, [color CGColor]);
    CGContextFillRect(context, rect);
    
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return image;
}
@end
