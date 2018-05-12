//
//  SCPrescriptionPayTableViewCell.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCMyOrderModel.h"

typedef void(^doPayBlock)(SCMyOrderModel *order);

@interface SCPrescriptionPayTableViewCell : UITableViewCell

@property (nonatomic, strong) doPayBlock doPayBlock;
@property (nonatomic, strong) SCMyOrderModel *order;

@end
