//
//  SpecialRegexKit.m
//  GZHealthCloudPatient
//
//  Created by 杜凯 on 16/8/4.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "SpecialRegexKit.h"

@implementation SpecialRegexKit
//验证密码
+ (BOOL) validateSpecialPassword:(NSString *)passWord{

    NSString *passWordRegex = @"^(?![0-9]+$)(?![a-zA-Z]+$)[A-Za-z0-9]{6,16}$";
    //    NSString *passWordRegex = @"^(?![0-9]+$)(?![a-zA-Z]+$)[A-Za-z0-9(!@#$%&_)]{6,16}$";
    //    NSString *passWordRegex = @"^[a-zA-Z0-9(!@#$%&_)]{6,16}$";
    NSPredicate *passWordPredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",passWordRegex];
    return [passWordPredicate evaluateWithObject:passWord];

    
}



#pragma mark - 验证真实姓名
+ (BOOL) validateRealname: (NSString *)realname
{
    BOOL flag;
    if (realname.length <= 0) {
        flag = NO;
        return flag;
    }
    NSString *regex2 =@"[\u4e00-\u9fa5]{2,6}";
    NSPredicate *realnamePredicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@",regex2];
    return [realnamePredicate evaluateWithObject:realname];
}
@end
