//
//  ReportNoticeCell.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCSystemListModel.h"
@interface SCSystemListCell : UITableViewCell
@property(nonatomic,strong) SCSystemListModel * model;
@property(nonatomic ,strong) UILabel *dateLabel;
@property(nonatomic ,strong) UILabel * titleLabel;
@property(nonatomic ,strong) UIImageView * picView;
@property(nonatomic ,strong) UILabel * desLabel;
@property(nonatomic ,strong) UIImageView *arroImageview;
@property(nonatomic ,strong) UIView * bgView;
@property(nonatomic ,strong) UILabel * openLinkLabel;
@property(nonatomic,strong) UIView * lineviewbottom;
@property(nonatomic ,strong) UIView * lineView;

@end
