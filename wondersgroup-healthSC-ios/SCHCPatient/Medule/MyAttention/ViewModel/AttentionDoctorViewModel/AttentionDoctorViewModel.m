//
//  AttentionDoctorViewModel.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AttentionDoctorViewModel.h"
#import "MyAttentionDoctorModel.h"


@implementation AttentionDoctorViewModel

- (void)getAttentionDoctorList:(void (^)(void))success failure:(void (^)(void))failure {
    

    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    
    [params setObject:[UserManager manager].uid forKey:@"uid"];
    
    [self.adapter request:DOCTOR_FAVORITE_LIST_URL params:params class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        self.dataArray = [MyAttentionDoctorModel mj_objectArrayWithKeyValuesArray:response.data[@"content"]];
        [@"s" integerValue];
        
        if (self.dataArray.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
            success();
            return ;
        }
        else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        self.hasMore = [response.data[@"more"] boolValue];
        if (self.hasMore) {
            self.moreParams = response.data[@"more_params"];
        }
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        if (![Global global].networkReachable) {
            self.requestCompeleteType = RequestCompeleteNoWifi;
        }
        else {
            self.requestCompeleteType = RequestCompeleteError;
        }

        failure();
    }];
}


- (void)getMoreAttentionDoctorList:(void (^)(void))success failure:(void (^)(void))failure {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:[UserManager manager].uid forKey:@"uid"];
    [params setObject:self.moreParams[@"flag"] forKey:@"flag"];
    
    [self.adapter request:DOCTOR_FAVORITE_LIST_URL params:params class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSArray *datas = [MyAttentionDoctorModel mj_objectArrayWithKeyValuesArray:response.data[@"content"]];
        [self.dataArray addObjectsFromArray:datas];
        
        self.requestCompeleteType = RequestCompeleteSuccess;
        
        self.hasMore = [response.data[@"more"] boolValue];
        if (self.hasMore) {
            self.moreParams = response.data[@"more_params"];
        }
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        
        if (![Global global].networkReachable) {
            self.requestCompeleteType = RequestCompeleteNoWifi;
        }
        else {
            self.requestCompeleteType = RequestCompeleteError;
        }
        
        failure();
    }];
}

@end
