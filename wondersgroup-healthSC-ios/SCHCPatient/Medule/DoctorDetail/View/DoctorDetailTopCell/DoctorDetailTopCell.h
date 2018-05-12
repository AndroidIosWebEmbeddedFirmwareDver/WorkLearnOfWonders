//
//  DoctorDetailTopCell.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DoctorDetailModel.h"

typedef void(^MoreButtonAction)();

@interface DoctorDetailTopCell : UITableViewCell

@property (nonatomic, strong) DoctorDetailModel *cellModel;

@property (nonatomic, copy) MoreButtonAction moreButtonBlock;

@end
