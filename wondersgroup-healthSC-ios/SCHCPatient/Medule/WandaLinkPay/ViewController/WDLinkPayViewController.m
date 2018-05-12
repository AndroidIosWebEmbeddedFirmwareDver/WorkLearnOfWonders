//
//  WDLinkPayViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDLinkPayViewController.h"
#import "ShouYinTaiAPI.h"
#import "WDPayResultViewController.h"

@interface WDLinkPayViewController () <ShouYinTaiDelegate>

@property (nonatomic, strong) UILabel     *orderIdLabel;
@property (nonatomic, strong) UILabel     *priceLabel;

@property (nonatomic, strong) UILabel     *minLabel;
@property (nonatomic, strong) UILabel     *secLabel;

@property (nonatomic, strong) UIButton    *weichatButton;
@property (nonatomic, strong) UIButton    *alipayButton;
@property (nonatomic, strong) UIImageView *weichatSearchImage;
@property (nonatomic, strong) UIImageView *alipaySearchImage;

@property (nonatomic, strong) UIButton    *payButton;

@property (nonatomic, strong) NSString    *payType;

@property (nonatomic, strong) NSString    *countdownString;
@property (nonatomic, assign) NSInteger   countdownSeconds;
@property (nonatomic, strong) NSTimer     *countdownTimer;

@property (nonatomic, strong) payorderModel *payOrder;

@property (nonatomic, assign) BOOL        isPushResult;

@end

@implementation WDLinkPayViewController

- (WDlnhospitalPayViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [WDlnhospitalPayViewModel new];
    }
    return _viewModel;
}

- (UILabel *)orderIdLabel {
    if (!_orderIdLabel) {
        _orderIdLabel = [UILabel new];
        _orderIdLabel.textColor = [UIColor tc2Color];
        _orderIdLabel.font = [UIFont systemFontOfSize:16];
    }
    return _orderIdLabel;
}

- (UILabel *)priceLabel {
    if (!_priceLabel) {
        _priceLabel = [UILabel new];
        _priceLabel.textColor = [UIColor tc5Color];
        _priceLabel.font = [UIFont systemFontOfSize:16];
    }
    return _priceLabel;
}

- (UILabel *)minLabel {
    if (!_minLabel) {
        _minLabel = [UILabel new];
        _minLabel.textColor = [UIColor stc1Color];
        _minLabel.font = [UIFont systemFontOfSize:20];
    }
    return _minLabel;
}

- (UILabel *)secLabel {
    if (!_secLabel) {
        _secLabel = [UILabel new];
        _secLabel.textColor = [UIColor stc1Color];
        _secLabel.font = [UIFont systemFontOfSize:20];
    }
    return _secLabel;
}

- (UIButton *)weichatButton {
    if (!_weichatButton) {
        _weichatButton = [UIButton new];
        [_weichatButton addTarget:self action:@selector(didSearchWeichat) forControlEvents:UIControlEventTouchUpInside];
    }
    return _weichatButton;
}

- (UIButton *)alipayButton {
    if (!_alipayButton) {
        _alipayButton = [UIButton new];
        [_alipayButton addTarget:self action:@selector(didSearchAlipay) forControlEvents:UIControlEventTouchUpInside];
    }
    return _alipayButton;
}

- (UIImageView *)weichatSearchImage {
    if (!_weichatSearchImage) {
        _weichatSearchImage = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"未选中"]];
    }
    return _weichatSearchImage;
}

- (UIImageView *)alipaySearchImage {
    if (!_alipaySearchImage) {
        _alipaySearchImage = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"未选中"]];
    }
    return _alipaySearchImage;
}

- (UIButton *)payButton {
    if (!_payButton) {
        _payButton = [UIButton new];
        _payButton.backgroundColor = [UIColor sbc1Color];
        _payButton.layer.masksToBounds = YES;
        _payButton.layer.cornerRadius = 3;
        [_payButton setTitle:@"立即支付" forState:UIControlStateNormal];
        [_payButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        _payButton.titleLabel.font = [UIFont systemFontOfSize:16];
        [_payButton addTarget:self action:@selector(doPay:) forControlEvents:UIControlEventTouchUpInside];
    }
    return _payButton;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(reLoadOrder) name:@"LINK_PAY_RELOAD_ORDER" object:nil];
    [self setupView];
    [self setupUIData];
    [self didSearchWeichat];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    _isPushResult = NO;
    [self reLoadOrder];
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
    if (_countdownTimer) {
        [_countdownTimer invalidate];
        _countdownTimer = nil;
    }
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"LINK_PAY_RELOAD_ORDER" object:nil];
}

