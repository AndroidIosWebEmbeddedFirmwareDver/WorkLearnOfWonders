//
//  HealthCardViewController.m
//  SCHCPatient
//
//  Created by ZJW on 2017/8/22.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "HealthCardViewController.h"

@interface HealthCardViewController ()

@end

@implementation HealthCardViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        
    }
    return self;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.navigationItem.title = @"居民健康卡";

    [self setupView];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark    - setupView
-(void)setupView {
    
    self.view.backgroundColor = [UIColor bc2Color];
    
    UIImageView *imageView = [UISetupView setupImageViewWithSuperView:self.view withImageName:@"03_1健康卡大背景"];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(self.view);
    }];
    
    UIImageView *cardView = [UISetupView setupImageViewWithSuperView:self.view withImageName:@"健康卡卡片背景"];
    [cardView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(self.view).offset(65);
        make.size.mas_equalTo(CGSizeMake(636/2.0, 439/2.0));
    }];
    
    UIImageView *headIcon = [UISetupView setupImageViewWithSuperView:cardView withImageName:@"默认用户男164"];
    [headIcon mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(cardView).offset(27+15);
        make.top.equalTo(cardView).offset(23);
        make.size.mas_equalTo(CGSizeMake(72, 72));
    }];
    
    UILabel *cardLabel = [UISetupView setupLabelWithSuperView:cardView withText:self.idcard withTextColor:[UIColor whiteColor] withFontSize:20];
    [cardLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(headIcon);
        make.top.equalTo(headIcon.mas_bottom).offset(22.5);
        make.right.equalTo(cardView).offset(-27);
    }];
    
    UILabel *nameLabel = [UISetupView setupLabelWithSuperView:cardView withText:self.name withTextColor:[UIColor whiteColor] withFontSize:16];
    [nameLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(headIcon);
        make.top.equalTo(cardLabel.mas_bottom).offset(15);
        make.right.equalTo(cardView).offset(-27);
    }];
    
}


@end
