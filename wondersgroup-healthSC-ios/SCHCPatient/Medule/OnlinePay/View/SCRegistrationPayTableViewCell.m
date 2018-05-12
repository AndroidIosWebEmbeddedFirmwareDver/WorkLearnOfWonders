//
//  SCRegistrationPayTableViewCell.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCRegistrationPayTableViewCell.h"

@interface SCRegistrationPayTableViewCell ()

@property (weak, nonatomic) IBOutlet UIView *topView;
@property (weak, nonatomic) IBOutlet UILabel *paystatus;
@property (weak, nonatomic) IBOutlet UIView *line1;
@property (weak, nonatomic) IBOutlet UILabel *hospitalName;
@property (weak, nonatomic) IBOutlet UILabel *doctorName;
@property (weak, nonatomic) IBOutlet UILabel *patientName;
@property (weak, nonatomic) IBOutlet UILabel *priceLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;
@property (weak, nonatomic) IBOutlet UIView *line2;
@property (weak, nonatomic) IBOutlet UILabel *orderIdLabel;
@property (weak, nonatomic) IBOutlet UIButton *doPayButton;


@end

@implementation SCRegistrationPayTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    
    [self setupView];
    [self bindModel];
}

- (void)setupView {
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    
    _topView.backgroundColor = [UIColor bc2Color];
    
    _paystatus.font = [UIFont systemFontOfSize:14];
    
    _line1.backgroundColor = [UIColor bc3Color];
    
    _hospitalName.textColor = [UIColor tc1Color];
    _hospitalName.font = [UIFont systemFontOfSize:16];
    
    _doctorName.textColor = [UIColor tc1Color];
    _doctorName.font = [UIFont systemFontOfSize:16];
    
    _patientName.textColor = [UIColor tc1Color];
    _patientName.font = [UIFont systemFontOfSize:16];
    
    _priceLabel.textColor = [UIColor tc1Color];
    _priceLabel.font = [UIFont systemFontOfSize:16];
    
    _timeLabel.textColor = [UIColor tc2Color];
    _timeLabel.font = [UIFont systemFontOfSize:16];
    
    _line2.backgroundColor = [UIColor bc3Color];
    
    _orderIdLabel.textColor = [UIColor tc2Color];
    _orderIdLabel.font = [UIFont systemFontOfSize:12];
    
    _doPayButton.backgroundColor = [UIColor sbc1Color];
    _doPayButton.layer.masksToBounds = YES;
    _doPayButton.layer.cornerRadius = 3;
    [_doPayButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
    _doPayButton.titleLabel.font = [UIFont systemFontOfSize:14];
    
    [_doPayButton addTarget:self action:@selector(gotoPay) forControlEvents:UIControlEventTouchUpInside];
}

- (void)bindModel {
    [RACObserve(self, order) subscribeNext:^(SCMyOrderModel *x) {
        if ([x.payStatus isEqualToString:@"NOTPAY"]||[x.payStatus isEqualToString:@"FAILURE"]) {
            _paystatus.text = @"待支付";
            _paystatus.textColor = [UIColor tc2Color];
            _doPayButton.hidden = NO;
        } else if ([x.payStatus isEqualToString:@"SUCCESS"]) {
            _paystatus.text = @"已支付";
            _paystatus.textColor = [UIColor stc4Color];
            _doPayButton.hidden = YES;
        } else if ([x.payStatus isEqualToString:@"EXPIRED"]) {
            _paystatus.text = @"已超时";
            _paystatus.textColor = [UIColor tc2Color];
            _doPayButton.hidden = YES;
        } else if ([x.payStatus isEqualToString:@"REFUND"]) {
            _paystatus.text = @"退款中";
            _paystatus.textColor = [UIColor tc2Color];
            _doPayButton.hidden = YES;
        }  else if ([x.payStatus isEqualToString:@"REFUNDSUCCESS"]) {
            _paystatus.text = @"已退款";
            _paystatus.textColor = [UIColor tc2Color];
            _doPayButton.hidden = YES;
        } 
        _orderIdLabel.text = [NSString stringWithFormat:@"订单号: %@", x.orderId];
        _hospitalName.text = x.business.hospitalName;
        _doctorName.text = [NSString stringWithFormat:@"%@ %@ %@", x.business.deptName, x.business.doctorName, x.business.outDoctorLevel];
        _patientName.text = [NSString stringWithFormat:@"就诊人: %@", x.business.patientName];
//        double price = [x.pay_order.amount doubleValue]/100.00;
        _priceLabel.text = [NSString stringWithFormat:@"挂号金额: %@元", x.price];
        _timeLabel.text = x.business.time;
//        _orderIdLabel.text = [NSString stringWithFormat:@"挂号单:%@", x.orderId];
    }];
}

- (void)gotoPay {
    _doPayBlock(self.order);
}

@end
