//
//  SignatureManager.m
//  VaccinePatient
//
//  Created by Jam on 16/8/30.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SignatureManager.h"
#import <sys/sysctl.h>
#import <sys/utsname.h>
#import <CommonCrypto/CommonHMAC.h>

#define APP_SECRET  @"b4fad2ff-368c-4ab5-aa0f-e703cd617954"         //APPsecret配置
#define SESSION_KEY @"d5891405-77f9-4c4f-b939-9442aa075836"         //用户key为空时默认的 Session Key

#define MAIN_AREA   @"51"        //区域码配置
#define SPEC_AREA   @""          //特色区域配置
#define API_VERSION @"1.0"       //API版本配置
#define APP_NAME    @"healthsichuan"               //APP_Name配置
#define SECRET_HEADER @"welovepanda!\n"    //加密头配置

//Request所需传入值的Key配置
#define REQUEST_HEADER_LIST @[@"access-token", @"app-version", @"channel",@"device",@"main-area",@"model",@"os-version",@"platform",@"screen-height",@"screen-width",@"spec-area", @"version"]


//以下IOS无需配置，针对安卓
#define CHANNEL     @"appstore"  //APP渠道配置
#define PLATFORM    @"0"         //平台配置


@implementation SignatureManager

#pragma mark - Request的Header、签名
#pragma mark   为接口请求、WebView的Request添加Header
+ (void)addHeader: (id)request
           params: (NSDictionary *)params
           method: (NSString *)method
      requestPath: (NSString *)url
          withAPI: (BOOL)isAPI {
    
//    NSArray *tmpKeys = @[@"access-token",
//                         @"app-version",
//                         @"channel",
//                         @"device",
//                         @"model",
//                         @"os-version",
//                         @"platform",
//                         @"screen-height",
//                         @"screen-width",
//                         @"version"
//                         ];
//    
//    NSArray *tmpValues = @[access_token,
//                           APP_VERSION,
//                           CHANNEL,
//                           deviceID,
//                           DEVICE_MODEL,
//                           DEVICE_VERSION,
//                           PLATFORM,
//                           SCREEN_HEIGHT_STR,
//                           SCREEN_WIDTH_STR,
//                           API_VERSION,
//                           ];
//    
//    NSMutableArray *keys    = [NSMutableArray arrayWithArray: tmpKeys];
//    NSMutableArray *values  = [NSMutableArray arrayWithArray: tmpValues];
//    for (int i=0; i<[keys count]; i++) {
//        [request setValue: values[i] forHTTPHeaderField: keys[i]];
//    }
//    NSDictionary *header = [NSDictionary dictionaryWithObjects: values forKeys: keys];

    
    
    NSString *access_token  = [UserManager manager].token ? [UserManager manager].token : @"";
    NSString *deviceID      = [Global global].deviceUUID  ? [Global global].deviceUUID  : @"deviceID";
    
    NSArray *values = @[access_token,
                        APP_VERSION,
                        CHANNEL,
                        deviceID,
                        MAIN_AREA,
                        DEVICE_MODEL,
                        DEVICE_VERSION,
                        PLATFORM,
                        SCREEN_HEIGHT_STR,
                        SCREEN_WIDTH_STR,
                        @"",
                        API_VERSION];

    for (int i=0; i<[REQUEST_HEADER_LIST count]; i++) {
        [request setValue: values[i] forHTTPHeaderField: REQUEST_HEADER_LIST[i]];
    }
    
    NSDictionary *header = [NSDictionary dictionaryWithObjects: values forKeys: REQUEST_HEADER_LIST];

    //接口调用唯一UUID
    NSString *uuid      = [[NSUUID UUID] UUIDString];
    //接口调用时间
    NSString *dateTime  = [[NSDate date] mt_stringFromDateWithFormat: @"yyyy-MM-dd'T'HH:mm:ss.SSSZZ" localized: NO];
    
    //Request的签名
    NSString *signature = [SignatureManager signature: header requestUUID : uuid requestTime: dateTime params: params requestpath: url method: method  withAPI: isAPI];
    [request setValue: signature    forHTTPHeaderField:@"signature"];
    [request setValue: uuid         forHTTPHeaderField:@"request-id"];
    [request setValue: dateTime     forHTTPHeaderField:@"client-request-time"];
    [request setValue: APP_NAME     forHTTPHeaderField: @"app-name"];
    if ([Context context].time_diff) {
        [request setValue: [Context context].time_diff forHTTPHeaderField: @"time-diff"];
    }
//    [request setValue: @"true" forHTTPHeaderField: @"header-check-skip"];
}

