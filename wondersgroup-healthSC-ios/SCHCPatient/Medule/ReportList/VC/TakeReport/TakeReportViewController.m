//
//  TakeReportViewController.m
//  SCHCPatient
//
//  Created by 刘松洪 on 2016/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "TakeReportViewController.h"
#import "ReportListViewController.h"
#import "ChooseReportTimeVC.h"
#import "PerfectInformationView.h"
#import "TakenReportTableViewCell.h"
#import "TakenReportViewModel.h"
#import "SCHospitalModel.h"
#import "ElectronicPrescribingVC.h"
#import "TakenReportSearchHistoryView.h"
#import "DBManager.h"

static NSString *const TABLECELL =  @"TakenReportTableViewCell";
@interface TakeReportViewController () <UIGestureRecognizerDelegate,UITableViewDelegate,UITableViewDataSource> {
    BOOL _requestFlag;//判断模糊搜索，网络请求是否成功,初始化为YES
    UIView   *_shadeView;//遮罩层
    NSString *_hospitalId;//记录所选择的医院ID,提取报告列表需要用
    NSString *_errorDescription;//记录服务器返回的错误描述信息
}

@property (strong, nonatomic) UITextField *inputName;
@property (strong, nonatomic) UILabel  *contentLabel;
@property (strong, nonatomic) UIButton *bgLabelBtn;
@property (strong, nonatomic) UIButton *takenButton;
@property (strong, nonatomic) UITableView *myTableView;
@property (strong, nonatomic) TakenReportViewModel *viewModel;
@property (strong, nonatomic) TakenReportSearchHistoryView *takenReportSearchHistoryView;
@property (strong, nonatomic) SCHospitalModel *selectedHospital;//用于记录，存历史

@end

@implementation TakeReportViewController

- (instancetype)init {
    self = [super init];
    if (self) {
        self.viewModel  = [[TakenReportViewModel alloc]init];
        _requestFlag    = YES;
     
    }
    return self;
}

- (instancetype)initWithUrlParameter:(NSDictionary *)parameter {
    self = [super init];
    if (self) {
        self.viewModel = [[TakenReportViewModel alloc]init];
        _requestFlag   = YES;
        self.reportTime = -1;//防止干扰第一次进入选择时间页面默认选择今天
        self.reportTime1 = -1;//防止干扰第一次进入选择时间页面默认选择今天
        self.type = [parameter[@"type"] integerValue];
        
    }
    return self;
}

- (UITableView *)myTableView {
    if (!_myTableView) {
        _myTableView                    = [[UITableView alloc]initWithFrame:CGRectMake(0, 60.0 + 1.0/*底部两条线高0.5*2 = 1.0 */, self.view.width, 308.0) style:UITableViewStylePlain];
        _myTableView.separatorStyle     = UITableViewCellSeparatorStyleNone;
        _myTableView.delegate           = self;
        _myTableView.dataSource         = self;
        _myTableView.backgroundColor    = [UIColor colorWithHex:0xF6F6F6];
        _myTableView.rowHeight          = 55.0;
        _myTableView.hidden             = YES;
        [self.view addSubview:self.myTableView];
        [_myTableView registerClass:[TakenReportTableViewCell class] forCellReuseIdentifier:TABLECELL];
        
        //同时建立一个遮罩层
        _shadeView                 = [[UIView alloc]initWithFrame:_myTableView.frame];
        _shadeView.backgroundColor = [UIColor redColor];
        [self.view addSubview:_shadeView];
    }
    return _myTableView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self createUI];
    [self RACBind];
    
    if (self.type == 1) {
        self.reportTime = Today;
        self.contentLabel.text = @"今天";
    }
}

