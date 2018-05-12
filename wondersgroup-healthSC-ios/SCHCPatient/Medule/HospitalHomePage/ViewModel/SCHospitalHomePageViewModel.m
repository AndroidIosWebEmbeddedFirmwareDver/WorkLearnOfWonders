//
//  SCHospitalHomePageViewModel.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/1.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalHomePageViewModel.h"

@implementation SCHospitalHomePageViewModel

- (void)requsetHospitalHomePageData:(NSString *)hospitalID
                  withSuccessHandle:(void(^)(SCHospitalHomePageModel *hospitalModel))successHandler
                      failureHandle:(void(^)(NSError *error))failureHandler {
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        self.tipErrorString = @"NO Wifi";
    }
    
    
    NSDictionary *params = [NSMutableDictionary dictionary];
    [params setValue:hospitalID ?: @"" forKey:@"hosId"];
    if ([UserManager manager].isLogin) {
        [params setValue:[UserManager manager].uid ?: @"" forKey:@"userId"];
    }
    
    __weak typeof(self)weakSelf = self;
    [self.adapter request:HOSPITAL_HOME_PAGE params:params class:[SCHospitalHomePageModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        __strong typeof(self)self = weakSelf;
        self.requestCompeleteType = RequestCompeleteSuccess;
        self.tipErrorString = @"Success";
        self.hospitalModel = response.data;
        self.collected = self.hospitalModel.concern;
        
        if (successHandler) {
            successHandler(self.hospitalModel);
        }
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        
        self.tipErrorString = error.localizedDescription;
        self.requestCompeleteType = RequestCompeleteError;
        if (failureHandler) {
            failureHandler(error);
        }
    }];
}


- (void)collectHospitalWithHospitalID:(NSString *)hospitalID
                        successHandle:(void(^)())successHandler
                        failureHandle:(void(^)(NSError *error))failureHandler {
    
    NSDictionary *params = @{
                             @"uid": [UserManager manager].uid ?: @"",
                             @"hospitalId": hospitalID ?: @"",
                             };
    
    __weak typeof(self)weakSelf = self;
    [self.adapter request:HOSPITAL_COLLECT params:params class:nil responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        __strong typeof(self)self = weakSelf;
        
        [MBProgressHUDHelper showHudWithText:response.message];
        
        self.collected = !self.isCollected;
        
        if (successHandler) {
            successHandler();
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        
        if (failureHandler) {
            failureHandler(error);
        };
    }];
}


- (void)publishEvaluation:(NSString *)evaluation withHospitalID:(NSString *)hospitalID successHandle:(void (^)())successHandler failureHandle:(void (^)(NSError *))failureHandler {
    NSDictionary *params = @{
                             @"uid": [UserManager manager].uid ?: @"",
                             @"hospitalId": hospitalID ?: @"",
                             @"content": evaluation ?: @"",
                             };
    
    [self.adapter request:HOSPITAL_EVALUATION_POST params:params class:nil responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        [MBProgressHUDHelper showHudWithText:response.message];
        
        if (successHandler) {
            successHandler();
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        
        if (failureHandler) {
            failureHandler(error);
        };
    }];
}
@end







