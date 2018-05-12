//
//  TakenReportViewModel.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "TakenReportViewModel.h"
#import "SCHospitalModel.h"
@implementation TakenReportViewModel


- (NSArray *)dataArray {
    if (!_dataArray) {
        _dataArray = [NSArray array];
    }
    return _dataArray;
}


- (void)getHostptialNameSuccess:(void(^)(void))success failed:(void(^)(NSError *error))failed {
    if (![Global global].networkReachable) {
        self.requestCompeleteType = RequestCompeleteNoWifi;
    }
    [self.adapter request:@"search/recommend"
                   params:@{@"wd":self.keyWord}
                    class:[SCHospitalModel class]
             responseType:Response_List
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      if (self.dataArray.count != 0) {
                            self.dataArray = nil;
                       }
                      self.dataArray = [self.dataArray arrayByAddingObjectsFromArray:response.data];
                      if (self.dataArray.count == 0) {
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

@end
