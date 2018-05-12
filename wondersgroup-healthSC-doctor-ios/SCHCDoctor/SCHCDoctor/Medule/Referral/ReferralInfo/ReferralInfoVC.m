//
//  ReferralInfoVC.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/8.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "ReferralInfoVC.h"
#import "ReferralInfoViewModel.h"

#import "PatientInfoBaseNormalCell.h"
#import "PatientInfoTextFieldCell.h"
#import "PatientInfoTextViewCell.h"
#import "RJDatePicker.h"
#import "ReferralResultVC.h"

#import "WDHospitalListRootViewController.h"
#import "SCDepartmentViewController.h"
#import "ReferralInfoModel.h"
#import "PatientInfoModel.h"

static NSString * const ReferralInfoNormalCellID = @"ReferralInfoNormalCellID";
static NSString * const ReferralInfoTextFieldCellID = @"ReferralInfoTextFieldCellID";
static NSString * const ReferralInfoTextViewCellID = @"ReferralInfoTextViewCellID";

@interface ReferralInfoVC () <UITableViewDelegate, UITableViewDataSource>

@property (strong, nonatomic) UITableView * tableView;
@property (strong, nonatomic) UIButton * registrationButton;

@property (strong, nonatomic) ReferralInfoViewModel * viewModel;
@end

@implementation ReferralInfoVC

- (instancetype)initWithType:(ReferralType)type  model:(PatientInfoModel *)model{
    self = [super init];
    if (self) {
        _viewModel = [[ReferralInfoViewModel alloc] initWithType:type model:model];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setTitle:@"转诊信息"];
    [self initInterface];
    [self buildConstraint];
    [self bindRAC];
}
#pragma mark - user-define initialization
- (void)initData {
    
}

- (void)initInterface {
    [self getTableView];
    [self getRegistrationButton];
    
    [self checkDataCorrectWithHud:NO];
}

- (void)buildConstraint {
    [_registrationButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(self.view).offset(15);
        make.right.equalTo(self.view).offset(-15);
        make.bottom.equalTo(self.view).offset(-10);
        make.height.mas_equalTo(44);
        make.top.equalTo(_tableView.mas_bottom).offset(10);
    }];
    
    [_tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.right.top.equalTo(self.view);
    }];
}

- (void)bindRAC {
    MJWeakSelf
    [[_registrationButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
        if([weakSelf checkDataCorrectWithHud:YES]) {
            SCDepartmentViewController * vc = [[SCDepartmentViewController alloc] init];
            [weakSelf.navigationController pushViewController:vc animated:YES];
        }
    }];
}

- (void)doRequest {
    
}

#pragma mark - event

#pragma mark - function
- (BOOL)checkDataCorrectWithHud:(BOOL)showHud {

    NSString * errorString = [_viewModel checkModelImportantFullWithErrorString];
    BOOL successBtn = [errorString isEqualToString:@""];
    
    [_registrationButton setBackgroundColor:successBtn ? RGBA_COLOR(46, 122, 240, 1) : RGBA_COLOR(46, 122, 240, 0.5)];

    if (!successBtn) {
        if (showHud) {
            [MBProgressHUDHelper showHudWithText:errorString];
        }
        return NO;
    }
    return YES;
}

