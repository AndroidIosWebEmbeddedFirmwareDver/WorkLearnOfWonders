//
//  AttentionHospitalViewModel.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AttentionHospitalViewModel.h"
#import "MyAttentionHospitalModel.h"


@implementation AttentionHospitalViewModel


- (void)getAttentionHospitalList:(void (^)(void))success failure:(void (^)(void))failure {
    
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    
    [params setObject:[UserManager manager].uid forKey:@"uid"];
    
    [self.adapter request:HOSPITAL_FAVORITE_LIST_URL params:params class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        self.dataArray = [MyAttentionHospitalModel mj_objectArrayWithKeyValuesArray:response.data[@"content"]];
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


- (void)getMoreAttentionHospitalList:(void (^)(void))success failure:(void (^)(void))failure {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }

    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params setObject:[UserManager manager].uid forKey:@"uid"];
    [params setObject:self.moreParams[@"flag"] forKey:@"flag"];
    
    [self.adapter request:HOSPITAL_FAVORITE_LIST_URL params:params class:nil responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        NSArray *datas = [MyAttentionHospitalModel mj_objectArrayWithKeyValuesArray:response.data[@"content"]];
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
