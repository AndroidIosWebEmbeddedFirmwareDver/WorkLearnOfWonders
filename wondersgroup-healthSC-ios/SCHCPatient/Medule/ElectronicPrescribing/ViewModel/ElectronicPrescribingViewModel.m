//
//  ElectronicPrescribingViewModel.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ElectronicPrescribingViewModel.h"

@implementation ElectronicPrescribingViewModel

- (NSArray *)dataArray {
    if (!_dataArray) {
        _dataArray = [NSArray array];
    }
    return _dataArray;
}

- (void)getElectronicPrescribingDataFromeServer:(void(^)(void))success failed:(void(^)(NSError *error))failed {
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    NSDictionary *dic = @{
                          @"yljgdm" :  self.medicalOrgId,
                          @"timeFlag" :self.day,
                          @"userId" :  [UserManager manager].uid};
    [[ResponseAdapter sharedInstance] request :@"eprescription/list"
          params:dic
           class:[ElectronicPrescribingListModel class]
    responseType:Response_Object
          method:Request_GET
       needLogin:YES
         success:^(NSURLSessionDataTask  *task, ResponseModel *response) {
            if (self.dataArray.count != 0) {
                self.dataArray = nil;
            }
            self.dataModel = response.data;
            for (id obj in self.dataModel.prescription) {
                self.dataArray = [self.dataArray arrayByAddingObject:@[obj]];
            }
            if (self.dataModel.prescription.count == 0) {
                self.requestCompeleteType = RequestCompeleteEmpty;
            }else {
                self.requestCompeleteType = RequestCompeleteSuccess;
            }
           success();
        } failure:^(NSURLSessionDataTask *task, NSError *error) {
            failed(error);
            self.requestCompeleteType = RequestCompeleteError;
        }];
}

//- (void)getElectronicPrescribingDataFromeServer:(void(^)(void))success failed:(void(^)(NSString *message))failed {
//    if (![Global global].networkReachable) {
//        self.requestCompeleteType = RequestCompeleteNoWifi;
//    }
//    NSDictionary *dic = @{@"yljgdm":self.medicalOrgId,@"timeFlag":self.day,@"userId":[UserManager manager].uid};
//    [[ResponseAdapter sharedInstance] request:@"eprescription/list" params:dic class:[ElectronicPrescribingListModel class] responseType:Response_Object method:Request_GET needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
//        if (self.dataArray.count != 0) {
//            self.dataArray = nil;
//        }
//        self.dataModel = response.data;
//        for (id obj in self.dataModel.prescription) {
//            self.dataArray = [self.dataArray arrayByAddingObject:@[obj]];
//        }
//        if (self.dataModel.prescription.count == 0) {
//            self.requestCompeleteType = RequestCompeleteEmpty;
//        }else {
//            self.requestCompeleteType = RequestCompeleteSuccess;
//        }
//        success();
//    } failure:^(NSURLSessionDataTask *task, NSError *error) {
//        self.requestCompeleteType = RequestCompeleteError;
//        failed();
//    }];
//}

@end
