//
//  ReferralDetailCell.h
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ReferralDetailReferralModel.h"
@interface ReferralDetailCell : UITableViewCell
@property (strong, nonatomic) UILabel * topBlankLabel;          //上方空白
@property (strong, nonatomic) UIImageView * typeImageView;      //类型图标（上转，下转）
@property (strong, nonatomic) UILabel * typeLabel;              //类型
@property (strong, nonatomic) UILabel * referralStatusLabel;    //转诊状态
@property (strong, nonatomic) UILabel * urgencyLabel;           //紧急程度
@property (strong, nonatomic) UILabel * timeLabel;              //时间
@property (strong, nonatomic) UILabel * topLineLabel;

@property (strong, nonatomic) UILabel * outTitleLabel;
@property (strong, nonatomic) UILabel * inTittlelabel;
@property (strong, nonatomic) UILabel * outHospitalLabel;
@property (strong, nonatomic) UILabel * inHospitalLabel;
@property (strong, nonatomic) UILabel * centerLineLabel;

@property (strong, nonatomic) UIView * detailContentView;
@property (strong, nonatomic) UILabel * reasonTitleLabel;
@property (strong, nonatomic) UILabel * reasonDetailLabel;      //转诊原因
@property (strong, nonatomic) UILabel * checkTitleLabel;
@property (strong, nonatomic) UILabel * checkDetailLabel;       //初步诊断
@property (strong, nonatomic) UILabel * historyTitleLabel;
@property (strong, nonatomic) UILabel * historyDetailLabel;     //病史摘要
@property (strong, nonatomic) UILabel * infoTitleLabel;
@property (strong, nonatomic) UILabel * infoDetailLabel;        //主要既往史
@property (strong, nonatomic) UILabel * cureTitleLabel;
@property (strong, nonatomic) UILabel * cureDetailLabel;        //治疗情况
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


/**
 设置数据
 */
- (void)setData:(ReferralDetailReferralModel *)data;
@end
