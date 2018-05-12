//
//  SCMyPreOrderFinalAllViewController.m
//  SCHCPatient
//
//  Created by wuhao on 2016/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCMyPreOrderFinalAllViewController.h"
#import "SCMyPreOrderListsFinalCell.h"

    //预约详情界面
#import "SCMyPreorderDetailViewController.h"
    //预约列表获取数据页面
#import "SCMyPreorderListsViewModel.h"

#import "SCMyOrderModel.h"

    //取消预约成功页面
#import "WDCanceRegisterlViewController.h"
#import "EvaluateView.h"





@interface SCMyPreOrderFinalAllViewController ()<UITableViewDelegate,UITableViewDataSource,cancelPreorderDelegate,evaluateDoctorDelegate>

@property(nonatomic,strong)UITableView *table;
@property(nonatomic,strong)NSArray *dataArray;
@property(nonatomic,strong)SCMyPreorderListsViewModel *viewModel;


@end

@implementation SCMyPreOrderFinalAllViewController


-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    [self requestData];
    
}


- (void)viewDidLoad {
    [super viewDidLoad];
    
        //初始化viewModel
    [self initViewModel];
        //绑定相关
    [self bindRac];
    self.view.backgroundColor=[UIColor bc2Color];
    self.table=[UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    self.table.rowHeight=259.f;
    [self.table setBackgroundColor: [UIColor clearColor]];
    
    self.table.mj_header = [UIUtility headerRefreshTarget:self action:@selector(requestData)];
    [self.table mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.bottom.equalTo(self.view);
        
    }];
    
 
}


-(void)initViewModel{
    self.viewModel=[[SCMyPreorderListsViewModel alloc]init];

}


#pragma mark 获得数据
-(void)requestData{
    
    [self.view hiddenFailView];
    [LoadingView showLoadingInView:self.view];
    
    
    [self.viewModel getPreOrderDataFromeServer:self.preOrderState success:^{
        
//        if (self.viewModel.datas.count==0) {
//            if (self.delegate) {
////                [self.delegate allPreorderCount:0];
//            }
//        }else{
//            if (self.delegate) {
//                if (self.preOrderState==0) {
////                    [self.delegate allPreorderCount:(int)self.viewModel.datas.count];
//                }
//            }
//        }
        [self endRefresh];
        [self.table reloadData];
        
    } failed:^{
        
        [self endRefresh];

    }];
}


#pragma mark 拉取更多数据
- (void)requestMoreData {
    WS(weakSelf);
    [self.view hiddenFailView];
    [LoadingView showLoadingInView:self.view];
    
    [self.viewModel getMorePreOrderDataFromeServer:self.preOrderState success:^{
        [weakSelf.table reloadData];
        if (self.viewModel.datas.count==0) {
            if (self.delegate) {
//                [self.delegate allPreorderCount:0];
            }
        }else{
            if (self.delegate) {
                if (self.preOrderState==0) {
//                    [self.delegate allPreorderCount:(int)self.viewModel.datas.count];
                }
            }
        }
        [weakSelf endRefresh];
    } failed:^{
        [weakSelf endRefresh];
        
    }];

 
    
    
}


-(void)bindRac{
    
    WS(weakSelf)
    [RACObserve(self, viewModel.hasMore) subscribeNext:^(id x) {
        
        BOOL more = [x boolValue];
        if (more) {
            weakSelf.table.mj_footer = [UIUtility footerMoreTarget:self action:@selector(requestMoreData)];
        }
        else {
            weakSelf.table.mj_footer = nil;
        }
    }];
    
    [RACObserve(self, viewModel.requestCompeleteType) subscribeNext:^(id x) {
        RequestCompeleteType type = [x integerValue];
        
        switch (type) {
            case RequestCompeleteEmpty: {
                [weakSelf.view showFailView:FailViewEmpty withAction:^{
                    [weakSelf requestData];
                }];
            }
                break;
            case RequestCompeleteError: {
                [weakSelf.view showFailView:FailViewError withAction:^{
                    [weakSelf requestData];
                }];
            }
                break;
            case RequestCompeleteNoWifi: {
                [weakSelf.view showFailView:FailViewNoWifi withAction:^{
                    [weakSelf requestData];
                }];
            }
                break;
            default:
                break;
        }
    }];
}


-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
     return self.viewModel.datas.count;
}


-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    if (section==0) {
        return 10.f;
    }
    return 0.000001f;
}


