//
//  OrderDetailViewController.m
//  HCPatient
//
//  Created by wuhao on 2016/10/31.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCMyPreorderDetailViewController.h"
#import "BaseTableViewCell.h"
#import "SCMyPreorderDetailViewModel.h"
#import "EvaluateView.h"
#import "WDCanceRegisterlViewController.h"
#import "PreorderDetailViewCell.h"



@interface SCMyPreorderDetailViewController ()

@property(nonatomic,strong)UITableView *table;//展示表格
@property(nonatomic,strong)NSArray *dataArray;//数据数组
@property(nonatomic,strong)NSArray *titleArary;//标题数组
@property(nonatomic,strong)UIButton *functionButton;//功能按钮(取消预约,已取消,评价医生,已评价)
@property(nonatomic,strong)UIView *bottomWhiteView;//底下的白色背景
@property(nonatomic,strong)UILabel *orderStateLabel;//订单状态标签
@property(nonatomic,strong)UILabel *orderStateDetailLabel;//订单状态详细标签
@property(nonatomic,strong)SCMyPreorderDetailViewModel *viewModel;

//@property(nonatomic,strong)NSDictionary *cancelPreOrderDic;//取消预约的字典

@end

@implementation SCMyPreorderDetailViewController

-(void)viewWillAppear:(BOOL)animated{

        //请求数据
    [self requestData];
    [super viewWillAppear:animated];

}



- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.navigationItem.title=@"预约详情";
    
    _titleArary=[NSArray new];
    _titleArary=@[@"医院",@"科室",@"医生",@"门诊类型",@"挂号费",@"就诊时间",@"就诊地点"];
    
    [self createUI];

        //Rac
        //[self racBinding];
    
}


