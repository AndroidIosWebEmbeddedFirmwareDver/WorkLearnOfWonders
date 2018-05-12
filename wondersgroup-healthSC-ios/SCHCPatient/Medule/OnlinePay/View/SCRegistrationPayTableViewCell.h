//
//  SCRegistrationPayTableViewCell.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCMyOrderModel.h"

typedef void(^doPay)(SCMyOrderModel *order);

@interface SCRegistrationPayTableViewCell : UITableViewCell

@property (nonatomic, strong) doPay doPayBlock;
@property (nonatomic, strong) SCMyOrderModel *order;

@end
