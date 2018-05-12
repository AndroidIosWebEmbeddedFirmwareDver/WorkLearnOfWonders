//
//  AppointmentDetailViewController.m
//  SCHCPatient
//
//  Created by ZJW on 2016/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCAppointmentDetailViewController.h"
#import "SCAppointmentDetailInfoCell.h"
#import "SCAppointmentDetailRuleCell.h"
#import "SCAppointmentDetailVisitsCell.h"
#import "SCAppointmentDetailInputCell.h"
#import "AppointmentResultViewController.h"

@interface SCAppointmentDetailViewController ()<UITableViewDelegate,UITableViewDataSource>
{

}
@property (nonatomic, strong) UITableView *myTableView;

@end

@implementation SCAppointmentDetailViewController

#pragma mark    - lifecycle

-(instancetype)init{
    self = [super init];
    if (self) {

        self.viewModel = [SCAppointmentDetailViewModel new];
        
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
//    [self verificationAlertTips];

    [self setupView];
    [self bindViewModel];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc {

}


#pragma mark    - setupView
-(void)setupView {
    self.navigationItem.title = @"预约信息";
    self.view.backgroundColor = [UIColor bc2Color];
    
    WS(weakSelf)
    
    UITableView *tableViewL = [[UITableView alloc] initWithFrame: CGRectMake(0, 0, SCREEN_WIDTH, self.view.frame.size.height)
                                                           style: UITableViewStyleGrouped];
    tableViewL.delegate      = self;
    tableViewL.dataSource    = self;
    [tableViewL setBackgroundColor: [UIColor clearColor]];
    [tableViewL setSeparatorColor: [UIColor clearColor]];
    [tableViewL setSeparatorStyle: UITableViewCellSeparatorStyleNone];
    [self.view addSubview: tableViewL];
    self.myTableView = tableViewL;
    
    [self.myTableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(weakSelf.view);
        make.bottom.equalTo(weakSelf.view).offset(-70);
    }];
    
//    UIView *btnView = [UISetupView setupViewWithSuperView:self.view withBackGroundColor:[UIColor bc1Color]];
//    [btnView mas_makeConstraints:^(MASConstraintMaker *make) {
//        make.bottom.left.right.equalTo(weakSelf.view);
//        make.height.mas_equalTo(70);
//    }];
    
    UIButton *submitBtn = [UISetupView setupButtonWithSuperView:self.view withTitleToStateNormal:@"确定预约" withTitleColorToStateNormal:[UIColor tc0Color] withTitleFontSize:16 withAction:^(UIButton *sender) {
        [weakSelf submit];
    }];
    submitBtn.backgroundColor = [UIColor bc7Color];
    [submitBtn setCornerRadius:5];
    [submitBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.centerX.equalTo(weakSelf.view);
        make.bottom.equalTo(self.view).offset(-30);
        make.left.equalTo(self.view).offset(15);
        make.height.mas_equalTo(44);
    }];
    
}

#pragma mark    - bindViewModel
-(void)bindViewModel {
//    WS(weakSelf)
//    [RACObserve(self.viewModel, requestCompeleteType) subscribeNext:^(NSNumber *type) {
//        if ([type intValue] == 0) {
//            return ;
//        }
//        [LoadingView hideLoadinForView:self.view];
//        [weakSelf endRefreshing];
//        
//        FailViewType failType = FailViewUnknow;
//        switch ([type intValue]) {
//            case RequestCompeleteEmpty:{
//                [weakSelf.view showFailView:FailViewEmpty withAction:^{
//                    [weakSelf submit];
//                }];
//            }
//                break;
//            case RequestCompeleteNoWifi:
//                failType = FailViewNoWifi;
//                break;
//            case RequestCompeleteError:
//                failType = FailViewError;
//                break;
//            case RequestCompeleteSuccess: {
//                [weakSelf.view hiddenFailView];
//
//                failType = FailViewUnknow;
//            }
//                break;
//            default:
//                break;
//        }
//        if (failType != FailViewUnknow && failType != FailViewEmpty) {
//
//            [weakSelf.view showFailView:failType withAction:^{
//                [weakSelf submit];
//            }];
//        }
//    }];
    
}

#pragma mark    - method
-(void)endRefreshing {
    [self.myTableView.mj_header endRefreshing];
    [self.myTableView.mj_footer endRefreshing];
}

