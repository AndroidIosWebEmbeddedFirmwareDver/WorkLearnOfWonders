//
//  SCSettingAccountCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCSettingModel.h"

@interface SCSettingAccountCell : UITableViewCell

@property (nonatomic, strong) SCSettingModel *model;
@property (nonatomic, assign) BOOL isLast;

@end
