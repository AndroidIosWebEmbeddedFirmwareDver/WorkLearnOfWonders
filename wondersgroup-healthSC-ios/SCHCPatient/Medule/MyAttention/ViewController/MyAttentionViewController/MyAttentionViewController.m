//
//  MyAttentionViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "MyAttentionViewController.h"
#import "MyAttentionViewModel.h"
#import "MyAttentionSegmentViewController.h"
#import "AttentionDoctorViewController.h"
#import "AttentionHospitalViewController.h"
#import "VTMagic.h"

@interface MyAttentionViewController () <VTMagicViewDelegate, VTMagicViewDataSource>

@property (nonatomic, strong) MyAttentionViewModel *viewModel;

@property (nonatomic, strong) VTMagicController *magicController;

@end

@implementation MyAttentionViewController
- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.navigationItem.title = @"我的关注";

    [self.view addSubview:self.magicController.view];
    WS(weakSelf)
    [_magicController.view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.view);
    }];
    [_magicController didMoveToParentViewController:self];
    [_magicController.magicView reloadData];
    
}
- (UIViewController<VTMagicProtocol> *)magicController {
    if (!_magicController) {
        _magicController = [[VTMagicController alloc] init];
        _magicController.magicView.navigationColor  = [UIColor whiteColor];
        _magicController.magicView.sliderColor      = [UIColor bc7Color];
        _magicController.magicView.sliderHeight     = 2;
        _magicController.magicView.sliderWidth      = 88;
        _magicController.magicView.layoutStyle      = VTLayoutStyleDivide;
        _magicController.magicView.switchStyle      = VTSwitchStyleDefault;
        _magicController.magicView.navigationHeight = 44;
        _magicController.magicView.dataSource       = self;
        _magicController.magicView.delegate         = self;
        _magicController.magicView.needPreloading   = NO;
        _magicController.magicView.separatorColor = [UIColor dc1Color];
        [self addChildViewController:self.magicController];
    }
    
    return _magicController;
}


#pragma mark - VTmagicViewDataSource

- (NSArray<__kindof NSString *> *)menuTitlesForMagicView:(VTMagicView *)magicView {
    NSArray *menuArr = @[@"关注的医生", @"关注的医院"];
    return menuArr;
}

- (UIButton *)magicView:(VTMagicView *)magicView menuItemAtIndex:(NSUInteger)itemIndex {
    static NSString *identifier = @"myAttentionVCItemButton";
    UIButton *menuBtn = [magicView dequeueReusableItemWithIdentifier:identifier];
    if (!menuBtn) {
        menuBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        [menuBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateSelected];
        [menuBtn setTitleColor:[UIColor tc1Color] forState:UIControlStateNormal];
        menuBtn.titleLabel.font = [UIFont systemFontOfSize:14.0];
    }
    
    return menuBtn;
}

- (UIViewController *)magicView:(VTMagicView *)magicView viewControllerAtPage:(NSUInteger)pageIndex {
    
    if (pageIndex == 0) {
        static NSString *testVC = @"AttentionDoctorViewController";
        Class FileListViewController = NSClassFromString(testVC);
        BaseViewController *vc = [magicView dequeueReusablePageWithIdentifier:testVC];
        if (!vc) {
            vc = [[FileListViewController alloc] init];
        }
        
        return vc;
    }
    else if (pageIndex == 1) {
        static NSString *testVC = @"AttentionHospitalViewController";
        Class FileListViewController = NSClassFromString(testVC);
        BaseViewController *vc = [magicView dequeueReusablePageWithIdentifier:testVC];
        if (!vc) {
            vc = [[FileListViewController alloc] init];
        }
        
        return vc;
    }
    else {
        return nil;
    }
    
}


@end
