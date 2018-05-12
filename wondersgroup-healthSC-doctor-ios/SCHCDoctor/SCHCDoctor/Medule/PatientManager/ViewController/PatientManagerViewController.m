//
//  PatientManagerViewController.m
//  SCHCDoctor
//
//  Created by Gu Jiajun on 2017/6/7.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientManagerViewController.h"
#import "PatientManagerViewModel.h"
#import "PatientsListViewController.h"
#import "VTMagic.h"
#import "PatientSearchView.h"
#import "PatientDetailViewController.h"
#import "NewAddViewController.h"
#import "PatientSearchViewController.h"
#import "NewAddPatientButton.h"

@interface PatientManagerViewController () <VTMagicViewDataSource, VTMagicViewDelegate>

@property (nonatomic, strong) PatientManagerViewModel *viewModel;
@property (nonatomic, strong) VTMagicController *magicController;
@property (nonatomic, strong) PatientSearchView *patientSearchView;
@property (nonatomic, strong) NewAddPatientButton *addNewPatientBtn;

@end

@implementation PatientManagerViewController

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [PatientManagerViewModel new];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"签约患者";
    
    [self setupView];
    [self bindViewModel];
    
    [self requestData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)setupView {
    
    self.view.backgroundColor = [UIColor bc2Color];
    
    _addNewPatientBtn = [[NewAddPatientButton alloc] initWithFrame:CGRectMake(0, 0, 50, 50)];
    [_addNewPatientBtn addTarget:self action:@selector(addButtonAction) forControlEvents:UIControlEventTouchUpInside];
    _addNewPatientBtn.isShowRedPoint = YES;
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_addNewPatientBtn];
    
    WS(weakSelf)
    _patientSearchView = [[PatientSearchView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 62)];
    _patientSearchView.searchActionBlock = ^(NSString *keyword) {
        NSLog(@"keyword:%@",keyword);
        PatientSearchViewController *vc = [[PatientSearchViewController alloc] init];
        vc.keyword = keyword;
        [weakSelf.navigationController pushViewController:vc animated:YES];
    };
    [self.view addSubview:_patientSearchView];
    
    
    [self addChildViewController:self.magicController];
    _magicController.view.frame = CGRectMake(0, _patientSearchView.bottom+10, SCREEN_WIDTH, self.view.height-_patientSearchView.height-10-64);
    [self.view addSubview:_magicController.view];
    
    [_magicController.magicView reloadData];
}

- (VTMagicController *)magicController {
    if (!_magicController) {
        _magicController = [[VTMagicController alloc] init];
        _magicController.magicView.navigationColor = [UIColor whiteColor];
        _magicController.magicView.sliderColor = [UIColor tc5Color];
        _magicController.magicView.layoutStyle = VTLayoutStyleCenter;
        _magicController.magicView.switchStyle = VTSwitchStyleDefault;
        _magicController.magicView.navigationHeight = 44.f;
        _magicController.magicView.dataSource = self;
        _magicController.magicView.delegate = self;
        _magicController.magicView.sliderWidth = 60;
        _magicController.magicView.sliderHeight = 1.5;
        _magicController.magicView.itemSpacing = (SCREEN_WIDTH-3*30)/4;
        _magicController.magicView.separatorColor = [UIColor bc3Color];
        _magicController.magicView.separatorHeight = 0.5;
    }
    return _magicController;
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    @weakify(self)
    [RACObserve(self.viewModel, hasNewlyAdded) subscribeNext:^(NSNumber *x) {
        //显示红点
        @strongify(self)
        self.addNewPatientBtn.isShowRedPoint = [x boolValue];
    }];
//    WS(weakSelf)
//    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
//        if ([type intValue] == 0) {
//            return ;
//        }
//        [LoadingView hideLoadinForView:self.view];
//        [weakSelf endRefreshing];
//        
//        FailViewType failType = FailViewUnknow;
//        switch ([type intValue]) {
//            case RequestCompeleteEmpty:{
//                [weakSelf.view showFailView:FailViewEmpty withAction:^{
//                    [weakSelf reloadData];
//                }];
//            }
//                break;
//            case RequestCompeleteNoWifi:
//                failType = FailViewNoWifi;
//                break;
//            case RequestCompeleteError:
//                failType = FailViewError;
//                break;
//            case RequestCompeleteSuccess: {
//                [weakSelf.view hiddenFailView];
//                [weakSelf.myTableView reloadData];
//                failType = FailViewUnknow;
//            }
//                break;
//            default:
//                break;
//        }
//        if (failType != FailViewUnknow && failType != FailViewEmpty) {
//            [weakSelf.view showFailView:failType withAction:^{
//                [weakSelf reloadData];
//            }];
//        }
//    }];

}

#pragma mark requestData
- (void)requestData {
    [self.viewModel getNewPatientsPrompt:^{
        
    } failed:^(NSError *error) {
        
    }];

}

#pragma mark VTMagicViewDataSource
- (NSArray<NSString *> *)menuTitlesForMagicView:(VTMagicView *)magicView {
    return @[@"全部",@"重点",@"贫困"];
}

- (UIButton *)magicView:(VTMagicView *)magicView menuItemAtIndex:(NSUInteger)itemIndex {
    static NSString *itemIdentifier = @"itemIdentifier";
    UIButton *menuItem = [magicView dequeueReusableItemWithIdentifier:itemIdentifier];
    if (!menuItem) {
        menuItem = [UIButton buttonWithType:UIButtonTypeCustom];
        [menuItem setTitleColor:[UIColor tc1Color] forState:UIControlStateNormal];
        [menuItem setTitleColor:[UIColor tc5Color] forState:UIControlStateSelected];
        menuItem.titleLabel.font = [UIFont fontWithName:@"Helvetica" size:14.f];
    }
    return menuItem;
}

- (UIViewController *)magicView:(VTMagicView *)magicView viewControllerAtPage:(NSUInteger)pageIndex {
    
    static NSString *identifier = @"patients.identifier";
    PatientsListViewController *vc = [magicView dequeueReusablePageWithIdentifier:identifier];
    if (!vc) {
        vc = [[PatientsListViewController alloc] initWithPatientTag:pageIndex];
    }
    
    WS(weakSelf)
    vc.selectBlock = ^(PatientListModel *model) {
        PatientDetailViewController *vc = [[PatientDetailViewController alloc] init];
        vc.model = model;
        [weakSelf.navigationController pushViewController:vc animated:YES];
    };
    
    return vc;
}

#pragma mark VTMagicViewDelegate

- (void)magicView:(VTMagicView *)magicView viewDidAppear:(UIViewController *)viewController atPage:(NSUInteger)pageIndex {
//    NSLog(@"pageIndex:%ld viewDidAppear:%@", (long)pageIndex, viewController.view);
}

- (void)magicView:(VTMagicView *)magicView viewDidDisappear:(UIViewController *)viewController atPage:(NSUInteger)pageIndex {
//    NSLog(@"pageIndex:%ld viewDidDisappear:%@", (long)pageIndex, viewController.view);
}

- (void)magicView:(VTMagicView *)magicView didSelectItemAtIndex:(NSUInteger)itemIndex {
//    NSLog(@"didSelectItemAtIndex:%ld", (long)itemIndex);
}

#pragma mark addButtonAction
- (void)addButtonAction {
    NewAddViewController *vc = [[NewAddViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
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