#pragma mark   生成签名
+ (NSString *)signature: (NSDictionary *)headers
            requestUUID: (NSString *)uuid
            requestTime: (NSString *)dateTime
                 params: (NSDictionary *)params
            requestpath: (NSString *)url
                 method: (NSString *)method
                withAPI: (BOOL)isAPI {
    //接口调用唯一UUID
    NSString *requestUUID   = [NSString stringWithFormat: @"request-id=%@", uuid];
    //接口调用时间
    NSString *requestTime   = [NSString stringWithFormat: @"client-request-time=%@", dateTime];
    if ([Context context].time_diff) {
        requestTime = [NSString stringWithFormat: @"%@+time-diff=%@", requestTime, [Context context].time_diff];
    }
    //接口调用
    //不带Method
    NSString *apiPath   = [NSString stringWithFormat: @"%@%@", isAPI ? @"/api/" : @"", [url lowercaseString]];
    //带Method
    //    NSString *apiPath   = [NSString stringWithFormat: @"%@/api/%@", method, [url lowercaseString]];
    
    NSString *requestStr = [NSString stringWithFormat: @"%@%@@%@\n%@", SECRET_HEADER,requestUUID, requestTime, apiPath];
    
    //排序header准备用作签名
    NSString *headerStr  = @"";
    NSArray *array = [[headers allKeys] sortedArrayUsingSelector:@selector(compare:)];
    for (NSString *aKey in array) {
        if(![aKey isEqualToString: @"signature"]){
            if ([headerStr length] == 0) {
                headerStr = [headerStr stringByAppendingString: [NSString stringWithFormat: @"%@=%@", aKey, [headers objectForKey: aKey]]];
            }
            else {
                headerStr = [headerStr stringByAppendingString: [NSString stringWithFormat: @"&%@=%@", aKey, [headers objectForKey: aKey]]];
            }
        }
    }
    //处理params准备用作签名
    NSString *paramsStr = @"";
    if (params) {
        if ([method isEqualToString: @"POST"]) {
            //POST 方法，转成JSONString
            NSError *error      = nil;
            NSData *jsonData    = [NSJSONSerialization dataWithJSONObject: params options: kNilOptions error: &error];
            paramsStr           = [[NSString alloc] initWithData: jsonData encoding: NSUTF8StringEncoding];
        }
        else {
            //非POST 方法，直接拼接
            array = [[params allKeys] sortedArrayUsingSelector:@selector(compare:)];
            for (NSString *aKey in array) {
                if ([paramsStr length] == 0) {
                    paramsStr =[paramsStr stringByAppendingString: [NSString stringWithFormat: @"%@=%@", aKey, [params objectForKey: aKey]]];
                }
                else {
                    paramsStr =[paramsStr stringByAppendingString: [NSString stringWithFormat: @"&%@=%@", aKey, [params objectForKey: aKey]]];
                }
            }
        }
    }
    
    //app secret
    NSString *signature = [NSString stringWithFormat: @"%@+%@+%@\n%@", requestStr,headerStr, paramsStr, APP_SECRET];
    
    //MD5加密
//    NSString *md5 = [SignatureManager md5: signature];
    
    //HA256加密
    NSString *h256 = [SignatureManager ha256AndBase64: signature];
    
    NSLog(@"签名加密前: %@\n签名加密后：%@", signature, h256);
    
    return h256;
}


#pragma mark - 获取设备UUID
+ (NSString *)deviceUUID {
    //拿本地userdefault的uuid
    //如果有 返回
    //如果没有 生成 存储 返回
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *returnString = [defaults objectForKey:@"DeviceUUID"];
    if (ISNULL(returnString)) {
        returnString = [NSUUID UUID].UUIDString;
        [defaults setObject:returnString forKey:@"DeviceUUID"];
        [defaults synchronize];
    }
    return returnString;
}


#pragma mark - 加密、解密类

#pragma mark   HA256 加密
+ (NSString *)ha256AndBase64: (NSString *)input {
    //加密密钥
    NSString *secret =  ISNULL([UserManager manager].key) ?  SESSION_KEY : [UserManager manager].key;
    
    NSData *saltData = [secret dataUsingEncoding:NSUTF8StringEncoding];
    NSData *inputData = [input dataUsingEncoding:NSUTF8StringEncoding];
    NSMutableData* hash = [NSMutableData dataWithLength:CC_SHA256_DIGEST_LENGTH];
    CCHmac(kCCHmacAlgSHA256, saltData.bytes, saltData.length, inputData.bytes, inputData.length, hash.mutableBytes);
    return [hash base64EncodedStringWithOptions: NSDataBase64Encoding64CharacterLineLength];
}

#pragma mark   MD5加密
+ (NSString *)md5:(NSString *)input {
    const char *cStr = [input UTF8String];
    unsigned char digest[16];
    CC_MD5( cStr, (unsigned int)strlen(cStr), digest );
    
    NSMutableString *output = [NSMutableString stringWithCapacity: CC_MD5_DIGEST_LENGTH * 2];
    
    for(int i = 0; i < CC_MD5_DIGEST_LENGTH; i++)
        [output appendFormat:@"%02x", digest[i]];
    
    return  output;
}




@end
