//
//  HomeAreaViewModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HomeAreaViewModel.h"
#import "AreaModel.h"
#import "ManagerAreaDataDao.h"
#define CITYLISTURL @"hospital/near/city" 
@implementation HomeAreaViewModel
-(void)getAllCityDatas:(void (^)(void))success failure:(void (^)(NSError * error))failure{
    if(![[Global global] networkReachable]){
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    [self.adapter request:CITYLISTURL params:nil class:[AreaModel class]responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        self.rightSelectArray = [[NSArray alloc]init];
        self.rightSelectArray = response.data;
        
        NSLog(@"^^^^^^^^^^^^^%ld",(unsigned long)self.rightSelectArray.count);
        success();
        
        if (self.rightSelectArray.count==0) {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }else{
            self.requestCompeleteType = RequestCompeleteSuccess;
            
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        if (failure) {
            failure(error);
        }
        
    }];
}

@end
