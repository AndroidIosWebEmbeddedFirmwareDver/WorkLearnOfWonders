//
//  SCAppointmentDoctorNameViewController.m
//  VaccinePatient
//
//  Created by ZJW on 2016/11/1.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDoctorNameViewController.h"
#import "SCAppointmentDoctorNameCell.h"
#import "WDAlertView.h"
#import "SCDoctorSchedulingViewController.h"
#import "WDShare.h"

@interface SCAppointmentDoctorNameViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) UITableView *myTableView;
@end

@implementation SCAppointmentDoctorNameViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [SCAppointmentDoctorNameViewModel new];

    }
    return self;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self setupView];
    [self bindViewModel];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark    - setupView
-(void)setupView {

    self.view.backgroundColor = [UIColor bc2Color];
//    WS(weakSelf)
    
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStylePlain withDelegateAndDataSource:self];
    self.myTableView.backgroundColor = [UIColor clearColor];
    self.myTableView.frame = CGRectMake(0, 0, self.view.width, self.view.height - 64 - 44);

//    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.left.right.top.equalTo(weakSelf.view);
//        make.bottom.equalTo(weakSelf.view).offset(-44-64);
//    }];
    
    MJRefreshGifHeader * header = [UIUtility headerRefreshTarget:self action:@selector(reloadData)];
    self.myTableView.mj_header = header;

    [self reloadData];

    [LoadingView showLoadingInView:self.view];
    
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
    WS(weakSelf)
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
        [weakSelf endRefreshing];
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:{
                [weakSelf.view showFailView:FailViewEmpty withAction:^{
                    [LoadingView showLoadingInView:weakSelf.view];
                    [weakSelf reloadData];
                }];
            }
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                [weakSelf.view hiddenFailView];
                [weakSelf.myTableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow && failType != FailViewEmpty) {
            [weakSelf.view showFailView:failType withAction:^{
                [LoadingView showLoadingInView:weakSelf.view];
                [weakSelf reloadData];
            }];
        }
    }];
    
    [RACObserve(self.viewModel, hasMore) subscribeNext:^(NSNumber *hasMore) {
        if ([hasMore boolValue]) {
            MJRefreshAutoNormalFooter * footer =[UIUtility footerMoreTarget:self action:@selector(reloadMoreData)];
            weakSelf.myTableView.mj_footer =footer;
        }
        else {
            weakSelf.myTableView.mj_footer =nil;
        }
    }];
}

#pragma mark    - reloadData
-(void)reloadData {
    [self.viewModel getAppointmentDoctorWithName];
}

-(void)reloadMoreData {
    [self.viewModel getAppointmentDoctorMoreWithName];
}

#pragma mark    - method
-(void)endRefreshing {
    [self.myTableView.mj_header endRefreshing];
    [self.myTableView.mj_footer endRefreshing];
}


#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return self.viewModel.datas.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    static NSString *identifier = @"SCAppointmentDoctorNameCell";
    
    SCAppointmentDoctorNameCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if(!cell)
    {
        cell = [[SCAppointmentDoctorNameCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.lineTopHidden = YES;
        cell.lineBottomHidden = NO;
    }
    
    if (self.viewModel.datas.count > indexPath.section) {
        cell.model = self.viewModel.datas[indexPath.section];
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [SCAppointmentDoctorNameCell cellHeight];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
//    [[WDShare shareInstance]shareInView:self.view content:nil];
//    return;

    if (self.viewModel.datas.count <= indexPath.section) return;
    [PerfectInformationView showPerfectInformationAlertIsSuccess:^(BOOL success) {
        if (!success) {
            SCAppointmentDetailModel *model = self.viewModel.datas[indexPath.section];
            if ([model.isFull boolValue] == NO) {
                return;
            }
            SCDoctorSchedulingViewController *vc = [SCDoctorSchedulingViewController new];
            vc.viewModel.hosDeptCode = model.hosDeptCode;
            vc.viewModel.hosDoctCode = model.hosDoctCode;
            vc.viewModel.hospitalCode = model.hosOrgCode;
            
            [self.navigationController pushViewController:vc animated:YES];
        }
        
    }];
    
}


@end
