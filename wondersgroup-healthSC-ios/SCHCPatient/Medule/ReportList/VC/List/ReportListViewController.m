//
//  ReportListViewController.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ReportListViewController.h"
#import "VTMagic.h"
#import "CheckReportViewController.h"
#import "InspectionReportViewController.h"
@interface ReportListViewController ()<VTMagicViewDelegate,VTMagicViewDataSource>
@property (strong, nonatomic) VTMagicController *magicController;
@end

@implementation ReportListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"提取报告";
    [self addChildViewController:self.magicController];
    [self.view addSubview:_magicController.magicView];
     [_magicController.view mas_makeConstraints:^(MASConstraintMaker *make) {
          make.edges.equalTo(self.view);
     }];
    [_magicController.magicView reloadData];
    
}

- (VTMagicController *)magicController {
    if (!_magicController) {
        _magicController = [[VTMagicController alloc]init];
        _magicController.magicView.navigationColor  = [UIColor whiteColor];
        _magicController.magicView.sliderColor      = [UIColor colorWithHex:0x2e7af0];
        _magicController.magicView.layoutStyle      = VTLayoutStyleDefault;
        _magicController.magicView.switchStyle      = VTSwitchStyleDefault;
        _magicController.magicView.navigationHeight = 40.0;
        _magicController.magicView.dataSource       = self;
        _magicController.magicView.delegate         = self;
        _magicController.magicView.itemWidth        = self.view.width/2;
        _magicController.magicView.sliderWidth      = 88.0;
        _magicController.magicView.separatorColor   = [UIColor bc2Color];
    }
    return _magicController;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - VTmagicViewDataSource

- (NSArray<__kindof NSString *> *)menuTitlesForMagicView:(VTMagicView *)magicView {
    NSArray *menuArr = @[@"检验报告",@"检查报告"];
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
    if (pageIndex == 1) {
        static NSString *CheckReportVC = @"CheckReportViewController";
        CheckReportViewController *vc  = [magicView dequeueReusablePageWithIdentifier:CheckReportVC];
        if (!vc) {
            vc = [[CheckReportViewController alloc] init];
        }
        vc.viewModel.medicalOrgId          = self.medicalOrgId;
        vc.viewModel.day                   = self.day;
        return vc;
    }
    
    static NSString *InspectionReportVC = @"InspectionReportViewController";
    InspectionReportViewController *vc = [magicView dequeueReusablePageWithIdentifier:InspectionReportVC];
    if (!vc) {
        vc = [[InspectionReportViewController alloc] init];
    }
    vc.viewModel.medicalOrgId          = self.medicalOrgId;
    vc.viewModel.day                   = self.day;
    return vc;
    
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
