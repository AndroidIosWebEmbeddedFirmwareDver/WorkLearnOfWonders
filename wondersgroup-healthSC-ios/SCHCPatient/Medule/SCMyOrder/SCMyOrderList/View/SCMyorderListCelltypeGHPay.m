//
//  SCMyorderListCelltypeGHPay.m
//  SCHCPatient
//
//  Created by wanda on 16/11/10.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCMyorderListCelltypeGHPay.h"

@implementation SCMyorderListCelltypeGHPay


- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setupSubview];
        [self bindRac];
    }
    return self;
}

- (void)setupSubview
{
    WS(weakSelf)
    UIView *topView = [[UIView alloc] init];
    topView.backgroundColor = [UIColor whiteColor];
    [self.contentView addSubview:topView];
    [topView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.contentView);
        make.top.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(44);
    }];
    
    _orderstateLb = [[UILabel alloc] init];
    _orderstateLb.text = @"挂号费支付";
    _orderstateLb.textColor = [UIColor tc2Color];
    _orderstateLb.backgroundColor = [UIColor clearColor];
    _orderstateLb.font = [UIFont systemFontOfSize:14];
    [topView addSubview:_orderstateLb];
    [_orderstateLb mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.centerY.equalTo(topView);
        make.width.mas_equalTo(125);
        make.height.mas_equalTo(20);
    }];
    
    UILabel *toplineLabel = [[UILabel alloc] init];
    toplineLabel.backgroundColor = [UIColor bc3Color];
    [topView addSubview:toplineLabel];
    [toplineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.bottom.equalTo(topView.mas_bottom);
        make.height.mas_equalTo(0.5);
    }];
    
    _hospitalNameLabel = [[UILabel alloc] init];
   // _hospitalNameLabel.text = @"成都第一人民医院";
    _hospitalNameLabel.textColor = [UIColor tc1Color];
    _hospitalNameLabel.backgroundColor = [UIColor clearColor];
    _hospitalNameLabel.font = [UIFont systemFontOfSize:16];
    [self.contentView addSubview:_hospitalNameLabel];
    [_hospitalNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.top.equalTo(topView.mas_bottom).offset(10);
//        make.width.mas_equalTo(165);
        make.height.mas_equalTo(20);
    }];
    
    _orderStateLabel = [[UILabel alloc] init];
    //_orderStateLabel.text = @"待支付";
    _orderStateLabel.textAlignment = NSTextAlignmentRight;
     _orderStateLabel.textColor = [UIColor tc2Color];
    _orderStateLabel.backgroundColor = [UIColor clearColor];
    _orderStateLabel.font = [UIFont systemFontOfSize:14];
    [topView addSubview:_orderStateLabel];
    [_orderStateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(topView);
        make.width.mas_equalTo(65);
        make.height.mas_equalTo(20);
    }];
    
    _keshiLabel = [[UILabel alloc] init];
   // _keshiLabel.text = @"皮肤科";
    _keshiLabel.textColor = [UIColor tc1Color];
    _keshiLabel.backgroundColor = [UIColor clearColor];
    _keshiLabel.font = [UIFont systemFontOfSize:16];
    [self.contentView addSubview:_keshiLabel];
    [_keshiLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.top.equalTo(_hospitalNameLabel.mas_bottom).offset(5);
        make.width.mas_equalTo(100);
        make.height.mas_equalTo(20);
    }];
    