-(void)submit {

//    if (self.viewModel.card.length == 0) {
//        self.viewModel.isNotInputCard = @YES;
//        return;
//    }
//    
//    [LoadingView showLoadingInView:self.view];
//    [self.viewModel submitAppointmentDetail:^(WDRegistrationPayModel *model){
//        [LoadingView hideLoadinForView:self.view];

        AppointmentResultViewController *vc = [AppointmentResultViewController new];
        vc.viewModel.isSuccess = NO;
        vc.viewModel.errorString = @"asdf";
        [self.navigationController pushViewController:vc animated:YES];
        
//    } failure:^{
//        [LoadingView hideLoadinForView:self.view];
//
//    }];
    
}

//-(void)verificationAlertTips {
//    NSString *verification  = [[UserDefaults shareDefaults] objectForKey: @"VerificationAlertTips"];
//    if (verification == nil) {
//        verification = @"1";
//        [[UserDefaults shareDefaults] setObject: verification  forKey: @"VerificationAlertTips"];
//        
//        WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeOne];
//        [alert reloadTitle:@"温馨提示" content:@"1、目前只支持有卡预约挂号；\n2、目前只支持自己在医院办理的就诊卡；\n3、请输入本账号认证身份证办理的就诊卡卡号；\n4、预约挂号成功后，携带就诊卡到医院取号或进去排队系统"];
//        alert.contentLabel.textAlignment = NSTextAlignmentLeft;
//        [alert.cancelBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
//        [alert.cancelBtn setTitle:@"确定" forState:UIControlStateNormal];
//        alert.cancelBlock = ^(WDAlertView *view) {
//            [view dismiss];
//        };
//        
//        [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
//    }
//}

#pragma mark    - UITableViewDelegate,UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 0) {
        return 1;
    }else if (section == 1) {
        return 2;
    }
    return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            static NSString *identifier = @"SCAppointmentDetailInfoCell";
            
            SCAppointmentDetailInfoCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
            if(!cell)
            {
                cell = [[SCAppointmentDetailInfoCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
                cell.lineTopHidden = YES;
                cell.lineBottomHidden = NO;
            }
            
            cell.model = self.viewModel.model;
            
            return cell;
            
        }
    }else {
        static NSString *identifier = @"SCAppointmentDetailVisitsCell";
        
        SCAppointmentDetailVisitsCell * cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if(!cell)
        {
            cell = [[SCAppointmentDetailVisitsCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        }
        if (indexPath.row == 0) {
            cell.lineTopHidden = YES;
            cell.lineBottomHidden = NO;
            cell.title = @"就诊人";
            cell.content = [UserManager manager].name;
        }else if (indexPath.row == 1) {
            cell.lineTopHidden = YES;
            cell.lineBottomHidden = NO;
            cell.title = @"预约时间段";
            NSString *startTime = [NSString stringWithFormat: @"%@", self.viewModel.model.schedule.startTime];
            NSString *endTime   = [NSString stringWithFormat: @"%@", self.viewModel.model.schedule.endTime];
            if (startTime.length > 4) {
                startTime = [startTime substringToIndex: 5];
            }
            if (endTime.length > 4) {
                endTime = [endTime substringToIndex: 5];
            }
            cell.content = [NSString stringWithFormat: @"%@-%@", startTime, endTime];
        }
        
        return cell;
            }
    
    return nil;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {

        
        }else {

        }
    }else {
        
        
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            return [SCAppointmentDetailInfoCell cellHeight];
        }
    }else {
        return [SCAppointmentDetailVisitsCell cellHeight];
    }
    return 0;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 0.5;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 10;
}

-(void)scrollViewDidScroll:(UIScrollView *)scrollView {
    [self.view endEditing:YES];
}

#pragma mark - 键盘监听事件
- (void)keyboardWillShow:(NSNotification *)notification
{
    NSDictionary *userInfo = notification.userInfo;
    NSNumber *duration = userInfo[@"UIKeyboardAnimationDurationUserInfoKey"];
    NSValue *rectValue = userInfo[@"UIKeyboardBoundsUserInfoKey"];
    CGRect rect = [rectValue CGRectValue];
    
    [UIView animateWithDuration:[duration floatValue] animations:^{
        
        CGRect inputCVFrame   = self.view.frame;
        inputCVFrame.origin.y = [UIScreen mainScreen].bounds.size.height - rect.size.height - inputCVFrame.size.height;
        self.view.frame  = inputCVFrame;
    }];

}

- (void)keyboardWillHide:(NSNotification *)notification
{
    NSDictionary *userInfo = notification.userInfo;
    NSNumber *duration = userInfo[@"UIKeyboardAnimationDurationUserInfoKey"];
    
    [UIView animateWithDuration:[duration floatValue] animations:^{
        
        CGRect inputCVFrame   = self.view.frame;
        inputCVFrame.origin.y = 64;
        self.view.frame  = inputCVFrame;
        
    }];

}
@end
