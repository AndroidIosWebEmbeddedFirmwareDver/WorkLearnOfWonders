//
//  Utility.h
//  haowan
//
//  Created by wupeijing on 3/12/15.
//  Copyright (c) 2015 iyaya. All rights reserved.
//

//--------------------------------------------------------------
/*  Utility
 *
 *  工具类
 *  添加时请按大类进行添加，如无大类，请放入其他或者新建一个有必要添加的大类
 *  自觉添加注释，方便以后维护！！！
 */
//--------------------------------------------------------------


#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonDigest.h>



@interface Utility : NSObject

#pragma mark - UI类
/**
 *  根据颜色生成图片
 *
 *  @param  color : 需要创建的颜色
 *  @param  size  : 需要创建图片的尺寸
 */
+ (UIImage *)createImageFromColor:(UIColor *)color size:(CGSize)size;


#pragma mark - 系统缓存类
/**
 *  获设备取缓存
 *
 *  @param  返回的Cache大小的NSString
 */
+ (NSString *)getDeviceCache;

/**
 *  清理设备缓存
 *
 *  @param  complete : 清除缓存后的回调
 */
+ (void)clearDeviceCache:(void (^)(void))complete;

#pragma mark - 设备信息类

/**
 *  设备型号判断
 *
 *  用C++方法，方便使用 (只支持4S以及4S+的设备，如需判断以下设备，请让作者加入)
 *  返回类型: BOOL
 *  isiPhone4S()    4S
 *  isiPhone5()     5
 *  isiPhone5C()    5C
 *  isiPhone5S()    5S
 *  isiPhone6P()    6Plus
 *  isiPhone6()     6
 *  isiPhone6SP()   6PLUS S
 *  isiPhone6S()    6S
 */
BOOL isiPhone4S();
BOOL isiPhone5();
BOOL isiPhone5C() ;
BOOL isiPhone5S();
BOOL isiPhone6P();
BOOL isiPhone6();
BOOL isiPhone6SP();
BOOL isiPhone6S();



#pragma mark - 其他工具类
/**
 *  UUID生成
 *
 *  @return 返回类型: NSString
 */
+ (NSString *) gen_UUID;


/**
 *  Attributes生成
 *
 *  @return 返回类型: NSDictionary
 */
+ (NSDictionary *)attributesForFont:(UIFont *)font andAlignment:(NSTextAlignment)aligment;

/**
 *  Attributes生成
 *
 *  @param font        字符大小
 *  @param lineSpacing 行高
 *
 *  @return NSDictionary
 */
+(NSDictionary *)attributesForFont:(UIFont *)font
                   withLineSpacing:(CGFloat)lineSpacing;

/**
 *  返回计算Size
 *
 *  @param font   字符大小
 *  @param text   文本
 *  @param width  文本最大宽度
 *  @param height 文本最大高度
 *
 *  @return CGSize
 */
+(CGSize)heightWithAttributesWithFont:(UIFont *)font
                              withText:(NSString *)text
                             withWidth:(CGFloat)width
                            withHeight:(CGFloat)height;


/**
 *  返回隐藏格式手机号
 *
 *  @param mobile   传入手机号
 */
+ (NSString *)hiddenMobile:(NSString *)mobile;


/**
 *  GCD延迟方法
 *
 *  @param sec    秒数
 *  @param action 回调
 */
+(void)delayInSeconds:(double)sec withAction:(void(^)(void))action;


@end