- (void)setupView {
    WS(ws)
    
    self.title = @"支付方式";
    
    UIView *mindView = [UIView new];
    mindView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:mindView];
    [mindView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.view).offset(10);
        make.left.right.equalTo(ws.view);
        make.height.mas_equalTo(140);
    }];
    
    UILabel *orderTitleLabel = [UILabel new];
    orderTitleLabel.text = @"订单号: ";
    orderTitleLabel.textColor = [UIColor tc1Color];
    orderTitleLabel.font = [UIFont systemFontOfSize:16];
    [mindView addSubview:orderTitleLabel];
    [orderTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(mindView).offset(10);
        make.left.equalTo(mindView).offset(15);
    }];
    
    UILabel *priceTilteLabel = [UILabel new];
    priceTilteLabel.text = @"金    额: ";
    priceTilteLabel.textColor = [UIColor tc1Color];
    priceTilteLabel.font = [UIFont systemFontOfSize:16];
    [mindView addSubview:priceTilteLabel];
    [priceTilteLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(orderTitleLabel.mas_bottom).offset(10);
        make.left.equalTo(mindView).offset(15);
    }];

    UIView *mindLineView = [UIView new];
    mindLineView.backgroundColor = [UIColor bc3Color];
    [mindView addSubview:mindLineView];
    [mindLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(priceTilteLabel.mas_bottom).offset(10);
        make.left.equalTo(mindView).offset(15);
        make.right.equalTo(mindView).offset(-15);
        make.height.mas_equalTo(0.5);
    }];
    
    UILabel *tipLabel1 = [UILabel new];
    tipLabel1.text = @"请在15分钟内完成支付";
    tipLabel1.textColor = [UIColor tc3Color];
    tipLabel1.font = [UIFont systemFontOfSize:12];
    [mindView addSubview:tipLabel1];
    [tipLabel1 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(mindLineView.mas_bottom).offset(15);
        make.left.equalTo(mindView).offset(15);
    }];

    UILabel *tipLabel2 = [UILabel new];
    tipLabel2.text = @"逾期订单将自动取消";
    tipLabel2.textColor = [UIColor tc3Color];
    tipLabel2.font = [UIFont systemFontOfSize:12];
    [mindView addSubview:tipLabel2];
    [tipLabel2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(tipLabel1.mas_bottom).offset(10);
        make.left.equalTo(mindView).offset(15);
    }];
    
    UILabel *secText = [UILabel new];
    secText.text = @"秒";
    secText.textColor = [UIColor stc1Color];
    secText.font = [UIFont systemFontOfSize:14];
    [mindView addSubview:secText];
    [secText mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(mindView).offset(-15);
        make.top.equalTo(mindLineView.mas_bottom).offset(27);
    }];
    
    [mindView addSubview:self.secLabel];
    [self.secLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(secText);
        make.right.equalTo(mindView).offset(-33);
    }];
    
    UILabel *minText = [UILabel new];
    minText.text = @"分";
    minText.textColor = [UIColor stc1Color];
    minText.font = [UIFont systemFontOfSize:14];
    [mindView addSubview:minText];
    [minText mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(mindView).offset(-63);
        make.centerY.equalTo(secText);
    }];
    
    [mindView addSubview:self.minLabel];
    [self.minLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(secText);
        make.right.equalTo(mindView).offset(-82);
    }];
    
    UILabel *chooseTitleLabel = [UILabel new];
    chooseTitleLabel.text = @"选择支付方式";
    chooseTitleLabel.textColor = [UIColor tc3Color];
    chooseTitleLabel.font = [UIFont systemFontOfSize:14];
    [self.view addSubview:chooseTitleLabel];
    [chooseTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(mindView.mas_bottom).offset(15);
        make.left.equalTo(mindView).offset(15);
    }];

    UIView *bottomView = [UIView new];
    bottomView.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:bottomView];
    [bottomView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(chooseTitleLabel.mas_bottom).offset(15);
        make.left.right.equalTo(ws.view);
        make.height.mas_equalTo(120);
    }];
    
    UIView *bottomLineView = [UIView new];
    bottomLineView.backgroundColor = [UIColor bc3Color];
    [bottomView addSubview:bottomLineView];
    [bottomLineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomView).offset(57);
        make.right.equalTo(bottomView);
        make.centerY.equalTo(bottomView);
        make.height.mas_equalTo(0.5);
    }];
    
    UIImageView *weichatPayImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"微信支付"]];
    [bottomView addSubview:weichatPayImageView];
    [weichatPayImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomView).offset(15);
        make.centerY.mas_equalTo(-30);
    }];
    
    UILabel *weichatLabel = [UILabel new];
    weichatLabel.text = @"微信支付";
    weichatLabel.textColor = [UIColor tc1Color];
    weichatLabel.font = [UIFont systemFontOfSize:14];
    [bottomView addSubview:weichatLabel];
    [weichatLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weichatPayImageView);
        make.left.equalTo(bottomView).offset(57);
    }];
    
    UIImageView *alipayImageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"支付宝支付"]];
    [bottomView addSubview:alipayImageView];
    [alipayImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(bottomView).offset(15);
        make.centerY.mas_equalTo(30);
    }];
    
    UILabel *alipayLabel = [UILabel new];
    alipayLabel.text = @"支付宝支付";
    alipayLabel.textColor = [UIColor tc1Color];
    alipayLabel.font = [UIFont systemFontOfSize:14];
    [bottomView addSubview:alipayLabel];
    [alipayLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(alipayImageView);
        make.left.equalTo(bottomView).offset(57);
    }];
    
    UIView *payView = [UIView new];
    payView.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:payView];
    [payView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(ws.view);
        make.height.mas_equalTo(64);
    }];
    
    [mindView addSubview:self.orderIdLabel];
    [self.orderIdLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(orderTitleLabel);
        make.left.equalTo(orderTitleLabel.mas_right).offset(10);
    }];
    
    [mindView addSubview:self.priceLabel];
    [self.priceLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(priceTilteLabel);
        make.left.equalTo(priceTilteLabel.mas_right).offset(10);
    }];
    
    [bottomView addSubview:self.weichatSearchImage];
    [self.weichatSearchImage mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(weichatPayImageView);
        make.right.equalTo(bottomView).offset(-15);
        make.height.mas_equalTo(20);
        make.width.mas_equalTo(20);
    }];
    
    [bottomView addSubview:self.alipaySearchImage];
    [self.alipaySearchImage mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerY.equalTo(alipayImageView);
        make.right.equalTo(bottomView).offset(-15);
        make.height.mas_equalTo(20);
        make.width.mas_equalTo(20);
    }];
    
    [bottomView addSubview:self.weichatButton];
    [self.weichatButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(bottomView);
        make.height.mas_equalTo(60);
    }];
    
    [bottomView addSubview:self.alipayButton];
    [self.alipayButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.bottom.left.right.equalTo(bottomView);
        make.height.mas_equalTo(60);
    }];
    
    [payView addSubview:self.payButton];
    [self.payButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(payView).offset(10);
        make.left.equalTo(payView).offset(15);
        make.right.equalTo(payView).offset(-15);
        make.bottom.equalTo(payView).offset(-15);
    }];
    
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"close"] style:UIBarButtonItemStylePlain target:self action:@selector(cancelPay)];
}

