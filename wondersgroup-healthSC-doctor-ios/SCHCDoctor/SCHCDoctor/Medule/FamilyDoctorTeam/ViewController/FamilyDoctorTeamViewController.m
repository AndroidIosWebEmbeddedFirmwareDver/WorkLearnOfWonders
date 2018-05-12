//
//  FamilyDoctorTeamViewController.m
//  SCHCPatient
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 Jam. All rights reserved.
//

#import "FamilyDoctorTeamViewController.h"
#import "FamilyDoctorTeamViewModel.h"
#import "FamilyDoctorTeamCell.h"

@interface FamilyDoctorTeamViewController ()<UITableViewDelegate,UITableViewDataSource>
{
    
}
@property (nonatomic, strong) UITableView *myTableView;
@property (nonatomic, strong) FamilyDoctorTeamViewModel *viewModel;

@end

@implementation FamilyDoctorTeamViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {
        
        self.viewModel = [FamilyDoctorTeamViewModel new];
        
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
    [self bindViewModel];
    [self requestData];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {
    
}


#pragma mark    - setupView
-(void)setupView {
    self.navigationItem.title = @"家庭医生团队";
    self.view.backgroundColor = [UIColor bc2Color];
    
    WS(weakSelf)
    
    UITableView *tableViewL = [[UITableView alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height)
                                                           style: UITableViewStyleGrouped];
    tableViewL.delegate      = self;
    tableViewL.dataSource    = self;
    [tableViewL setBackgroundColor: [UIColor clearColor]];
    [tableViewL setSeparatorColor: [UIColor clearColor]];
    [tableViewL setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: tableViewL];
    self.myTableView = tableViewL;
    self.myTableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestData)];

    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(weakSelf.view);
        make.bottom.equalTo(weakSelf.view);
    }];
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
                    [weakSelf.view hiddenFailView];
    
                    failType = FailViewUnknow;
                }
                    break;
                default:
                    break;
            }
            if (failType != FailViewUnknow) {
                [weakSelf.view showFailView:failType withAction:^{
                    [weakSelf requestData];
                }];
            }
        }];
    
    
    [RACObserve(self.viewModel, hasMore) subscribeNext:^(NSNumber *x) {
        BOOL more = x.boolValue;
        if (more) {
            weakSelf.myTableView.mj_footer =[UIUtility footerMoreTarget:self action:@selector(requestMoreData)];
        }else {
            weakSelf.myTableView.mj_footer = nil;
        }
    }];
    
}

#pragma mark    - method
- (void)requestData {
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getFamilyDoctorTeamComplete:^{
        [LoadingView hideLoadinForView:self.view];
        [self endRefreshing];
        [self.myTableView reloadData];
    } failure:^(NSError *error) {
        [self endRefreshing];
        [LoadingView hideLoadinForView:self.view];
    }];
}

- (void)requestMoreData {
    [self.viewModel getMoreFamilyDoctorTeamComplete:^{
        [self endRefreshing];
        [self.myTableView reloadData];
    } failure:^(NSError *error) {
        [self endRefreshing];
    }];
}

- (void)endRefreshing {
    [self.myTableView.mj_header endRefreshing];
    [self.myTableView.mj_footer endRefreshing];
}


#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.teamArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *identifier = @"FamilyDoctorTeamCell";
    
    FamilyDoctorTeamCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if(!cell)
    {
        cell = [[FamilyDoctorTeamCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.lineTopHidden = YES;
        cell.lineBottomHidden = YES;
    }
    
    cell.model = self.viewModel.teamArray[indexPath.row];
    cell.isLast = (self.viewModel.teamArray.count-1 == indexPath.row);
    
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {

    
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [FamilyDoctorTeamCell cellHeight];
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 10;
}

@end
