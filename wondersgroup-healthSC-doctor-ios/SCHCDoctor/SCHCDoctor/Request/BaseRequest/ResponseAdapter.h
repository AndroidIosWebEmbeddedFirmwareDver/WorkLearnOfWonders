//
//  ResponseAdapter.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ResponseModel.h"
#import "ListModel.h"
#import "UploadModel.h"


typedef enum {
    
    Request_POST = 0,       //POST 请求
    Request_GET,            //GET  请求
    Request_DELETE,         //DELETE 请求
    Request_PUT             //PUT 请求
    
} RequestMethod;

typedef enum {
    Response_Object = 0,    //返回的是对象
    Response_List,          //返回的是集合
    Response_Message,       //返回的是文本
} ResponseType;



typedef void(^Success)(NSURLSessionDataTask *task,  ResponseModel *response);   //接口调用成功的Block
typedef void(^Failure)(NSURLSessionDataTask *task,  NSError *error);            //接口调用失败的Block

typedef void(^uploadSuccess)(ResponseModel *response);                          //上传文件成功的Block
typedef void(^uploadFailure)(NSError *error);                                   //上传文件失败的Block


@interface ResponseAdapter : NSObject

+ (instancetype)sharedInstance;


#pragma mark
#pragma mark   Request
/** 接口请求
 *  url      : 接口地址
 *  params   : 接口传参
 *  responseClass   : 解析后的Model Class
 *  type     : 返回类型  列表、对象、文本
 *  method   : 接口请求类型，见RequestMethod枚举
 *  needLogin: 接口请求是否需要登录
 *  success  : 接口成功的回调
 *  failure  : 接口失败的回调
 */
- (void)request: (NSString *)url
         params: (NSDictionary *)params
          class: (Class)responseClass
   responseType: (ResponseType)type
         method: (RequestMethod)method
      needLogin: (BOOL)needLogin
        success: (Success)success
        failure: (Failure)failure;

////不需要显示提示信息
//- (void)request: (NSString *)url
//         params: (NSDictionary *)params
//          class: (Class)responseClass
//   responseType: (ResponseType)type
//         method: (RequestMethod)method
//    showMessage: (BOOL)showMessage
//        success: (Success)success
//        failure: (Failure)failure;
//
//
//#pragma mark
//#pragma mark Request 不需要弹出登录界面
//
//- (void)request: (NSString *)url
//         params: (NSDictionary *)params
//          class: (Class)responseClass
//   responseType: (ResponseType)type
//         method: (RequestMethod)method
//  showLoginView: (BOOL)showLoginView
//        success: (Success)success
//        failure: (Failure)failure;

#pragma mark
#pragma mark   上传文件接口
/**
 *  调用七牛上传文件请求
 *
 *  @param  file     : 上传文件(PS: 请封装成WDUploadFile)
 *  @param  token    : 七牛上传的token值
 *  @param  success  : 接口成功的回调
 *  @param  failure  : 接口失败的回调
 */
- (void)upload: (UploadModel *)file
         token: (NSString *)token
       success: (uploadSuccess)success
       failure: (uploadFailure)failure;



@end
