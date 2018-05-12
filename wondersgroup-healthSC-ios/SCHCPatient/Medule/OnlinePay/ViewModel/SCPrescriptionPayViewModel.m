//
//  SCPrescriptionPayViewModel.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCPrescriptionPayViewModel.h"

@implementation SCPrescriptionPayViewModel

- (void)requestUnPayRecords:(void(^)(void))success failed:(void(^)(void))failed {
    NSDictionary *dic = @{
                          @"hospitalCode":_hospitalCode,
                          @"uid":[UserManager manager].uid
                          };
    
    [self.adapter request:MYORDER_UNPAY_RECORDS
                   params:dic
                    class:[SCMyOrderModel class]
             responseType:Response_List
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      ListModel *list = response.data;
                      self.hasMore = [list.more boolValue];
                      self.moreParams = list.more_params;
                      self.orderArr = list.content;
                      if (self.orderArr.count == 0) {
                          self.requestCompeleteType = RequestCompeleteEmpty;
                      }else {
                          self.requestCompeleteType = RequestCompeleteSuccess;
                      }
                      success();
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      self.requestCompeleteType = RequestCompeleteError;
                      failed();
                  }];
}

- (void)requestGenerateOrderWithOrder:(SCMyOrderModel *)order success:(void(^)(SCMyOrderModel *order))success failed:(void(^)(void))failed {
    NSDictionary *dic = @{
                          @"prescriptionNum":StringOrNUll(order.business.prescriptionNum),
                          @"hospitalName":StringOrNUll(order.business.hospitalName),
                          @"time":StringOrNUll(order.business.time),
                          @"deptName":StringOrNUll(order.business.deptName),
                          @"hospitalCode":StringOrNUll(order.business.hospitalCode),
                          @"jzlsh":StringOrNUll(order.business.jzlsh),
                          @"price":[NSString stringWithFormat:@"%@", order.price],
                          @"uid":[UserManager manager].uid
                          };
    [self.adapter request:MYORDER_GENERATE_ORDER
                   params:dic
                    class:[SCMyOrderModel class]
             responseType:Response_Object
                   method:Request_POST
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      SCMyOrderModel *model = response.data;
                      success(model);
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      failed();
                  }];
    
}

@end
