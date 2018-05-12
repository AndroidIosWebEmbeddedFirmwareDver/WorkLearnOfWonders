//
//  WDPayResultViewController.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/13.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"

typedef NS_ENUM(NSInteger, WDPayResultType) {
    WDPaySuccess,
    WDPayFaild
};

@interface WDPayResultViewController : BaseViewController

@property (nonatomic, assign) WDPayResultType payResult;
@property (nonatomic, strong) NSString *price;
@property (nonatomic, strong) NSString *massage;
@property (nonatomic, strong) NSString *orderId;
@property (nonatomic, assign) BOOL isRegistration;
@property (nonatomic, assign) BOOL notCanRePay;

@end
