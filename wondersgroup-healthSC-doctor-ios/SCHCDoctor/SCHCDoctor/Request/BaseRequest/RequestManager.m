//
//  RequestManager.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "RequestManager.h"
#import "ResponseCheck.h"
#import "ErrorModel.h"
#import "UploadModel.h"
#import "SignatureManager.h"


@implementation RequestManager


+ (RequestManager *)sharedInstance {
    static dispatch_once_t pred;
    static RequestManager *shared = nil;
    dispatch_once(&pred, ^{
        
        NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
        configuration.timeoutIntervalForRequest = 30.0;
        
        //te环境下给放出 缓存，只为了测试人员用代理看数据
//        NSString *plistPath = [[NSBundle mainBundle] pathForResource:@"config" ofType:@"plist"];
//        NSMutableDictionary *data = [[NSMutableDictionary alloc] initWithContentsOfFile:plistPath ];
//        NSString * servertype = [data objectForKey:@"servertype"];
//        if ([servertype isEqualToString:@"te"]) {
//            configuration.requestCachePolicy = NSURLRequestReloadIgnoringLocalCacheData;
//        }
        configuration.requestCachePolicy = NSURLRequestReloadIgnoringLocalCacheData;
        shared = [[RequestManager alloc] initWithBaseURL: [Global global].BASE_URL sessionConfiguration: configuration];
    });
    return shared;
}

#pragma mark - 接口请求成功后处理返回结果
- (void)responseComplete: (id)response
                dataTask: (NSURLSessionDataTask *)task
            invalidToken: (TokenInvalidBlock)invalid
                 success: (SuccessBlock)success
                 failure: (FailureBlock)failure {
    
    NSError *error = [ResponseCheck checkResponseObject: response];
    if (error) {
        if ([ResponseCheck isInvalidToken: error] ) {
            //用户Token过期的回调
            if (invalid) {
                invalid(task, error, response);
            }
        }
        else {
            //接口请求失败
            if (failure) {
                if ((int)error.code == 16 && response[@"time_diff"]) { //本地时间和服务器时间差超过10分钟
                    [Context context].time_diff = [response[@"time_diff"] stringValue];
                }
                failure(task, error, response);
            }
        }
    }
    else {
        //接口调用成功
        if (success) {
            success(task, response);
        }
    }
}




#pragma mark -
#pragma mark - 无需Retry的接口请求

#pragma mark   POST请求
- (NSURLSessionDataTask *)requestPOST:  (NSString *)url
                               params:  (NSDictionary *)params
                         tokenInvalid:  (TokenInvalidBlock)invalidToken
                              success:  (SuccessBlock)success
                              failure:  (FailureBlock)failure {
    
    
    //清空HTTP头，给定接口访问类型
    self.requestSerializer = [AFJSONRequestSerializer serializer];
    [self.responseSerializer setAcceptableContentTypes: [NSSet setWithObjects:@"application/json",@"charset=UTF-8", nil]];
    
    //添加HTTP头
    [SignatureManager addHeader: self.requestSerializer params: params method: @"POST" requestPath: url withAPI: YES];
//    NSLog(@"RequestPOST_URL : %@\n Parameters: %@", url, params);
    return [self POST: url parameters: params progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSLog(@"RequestPOST_URL : %@\n Parameters: %@\n Response: %@", url, params, responseObject);
        //接口调用成功后处理
        [self responseComplete: responseObject dataTask: task invalidToken: invalidToken success: success failure: failure];
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"RequestPOST_URL : %@\n Parameters: %@\n Error: %@", url, params, error);
        failure(task, [ResponseCheck checkResponseError: error], nil);
    }];
}

#pragma mark   GET请求
- (NSURLSessionDataTask *)requestGET: (NSString *)url
                              params: (NSDictionary *)params
                        tokenInvalid: (TokenInvalidBlock)invalidToken
                             success: (SuccessBlock)success
                             failure: (FailureBlock)failure {
    //清空HTTP头，给定接口访问类型
    self.requestSerializer = [AFJSONRequestSerializer serializer];
    [self.responseSerializer setAcceptableContentTypes: [NSSet setWithObjects: @"application/json",@"charset=UTF-8", @"text/json", @"text/javascript",@"text/html", nil]];
    //添加HTTP头
    [SignatureManager addHeader: self.requestSerializer params: params method: @"GET" requestPath: url withAPI: YES];
//    NSLog(@"RequestGET_URL : %@\n Parameters: %@", url, params);
    return [self GET: url parameters: params progress:^(NSProgress * _Nonnull downloadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSLog(@"RequestGET_URL : %@\n Parameters: %@\n Response: %@", url, params, responseObject);
        //接口调用成功后处理
        [self responseComplete: responseObject dataTask: task invalidToken: invalidToken success: success failure: failure];
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"RequestGET_URL : %@\n Parameters: %@\n Error: %@", url, params, error);
        failure(task, [ResponseCheck checkResponseError: error], nil);
    }];
    
}

