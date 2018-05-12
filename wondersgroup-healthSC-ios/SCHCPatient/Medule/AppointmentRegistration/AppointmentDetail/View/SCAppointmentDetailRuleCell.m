//
//  SCAppointmentDetailRuleCell.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDetailRuleCell.h"

@interface SCAppointmentDetailRuleCell ()

@property (nonatomic, strong) UILabel *titleLabel;
@property (nonatomic, strong) UILabel *explainLabel;

@end

@implementation SCAppointmentDetailRuleCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        [self setupView];
        [self bindViewModel];
    }
    return self;
}

- (void)setupView {
    self.backgroundColor = [UIColor bc1Color];
    self.contentView.backgroundColor = [UIColor bc1Color];
    
    WS(weakSelf)
    self.titleLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"" withTextColor:[UIColor tc5Color] withFontSize:12];
    self.titleLabel.numberOfLines = 0;
    [self.titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.top.equalTo(weakSelf.contentView).offset(15);
    }];

    NSMutableAttributedString *str = [[NSMutableAttributedString alloc] initWithString:@"费用标准由医院自行设立，平台不收取任何额外费用"];
    [str addAttribute:NSForegroundColorAttributeName value:[UIColor tc3Color] range:NSMakeRange(0,12)];
    [str addAttribute:NSForegroundColorAttributeName value:[UIColor tc1Color] range:NSMakeRange(12,11)];
    self.titleLabel.attributedText = str;
    
    self.explainLabel = [UISetupView setupLabelWithSuperView:self.contentView withText:@"我已了解并同意" withTextColor:[UIColor tc3Color] withFontSize:12];
    self.explainLabel.numberOfLines = 0;
    [self.explainLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(weakSelf.titleLabel.mas_bottom).offset(10);
    }];
    
    UIButton *tipsBtn = [UISetupView setupButtonWithSuperView:self.contentView withTitleToStateNormal:@"《微健康平台预约挂号规则》" withTitleColorToStateNormal:[UIColor tc5Color] withTitleFontSize:12 withAction:^(UIButton *sender) {
        
        [[BFRouter router] open:[TaskManager manager].appConfig.common.appointmentRule];

    }];
    [tipsBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.explainLabel.mas_right);
        make.centerY.equalTo(weakSelf.explainLabel);
    }];
    
}

-(void)bindViewModel {
//    WS(weakSelf)

    
}


+(CGFloat)cellHeight {
    CGFloat height = 15;
    
    CGFloat width = SCREEN_WIDTH-15-15;
    CGSize size = [Utility heightWithAttributesWithFont:[UIFont systemFontOfSize:12] withText:@"费用标准由医院自行设立，平台不收取任何额外费用" withWidth:width withHeight:MAXFLOAT];
    height += size.height+1;
    height += 10;
    
    width = SCREEN_WIDTH-15-15-14-5;
    size = [Utility heightWithAttributesWithFont:[UIFont systemFontOfSize:12] withText:@"我已了解并同意 《微健康平台预约挂号规则》" withWidth:width withHeight:MAXFLOAT];
    height +=size.height+1;
    height += 10;

    return height;
}

@end
