//
//  AttentionDoctorCell.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MyAttentionDoctorModel.h"


typedef void(^RegisterButtonBlock)(void);

@interface AttentionDoctorCell : UITableViewCell

@property (nonatomic, strong) MyAttentionDoctorModel *cellModel;

@end
