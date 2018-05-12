//
//  SCInhospitalPayTableViewCell.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCInhospitalPayTableViewCell.h"

@interface SCInhospitalPayTableViewCell ()

@property (weak, nonatomic) IBOutlet UIView *topView;
@property (weak, nonatomic) IBOutlet UILabel *payStatus;
@property (weak, nonatomic) IBOutlet UIView *line1;
@property (weak, nonatomic) IBOutlet UILabel *hospitalName;
@property (weak, nonatomic) IBOutlet UILabel *priceLabel;
@property (weak, nonatomic) IBOutlet UILabel *inhospitalIdLabel;
@property (weak, nonatomic) IBOutlet UIView *line2;
@property (weak, nonatomic) IBOutlet UILabel *orderIdLabel;
@property (weak, nonatomic) IBOutlet UIButton *doPayButton;


@end

@implementation SCInhospitalPayTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    
    [self setupView];
}

- (void)setupView {
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    
    _topView.backgroundColor = [UIColor bc2Color];
    
    _payStatus.font = [UIFont systemFontOfSize:14];
    
    _line1.backgroundColor = [UIColor bc3Color];
    
    _hospitalName.textColor = [UIColor tc1Color];
    _hospitalName.font = [UIFont systemFontOfSize:16];
    
    _priceLabel.textColor = [UIColor tc1Color];
    _priceLabel.font = [UIFont systemFontOfSize:16];
    
    _inhospitalIdLabel.textColor = [UIColor tc2Color];
    _inhospitalIdLabel.font = [UIFont systemFontOfSize:16];
    
    _line2.backgroundColor = [UIColor bc3Color];
    
    _orderIdLabel.textColor = [UIColor tc2Color];
    _orderIdLabel.font = [UIFont systemFontOfSize:12];
    
    _doPayButton.backgroundColor = [UIColor sbc1Color];
    _doPayButton.layer.masksToBounds = YES;
    _doPayButton.layer.cornerRadius = 3;
    [_doPayButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
    _doPayButton.titleLabel.font = [UIFont systemFontOfSize:14];
}

@end
