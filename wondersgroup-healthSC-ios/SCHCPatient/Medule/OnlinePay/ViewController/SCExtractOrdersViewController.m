//
//  SCExtractOrdersViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCExtractOrdersViewController.h"
#import "TakenReportTableViewCell.h"
#import "SCExtractOrdersHistoryView.h"
#import "SCExtractOrdersViewModel.h"
#import "SCPrescriptionPayViewController.h"

@interface SCExtractOrdersViewController () <UITableViewDataSource, UITableViewDelegate,UITextFieldDelegate> {
    BOOL _requestFlag;//判断模糊搜索，网络请求是否成功,初始化为YES
}

@property (nonatomic, strong) UITextField *hospitalField;
@property (nonatomic, strong) UIView *blueLine;
@property (nonatomic, strong) UIButton *extractButton;
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UIView *bgView;
@property (nonatomic, strong) NSString *hospitalCode;
@property (nonatomic, strong) NSString *hospitalName;

@property (nonatomic, strong) SCExtractOrdersViewModel *viewModel;
@property (nonatomic, strong) SCExtractOrdersHistoryView * historyView;
@end

@implementation SCExtractOrdersViewController

- (UITextField *)hospitalField {
    if (!_hospitalField) {
        _hospitalField = [UITextField new];
        _hospitalField.textColor = [UIColor tc1Color];
        _hospitalField.font = [UIFont systemFontOfSize:16];
        _hospitalField.placeholder = @"请输入医院名称";
        _hospitalField.delegate = self;
    }
    return _hospitalField;
}

- (UIButton *)extractButton {
    if (!_extractButton) {
        _extractButton = [UIButton new];
        [_extractButton setTitle:@"提取" forState:UIControlStateNormal];
        [_extractButton setTitleColor:[UIColor tc0Color] forState:UIControlStateNormal];
        _extractButton.titleLabel.font = [UIFont systemFontOfSize:16];
        _extractButton.backgroundColor = [UIColor bc7Color];
        _extractButton.layer.masksToBounds = YES;
        _extractButton.layer.cornerRadius = 3;
        [_extractButton addTarget:self action:@selector(pushToRegistration) forControlEvents:UIControlEventTouchUpInside];
        [_extractButton setBackgroundImage:[UIImage imageWithColor:[UIColor bc4Color]] forState:UIControlStateDisabled];
    }
    return _extractButton;
}

- (UITableView *)tableView {
    if (!_tableView) {
        _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 0) style:UITableViewStylePlain];
        _tableView.backgroundColor = [UIColor whiteColor];
        _tableView.dataSource = self;
        _tableView.delegate = self;
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView registerClass:[TakenReportTableViewCell class] forCellReuseIdentifier:@"TakenReportTableViewCell"];
        //_tableView.hidden = YES;
    }
    return _tableView;
}

- (SCExtractOrdersHistoryView *)getHistoryview {
    if (!_historyView) {
        _historyView = [[SCExtractOrdersHistoryView alloc] init];
        __weak typeof(self) weakSelf = self;
        [_historyView setDataWithCount:^NSInteger(NSInteger section) {
            return weakSelf.viewModel.searchHistoryArray.count;
        } title:^NSString *(NSIndexPath *indexPath) {
            SearchHistoryWithHospitalModel * model = weakSelf.viewModel.searchHistoryArray[indexPath.row];
            
            return model.cat_name;
        } selected:^(NSInteger count) {
            weakSelf.extractButton.enabled = YES;
            SearchHistoryWithHospitalModel * model = weakSelf.viewModel.searchHistoryArray[count];
            [weakSelf.hospitalField setText:model.cat_name];
            weakSelf.hospitalName = model.cat_name;
            weakSelf.hospitalCode = model.cat_id;
        }];
    }
    return _historyView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.viewModel = [[SCExtractOrdersViewModel alloc]init];
    [_viewModel getSearchHistoryData];
    _requestFlag   = YES;
    
    [self setupView];
    [self RACBind];
    _extractButton.enabled = NO;
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    [_historyView.tableView reloadData];
}

