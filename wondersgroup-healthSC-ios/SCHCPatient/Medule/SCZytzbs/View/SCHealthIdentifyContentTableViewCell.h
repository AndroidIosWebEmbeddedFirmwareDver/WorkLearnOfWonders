//
//  HealthIdentifyContentTableViewCell.h
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SCHealthIdentifyContentTableViewCell : UITableViewCell
@property (strong, nonatomic)  UIView *baseView;
@property (strong, nonatomic)  UIImageView *imageSelect;
@property (strong, nonatomic)  UILabel *contentLabel;

- (void)creatMessageOnViewMessage:(NSString *)str;

@end
