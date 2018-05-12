//
//  WDlnhospitalPayViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDlnhospitalPayViewController.h"
#import "ShouYinTaiAPI.h"
#import "WDPayResultViewController.h"

@interface WDlnhospitalPayViewController ()<ShouYinTaiDelegate>

@property (weak, nonatomic) IBOutlet UILabel *payTitle;
@property (weak, nonatomic) IBOutlet UILabel *hospitalName;
@property (weak, nonatomic) IBOutlet UILabel *spriceTitle;
@property (weak, nonatomic) IBOutlet UILabel *timeTitle;
@property (weak, nonatomic) IBOutlet UILabel *prescriptionIdTitle;
@property (weak, nonatomic) IBOutlet UILabel *orderIdTitle;
@property (weak, nonatomic) IBOutlet UILabel *priceLabel;
@property (weak, nonatomic) IBOutlet UILabel *timeLabel;
@property (weak, nonatomic) IBOutlet UILabel *prescriptionIdLabel;
@property (weak, nonatomic) IBOutlet UILabel *orderIdLabel;
@property (weak, nonatomic) IBOutlet UILabel *searchTitle;
@property (weak, nonatomic) IBOutlet UILabel *weixinTitle;
@property (weak, nonatomic) IBOutlet UIImageView *weixinSearchImage;
@property (weak, nonatomic) IBOutlet UILabel *alipayTitle;
@property (weak, nonatomic) IBOutlet UIImageView *alipaySearchImage;
@property (weak, nonatomic) IBOutlet UIView *lineView;
@property (weak, nonatomic) IBOutlet UIButton *doPayButton;

@property (nonatomic, strong) NSString *channel;

@end

@implementation WDlnhospitalPayViewController

- (WDlnhospitalPayViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [WDlnhospitalPayViewModel new];
    }
    return _viewModel;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self setupUIData];
    _weixinSearchImage.image = [UIImage imageNamed:@"选中蓝色"];
    _alipaySearchImage.image = [UIImage imageNamed:@"未选中"];
    _channel = @"weixin";
}

- (void)setupView {
    self.title = @"支付详情";
    
    _payTitle.textColor = [UIColor tc3Color];
    _payTitle.font = [UIFont systemFontOfSize:14];
    
    _hospitalName.textColor = [UIColor tc1Color];
    _hospitalName.font = [UIFont systemFontOfSize:16];
    
    _spriceTitle.textColor = [UIColor tc1Color];
    _spriceTitle.font = [UIFont systemFontOfSize:16];
    
    _timeTitle.textColor = [UIColor tc1Color];
    _timeTitle.font = [UIFont systemFontOfSize:16];
    
    _prescriptionIdTitle.textColor = [UIColor tc1Color];
    _prescriptionIdTitle.font = [UIFont systemFontOfSize:16];
    
    _orderIdTitle.textColor = [UIColor tc1Color];
    _orderIdTitle.font = [UIFont systemFontOfSize:16];
    
    _priceLabel.textColor = [UIColor tc5Color];
    _priceLabel.font = [UIFont systemFontOfSize:16];
    
    _timeLabel.textColor = [UIColor tc1Color];
    _timeLabel.font = [UIFont systemFontOfSize:16];
    
    _prescriptionIdLabel.textColor = [UIColor tc1Color];
    _prescriptionIdLabel.font = [UIFont systemFontOfSize:16];
    
    _orderIdTitle.textColor = [UIColor tc1Color];
    _orderIdTitle.font = [UIFont systemFontOfSize:16];
    
    _searchTitle.textColor = [UIColor tc3Color];
    _searchTitle.font = [UIFont systemFontOfSize:14];
    
    _weixinTitle.textColor = [UIColor tc1Color];
    _weixinTitle.font = [UIFont systemFontOfSize:14];
    
    _alipayTitle.textColor = [UIColor tc1Color];
    _alipayTitle.font = [UIFont systemFontOfSize:14];
    
    _lineView.backgroundColor = [UIColor bc3Color];
    
    _doPayButton.backgroundColor = [UIColor sbc1Color];
    _doPayButton.layer.masksToBounds = YES;
    _doPayButton.layer.cornerRadius = 3;
    [_doPayButton setTitle:@"立即支付" forState:UIControlStateNormal];
    [_doPayButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
    _doPayButton.titleLabel.font = [UIFont systemFontOfSize:16];
    
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"close"] style:UIBarButtonItemStylePlain target:self action:@selector(cancelPay)];
}

