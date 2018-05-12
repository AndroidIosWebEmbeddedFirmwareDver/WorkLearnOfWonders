//
//  ElectronicPrescribingVC.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "ElectronicPrescribingVC.h"
#import "ElectronicPrescribingTableViewCell.h"
#import "UINavigationBar+BottomLineColor.h"
#import "ElectronicPrescribingModel.h"

#define TABLECELL @"ElectronicPrescribingTableViewCell"
@interface ElectronicPrescribingVC ()<UITableViewDelegate,UITableViewDataSource> {
    BOOL _refreshOnly;
    NSString *_errorDescription;
}
@property (strong, nonatomic) UITableView *myTableView;

@end

@implementation ElectronicPrescribingVC

- (instancetype)init {
    self = [super init];
    if (self) {
        self.viewModel = [[ElectronicPrescribingViewModel alloc]init];
        _refreshOnly   = YES;
    }
    return self;
}


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self createUI];
    [self RACBind];
    [self requestData];
}


- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
//    [self.navigationController.navigationBar setBottomBorderColor:[UIColor whiteColor] height:0.5];
}


- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
//    [self.navigationController.navigationBar setBottomBorderColor:[UIColor redColor] height:0.5];
}


- (void)RACBind {
    @weakify(self)
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
        @strongify(self)
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:self.view];
        [self endRefreshing];
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:
                failType = FailViewEmpty;
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
            {
                failType = FailViewUnknow;
                if (_errorDescription) {//服务器返回错误信息，显示错误信息，如果没油返回，则显示本地的错误信息页面
                    [self.view showFailViewWith:[UIImage imageNamed:@"网络出错"] withTitle:_errorDescription withAction:^{
                        [self requestData];
                    }];
                }else {
                    failType = FailViewError;
                }
            }
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
                [self requestData];
            }];
        }
    }];
}


- (void)endRefreshing {
    [self.myTableView.mj_header endRefreshing];
    [self.myTableView.mj_footer endRefreshing];
}


- (void)requestMore {
    
}


- (void)requestData {
    if (_refreshOnly) {
        _refreshOnly = NO;
        [LoadingView showLoadingInView:self.view];
    }
    
    [self.viewModel getElectronicPrescribingDataFromeServer:^{
        [self.myTableView reloadData];
        [self.myTableView.mj_header endRefreshing];
        [LoadingView hideLoadinForView:self.view];
    } failed:^(NSError *error){
        [self.myTableView reloadData];
        [self.myTableView.mj_header endRefreshing];
        [LoadingView hideLoadinForView:self.view];
        _errorDescription = error.localizedDescription;
    }];
}


- (void)createUI {
    self.title = @"电子处方";
    self.myTableView = [[UITableView alloc]initWithFrame:CGRectZero style:UITableViewStylePlain];
    self.myTableView.separatorStyle     = UITableViewCellSeparatorStyleNone;
    self.myTableView.backgroundColor    = [UIColor colorWithHex:0xF6F6F6];
    self.myTableView.delegate           = self;
    self.myTableView.dataSource         = self;
    self.myTableView.estimatedRowHeight = 60.0;
    self.myTableView.mj_header          = [UIUtility headerRefreshTarget:self action:@selector(requestData)];
    [self.view addSubview:self.myTableView];
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    [self.myTableView registerClass:[ElectronicPrescribingTableViewCell class] forCellReuseIdentifier:TABLECELL];
}



#pragma mark - UITableViewDelegate And dataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return self.viewModel.dataArray.count;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.viewModel.dataArray[section] count];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    ElectronicPrescribingTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:TABLECELL];
    cell.cellModel            = [self.viewModel.dataArray[indexPath.section] objectAtIndex:indexPath.row];
    return cell;
}


/*/healthSC-app-h5/healthRecords/prescribingInfo?id=O13030212388x&idc=513024195101259705&name=市慢病医院&department=门诊部&amount=7.3600&date=2012-11-11&type=app*/
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    ElectronicPrescribingModel *model = [self.viewModel.dataArray[indexPath.section] objectAtIndex:indexPath.row];
    [[BFRouter router] open:model.url];
}


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 10.0;
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
