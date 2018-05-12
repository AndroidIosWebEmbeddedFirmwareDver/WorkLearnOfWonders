//
//  UIColor+ConfigColor.h
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>


#define RGB_COLOR(r, g, b)      [UIColor colorWithRed:r/255.0 green:g/255.0 blue:b/255.0 alpha: 1.0]
#define RGBA_COLOR(r, g, b, a)  [UIColor colorWithRed:r/255.0 green:g/255.0 blue:b/255.0 alpha:a]
#define HEX_COLOR(h)            [UIColor colorWithHex:h alpha:1.0]
#define HEXA_COLOR(h, a)        [UIColor colorWithHex:h alpha:a]
// rgb颜色转换（16进制->10进制）
#define UIColorByRGB(rgbValue)  [UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 green:((float)((rgbValue & 0xFF00) >> 8))/255.0 blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]



@interface UIColor (ConfigColor)

#pragma mark - 颜色管理

#pragma mark -
+ (UIColor *)main1Color;
//+ (UIColor *)main2Color;
//+ (UIColor *)main3Color;
//+ (UIColor *)main4Color;


#pragma mark - 

+ (UIColor *)bc0Color;
+ (UIColor *)bc1Color;
+ (UIColor *)bc2Color;
+ (UIColor *)bc3Color;
+ (UIColor *)bc4Color;
+ (UIColor *)bc5Color;
+ (UIColor *)bc6Color;
+ (UIColor *)bc7Color;
+ (UIColor *)bc8Color;
+ (UIColor *)bc9Color;
+ (UIColor *)bc10Color;
+ (UIColor *)bc11Color;
+ (UIColor *)bc12Color;


#pragma mark - 

+ (UIColor *)dc1Color;
+ (UIColor *)dc2Color;
+ (UIColor *)dc3Color;
+ (UIColor *)dc4Color;


#pragma mark - 

+ (UIColor *)stc1Color;
+ (UIColor *)stc2Color;
+ (UIColor *)stc3Color;
+ (UIColor *)stc4Color;


#pragma mark - 

+ (UIColor *)sbc1Color;
+ (UIColor *)sbc2Color;
+ (UIColor *)sbc3Color;
+ (UIColor *)sbc4Color;
+ (UIColor *)sbc5Color;
+ (UIColor *)sbc6Color;

#pragma mark -

+ (UIColor *)tc0Color;
+ (UIColor *)tc1Color;
+ (UIColor *)tc2Color;
+ (UIColor *)tc3Color;
+ (UIColor *)tc4Color;
+ (UIColor *)tc5Color;
+ (UIColor *)tc6Color;

#pragma mark -

+ (UIColor *)searchForColorByName:(NSString *)cssColorName;
+ (UIColor *)randomColor;


#pragma mark - 颜色工具
+ (UIColor *) colorWithHexString:(NSString*) hex;
+ (UIColor *) colorWithHex:(uint) hex;
+ (UIColor *) colorWithHex:(uint) hex alpha:(CGFloat)alpha;
+ (UIColor *) colorWithColor:(UIColor *)color alpha:(CGFloat)alpha;

@end
