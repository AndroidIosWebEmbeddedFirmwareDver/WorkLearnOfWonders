//
//  MyOrderViewController.m
//  HCPatient
//
//  Copyright © 2016年 非丶白. All rights reserved.
//

#import "SCMyOrderViewController.h"
#import "SCMyOrderViewModel.h"
#import "SCMyOrderListViewController.h"
#import "SCorderDetailViewController.h"
#import <VTMagic/VTMagic.h>
@interface SCMyOrderViewController () <VTMagicViewDataSource,VTMagicViewDelegate>
{
    BOOL _currentIndex;
}

@property (strong, nonatomic) SCMyOrderViewModel *viewModel;

@property (strong, nonatomic) NSArray *viewControllersArray;

@property (nonatomic, strong) VTMagicController *magicController;
@end

@implementation SCMyOrderViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    [self prepareData];
    [self prepareUI];
}


#pragma mark lazyLoad
- (VTMagicController *)magicController {
    if (!_magicController) {
        _magicController = [VTMagicController new];
        _magicController.magicView.navigationColor = [UIColor whiteColor];
        _magicController.magicView.sliderColor = [UIColor bc7Color];
        _magicController.magicView.sliderHeight = 2;
        _magicController.magicView.layoutStyle = VTLayoutStyleDivide;
        _magicController.magicView.switchStyle = VTSwitchStyleDefault;
        _magicController.magicView.navigationHeight = 40.f;
        _magicController.magicView.dataSource = self;
        _magicController.magicView.delegate = self;
        _magicController.magicView.sliderWidth = SCREEN_WIDTH/5-20;
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

#pragma mark VTMagicViewDataSource
- (NSArray<NSString *> *)menuTitlesForMagicView:(VTMagicView *)magicView {
    return self.viewModel.topTabs;
}



- (UIButton *)magicView:(VTMagicView *)magicView menuItemAtIndex:(NSUInteger)itemIndex {
    UIButton *menuItem = [UIButton buttonWithType:UIButtonTypeCustom];
    [menuItem setTitleColor:[UIColor tc3Color] forState:UIControlStateNormal];
    [menuItem setTitleColor:[UIColor tc5Color] forState:UIControlStateSelected];
    menuItem.titleLabel.font = [UIFont systemFontOfSize:14];
    return menuItem;
}

- (void)viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
}

- (void)popBack {
    
    [[VCManager manager] showTabbarControllerAtIndex:3];
    [self.navigationController popToRootViewControllerAnimated:YES];
}

- (void)prepareData {
    
    self.viewModel = [[SCMyOrderViewModel alloc] init];
}

- (void)prepareUI {
    self.navigationItem.title = @"我的订单";
    NSMutableArray *array = [NSMutableArray array];
    NSInteger count = self.viewModel.topTabs.count;
     for (int i = 0; i < count; i++) {        
        SCMyOrderListViewController *viewController = [[SCMyOrderListViewController alloc] init];
        viewController.type = [self.viewModel.typesArray[i] integerValue];
        viewController.title = self.viewModel.topTabs[i];
        viewController.mParentViewController = self;

        [array addObject:viewController];
    }
    self.viewControllersArray = [NSArray arrayWithArray:array];
    
    [self addChildViewController:self.magicController];
    [self.view addSubview:_magicController.view];
    WS(weakSelf)
    [_magicController.view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.view);
    }];
    [_magicController didMoveToParentViewController:self];

    [_magicController.magicView reloadData];
    [_magicController.magicView switchToPage:0 animated:NO];
    
}

- (UIViewController *)magicView:(VTMagicView *)magicView viewControllerAtPage:(NSUInteger)pageIndex {
    SCMyOrderListViewController *viewController = self.viewControllersArray[pageIndex];
    viewController.fatherViewController = self;
    _currentIndex = pageIndex;
    return viewController;
}



@end
