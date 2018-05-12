//
//  ReferralDetailInfoCell.h
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ReferralDetailInfoCell : UITableViewCell
@property (strong, nonatomic) UILabel * titleLabel;
@property (strong, nonatomic) UILabel * detailLabel;

- (void)showTopBlank:(BOOL)showBlank;
@end
