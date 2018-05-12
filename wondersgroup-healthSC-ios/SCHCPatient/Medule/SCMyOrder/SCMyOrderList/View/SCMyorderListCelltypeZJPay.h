//
//  SCMyorderListCelltypePay.h
//  SCHCPatient
//  诊间支付 cell
//  Created by wanda on 16/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCMyOrderModel.h"
typedef void(^OrderStatusButtonBlock)(SCMyOrderModel*model);
@interface SCMyorderListCelltypeZJPay : UITableViewCell
@property (nonatomic, copy ) OrderStatusButtonBlock statusButtonBlock;
@property (nonatomic,strong) SCMyOrderModel         *orderModel;
@property (nonatomic,strong) UIButton               *payButton;
@property (nonatomic,strong) UILabel                *hospitalNameLabel;
@property (nonatomic,strong) UILabel                *keshiLabel;
@property (nonatomic,strong) UILabel                *orderStateLabel;
@property (nonatomic,strong) UILabel                *timeLabel;
@property (nonatomic,strong) UILabel                *moneyLabel;
@property (nonatomic,strong) UILabel                *orderNumberLabel;
@property (nonatomic,strong) UILabel                *orderstateLb;
@property (nonatomic,strong) UILabel                *kfNumberLabel;
@end
