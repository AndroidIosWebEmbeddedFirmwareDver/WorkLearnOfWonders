//
//  RequestManager.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFNetworking.h"


/*  Block
 *
 *  task        : 请求数据对话
 *  response    : 返回数据
 *  error       : 请求失败的Error
 */
typedef void(^SuccessBlock)(NSURLSessionDataTask *task, id response);                       //接口调用成功的Block
typedef void(^FailureBlock)(NSURLSessionDataTask *task, NSError *error, id response);       //接口调用失败的Block
typedef void(^TokenInvalidBlock)(NSURLSessionDataTask *task,  NSError *error, id response); //用户Token失效的Block

typedef NSURLSessionDataTask *(^AutoCallBlock)(FailureBlock retryBlock);  //Retry接口回调的Block
//下面注掉的是AutoCallBlock的全写
//typedef NSURLSessionDataTask *(^AutoCallBlock)(void (^)(NSURLSessionDataTask *, NSError *));//Retry接口回调的Block

@interface RequestManager : AFHTTPSessionManager

#pragma mark - 请求单例
+(RequestManager *)sharedInstance;

#pragma mark -
#pragma mark   接口请求

/*
 *  POST、GET、DELETE、PUT请求
 *
 *  url      : 接口地址
 *  params   : 接口传参
 *  success  : 接口成功的回调
 *  failure  : 接口失败的回调
 *  返回      : NSURLSessionDataTask
 */

#pragma mark
#pragma mark   POST请求
- (NSURLSessionDataTask *)requestPOST:  (NSString *)url
                               params:  (NSDictionary *)params
                         tokenInvalid:  (TokenInvalidBlock)invalidToken
                              success:  (SuccessBlock)success
                              failure:  (FailureBlock)failure;

#pragma mark   GET请求
- (NSURLSessionDataTask *)requestGET:   (NSString *)url
                              params:   (NSDictionary *)params
                        tokenInvalid:   (TokenInvalidBlock)invalidToken
                             success:   (SuccessBlock)success
                             failure:   (FailureBlock)failure;

#pragma mark   DELETE请求
- (NSURLSessionDataTask *)requestDELETE: (NSString *)url
                                 params: (NSDictionary *)params
                           tokenInvalid: (TokenInvalidBlock)invalidToken
                                success: (SuccessBlock)success
                                failure: (FailureBlock)failure;

#pragma mark   PUT请求
- (NSURLSessionDataTask *)requestPUT:   (NSString *)url
                              params:   (NSDictionary *)params
                        tokenInvalid:   (TokenInvalidBlock)invalidToken
                             success:   (SuccessBlock)success
                             failure:   (FailureBlock)failure;

/*
 *  上传文件请求
 *
 *  url      : 接口地址
 *  datas    : 上传文件list (PS: 请封装成WDUploadFile的List)
 *  params   : 接口传参
 *  success  : 接口成功的回调
 *  failure  : 接口失败的回调
 *  返回      : NSURLSessionDataTask
 */
#pragma mark   上传文件
//- (NSURLSessionDataTask *)requestUploadFile:  (NSString *)url
//                                      files:  (NSArray *)datas
//                                     params:  (NSDictionary *)params
//                               tokenInvalid:  (TokenInvalidBlock)invalidToken
//                                    success:  (SuccessBlock)success
//                                    failure:  (FailureBlock)failure;



@end
