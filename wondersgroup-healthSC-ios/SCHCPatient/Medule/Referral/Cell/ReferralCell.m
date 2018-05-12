//
//  ReferralCell.m
//  SCHCPatient
//
//  Created by Po on 2017/6/1.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "ReferralCell.h"

@implementation ReferralCell

- (void)awakeFromNib {
    [super awakeFromNib];
    
}

- (void)setData:(ReferralModel *)data {
    [_typeImageView setImage:[UIImage imageNamed:@"下转"]];
}

@end