#pragma mark   上传请求
- (NSURLSessionDataTask *)requestUploadFile: (NSString *)url
                                      files: (NSArray *)datas
                                 parameters: (NSDictionary *)params
                               tokenInvalid: (TokenInvalidBlock)invalidToken
                                    success: (SuccessBlock)success
                                    failure: (FailureBlock)failure {
    //清空HTTP头，给定接口访问类型
    self.requestSerializer = [AFJSONRequestSerializer serializer];
    [self.responseSerializer setAcceptableContentTypes: [NSSet setWithObjects:@"application/json",@"charset=UTF-8", @"image/*", nil]];
    //添加HTTP头
    [SignatureManager addHeader: self.requestSerializer params: params method: @"POST" requestPath: url withAPI: YES];
//    NSLog(@"RequestUploadFile_URL : %@\n Parameters: %@", url, params);
    return [self POST: url parameters: params constructingBodyWithBlock:^(id<AFMultipartFormData>  _Nonnull formData) {
        //上传数据准备
        for(int i =0; i< [datas count]; i++) {
            
            UploadModel *image= datas[i];
            
            [formData appendPartWithFileData: image.fileData
                                        name: image.fileName
                                    fileName: [image.fileName stringByAppendingString:[NSString stringWithFormat:@"image%d.jpg",i]]
                                    mimeType: @"application/octet-stream" ];
        }
    } progress:^(NSProgress * _Nonnull uploadProgress) {
        
    } success:^(NSURLSessionDataTask * _Nonnull task, id  _Nullable responseObject) {
        NSLog(@"RequestUploadFile_URL : %@\n Parameters: %@\n Response: %@", url, params, responseObject);
        //接口调用成功后处理
        [self responseComplete: responseObject dataTask: task invalidToken: invalidToken success: success failure: failure];
    } failure:^(NSURLSessionDataTask * _Nullable task, NSError * _Nonnull error) {
        NSLog(@"RequestUploadFile_URL : %@\n Parameters: %@\n Error: %@", url, params, error);
        failure(task, [ResponseCheck checkResponseError: error], nil);
    }];
}

#pragma mark   DELETE请求
- (NSURLSessionDataTask *)requestDELETE: (NSString *)url
                                 params: (NSDictionary *)params
                           tokenInvalid: (TokenInvalidBlock)invalidToken
                                success: (SuccessBlock)success
                                failure: (FailureBlock)failure {
    //清空HTTP头，给定接口访问类型
    self.requestSerializer = [AFJSONRequestSerializer serializer];
    [self.responseSerializer setAcceptableContentTypes: [NSSet setWithObjects: @"application/json", @"text/json", @"text/javascript",@"text/html",@"charset=UTF-8", nil]];
    //添加HTTP头
    [SignatureManager addHeader: self.requestSerializer params: params method: @"DELETE" requestPath: url withAPI: YES];
//    NSLog(@"RequestDELETE_URL : %@\n Parameters: %@", url, params);
    
    return [self DELETE: url parameters: params success:^(NSURLSessionDataTask *task, id responseObject) {
        NSLog(@"RequestDELETE_URL : %@\n Parameters: %@\n Response: %@", url, params, responseObject);
        //接口调用成功后处理
        [self responseComplete: responseObject dataTask: task invalidToken: invalidToken success: success failure: failure];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"RequestDELETE_URL : %@\n Parameters: %@\n Error: %@", url, params, error);
        failure(task, [ResponseCheck checkResponseError: error], nil);
    }];
    
}

#pragma mark   PUT请求
- (NSURLSessionDataTask *)requestPUT: (NSString *)url
                              params: (NSDictionary *)params
                        tokenInvalid: (TokenInvalidBlock)invalidToken
                             success: (SuccessBlock)success
                             failure: (FailureBlock)failure {
    //清空HTTP头，给定接口访问类型
    self.requestSerializer = [AFJSONRequestSerializer serializer];
    [self.responseSerializer setAcceptableContentTypes: [NSSet setWithObjects: @"application/json", @"text/json", @"text/javascript",@"text/html",@"charset=UTF-8", nil]];
    //添加HTTP头
    //    [Utility addHeader: self.requestSerializer params: params method: NO];
    [SignatureManager addHeader: self.requestSerializer params: params method: @"PUT" requestPath: url withAPI: YES];
//    NSLog(@"RequestPUT_URL : %@\n Parameters: %@", url, params);
    return [self PUT: url parameters: params success:^(NSURLSessionDataTask *task, id responseObject) {
        NSLog(@"RequestPUT_URL : %@\n Parameters: %@\n Response: %@", url, params, responseObject);
        //接口调用成功后处理
        [self responseComplete: responseObject dataTask: task invalidToken: invalidToken success: success failure: failure];
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        NSLog(@"RequestPUT_URL : %@\n Parameters: %@\n Error: %@", url, params, error);
        failure(task, [ResponseCheck checkResponseError: error], nil);
        
    }];
}



@end
