//
//  RegexKit.h
//  HCPatient
//
//  Created by Jam on 15/6/15.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

//--------------------------------------------------------------
/*  RegexKit
 *
 *  各种文本信息规则判断，自行添加时请先检查是否已经有过！
 *
 *  自觉添加注释，方便以后维护！！！
 */
//--------------------------------------------------------------


#import <Foundation/Foundation.h>

@interface RegexKit : NSObject

//验证邮箱
+ (BOOL) validateEmail:(NSString *)email;

//验证手机号码
+ (BOOL) validateMobile:(NSString *)mobile;

//验证用户名
+ (BOOL) validateUserName:(NSString *)name;

//验证密码
+ (BOOL) validatePassword:(NSString *)passWord;

//验证身份证号 开头是14位或者17位数字，结尾可以是数字或者是x或者是X
+ (BOOL) validateIdentityCard: (NSString *)identityCard;

//验证真实姓名 只允许中文
+ (BOOL) validateRealname: (NSString *)realname;

#pragma mark - 验证是英文
+ (BOOL) validateEnglish:(NSString *)value;

//身份证号码验证
+ (BOOL) validateIDCardNumber:(NSString *)value;

//验证QQ号码5位包含5位以上的数字
+ (BOOL) validateQQNum:(NSString *)qq;

//验证验证码
+ (BOOL) validateDynamicCode:(NSString *)code;

//验证密码是否为全数字
+ (BOOL)validateDynamicPWD:(NSString *)code;


/**
 *  验证空字符串
 *
 *  @param value 字符串
 *
 *  @return 为空：NO 不为空：YES
 */
+ (BOOL) validateEmptyString:(NSString *)value;




@end
