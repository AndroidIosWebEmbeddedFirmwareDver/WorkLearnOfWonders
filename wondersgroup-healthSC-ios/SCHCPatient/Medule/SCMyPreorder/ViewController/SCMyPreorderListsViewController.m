//
//  SCMyAppointmentListsViewController.m
//  HCPatient
//
//  Created by wuhao on 2016/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCMyPreorderListsViewController.h"
#import "SCMyPreorderListsCell.h"
#import "SCMyPreorderListsViewModel.h"

@interface SCMyPreorderListsViewController ()<UITableViewDataSource,UITableViewDelegate,UISearchResultsUpdating>

@property (nonatomic, strong) SCMyPreorderListsViewModel *viewModel;
@property (nonatomic, strong) UITableView *myTableView;

@end

@implementation SCMyPreorderListsViewController
{
        //搜索视图
    UISearchController * _searchVC;//(ios 8.0 and later)
                                   //搜索结果的表格视图
    UITableViewController * _searchResultTableView;
}

#pragma mark    - lifecycle

-(instancetype)init {
    self = [super init];
    if (self) {
        self.viewModel = [SCMyPreorderListsViewModel new];
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

#pragma mark 表格视图
-(void)setupView {
    
    self.title = @"我的预约";
    self.view.backgroundColor = [UIColor bc2Color];
    WS(weakSelf)
    
    self.myTableView = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStylePlain withDelegateAndDataSource:self];
    self.myTableView.backgroundColor = [UIColor clearColor];
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(weakSelf.view);
    }];
    
    MJRefreshGifHeader * header = [UIUtility headerRefreshTarget:self action:@selector(reloadData)];
    self.myTableView.mj_header = header;
    
    [self reloadData];
    
    [LoadingView showLoadingInView:self.view];
    
    [self createSearchController];//
}

#pragma mark 搜索视图
- (void)createSearchController
{
        //表格界面
    _searchResultTableView = [[UITableViewController alloc]initWithStyle:UITableViewStylePlain];
        //tableview是表格视图
        //UITableViewController表格视图控制器
    _searchResultTableView.tableView.dataSource = self;
    _searchResultTableView.tableView.delegate = self;
            //设置大小
    _searchResultTableView.tableView.frame = self.view.bounds;
    
        //创建搜索界面
    _searchVC = [[UISearchController alloc]initWithSearchResultsController:_searchResultTableView];
        //把表格视图控制器跟搜索界面相关联
    _searchVC.searchBar.placeholder=@"预约";
    _searchVC.searchBar.barTintColor = [UIColor lightGrayColor];
    
        //设置搜索栏的大小
    _searchVC.searchBar.frame = CGRectMake(0, 0, self.view.bounds.size.width, 44);
    
        //把搜索栏放到tableview的头视图上
    self.myTableView.tableHeaderView = _searchVC.searchBar;
        //设置搜索的代理
    _searchVC.searchResultsUpdater = self;
    
}

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
    [self.viewModel getMyAppointment];
}

-(void)reloadMoreData {
    [self.viewModel getMyAppointmentMore];
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
    
    static NSString *identifier = @"SCMyPreorderListsCell";
    
    SCMyPreorderListsCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if(!cell)
    {
        cell = [[SCMyPreorderListsCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.lineTopHidden = NO;
        cell.lineBottomHidden = NO;
    }
    
    if (self.viewModel.datas.count > indexPath.section) {
        //        cell.model = self.viewModel.datas[indexPath.section];
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return [SCMyPreorderListsCell cellHeight];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (self.viewModel.datas.count <= indexPath.section) return;
    
    
    
    
}

#pragma mark 搜索的协议方法
- (void)updateSearchResultsForSearchController:(UISearchController *)searchController
{
    NSLog(@"搜索");
        //在点击搜索时会调用一次，点击取消按钮又调用一次
        //判断当前搜索是否在搜索状态还是取消状态
    if (_searchVC.isActive) {
            //表示搜索状态
        
        
        
            //刷新搜索界面的tableview
        [_searchResultTableView.tableView reloadData];
    }
}





@end
