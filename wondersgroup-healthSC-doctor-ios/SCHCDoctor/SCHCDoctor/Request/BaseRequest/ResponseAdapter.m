//
//  ResponseAdapter.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ResponseAdapter.h"
#import "QiniuSDK.h"
#import "RequestManager.h"
#import "ResponseCheck.h"
#import "ListModel.h"
#import "ErrorModel.h"
#import "TaskService.h"
#import "UserService.h"

@interface ResponseAdapter () {
}

@property (nonatomic, strong) RequestManager *requestManager;
@property (nonatomic, strong) QNUploadManager  *uploadManager;


@end


@implementation ResponseAdapter


+ (instancetype)sharedInstance {
    static dispatch_once_t onceToken;
    static ResponseAdapter *shared = nil;
    dispatch_once(&onceToken, ^{
        shared = [[ResponseAdapter alloc] init];
    });
    return shared;
}



#pragma mark - 接口请求单例
- (RequestManager *)requestManager {
    return [RequestManager sharedInstance];
}

#pragma mark - 上传请求单例
- (QNUploadManager *)uploadManager {
    if (!_uploadManager) {
        return [[QNUploadManager alloc] init];
    }
    return _uploadManager;
}


//#pragma mark - 上传请求单例
//- (QNUploadManager *)uploadManager {
//    if (!_uploadManager) {
//        return [[QNUploadManager alloc] init];
//    }
//    return _uploadManager;
//}


#pragma mark - 接口返回数据解析
- (ResponseModel *)analysisSuccessData: (id)response
                             withClass: (Class)class
                      withResponseType: (ResponseType)type {
    
    ResponseModel *returnObject = [[ResponseModel alloc] init];
    returnObject.message = response[@"msg"];
    if (class) {
        if(response[@"data"]) {
            switch (type) {
                case Response_Object:
                {
                    //返回对象
                    returnObject.data = [class mj_objectWithKeyValues: response[@"data"]];
                }
                    break;
                case Response_List:
                {
                    if ([response[@"data"] isKindOfClass:[NSArray class]]) {
                        returnObject.data = [class mj_objectArrayWithKeyValuesArray: response[@"data"]];
                    }
                    else if ([response[@"data"] objectForKey:@"content"]) {
                        NSString *content = NSStringFromClass(class);
                        [ListModel mj_setupObjectClassInArray:^NSDictionary *{
                            return @{
                                     @"content"        : content,
                                     };
                        }];
                        //返回对象集合
                        returnObject.data = [ListModel mj_objectWithKeyValues: response[@"data"]];
                    }
                    
                }
                    break;
                default:
                    break;
            }
        }
    }
    else {
        if(response[@"data"]) {
            returnObject.data = response[@"data"];
        }
        else {
            returnObject.data = response;
        }
    }
    return returnObject;
}


#pragma mark
#pragma mark - Request
//- (void)request: (NSString *)url
//         params: (NSDictionary *)params
//          class: (Class)responseClass
//   responseType: (ResponseType)type
//         method: (RequestMethod)method
//      needLogin: (BOOL)needLogin
//        success: (Success)success
//        failure: (Failure)failure {
//    [self request:url params:params class:responseClass responseType:type method:method needLogin: needLogin success:success failure:failure];
//}
//

#pragma mark
#pragma mark   Request  showLoginView
- (void)request: (NSString *)url
         params: (NSDictionary *)params
          class: (Class)responseClass
   responseType: (ResponseType)type
         method: (RequestMethod)method
      needLogin: (BOOL)needLogin
        success: (Success)success
        failure: (Failure)failure {
    
    switch (method) {
        case Request_POST:
        {
            //POST 请求
            [self.requestManager requestPOST: url params: params tokenInvalid:^(NSURLSessionDataTask *task, NSError *error, id response) {
                //用户Token过期的处理
                ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                if (needLogin) {
                    [MBProgressHUDHelper showHudWithText: analysisData.message];
                    
                    //需要弹出登录
                    [[UserService service] userKickLogoff];
                    [[UserManager manager] updateLogOffInfo: YES];
                    
                }
                else {
                    //不需要弹出登录
                    success(task, analysisData);
                }
            } success:^(NSURLSessionDataTask *task, id response) {
                //成功，直接解析数据
                ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                success(task, analysisData);

            } failure:^(NSURLSessionDataTask *task, NSError *error, id response) {
//                if (showMessage) {
//                    //需要弹提示
//                    [MBProgressHUDHelper showHudWithText: error.localizedDescription];
//                }
                failure(task, error);
            }];
        }
            break;
        case Request_GET:
        {
            //GET 请求
            [self.requestManager requestGET: url params: params tokenInvalid:^(NSURLSessionDataTask *task, NSError *error, id response) {
                //用户Token过期的处理
                ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                if (needLogin) {
                    [MBProgressHUDHelper showHudWithText: analysisData.message];
                    //需要弹出登录
                    //用户注销  先调用service 否则UID会被清空
                    [[UserService service] userKickLogoff];
                    [[UserManager manager] updateLogOffInfo: YES];
//                    failure(task, error);
                }
                else {
                    //不需要弹出登录
                    ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                    success(task, analysisData);
                }
                
            } success:^(NSURLSessionDataTask *task, id response) {
                //成功，直接解析数据
                ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                success(task, analysisData);
            } failure:^(NSURLSessionDataTask *task, NSError *error, id response) {
//                if (showMessage) {
//                    //需要弹提示
//                    [MBProgressHUDHelper showHudWithText: error.localizedDescription];
//                }
                failure(task, error);
            }];
        }
            break;
        case Request_DELETE:
        {
            //DELETE 请求
            [self.requestManager requestDELETE: url params: params tokenInvalid:^(NSURLSessionDataTask *task, NSError *error, id response) {
                //用户Token过期的处理
                ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                if (needLogin) {
                    [MBProgressHUDHelper showHudWithText: analysisData.message];
                    //需要弹出登录
                    //用户注销  先调用service 否则UID会被清空
                    [[UserService service] userKickLogoff];
                    [[UserManager manager] updateLogOffInfo: YES];
                }
                else {
                    //不需要弹出登录
                    ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                    success(task, analysisData);
                }
                
            } success:^(NSURLSessionDataTask *task, id response) {
                //成功，直接解析数据
                ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                success(task, analysisData);
            } failure:^(NSURLSessionDataTask *task, NSError *error, id response) {
//                if (showMessage) {
//                    //需要弹提示
//                    [MBProgressHUDHelper showHudWithText: error.localizedDescription];
//                }
                failure(task, error);
            }];
        }
            break;
        case Request_PUT:
        {
            //PUT 请求
            [self.requestManager requestPUT: url params: params tokenInvalid:^(NSURLSessionDataTask *task, NSError *error, id response) {
                //用户Token过期的处理
                ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                if (needLogin) {
                    [MBProgressHUDHelper showHudWithText: analysisData.message];
                    //需要弹出登录
                    //用户注销  先调用service 否则UID会被清空
                    [[UserService service] userKickLogoff];
                    [[UserManager manager] updateLogOffInfo: YES];
                }
                else {
                    //不需要弹出登录
                    ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                    success(task, analysisData);
                }
                
            } success:^(NSURLSessionDataTask *task, id response) {
                //成功，直接解析数据
                ResponseModel *analysisData = [self analysisSuccessData: response withClass: responseClass withResponseType: type];
                success(task, analysisData);
            } failure:^(NSURLSessionDataTask *task, NSError *error, id response) {
//                if (showMessage) {
//                    //需要弹提示
//                    [MBProgressHUDHelper showHudWithText: error.localizedDescription];
//                }
                failure(task, error);
            }];
        }
            break;
        default:
            break;
    }
    
}

