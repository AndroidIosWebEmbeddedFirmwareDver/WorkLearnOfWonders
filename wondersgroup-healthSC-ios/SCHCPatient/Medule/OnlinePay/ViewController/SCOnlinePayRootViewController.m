//
//  SCOnlinePayRootViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCOnlinePayRootViewController.h"
#import <VTMagic/VTMagic.h>
#import "SCRegistrationPayViewController.h"
#import "SCPrescriptionPayViewController.h"
#import "SCInhospitalPayViewController.h"
#import "SCExtractOrdersViewController.h"

#import "SCInHospitalVC.h"

@interface SCOnlinePayRootViewController () <VTMagicViewDataSource,VTMagicViewDelegate>

@property (nonatomic, strong) VTMagicController *magicController;

@end

@implementation SCOnlinePayRootViewController
- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
    }
    return self;
}

#pragma mark lazyLoad
- (VTMagicController *)magicController {
    if (!_magicController) {
        _magicController = [VTMagicController new];
        _magicController.magicView.navigationColor = [UIColor bc1Color];
        _magicController.magicView.sliderColor = [UIColor bc7Color];
        _magicController.magicView.sliderHeight = 2;
        _magicController.magicView.layoutStyle = VTLayoutStyleDivide;
        _magicController.magicView.switchStyle = VTSwitchStyleDefault;
        _magicController.magicView.navigationHeight = 40.f;
        _magicController.magicView.dataSource = self;
        _magicController.magicView.delegate = self;
        _magicController.magicView.sliderExtension = 0;
        UIView *lineView = [UIView new];
        lineView.backgroundColor = [UIColor bc3Color];
        [_magicController.magicView.navigationView addSubview:lineView];
        
        [lineView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.right.bottom.equalTo(_magicController.magicView.navigationView);
            make.height.mas_equalTo(0.5);
        }];
        
    }
    return _magicController;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
}

- (void)setupView {
    self.title = @"在线支付";
    
    [self addChildViewController:self.magicController];
    [self.view addSubview:_magicController.view];
    WS(weakSelf)
    [_magicController.view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.view);
    }];
    [_magicController.magicView reloadData];
}

#pragma mark VTMagicViewDataSource
- (NSArray<NSString *> *)menuTitlesForMagicView:(VTMagicView *)magicView {
    return @[@"挂号费支付",@"诊间支付",@"住院金预缴"];
}

- (UIButton *)magicView:(VTMagicView *)magicView menuItemAtIndex:(NSUInteger)itemIndex {
    UIButton *menuItem = [UIButton buttonWithType:UIButtonTypeCustom];
    [menuItem setTitleColor:[UIColor tc2Color] forState:UIControlStateNormal];
    [menuItem setTitleColor:[UIColor tc5Color] forState:UIControlStateSelected];
    menuItem.titleLabel.font = [UIFont systemFontOfSize:14];
    return menuItem;
}

- (UIViewController *)magicView:(VTMagicView *)magicView viewControllerAtPage:(NSUInteger)pageIndex {
    //    WS(ws)
    switch (pageIndex) {
        case 0: {
            SCRegistrationPayViewController *vc = [SCRegistrationPayViewController new];
            return vc;
        }
            break;
            
        case 1: {
            SCExtractOrdersViewController *vc = [SCExtractOrdersViewController new];
            //            vc.doPsuh = ^(){
            //                SCPrescriptionPayViewController *vc = [SCPrescriptionPayViewController new];
            //                [ws.navigationController pushViewController:vc animated:YES];
            //            };
            return vc;
        }
            break;
            
        case 2: {
            SCInHospitalVC * vc = [[SCInHospitalVC alloc] init];
            //            SCInhospitalPayViewController *vc = [SCInhospitalPayViewController new];
            return vc;
        }
            break;
            
        default:
            return nil;
            break;
    }
}

@end
