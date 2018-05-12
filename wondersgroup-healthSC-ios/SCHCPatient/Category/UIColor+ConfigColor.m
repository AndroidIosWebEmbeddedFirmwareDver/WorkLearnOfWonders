//
//  UIColor+ConfigColor.m
//  VaccinePatient
//
//  Created by Jam on 16/5/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "UIColor+ConfigColor.h"


static const char *colorNameDB = ","
"main1#ffc239,main2#fcc12b,main3#ff6161,main4#ff2711,"
"bc0#000000,bc1#ffffff,bc2#f6f6f6,bc3#e1e1e1,bc4#e1e1e1,bc5#f6f6f6,bc6#ffffff,bc7#2e7af0,bc8#2461c0,bc9#ff8c8a,bc10#f6f9fd,bc11#cccccc"
"dc1#dddddd,dc2#eeeeee,dc3#ffc239,dc4#f1f1f1,"
"tc0#ffffff,tc1#333333,tc2#666666,tc3#9b9b9b,tc4#bbbbbb,tc5#2e7af0,tc6#dddddd,tc7#cccccc,tc8#f5a623,tc9#3ad8b5,tc10#5bc3ff,tc11#ff97cd,tc12#ff4628,tc13#50d2e9,tc14#e6630b,"
"stc1#ffa217,stc2#fa3e34,stc3#009711,stc4#8ed308,"
"sbc1#ffa217,sbc2#fa3e34,sbc3#009711,sbc4#8ed408,sbc5#ff665e,sbc6#1194ff,sbc7#ff665e#0.5"
"btn1#3ad8b5,btn2#2dceaa,btn3#2dceaa#0.5,btn4#63eccd,btn5#4cdebd,btn6#ffffff,btn7#fccf5d,btn8#ff8787,btn9#4dd796,btn10#999999,btn9527#fcc12b#0.5,btn11#3ad8b5#0.3,"
"gard1#333333#0.5,gard2#333333#0.0,"
;


@implementation UIColor (ConfigColor)


+ (UIColor *)searchForColorByName:(NSString *)cssColorName {
    UIColor *result = nil;
    const char *searchString = [[NSString stringWithFormat:@",%@#", cssColorName] UTF8String];
    const char *found;
    found = strstr(colorNameDB, searchString);
    if (found) {
        NSString * searchSubString = [NSString stringWithUTF8String:found];
        NSArray *splitString = [searchSubString componentsSeparatedByString:@","];
        NSString *colorAlphaString = splitString[1];
        NSArray *colorAlphaAry = [colorAlphaString componentsSeparatedByString:@"#"];
        NSString *color = colorAlphaAry[1];
        const char *after = [color UTF8String];
        int hex;
        if (sscanf(after, "%x", &hex) == 1) {
            result = [self colorWithHex:hex];
        }
        NSString *alpha = nil;
        if (colorAlphaAry.count>2) {
            alpha = colorAlphaAry[2];
            result = [self colorWithHex:hex alpha:[alpha floatValue]];
        }
    }
    return result;
}


+ (UIColor *)randomColor {
    return [UIColor colorWithRed:(CGFloat)RAND_MAX / random()
                           green:(CGFloat)RAND_MAX / random()
                            blue:(CGFloat)RAND_MAX / random()
                           alpha:1.0f];
}


#pragma mark - color

+ (UIColor *)main1Color{
    return [self searchForColorByName:@"main1"];
}
//+ (UIColor *)main2Color{
//    return [self searchForColorByName:@"main2"];
//}
//+ (UIColor *)main3Color{
//    return [self searchForColorByName:@"main3"];
//}
//+ (UIColor *)main4Color{
//    return [self searchForColorByName:@"main4"];
//}


#pragma mark - 

