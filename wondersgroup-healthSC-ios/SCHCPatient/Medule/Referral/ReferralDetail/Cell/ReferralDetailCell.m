//
//  ReferralDetailCell.m
//  SCHCPatient
//
//  Created by Po on 2017/6/2.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "ReferralDetailCell.h"
#import "UIButton+RJButton.h"

typedef void(^ReferralShowDetailBlock)(UITableViewCell * cell);
typedef void(^ClickInfoBlock)(UITableViewCell * cell);

@interface ReferralDetailCell()
@property (strong, nonatomic) NSArray * pointLabels;                    //详情圆点

@property (copy, nonatomic) ReferralShowDetailBlock showDetailBlock;    //点击展示详情回调
@property (copy, nonatomic) ClickInfoBlock clickInfoBlock;              //点击挂号信息回调
@end

@implementation ReferralDetailCell

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
    [self.contentView setBackgroundColor:[UIColor whiteColor]];
    _topBlankLabel = [[UILabel alloc] init];
    [_topBlankLabel setBackgroundColor:RGB_COLOR(244, 244, 244)];
    [self.contentView addSubview:_topBlankLabel];
    
    _typeImageView = [[UIImageView alloc] init];
    _typeLabel = [[UILabel alloc] init];
    [_typeLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];
    [_typeLabel setTextColor:[UIColor tc1Color]];
    _timeLabel = [[UILabel alloc] init];
    [_timeLabel setTextColor:RGB_COLOR(153, 153, 153)];
    [_timeLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:12]];
    _topLineLabel = [[UILabel alloc] init];
    
    
    _outTitleLabel = [self getTitleLabel];
    [_outTitleLabel setText:@"转入:"];
    [_outTitleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    _inTittlelabel = [self getTitleLabel];
    [_inTittlelabel  setText:@"转出:"];
    [_inTittlelabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
    _outHospitalLabel = [self getDetailLabel];
    [_outHospitalLabel setTextColor:[UIColor tc1Color]];
    _inHospitalLabel = [self getDetailLabel];
    [_inHospitalLabel setTextColor:[UIColor tc1Color]];
    _centerLineLabel = [[UILabel alloc] init];
    
    _detailContentView = [[UIView alloc] init];
    [_detailContentView setBackgroundColor:[UIColor whiteColor]];
    _reasonTitleLabel = [self getTitleLabel];
    [_reasonTitleLabel setText:@"转诊原因"];
    _reasonDetailLabel = [self getDetailLabel];
    _checkTitleLabel = [self getTitleLabel];
    [_checkTitleLabel setText:@"初步诊断"];
    _checkDetailLabel = [self getDetailLabel];
    _historyTitleLabel = [self getTitleLabel];
    [_historyTitleLabel setText:@"病史摘要"];
    _historyDetailLabel = [self getDetailLabel];
    _infoTitleLabel = [self getTitleLabel];
    [_infoTitleLabel setText:@"主要既往史"];
    _infoDetailLabel = [self getDetailLabel];
    _cureTitleLabel = [self getTitleLabel];
    [_cureTitleLabel setText:@"治疗情况"];
    _cureDetailLabel = [self getDetailLabel];
    _bottomLineLabel = [self getDetailLabel];
    
    _detailButton = [[UIButton alloc] init];
    [_detailButton addTarget:self action:@selector(pressDetailButton:) forControlEvents:UIControlEventTouchUpInside];
    [_detailButton setTitle:@"查看详情" forState:UIControlStateNormal];
    [_detailButton setImage:[UIImage imageNamed:@"link_down"] forState:UIControlStateNormal];
    [_detailButton setTitle:@"收起详情" forState:UIControlStateSelected];
    [_detailButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:13]];
    [_detailButton setTitleColor:RGB_COLOR(153, 153, 153) forState:UIControlStateNormal];
    //    [_detailButton rj_leftTextRightImage];
    
    
    _registrationInfo = [[UIButton alloc] init];
    [_registrationInfo setTitle:@"转诊信息" forState:UIControlStateNormal];
    [_registrationInfo setBackgroundColor:RGB_COLOR(46, 122, 240)];
    [_registrationInfo.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Light" size:14]];
    [self.contentView addSubview:_registrationInfo];
    //线条背景颜色
    for (UILabel * label in @[_topLineLabel, _centerLineLabel, _bottomLineLabel]) {
        [label setBackgroundColor:RGB_COLOR(240, 240, 240)];
    }
    
    NSArray * views = @[_typeImageView,_typeLabel,_timeLabel,_topLineLabel,_outTitleLabel,_inTittlelabel,_outHospitalLabel,_inHospitalLabel,_centerLineLabel,_detailButton,_detailContentView];
    for (UIView * view in views) {
        [self.contentView addSubview:view];
    }
    
    NSArray * detailLabels = @[_reasonTitleLabel,_reasonDetailLabel,_checkTitleLabel,_checkDetailLabel,_historyTitleLabel,_historyDetailLabel,_infoTitleLabel,_infoDetailLabel,_cureTitleLabel,_cureDetailLabel,_bottomLineLabel];
    
    NSMutableArray * temp = [[NSMutableArray alloc] init];
    for (NSInteger i = 0; i < detailLabels.count ; i ++) {
        UILabel * label = detailLabels[i];
        [_detailContentView addSubview:label];
        if (i == detailLabels.count - 1) {
            break;
        }
        
        [label setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        if (i % 2) {
            [label setTextColor:RGB_COLOR(153, 153, 153)];
            //圆点
            UILabel * label = [[UILabel alloc] init];
            [label.layer setCornerRadius:2];
            [label setClipsToBounds:YES];
            [label setBackgroundColor:RGB_COLOR(153, 153, 153)];
            [temp addObject:label];
            [_detailContentView addSubview:label];
        } else {
            
            [label setTextColor:RGB_COLOR(102, 102, 102)];
        }
    }
    _pointLabels = [NSArray arrayWithArray:temp];
}

- (void)buildConstraint {
    [_topBlankLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.contentView);
        make.height.mas_equalTo(10);
    }];
    
    [_typeImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.mas_equalTo(_topBlankLabel.bottom).offset(15);
        make.left.mas_equalTo(15);
        make.width.height.mas_equalTo(16);
    }];
    
    [_timeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(_typeImageView);
        make.right.equalTo(self.contentView).offset(-10);
        make.width.mas_greaterThanOrEqualTo(50);
    }];
    
    [_typeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_typeImageView.mas_right).offset(7);
        make.top.bottom.equalTo(_typeImageView);
        make.right.mas_lessThanOrEqualTo(_timeLabel.left);
        make.width.mas_greaterThanOrEqualTo(50);
    }];
    
    [_topLineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_typeImageView);
        make.height.mas_equalTo(1);
        make.top.equalTo(_typeImageView.mas_bottom).offset(10);
        make.right.equalTo(self.contentView);
    }];
    
    [_registrationInfo mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_topLineLabel.mas_bottom).offset(22);
        make.right.equalTo(_timeLabel);
        make.width.mas_equalTo(72);
        make.height.mas_equalTo(30);
    }];
    
    [_inTittlelabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_typeImageView);
        make.top.equalTo(_topLineLabel.mas_bottom).offset(10);
        make.width.mas_equalTo(40);
        make.height.mas_equalTo(14);
    }];
    
    [_inHospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_inTittlelabel.mas_right);
        make.top.equalTo(_inTittlelabel);
        make.right.equalTo(_timeLabel).priorityMedium();
        make.height.mas_greaterThanOrEqualTo(_inTittlelabel);
    }];
    
    [_outHospitalLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(_inHospitalLabel);
        make.top.equalTo(_inHospitalLabel.mas_bottom).offset(10);
        make.height.mas_greaterThanOrEqualTo(_inTittlelabel);
    }];
    
    [_outTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.height.equalTo(_inTittlelabel);
        make.top.equalTo(_outHospitalLabel);
    }];
    
    [_centerLineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.height.equalTo(_topLineLabel);
        make.top.equalTo(_outHospitalLabel.mas_bottom).offset(20);
    }];
    
    //详情
    [_detailContentView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.contentView);
        make.top.equalTo(_centerLineLabel.mas_bottom);
        //        make.height.mas_equalTo(0);
    }];
    
    [_reasonTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_detailContentView).offset(20);
        make.left.equalTo(_detailContentView).offset(27);
        make.width.mas_equalTo(80);
        make.height.mas_equalTo(12);
    }];
    
    [_reasonDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_reasonTitleLabel);
        make.left.equalTo(_reasonTitleLabel.mas_right).offset(5);
        make.right.equalTo(self.contentView).offset(-20);
        make.height.mas_greaterThanOrEqualTo(_reasonTitleLabel);
    }];
    
    UILabel * pointLabel = _pointLabels[0];
    [pointLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(_reasonTitleLabel);
        make.right.equalTo(_reasonTitleLabel.mas_left).offset(-5);
        make.width.height.mas_equalTo(4);
    }];
    
    NSArray * detailLabels = @[_reasonTitleLabel ,_reasonDetailLabel,_checkTitleLabel,_checkDetailLabel,_historyTitleLabel,_historyDetailLabel,_infoTitleLabel,_infoDetailLabel,_cureTitleLabel,_cureDetailLabel];
    for (NSInteger i = 2; i < detailLabels.count; i += 2) {
        UILabel * titleLabel = detailLabels[i];
        UILabel * detailLabel = detailLabels[i + 1];
        UILabel * lastDetailLabel = detailLabels[i - 1];
        //详情label
        [detailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(lastDetailLabel.mas_bottom).offset(20);
            make.left.right.equalTo(lastDetailLabel);
            make.height.mas_greaterThanOrEqualTo(_reasonTitleLabel);
        }];
        
        //标题label
        [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(detailLabel);
            make.left.height.right.equalTo(_reasonTitleLabel);
        }];
        
        //圆点
        UILabel * point = _pointLabels[i / 2];
        [point mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerY.equalTo(titleLabel);
            make.right.equalTo(titleLabel.mas_left).offset(-5);
            make.width.height.mas_equalTo(4);
        }];
        
    }
    
    [_bottomLineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_detailContentView).mas_equalTo(15);
        make.right.equalTo(_detailContentView);
        make.height.mas_equalTo(1);
        make.top.equalTo(_cureDetailLabel.mas_bottom).offset(20);
        make.bottom.equalTo(_detailContentView);
    }];
    
    [_detailButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_centerLineLabel.mas_bottom);
        make.left.bottom.right.equalTo(self.contentView);
        make.height.mas_equalTo(50).priorityMedium();
    }];
}

