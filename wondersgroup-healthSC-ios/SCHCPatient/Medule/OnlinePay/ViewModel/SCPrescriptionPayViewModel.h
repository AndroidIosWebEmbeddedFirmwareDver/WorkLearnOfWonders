//
//  SCPrescriptionPayViewModel.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/16.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCMyOrderModel.h"

@interface SCPrescriptionPayViewModel : BaseViewModel

@property (nonatomic, strong) NSString *hospitalCode;

@property (nonatomic, strong) NSArray<SCMyOrderModel *> *orderArr;

- (void)requestUnPayRecords:(void(^)(void))success failed:(void(^)(void))failed;
- (void)requestGenerateOrderWithOrder:(SCMyOrderModel *)order success:(void(^)(SCMyOrderModel *order))success failed:(void(^)(void))failed;

@end
