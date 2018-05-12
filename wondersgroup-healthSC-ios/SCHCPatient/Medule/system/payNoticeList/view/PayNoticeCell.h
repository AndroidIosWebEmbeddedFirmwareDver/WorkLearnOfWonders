//
//  PayNoticeCell.h
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "PayNoticeModel.h"
@interface PayNoticeCell : UITableViewCell
@property(nonatomic ,strong) PayNoticeModel * model;
@property(nonatomic ,strong) UILabel * noticeDateLabel;
@property(nonatomic ,strong) UIView  * bgview;
@property(nonatomic ,strong) UILabel * title;
@property(nonatomic ,strong) UILabel * hospitalNameLabel;
@property(nonatomic ,strong) UILabel * patientNameLabel;
@property(nonatomic ,strong) UILabel * departLabel;
@property(nonatomic ,strong) UILabel * dateLabel;
@property(nonatomic ,strong) UILabel * payLabel;
@property(nonatomic ,strong) UILabel * payStatusLabel;
@property(nonatomic ,strong) UILabel * oprationLabel;
@property(nonatomic ,strong)  UIView * endline;
@property(nonatomic ,strong) UIImageView  * arroImageview;

@end
