//
//  ReferralCell.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralCell.h"

#import "UIImageView+RJImageView.h"
@interface ReferralCell ()



@property (assign, nonatomic) CGFloat imageViewSize;
@end

@implementation ReferralCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self initData];
        [self initInterface];
        [self buildConstraint];
    }
    return self;
}

#pragma mark - user-define initialization
- (void)initData {
    _imageViewSize = 45;
}

- (void)initInterface {
    _userImageView = [[UIImageView alloc] init];
    [_userImageView setBackgroundColor:[UIColor redColor]];
    //圆角
    CGRect rect = CGRectMake(0, 0, _imageViewSize, _imageViewSize);
    [_userImageView rj_setCornerRadiusWithBounds:rect];
    
    _userNameLabel = [[UILabel alloc] init];
    [_userNameLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];
    [_userNameLabel setTextColor:RGB_COLOR(51, 51, 51)];
    [_userNameLabel setText:@"123"];
    
    _typeLabel = [[UILabel alloc] init];
    [_typeLabel setFont:[UIFont fontWithName:@"PingFangSC-Light" size:12]];
    [_typeLabel setTextAlignment:NSTextAlignmentCenter];
    
    _timeLabel = [[UILabel alloc] init];
    [_timeLabel setText:@"xxxx-xx-xx"];
    [_timeLabel setTextColor:RGB_COLOR(153, 153, 153)];
    [_timeLabel setFont:[UIFont systemFontOfSize:12]];
    
    _inHospitalTitleLabel = [[UILabel alloc] init];
    [_inHospitalTitleLabel setText:@"转入"];
    _inHospitalLabel = [[UILabel alloc] init];
    [_inHospitalLabel setText:@"sdfasdfasfsafdsaasdfasdfasfsafdsaasdfasdfasfsafdsaasdfasdfasfsafdsaasdf"];
    _outHospitalTitleLabel = [[UILabel alloc] init];
    [_outHospitalTitleLabel setText:@"转出"];
    _outHospitalLabel = [[UILabel alloc] init];
    [_outHospitalLabel setText:@"呵呵234"];
    for (UILabel * label in @[_inHospitalTitleLabel, _inHospitalLabel, _outHospitalTitleLabel, _outHospitalLabel]) {
        [label setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:12]];
        [label setTextColor:RGB_COLOR(153, 153, 153)];
    }
    
    
    _progressLabel = [[UILabel alloc] init];
    [_progressLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    
    NSArray * allViews = @[_userImageView, _userNameLabel, _typeLabel, _timeLabel, _inHospitalLabel, _inHospitalTitleLabel, _outHospitalLabel, _outHospitalTitleLabel, _progressLabel];
    for (UIView * view in allViews) {
        [self.contentView addSubview:view];
    }
}

- (void)buildConstraint {
    [_userImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.equalTo(self.contentView).offset(15);
        make.width.height.mas_equalTo(_imageViewSize);
    }];
    
    [_timeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.contentView).offset(17);
        make.right.equalTo(self.contentView).offset(-15);
        make.height.mas_equalTo(12);
        make.width.mas_greaterThanOrEqualTo(50);
    }];
    
    [_userNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_userImageView.mas_right).offset(15);
        make.top.equalTo(_userImageView);
        make.height.mas_equalTo(16);
    }];
    
    [_typeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(_userNameLabel);
        make.left.equalTo(_userNameLabel.mas_right).offset(5);
        make.right.lessThanOrEqualTo(_timeLabel.mas_left);
    }];
    
    [_inHospitalTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_userNameLabel);
        make.top.equalTo(_userNameLabel.mas_bottom).offset(10);
        make.height.mas_equalTo(12);
        make.width.mas_greaterThanOrEqualTo(36);
    }];
    
    [_inHospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_inHospitalTitleLabel.mas_right).offset(5);
        make.top.bottom.equalTo(_inHospitalTitleLabel);
        make.right.equalTo(_timeLabel);
    }];
    
    [_outHospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(_inHospitalLabel);
        make.top.equalTo(_inHospitalLabel.mas_bottom).offset(10);
        make.bottom.equalTo(_outHospitalTitleLabel);
        make.bottom.equalTo(self.contentView).mas_offset(-15);
    }];
    
    [_outHospitalTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_outHospitalLabel);
        make.left.right.height.equalTo(_inHospitalTitleLabel);
    }];
}

- (void)doRequest {
    
}

#pragma mark - event

#pragma mark - function
- (void)showProgress:(ReferralProgress)type {
    [self setProgressType:type];
    
    [_progressLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_outHospitalLabel);
        make.right.equalTo(_timeLabel);
        make.height.mas_equalTo(14);
        CGFloat width = type == ReferralProgressNone ? 0 : 50;
        make.width.mas_equalTo(width);
    }];
    
    [_outHospitalLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(_progressLabel.mas_left).offset(-5);
        make.left.equalTo(_inHospitalLabel);
        make.top.equalTo(_inHospitalLabel.mas_bottom).offset(10);
        make.bottom.equalTo(_outHospitalTitleLabel);
        make.bottom.equalTo(self.contentView).mas_offset(-15);
    }];
}

#pragma mark - delegate

#pragma mark - notification

#pragma mark - setter
- (void)setProgressType:(ReferralProgress)type {
    NSString * typeString = @"";
    UIColor * textColor = [UIColor whiteColor];
    switch (type) {
        case ReferralProgressNone:
            break;
        case ReferralProgressSuccess:
            typeString = @"已转诊";
            textColor = RGB_COLOR(142, 212, 8);
            break;
        case ReferralProgressRequest:
            typeString = @"申请中";
            textColor = RGB_COLOR(255, 102, 94);
            break;
        default:
            typeString = @"已驳回";
            textColor = RGB_COLOR(102, 102, 102);
            break;
    }
    [_progressLabel setText:typeString];
    [_progressLabel setTextColor:textColor];
}


- (void)setPriority:(ReferralPriority)type {
    NSString * typeString = @"";
    UIColor * color = [UIColor clearColor];
    CGFloat width = type == ReferralPriorityNone ? 0 : 35;
    switch (type) {
        case ReferralPriorityHigh:
            typeString = @"紧急";
            color = RGB_COLOR(245, 103, 53);
            break;
        case ReferralPriorityMedieam:
            typeString = @"一般";
            color = RGB_COLOR(0, 151, 17);
            break;
        default:
            break;
    }
    
    [_typeLabel setText:typeString];
    [_typeLabel setTextColor:color];
    [_typeLabel.layer setBorderWidth:type == ReferralPriorityNone ? 0 : 1];
    [_typeLabel.layer setCornerRadius:8];
    [_typeLabel.layer setBorderColor:color.CGColor];
    
    [_typeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(_userNameLabel);
        make.left.equalTo(_userNameLabel.mas_right).offset(5);
        make.right.lessThanOrEqualTo(_timeLabel.mas_left);
        make.width.mas_greaterThanOrEqualTo(width).priorityLow();
    }];
    
    
}
#pragma mark - getter


@end
