//
//  SystemDetialListViewController.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCReportListViewController.h"
#import "SCReportListCell.h"
#define reportListCellIndentifier @"SCReportListCell"


#import "UITableView+FDTemplateLayoutCell.h"
@interface SCReportListViewController ()

@end

@implementation SCReportListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title=@"报告通知";
    self.view.backgroundColor = [UIColor bc2Color];
    self.viewModel = [[SCReportListViewModel alloc] init];
    [self.viewModel getReportRequest];
    [self setUpView];
    
    [self.mTableview reloadData];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) setUpView
{
    self.mTableview = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStylePlain withDelegateAndDataSource:self];
    self.mTableview.backgroundColor = [UIColor clearColor];
    [self.mTableview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(self.view);
        make.top.equalTo(self.view.mas_top);
        make.bottom.equalTo(self.view.mas_bottom);
    }];
    
    [self.mTableview registerClass:[SCReportListCell class]
            forCellReuseIdentifier:reportListCellIndentifier];
    self.mTableview.mj_header = [UIUtility headerRefreshTarget:self action:@selector(reloadReFresh)];
}

#pragma mark reloadReFresh
-(void)reloadReFresh
{
}

#pragma mark tableview delegate

-(NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.viewModel.dataArray.count;
}

-(CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    return [tableView fd_heightForCellWithIdentifier:reportListCellIndentifier configuration:^(SCReportListCell *cell) {
        if (self.viewModel.dataArray.count > indexPath.row)
            cell.model =[self.viewModel.dataArray objectAtIndex:indexPath.row];
    }];
    
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    SCReportListCell* cell = [tableView dequeueReusableCellWithIdentifier:reportListCellIndentifier];
    if(!cell)
    {
        cell = [[SCReportListCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:reportListCellIndentifier];
    }
    
    if (self.viewModel.dataArray.count > indexPath.row)
        cell.model =[self.viewModel.dataArray objectAtIndex:indexPath.row];
    
    return cell;
}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if (self.viewModel.dataArray.count < indexPath.row)
    {
        return;
    }
    SCReportListModel * model =[self.viewModel.dataArray objectAtIndex:indexPath.row];
    
    [[BFRouter router] open:model.linkUp];
    
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
