//
//  ReferralResultVC.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralResultVC.h"

@interface ReferralResultVC ()

@property (strong, nonatomic) UILabel       * topBlankLabel;
@property (strong, nonatomic) UIButton      * finishButton;
@property (strong, nonatomic) UIButton      * cancelButton;
@property (strong, nonatomic) UIButton      * reSetButton;

@property (strong, nonatomic) UIImageView   * imageView;
@property (strong, nonatomic) UILabel       * remindTitleLabel;
@property (strong, nonatomic) UILabel       * remindDetailLabel;

@property (assign, nonatomic) BOOL            isSuccess;            //成功与否
@end

@implementation ReferralResultVC
- (instancetype)initWithResult:(BOOL)isSuccess {
    self = [super init];
    if (self) {
        _isSuccess = isSuccess;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setTitle:@"转诊信息"];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self setupView];
    [self buildConstraint];
    [self bindRAC];
}

- (void)setupView {
    _topBlankLabel = [[UILabel alloc] init];
    [_topBlankLabel setBackgroundColor:RGB_COLOR(244, 244, 244)];
    [self.view addSubview:_topBlankLabel];
    
    _imageView = [[UIImageView alloc] init];
    [_imageView setContentMode:UIViewContentModeCenter];
    NSString * imageName = _isSuccess ? @"预约成功" : @"预约失败";
    [_imageView setImage:[UIImage imageNamed:imageName]];
    [self.view addSubview:_imageView];
    
    _remindTitleLabel = [[UILabel alloc] init];
    [_remindTitleLabel setTextColor:RGB_COLOR(51, 51, 51)];
    [_remindTitleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:18]];
    [_remindTitleLabel setTextAlignment:NSTextAlignmentCenter];
    _remindTitleLabel.text = _isSuccess ? @"预约成功" : @"预约失败";
    [self.view addSubview:_remindTitleLabel];
    
    if (_isSuccess) {
        _finishButton = [[UIButton alloc] init];
        [_finishButton setBackgroundColor:RGB_COLOR(46, 122, 240)];
        [_finishButton setTitle:@"完成" forState:UIControlStateNormal];
        [_finishButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [_finishButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];
        [_finishButton.layer setCornerRadius:2];
        [self.view addSubview:_finishButton];
    } else {
        _remindDetailLabel = [[UILabel alloc] init];
        [_remindDetailLabel setText:@"号源已被抢，请重新预约"];
        [_remindDetailLabel setTextColor:RGB_COLOR(153, 153, 153)];
        [_remindDetailLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:14]];
        [_remindDetailLabel setTextAlignment:NSTextAlignmentCenter];
        [self.view addSubview:_remindDetailLabel];
        
        _reSetButton = [[UIButton alloc] init];
        [_reSetButton setBackgroundColor:RGB_COLOR(46, 122, 240)];
        [_reSetButton setTitle:@"重新预约" forState:UIControlStateNormal];
        [_reSetButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [_reSetButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];
        [_reSetButton.layer setCornerRadius:2];
        [self.view addSubview:_reSetButton];
        
        _cancelButton = [[UIButton alloc] init];
        [_cancelButton setBackgroundColor:[UIColor whiteColor]];
        [_cancelButton setTitle:@"重新预约" forState:UIControlStateNormal];
        [_cancelButton setTitleColor:RGB_COLOR(102, 102, 102) forState:UIControlStateNormal];
        [_cancelButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];
        [_cancelButton.layer setCornerRadius:2];
        [_cancelButton.layer setBorderWidth:1];
        [_cancelButton.layer setBorderColor:RGB_COLOR(244, 244, 244).CGColor];
        [self.view addSubview:_cancelButton];
    }
}

- (void)buildConstraint {
    [_topBlankLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.top.right.equalTo(self.view);
        make.height.mas_equalTo(10);
    }];
    
    [_remindTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_centerY);
        make.left.right.equalTo(self.view);
        make.height.mas_equalTo(18);
    }];
    
    [_imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.bottom.equalTo(_remindTitleLabel.mas_top).offset(-14);
        make.width.height.mas_equalTo(62);
    }];
    
    if (_isSuccess) {
        [_finishButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.leftMargin.mas_equalTo(15);
            make.rightMargin.mas_equalTo(-15);
            make.bottom.equalTo(self.view).offset(- 30);
            make.height.mas_equalTo(44);
        }];
    } else {
        [_cancelButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.leftMargin.mas_equalTo(15);
            make.rightMargin.mas_equalTo(-15);
            make.bottom.equalTo(self.view).offset(-30);
            make.height.mas_equalTo(44);
        }];
        
        [_reSetButton mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.equalTo(_cancelButton);
            make.bottom.equalTo(_cancelButton.mas_top).offset(-20);
            make.height.equalTo(_cancelButton);
        }];
        
        [_remindDetailLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.equalTo(_remindTitleLabel);
            make.top.equalTo(_remindTitleLabel.mas_bottom).offset(10);
            make.height.mas_equalTo(18);
        }];
    }
}

- (void)bindRAC {
    MJWeakSelf
    if (_isSuccess) {
        [[_finishButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
            [[NSNotificationCenter defaultCenter] postNotificationName:@"PopToReferralHomeVC" object:nil];
        }];
    } else {
        [[_reSetButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
            [weakSelf.navigationController popViewControllerAnimated:YES];
        }];
        
        [[_cancelButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
            [[NSNotificationCenter defaultCenter] postNotificationName:@"PopToReferralHomeVC" object:nil];
        }];
    }
}

#pragma mark - event

#pragma mark - function

#pragma mark - delegate

#pragma mark - notification

#pragma mark - setter

#pragma mark - getter


@end
