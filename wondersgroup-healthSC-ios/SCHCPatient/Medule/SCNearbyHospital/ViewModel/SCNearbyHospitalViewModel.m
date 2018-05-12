//
//  WCNearbyHospitalViewModel.m
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCNearbyHospitalViewModel.h"
#import "SCHospitalModel.h"

@implementation SCNearbyHospitalViewModel

- (void)getNearbyHospitalListWithParams:(NSDictionary *)params Success: (void(^)(void))success failure:(void (^)(void))failure{
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSMutableDictionary * parameters = [[NSMutableDictionary alloc] initWithDictionary:params];
    if (self.moreParams && [self.moreParams objectForKey:@"flag"]) {
        [parameters setValue:[self.moreParams objectForKey:@"flag"] forKey:@"flag"];
    }
    
    [self.adapter request:NEARBY_HOSPITAL_LIST params:parameters class:[SCHospitalModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *listModel = response.data;
        self.moreParams = listModel.more_params;
        self.hasMore = [listModel.more boolValue];
        
        [self.dataArray addObjectsFromArray:listModel.content];
        
        if (_dataArray.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
        } else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        self.requestCompeleteType = RequestCompeleteError;

        failure();
    }];
}

- (void)getNearbyHospitalListWithNewLocationParams:(NSDictionary *)params Success: (void(^)(void))success failure:(void (^)(void))failure{
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    
    self.moreParams = nil;
    [self.dataArray removeAllObjects];
    
    NSMutableDictionary * parameters = [[NSMutableDictionary alloc] initWithDictionary:params];
    if (self.moreParams && [self.moreParams objectForKey:@"flag"]) {
        [parameters setValue:[self.moreParams objectForKey:@"flag"] forKey:@"flag"];
    }
    
    [self.adapter request:NEARBY_HOSPITAL_LIST params:parameters class:[SCHospitalModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *listModel = response.data;
        self.moreParams = listModel.more_params;
        self.hasMore = [listModel.more boolValue];
//        NSLog(@"near hospital count:%zd",listModel.content.count);
        [self.dataArray addObjectsFromArray:listModel.content];
        
        if (_dataArray.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
        } else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        self.requestCompeleteType = RequestCompeleteError;
        
        failure();
    }];
}

- (void)getNearbyHospitalListWithNewCityParams:(NSDictionary *)params Success: (void(^)(void))success failure:(void (^)(void))failure{
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    
    self.moreParams = nil;
    [self.dataArray removeAllObjects];
    
    NSMutableDictionary * parameters = [[NSMutableDictionary alloc] initWithDictionary:params];
    if (self.moreParams && [self.moreParams objectForKey:@"flag"]) {
        [parameters setValue:[self.moreParams objectForKey:@"flag"] forKey:@"flag"];
    }
    
    [self.adapter request:NEARBY_HOSPITAL_LIST params:parameters class:[SCHospitalModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *listModel = response.data;
        self.moreParams = listModel.more_params;
        self.hasMore = [listModel.more boolValue];
        NSLog(@"near hospital count:%zd",listModel.content.count);
        [self.dataArray addObjectsFromArray:listModel.content];
        
        if (_dataArray.count == 0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
        } else {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }
        
        success();
        
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        self.requestCompeleteType = RequestCompeleteError;
        
        failure();
    }];
}

- (NSMutableArray *)dataArray{
    if (_dataArray == nil) {
        _dataArray = [[NSMutableArray alloc] init];
    }
    return _dataArray;
}

@end
