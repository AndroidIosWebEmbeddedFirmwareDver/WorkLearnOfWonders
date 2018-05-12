//
//  AllDoctorsListViewController.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AllDoctorsListViewController.h"
#import "InspectMoreViewModel.h"
#import "SearchResultDoctorCell.h"
#import "DoctorDetailViewController.h"
@interface AllDoctorsListViewController ()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong) UITableView * myTableView;
@property (nonatomic,strong) InspectMoreViewModel * viewModel;
@property (nonatomic,strong) NSString *flagStr;

@end

@implementation AllDoctorsListViewController

- (void)viewDidLoad {
    self.viewModel = [[InspectMoreViewModel alloc]init];
    [super viewDidLoad];
    self.title = @"全部医生";
    [self setUI];
    [self getDatas];
    [self bindViewModel];
}
-(void)getDatas{
    
    
    self.viewModel.moreParams = nil;
    
[self.viewModel getAllDoctorsList:self.keyWord success:^{
    
    [self.myTableView reloadData];
    
} failure:^(NSError * error){

}];



}
-(void)bindViewModel{
    @weakify(self)
    RACSignal *requestCompeleteTypeSignal = RACObserve(self.viewModel, requestCompeleteType);
    [requestCompeleteTypeSignal subscribeNext:^(NSNumber *type) {
        @strongify(self)
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
        //                [self endRefreshing];
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:
                failType = FailViewEmpty;
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                [self.view hiddenFailView];
                [self.myTableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            [self.view showFailView:failType withAction:^{
                [self getDatas];
            }];
        }
    }];
    
    [RACObserve(self.viewModel, hasMore) subscribeNext:^(NSNumber *x) {
        @strongify(self)
        BOOL more = x.boolValue;
        if (more) {
            self.myTableView.mj_footer = [UIUtility footerMoreTarget:self action:@selector(requestMores)];
        }else {
            self.myTableView.mj_footer = nil;
        }
    }];
    
   
}
-(void)requestMores{
    
    [self.viewModel getMoreDoctorsList:self.keyWord success:^{
        [self.myTableView.mj_footer endRefreshing];
        [self.myTableView reloadData];
    } failure:^(NSError * error){
        [self.myTableView.mj_footer endRefreshing];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        
    }];


}
-(void)setUI{
    
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    WS(weakSelf)
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.bottom.right.equalTo(weakSelf.view);
    }];
    
    [self.myTableView registerClass:[SearchResultDoctorCell class] forCellReuseIdentifier:@"SearchResultDoctorCell"];
    self.myTableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(reloadReFresh)];
}
-(void)reloadReFresh{
    
    [self.viewModel getAllDoctorsList:self.keyWord success:^{
        [self.myTableView.mj_header endRefreshing];
        [self.myTableView reloadData];
        
    } failure:^(NSError * error){
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];

}
#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.dataDoctorArray.count;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SearchResultDoctorCell * cell = [tableView dequeueReusableCellWithIdentifier:@"SearchResultDoctorCell"];
    
    
    if(self.viewModel.dataDoctorArray.count > indexPath.row)
        cell.model = self.viewModel.dataDoctorArray[indexPath.row];
    
    return cell;
    
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 111;
}



- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    if (section == 0) {
        return 15;
    }
    return 0.1f;
}

-(CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    
    
    return 0.1f;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    
    if(self.viewModel.dataDoctorArray.count > indexPath.row)
    {
        DoctorssModel *  docModel = self.viewModel.dataDoctorArray[indexPath.row];
        DoctorDetailViewController * doctorVC = [DoctorDetailViewController new];
        doctorVC.showRegister = YES;
        doctorVC.hospitalCode = docModel.hosOrgCode;
        doctorVC.hosDeptCode = docModel.hosDeptCode;
        doctorVC.hosDoctCode = docModel.hosDoctCode;
        [self.navigationController pushViewController:doctorVC animated:YES];
    }
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
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
