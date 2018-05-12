//
//  WDPayResultViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/13.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDPayResultViewController.h"
#import "SCMyPreorderDetailViewController.h"

@interface WDPayResultViewController ()

@property (nonatomic, strong) UIView *successView;
@property (nonatomic, strong) UIView *faildView;
@property (nonatomic, strong) UILabel *priceLabel;
@property (nonatomic, strong) UIButton *completeButton;
@property (nonatomic, strong) UIButton *lockButton;
@property (nonatomic, strong) UILabel *falidLabel;
@property (nonatomic, strong) UIButton *rePayButton;

@end

@implementation WDPayResultViewController

- (UILabel *)priceLabel {
    if (!_priceLabel) {
        _priceLabel = [UILabel new];
        _priceLabel.textColor = [UIColor stc1Color];
        _priceLabel.font = [UIFont systemFontOfSize:18];
    }
    return _priceLabel;
}

- (UIButton *)completeButton {
    if (!_completeButton) {
        _completeButton = [UIButton new];
        [_completeButton setTitle:@"完成" forState:UIControlStateNormal];
        [_completeButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        _completeButton.titleLabel.font = [UIFont systemFontOfSize:16];
        _completeButton.backgroundColor = [UIColor bc7Color];
        _completeButton.layer.masksToBounds = YES;
        _completeButton.layer.cornerRadius = 3;
        [_completeButton addTarget:self action:@selector(payComplete) forControlEvents:UIControlEventTouchUpInside];
    }
    return _completeButton;
}

- (UIButton *)lockButton {
    if (!_lockButton) {
        _lockButton = [UIButton new];
        [_lockButton setTitle:@"查看预约详情" forState:UIControlStateNormal];
        [_lockButton setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        _lockButton.titleLabel.font = [UIFont systemFontOfSize:16];
        _lockButton.layer.masksToBounds = YES;
        _lockButton.layer.cornerRadius = 3;
        _lockButton.layer.borderWidth = 1;
        _lockButton.layer.borderColor = [UIColor bc7Color].CGColor;
    }
    return _lockButton;
}

- (UILabel *)falidLabel {
    if (!_falidLabel) {
        _falidLabel = [UILabel new];
        _falidLabel.textColor = [UIColor tc1Color];
        _falidLabel.font = [UIFont systemFontOfSize:16];
    }
    return _falidLabel;
}

- (UIButton *)rePayButton {
    if (!_rePayButton) {
        _rePayButton = [UIButton new];
        [_rePayButton setTitle:@"重新支付" forState:UIControlStateNormal];
        [_rePayButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        _rePayButton.titleLabel.font = [UIFont systemFontOfSize:16];
        _rePayButton.backgroundColor = [UIColor sbc1Color];
        _rePayButton.layer.masksToBounds = YES;
        _rePayButton.layer.cornerRadius = 3;
        [_rePayButton addTarget:self action:@selector(popBack) forControlEvents:UIControlEventTouchUpInside];
    }
    return _rePayButton;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self bindData];
}

- (void)setupView {
    self.title = @"支付详情";
    self.navigationItem.hidesBackButton = YES;
    self.navigationItem.leftBarButtonItem = nil;
}

- (void)bindData {
    [RACObserve(self, payResult) subscribeNext:^(id x) {
        if (_payResult == WDPaySuccess) {
            [self setupSuccessView];
        } else {
            [self setupFaildView];
        }
    }];
}

- (void)setupSuccessView {
    WS(ws)
    
    _successView = [UIView new];
    _successView.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:_successView];
    [_successView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.view).offset(10);
        make.left.right.bottom.equalTo(ws.view);
    }];
    
    UIImageView *resultImage = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"支付成功"]];
    [_successView addSubview:resultImage];
    [resultImage mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.view).offset(40);
        make.centerX.equalTo(ws.view);
    }];
    
    UILabel *textLabel = [UILabel new];
    textLabel.text = @"支付成功";
    textLabel.textColor = [UIColor tc1Color];
    textLabel.font = [UIFont systemFontOfSize:18];
    [_successView addSubview:textLabel];
    [textLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(resultImage.mas_bottom).offset(15);
        make.centerX.equalTo(ws.view);
    }];
    
    [_successView addSubview:self.priceLabel];
    double mun = [_price doubleValue]/100.00;
    self.priceLabel.text = [NSString stringWithFormat:@"%.2f元", mun];
    [self.priceLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(_successView);
        make.top.equalTo(textLabel.mas_bottom).offset(15);
    }];
    
    [_successView addSubview:self.completeButton];
    [self.completeButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.priceLabel.mas_bottom).offset(35);
        make.left.equalTo(_successView).offset(16);
        make.right.equalTo(_successView).offset(-16);
        make.height.mas_equalTo(44);
    }];
    
