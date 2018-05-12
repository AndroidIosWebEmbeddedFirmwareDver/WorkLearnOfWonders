//
//  HealthResultTableViewCell.h
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SCHealthResultTableViewCell : UITableViewCell
@property (strong, nonatomic  ) UIImageView *imageIcon;
@property (strong, nonatomic  ) UILabel     *titleLabel;
@property (strong, nonatomic  ) UIView      *grayView;
- (void)addViewAndMessage:(NSString *)content title:(NSString *)title image:(NSString *)image inter:(NSInteger)inter;
+ (CGFloat)calculateCellHeightWithMessage:(NSString *)str;
- (void)addgrayView;
@end