- (void)updateUI{
    
    self.functionButton.tag = 0;
    [self.bottomWhiteView setHidden: NO];

    
    NSString *totalString=@"订单状态: ";
    NSString *contentString=@"";
    UIColor *color=[UIColor redColor];
    switch ([self.viewModel.detailModel.orderStatus intValue]) {
        case 1:
            //            contentString=[NSString stringWithFormat:@"待就诊 (%i天后就诊)",sub];
            contentString=@"待就诊";
            color=[UIColor stc1Color];
            break;
            
        case 2:
            //            contentString=[NSString stringWithFormat:@"待就诊 (%i天后就诊)",sub];
            contentString= @"待就诊";
            color=[UIColor stc1Color];
            break;
            
        case 3:
            contentString=@"已取消";
            color=[UIColor tc2Color];
            break;
        case 4:
            contentString=@"已就诊";
            color=[UIColor stc4Color];
            break;
        case 6:
            contentString=@"已爽约";
            color=[UIColor redColor];
            break;
        
            
    }
    
    totalString=[totalString stringByAppendingString:contentString];
    
    
    NSMutableAttributedString *testString=[[NSMutableAttributedString alloc]initWithString:totalString];
    [testString addAttribute:NSForegroundColorAttributeName value:[UIColor tc2Color] range:NSMakeRange(0, 4)];
    [testString addAttribute:NSForegroundColorAttributeName value:color range:NSMakeRange(5, totalString.length-5)];
    
    
    WS(weakSelf)
    if (!self.orderStateLabel) {
        //订单状态标签
        
        self.orderStateLabel=[UISetupView setupLabelWithSuperView:self.view withText:@"" withTextColor:[UIColor tc2Color] withFontSize:12];
        self.orderStateLabel.attributedText=testString;
        
        [self.orderStateLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(self.view.mas_top).offset(15);
            make.bottom.equalTo(weakSelf.table.mas_top).offset(-15);
            //make.size.mas_equalTo(CGSizeMake(200, 12));
            
            make.centerX.equalTo(self.view);
            
            
        }];
        
        
    }else{
        
        if (self.orderStateLabel.attributedText!=nil) {
            self.orderStateLabel.attributedText=nil;
            self.orderStateLabel.attributedText=testString;
            
        }
    }
    
    
    if ([self.viewModel.detailModel.orderStatus intValue]==1 || [self.viewModel.detailModel.orderStatus intValue]==2) {
        //取消预约
        [self.functionButton setTitle: @"取消预约" forState: UIControlStateNormal];
        self.functionButton.backgroundColor=[UIColor bc3Color];
        self.functionButton.tag = 101;
    }
    
    //已取消或已爽约,没有按钮了
    if ([self.viewModel.detailModel.orderStatus intValue]==3 || [self.viewModel.detailModel.orderStatus intValue]==6) {
        [self.bottomWhiteView setHidden: YES];
    }
    
    //已就诊未评价
    if ([self.viewModel.detailModel.orderStatus intValue]==4 && ![self.viewModel.detailModel.isEvaluated boolValue]) {
        [self.functionButton setTitle: @"评价医生" forState: UIControlStateNormal];
        [self.functionButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        self.functionButton.backgroundColor=[UIColor tc5Color];
        self.functionButton.tag = 102;
    }
    //已就诊已评价
    if ([self.viewModel.detailModel.orderStatus intValue]==4 && [self.viewModel.detailModel.isEvaluated boolValue]) {
        
        [self.functionButton setTitle: @"已评价" forState: UIControlStateNormal];
        [self.functionButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        self.functionButton.backgroundColor=[UIColor bc3Color];

    }
}

- (void)buttonAction:(UIButton *)sender {
    if (sender.tag == 101) {
        //取消预约的点击事件,等待接口补齐
        UIAlertView *alert=[[UIAlertView alloc]initWithTitle:nil message:@"是否取消本次预约" delegate:self cancelButtonTitle:@"暂不取消" otherButtonTitles:@"立即取消", nil];
        alert.delegate=self;
        [alert show];
    }
    else if  (sender.tag == 102) {
        NSDictionary *dic=@{@"orderId":self.viewModel.detailModel.orderId,@"uid":[UserManager manager].uid,};
        //评价医生
        [self evaluateDoctorMethodWithDic: dic];
    }
}

    //请求数据
-(void)requestData{
    
    [LoadingView showLoadingInView:self.view];
    
    self.viewModel=[[SCMyPreorderDetailViewModel alloc]init];
//    self.viewModel.delegate=self;
    
    [self.viewModel getPreOrderDetailDataFromeServer:self.OrderIdString success:^{
        [LoadingView hideLoadinForView: self.view];
        [self updateUI];
        [self.table reloadData];
    } failed:^{
        [LoadingView hideLoadinForView: self.view];
    }];
    
}


-(UIButton *)functionButton {
    if (!_functionButton) {
        _functionButton = [UIButton buttonWithType: UIButtonTypeCustom];
        [_functionButton.titleLabel setFont: [UIFont systemFontOfSize: 15.0]];
        [_functionButton setTitleColor: [UIColor tc1Color] forState: UIControlStateNormal];
        _functionButton.backgroundColor=[UIColor bc3Color];
        _functionButton.layer.masksToBounds=YES;
        _functionButton.layer.cornerRadius=8.f;
        [_functionButton addTarget: self action:@selector(buttonAction:) forControlEvents: UIControlEventTouchUpInside];
        
    }
    return _functionButton;
}



    //搭建UI
-(void)createUI{
       WS(weakSelf);
    
        //右边的完成按钮
//    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithTitle:@"完成" style:UIBarButtonItemStylePlain target:self action:@selector(pressDoneButton)];
//    self.navigationItem.rightBarButtonItem.tintColor=[UIColor  colorWithHexString:@"#2E7AF0"];
    
    self.bottomWhiteView=[UISetupView setupViewWithSuperView:self.view withBackGroundColor:[UIColor whiteColor]];
    [self.bottomWhiteView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view.mas_left);
        make.right.equalTo(weakSelf.view.mas_right);
        make.bottom.equalTo(weakSelf.view.mas_bottom);
        make.size.mas_equalTo(CGSizeMake(self.view.width, 64));
        
    }];
    
    [self.bottomWhiteView addSubview: self.functionButton];
    self.functionButton.tag = 0;
    [self.functionButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.bottomWhiteView.mas_left).offset(15);
        make.right.equalTo(self.bottomWhiteView.mas_right).offset(-15);
        make.bottom.equalTo(self.bottomWhiteView.mas_bottom).offset(-10);
        make.top.equalTo(self.bottomWhiteView.mas_top).offset(10);
    }];

    [self.bottomWhiteView setHidden: YES];
    
        //创建Tabel
    self.table=[UISetupView setupTableViewWithSuperView:self.view withStyle:UITableViewStyleGrouped withDelegateAndDataSource:self];
    self.table.scrollEnabled=YES;
    self.table.rowHeight=44.f;
    
    [self.table mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.view.mas_top).offset(45);
        make.left.equalTo(self.view.mas_left);
        make.width.equalTo(self.view);
        make.bottom.equalTo(weakSelf.bottomWhiteView.mas_top);
    }];
    
    
        //白色背景 [UIColor colorWithHexString:@"#FFFFFF"]
    
        //拼接字符串
    
        //self.detailModel.orderTime=@"2016-11-24";
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    if (section==2) {
        return 0.0000001f;
    }
    return 14.f;
    
}



- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 0.00000001f;
    
}

-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}

    //创建组数