#pragma mark   上传文件接口

- (void)upload: (UploadModel *)file
         token: (NSString *)token
       success: (uploadSuccess)success
       failure: (uploadFailure)failure {
    
    [[TaskService service] getQiNiuToken:^{
        if ([TaskManager manager].qnToken) {

            [self.uploadManager putData: file.fileData key: file.fileName token: [TaskManager manager].qnToken complete:^(QNResponseInfo *info, NSString *key, NSDictionary *resp) {
                
                if (resp) {
                    ResponseModel *model = [[ResponseModel alloc] init];
                    
                    model.data    = [NSString stringWithFormat: @"%@/%@", [TaskManager manager].domain, key];
                    model.message = @"上传图片成功";
                    
                    NSLog(@"%@", model.data );
                    success(model);
                }
                else {
                    NSNumber *errorCode = [NSNumber numberWithInt: info.statusCode];
//                NSDictionary *errorInfo = @{NSLocalizedDescriptionKey: info.error.localizedDescription, NSLocalizedFailureReasonErrorKey : errorCode};
                    NSDictionary *errorInfo = @{NSLocalizedDescriptionKey: @"上传图片失败", NSLocalizedFailureReasonErrorKey : errorCode};
                    NSError *error = [NSError errorWithDomain: ERROR_DOMAIN code: info.statusCode userInfo: errorInfo];
                    //七牛Token没有，重新获取七牛Token
//                    [[TaskService service] getQiNiuToken:^{
//                    }];
                    failure(error);
                }
            } option: nil];
        }
        else {
            NSDictionary *errorInfo = @{NSLocalizedDescriptionKey: @"上传图片失败", NSLocalizedFailureReasonErrorKey : @"-1002"};
            NSError *error = [NSError errorWithDomain: ERROR_DOMAIN code: -1002 userInfo: errorInfo];
            failure(error);
        }
    }];
    
/*
    if (token) {
        [self.uploadManager putData: file.fileData key: file.fileName token: token complete:^(QNResponseInfo *info, NSString *key, NSDictionary *resp) {
            
            if (resp) {
                ResponseModel *model = [[ResponseModel alloc] init];
                
                model.data    = [NSString stringWithFormat: @"http://%@/%@", [TaskManager manager].domain, key];
                model.message = @"上传图片成功";
                
                NSLog(@"%@", model.data );
                success(model);
            }
            else {
                NSNumber *errorCode = [NSNumber numberWithInt: info.statusCode];
//                NSDictionary *errorInfo = @{NSLocalizedDescriptionKey: info.error.localizedDescription, NSLocalizedFailureReasonErrorKey : errorCode};
                NSDictionary *errorInfo = @{NSLocalizedDescriptionKey: @"上传图片失败", NSLocalizedFailureReasonErrorKey : errorCode};
                NSError *error = [NSError errorWithDomain: ERROR_DOMAIN code: info.statusCode userInfo: errorInfo];
                //七牛Token没有，重新获取七牛Token
                [[TaskService service] getQiNiuToken:^{
                }];
                failure(error);
            }
        } option: nil];
    }
    else {
        //七牛Token没有，重新获取七牛Token
        [[TaskService service] getQiNiuToken:^{
            //重新上传图片
            [self upload: file token: [TaskManager manager].qnToken success: success failure: failure];
        }];
    }
*/
}

#pragma mark - 返回Failure后组装error
- (ErrorModel *)failureError:(NSError *)error {
    ErrorModel *errorInfo = [[ErrorModel alloc] init];
    errorInfo.code = [NSNumber numberWithInteger: error.code];
    if (errorInfo.status != Request_Model_Success) {
        NSLog(@"RequestFailure : %@", error);
    }
    return  errorInfo;
}



@end