- (void)RACBind {
    @weakify(self)
    RACSignal *requestCompeleteTypeSignal = RACObserve(self.viewModel, requestCompeleteType);
    [requestCompeleteTypeSignal subscribeNext:^(NSNumber *type) {
        @strongify(self)
        if ([type intValue] == 0) {
            return ;
        }
        [LoadingView hideLoadinForView:_shadeView];
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
                    [_shadeView showFailViewWith:[UIImage imageNamed:@"网络出错"] withTitle:_errorDescription withAction:^{
                        [self requestData];
                    }];
                }else {
                    failType = FailViewError;
                }
            }
                break;
            case RequestCompeleteSuccess: {
                [_shadeView hiddenFailView];
                [self.myTableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        if (failType != FailViewUnknow) {
            [_shadeView showFailView:failType withAction:^{
                [self requestData];
            }];
        }
    }];

    RACSignal *mytableHiddenSignal = RACObserve(self, myTableView.hidden);
    [[RACSignal combineLatest:@[requestCompeleteTypeSignal,mytableHiddenSignal] reduce:^id(NSNumber *returnType,NSNumber *hidden){
        if (returnType.integerValue == 0) {//rac 初始化监听会返回0 忽略掉表现为:_shadeView.hidden 隐藏
            return @(YES);
        }
        if (returnType.integerValue == 103 || hidden.boolValue) {//当数据请求成功，或者myTableView 隐藏时 ，那么 _shadeView 也需要隐藏
            return @(YES);
        }
        return @(NO);
    }] subscribeNext:^(NSNumber *hidden) {
        _shadeView.hidden = hidden.boolValue;
    }];
}


- (void)endRefreshing {
    [self.myTableView.mj_header endRefreshing];
    [self.myTableView.mj_footer endRefreshing];
}


- (void)createUI {
    WS(weakSelf)
    
    if (self.type == 1) {
        self.title = @"提取报告";
        
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"规则" style:UIBarButtonItemStylePlain target:self action:@selector(rule)];
        
    }else {
        self.title = @"电子处方";
    }

    self.view.backgroundColor = [UIColor bc2Color];
    
    self.inputName                 = [[UITextField alloc]init];
    self.inputName.tag             = 100;
    self.inputName.placeholder     = @"请输入医院名称";
    self.inputName.backgroundColor = [UIColor bc6Color];
    self.inputName.textColor       = [UIColor tc2Color];
    self.inputName.leftViewMode    = UITextFieldViewModeAlways;
    self.inputName.leftView        = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 15.0, 50.0)];
    self.inputName.font            = [UIFont systemFontOfSize:16.0];
    self.inputName.returnKeyType   = UIReturnKeyDefault;
    self.inputName.clearButtonMode = UITextFieldViewModeWhileEditing;
    [self.inputName setValue:[UIColor tc2Color] forKeyPath:@"_placeholderLabel.textColor"];
    [self.view addSubview:self.inputName];
    [self.inputName mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.view).offset(10.0);
        make.left.and.right.equalTo(weakSelf.view);
        make.height.mas_equalTo(50.0);
    }];
    
    UIView *lineView         = [[UIView alloc]init];
    lineView.backgroundColor = [UIColor dc4Color];
    [self.view addSubview:lineView];
    [lineView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(weakSelf.inputName.mas_bottom);
        make.left.equalTo(weakSelf.view).offset(10.0);
        make.right.equalTo(weakSelf.view);
        make.height.mas_equalTo(0.5);
    }];
    
    @weakify(self)
    //RAC 监听模糊搜索的内容
    [[self.inputName rac_signalForControlEvents:UIControlEventEditingChanged] subscribeNext:^(UITextField *textfiled) {
        if (textfiled.text.length > 0) {
            self.takenReportSearchHistoryView.hidden = YES;
            self.viewModel.keyWord = self.inputName.text;
            [LoadingView showLoadingInView:_shadeView];
            if (_requestFlag) {
                _requestFlag = NO;
               [self requestData];
            }
            if (self.myTableView.hidden) {
//                [self updateBtnFrame];
                lineView.backgroundColor = [UIColor bc7Color];
            }
            self.myTableView.hidden = NO;
        }else {
            self.takenReportSearchHistoryView.hidden = NO;
                self.myTableView.hidden = YES;
                lineView.backgroundColor = [UIColor dc4Color];
//            [self resumeBtnFrame];
        }
    }];
    //监听return按钮
    [[self.inputName rac_signalForControlEvents:UIControlEventEditingDidEndOnExit] subscribeNext:^(UITextField *textfiled) {
        [textfiled resignFirstResponder];
    }];
    
    
    self.bgLabelBtn        = [[UIButton alloc]init];
    self.bgLabelBtn.backgroundColor  = [UIColor bc6Color];
    [self.view addSubview:self.bgLabelBtn];
    [self.bgLabelBtn mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(lineView.mas_bottom);
        make.left.and.right.equalTo(weakSelf.inputName);
        make.height.equalTo(weakSelf.inputName);
    }];