-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    if (self.viewModel.detailModel) {
        return 3;
    }
    return 0;
    
    
}


    //组数里面的行数
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (section==1) {
        return 6;
    }
    return 1;
}



    //配置cell
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    static NSString *identifier = @"PreorderDetailViewCell";
    PreorderDetailViewCell *cell=[tableView dequeueReusableCellWithIdentifier: identifier];
    if (cell==nil) {
        cell=[[PreorderDetailViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier: identifier];
    }
    
    if (indexPath.section==0) {
        cell.titleLabel.text =  @"就诊人";
        
        cell.contentLabel.text = self.viewModel.detailModel.patientName;
        
        cell.lineTopHidden=NO;
        cell.lineBottomHidden=NO;
    }
    else if (indexPath.section==1) {
        
        cell.titleLabel.text = self.titleArary[indexPath.row];
        
        
        NSString *contentString=@"";
        NSString *timeRangeString=@"";
        if (self.model.timeRnge==0) {
            timeRangeString=@"上午";
        }else{
            timeRangeString=@"下午";
        }
        
        switch (indexPath.row) {
            case 0:
                contentString=self.viewModel.detailModel.hosOrgName;
            break;
            case 1:
                contentString=self.viewModel.detailModel.deptName;
            break;
            case 2:
                contentString=self.viewModel.detailModel.doctName;
                break;
            case 3:
                contentString=self.viewModel.detailModel.visitLevel;
                break;
            case 4:
                contentString=[NSString stringWithFormat:@"%@元",self.viewModel.detailModel.visitCost];
                break;
            case 5:
                
//                contentString=[NSString stringWithFormat:@"%@  %@  %@",self.detailModel.orderTime,self.model.week,self.detailModel.timeRange];
                    //暂时没有周几这个字段
                contentString = self.viewModel.detailModel.orderTime;
                break;
        }
        
        cell.contentLabel.text = contentString;

        
        if (indexPath.row==0) {
            cell.lineTopHidden=NO;
            cell.lineBottomHidden=NO;
        }
        cell.lineTopHidden=YES;
        cell.lineBottomHidden=NO;
    }
    else if (indexPath.section==2) {
        
        cell.titleLabel.text    = @"预约单号";
        cell.contentLabel.text = self.viewModel.detailModel.showOrderId;

        cell.lineTopHidden=NO;
        cell.lineBottomHidden=NO;
    }
    
    
    return cell;
}


    //rac绑定
-(void)racBinding{

}




#pragma mark 完成按钮的点击事件
-(void)pressDoneButton{
   // WS(weakSelf);
        //根据某个字段,未定,来判断是否已取消预约,已评价医生
    [RACObserve(self.model, state)subscribeNext:^(id x) {
    
    }];



}


#pragma mark 取消预约
-(void)cancelPreOrderMethodWithDic:(NSDictionary *)dic{
    if (dic==nil) {
        return;
    }
    
    [MBProgressHUDHelper showHudIndeterminate];
    [[ResponseAdapter sharedInstance]request:ORDER_CANCEL params:dic class:nil responseType:Response_Object method:Request_POST needLogin:YES success:^(NSURLSessionDataTask *task, ResponseModel *response) {
        
        [MBProgressHUDHelper showHudWithText: response.message ? response.message : @"取消预约成功!"];
            //预约成功跳转到的页面
        WDCanceRegisterlViewController *vc=[[WDCanceRegisterlViewController alloc]init];
        vc.isShowTitlePopRoot=YES;
        [self.navigationController pushViewController:vc animated:YES];


    } failure:^(NSURLSessionDataTask *task, NSError *error) {
        [MBProgressHUDHelper showHudWithText: error.localizedDescription];
    }];
    
}


#pragma mark 评价医生
-(void)evaluateDoctorMethodWithDic:(NSDictionary *)dic{
    
    
    [EvaluateView createEvaluateView:@"评价医生" buttoncancelTitle:@"取消" committitle:@"提交" starsNum:0 type:EvaluateViewTwo  cancelAction:^(id view) {
        [view removeFromSuperview];
    } commitAction:^(NSString *string, NSInteger selectNumber ,EvaluateView * view) {
        
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

                //评价医生成功
            if (self.functionButton) {
                [self.functionButton setTitle:@"已评价" forState:UIControlStateNormal];
                [self.functionButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
                self.functionButton.backgroundColor=[UIColor bc3Color];
                self.functionButton.layer.masksToBounds=YES;
                self.functionButton.layer.cornerRadius=8.f;
                [self.functionButton setEnabled:NO];
            }
            
        } failure:^(NSURLSessionDataTask *task, NSError *error) {
            [MBProgressHUDHelper showHudWithText:@"评价医生失败!"];

        }];
        [view removeFromSuperview];
    }];
    
}


#pragma mark alertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    if (buttonIndex==0) {
        return;
    }else{
        NSDictionary *dic=@{@"orderId":self.viewModel.detailModel.orderId,@"cancelObj":@1,@"cancelReason":@1};
        [self cancelPreOrderMethodWithDic: dic];
    }
    
}












@end
