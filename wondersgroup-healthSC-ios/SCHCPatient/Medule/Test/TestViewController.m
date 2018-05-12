//
//  TestViewController.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "TestViewController.h"

#import "SCDepartmentViewController.h"

@interface TestViewController ()

@end

@implementation TestViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    UIButton * butt = [UISetupView setupButtonWithSuperView:self.view withTitleToStateNormal:@"选择科室" withTitleColorToStateNormal:[UIColor tc2Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        SCDepartmentViewController *vc = [SCDepartmentViewController new];
        vc.hidesBottomBarWhenPushed = YES;
        [self.navigationController pushViewController:vc animated:YES];
    
    }];
    [butt setBackgroundColor:[UIColor redColor]];
    [butt mas_makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(40);
        make.top.equalTo(self.view).offset(0);
        make.centerX.equalTo(self.view);
    }];

    
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
