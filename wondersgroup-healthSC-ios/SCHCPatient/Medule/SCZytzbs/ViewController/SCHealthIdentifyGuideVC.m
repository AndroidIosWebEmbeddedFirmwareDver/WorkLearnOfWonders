//
//  WDHealthIdentifyGuideVC.m
//  SHHealthCloudNormal
//
//  Created by wanda on 16/8/9.
//  Copyright © 2016年 WondersGroup. All rights reserved.
//

#import "SCHealthIdentifyGuideVC.h"
#import "SCHealthIdentifyViewController.h"
#import "SCorderDetailViewController.h"
@interface SCHealthIdentifyGuideVC ()

@end

@implementation SCHealthIdentifyGuideVC

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setupview];
    
}

- (void)setupview
{
    UILabel *titleLb = [[UILabel alloc] init];
    titleLb.text = @"您属于以下哪种体质？";
    titleLb.backgroundColor = [UIColor clearColor];
    titleLb.textColor = [UIColor blackColor];
    titleLb.textAlignment = NSTextAlignmentCenter;
    titleLb.font = [UIFont systemFontOfSize:14.0];
    [self.view addSubview:titleLb];
    [titleLb mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view);
        make.top.equalTo(self.view).offset(25);
        make.right.equalTo(self.view);
        make.height.mas_equalTo(25);
    }];
    
    UIImageView *bgimageView = [[UIImageView alloc] init];
    [self.view addSubview:bgimageView];
    bgimageView.image = [UIImage imageNamed:@"中医体质辨识img"];
    [bgimageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(self.view);
        make.top.equalTo(titleLb.mas_bottom).offset(15);
        make.left.equalTo(self.view).offset(25);
        make.right.equalTo(self.view).offset(-25);
        make.height.mas_equalTo(350);

    }];
    
    UIButton *startBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.view addSubview:startBtn];
    startBtn.layer.cornerRadius = 22.0;
    startBtn.layer.borderColor = [[UIColor tc5Color] CGColor];
    startBtn.layer.borderWidth = 1.0;
    [startBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(55);
        make.bottom.equalTo(self.view.mas_bottom).offset(-40);
        make.right.equalTo(self.view).offset(-55);
        make.height.mas_equalTo(44);
    }];
    [startBtn setTitle:@"开始评估" forState:UIControlStateNormal];
    [startBtn setBackgroundColor:[UIColor bc7Color]];
    startBtn.titleLabel.font = [UIFont systemFontOfSize:16.0];
    //zytc0
    [startBtn setTitleColor:[UIColor tc1Color] forState:UIControlStateNormal];
    [[startBtn rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
        SCHealthIdentifyViewController *HealthIdentifyViewController = [[SCHealthIdentifyViewController alloc] init];
        HealthIdentifyViewController.title = @"中医体质辨识";
        [self.navigationController pushViewController:HealthIdentifyViewController animated:YES];
    }];
    
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



@end
