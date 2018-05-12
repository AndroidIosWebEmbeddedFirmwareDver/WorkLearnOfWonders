//
//  SCRegistrationPayViewModel.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCRegistrationPayViewModel.h"

@implementation SCRegistrationPayViewModel

- (void)requestRegistration:(void(^)(void))success failed:(void(^)(void))failed {
    NSDictionary *dic = @{
                          @"uid":[UserManager manager].uid
                          };
    
    [self.adapter request:INTER_DIA_PAYMENT_APPOINT
                   params:dic
                    class:[SCMyOrderModel class]
             responseType:Response_List
                   method:Request_GET
                needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                    ListModel *list = response.data;
                    _orderArr = list.content;
                    self.hasMore = [list.more boolValue];
                    self.moreParams = list.more_params;
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

- (void)requestMoreRegistration:(void(^)(void))success failed:(void(^)(void))failed {
    NSDictionary *dic = @{
                          @"uid":[UserManager manager].uid,
                          @"flag":[self.moreParams objectForKey:@"flag"]
                          };
    
    [self.adapter request:INTER_DIA_PAYMENT_APPOINT
                   params:dic
                    class:[SCMyOrderModel class]
             responseType:Response_List
                   method:Request_GET
                needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                    ListModel *list = response.data;
                    _orderArr = [_orderArr arrayByAddingObjectsFromArray:list.content];
                    self.hasMore = [list.more boolValue];
                    self.moreParams = list.more_params;
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

@end
