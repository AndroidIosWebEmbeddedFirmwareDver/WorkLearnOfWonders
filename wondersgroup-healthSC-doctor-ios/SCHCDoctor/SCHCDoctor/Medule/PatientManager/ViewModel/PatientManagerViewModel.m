//
//  PatientManagerViewModel.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientManagerViewModel.h"
#import "PatientListModel.h"

@implementation PatientManagerViewModel

- (void)getNewPatientsPrompt:(void(^)(void))success failed:(void(^)(NSError *error))failed {
    
//    if (![Global global].networkReachable) {
//        self.requestCompeleteType = RequestCompeleteNoWifi;
//        return;
//    }
//    NSDictionary *params = @{@"":@"",@"":@""};
//    NSMutableDictionary * parameters = [[NSMutableDictionary alloc] initWithDictionary:params];
//    
//    [self.adapter request:patient_New_Prompt params:parameters class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        
//        self.hasNewlyAdded = response.data[@"hasNewlyAdded"];
//        
//        self.requestCompeleteType = RequestCompeleteSuccess;
//        
//        success();
//        
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
//        self.requestCompeleteType = RequestCompeleteError;
//        
//        failed(error);
//    }];
    
    self.hasNewlyAdded = @(YES);
}

@end