#pragma mark - event
- (void)pressDetailButton:(UIButton *)sender {
    sender.selected = !sender.selected;
    if(_showDetailBlock) {
        __weak typeof(self) weakSelf = self;
        _showDetailBlock(weakSelf);
    }
}

- (void)pressInfoButton:(UIButton *)sender {
    if (_clickInfoBlock) {
        MJWeakSelf
        _clickInfoBlock(weakSelf);
    }
}

#pragma mark - function
- (void)setShowDetail:(BOOL)showDetail {
    CGFloat alpha = showDetail ? 1 : 0;
    [_detailButton mas_remakeConstraints:^(MASConstraintMaker *make) {
        
        make.leftMargin.bottomMargin.rightMargin.mas_equalTo(0);
        if (showDetail) {
            make.top.equalTo(_detailContentView.mas_bottom);
        } else {
            make.top.equalTo(_centerLineLabel.mas_bottom);
        }
        make.height.mas_equalTo(50);
    }];
    [_detailContentView setAlpha:alpha];
    [_detailButton setSelected:showDetail];
}

- (void)showRegistrationInfoButton:(BOOL)showRegistrationButton {
    
    [_registrationInfo setHidden:!showRegistrationButton];
    [_inHospitalLabel mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_inTittlelabel.mas_right);
        make.top.equalTo(_inTittlelabel);
        if (showRegistrationButton) {
            make.right.equalTo(_registrationInfo.mas_left).offset(-5);
        } else {
            make.right.equalTo(_timeLabel);
        }
        make.height.mas_greaterThanOrEqualTo(_inTittlelabel);
    }];
}

#pragma mark - getter
- (UILabel *)getTitleLabel {
    UILabel * label = [self getCommonLabel];
    [label setTextColor:[UIColor tc1Color]];
    return label;
}

- (UILabel *)getDetailLabel {
    UILabel * label = [self getCommonLabel];
    [label setTextColor:RGB_COLOR(32, 32, 32)];
    [label setNumberOfLines:0];
    return label;
}

- (UILabel *)getCommonLabel {
    UILabel * label = [[UILabel alloc] init];
    [label setTextAlignment:NSTextAlignmentLeft];
    [label setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:12]];
    return label;
}

#pragma mark - setter
- (void)setShowDetailBlock:(void(^)(UITableViewCell * cell))block {
    _showDetailBlock = block;
}

- (void)setClickInfoButton:(void(^)(UITableViewCell * cell))block {
    _clickInfoBlock = block;
}
@end
