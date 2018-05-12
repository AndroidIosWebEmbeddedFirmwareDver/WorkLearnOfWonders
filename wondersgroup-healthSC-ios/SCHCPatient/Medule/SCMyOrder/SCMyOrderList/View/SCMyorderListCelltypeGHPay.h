//
//  SCMyorderListCelltypeGHPay.h
//  SCHCPatient
//
//  Created by wanda on 16/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCMyOrderModel.h"
typedef void(^OrderStatusButtonBlock)(SCMyOrderModel*model);
@interface SCMyorderListCelltypeGHPay : UITableViewCell
@property (nonatomic,strong) SCMyOrderModel         *orderModel;
@property (nonatomic,strong) UIButton               *statusButton;
@property (nonatomic,strong) UIButton               *payButton;
@property (nonatomic, copy ) OrderStatusButtonBlock statusButtonBlock;
@property (nonatomic,strong) UILabel                *hospitalNameLabel;
@property (nonatomic,strong) UILabel                *orderStateLabel;
@property (nonatomic,strong) UILabel                *keshiLabel;
//@property (nonatomic,strong) UILabel                *doctorNameLabel;
//@property (nonatomic,strong) UILabel                *menzhenLabel;
@property (nonatomic,strong) UILabel                *timeLabel;
@property (nonatomic,strong) UILabel                *moneyLabel;
@property (nonatomic,strong) UILabel                *peopleLabel;
@property (nonatomic,strong) UILabel                *orderNumberLabel;
@property (nonatomic,strong) UILabel                *orderstateLb;
@end