- (void)setupView {
    WS(ws)
    
    UILabel *titleLabel = [UILabel new];
    titleLabel.text = @"医院名称";
    titleLabel.textColor = [UIColor tc2Color];
    titleLabel.font = [UIFont systemFontOfSize:14];
    
    [self.view addSubview:titleLabel];
    [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(ws.view).offset(15);
        make.left.equalTo(ws.view).offset(15);
    }];
    
    _bgView = [UIView new];
    _bgView.backgroundColor = [UIColor bc1Color];
    [self.view addSubview:_bgView];
    [_bgView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(titleLabel.mas_bottom).offset(15);
        make.left.right.equalTo(ws.view);
        make.height.mas_equalTo(50);
    }];
    
    [_bgView addSubview:self.hospitalField];
    [self.hospitalField mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.equalTo(_bgView);
        make.left.equalTo(_bgView).offset(15);
        make.right.equalTo(_bgView).offset(-15);
    }];
    
    _blueLine = [UIView new];
    _blueLine.backgroundColor = [UIColor clearColor];
    [_bgView addSubview:_blueLine];
    [_blueLine mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(_bgView).offset(15);
        make.right.bottom.equalTo(_bgView);
        make.height.mas_equalTo(0.5);
    }];
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_bgView.mas_bottom);
        make.left.right.equalTo(ws.view);
        make.height.mas_equalTo(0);
    }];
    
    [self.view addSubview:self.extractButton];
    [self.extractButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_bgView.mas_bottom).offset(105);
        make.left.equalTo(ws.view).offset(15);
        make.right.equalTo(ws.view).offset(-15);
        make.height.mas_equalTo(44);
    }];
    
    [self.view addSubview:[self getHistoryview]];
    [self.historyView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.equalTo(ws.extractButton);
        make.top.equalTo(ws.extractButton.mas_bottom);
        make.bottom.equalTo(ws.view).offset(-20);
    }];
    
}

- (void)RACBind {
    @weakify(self)
    RACSignal *requestCompeleteTypeSignal = RACObserve(self.viewModel, requestCompeleteType);
    [requestCompeleteTypeSignal subscribeNext:^(NSNumber *type) {
        @strongify(self)
        if ([type intValue] == 0) {
            return ;
        }
        
        FailViewType failType = FailViewUnknow;
        switch ([type intValue]) {
            case RequestCompeleteEmpty:
                failType = FailViewEmpty;
                [_tableView mas_updateConstraints:^(MASConstraintMaker *make) {
                    make.height.mas_equalTo(0);
                }];
                [_tableView reloadData];
                break;
            case RequestCompeleteNoWifi:
                failType = FailViewNoWifi;
                break;
            case RequestCompeleteError:
                failType = FailViewError;
                break;
            case RequestCompeleteSuccess: {
                
                [_tableView mas_updateConstraints:^(MASConstraintMaker *make) {
                    float i = 2.5;
                    if (IS_IPHONE_5_OR_LESS) {
                        i = 2.5;
                    }else if (IS_IPHONE_6) {
                        i = 4.8;
                    }else if (IS_IPHONE_6P) {
                        i = 6;
                    }
                    make.height.mas_equalTo(44*MIN(i, _viewModel.dataArray.count));
                }];
                
                [_tableView reloadData];
                failType = FailViewUnknow;
            }
                break;
            default:
                break;
        }
        //        if (failType != FailViewUnknow) {
        //            [_shadeView showFailView:failType withAction:^{
        //                [self requestData];
        //            }];
        //        }
    }];
    
    //    [_hospitalField.rac_textSignal subscribeNext:^(NSString *x) {
    //        if ([x isEqualToString:_hospitalName]) {
    //            _extractButton.enabled = YES;
    //        } else {
    //            _extractButton.enabled = NO;
    //        }
    //    }];
    
    _hospitalField.delegate = self;
    [_hospitalField addTarget:self action:@selector(hospitalChange:) forControlEvents:UIControlEventEditingChanged];
    
    //    RACSignal *mytableHiddenSignal = RACObserve(self, myTableView.hidden);
    //    [[RACSignal combineLatest:@[requestCompeleteTypeSignal,mytableHiddenSignal] reduce:^id(NSNumber *returnType,NSNumber *hidden){
    //        if (returnType.integerValue == 0) {//rac 初始化监听会返回0 忽略掉表现为:_shadeView.hidden 隐藏
    //            return @(YES);
    //        }
    //        if (returnType.integerValue == 103 || hidden.boolValue) {//当数据请求成功，或者myTableView 隐藏时 ，那么 _shadeView 也需要隐藏
    //            return @(YES);
    //        }
    //        return @(NO);
    //    }] subscribeNext:^(NSNumber *hidden) {
    //        _shadeView.hidden = hidden.boolValue;
    //    }];
    
    
    
    //    RACSignal *inputSignal =  [self.hospitalField rac_signalForControlEvents:UIControlEventEditingDidBegin | UIControlEventEditingDidEnd];
    //    [inputSignal subscribeNext:^(UITextField *textfiled) {
    //        @strongify(self)
    //        if ([textfiled.text isEqualToString:@""]) {
    //            self.hospitalField.text = @"请输入医院名称";
    //        }else if ([textfiled.text isEqualToString:@"请输入医院名称"]){
    //            self.hospitalField.text =    @"";
    //        }
    //    }];
    
    //RAC 监听模糊搜索的内容
    [[self.hospitalField rac_signalForControlEvents:UIControlEventEditingChanged | UIControlEventEditingDidEndOnExit] subscribeNext:^(UITextField *textfiled) {
        if (textfiled.text.length > 0) {
            self.viewModel.keyWord = self.hospitalField.text;
            if (_requestFlag) {
                _requestFlag = NO;
                [self requestData];
            }
            
            _blueLine.backgroundColor = [UIColor tc5Color];
            //_tableView.hidden = NO;
        }else {
            //_tableView.hidden = YES;
            [_tableView mas_updateConstraints:^(MASConstraintMaker *make) {
                make.height.mas_equalTo(0);
            }];
            _viewModel.dataArray = nil;
            _blueLine.backgroundColor = [UIColor clearColor];
        }
    }];
    //监听return按钮
    [[self.hospitalField rac_signalForControlEvents:UIControlEventEditingDidEndOnExit] subscribeNext:^(UITextField *textfiled) {
        [textfiled resignFirstResponder];
    }];
    
    //监听搜索历史变化
}

//- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
//
//    NSString *str = [NSString stringWithFormat: @"%@%@",textField.text,string];
//    if ([str isEqualToString: _hospitalName]) {
//        _extractButton.enabled = YES;
//    } else {
//        _extractButton.enabled = NO;
//    }
//
//    return YES;
//}

- (void)hospitalChange:(id)sender {
    if ([_hospitalField.text isEqualToString:_hospitalName]) {
        _extractButton.enabled = YES;
    } else {
        _extractButton.enabled = NO;
    }
}

#pragma mark -请求数据
- (void)requestData {
    //    [LoadingView showLoadingInView:self.view];
    [[RACSignal createSignal:^RACDisposable *(id<RACSubscriber> subscriber) {
        [self.viewModel getHostptialNameSuccess:^{
            [subscriber sendNext:@(YES)];
            [subscriber sendCompleted];
        } failed:^{
            [subscriber sendNext:@(NO)];
            [subscriber sendCompleted];
        }];
        return nil;
    }] subscribeNext:^(NSNumber *value) {
        if ([value boolValue]) {
            [_tableView reloadData];
            _requestFlag = YES; //返回请求成功
        }else {
            _requestFlag = NO; //返回请求失败，锁住，一直到上次请求有结果
        }
    }];
}


#pragma mark tableView

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _viewModel.dataArray.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    TakenReportTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"TakenReportTableViewCell"];
    [cell setCellModel:(SCHospitalModel *)[self.viewModel.dataArray objectAtIndex:indexPath.row] withKeyWord:self.hospitalField.text];
    cell.contentView.tappedBlock=^(UIGestureRecognizer*ges)
    {
        
        [self tableView:tableView didSelectRowAtIndexPath:indexPath];
        
    };
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    SCHospitalModel *model = [self.viewModel.dataArray objectAtIndex:indexPath.row];
    _hospitalCode = model.hospitalCode;
    _hospitalName = model.hospitalName;
    _hospitalField.text = model.hospitalName;
    _extractButton.enabled = YES;
    //_tableView.hidden = YES;
    [_tableView mas_updateConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(0);
    }];
    _viewModel.dataArray = nil;
    _blueLine.backgroundColor = [UIColor clearColor];
}

#pragma mark textField Delegate
- (void)textFieldDidBeginEditing:(UITextField *)textField{
    [self setSubviewFrame:YES];
}

- (void)textFieldDidEndEditing:(UITextField *)textField{
    [self setSubviewFrame:NO];
}

#pragma mark setui 是否在输入，btn位置不一样
- (void)setSubviewFrame:(BOOL)isEditing{
    self.tableView.hidden = !isEditing;
    
    WS(ws)
    if (isEditing) {
        [self.extractButton mas_remakeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(_tableView.mas_bottom).offset(105);
            make.left.equalTo(ws.view).offset(15);
            make.right.equalTo(ws.view).offset(-15);
            make.height.mas_equalTo(44);
        }];
    } else {
        [self.extractButton mas_remakeConstraints:^(MASConstraintMaker *make) {
            make.top.equalTo(_bgView.mas_bottom).offset(105);
            make.left.equalTo(ws.view).offset(15);
            make.right.equalTo(ws.view).offset(-15);
            make.height.mas_equalTo(44);
        }];
        
        [_tableView mas_updateConstraints:^(MASConstraintMaker *make) {
            make.height.mas_equalTo(0);
        }];
    }
    
}

- (void)pushToRegistration {
    if (_hospitalCode && [_hospitalField.text isEqualToString:_hospitalName]) {
        SCPrescriptionPayViewController *vc = [SCPrescriptionPayViewController new];
        vc.viewModel.hospitalCode = _hospitalCode;
        
        SCHospitalModel * temp = [[SCHospitalModel alloc] init];
        temp.hospitalName = _hospitalName;
        temp.hospitalCode = _hospitalCode;
        [_viewModel saveSearchHistoryModel:temp];
        [self.navigationController pushViewController:vc animated:YES];
    } else {
        [MBProgressHUDHelper showHudWithText:@"医院输入错误"];
    }
}



@end