//    _doctorNameLabel = [[UILabel alloc] init];
//  //  _doctorNameLabel.text = @"张医师";
//    _doctorNameLabel.textColor = [UIColor tc1Color];
//    _doctorNameLabel.backgroundColor = [UIColor clearColor];
//    _doctorNameLabel.font = [UIFont systemFontOfSize:16];
//    [self.contentView addSubview:_doctorNameLabel];
//    [_doctorNameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(_keshiLabel.mas_right).offset(5);
//        make.top.equalTo(_keshiLabel);
//        make.width.mas_equalTo(65);
//        make.height.mas_equalTo(20);
//    }];
//    
//    _menzhenLabel = [[UILabel alloc] init];
//   // _menzhenLabel.text = @"专家门诊";
//    _menzhenLabel.textColor = [UIColor tc1Color];
//    _menzhenLabel.backgroundColor = [UIColor clearColor];
//    _menzhenLabel.font = [UIFont systemFontOfSize:16];
//    [self.contentView addSubview:_menzhenLabel];
//    [_menzhenLabel mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.equalTo(_doctorNameLabel.mas_right).offset(5);
//        make.top.equalTo(_keshiLabel);
//      //  make.width.mas_equalTo(100);
//        make.height.mas_equalTo(20);
//    }];
    
    _peopleLabel = [[UILabel alloc] init];
   // _peopleLabel.text = @"就诊人:张小亮";
    _peopleLabel.textColor = [UIColor tc1Color];
    _peopleLabel.backgroundColor = [UIColor clearColor];
    _peopleLabel.font = [UIFont systemFontOfSize:16];
    [self.contentView addSubview:_peopleLabel];
    [_peopleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.top.equalTo(_keshiLabel.mas_bottom).offset(5);
        make.height.mas_equalTo(20);
    }];
    
    _moneyLabel = [[UILabel alloc] init];
 //   _moneyLabel.text = @"挂号金额:50元";
    _moneyLabel.textAlignment = NSTextAlignmentRight;
    _moneyLabel.textColor = [UIColor tc1Color];
    _moneyLabel.backgroundColor = [UIColor clearColor];
    _moneyLabel.font = [UIFont systemFontOfSize:16];
    [self.contentView addSubview:_moneyLabel];
    [_moneyLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(_peopleLabel.mas_bottom).offset(5);
        make.height.mas_equalTo(20);
    }];

    _timeLabel = [[UILabel alloc] init];
  //  _timeLabel.text = @"2016-09-28  下午";
    _timeLabel.textColor = [UIColor tc2Color];
    _timeLabel.backgroundColor = [UIColor clearColor];
    _timeLabel.font = [UIFont systemFontOfSize:16];
    [self.contentView addSubview:_timeLabel];
    [_timeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.top.equalTo(_moneyLabel.mas_bottom).offset(5);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.height.mas_equalTo(20);
    }];
    
    UIView *orderView = [[UIView alloc] init];
    [self.contentView addSubview:orderView];
    [orderView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.equalTo(weakSelf.contentView);
        make.height.equalTo(@44);
        make.left.right.equalTo(weakSelf.contentView);
    }];
    
    UILabel *lineLabel = [[UILabel alloc] init];
    lineLabel.backgroundColor = [UIColor bc3Color];
    [orderView addSubview:lineLabel];
    [lineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.top.equalTo(orderView);
        make.height.mas_equalTo(0.5);
    }];
    
    _orderNumberLabel = [[UILabel alloc] init];
    _orderNumberLabel.font = [UIFont systemFontOfSize:12.0];
    _orderNumberLabel.textColor = [UIColor tc1Color];
   // _orderNumberLabel.text = @"订单号:612836871263112";
    _orderNumberLabel.backgroundColor = [UIColor clearColor];
    [orderView addSubview:_orderNumberLabel];
    [_orderNumberLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.contentView).offset(15);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.centerY.equalTo(orderView);
    }];
    
    UILabel *bottomLine = [[UILabel alloc] init];
    bottomLine.backgroundColor = [UIColor bc3Color];
    [self.contentView addSubview:bottomLine];
    [bottomLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(weakSelf.contentView);
        make.height.mas_equalTo(0.5);
    }];
    
    self.payButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.payButton.layer.masksToBounds = YES;
    self.payButton.layer.cornerRadius = 3.;
    self.payButton.hidden = YES;
    self.payButton.backgroundColor = [UIColor stc1Color];
    [self.payButton setTitle:@"立即支付" forState:UIControlStateNormal];
    self.payButton.layer.borderColor = [UIColor tc2Color].CGColor;
    [self.payButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    self.payButton.titleLabel.font = [UIFont systemFontOfSize:14.0f];
    [[self.payButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
        if (weakSelf.statusButtonBlock) {
            weakSelf.statusButtonBlock(weakSelf.orderModel);
        }
    }];
    [orderView addSubview:self.payButton];
    [self.payButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(orderView);
        make.right.equalTo(weakSelf.contentView).offset(-15);
        make.height.mas_equalTo(@30);
        make.width.mas_equalTo(@75);
    }];
    
}

