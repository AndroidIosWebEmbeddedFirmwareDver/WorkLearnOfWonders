//
//  InspectionReportViewController.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "InspectionReportViewController.h"
#import "ReportTableViewCell.h"

static NSString *const TABLECELL = @"ReportTableViewCell";
@interface InspectionReportViewController ()<UITableViewDelegate,UITableViewDataSource> {
    BOOL _refreshOnly;
    NSString *_errorDescription;
}
@property (strong, nonatomic) UITableView *myTableView;

@end

@implementation InspectionReportViewController

- (instancetype)init {
    self = [super init];
    if (self) {
        self.viewModel = [[ReportViewModel alloc]init];
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
}


- (void)requestData {
    if (_refreshOnly) {
        _refreshOnly = NO;
        [LoadingView showLoadingInView:self.view];
    }
    [self.viewModel getInspectionDataFromeServer:^{
        [self.myTableView reloadData];
        [self endRefreshing];
        [LoadingView hideLoadinForView:self.view];
    } failed:^(NSError *error){
        [self endRefreshing];
        [LoadingView hideLoadinForView:self.view];
        _errorDescription = error.localizedDescription;
    }];
}


- (void)createUI {
    self.myTableView                    = [[UITableView alloc]initWithFrame:CGRectZero style:UITableViewStylePlain];
    self.myTableView.separatorStyle     = UITableViewCellSeparatorStyleNone;
    self.myTableView.backgroundColor    = [UIColor colorWithHex:0xF6F6F6];
    self.myTableView.delegate           = self;
    self.myTableView.dataSource         = self;
    self.myTableView.estimatedRowHeight = 68.5;
    self.myTableView.rowHeight          = UITableViewAutomaticDimension;
    self.myTableView.mj_header          = [UIUtility headerRefreshTarget:self action:@selector(requestData)];
    [self.view addSubview:self.myTableView];
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(self.view);
    }];
    [self.myTableView registerClass:[ReportTableViewCell class] forCellReuseIdentifier:TABLECELL];
    
}



#pragma mark - UITableViewDelegate And dataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.dataArray.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    ReportTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:TABLECELL];
    cell.cellModel            = [self.viewModel.dataArray objectAtIndex:indexPath.row];
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *url = [[self.viewModel.dataArray objectAtIndex:indexPath.row] view_url];
    [[BFRouter router] open:url];
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
