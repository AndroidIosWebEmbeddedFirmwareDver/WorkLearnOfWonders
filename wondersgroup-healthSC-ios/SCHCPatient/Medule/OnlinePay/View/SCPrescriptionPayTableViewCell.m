//
//  SCPrescriptionPayTableViewCell.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCPrescriptionPayTableViewCell.h"

@interface SCPrescriptionPayTableViewCell ()

@property (weak, nonatomic) IBOutlet UIView *topView;
@property (weak, nonatomic) IBOutlet UILabel *payStatus;
@property (weak, nonatomic) IBOutlet UIView *line1;
@property (weak, nonatomic) IBOutlet UILabel *hospitalName;
@property (weak, nonatomic) IBOutlet UILabel *departmentLabel;
@property (weak, nonatomic) IBOutlet UILabel *priceLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;
@property (weak, nonatomic) IBOutlet UILabel *prescriptionIdLabel;
@property (weak, nonatomic) IBOutlet UIView *line2;
@property (weak, nonatomic) IBOutlet UILabel *orderIdLabel;
@property (weak, nonatomic) IBOutlet UIButton *doPayButton;


@end

@implementation SCPrescriptionPayTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    
    [self setupView];
    [self bindModel];
}

- (void)setupView {
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    
    _topView.backgroundColor = [UIColor bc2Color];
    
    _payStatus.font = [UIFont systemFontOfSize:14];
    
    _line1.backgroundColor = [UIColor bc3Color];
    
    _hospitalName.textColor = [UIColor tc1Color];
    _hospitalName.font = [UIFont systemFontOfSize:16];
    
    _departmentLabel.textColor = [UIColor tc1Color];
    _departmentLabel.font = [UIFont systemFontOfSize:16];
    
    _priceLabel.textColor = [UIColor tc1Color];
    _priceLabel.font = [UIFont systemFontOfSize:16];
    
    _timeLabel.textColor = [UIColor tc1Color];
    _timeLabel.font = [UIFont systemFontOfSize:16];
    
    _prescriptionIdLabel.textColor = [UIColor tc2Color];
    _prescriptionIdLabel.font = [UIFont systemFontOfSize:16];
    
    _line2.backgroundColor = [UIColor bc3Color];
    
    _orderIdLabel.textColor = [UIColor tc2Color];
    _orderIdLabel.font = [UIFont systemFontOfSize:12];
    
    _doPayButton.backgroundColor = [UIColor sbc1Color];
    _doPayButton.layer.masksToBounds = YES;
    _doPayButton.layer.cornerRadius = 3;
    [_doPayButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
    _doPayButton.titleLabel.font = [UIFont systemFontOfSize:14];
}

- (void)bindModel {
    [RACObserve(self, order) subscribeNext:^(SCMyOrderModel *x) {
        if ([x.payStatus isEqualToString:@"NOTPAY"]||[x.payStatus isEqualToString:@"FAILURE"]) {
            _payStatus.text = @"待支付";
            _payStatus.textColor = [UIColor tc2Color];
            _doPayButton.hidden = NO;
        } else if ([x.payStatus isEqualToString:@"SUCCESS"]) {
            _payStatus.text = @"已支付";
            _payStatus.textColor = [UIColor stc4Color];
            _doPayButton.hidden = YES;
        } else if ([x.payStatus isEqualToString:@"EXPIRED"]) {
            _payStatus.text = @"已超时";
            _payStatus.textColor = [UIColor tc2Color];
            _doPayButton.hidden = YES;
        } else if ([x.payStatus isEqualToString:@"REFUND"]) {
            _payStatus.text = @"退款中";
            _payStatus.textColor = [UIColor tc2Color];
            _doPayButton.hidden = YES;
        } else if ([x.payStatus isEqualToString:@"REFUNDSUCCESS"]) {
            _payStatus.text = @"已退款";
            _payStatus.textColor = [UIColor tc2Color];
            _doPayButton.hidden = YES;
        } 
//        _orderIdLabel.text = [NSString stringWithFormat:@"订单号: %@", x.orderId];
        _orderIdLabel.text = @"   ";
        businessModel *business = x.business;
        _hospitalName.text = business.hospitalName;
        _departmentLabel.text = [NSString stringWithFormat:@"科  室: %@", business.deptName];
        _priceLabel.text = [NSString stringWithFormat:@"支付金额: %@元", x.price];
        _timeLabel.text = [NSString stringWithFormat:@"开方时间: %@", business.time];
        _prescriptionIdLabel.text = [NSString stringWithFormat:@"处方号码: %@", business.prescriptionNum];
    }];
}

- (IBAction)doPay:(id)sender {
    _doPayBlock(_order);
}

@end
