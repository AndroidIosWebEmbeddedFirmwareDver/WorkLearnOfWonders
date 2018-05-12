//
//  SystemViewController.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSystemViewController.h"
#import "SCSystemCell.h"
#import "SCSystemListViewController.h"
#import "SCReportListViewController.h"
#import "PayNoticeViewController.h"

#define SystemCellIndentifier @"SystemCell"

@interface SCSystemViewController ()

@end

@implementation SCSystemViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    
    
    
    self.navigationItem.title=@"消息";
    self.view.backgroundColor = [UIColor bc2Color];
    self.viewModel = [[SCSystemViewModel alloc] init];
   
    [self setUpView];
   
    [self.mTableview reloadData];
    [self binmodel];

    // Do any additional setup after loading the view.
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) setUpView
{
    WS(weakSelf);
    self.mTableview = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStylePlain withDelegateAndDataSource:self];
    self.mTableview.backgroundColor = [UIColor clearColor];
    [self.mTableview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.view);
        make.top.equalTo(weakSelf.view.mas_top).offset(10);
        make.bottom.equalTo(weakSelf.view.mas_bottom).offset(-44);
    }];
    self.mTableview.mj_header = [UIUtility headerRefreshTarget:self action:@selector(reloadReFresh)];
}

#pragma mark reloadReFresh
-(void)reloadReFresh
{
     [self updaterequest];
}

#pragma mark tableview delegate

-(NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.viewModel.dataArray.count;
}

-(CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 80.0;
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    SCSystemCell * cell = [tableView dequeueReusableCellWithIdentifier:SystemCellIndentifier];
    
    if(!cell)
    {
        cell = [[SCSystemCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:SystemCellIndentifier];
        
    }
    cell.objectCount =self.viewModel.dataArray.count;
    cell.model =[self.viewModel.dataArray objectAtIndex:indexPath.row];
    cell.index =indexPath.row;
    return cell;
}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if(indexPath.row> self.viewModel.dataArray.count)
    {
        return;
        
    }
    
    SCSystemModel * m = [self.viewModel.dataArray objectAtIndex:indexPath.row];
    switch ([m.type intValue]) {
        case 1:
        {
        
            SCSystemListViewController * con = [[SCSystemListViewController alloc] init];
            con.hidesBottomBarWhenPushed = YES;
           
            [self.navigationController pushViewController:con animated:YES];
        }
            break;
        case 2:
        {
            PayNoticeViewController * con = [[PayNoticeViewController alloc] init];
             con.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:con animated:YES];
        }
            break;
        case 3:
        {
            SCReportListViewController *con =[[SCReportListViewController alloc] init];
             con.hidesBottomBarWhenPushed = YES;
            [self.navigationController pushViewController:con animated:YES];
        }
            break;
        default:
            break;
    }
    
    
}


-(void) updaterequest
{
    [self.mTableview.mj_header beginRefreshing];
     [self requestNetWork];
}

-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
   
     [LoadingView showLoadingInView:self.view];
    [self requestNetWork];
    
   
   
}

-(void)requestNetWork
{
    
    WS(weakSelf);
    [self.viewModel getSystemMessageRequest:@{@"uid":[UserManager manager].uid==nil ?@"1":[UserManager manager].uid } success:^{
       [LoadingView hideLoadinForView:self.view];
         [weakSelf.view hiddenFailView];
         [weakSelf.mTableview.mj_header endRefreshing];
        [LoadingView hideLoadinForView:weakSelf.view];
    } falied:^(NSError *error) {
        [LoadingView hideLoadinForView:weakSelf.view];
         [weakSelf.view hiddenFailView];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
         [weakSelf.mTableview.mj_header endRefreshing];
    }];
}

-(void) binmodel
{
    WS(weakSelf);
    [RACObserve(self.viewModel, requestCompeleteType )subscribeNext:^(NSNumber * x) {
        
        [weakSelf.view hiddenFailView];
        switch (weakSelf.viewModel.requestCompeleteType ) {
            case RequestCompeleteEmpty:
            {
                
                 [LoadingView showLoadingInView:weakSelf.view];
                [weakSelf.view showFailView:FailViewEmpty withAction:^{
                    [weakSelf requestNetWork];
                }];
            }
               
                break;
            case RequestCompeleteNoWifi:
            {
                [LoadingView showLoadingInView:weakSelf.view];
                [weakSelf.view showFailView:FailViewNoWifi withAction:^{
                    [weakSelf requestNetWork];
                }];
            }
                break;
            case RequestCompeleteSuccess:
            {
                [self.mTableview reloadData];
            }
                break;
            case RequestCompeleteError:
            {
                [LoadingView showLoadingInView:weakSelf.view];
                [weakSelf.view showFailView:FailViewError withAction:^{
                    [weakSelf requestNetWork];
                }];
            }
                break;
            default:
                break;
        }
        
    }];
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
