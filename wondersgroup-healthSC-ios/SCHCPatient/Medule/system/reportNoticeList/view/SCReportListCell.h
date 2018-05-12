//
//  SystemDetialCell.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCReportListModel.h"
@interface SCReportListCell : UITableViewCell

@property(nonatomic ,strong) SCReportListModel * model;



@property(nonatomic ,strong) UILabel * dateLabel;
@property(nonatomic ,strong) UIView * bgView;
@property(nonatomic ,strong) UILabel * titleLabel;
@property(nonatomic ,strong) UILabel * nameLabel;
@property(nonatomic ,strong) UILabel * sexLabel;
@property(nonatomic ,strong) UILabel * ageLabel;
@property(nonatomic ,strong) UILabel * hospitalNameLabel;
@property(nonatomic ,strong) UILabel * checkProjectLabel;


@property(nonatomic ,strong) UILabel * checkPartLabel;
@property(nonatomic ,strong) UILabel * departmentLable;
@property(nonatomic ,strong) UILabel * checkDateLabel;
@property(nonatomic ,strong) UILabel * reportDateLabel;




@end