//
    self.bgLabelBtn.rac_command = [[RACCommand alloc]initWithSignalBlock:^RACSignal *(UIButton *sender) {
        ChooseReportTimeVC *vc = [[ChooseReportTimeVC alloc]init];
        vc.reportCategoryType = self.type;
        if (self.type == 1) {
            vc.defalutChoose = [NSNumber numberWithInteger:self.reportTime];
            vc.chooseTimerBlockAction = ^(NSString *text,NSInteger type){
                self.contentLabel.text = text ==nil?@"请选择时间段":text;
                self.reportTime = type;
            };

        }else {
            vc.defalutChoose = [NSNumber numberWithInteger:self.reportTime1];
            vc.chooseTimerBlockAction = ^(NSString *text,NSInteger type){
                self.contentLabel.text = text ==nil?@"请选择时间段":text;
                self.reportTime1 = type;
            };

        }
        [self.navigationController pushViewController:vc animated:YES];
        return [RACSignal empty];
    }];
   
    self.contentLabel                 = [[UILabel alloc]init];
    self.contentLabel.text            = @"请选择时间段";
    self.contentLabel.backgroundColor = [UIColor bc6Color];
    self.contentLabel.textColor       = [UIColor tc2Color];
    self.contentLabel.font            = [UIFont systemFontOfSize:16.0];
    [self.view addSubview:self.contentLabel];
    [self.contentLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.bgLabelBtn).offset(15.0);
        make.top.and.bottom.equalTo(weakSelf.bgLabelBtn);
        make.right.equalTo(weakSelf.bgLabelBtn);
    }];
    
    
    
    UIImageView *imageView   = [[UIImageView alloc]init];
    imageView.image          = [UIImage imageNamed:@"link_right"];
    [self.view addSubview:imageView];
    [imageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.right.equalTo(weakSelf.bgLabelBtn).offset(-15.0);
        make.size.mas_equalTo(CGSizeMake(7.0, 14.0));
        make.centerY.equalTo(weakSelf.bgLabelBtn);
    }];
    
    UIView *bottomLine         = [[UIView alloc]init];
    bottomLine.backgroundColor = [UIColor dc4Color];
    [self.view addSubview:bottomLine];
    [bottomLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(lineView);
        make.top.equalTo(weakSelf.bgLabelBtn.mas_bottom);
        make.right.equalTo(weakSelf.view);
        make.height.mas_equalTo(0.5);
    }];
    
    CGFloat padding   = 15.0;
     self.takenButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.takenButton setTitle:@"提取报告" forState:UIControlStateNormal];
    [self.takenButton setTitleColor:[UIColor colorWithHex:0xffffff] forState:UIControlStateNormal];
    [self.takenButton setCornerRadius:4.0];
    [self.takenButton setBackgroundImage:[UIImage imageWithColor:[UIColor colorWithHex:0xffa217]] forState:UIControlStateNormal];
    [self.view addSubview:self.takenButton];
    [self.takenButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(padding);
        make.right.equalTo(weakSelf.view).offset(-padding);
        make.centerY.equalTo(weakSelf.view);
        make.height.mas_equalTo(44.0);
    }];
    [[self.takenButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x) {
      @strongify(self)
        if ([self.inputName.text isEqualToString:@""] || [self.contentLabel.text isEqualToString:@"请选择时间段"]) {
            [MBProgressHUDHelper showHudWithText:@"提交的信息不完善，无法查询"];
        }else {
            if (self.type == 1) {//条提取报告
                ReportListViewController *vc = [[ReportListViewController alloc]init];
                vc.medicalOrgId              = _hospitalId;
                vc.day                       = [NSNumber numberWithInteger:self.reportTime];
                [self.navigationController pushViewController:vc animated:YES];
                
                //存历史
                [self saveHistoryData];
            }else {//条电子处方
                ElectronicPrescribingVC *vc  = [[ElectronicPrescribingVC alloc]init];
                vc.viewModel.medicalOrgId    = _hospitalId;
                vc.viewModel.day             = [NSNumber numberWithInteger:self.reportTime1];
                [self.navigationController pushViewController:vc animated:YES];
            }
        }
    }];
    
    
    if (self.type == 1) {
        _takenReportSearchHistoryView = [[TakenReportSearchHistoryView alloc] init];
        [self.view addSubview:_takenReportSearchHistoryView];
        [_takenReportSearchHistoryView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.top.mas_equalTo(weakSelf.takenButton.mas_bottom).offset(10);
            make.bottom.equalTo(weakSelf.view.mas_bottom);
            make.left.right.equalTo(weakSelf.view);
        }];
        
        WS(weakSelf)
        _takenReportSearchHistoryView.historySelectBlock = ^(SCHospitalModel *model) {
//            ReportListViewController *vc = [[ReportListViewController alloc]init];
//            vc.medicalOrgId              = model.hospitalId;
//            vc.day                       = [NSNumber numberWithInteger:weakSelf.reportTime];
//            [weakSelf.navigationController pushViewController:vc animated:YES];
            weakSelf.inputName.text = model.hospitalName;
            _hospitalId = model.hospitalCode;
            weakSelf.selectedHospital = model;
        };
    }
}