- (void)didEditWithIndexPath:(NSIndexPath *)indexPath text:(NSString *)text{
    ReferralInfoModel * model = _viewModel.modelArray[indexPath.row];
    model.detail = text;
    [_viewModel configModel];
    [self checkDataCorrectWithHud:NO];
}
#pragma mark - delegate
#pragma mark - tableView delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == 0) {
        //医院选择
        WDHospitalListRootViewController * vc = [[WDHospitalListRootViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
        return;
    }
    
    ReferralInfoModel * model = _viewModel.modelArray[indexPath.row];
    MJWeakSelf
    if (_viewModel.type == ReferralTypeOutpatient) {
        
    } else {
        if ([model.title isEqualToString:@"申请日期"]) {
            RJDatePicker * picker = [[RJDatePicker alloc] init];
            [picker.picker setDatePickerMode:UIDatePickerModeDate];
            [[[[UIApplication sharedApplication] delegate] window] addSubview:picker];
            [picker setSelectedBlock:^(NSDate *date) {
                NSDateFormatter * formatter = [[NSDateFormatter alloc] init];
                formatter.dateFormat = @"yyyy-MM-dd";
                NSString * dateString = [formatter stringFromDate:date];
                ReferralInfoModel * model = weakSelf.viewModel.modelArray[indexPath.row];
                model.detail = dateString;
                [weakSelf.viewModel configModel];
                [weakSelf checkDataCorrectWithHud:NO];
                [weakSelf.tableView reloadRowsAtIndexPaths:@[[NSIndexPath indexPathForRow:2 inSection:0]] withRowAnimation:UITableViewRowAnimationNone];
            }];
            [picker build];
        }
    }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _viewModel.modelArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    ReferralInfoModel * model = _viewModel.modelArray[indexPath.row];
    PatientInfoBaseCell * cell = nil;
    if (model.type == PatientInfoBaseCellTypeNormal) {
        cell = [tableView dequeueReusableCellWithIdentifier:ReferralInfoNormalCellID forIndexPath:indexPath];
    } else {
        if (model.type == PatientInfoBaseCellTypeTextFiled) {
            cell = [tableView dequeueReusableCellWithIdentifier:ReferralInfoTextFieldCellID forIndexPath:indexPath];
        }
        else if (model.type == PatientInfoBaseCellTypeTextView) {
            cell = [tableView dequeueReusableCellWithIdentifier:ReferralInfoTextViewCellID forIndexPath:indexPath];
        }
    }
    cell.textField.userInteractionEnabled = (model.type == PatientInfoBaseCellTypeTextFiled);
    [cell showTopBlank:model.topBlank];
    [cell setImportant:model.isImportant];
    
    MJWeakSelf
    [cell setEditBlock:^(BOOL isEdit,NSString * string, UITableViewCell *cell) {
        if (cell) {
            NSIndexPath * indexPath = [weakSelf.tableView indexPathForCell:cell];
            if (indexPath) {
                [weakSelf didEditWithIndexPath:indexPath
                                          text:string];
            }
        }
    }];
    [cell.titleLabel setText:model.title];
    [cell.textField setPlaceholder:model.placeHolder];
    
    //发起医院名称直接取用户数据
    if (_viewModel.type == ReferralTypeOutpatient && indexPath.row == 1) {
        [cell.arrowImageView setHidden:YES];
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    
    ReferralInfoModel * model = _viewModel.modelArray[indexPath.row];
    PatientInfoBaseCell * myCell = (PatientInfoBaseCell *)cell;
    [myCell.textField setText:model.detail];
}

- (CGFloat)tableView:(UITableView *)tableView estimatedHeightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 45;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return UITableViewAutomaticDimension;
}
#pragma mark - notification

#pragma mark - setter

#pragma mark - getter
- (UITableView *)getTableView {
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        [_tableView setBackgroundColor:RGB_COLOR(246, 246, 246)];
        [_tableView setDelegate:self];
        [_tableView setDataSource:self];
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView registerClass:[PatientInfoBaseNormalCell class] forCellReuseIdentifier:ReferralInfoNormalCellID];
        [_tableView registerClass:[PatientInfoTextFieldCell class] forCellReuseIdentifier:ReferralInfoTextFieldCellID];
        [_tableView registerClass:[PatientInfoTextViewCell class] forCellReuseIdentifier:ReferralInfoTextViewCellID];
//        _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(doRequest)];
        [self.view addSubview:_tableView];
    }
    return _tableView;
}

- (UIButton *)getRegistrationButton {
    if (!_registrationButton) {
        _registrationButton = [[UIButton alloc] init];
        [_registrationButton setBackgroundColor:RGB_COLOR(46, 122, 240)];
        [_registrationButton setTitle:@"预约挂号" forState:UIControlStateNormal];
        [_registrationButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [_registrationButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];
        [self.view addSubview:_registrationButton];
    }
    return _registrationButton;

}

@end
