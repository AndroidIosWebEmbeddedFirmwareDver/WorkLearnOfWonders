//
//  UrlParamUtil.m
//  HCPatient
//
//  Created by Lve on 15/6/24.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

#import "UrlParamUtil.h"

@implementation UrlParamUtil

+ (NSDictionary *)getParamsFromUrl:(NSURL *)url
{
    NSMutableDictionary *paramsDict = [NSMutableDictionary dictionary];
    
    NSString *requestURLString = [NSString stringWithFormat:@"%@",url];
    
    NSArray *urlArray = [requestURLString componentsSeparatedByString:@"?"];
    
    if (urlArray.count < 2) {
        return paramsDict;
    }
    
    NSArray *paramArray = [[urlArray objectAtIndex:1] componentsSeparatedByString:@"&"];
    
    for (int i = 0; i < paramArray.count; i++) {
        NSString *paramOne = [paramArray objectAtIndex:i];
        NSArray *keyValue = [paramOne componentsSeparatedByString:@"="];
        
        if (keyValue.count == 2) {
            //CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2
            NSString * _dataString = [[[keyValue objectAtIndex:1] stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding] stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            if (_dataString == nil) {
                _dataString = @"";
            }
            [paramsDict setObject:_dataString forKey:[keyValue objectAtIndex:0]];
        }
    }
    
    return paramsDict;
}

+ (NSDictionary*)paramsFromURL:(NSURL*)url
{
    return [self paramsFromURL:url usingEncoding:NSUTF8StringEncoding];
}

+ (NSDictionary*)paramsFromURL:(NSURL*)url usingEncoding:(NSStringEncoding)encoding {
    NSString *query = [url query];
    
    NSCharacterSet* delimiterSet = [NSCharacterSet characterSetWithCharactersInString:@"&;"];
    NSMutableDictionary* pairs = [NSMutableDictionary dictionary];
    NSScanner* scanner = [[NSScanner alloc] initWithString:query];
    while (![scanner isAtEnd]) {
        NSString* pairString = nil;
        [scanner scanUpToCharactersFromSet:delimiterSet intoString:&pairString];
        [scanner scanCharactersFromSet:delimiterSet intoString:NULL];
        NSArray* kvPair = [pairString componentsSeparatedByString:@"="];
        if (kvPair.count == 2) {
            NSString* key = [[kvPair objectAtIndex:0]
                             stringByReplacingPercentEscapesUsingEncoding:encoding];
            NSString* value = [[kvPair objectAtIndex:1]
                               stringByReplacingPercentEscapesUsingEncoding:encoding];
            NSString* decodeValue = [self decodeURLString:value];
            
            [pairs setObject:decodeValue forKey:key];
        }
    }
    
    return [NSDictionary dictionaryWithDictionary:pairs];
}


+ (NSURL *)urlWithUrlString:(NSString *)url withParams:(NSDictionary *)params
{
    NSString *requestURLString = [NSString stringWithFormat:@"%@",url];
    if ([requestURLString rangeOfString:@"?"].location != NSNotFound) {
        requestURLString = [requestURLString stringByAppendingString:@"?"];
    } else {
        requestURLString = [requestURLString stringByAppendingString:@"&"];
    }
    
    for (NSString *key in params.allKeys) {
        requestURLString = [requestURLString stringByAppendingFormat:@"%@=%@", key, [params objectForKey:key]];
        
        if ([params.allKeys indexOfObject:key] != params.allKeys.count - 1) {
            requestURLString = [requestURLString stringByAppendingString:@"&"];
        }
    }
    
    return [NSURL URLWithString:requestURLString];
}

// url 编码
CFStringRef nonAlphaNumValidChars = CFSTR("!$&'()*+,-./:;=?@_~");

+ (NSString *)encodeURLString:(NSString *)text
{
    return (__bridge NSString *)CFURLCreateStringByAddingPercentEscapes(NULL,
                                                                        (__bridge CFStringRef)text, NULL,
                                                                        nonAlphaNumValidChars,
                                                                        kCFStringEncodingUTF8);
}

+ (NSString *)decodeURLString:(NSString *)urlString
{
    return (__bridge NSString *)CFURLCreateStringByReplacingPercentEscapesUsingEncoding(NULL,
                                                                        (__bridge CFStringRef)urlString,
                                                                        CFSTR(""),
                                                                        kCFStringEncodingUTF8);
}

#pragma mark - 收到 url 打开app
+ (NSDictionary *)getParamsFromOtherApp:(NSURL *)url
{
    // 不能用 [url path] 是因为会先decode
    NSArray *array = [[url absoluteString] componentsSeparatedByString:@"/"];
    NSMutableArray *pathComponents = [array mutableCopy];
    
    if (pathComponents.count >= 4) {
        // 删除 前面的 com.wondersgroup.healthcloud://patient
        [pathComponents removeObjectsAtIndexes:[NSIndexSet indexSetWithIndexesInRange:NSMakeRange(0, 3)]];
        // 删除最后的 空字符串
        if ([pathComponents.lastObject isKindOfClass:[NSString class]] &&
            [pathComponents.lastObject isEqualToString:@""]) {
            [pathComponents removeLastObject];
        }
    }
    
    if (pathComponents.count <1) {
        return nil;
    }
    
    // 去掉 编码
    for (int i = 0; i < pathComponents.count; i++) {
        id content = pathComponents[i];
        if ([content isKindOfClass:[NSString class]]) {
            NSString *newContent = [content stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            [pathComponents replaceObjectAtIndex:i withObject:newContent];
        }
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    NSString *target = [pathComponents lastObject];
    
    // 分离 参数
    NSMutableArray *targetAndParameters = [[target componentsSeparatedByString:@"?"] mutableCopy];
    if (targetAndParameters.count <= 0) {
        return nil;
    }
    target = targetAndParameters[0];
    
    if ((int)(targetAndParameters.count) > 1) {
        NSString *value = targetAndParameters[1];
        NSArray *valuse = [value componentsSeparatedByString: @"&"];
        for (NSString *paramString in valuse) {
            NSArray * tempParams = [paramString componentsSeparatedByString:@"="];
            if (tempParams.count != 2) {
                continue;
            }
            params[tempParams[0]] = tempParams[1];
        }
    }
    params[@"target"] = target; // 赋值 target
    //1.测一测
    if ([target isEqualToString:@"health_measure"]) {
        
    }
    // 2.挂个号
    else if ([target isEqualToString:@"reg_hospital"]) {
    }
    // 3.健康圈
    else if ([target isEqualToString:@"health_circle"])
    {
        
    }
    // 4.家庭医生
    else if ([target isEqualToString:@"home_doctor"]) {

    }
    // 5.买个药
    else if ([target isEqualToString:@"buy_medicine"]) {
    }
    // 6.慢病评估
    else if ([target isEqualToString:@"user_ask"]) {
        
    }
    //7.提个问
    else if ([target isEqualToString:@"activity_snake"]) {
        
    }
    //我的日程
    else if ([target isEqualToString:@"schedule"])
    {
        
    }
    // 其它情况 跳转webview
//    else if([target isEqualToString:@"webview"]) {
//    }

    return params;
}


#pragma mark - 收到 url 打开app
+ (NSDictionary *)getParamsFromPush:(NSURL *)url
{
    // 不能用 [url path] 是因为会先decode
    NSArray *array = [[url absoluteString] componentsSeparatedByString:@"/"];
    NSMutableArray *pathComponents = [array mutableCopy];
    
    if (pathComponents.count >= 4) {
        // 删除 前面的 com.wondersgroup.healthcloud://patient
        [pathComponents removeObjectsAtIndexes:[NSIndexSet indexSetWithIndexesInRange:NSMakeRange(0, 3)]];
        // 删除最后的 空字符串
        if ([pathComponents.lastObject isKindOfClass:[NSString class]] &&
            [pathComponents.lastObject isEqualToString:@""]) {
            [pathComponents removeLastObject];
        }
    }
    if (pathComponents.count <1) {
        return nil;
    }
    
    // 去掉 编码
    for (int i = 0; i < pathComponents.count; i++) {
        id content = pathComponents[i];
        if ([content isKindOfClass:[NSString class]]) {
            NSString *newContent = [content stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            [pathComponents replaceObjectAtIndex:i withObject:newContent];
        }
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    
    BOOL needPost = NO;
    NSString *rootStr = [pathComponents count] > 0 ? pathComponents[0] : @"";
    if ([rootStr isEqualToString: @"inquiry"]) {
        //订单相关处理
        needPost = YES;
    }
    params[@"needPost"] = [NSNumber numberWithBool: needPost]; // 赋值 needPost

    NSString *target = [pathComponents lastObject];
    
    // 分离 参数
    NSMutableArray *targetAndParameters = [[target componentsSeparatedByString:@"?"] mutableCopy];
    if (targetAndParameters.count <= 0) {
        return nil;
    }
    target = targetAndParameters[0];

    if ((int)(targetAndParameters.count) > 1) {
        NSString *value = targetAndParameters[1];
        NSArray *valuse = [value componentsSeparatedByString: @"&"];
        for (NSString *paramString in valuse) {
            NSArray * tempParams = [paramString componentsSeparatedByString:@"="];
            if (tempParams.count != 2) {
                continue;
            }
            params[tempParams[0]] = tempParams[1];
        }
    }
    params[@"target"] = target; // 赋值 target
    return params;
}

@end