//    [_successView addSubview:self.lockButton];
//    [self.lockButton mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.top.equalTo(ws.completeButton.mas_bottom).offset(20);
//        make.left.equalTo(_successView).offset(16);
//        make.right.equalTo(_successView).offset(-16);
//        make.height.mas_equalTo(44);
//    }];
    
    
}

- (void)setupFaildView {
    WS(ws)
    
    _faildView = [UIView new];
    _faildView.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:_faildView];
    [_faildView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.view).offset(10);
        make.left.right.bottom.equalTo(ws.view);
    }];
    
    UIImageView *resultImage = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"icon支付失败"]];
    [_faildView addSubview:resultImage];
    [resultImage mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.view).offset(40);
        make.centerX.equalTo(ws.view);
    }];
    
    UILabel *textLabel = [UILabel new];
    textLabel.text = @"支付失败";
    textLabel.textColor = [UIColor tc1Color];
    textLabel.font = [UIFont systemFontOfSize:18];
    [_faildView addSubview:textLabel];
    [textLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(resultImage.mas_bottom).offset(15);
        make.centerX.equalTo(ws.view);
    }];
    
    [_faildView addSubview:self.falidLabel];
    self.falidLabel.text = _massage;
    [self.falidLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(textLabel.mas_bottom).offset(45);
        make.centerX.equalTo(_faildView);
    }];
    
    [_faildView addSubview:self.rePayButton];
    [self.rePayButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.falidLabel).offset(55);
        make.left.equalTo(ws.view).offset(16);
        make.right.equalTo(ws.view).offset(-16);
        make.height.mas_equalTo(44);
    }];
    
    UIButton *completButton = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 50, 50)];
    [completButton setTitle:@"完成" forState:UIControlStateNormal];
    [completButton setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    completButton.titleLabel.font = [UIFont systemFontOfSize:16];
    [completButton addTarget:self action:@selector(faildComplete) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:completButton];
    
    if (_notCanRePay) {
        self.rePayButton.hidden = YES;
    }
}

- (void)payComplete {
    if (_isRegistration) {
        [self goResult];
    } else {
        NSUInteger index = [self.navigationController.viewControllers indexOfObject:self];
        if (index >= 2) {
            UIViewController *viewController = [self.navigationController.viewControllers objectAtIndex:index - 2];
            [self.navigationController popToViewController:viewController animated:YES];
        }
        else {
            [self.navigationController popViewControllerAnimated:YES];
        }
    }
}

- (void)faildComplete {
    if (_isRegistration) {
        [self.navigationController popToRootViewControllerAnimated:YES];
    } else {
        NSUInteger index = [self.navigationController.viewControllers indexOfObject:self];
        if (index >= 2) {
            UIViewController *viewController = [self.navigationController.viewControllers objectAtIndex:index - 2];
            [self.navigationController popToViewController:viewController animated:YES];
        }
        else {
            [self.navigationController popViewControllerAnimated:YES];
        }
    }
}

- (void)goResult {
    SCMyPreorderDetailViewController *vc = [SCMyPreorderDetailViewController new];
    vc.OrderIdString = self.orderId;
    vc.isShowTitlePopRoot = YES;
    [self.navigationController pushViewController:vc animated:YES];
}

@end
