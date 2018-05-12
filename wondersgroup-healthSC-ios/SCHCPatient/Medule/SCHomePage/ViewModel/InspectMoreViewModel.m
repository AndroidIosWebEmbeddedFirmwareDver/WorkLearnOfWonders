//
//  InspectMoreViewModel.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/13.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "InspectMoreViewModel.h"
#define SearchHospitalURL @"home/search/hospital"
#define SearchDoctorsURL @"home/search/doctor"
#define SearchArticeslURL @"home/search/article"

@implementation InspectMoreViewModel

//- (NSArray *)dataArray {
//    if (!_dataArray) {
//        _dataArray = [NSArray array];
//    }
//    return _dataArray;
//}
//- (NSArray *)dataDoctorArray {
//    if (!_dataDoctorArray) {
//        _dataDoctorArray = [NSArray array];
//    }
//    return _dataDoctorArray;
//}
//- (NSArray *)dataArticleArray {
//    if (!_dataArticleArray) {
//        _dataArticleArray = [NSArray array];
//    }
//    return _dataArticleArray;
//}


-(void)getMoreHospitalsList:(NSString *)keyword success:(void (^)(void))success failure:(void (^)(NSError *error))failure{

    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    NSMutableDictionary * dic = [NSMutableDictionary dictionary];
    [dic addEntriesFromDictionary:self.moreParams];
    [dic setObject:keyword forKey:@"keyword"];
    
    [self.adapter request:SearchHospitalURL params:dic class:[SCHospitalModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel *model = response.data;
       self.dataArray = [self.dataArray arrayByAddingObjectsFromArray:model.content];
        self.hasMore = [model.more boolValue];
        if (self.hasMore) {
            self.moreParams = model.more_params;
        }else {
            self.moreParams = nil;
        }
        if (success) {
            success();
        }
        if (self.dataArray.count==0) {
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
-(void)getAllHospitalsList:(NSString *)keyword success:(void (^)(void))success failure:(void (^)(NSError *error))failure{
    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSMutableDictionary  * params = [NSMutableDictionary new];
    
    [params setObject:keyword forKey:@"keyword"];
    
//    if (self.dataArray.count>0) {
//        self.dataArray = nil;
//    }
    
    
    if(self.moreParams)
    {
        [params addEntriesFromDictionary:self.moreParams];
    }
    
    
    [self.adapter request:SearchHospitalURL params:params class:[SCHospitalModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel *model = response.data;
        self.dataArray = [NSArray arrayWithArray: model.content];
        self.hasMore = [model.more boolValue];
        if (self.hasMore) {
            self.moreParams = model.more_params;
        }else {
            self.moreParams = nil;
        }
        if (success) {
            success();
        }
        if (self.dataArray.count==0) {
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
-(void)getMoreDoctorsList:(NSString *)keyword success:(void (^)(void))success failure:(void (^)(NSError *error))failure{
    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSMutableDictionary * dic = [NSMutableDictionary dictionary];
    [dic addEntriesFromDictionary:self.moreParams];
    [dic setObject:keyword forKey:@"keyword"];
    
    [self.adapter request:SearchDoctorsURL params:dic class:[DoctorssModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel * model = response.data;
        self.dataDoctorArray = [self.dataDoctorArray arrayByAddingObjectsFromArray:model.content];
        
//        NSMutableArray *temarray = [NSMutableArray new];
//        [temarray addObject:self.dataDoctorArray];
//        [temarray addObject:model.content];
//        
//        self.dataDoctorArray = temarray.copy;
        self.hasMore = [model.more boolValue];
        
        if (self.hasMore) {
            self.moreParams = model.more_params;
        }else {
            self.moreParams = nil;
        }
        if (success) {
            success();
        }
        if (self.dataDoctorArray.count==0) {
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
-(void)getAllDoctorsList:(NSString *)keyword success:(void (^)(void))success failure:(void (^)(NSError *error))failure{

    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSDictionary * params = @{
                              @"keyword":
                                  keyword
                              };
//    if(self.dataDoctorArray.count!=0){
//        self.dataDoctorArray = nil;
//    }
    [self.adapter request:SearchDoctorsURL params:params class:[DoctorssModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel * model = response.data;
        self.dataDoctorArray = [NSArray arrayWithArray: model.content];;
        self.hasMore = [model.more boolValue];
        
        if (self.hasMore) {
            self.moreParams = model.more_params;
        }else {
            self.moreParams = nil;
        }
        if (success) {
            success();
            
        }
        if (self.dataDoctorArray.count==0) {
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
-(void)getAllAritlesList:(NSString *)keyword success:(void (^)(void))success failure:(void (^)(NSError *error))failure{

    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    
    NSDictionary * params = @{
                              @"keyword":
                                  keyword
                              };
    
//    if (self.dataArticleArray.count>0) {
//        self.dataArticleArray = nil;
//    }
    
    [self.adapter request:SearchArticeslURL params:params class:[ArticlessModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel * model = response.data;
        self.dataArticleArray = [NSArray arrayWithArray: model.content];
        self.hasMore = [model.more boolValue];
        
        if (self.hasMore) {
            self.moreParams = model.more_params;
        }else {
            self.moreParams = nil;
        }
    
        if (success) {
            success();
        }
        if (self.dataArticleArray.count==0) {
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
-(void)getMoreAritlesList:(NSString *)keyword success:(void (^)(void))success failure:(void (^)(NSError *error))failure{
    if(![[Global global] networkReachable])
    {
        self.requestCompeleteType = RequestCompeleteNoWifi;
        return;
    }
    NSMutableDictionary * dic = [NSMutableDictionary dictionary];
    [dic addEntriesFromDictionary:self.moreParams];
    [dic setObject:keyword forKey:@"keyword"];

    [self.adapter request:SearchArticeslURL params:dic class:[ArticlessModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        ListModel * model = response.data;
        self.dataArticleArray = [self.dataArticleArray arrayByAddingObjectsFromArray:model.content];;
        self.hasMore = [model.more boolValue];
        
        if (self.hasMore) {
            self.moreParams = model.more_params;
        }else {
            self.moreParams = nil;
        }
        if (success) {
            success();
        }
        if (self.dataArticleArray.count==0) {
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
