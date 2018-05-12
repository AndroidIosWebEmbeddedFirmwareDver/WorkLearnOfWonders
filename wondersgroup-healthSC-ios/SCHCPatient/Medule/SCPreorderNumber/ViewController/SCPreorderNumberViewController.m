//
//  ChooseHospitalViewController.m
//  SCHCPatient
//
//  Created by wuhao on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCPreorderNumberViewController.h"

@interface SCPreorderNumberViewController ()<UITableViewDataSource,UITableViewDelegate,UISearchResultsUpdating>

@end

@implementation SCPreorderNumberViewController
{
        //数据源的表格
    UITableView * _tableView;
        //数据源
    NSMutableArray * _dataArray;
        //搜索结果的数组
    NSMutableArray * _searchArray;
        //搜索视图
    UISearchController * _searchVC;//(ios 8.0 and later)
                                   //搜索结果的表格视图
    UITableViewController * _searchTableView;


}

- (void)viewDidLoad {
    [super viewDidLoad];
        // Do any additional setup after loading the view.
    
        //  self.view.backgroundColor=[UIColor redColor];
    
        //自定义导航栏界面
    [self initNaviBar];
    
        //数据源
    [self createDataSource];
    [self createTableView];
    [self createSearchController];
}


-(void)initNaviBar{

    self.navigationItem.title=@"预约挂号";
}



#pragma mark 数据源
- (void)createDataSource
{
        //初始化数据源
    _dataArray = [[NSMutableArray alloc]init];
    for (int i = 'A'; i <= 'Z' ; i++) {
        NSMutableArray * small = [[NSMutableArray alloc]init];
        for (int j = 0; j<arc4random()%10+8; j++) {
            NSString * string = [NSString stringWithFormat:@"%c%c%c%c%c",i,arc4random()%26+'A',arc4random()%26+'A',arc4random()%26+'A',arc4random()%26+'A'];
            [small addObject:string];
        }
        [_dataArray addObject:small];
    }
}
#pragma mark 表格视图
- (void)createTableView
{
    _tableView = [[UITableView alloc]initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _tableView.rowHeight=44.f;
    _tableView.delegate = self;
    _tableView.dataSource = self;
    [self.view addSubview:_tableView];
}
#pragma mark 搜索视图
- (void)createSearchController
{
        //表格界面
    _searchTableView = [[UITableViewController alloc]initWithStyle:UITableViewStylePlain];
        //tableview是表格视图
        //UITableViewController表格视图控制器
    _searchTableView.tableView.dataSource = self;
    _searchTableView.tableView.delegate = self;
    
    _searchTableView.tableView.rowHeight=44.f;
        //设置大小
    _searchTableView.tableView.frame = self.view.bounds;
    
        //创建搜索界面
    _searchVC = [[UISearchController alloc]initWithSearchResultsController:_searchTableView];
        //把表格视图控制器跟搜索界面相关联
    
        //设置搜索栏的大小
    _searchVC.searchBar.frame = CGRectMake(0, 0, self.view.bounds.size.width, 44);
    _searchVC.searchBar.placeholder = @"搜索医院";
    _searchVC.searchBar.barTintColor = [UIColor lightGrayColor];
        //把搜索栏放到tableview的头视图上
    _tableView.tableHeaderView = _searchVC.searchBar;
        //设置搜索的代理
    _searchVC.searchResultsUpdater = self;
    
}
#pragma mark 协议方法
    //返回多少组
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    if (tableView == _tableView) {
        return _dataArray.count;
    }
    return 1;
}
    //返回每组的个数
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (tableView == _tableView) {
        return [_dataArray[section] count];
    }
    return _searchArray.count;
}
    //返回每一个cell的内容
- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString * str = @"cell";
    UITableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:str];
    if (cell == nil) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:str];
    }
    if (tableView == _tableView) {
        cell.textLabel.text = _dataArray[indexPath.section][indexPath.row];
    }else{
        cell.textLabel.text = _searchArray[indexPath.row];
    }
    return cell;
}
    //返回每组的头标题
    //- (NSString*)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
    //{
    //    if (tableView == _tableView) {
    //        return [NSString stringWithFormat:@"第%c组",'A'+(int)section];
    //    }else{
    //        return @"搜索结果";
    //    }
    //
    //}





#pragma mark 搜索的协议方法
- (void)updateSearchResultsForSearchController:(UISearchController *)searchController
{
    NSLog(@"搜索");
        //在点击搜索时会调用一次，点击取消按钮又调用一次
        //判断当前搜索是否在搜索状态还是取消状态
    if (_searchVC.isActive) {
            //表示搜索状态
        
            //初始化搜索数组
        if (_searchArray == nil) {
            _searchArray = [[NSMutableArray alloc]init];
        }else{
            [_searchArray removeAllObjects];
                //遍历数据源，给搜索数组添加对象
            for (NSArray * array in _dataArray)
                {
                for (NSString * name in array)
                    {
                    NSRange range = [name rangeOfString:searchController.searchBar.text];
                    if (range.location != NSNotFound) {
                        [_searchArray addObject:name];
                        
                    }
                    }
                }
        }
            //刷新搜索界面的tableview
        [_searchTableView.tableView reloadData];
    }
}


@end
