//
//  SCHospitalEvaluationListViewModel.m
//  HospitalHomePage
//
//  Created by Joseph Gao on 2016/11/2.
//  Copyright © 2016年 Joseph. All rights reserved.
//

#import "SCHospitalEvaluationListViewModel.h"
#import "SCEvaluationModel.h"


@implementation SCHospitalEvaluationListViewModel

- (void)requstEvaluationListWithHospitalID:(NSString *)hospitalID
                             isMoreRequest:(BOOL)isMoreRequest
                              successBlock:(SuccessHandle)successHandler
                              failureBlock:(FailureHandle)failureHandler {
    if (hospitalID == nil || [hospitalID isEqualToString:@""]) {
        self.requestCompeleteType = RequestCompeleteError;
        self.tipErrorString = @"HospitalID no content";
    }
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        self.tipErrorString = @"NO Wifi";
    }
    
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:hospitalID ?: @"" forKey:@"hospitalId"];
    if (isMoreRequest) {
        NSString *flag = self.moreParams[@"flag"] ?: @"0";
        [params setObject:flag forKey:@"flag"];
    }
    
    [self.adapter request:HOSPITAL_EVALUATION_LIST params:params class:[SCEvaluationModel class] responseType:Response_List method:Request_GET needLogin:NO success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        self.requestCompeleteType = RequestCompeleteSuccess;
        self.tipErrorString = @"Success";
        
        ListModel *listModel = response.data;
        self.moreParams = listModel.more_params;
        self.hasMore = [listModel.more boolValue];
        
        if (isMoreRequest) {
            [self.evaluationList addObjectsFromArray:listModel.content];
        } else {
            self.evaluationList = listModel.content.mutableCopy;
        }
        
        if (listModel.content == nil || listModel.content.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }
        
        if (successHandler) {
            successHandler(self.evaluationList);
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



- (NSMutableArray *)evaluationList {
    if (!_evaluationList) {
        _evaluationList = [NSMutableArray array];
    }
    return _evaluationList;
}

@end