+ (UIColor *)bc0Color{
    return [self searchForColorByName:@"bc0"];
}
+ (UIColor *)bc1Color{
    return [self searchForColorByName:@"bc1"];
}
+ (UIColor *)bc2Color{
    return [self searchForColorByName:@"bc2"];
}
+ (UIColor *)bc3Color{
    return [self searchForColorByName:@"bc3"];
}
+ (UIColor *)bc4Color{
    return [self searchForColorByName:@"bc4"];
}
+ (UIColor *)bc5Color{
    return [self searchForColorByName:@"bc5"];
}
+ (UIColor *)bc6Color{
    return [self searchForColorByName:@"bc6"];
}
+ (UIColor *)bc7Color{
    return [self searchForColorByName:@"bc7"];
}
+ (UIColor *)bc8Color{
    return [self searchForColorByName:@"bc8"];
}
+ (UIColor *)bc9Color{
    return [self searchForColorByName:@"bc9"];
}
+ (UIColor *)bc10Color{
    return [self searchForColorByName:@"bc10"];
}
+ (UIColor *)bc11Color{
    return [self searchForColorByName:@"bc11"];
}


#pragma mark - 

+ (UIColor *)dc1Color{
    return [self searchForColorByName:@"bc3"];
}
+ (UIColor *)dc2Color{
    return [self searchForColorByName:@"dc2"];
}
+ (UIColor *)dc3Color{
    return [self searchForColorByName:@"dc3"];
}
+ (UIColor *)dc4Color{
    return [self searchForColorByName:@"dc4"];
}


#pragma mark - 

+ (UIColor *)stc1Color {
    return [self searchForColorByName:@"stc1"];
}
+ (UIColor *)stc2Color {
    return [self searchForColorByName:@"stc2"];
}
+ (UIColor *)stc3Color {
    return [self searchForColorByName:@"stc3"];
}
+ (UIColor *)stc4Color {
    return [self searchForColorByName:@"stc4"];
}


#pragma mark -

+ (UIColor *)sbc1Color {
    return [self searchForColorByName:@"sbc1"];
}
+ (UIColor *)sbc2Color {
    return [self searchForColorByName:@"sbc2"];
}
+ (UIColor *)sbc3Color {
    return [self searchForColorByName:@"sbc3"];
}
+ (UIColor *)sbc4Color {
    return [self searchForColorByName:@"sbc4"];
}
+ (UIColor *)sbc5Color {
    return [self searchForColorByName:@"sbc5"];
}
+ (UIColor *)sbc6Color {
    return [self searchForColorByName:@"sbc6"];
}
+ (UIColor *)sbc7Color {
    return [self searchForColorByName:@"sbc7"];
}



#pragma mark - 
+ (UIColor *)gard1Color {
    return [self searchForColorByName:@"gard1"];
}

+ (UIColor *)gard2Color {
    return [self searchForColorByName:@"gard2"];
}


#pragma mark -

+ (UIColor *)tc0Color{
    return [self searchForColorByName:@"tc0"];
}
+ (UIColor *)tc1Color{
    return [self searchForColorByName:@"tc1"];
}
+ (UIColor *)tc2Color{
    return [self searchForColorByName:@"tc2"];
}
+ (UIColor *)tc3Color{
    return [self searchForColorByName:@"tc3"];
}
+ (UIColor *)tc4Color{
    return [self searchForColorByName:@"tc4"];
}
+ (UIColor *)tc5Color{
    return [self searchForColorByName:@"tc5"];
}
+ (UIColor *)tc6Color{
    return [self searchForColorByName:@"tc6"];
}
+ (UIColor *)tc7Color{
    return [self searchForColorByName:@"tc7"];
}
+ (UIColor *)tc8Color{
    return [self searchForColorByName:@"tc8"];
}
+ (UIColor *)tc9Color{
    return [self searchForColorByName:@"tc9"];
}
+ (UIColor *)tc10Color{
    return [self searchForColorByName:@"tc10"];
}
+ (UIColor *)tc11Color{
    return [self searchForColorByName:@"tc11"];
}
+ (UIColor *)tc12Color{
    return [self searchForColorByName:@"tc12"];
}
+ (UIColor *)tc13Color{
    return [self searchForColorByName:@"tc13"];
}
//+ (UIColor *)tc14Color{
//    return [self searchForColorByName:@"tc14"];
//}