- (void)setupUIData {
    if (_isRegistration) {
        self.orderIdLabel.text = self.viewModel.registrationOrder.showOrderId;
//        double price = [self.viewModel.registrationOrder.amount doubleValue]/100.00;
        self.priceLabel.text = [NSString stringWithFormat:@"%@元", self.viewModel.registrationOrder.price];
    } else {
        self.orderIdLabel.text = self.viewModel.payOrder.orderId;
//        double price = [self.viewModel.payOrder.pay_order.amount doubleValue]/100.00;
        self.priceLabel.text = [NSString stringWithFormat:@"%@元", self.viewModel.payOrder.price];

    }
}

- (void)reLoadOrder {
    if (_isRegistration) {
        [LoadingView showLoadingInView:self.view];
        [self.viewModel getLinkPayInfoWithOrderId:self.viewModel.registrationOrder.payOrderId
                                          success:^(payorderModel *payorderModel) {
                                              int remaining = [payorderModel.time_left intValue];
                                              _payOrder = payorderModel;
                                              if (remaining <= 0) {
                                                  WDPayResultViewController *vc = [WDPayResultViewController new];
                                                  vc.massage = @"支付超时";
                                                  vc.notCanRePay = YES;
                                                  if (_isRegistration) {
                                                      vc.isRegistration = YES;
                                                  }
                                                  vc.payResult = WDPayFaild;
                                                  if (!_isPushResult) {
                                                      _isPushResult = YES;
                                                      [self.navigationController pushViewController:vc animated:YES];
                                                  }
                                              } else {
                                                  _countdownSeconds = remaining;
//                                                  _minLabel.text = [NSString stringWithFormat:@"%02d", (int)_countdownSeconds/60];
//                                                  _secLabel.text = [NSString stringWithFormat:@"%02d", (int)_countdownSeconds%60];
                                                  [self startCountdownSecond];
                                                  [LoadingView hideLoadinForView:self.view];
                                              }
                                          } failed:^{
                                              [LoadingView hideLoadinForView:self.view];
                                              WDPayResultViewController *vc = [WDPayResultViewController new];
                                              vc.massage = @"获取信息失败，请稍后再试";
                                              vc.notCanRePay = YES;
                                              if (_isRegistration) {
                                                  vc.isRegistration = YES;
                                              }
                                              vc.payResult = WDPayFaild;
                                              if (!_isPushResult) {
                                                  _isPushResult = YES;
                                                  [self.navigationController pushViewController:vc animated:YES];
                                              }
                                          }];
    } else {
        [LoadingView showLoadingInView:self.view];
        [self.viewModel getLinkPayInfoWithOrderId:self.viewModel.payOrder.pay_order.oid
                                          success:^(payorderModel *payorderModel) {
                                              int remaining = [payorderModel.time_left intValue];
                                              _payOrder = payorderModel;
                                              if (remaining <= 0) {
                                                  WDPayResultViewController *vc = [WDPayResultViewController new];
                                                  vc.massage = @"支付超时";
                                                  vc.notCanRePay = YES;
                                                  if (_isRegistration) {
                                                      vc.isRegistration = YES;
                                                  }
                                                  vc.payResult = WDPayFaild;
                                                  if (!_isPushResult) {
                                                      _isPushResult = YES;
                                                      [self.navigationController pushViewController:vc animated:YES];
                                                  }
                                              } else {
                                                  _countdownSeconds = remaining;
//                                                  _minLabel.text = [NSString stringWithFormat:@"02%d", (int)_countdownSeconds/60];
//                                                  _secLabel.text = [NSString stringWithFormat:@"02%d", (int)_countdownSeconds%60];
                                                  [self startCountdownSecond];
                                                  [LoadingView hideLoadinForView:self.view];
                                              }
                                          } failed:^{
                                              [LoadingView hideLoadinForView:self.view];
                                              WDPayResultViewController *vc = [WDPayResultViewController new];
                                              vc.massage = @"获取信息失败，请稍后再试";
                                              vc.notCanRePay = YES;
                                              if (_isRegistration) {
                                                  vc.isRegistration = YES;
                                              }
                                              vc.payResult = WDPayFaild;
                                              if (!_isPushResult) {
                                                  _isPushResult = YES;
                                                  [self.navigationController pushViewController:vc animated:YES];
                                              }
                                          }];
    }
}


