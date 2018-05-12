//
//  WDHospitalListRootViewModel.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDHospitalListRootViewModel.h"

@implementation WDHospitalListRootViewModel

- (void)requestHospitalList:(void(^)(void))success failure:(void (^)(void))failure {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSDictionary *dic;
    if (_cityCode) {
        dic = @{@"cityCode":_cityCode};
    } else {
        dic = @{@"cityCode":@"510100000000"};
    }
    
    [self.adapter request:QUERYHOSPITLS
                   params:dic
                    class:[SCHospitalModel class]
             responseType:Response_List
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      ListModel *list = response.data;
                      self.hospitalArr = list.content;
                      self.hasMore = list.more.boolValue;
                      self.flag = [list.more_params objectForKey:@"flag"];
                      if (self.hospitalArr.count) {
                          self.requestCompeleteType = RequestCompeleteSuccess;
                      }else {
                          self.requestCompeleteType = RequestCompeleteEmpty;
                      }
                      success();
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      self.requestCompeleteType = RequestCompeleteError;
                  }];
}

- (void)requestMoreHospitalList:(void(^)(void))success failure:(void (^)(void))failure {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSDictionary *dic;
    if (_cityCode) {
        dic = @{@"cityCode":_cityCode,
                @"flag":_flag};
    } else {
        dic = @{@"cityCode":@"510100000000",
                @"flag":_flag};
    }
    
    [self.adapter request:QUERYHOSPITLS
                   params:dic
                    class:[SCHospitalModel class]
             responseType:Response_List
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      ListModel *list = response.data;
                      self.hospitalArr = [self.hospitalArr arrayByAddingObjectsFromArray:list.content];
                      self.hasMore = list.more.boolValue;
                      self.flag = [list.more_params objectForKey:@"flag"];
                      if (self.hospitalArr.count) {
                          self.requestCompeleteType = RequestCompeleteSuccess;
                      }else {
                          self.requestCompeleteType = RequestCompeleteEmpty;
                      }
                      success();
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      self.requestCompeleteType = RequestCompeleteError;
                  }];
}

@end
