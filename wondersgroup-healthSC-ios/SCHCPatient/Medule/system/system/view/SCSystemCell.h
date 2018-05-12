//
//  SystemCell.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCSystemModel.h"
@interface SCSystemCell : UITableViewCell
@property(nonatomic ,strong) UIImageView * iconImageView;
@property(nonatomic ,strong) UILabel * messageCountLabel;
@property(nonatomic ,strong) UILabel * titleLable;
@property(nonatomic ,strong) UILabel * contentLable;
@property(nonatomic ,strong) SCSystemModel * model;
@property(nonatomic ,strong) UILabel * datelabel;

@property(nonatomic ,strong) UIView * topLine;
@property(nonatomic ,strong) UIView * endLine;

@property(nonatomic ,assign) NSInteger  objectCount;
@property(nonatomic ,assign) NSInteger  index;

@end
