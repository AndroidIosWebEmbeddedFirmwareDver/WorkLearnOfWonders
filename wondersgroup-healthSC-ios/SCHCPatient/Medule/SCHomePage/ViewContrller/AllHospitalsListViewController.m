//
//  AllHospitalsListViewController.m
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "AllHospitalsListViewController.h"
#import "InspectMoreViewModel.h"
#import "SCNearbyHospitalCell.h"
#import "SCHospitalHomePageViewController.h"
@interface AllHospitalsListViewController ()<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong) UITableView * myTableView;

@property (nonatomic,strong)InspectMoreViewModel * viewModel;

@property (nonatomic,strong) NSString * flagStr;
@end

@implementation AllHospitalsListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.viewModel = [[InspectMoreViewModel alloc]init];
    self.title = @"全部医院";
    [self getAllData];
    [self setUI];
    [self bindViewModel];
    // Do any additional setup after loading the view.
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
                [self getAllData];
            }];
        }
    }];
    
    [RACObserve(self.viewModel, hasMore) subscribeNext:^(NSNumber *x) {
        @strongify(self)
        BOOL more = x.boolValue;
        if (more) {
            self.myTableView.mj_footer = [UIUtility footerMoreTarget:self action:@selector(requestMore)];
        }else {
            self.myTableView.mj_footer = nil;
        }
    }];
    

}
-(void)requestMore{

    [self.viewModel getMoreHospitalsList:self.keyWord success:^{
        WS(weakSelf)
        [_myTableView.mj_header endRefreshing];
        [weakSelf.view hiddenFailView];
    } failure:^(NSError * error){
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];
}
-(void)setUI{

    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    WS(weakSelf)
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.bottom.right.equalTo(weakSelf.view);
    }];
    [self.myTableView registerClass:[SCNearbyHospitalCell class] forCellReuseIdentifier:@"SCNearbyHospitalCell"];
    self.myTableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(reloadReFresh)];
}
-(void)reloadReFresh{

    [self.viewModel getAllHospitalsList:self.keyWord success:^{
        [self.myTableView.mj_header endRefreshing];
        [self.myTableView reloadData];
    } failure:^(NSError *error){
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];
}
-(void)getAllData{
    [self.viewModel getAllHospitalsList:self.keyWord success:^{
        [self.myTableView reloadData];
    } failure:^(NSError *error){
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];
}
#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.dataArray.count;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    SCNearbyHospitalCell * cell = [tableView dequeueReusableCellWithIdentifier:@"SCNearbyHospitalCell"];
    cell.model = self.viewModel.dataArray[indexPath.row];
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
    SCHospitalModel * model = self.viewModel.dataArray[indexPath.row];
    SCHospitalHomePageViewController * hospital = [SCHospitalHomePageViewController new];
    hospital.hospitalID = model.hospitalId;
    [self.navigationController pushViewController:hospital animated:YES];
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