- (void)startCountdownSecond {
    [_countdownTimer invalidate];
    _countdownTimer = nil;
    _countdownTimer = [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(doCountdown) userInfo:nil repeats:YES];
}

- (void)doCountdown {
    _countdownSeconds--;
    _minLabel.text = [NSString stringWithFormat:@"%02d", (int)_countdownSeconds/60];
    _secLabel.text = [NSString stringWithFormat:@"%02d", (int)_countdownSeconds%60];
    if (_countdownSeconds == 0) {
        [_countdownTimer invalidate];
        _countdownTimer = nil;
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = @"支付超时";
        vc.notCanRePay = YES;
        if (_isRegistration) {
            vc.isRegistration = YES;
        }
        vc.payResult = WDPayFaild;
        if (!_isPushResult) {
            _isPushResult = YES;
            [self.navigationController pushViewController:vc animated:YES];
        }
    }
}


- (void)didSearchWeichat {
    _weichatSearchImage.image = [UIImage imageNamed:@"选中蓝色"];
    _alipaySearchImage.image = [UIImage imageNamed:@"未选中"];
    _payType = @"weixin";
}

- (void)didSearchAlipay {
    _weichatSearchImage.image = [UIImage imageNamed:@"未选中"];
    _alipaySearchImage.image = [UIImage imageNamed:@"选中蓝色"];
    _payType = @"alipay";
}

- (IBAction)doPay:(id)sender {
    if (_payType) {
        [self startPay];
    } else {
        [MBProgressHUDHelper showHudWithText:@"请选择支付方式"];
    }
}

- (void)startPay {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getLinkPayKeyWithChannel:_payType success:^{
        [LoadingView hideLoadinForView:self.view];
        [self paying];
    } failed:^(NSString *msg) {
        [LoadingView hideLoadinForView:self.view];
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = msg;
        if (_isRegistration) {
            vc.isRegistration = YES;
        }
        vc.payResult = WDPayFaild;
        vc.notCanRePay = YES;
        if (!_isPushResult) {
            _isPushResult = YES;
            [self.navigationController pushViewController:vc animated:YES];
        }
    }];
}