#pragma mark rule
- (void)rule {
    WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeOne];
    [alert reloadTitle:@"温馨提示" content:@"1、输入医院名称和时间段，即可查看检验检查报告；\n2、检验检查报告需实名认证；\n3、特殊项目（如HIV、病毒、微生物项目）请到医院查询；\n4、查询结果不作为任何医学凭据，请以医院提供的报告单为准。"];
    alert.contentLabel.textAlignment = NSTextAlignmentLeft;
    [alert.cancelBtn setTitle:@"知道啦" forState:UIControlStateNormal];
    [alert.cancelBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
    
    alert.cancelBlock = ^(WDAlertView *view) {
        [view dismiss];
    };
    
    [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
}

#pragma mark saveHistoryData
- (void)saveHistoryData {
    [[DBManager manager] saveTakeReportHospital:_selectedHospital withUserid:[UserManager manager].uid withType:HospitalSearchTypeExtract];
}

#pragma mark - 还原按钮frame
- (void)resumeBtnFrame {
    WS(weakSelf)
    CGFloat padding   = 15.0;
    [self.takenButton mas_remakeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(weakSelf.view).offset(padding);
        make.right.equalTo(weakSelf.view).offset(-padding);
        make.centerY.equalTo(weakSelf.view);
        make.height.mas_equalTo(50.0);
    }];
}

#pragma mark - 改变frame
- (void)updateBtnFrame {
    WS(weakSelf)
    CGFloat padding   = 15.0;
   [self.takenButton mas_remakeConstraints:^(MASConstraintMaker *make) {
       make.left.equalTo(weakSelf.view).offset(padding);
       make.right.equalTo(weakSelf.view).offset(-padding);
       if (IS_IPHONE_5) {
        make.top.equalTo(weakSelf.myTableView.mas_bottom).offset(AdaptiveFrameHeight(80.0));
       }else {
        make.top.equalTo(weakSelf.myTableView.mas_bottom).offset(80.0);
       }
       make.height.mas_equalTo(50.0);
   }];
}



#pragma mark -请求数据
- (void)requestData {
    [self.viewModel getHostptialNameSuccess:^{
        [self.myTableView reloadData];
        [LoadingView hideLoadinForView:_shadeView];
        _requestFlag = YES;
    } failed:^(NSError *error){
        _requestFlag = YES;
        [self endRefreshing];
        [LoadingView hideLoadinForView:_shadeView];
        _errorDescription = error.localizedDescription;
    }];
}

#pragma mark - UITableViewDelegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.viewModel.dataArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    TakenReportTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:TABLECELL];
    [cell setCellModel:(SCHospitalModel *)[self.viewModel.dataArray objectAtIndex:indexPath.row] withKeyWord:self.inputName.text];
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    TakenReportTableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    self.inputName.text            = cell.nameLB.text;
    _hospitalId                    = [(SCHospitalModel *)self.viewModel.dataArray[indexPath.row] hospitalCode];
    self.selectedHospital = self.viewModel.dataArray[indexPath.row];
    tableView.hidden = YES;
    self.takenReportSearchHistoryView.hidden = NO;
//    [self resumeBtnFrame];
}



- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
//    [self.navigationController.navigationBar setBottomBorderColor:[UIColor whiteColor] height:0.5];
    
    if (self.type == 1) {
        _takenReportSearchHistoryView.historyArray = [[DBManager manager] getTakeReportHospitalWithUserid:[UserManager manager].uid withType:HospitalSearchTypeExtract];
    }
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
//    [self.navigationController.navigationBar setBottomBorderColor:[UIColor redColor] height:0.5];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
