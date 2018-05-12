//
//  HealthFilesViewController.m
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/11.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "HealthFilesViewController.h"
#import "VTMagic.h"
#import "HealthFileListViewController.h"


@interface HealthFilesViewController () <VTMagicViewDelegate,VTMagicViewDataSource>

@property (strong, nonatomic) VTMagicController *magicController;

@end

@implementation HealthFilesViewController


- (void)viewWillAppear:(BOOL)animated {
    
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = NO;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self prepareData];
    [self prepareUI];
}

- (void)prepareData {
    
    
}

- (void)prepareUI {
    
    self.navigationItem.title = @"健康档案";
    
    self.magicController = [[VTMagicController alloc] init];
    self.magicController.magicView.navigationColor  = [UIColor bc1Color];
    self.magicController.magicView.sliderColor      = [UIColor tc5Color];
    self.magicController.magicView.layoutStyle      = VTLayoutStyleDefault;
    self.magicController.magicView.switchStyle      = VTSwitchStyleDefault;
    self.magicController.magicView.navigationHeight = 40.0;
    self.magicController.magicView.dataSource       = self;
    self.magicController.magicView.delegate         = self;
    self.magicController.magicView.itemWidth        = self.view.width/4;
    self.magicController.magicView.sliderWidth      = 88.0;
    [self addChildViewController:self.magicController];
    
    [self.view addSubview:self.magicController.view];
    
    [self.magicController.magicView reloadData];
}

#pragma mark - VTmagicViewDataSource

- (NSArray<__kindof NSString *> *)menuTitlesForMagicView:(VTMagicView *)magicView {
    
    NSArray *menuArr = @[@"就诊记录", @"查验报告", @"电子处方", @"住院史"];
    return menuArr;
}

- (UIButton *)magicView:(VTMagicView *)magicView menuItemAtIndex:(NSUInteger)itemIndex {
    
    static NSString *identifier = @"itemBtn";
    UIButton *menuBtn = [magicView dequeueReusableItemWithIdentifier:identifier];
    if (!menuBtn) {
        menuBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        [menuBtn setTitleColor:[UIColor colorWithHex:0x2e7af0] forState:UIControlStateSelected];
        [menuBtn setTitleColor:[UIColor tc1Color] forState:UIControlStateNormal];
        menuBtn.titleLabel.font = [UIFont systemFontOfSize:14.0];
    }
    
    return menuBtn;
}

- (UIViewController *)magicView:(VTMagicView *)magicView viewControllerAtPage:(NSUInteger)pageIndex {
    
    static NSString *testVC = @"HealthFileListViewController";
    Class FileListViewController = NSClassFromString(testVC);
    BaseViewController *vc = [magicView dequeueReusablePageWithIdentifier:testVC];
    if (!vc) {
        vc = [[FileListViewController alloc] init];
    }
    return vc;
    
}


@end
