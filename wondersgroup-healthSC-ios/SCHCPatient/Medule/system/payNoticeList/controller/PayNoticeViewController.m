//
//  PayNoticeViewController.m
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "PayNoticeViewController.h"
#import "PayNoticeCell.h"

#define  PaynoticeCellIndentifier @"PayNoticeCell"
@interface PayNoticeViewController ()

@end

@implementation PayNoticeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title=@"支付通知";
    self.view.backgroundColor = [UIColor bc2Color];
    self.viewModel = [[PayNoticeViewModel alloc] init];
     self.updateMessageViewmodel = [[SCSystemViewModel alloc] init];
    [self setUpView];
    [self bindModel];
    
       [LoadingView showLoadingInView:self.view];
    [self.viewModel getPayList:@{@"uid":[UserManager manager].uid==nil ?@"1":[UserManager manager].uid }  success:^{
      
         [LoadingView hideLoadinForView:self.view];
    } failed:^(NSError *error) {
        [LoadingView hideLoadinForView:self.view];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];
   // [self getFrountRequest];
//    [self.mTableview reloadData];
    
      
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
    WS(weakSelf)
    [self.mTableview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.view);
        make.top.equalTo(weakSelf.view.mas_top).offset(10);
        make.bottom.equalTo(weakSelf.view.mas_bottom);
    }];
    [self.mTableview registerClass:[PayNoticeCell class]
            forCellReuseIdentifier:PaynoticeCellIndentifier];
    self.mTableview.mj_header = [UIUtility headerRefreshTarget:self action:@selector(getFrountRequest)];
}

#pragma mark frontRequest

-(void)getFrountRequest
{
   
    self.viewModel.moreParams  = nil;
 
    WS(weakSelf);
    [self.view hiddenFailView];
 
    [self.viewModel getPayList:@{@"uid":[UserManager manager].uid==nil ?@"1":[UserManager manager].uid}  success:^{
        
         [weakSelf.mTableview.mj_header endRefreshing ];
        [LoadingView hideLoadinForView:weakSelf.view];
        
    } failed:^(NSError *error) {
         [weakSelf.mTableview.mj_header endRefreshing ];
        [LoadingView hideLoadinForView:weakSelf.view];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];
}

-(void)getMoreRequest
{
    
    
    WS(weakSelf);
     [self.mTableview.mj_footer beginRefreshing ];
    [self.viewModel getPayList:@{@"uid":[UserManager manager].uid==nil ?@"1":[UserManager manager].uid}  success:^{
        
        [weakSelf.mTableview.mj_footer endRefreshing];
        
    } failed:^(NSError *error) {
         [weakSelf.mTableview.mj_footer endRefreshing];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];

}

#pragma mark reloadReFresh
-(void)reloadReFresh
{
    
    [self getMoreRequest];
}

#pragma mark tableview delegate

-(NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.viewModel.dataArray.count;
}

-(CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
  
    return [tableView fd_heightForCellWithIdentifier:PaynoticeCellIndentifier configuration:^(PayNoticeCell *cell) {
        if (self.viewModel.dataArray.count > indexPath.row)
            cell.model =[self.viewModel.dataArray objectAtIndex:indexPath.row];
    }];
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    PayNoticeCell * cell = [tableView dequeueReusableCellWithIdentifier:PaynoticeCellIndentifier];
    
    if(!cell)
    {
        cell = [[PayNoticeCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:PaynoticeCellIndentifier];
        
    }
    cell.model =[self.viewModel.dataArray objectAtIndex:indexPath.row];
    return cell;
}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    if(indexPath.row> self.viewModel.dataArray.count)
    {
        return;
        
    }
    
    
    PayNoticeModel * model = [self.viewModel.dataArray objectAtIndex:indexPath.row];
    [[BFRouter router] open:model.linkUp];
//    if([model.state intValue] ==0)
//    {
//    [self updateMessage:@{@"uid":[UserManager manager].uid==nil ?@"1":[UserManager manager].uid,@"messageId":model.messageId==nil?@"":model.messageId}];
//    }
    
    
}

-(void)updateMessage:(NSDictionary *)dic
{
    
  
    [self.updateMessageViewmodel updateMessageIdStatus:dic success:^{
        
    } falied:^(NSError *error) {
        
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

-(void) bindModel
    {
        WS(weakSelf);
        [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(id x) {
            
            
            
            
            
            if(weakSelf.viewModel.hasMore)
            {
                self.mTableview.mj_footer = [UIUtility footerMoreTarget:self action:@selector(reloadReFresh)];
            }
            else
            {
                self.mTableview.mj_footer = nil;
            }
            
            
            switch (weakSelf.viewModel.requestCompeleteType ) {
                case RequestCompeleteEmpty:
                {
                    
                    [LoadingView showLoadingInView:weakSelf.view];
                    [weakSelf.view showFailView:FailViewEmpty withAction:^{
                        [weakSelf getFrountRequest];
                    }];
                }
                    
                    break;
                case RequestCompeleteNoWifi:
                {
                    [LoadingView showLoadingInView:weakSelf.view];
                    [weakSelf.view showFailView:FailViewNoWifi withAction:^{
                        [weakSelf getFrountRequest];
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
                        [weakSelf getFrountRequest];
                    }];
                }
                    break;
                default:
                    break;
            }
            
        }];
        
        
    }

@end
