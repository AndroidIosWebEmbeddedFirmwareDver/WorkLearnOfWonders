//
//  SCCertificationTypeIDCardCell.h
//  SCHCPatient
//
//  Created by wanda on 16/11/9.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCTrueNameModel.h"
@interface SCCertificationTypeIDCardCell : UITableViewCell
@property (nonatomic,strong) SCTrueNameModel *model;
@property (nonatomic,strong) UILabel *idcardLabel;
@end