- (void)bindRac
{
    WS(weakSelf)
    [RACObserve(self, orderModel.business.hospitalName) subscribeNext:^(id x) {
        weakSelf.hospitalNameLabel.text = x;
    }];
    
    [RACObserve(self, orderModel.payStatus) subscribeNext:^(id x) {
        if ([x isEqualToString:@"NOTPAY"]||[x isEqualToString:@"FAILURE"]) {
            weakSelf.orderStateLabel.text = @"待支付";
            weakSelf.payButton.hidden = NO;
            weakSelf.orderStateLabel.textColor = [UIColor tc2Color];
        } else if ([x isEqualToString:@"SUCCESS"]) {
             weakSelf.orderStateLabel.text = @"已支付";
             weakSelf.payButton.hidden = YES;
             weakSelf.orderStateLabel.textColor = [UIColor stc4Color];
        } else if ([x isEqualToString:@"REFUND"]) {
             weakSelf.orderStateLabel.text = @"退款中";
             weakSelf.payButton.hidden = YES;
             weakSelf.orderStateLabel.textColor = [UIColor tc2Color];
        } else if ([x isEqualToString:@"EXPIRED"]) {
             weakSelf.orderStateLabel.text = @"已超时";
             weakSelf.payButton.hidden = YES;
             weakSelf.orderStateLabel.textColor = [UIColor tc2Color];
        } else if ([x isEqualToString:@"REFUNDSUCCESS"]) {
            weakSelf.orderStateLabel.text = @"已退款";
            weakSelf.payButton.hidden = YES;
            weakSelf.orderStateLabel.textColor = [UIColor tc2Color];
        }
    }];
    
    [RACObserve(self, orderModel.price) subscribeNext:^(id x) {
        weakSelf.moneyLabel.text = [NSString stringWithFormat:@"挂号金额: %@元",x];
    }];
    
    [RACObserve(self, orderModel.business.time) subscribeNext:^(id x) {
        weakSelf.timeLabel.text = [NSString stringWithFormat:@"%@",x];
    }];
    
    [RACObserve(self, orderModel.orderId) subscribeNext:^(id x) {
        weakSelf.orderNumberLabel.text = [NSString stringWithFormat:@"订单号: %@",x];
    }];
    
//    [RACObserve(self, orderModel.business.doctorName) subscribeNext:^(id x) {
//        weakSelf.doctorNameLabel.text = [NSString stringWithFormat:@"%@",x];
//    }];
    
    [RACObserve(self, orderModel.business.deptName) subscribeNext:^(id x) {
//        weakSelf.keshiLabel.text = [NSString stringWithFormat:@"%@",x];
        
        weakSelf.keshiLabel.text = [NSString stringWithFormat:@"%@　%@　%@", weakSelf.orderModel.business.deptName, weakSelf.orderModel.business.doctorName, weakSelf.orderModel.business.outDoctorLevel];
    }];
    
//    [RACObserve(self, orderModel.business.outDoctorLevel) subscribeNext:^(id x) {
//        weakSelf.menzhenLabel.text = [NSString stringWithFormat:@"%@",x];
//    }];
//    
    [RACObserve(self, orderModel.business.patientName) subscribeNext:^(id x) {
        weakSelf.peopleLabel.text = [NSString stringWithFormat:@"就诊人: %@",x];
    }];
    
}

@end
