//
//  SCAppointmentDoctorTabViewController.m
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDoctorTabViewController.h"
#import "VTMagic.h"
#import "SCAppointmentDoctorNameViewController.h"
#import "SCAppointmentDoctorDateViewController.h"

@interface SCAppointmentDoctorTabModel : NSObject
@property (nonatomic, strong) NSString *name;
@property (nonatomic, strong) NSString *className;
-(instancetype)initWithName:(NSString *)name className:(NSString *)className;
@end

@implementation SCAppointmentDoctorTabModel

-(instancetype)initWithName:(NSString *)name className:(NSString *)className {
    self = [super init];
    if (self) {
        self.name = name;
        self.className = className;
    }
    return self;
}

@end

@interface SCAppointmentDoctorTabViewController ()<VTMagicViewDataSource,VTMagicViewDelegate>
@property (nonatomic, strong) VTMagicController *magicController;
@property (nonatomic, strong) SCAppointmentDoctorNameViewController *nameVC;
@property (nonatomic, strong) SCAppointmentDoctorDateViewController *dateVC;
@property (nonatomic, strong) NSMutableArray *dataArray;

@end

@implementation SCAppointmentDoctorTabViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.navigationItem.title = self.model.deptName;
    
    [self combineData];
    [self prepareMagicController];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Data
- (void)combineData {
    [self.dataArray addObject:[[SCAppointmentDoctorTabModel alloc] initWithName:@"按专家预约" className:@"SCAppointmentDoctorNameViewController"]];
    [self.dataArray addObject:[[SCAppointmentDoctorTabModel alloc] initWithName:@"按日期预约" className:@"SCAppointmentDoctorDateViewController"]];
}

- (void)prepareMagicController {
    [self.view addSubview:self.magicController.view];
    WS(weakSelf)
    [_magicController.view mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.view);
    }];
    [_magicController didMoveToParentViewController:self];
    [_magicController.magicView reloadData];
}

#pragma mark - VTMagic

- (NSArray<NSString *> *)menuTitlesForMagicView:(VTMagicView *)magicView {
    NSMutableArray *array = [NSMutableArray array];
    [self.dataArray enumerateObjectsUsingBlock:^(SCAppointmentDoctorTabModel * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        [array addObject:obj.name];
    }];
    return array;
}


- (UIButton *)magicView:(VTMagicView *)magicView menuItemAtIndex:(NSUInteger)itemIndex {
    UIButton *menuItem = [magicView dequeueReusableItemWithIdentifier:@"SCAppointmentDoctorTabViewController"];
    
    if (!menuItem) {
        menuItem = [UIButton buttonWithType:UIButtonTypeCustom];
        [menuItem setTitleColor:[UIColor tc1Color] forState:UIControlStateNormal];
        [menuItem setTitleColor:[UIColor tc5Color] forState:UIControlStateSelected];
        menuItem.titleLabel.font = [UIFont systemFontOfSize:14];
        
    }
    return menuItem;
}


- (UIViewController *)magicView:(VTMagicView *)magicView viewControllerAtPage:(NSUInteger)pageIndex {
    SCAppointmentDoctorTabModel *model = self.dataArray[pageIndex];
    
    NSString *REUSE_ID = [NSString stringWithFormat:@"REUSE_ID_%tu", pageIndex];
    UIViewController *vc = [magicView dequeueReusablePageWithIdentifier:REUSE_ID];
    
    if (!vc) {
        vc = [[NSClassFromString(model.className) alloc] init];
        ((SCAppointmentDoctorNameViewController *)vc).viewModel.hospitalCode = self.model.hosOrgCode;
        ((SCAppointmentDoctorNameViewController *)vc).viewModel.hosDeptCode = self.model.hosDeptCode;        
    }
    return vc;
}

#pragma mark - Lazy Loading

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


- (NSMutableArray *)dataArray {
    if (!_dataArray) {
        _dataArray = [NSMutableArray array];
    }
    return _dataArray;
}


@end