#pragma mark - 

+ (UIColor *)btn1Color{
    return [self searchForColorByName:@"btn1"];
}
+ (UIColor *)btn2Color{
    return [self searchForColorByName:@"btn2"];
}
+ (UIColor *)btn3Color{
    return [self searchForColorByName:@"btn3"];
}
+ (UIColor *)btn4Color{
    return [self searchForColorByName:@"btn4"];
}
+ (UIColor *)btn5Color{
    return [self searchForColorByName:@"btn5"];
}
+ (UIColor *)btn6Color{
    return [self searchForColorByName:@"btn6"];
}
//+ (UIColor *)btn7Color{
//    return [self searchForColorByName:@"btn7"];
//}
//+ (UIColor *)btn8Color{
//    return [self searchForColorByName:@"btn8"];
//}
//
//+ (UIColor *)ic9Color{
//    return [self searchForColorByName:@"ic9"];
//}
//+ (UIColor *)ic10Color{
//    return [self searchForColorByName:@"ic10"];
//}
//+ (UIColor *)ic9527Color {
//    return [self searchForColorByName:@"ic9527"];
//}

+ (UIColor *)btn11Color{
    return [self searchForColorByName:@"btn11"];
}


#pragma mark - 颜色工具

+ (UIColor *) colorWithHexString:(NSString*)hex{
    //删除字符串中的空格
    NSString *cString = [[hex stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] uppercaseString];
    // String should be 6 or 8 characters
    if ([cString length] < 6)
    {
        return [UIColor clearColor];
    }
    // strip 0X if it appears
    //如果是0x开头的，那么截取字符串，字符串从索引为2的位置开始，一直到末尾
    if ([cString hasPrefix:@"0X"])
    {
        cString = [cString substringFromIndex:2];
    }
    //如果是#开头的，那么截取字符串，字符串从索引为1的位置开始，一直到末尾
    if ([cString hasPrefix:@"#"])
    {
        cString = [cString substringFromIndex:1];
    }
    if ([cString length] != 6)
    {
        return [UIColor clearColor];
    }
    
    // Separate into r, g, b substrings
    NSRange range;
    range.location = 0;
    range.length = 2;
    //r
    NSString *rString = [cString substringWithRange:range];
    //g
    range.location = 2;
    NSString *gString = [cString substringWithRange:range];
    //b
    range.location = 4;
    NSString *bString = [cString substringWithRange:range];
    
    // Scan values
    unsigned int r, g, b;
    [[NSScanner scannerWithString:rString] scanHexInt:&r];
    [[NSScanner scannerWithString:gString] scanHexInt:&g];
    [[NSScanner scannerWithString:bString] scanHexInt:&b];
    return [UIColor colorWithRed:((float)r / 255.0f) green:((float)g / 255.0f) blue:((float)b / 255.0f) alpha:1.0];
}

+ (UIColor *) colorWithHex:(uint) hex{
    return [self colorWithHex:hex alpha:1.0f];
}

//UIColor *blueColor = [UIColor colorWithHex:0x0174AC alpha:1];
+ (UIColor *) colorWithHex:(uint) hex alpha:(CGFloat)alpha
{
    NSInteger red, green, blue;
    
    blue = hex & 0x0000FF;
    green = ((hex & 0x00FF00) >> 8);
    red = ((hex & 0xFF0000) >> 16);
    
    return [UIColor colorWithRed:red/255.0f green:green/255.0f blue:blue/255.0f alpha:alpha];
}

+ (UIColor *) colorWithColor:(UIColor *)color alpha:(CGFloat)alpha
{
    return [UIColor colorWithRed:color.red green:color.green blue:color.blue alpha:alpha];
}

@end
