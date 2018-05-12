//
//  SCRegistrationPayViewModel.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/17.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCMyOrderModel.h"

@interface SCRegistrationPayViewModel : BaseViewModel

@property (nonatomic, strong) NSArray<SCMyOrderModel *> *orderArr;

- (void)requestRegistration:(void(^)(void))success failed:(void(^)(void))failed;
- (void)requestMoreRegistration:(void(^)(void))success failed:(void(^)(void))failed;

@end
