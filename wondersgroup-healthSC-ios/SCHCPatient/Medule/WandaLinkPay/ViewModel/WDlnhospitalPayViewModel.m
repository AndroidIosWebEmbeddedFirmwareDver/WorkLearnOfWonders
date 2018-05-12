//
//  WDlnhospitalPayViewModel.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDlnhospitalPayViewModel.h"
#import "WDLinkPayKeyModel.h"

@implementation WDlnhospitalPayViewModel

- (void)getLinkPayKeyWithChannel:(NSString *)channel success:(void (^)(void))success failed:(void (^)(NSString *))failed {
    NSDictionary *dic;
    NSString *oid;
    if (_payOrder) {
        oid = _payOrder.pay_order.oid;
    } else {
        oid = _registrationOrder.payOrderId;
    }
    if ([channel isEqualToString:@"alipay"]) {
        dic = @{
                @"id":oid,
                @"channel":@"alipay"
                };
    } else if ([channel isEqualToString:@"weixin"]) {
        dic = @{
                @"id":oid,
                @"channel":@"wepay"
                };
    }
    
    [self.adapter request:LINK_PAY_KEY
                   params:dic
                    class:[WDLinkPayKeyModel class]
             responseType:Response_Object
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      WDLinkPayKeyModel *model = response.data;
                      self.key = model.key;
                      success();
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      failed(error.localizedDescription);
                  }];
}

- (void)getLinkPayInfoWithOrderId:(NSString *)orderId success:(void(^)(payorderModel *payorderModel))success failed:(void(^)(void))failed {
    NSDictionary *dic = @{
                          @"id":orderId
                          };
    
    [self.adapter request:LINK_PAY_INFO
                   params:dic
                    class:[payorderModel class]
             responseType:Response_Object
                   method:Request_GET
                needLogin:YES
                  success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                      payorderModel *model = response.data;
                      success(model);
                  } failure:^(NSURLSessionDataTask *task, NSError *error) {
                      failed();
                  }];
}

@end
