//
//  WDRegistrationPayModel.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/18.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"

@interface WDRegistrationPayModel : BaseViewModel

@property (nonatomic, strong) NSString *payOrderId;
@property (nonatomic, strong) NSString *orderId;
@property (nonatomic, strong) NSString *amount;       //价格，最小单位分；
@property (nonatomic, strong) NSString *price;        //价格，显示用
@property (nonatomic, strong) NSString *showOrderId;

@end
