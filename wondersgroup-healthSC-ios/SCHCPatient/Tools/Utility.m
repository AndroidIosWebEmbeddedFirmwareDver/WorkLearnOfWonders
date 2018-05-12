//
//  Utility.m
//  haowan
//
//  Created by wupeijing on 3/12/15.
//  Copyright (c) 2015 iyaya. All rights reserved.
//

#import "Utility.h"
#import <sys/sysctl.h>
#import <sys/utsname.h>
#import <CommonCrypto/CommonHMAC.h>

@implementation Utility

#pragma mark - UI类
#pragma mark   根据颜色生成图片
+ (UIImage *)createImageFromColor:(UIColor *)color size:(CGSize)size {
    
    CGRect rect = CGRectMake(0, 0, size.width, size.height);
    
    CGFloat alpha = 0;
    [color getWhite:nil alpha:&alpha];
    UIGraphicsBeginImageContextWithOptions(rect.size, alpha > 0.99, 0);
    
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetFillColorWithColor(context, color.CGColor);
    CGContextFillRect(context, rect);
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return image;
}


#pragma mark - 系统缓存类
#pragma mark   获设备取缓存
+ (NSString *)getDeviceCache {
    NSUInteger catch = [[SDImageCache sharedImageCache] getSize];
    float catchFloat = catch/1024.0/1024.0;
    NSString *catchSize = @"0K";
    if (catchFloat >= 1) {
        //超过1M的显示方式
        catchSize = [NSString stringWithFormat: @"%.1fM", catchFloat];
    }
    else if (catchFloat == 0 || catchFloat < 0.1) {
        //小于0.1K的显示方式
        catchSize = [NSString stringWithFormat: @"0K"];
    }
    else {
        //大于0.1K并且小于1M的显示方式
        catchSize = [NSString stringWithFormat: @"%.1fK", catchFloat];
    }
    return catchSize;
}

#pragma mark - 清理设备缓存
+ (void)clearDeviceCache:(void (^)(void))complete {
    [[SDImageCache sharedImageCache] clearMemory];
    [[SDImageCache sharedImageCache] clearDiskOnCompletion:^{
        complete();
    }];
}

#pragma mark - 设备信息类
#pragma mark   设备型号判断(从4S-6SP)
//获取设备型号
NSString* platform() {
    //    另一种方法，需验证
    //    struct utsname systemInfo;
    //    uname(&systemInfo);
    //    NSString *platform = [NSString stringWithCString:systemInfo.machine encoding:NSUTF8StringEncoding];
    
    int mib[2];
    size_t len;
    char *machine;
    
    mib[0] = CTL_HW;
    mib[1] = HW_MACHINE;
    sysctl(mib, 2, NULL, &len, NULL, 0);
    machine = malloc(len);
    sysctl(mib, 2, machine, &len, NULL, 0);
    
    NSString *platform = [NSString stringWithCString:machine encoding:NSASCIIStringEncoding];
    free(machine);
    
    return platform;
}

BOOL isiPhone4S() {
    //  iPhone 4S (A1387/A1431)
    NSString *model = platform();
    if ([model isEqualToString: @"iPhone4,1"]) {
        return YES;
    }
    return NO;
}

BOOL isiPhone5() {
    //  iPhone 5 (A1428)
    //  iPhone 5 (A1429/A1442)
    NSString *model = platform();
    if ([model isEqualToString: @"iPhone5,1"] || [model isEqualToString: @"iPhone5,2"]) {
        return YES;
    }
    return NO;
}

BOOL isiPhone5C() {
    //  iPhone 5c (A1456/A1532)
    //  iPhone 5c (A1507/A1516/A1526/A1529)
    NSString *model = platform();
    if ([model isEqualToString: @"iPhone5,3"] || [model isEqualToString: @"iPhone5,4"]) {
        return YES;
    }
    return NO;
}


BOOL isiPhone5S() {
    //  iPhone 5s (A1453/A1533)
    //  iPhone 5s (A1457/A1518/A1528/A1530)
    NSString *model = platform();
    if ([model isEqualToString: @"iPhone6,1"] || [model isEqualToString: @"iPhone6,2"]) {
        return YES;
    }
    return NO;
}


BOOL isiPhone6P() {
    //  iPhone 6 Plus (A1522/A1524)
    NSString *model = platform();
    if ([model isEqualToString: @"iPhone7,1"]) {
        return YES;
    }
    return NO;
}

BOOL isiPhone6() {
    //  iPhone 6 (A1549/A1586)
    NSString *model = platform();
    if ([model isEqualToString: @"iPhone7,2"]) {
        return YES;
    }
    return NO;
}


BOOL isiPhone6S() {
    
    return NO;
}

BOOL isiPhone6SP() {
    
    return NO;
}


#pragma mark - 其他工具类
#pragma mark   生成UUID
+ (NSString *) gen_UUID {
    CFUUIDRef puuid = CFUUIDCreate( nil );
    CFStringRef uuidString = CFUUIDCreateString( nil, puuid );
    NSString * result = (NSString *)CFBridgingRelease(CFStringCreateCopy( NULL, uuidString));
    CFRelease(puuid);
    CFRelease(uuidString);
    return result;
}

#pragma mark   生成 Attributes
+ (NSDictionary *)attributesForFont:(UIFont *)font andAlignment:(NSTextAlignment)aligment {
    NSMutableParagraphStyle *style = [[NSMutableParagraphStyle alloc] init];
    [style setLineSpacing: 2.0];
    [style setLineBreakMode: NSLineBreakByWordWrapping];
    [style setAlignment: aligment];
    return  @{NSFontAttributeName : font, NSForegroundColorAttributeName : [UIColor blackColor], NSParagraphStyleAttributeName : style};
}

+(NSDictionary *)attributesForFont:(UIFont *)font
                   withLineSpacing:(CGFloat)lineSpacing{
    
    NSMutableDictionary *attributes = [NSMutableDictionary dictionary];
    if (font) [attributes setObject:font forKey:NSFontAttributeName];

    if (lineSpacing) {
        NSMutableParagraphStyle *style = [[NSMutableParagraphStyle alloc] init];
        [style setLineSpacing: lineSpacing];
        [style setLineBreakMode: NSLineBreakByCharWrapping | NSLineBreakByWordWrapping];
        [attributes setObject:style forKey:NSParagraphStyleAttributeName];
    }
    
    return attributes;
}

+(CGSize)heightWithAttributesWithFont:(UIFont *)font
                             withText:(NSString *)text
                            withWidth:(CGFloat)width
                           withHeight:(CGFloat)height{
    if (!text.length) {
        return CGSizeZero;
    }
    
    NSDictionary *attributes = [Utility attributesForFont:font withLineSpacing:0];
    CGSize sizeToFit = [text boundingRectWithSize: CGSizeMake(width, height)
                                          options: NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading
                                       attributes: attributes
                                          context:nil].size;
    
    return sizeToFit;
}


#pragma mark - 隐藏手机号
+ (NSString *)hiddenMobile:(NSString *)mobile {
    if (![RegexKit validateMobile: mobile]) {
        return mobile;
    }
    return [mobile stringByReplacingCharactersInRange: NSMakeRange(3, 4) withString: @"****"];
}

+(void)delayInSeconds:(double)sec withAction:(void(^)(void))action {
    double delayInSeconds = sec;
    dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delayInSeconds * NSEC_PER_SEC));
    dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
        action();
    });
}


@end