- (void)paying {
    if (_payOrder == nil) {
        return;
    }
//    NSString *orderId;
//    NSString *price;
//    if (_isRegistration) {
//        orderId = self.viewModel.registrationOrder.payOrderId;
//        price = self.viewModel.registrationOrder.amount;
//    } else {
//        orderId = self.viewModel.payOrder.pay_order.oid;
//        price = self.viewModel.payOrder.pay_order.amount;
//    }
    
    [ShouYinTaiAPI pay_use_SYT_with_appid:_payOrder.appid
//    [ShouYinTaiAPI pay_use_SYT_with_appid:LINK_PAY_APPID
                                  channel:_payType
                                 submerno:_payOrder.submerno.length > 0?_payOrder.submerno:LINK_PAY_SUBNUM
                                 order_no:_payOrder.oid
                                   amount:_payOrder.amount
                                  subject:_payOrder.subject
                                     body:_payOrder.body
                              description:_payOrder.description
                               fromScheme:LINK_PAY_SCHEME
                                      key:self.viewModel.key
                           viewcontroller:self
                                 payStyle:LINK_PAY_STYLE
                              theDelegate:self];
}

- (void)getResult:(NSDictionary *)resultDic {
//    [LoadingView hideLoadinForView:self.view];
    NSString *code = [resultDic objectForKey:@"result_code"];
    NSString *msg = [resultDic objectForKey:@"result_msg"];
    //NSString *detail = [resultDic objectForKey:@"err_detail"];
    if ([code isEqualToString:@"1"]) {
        //支付成功
        WDPayResultViewController *vc = [WDPayResultViewController new];
        if (_isRegistration) {
            vc.price = self.viewModel.registrationOrder.amount;
            vc.orderId = self.viewModel.registrationOrder.orderId;
            vc.isRegistration = YES;
        } else {
            vc.price = self.viewModel.payOrder.pay_order.amount;
        }
        vc.payResult = WDPaySuccess;
        if (!_isPushResult) {
            _isPushResult = YES;
            [self.navigationController pushViewController:vc animated:YES];
        }
    } else if ([code isEqualToString:@"0"]) {
        //参数错误无法启动SDK支付
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = @"";
        vc.payResult = WDPayFaild;
        if (_isRegistration) {
            vc.isRegistration = YES;
        }
        if (!_isPushResult) {
            _isPushResult = YES;
            [self.navigationController pushViewController:vc animated:YES];
        }
    } else if ([code isEqualToString:@"-1"]) {
        //取消
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = msg;
        vc.payResult = WDPayFaild;
        if (_isRegistration) {
            vc.isRegistration = YES;
        }
        if (!_isPushResult) {
            _isPushResult = YES;
            [self.navigationController pushViewController:vc animated:YES];
        }
    } else if ([code isEqualToString:@"-3"]) {
        //失败
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = msg;
        vc.payResult = WDPayFaild;
        if (_isRegistration) {
            vc.isRegistration = YES;
        }
        if (!_isPushResult) {
            _isPushResult = YES;
            [self.navigationController pushViewController:vc animated:YES];
        }
    } else if ([code isEqualToString:@"-4"]) {
        //不支持
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = @"";
        vc.payResult = WDPayFaild;
        if (_isRegistration) {
            vc.isRegistration = YES;
        }
        if (!_isPushResult) {
            _isPushResult = YES;
            [self.navigationController pushViewController:vc animated:YES];
        }
    } else {
        //奇怪的情况
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = @"";
        vc.payResult = WDPayFaild;
        vc.notCanRePay = YES;
        if (_isRegistration) {
            vc.isRegistration = YES;
        }
        if (!_isPushResult) {
            _isPushResult = YES;
            [self.navigationController pushViewController:vc animated:YES];
        }
    }
}

- (void)cancelPay {
    UIAlertController *alertC = [UIAlertController alertControllerWithTitle:@"是否离开支付页面"
                                                                    message:nil
                                                             preferredStyle:UIAlertControllerStyleAlert];
    [alertC addAction:[UIAlertAction actionWithTitle:@"确定离开" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        if (_isRegistration) {
            [self.navigationController popToRootViewControllerAnimated:YES];
        } else {
            [self popBack];
        }
    }]];
    [alertC addAction:[UIAlertAction actionWithTitle:@"继续支付" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        
    }]];
    [self.navigationController presentViewController:alertC animated:YES completion:^{
        
    }];
}




@end
