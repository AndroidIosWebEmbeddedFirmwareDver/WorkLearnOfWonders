//
//  DoctorSchedulingViewController.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCDoctorSchedulingViewController.h"
#import "SCDoctorSchedulingInfoCell.h"
#import "SCDoctorSchedulingDateCell.h"
#import "SCDoctorSchedulingRuleCell.h"
#import "SCAppointmentDetailViewController.h"
#import "DoctorDetailViewController.h"

@interface SCDoctorSchedulingViewController ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) UITableView *myTableView;

@end

@implementation SCDoctorSchedulingViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        self.viewModel = [SCDoctorSchedulingViewModel new];
        
    }
    return self;
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.navigationController.navigationBar.hidden = YES;
}

-(void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    self.navigationController.navigationBar.hidden = NO;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self bindViewModel];
    [self reloadData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {

}


#pragma mark    - setupView
-(void)setupView {
    self.view.backgroundColor = [UIColor bc2Color];
    
    WS(weakSelf)
    
    UITableView *tableViewL = [[UITableView alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH/3, self.view.frame.size.height)
                                                           style: UITableViewStyleGrouped];
    tableViewL.delegate      = self;
    tableViewL.dataSource    = self;
    [tableViewL setBackgroundColor: [UIColor clearColor]];
    [tableViewL setSeparatorColor: [UIColor clearColor]];
    [tableViewL setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: tableViewL];
    self.myTableView = tableViewL;
    tableViewL.mj_header = [UIUtility headerRefreshTarget:self action:@selector(refreshData)];
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.left.right.equalTo(weakSelf.view);
    }];
    
    UIButton *backBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [backBtn setImage:[UIImage imageNamed:@"back_white"] forState:UIControlStateNormal];
    [backBtn setImageEdgeInsets:UIEdgeInsetsMake(-10, -10, 10, 10)];
    backBtn.frame = CGRectMake(0, 20, 64, 64);
    [backBtn addTarget:self action:@selector(popBack) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:backBtn];
    
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
//                [weakSelf.view showFailView:FailViewEmpty withAction:^{
//                    [weakSelf reloadData];
//                }];
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
//            [weakSelf.view showFailView:failType withAction:^{
//                [weakSelf reloadData];
//            }];
        }
    }];
    
}

- (void)refreshData {
    
    [self.viewModel getDoctorScheduling:^{
        
    [self.myTableView.mj_header endRefreshing];
        
    } failure:^(NSError *error) {
        
    }];
}


#pragma mark    - reloadData

-(void)reloadData {
    [LoadingView showLoadingInView:self.view];

//    [self.viewModel getDoctorScheduling];
    [self.viewModel getDoctorScheduling:^{
        
    } failure:^(NSError *error) {
        
    }];
}

#pragma mark    - method
-(void)endRefreshing {
    [self.myTableView.mj_header endRefreshing];
    [self.myTableView.mj_footer endRefreshing];
}

-(SCAppointmentDetailModel *)getDetailModelWithDoctorInfo:(DoctorInfo *)info withSchedule:(Schedule *)schedule {
    
    SCAppointmentDetailModel *model = [SCAppointmentDetailModel new];
    model.schedule = schedule;
    model.hosOrgCode = info.hosOrgCode;
    model.hosName = info.hosName;
    model.hosDeptCode = info.hosDeptCode;
    model.department = info.deptName;
    model.hosDoctCode = info.hosDoctCode;
    model.doctorName = info.doctorName;
    model.headphoto = info.headphoto;
    model.doctorTitle = info.doctorTitle;
    model.orderCount = info.orderCount;
    model.deptName = info.deptName;
    model.gender   = info.gender;
    return model;
}

#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        static NSString *identifier = @"SCDoctorSchedulingInfoCell";
        
        SCDoctorSchedulingInfoCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if(!cell)
        {
            cell = [[SCDoctorSchedulingInfoCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.lineTopHidden = YES;
            cell.lineBottomHidden = YES;
        }
        cell.model = self.viewModel.model;
        
        return cell;
        
    }else if (indexPath.section == 1) {
        static NSString *identifier = @"SCDoctorSchedulingDateCell";
        
        SCDoctorSchedulingDateCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if(!cell)
        {
            cell = [[SCDoctorSchedulingDateCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.lineTopHidden = YES;
            cell.lineBottomHidden = YES;
        }
        WS(weakSelf)
        cell.viewModel = self.viewModel;
        cell.selectedBlock = ^(Schedule *schedule,DoctorInfo *doctor){
            NSLog(@"排班表id：%@",schedule.scheduleId);
            SCAppointmentDetailViewController *vc = [SCAppointmentDetailViewController new];
            vc.viewModel.model = [weakSelf getDetailModelWithDoctorInfo:doctor withSchedule:schedule];
            [weakSelf.navigationController pushViewController:vc animated:YES];
            
        };
        return cell;
    }else {
        static NSString *identifier = @"SCDoctorSchedulingRuleCell";
        
        SCDoctorSchedulingRuleCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if(!cell)
        {
            cell = [[SCDoctorSchedulingRuleCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.lineTopHidden = YES;
            cell.lineBottomHidden = YES;
        }
        
        return cell;
    }
    
    return nil;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        
        SCDoctorSchedulingModel *cellModel = self.viewModel.model;
        DoctorDetailViewController *vc = [DoctorDetailViewController new];
        vc.hospitalCode = cellModel.doctorInfo.hosOrgCode;
        vc.hosDeptCode = cellModel.doctorInfo.hosDeptCode;
        vc.hosDoctCode = cellModel.doctorInfo.hosDoctCode;

        [self.navigationController pushViewController:vc animated:YES];
        
    }else if (indexPath.section == 0) {
        
        
    }else {
        
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        return [SCDoctorSchedulingInfoCell cellHeightWithModel:self.viewModel.model];
    }else  if (indexPath.section == 1) {
        return [SCDoctorSchedulingDateCell cellHeight];
    }else {
        return [SCDoctorSchedulingRuleCell cellHeight];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 0.5;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 10;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}


@end
