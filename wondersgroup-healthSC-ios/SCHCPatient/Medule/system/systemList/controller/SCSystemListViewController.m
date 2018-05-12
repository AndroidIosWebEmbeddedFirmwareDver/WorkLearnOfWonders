//
//  ReportNoticeViewController.m
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import"SCSystemListViewController.h"
#import "SCSystemListCell.h"
#define SystemListCellIndentifier @"SCSystemListCell"


#import "UITableView+FDTemplateLayoutCell.h"

@interface SCSystemListViewController ()

@end

@implementation SCSystemListViewController

- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.title=@"系统消息";
    self.view.backgroundColor = [UIColor bc2Color];
    self.viewModel = [[SCSystemListViewModel alloc] init];
    self.updateMessageViewmodel = [[SCSystemViewModel alloc] init];
    [self setUpView];
    [self bindmodel];
    [self getFrontRequest];
  
    // Do any additional setup after loading the view.
}

-(void)getFrontRequest
{
    WS(weakSelf);
    [self.view hiddenFailView];
   
    self.viewModel.moreParams =nil;
    [LoadingView showLoadingInView:self.view];
    [self.viewModel getSystemMessage:@{@"uid":[UserManager manager].uid==nil ?@"1":[UserManager manager].uid }  success:^{
        
        [weakSelf.mTableview.mj_header endRefreshing ];
         [LoadingView hideLoadinForView:weakSelf.view];
      
    } failure:^(NSError *error) {
         [weakSelf.mTableview.mj_header endRefreshing ];
        [LoadingView hideLoadinForView:weakSelf.view];
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
    }];
}
-(void)getMoreRequest
{
    [self.mTableview.mj_footer beginRefreshing ];
    WS(weakSelf);
    [self.viewModel getSystemMessage:@{@"uid":[UserManager manager].uid==nil ?@"1":[UserManager manager].uid }  success:^{
        
        [weakSelf.mTableview.mj_footer endRefreshing];
    } failure:^(NSError *error) {
        [MBProgressHUDHelper showHudWithText:error.localizedDescription];
        [weakSelf.mTableview.mj_footer endRefreshing];
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) setUpView
{
    self.mTableview = [UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStylePlain withDelegateAndDataSource:self];
    self.mTableview.backgroundColor = [UIColor clearColor];
    WS(weakSelf);
    [self.mTableview mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(weakSelf.view);
        make.top.equalTo(weakSelf.view.mas_top);
        make.bottom.equalTo(weakSelf.view.mas_bottom);
    }];
    
    [self.mTableview registerClass:[SCSystemListCell class]
             forCellReuseIdentifier:SystemListCellIndentifier];
    self.mTableview.mj_header = [UIUtility headerRefreshTarget:self action:@selector(getFrontRequest)];
}

#pragma mark reloadReFresh
-(void)reloadReFresh
{
    [self  getMoreRequest ];
}

#pragma mark tableview delegate

-(NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.viewModel.DataArray.count;
}

-(CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    WS(weakSelf);
    return [tableView fd_heightForCellWithIdentifier:SystemListCellIndentifier configuration:^(SCSystemListCell *cell) {
        if (weakSelf.viewModel.DataArray.count > indexPath.row)
            cell.model =[weakSelf.viewModel.DataArray objectAtIndex:indexPath.row];
    }];
    
}

-(UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    SCSystemListCell* cell = [tableView dequeueReusableCellWithIdentifier:SystemListCellIndentifier];
    if(!cell)
    {
        cell = [[SCSystemListCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:SystemListCellIndentifier];
    }
    
     if (self.viewModel.DataArray.count > indexPath.row)
         cell.model =[self.viewModel.DataArray objectAtIndex:indexPath.row];

    return cell;
}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
 
    if (self.viewModel.DataArray.count < indexPath.row)
    {
        return;
    }
    SCSystemListModel * model =[self.viewModel.DataArray objectAtIndex:indexPath.row];
    
//    if([model.state intValue] ==0)
//    {
//        
//        ((SCSystemListModel*)[self.viewModel.DataArray objectAtIndex:indexPath.row]).state =@"1";
//        [self updateMessage:@{@"uid":[UserManager manager].uid==nil ?@"1":[UserManager manager].uid,@"messageId":model.messageId==nil?@"":model.messageId}];
//    }
//    
//    
    [[BFRouter router] open:model.linkUp];
    
}



-(void)bindmodel
{
    WS(weakSelf);
    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(id x) {
        
        
        
        
        
        if(weakSelf.viewModel.hasMore)
        {
            weakSelf.mTableview.mj_footer = [UIUtility footerMoreTarget:self action:@selector(reloadReFresh)];
        }
        else
        {
            weakSelf.mTableview.mj_footer = nil;
        }
        
        
        switch (weakSelf.viewModel.requestCompeleteType ) {
            case RequestCompeleteEmpty:
            {
                 [LoadingView showLoadingInView:weakSelf.view];
                [weakSelf.view showFailView:FailViewEmpty withAction:^{
                    [weakSelf getFrontRequest];
                }];
            }
                
                break;
            case RequestCompeleteNoWifi:
            {
                 [LoadingView showLoadingInView:weakSelf.view];
                [weakSelf.view showFailView:FailViewNoWifi withAction:^{
                    [weakSelf getFrontRequest];
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
                    [weakSelf getFrontRequest];
                }];
            }
                break;
            default:
                break;
        }
        
    }];

   
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

@end
