//
//  WDlnhospitalPayViewModel.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCMyOrderModel.h"
#import "WDRegistrationPayModel.h"

@interface WDlnhospitalPayViewModel : BaseViewModel

@property (nonatomic, strong) SCMyOrderModel *payOrder;
@property (nonatomic, strong) WDRegistrationPayModel *registrationOrder;

@property (nonatomic, strong) NSString *key;

- (void)getLinkPayKeyWithChannel:(NSString *)channel success:(void (^)(void))success failed:(void (^)(NSString *msg))failed;
- (void)getLinkPayInfoWithOrderId:(NSString *)orderId success:(void(^)(payorderModel *payorderModel))success failed:(void(^)(void))failed;

@end
