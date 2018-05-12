//
//  SCAppointmentDoctorNameViewModel.m
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDoctorNameViewModel.h"

@implementation SCAppointmentDoctorNameViewModel


-(void)getAppointmentDoctorWithName{
    
//    self.moreParams = [NSDictionary dictionary];
//    self.hasMore = NO;
//    
//    if (![Global global].networkReachable) {
//        self.requestCompeleteType = RequestCompeleteNoWifi;
//    }
//    
//
//    SCAppointmentDetailModel *model = [SCAppointmentDetailModel new];
//    model.doctorName = @"名字思思1111";
//    model.doctorTitle = @"副主任";
//    model.expertin = @"擅长：案例；快速的减肥；阿莱克斯地方";
//    model.patientNumber = @7;
//    model.orderCount = @20;
//    
//    SCAppointmentDetailModel *model1 = [SCAppointmentDetailModel new];
//    model1.doctorName = @"名字去玩儿111";
//    model1.doctorTitle = @"去主任";
//    model1.expertin = @"擅长：去玩儿去玩儿家";
//    model1.patientNumber = @0;
//    model1.orderCount = @1000;
//    
//    self.datas = @[model,model1];
//
//    self.requestCompeleteType = RequestCompeleteSuccess;
//
//    return;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:@{
                                                                                  @"hospitalCode":self.hospitalCode,
                                                                                  @"hosDeptCode":self.hosDeptCode}];
    
    [self.adapter request:QUERYDOCTORLIST params:params class:[SCAppointmentDetailModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *listModel = response.data;
        self.datas = [NSArray arrayWithArray: listModel.content];
        self.hasMore = listModel.more.boolValue;
        if (self.hasMore == NO) {
            self.moreParams = nil;
        }
        else
        {
            self.moreParams = listModel.more_params;
        }
        
        if (self.datas.count) {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }else {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
    }];
}

-(void)getAppointmentDoctorMoreWithName {
    
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithDictionary:@{
                                                                                  @"hospitalCode":self.hospitalCode,
                                                                                  @"hosDeptCode":self.hosDeptCode}];
    
    if (self.moreParams) {
        [params addEntriesFromDictionary:self.moreParams];
    }
    
    [self.adapter request:QUERYDOCTORLIST params:params class:[SCAppointmentDetailModel class] responseType:Response_List method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        ListModel *listModel = response.data;
        [self.datas arrayByAddingObjectsFromArray:listModel.content];
        self.hasMore = listModel.more.boolValue;
        if (self.hasMore == NO) {
            self.moreParams = nil;
        }
        else
        {
            self.moreParams = listModel.more_params;
        }
        
        if (self.datas.count) {
            self.requestCompeleteType = RequestCompeleteSuccess;
        }else {
            self.requestCompeleteType = RequestCompeleteEmpty;
        }
    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        self.requestCompeleteType = RequestCompeleteError;
    }];
}

@end
