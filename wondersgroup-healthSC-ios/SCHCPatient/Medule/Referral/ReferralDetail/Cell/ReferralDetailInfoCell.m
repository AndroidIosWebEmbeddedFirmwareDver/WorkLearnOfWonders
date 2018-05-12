//
//  ReferralDetailInfoCell.m
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "ReferralDetailInfoCell.h"

@interface ReferralDetailInfoCell ()

@property (strong, nonatomic) UILabel * topBlankLabel;              //上方空白
@property (strong, nonatomic) UILabel * bottomLineLabel;            //下方线条

@end

@implementation ReferralDetailInfoCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setupView];
        [self buildConstraint];
    }
    return self;
}

- (void)setupView {
    _topBlankLabel = [[UILabel alloc] init];
    [_topBlankLabel setBackgroundColor:RGB_COLOR(244, 244, 244)];
    [self.contentView addSubview:_topBlankLabel];
    
    _titleLabel = [[UILabel alloc] init];
    [_titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    [_titleLabel setTextColor:RGB_COLOR(102, 102, 102)];
    [self.contentView addSubview:_titleLabel];
    
    _detailLabel = [[UILabel alloc] init];
    [_detailLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    [_detailLabel setTextColor:RGB_COLOR(51, 51, 51)];
    [_detailLabel setNumberOfLines:2];
    [_detailLabel setTextAlignment:NSTextAlignmentRight];
    [self.contentView addSubview:_detailLabel];
    
    _bottomLineLabel = [[UILabel alloc] init];
    [_bottomLineLabel setBackgroundColor:RGB_COLOR(244, 244, 244)];
    [self.contentView addSubview:_bottomLineLabel];
}

- (void)buildConstraint {
    [_topBlankLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.contentView);
        make.height.mas_equalTo(0);
    }];
    
    [_titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(10);
        make.top.equalTo(self.topBlankLabel.mas_bottom);
        make.bottom.equalTo(self.contentView);
        make.height.mas_equalTo(45).priorityHigh();
        make.width.mas_equalTo(80);
    }];
    
    [_detailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_titleLabel.mas_right);
        make.right.equalTo(self.contentView).offset(-10);
        make.top.bottom.equalTo(_titleLabel);
    }];
    
    [_bottomLineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.contentView).offset(10);
        make.bottom.right.equalTo(self.contentView);
        make.height.mas_equalTo(1);
    }];
}

- (void)showTopBlank:(BOOL)showBlank {
    [_topBlankLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.contentView);
        if (showBlank) {
            make.height.mas_equalTo(10);
        } else {
            make.height.mas_equalTo(0);
        }
    }];
}

















@end