- (void)setupUIData {
    _hospitalName.text = self.viewModel.payOrder.business.hospitalName;
//    double price = [self.viewModel.payOrder.pay_order.amount doubleValue]/100.00;
    _priceLabel.text = [NSString stringWithFormat:@"%@元", self.viewModel.payOrder.price];
    _timeLabel.text = self.viewModel.payOrder.business.time;
    _prescriptionIdLabel.text = self.viewModel.payOrder.business.prescriptionNum;
    _orderIdLabel.text = self.viewModel.payOrder.orderId;
}

- (IBAction)searchWeixin:(id)sender {
    _weixinSearchImage.image = [UIImage imageNamed:@"选中蓝色"];
    _alipaySearchImage.image = [UIImage imageNamed:@"未选中"];
    _channel = @"weixin";
}

- (IBAction)searchAlipay:(id)sender {
    _weixinSearchImage.image = [UIImage imageNamed:@"未选中"];
    _alipaySearchImage.image = [UIImage imageNamed:@"选中蓝色"];
    _channel = @"alipay";
}

- (IBAction)doPay:(id)sender {
    if (_channel) {
        [self startPay];
    } else {
        [MBProgressHUDHelper showHudWithText:@"请选择支付方式"];
    }
}

- (void)startPay {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getLinkPayKeyWithChannel:_channel success:^{
        [LoadingView hideLoadinForView:self.view];
        [self paying];
    } failed:^(NSString *msg) {
        [LoadingView hideLoadinForView:self.view];
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = msg;
        vc.payResult = WDPayFaild;
        vc.notCanRePay = YES;
        [self.navigationController pushViewController:vc animated:YES];
    }];
}

- (void)paying {
    [ShouYinTaiAPI pay_use_SYT_with_appid:self.viewModel.payOrder.pay_order.appid
//     [ShouYinTaiAPI pay_use_SYT_with_appid:LINK_PAY_APPID
                                  channel:_channel
                                 submerno:self.viewModel.payOrder.pay_order.submerno.length > 0?self.viewModel.payOrder.pay_order.submerno:LINK_PAY_SUBNUM
                                 order_no:self.viewModel.payOrder.pay_order.oid
                                   amount:self.viewModel.payOrder.pay_order.amount
                                  subject:self.viewModel.payOrder.pay_order.subject
                                     body:self.viewModel.payOrder.pay_order.descrip
                              description:self.viewModel.payOrder.pay_order.descrip
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
//    NSString *detail = [resultDic objectForKey:@"err_detail"];
    if ([code isEqualToString:@"1"]) {
        //支付成功
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.price = self.viewModel.payOrder.pay_order.amount;
        vc.payResult = WDPaySuccess;
        [self.navigationController pushViewController:vc animated:YES];
    } else if ([code isEqualToString:@"0"]) {
        //参数错误无法启动SDK支付
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = @"";
        vc.payResult = WDPayFaild;
        [self.navigationController pushViewController:vc animated:YES];
    } else if ([code isEqualToString:@"-1"]) {
        //取消
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = msg;
        vc.payResult = WDPayFaild;
        [self.navigationController pushViewController:vc animated:YES];
    } else if ([code isEqualToString:@"-3"]) {
        //失败
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = msg;
        vc.payResult = WDPayFaild;
        [self.navigationController pushViewController:vc animated:YES];
    } else if ([code isEqualToString:@"-4"]) {
        //不支持
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = @"";
        vc.payResult = WDPayFaild;
        [self.navigationController pushViewController:vc animated:YES];
    } else {
        //奇怪的情况
        WDPayResultViewController *vc = [WDPayResultViewController new];
        vc.massage = @"";
        vc.payResult = WDPayFaild;
        vc.notCanRePay = YES;
        [self.navigationController pushViewController:vc animated:YES];
    }
}

- (void)cancelPay {
    UIAlertController *alertC = [UIAlertController alertControllerWithTitle:@"是否离开支付页面"
                                                                    message:nil
                                                             preferredStyle:UIAlertControllerStyleAlert];
    [alertC addAction:[UIAlertAction actionWithTitle:@"确定离开" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        [self popBack];
    }]];
    [alertC addAction:[UIAlertAction actionWithTitle:@"继续支付" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        
    }]];
    [self.navigationController presentViewController:alertC animated:YES completion:^{
        
    }];
}

@end