-(CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 0.00001f;
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{

    static NSString *identifier = @"SCMyPreOrderListsFinalCell";
    SCMyPreOrderListsFinalCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[SCMyPreOrderListsFinalCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    cell.cellModel=self.viewModel.datas[indexPath.row];
    
    cell.cancelDelegate=self;
    cell.evaluateDelegate=self;
    return cell;
    
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    SCMyPreorderDetailViewController *preOrderVC=[[SCMyPreorderDetailViewController alloc]init];
    SCMyOrderModel *model = self.viewModel.datas[indexPath.row];
        //传递参数
    preOrderVC.OrderIdString=model.orderId;
    
    preOrderVC.hidesBottomBarWhenPushed=YES;
    [self.navigationController pushViewController:preOrderVC animated:YES];
}

    //取消预约成功
-(void)cancelPreOrderSuccess:(NSDictionary *)dic{
    
  //  WS(weakSelf);
    
    
    if (self.viewModel) {
        
            //取消预约
        [self.viewModel cancelPreOrderWithParams:dic success:^{
            
                //取消预约成功跳转到相关页面
            WDCanceRegisterlViewController  *vc=[[WDCanceRegisterlViewController alloc]init];
            vc.isShowTitlePopRoot = YES;
            [self.navigationController pushViewController:vc animated:YES];
            
        } failed:^{
            
        }];
       
            //获取数据
//        [self.viewModel getPreOrderDataFromeServer:self.preOrderState success:^{
//            [weakSelf.table reloadData];
//            if (self.viewModel.datas.count==0) {
//                if (self.delegate) {
//                    [self.delegate allPreorderCount:0];
//                }
//            }else{
//                if (self.delegate) {
//                    if (self.preOrderState==0) {
//                        [self.delegate allPreorderCount:(int)self.viewModel.datas.count];
//                    }
//                }
//            }
//            [weakSelf endRefresh];
//        } failed:^{
//            [weakSelf endRefresh];
//            
//        }];
}

    
    
}


    //评价医生成功
-(void)evaluateDoctorSuccess:(NSDictionary *)dic{
        //刷新VC
//    WS(weakSelf);
    
    if (self.viewModel) {
        
        [EvaluateView createEvaluateView:@"评价医生" buttoncancelTitle:@"取消" committitle:@"提交" starsNum:0 type:EvaluateViewTwo  cancelAction:^(id view) {
            [view removeFromSuperview];
        } commitAction:^(NSString *string, NSInteger selectNumber ,EvaluateView * view) {
            NSLog(@"%@ %ld",string ,(long)selectNumber);
            
            NSString *trimStr = [string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
            NSLog(@"%@ %ld",string ,(long)selectNumber);
            
            if (string.length==0 || trimStr.length == 0) {
                
                [MBProgressHUDHelper showHudWithText:@"请输入评价内容!"];
                
                return ;
            }

            
                //[dic setValue:string forKey:@"content"];
            NSDictionary *testDic=@{@"content":string};
            
            
            NSMutableDictionary *newDic=[NSMutableDictionary dictionaryWithDictionary:dic];
            [newDic setValuesForKeysWithDictionary:testDic];
            
            
            
            [MBProgressHUDHelper showHudIndeterminate];
            
            [[ResponseAdapter sharedInstance]request:DOCTOR_EVALUATE params:newDic class:nil responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
                [MBProgressHUDHelper showHudWithText:@"评价医生成功!"];
                
                [self requestData];

            } failure:^(NSURLSessionDataTask *task, NSError *error) {
                [MBProgressHUDHelper showHudWithText:@"评价医生失败!"];
                
            }];
            [view removeFromSuperview];
        }];
/*
            //获取数据
        [self.viewModel getPreOrderDataFromeServer:self.preOrderState success:^{
            [weakSelf.table reloadData];
            if (self.viewModel.datas.count==0) {
                if (self.delegate) {
//                    [self.delegate allPreorderCount:0];
                }
            }else{
                if (self.delegate) {
                    if (self.preOrderState==0) {
//                        [self.delegate allPreorderCount:(int)self.viewModel.datas.count];
                    }
                }
            }
            [weakSelf endRefresh];
        } failed:^{
            [weakSelf endRefresh];
            
        }];
 */
    }
}



    //停止刷新
-(void)endRefresh{
    [LoadingView hideLoadinForView:self.view];
    [self.table.mj_header endRefreshing];
    [self.table.mj_footer endRefreshing];

}







@end
