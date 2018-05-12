//
//  AppointmentResultViewController.m
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/9.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "AppointmentResultViewController.h"

@interface AppointmentResultViewController ()

@property (nonatomic, strong) UIImageView *typeImageView;
@property (nonatomic, strong) UILabel *typeTitleLabel;
@property (nonatomic, strong) UILabel *typeContentLabel;
@property (nonatomic, strong) UIButton *finishButton;
@property (nonatomic, strong) UIButton *againButton;
@property (nonatomic, strong) UIButton *gaveUpButton;


@end

@implementation AppointmentResultViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [AppointmentResultViewModel new];
        self.hasBack = NO;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self bindViewModel];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    
}

#pragma mark    - setupView
-(void)setupView {
    self.navigationItem.title = @"转诊信息";
    self.view.backgroundColor = [UIColor bc2Color];
    
    UIView *backView = [UISetupView setupViewWithSuperView:self.view withBackGroundColor:[UIColor bc1Color]];
    [backView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.bottom.equalTo(self.view);
        make.top.equalTo(self.view).offset(10);
    }];
    
    self.typeImageView = [UISetupView setupImageViewWithSuperView:backView withImageName:@""];
    [self.typeImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(backView);
        make.top.equalTo(backView).offset((SCREEN_HEIGHT-90-44-10-64-62)/2.0);
        make.size.mas_equalTo(CGSizeMake(62, 62));
    }];
    
    self.typeTitleLabel = [UISetupView setupLabelWithSuperView:backView withText:@"" withTextColor:[UIColor tc1Color] withFontSize:18];
    [self.typeTitleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(backView);
        make.top.equalTo(self.typeImageView.mas_bottom).offset(15);
    }];
    
    self.typeContentLabel = [UISetupView setupLabelWithSuperView:backView withText:@"" withTextColor:[UIColor tc3Color] withFontSize:14];
    [self.typeContentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(backView);
        make.top.equalTo(self.typeTitleLabel.mas_bottom).offset(10);
    }];

    self.gaveUpButton = [UISetupView setupButtonWithSuperView:backView withTitleToStateNormal:@"放弃预约" withTitleColorToStateNormal:[UIColor tc2Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        
    }];
    self.gaveUpButton.hidden = YES;
    self.gaveUpButton.backgroundColor = [UIColor bc1Color];
    [self.gaveUpButton setCornerRadius:4];
    [self.gaveUpButton setborderWithColor:[UIColor dc1Color] withWidth:0.5];
    [self.gaveUpButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(backView).offset(15);
        make.centerX.equalTo(backView);
        make.height.mas_equalTo(44);
        make.bottom.equalTo(backView).offset(-25);
    }];
    
    self.againButton = [UISetupView setupButtonWithSuperView:backView withTitleToStateNormal:@"重新预约" withTitleColorToStateNormal:[UIColor tc0Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        
    }];
    self.againButton.hidden = YES;
    self.againButton.backgroundColor = [UIColor bc7Color];
    [self.againButton setCornerRadius:4];
    [self.againButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(backView).offset(15);
        make.centerX.equalTo(backView);
        make.height.mas_equalTo(44);
        make.bottom.equalTo(self.gaveUpButton.mas_top).offset(-20);
    }];
    
    self.finishButton = [UISetupView setupButtonWithSuperView:backView withTitleToStateNormal:@"完成" withTitleColorToStateNormal:[UIColor tc0Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        
    }];
    self.finishButton.backgroundColor = [UIColor bc7Color];
    [self.finishButton setCornerRadius:4];
    [self.finishButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(backView).offset(15);
        make.centerX.equalTo(backView);
        make.height.mas_equalTo(44);
        make.bottom.equalTo(backView).offset(-30);
    }];
    
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    [RACObserve(self.viewModel, isSuccess) subscribeNext:^(NSNumber *x) {
        if ([x boolValue]) {
            self.typeImageView.image = [UIImage imageNamed:@"预约成功"];
            self.typeTitleLabel.text = @"预约成功";
            self.typeContentLabel.hidden = YES;
            self.finishButton.hidden = NO;
            self.againButton.hidden = YES;
            self.gaveUpButton.hidden = YES;
        }else {
            self.typeImageView.image = [UIImage imageNamed:@"预约失败"];
            self.typeTitleLabel.text = @"预约失败";
            self.typeContentLabel.hidden = NO;
            self.finishButton.hidden = YES;
            self.againButton.hidden = NO;
            self.gaveUpButton.hidden = NO;
        }
    }];
    
    [RACObserve(self.viewModel, errorString) subscribeNext:^(NSString *x) {
        if (x) {
            self.typeContentLabel.text = x;
        }
    }];
}


@end
