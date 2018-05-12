//
//  ReferralDetailCell.h
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ReferralDetailCell : UITableViewCell
@property (strong, nonatomic) UILabel * topBlankLabel;
@property (strong, nonatomic) UIImageView * typeImageView;
@property (strong, nonatomic) UILabel * typeLabel;
@property (strong, nonatomic) UILabel * timeLabel;
@property (strong, nonatomic) UILabel * topLineLabel;

@property (strong, nonatomic) UILabel * outTitleLabel;
@property (strong, nonatomic) UILabel * inTittlelabel;
@property (strong, nonatomic) UILabel * outHospitalLabel;
@property (strong, nonatomic) UILabel * inHospitalLabel;
@property (strong, nonatomic) UILabel * centerLineLabel;

@property (strong, nonatomic) UIView * detailContentView;
@property (strong, nonatomic) UILabel * reasonTitleLabel;
@property (strong, nonatomic) UILabel * reasonDetailLabel;
@property (strong, nonatomic) UILabel * checkTitleLabel;
@property (strong, nonatomic) UILabel * checkDetailLabel;
@property (strong, nonatomic) UILabel * historyTitleLabel;
@property (strong, nonatomic) UILabel * historyDetailLabel;
@property (strong, nonatomic) UILabel * infoTitleLabel;
@property (strong, nonatomic) UILabel * infoDetailLabel;
@property (strong, nonatomic) UILabel * cureTitleLabel;
@property (strong, nonatomic) UILabel * cureDetailLabel;
@property (strong, nonatomic) UILabel * bottomLineLabel;

@property (strong, nonatomic) UIButton * detailButton;          //查看详情
@property (strong, nonatomic) UIButton * registrationInfo;      //挂号信息

/**
 是否展示详情
 */
- (void)setShowDetail:(BOOL)showDetail;


/**
 是否显示挂号信息按钮
 */
- (void)showRegistrationInfoButton:(BOOL)showRegistrationButton;


/**
 点击展示详情
 */
- (void)setShowDetailBlock:(void(^)(UITableViewCell * cell))block;


/**
 点击挂号信息
 */
- (void)setClickInfoButton:(void(^)(UITableViewCell * cell))block;
@end
