//
//  HealthIdentifyTitleTableViewCell.h
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SCHealthIdentifyTitleTableViewCell : UITableViewCell
//@property (strong, nonatomic) UIProgressView *progressView;
//@property (strong, nonatomic) IBOutlet UILabel *titleLable;
@property (strong, nonatomic) UILabel        *contentLabel;
@property (strong, nonatomic) UILabel        *labelPrompt;

- (void)creatMessageOnView:(NSInteger)inter message:(NSString *)str total:(NSInteger)total;
- (void)creatMessage:(NSInteger)inter message:(NSString *)str total:(NSInteger)total;


@end
