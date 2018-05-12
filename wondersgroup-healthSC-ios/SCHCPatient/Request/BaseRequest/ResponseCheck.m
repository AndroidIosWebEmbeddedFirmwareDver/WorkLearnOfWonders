//
//  ResponseCheck.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ResponseCheck.h"

@implementation ResponseCheck

#pragma mark - 检查response结果是否成功，成功返回nil， 失败返回自己组装的Error（PS：Message为接口返回）
+ (NSError *)checkResponseObject:(id)response {
    int code = [response[@"code"] intValue];
    if (code != 0) {
        NSLog(@"错误代码: %d \n错误信息: %@", code, response[@"msg"]);
        if (response[@"msg"] && [response[@"msg"] isKindOfClass:[NSString class]] ) { //业务模块错误代码
            return [self publicError: code message: response[@"msg"]];
        }
        else {  //公共错误
            return [self BusinessError: code message: @"连接失败"];
        }
    }
    //code为0，表示成功
    return nil;
}


#pragma mark - 接口调用失败，把返回的Error转为我们自己的格式
+ (NSError *)checkResponseError:(NSError *)error {
    return [self publicError: (int)(error.code) message: @"连接失败"];
}


#pragma mark - 公共错误
+ (NSError *)publicError:(int)code message:(NSString *)msg {
    
    switch (code) {
        case 1:
            msg = [msg length] ? msg : @"缺少版本号version";
            break;
        case 2:
            msg = [msg length] ? msg : @"缺少设备号device";
            break;
        case 3:
            msg = [msg length] ? msg : @"缺少渠道号channel";
            break;
        case 4:
            msg = [msg length] ? msg : @"缺少手机类型platform";
            break;
        case 5:
            msg = [msg length] ? msg : @"缺少手机型号model";
            break;
        case 6:
            msg = [msg length] ? msg : @"缺少手机系统版本os-version";
            break;
        case 7:
            msg = [msg length] ? msg : @"缺少手机屏幕分辨率screen-width或screen-height";
            break;
        case 10:
            msg = [msg length] ? msg : @"缺少令牌access-token";
            break;
        case 11:
            msg = [msg length] ? msg : @"令牌access-token过期";
            break;
        case 12:
            msg = [msg length] ? msg : @"令牌access-token无效";
            break;
        case 13:
            msg = [msg length] ? msg : @"账户在其他设备登录, access-token无效";
            break;
        case 20:
            msg = [msg length] ? msg : @"缺少签名signature";
            break;
        case 21:
            msg = [msg length] ? msg : @"签名signature验证错误";
            break;
        case 40:
            msg = [msg length] ? msg : @"客户端输入参数缺失";
            break;
        case 41:
            msg = [msg length] ? msg : @"客户端输入参数格式错误";
            break;
        default:
            msg = [msg length] ? msg : @"连接失败";
            break;
    }
    NSNumber *errorCode = [NSNumber numberWithInt: code];
    NSDictionary *errorInfo = @{NSLocalizedDescriptionKey: msg, NSLocalizedFailureReasonErrorKey : errorCode};
    NSError *error = [NSError errorWithDomain: ERROR_DOMAIN code: code userInfo: errorInfo];
    return error;
}


#pragma mark - 业务模块错误代码
+ (NSError *)BusinessError:(int)code message:(NSString *)msg {
    
    NSNumber *errorCode = [NSNumber numberWithInt: code];
    NSDictionary *errorInfo = @{NSLocalizedDescriptionKey: msg, NSLocalizedFailureReasonErrorKey : errorCode};
    NSError *error = [NSError errorWithDomain: ERROR_DOMAIN code: code userInfo: errorInfo];
    return error;
}


#pragma mark - 检查ErrorCode是否是用户登录过期
+ (BOOL)isInvalidToken: (NSError *)error {
    if (error.code == 11 || error.code == 12 || error.code == 13) {
        return YES;
    }
    return NO;
}


@end
