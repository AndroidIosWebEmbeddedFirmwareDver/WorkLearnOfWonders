//
//  UrlParamUtil.h
//  HCPatient
//
//  Created by Lve on 15/6/24.
//  Copyright (c) 2015年 陈刚. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UrlParamUtil : NSObject

+ (NSDictionary *)getParamsFromUrl:(NSURL *)url;

+ (NSDictionary*)paramsFromURL:(NSURL*)url;
+ (NSDictionary*)paramsFromURL:(NSURL*)url usingEncoding:(NSStringEncoding)encoding;

+ (NSURL *)urlWithUrlString:(NSString *)url withParams:(NSDictionary *)params;

+ (NSString *)encodeURLString:(NSString *)text;
+ (NSString *)decodeURLString:(NSString *)urlString;


#pragma mark - 收到 url 打开app
+ (NSDictionary *)getParamsFromOtherApp:(NSURL *)url;

#pragma mark - 收到 push后处理
+ (NSDictionary *)getParamsFromPush:(NSURL *)url;
@end
