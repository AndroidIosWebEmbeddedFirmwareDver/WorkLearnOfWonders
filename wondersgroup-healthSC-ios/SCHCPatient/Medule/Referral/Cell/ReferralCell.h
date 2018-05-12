//
//  ReferralCell.h
//  SCHCPatient
//
//  Created by Po on 2017/6/1.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ReferralModel.h"
@interface ReferralCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIImageView *typeImageView;
@property (weak, nonatomic) IBOutlet UILabel *typeLabel;
@property (weak, nonatomic) IBOutlet UILabel *stateLabel;

@property (weak, nonatomic) IBOutlet UILabel *outHospitalLabel;
@property (weak, nonatomic) IBOutlet UILabel *inHospitalLabel;

@property (weak, nonatomic) IBOutlet UILabel *timeLabel;

- (void)setData:(ReferralModel *)data;
@end
