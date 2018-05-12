//
//  PatientInfoVC.m
//  SCHCDoctor
//
//  Created by Po on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "PatientInfoVC.h"
#import "PatientInfoBaseCell.h"
#import "PatientDetailCell.h"
#import "PatientSelectCell.h"
#import "ReferralActionSheetView.h"
#import "RJDataPicker.h"

#import "PatientInfoViewModel.h"

#import "ReferralInfoVC.h"
#import "MBProgressHUDHelper.h"

#import "PatientInfoBaseNormalCell.h"
#import "PatientInfoTextFieldCell.h"
#import "PatientInfoTextViewCell.h"

static NSString * const PatientDetailCellID = @"PatientDetailCellID";
static NSString * const PatientSelectCellID = @"PatientSelectCellID";
static NSString * const PatientInfoBaseNormalCellID = @"PatientInfoBaseNormalCellID";
static NSString * const PatientInfoTextFieldCellID = @"PatientInfoTextFieldCellID";
static NSString * const PatientInfoTextViewCellID = @"PatientInfoTextViewCellID";

@interface PatientInfoVC () <UITableViewDelegate, UITableViewDataSource>
@property (strong, nonatomic) UITableView * tableView;
@property (strong, nonatomic) UIButton * nextButton;

@property (strong, nonatomic) PatientInfoViewModel * viewModel;
@property (assign, nonatomic) BOOL isEditAddress;
@property (weak, nonatomic)   UITextView * editTextView;                //当前编辑对象
@end

@implementation PatientInfoVC

- (instancetype)initWithType:(ReferralType)type model:(PatientInfoModel *)model{
    self = [super init];
    if (self) {
        _isEditAddress = NO;
        _viewModel = [[PatientInfoViewModel alloc] initWithType:type model:model];
        
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self initInterface];
    [self checkDataCorrectWithShowHud:NO];
}

#pragma mark - user-define initialization

- (void)initInterface {
    [self.view setBackgroundColor:RGB_COLOR(246, 246, 246)];
    [self setTitle:_viewModel.data.name];
    [self getTableView];
    [self configNextButton];
    [self buildConstraint];
    [self bindRAC];
}

- (void)buildConstraint {
    [_nextButton mas_makeConstraints:^(MASConstraintMaker *make) {
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
    [[_nextButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(__kindof UIControl * _Nullable x) {
        if([weakSelf checkDataCorrectWithShowHud:YES]) {
            ReferralInfoVC * vc = [[ReferralInfoVC alloc] initWithType:weakSelf.viewModel.type model:weakSelf.viewModel.data];
            [weakSelf.navigationController pushViewController:vc animated:YES];
        }
    }];
    
    [[[NSNotificationCenter defaultCenter] rac_addObserverForName:@"PatientDetailCellEditTextView" object:nil] subscribeNext:^(NSNotification * _Nullable x) {
        UITextView * textView = x.object;
        weakSelf.editTextView = textView;
    }];
}

- (void)doRequest {
    
}

#pragma mark - event

#pragma mark - function
- (void)showSexSelectedPanel {
    ReferralActionSheetView * sheetView = [[ReferralActionSheetView alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 150) titles:@[@"男",@"女"]];
    MJWeakSelf
    [sheetView setClickBlock:^(NSInteger index, BOOL isCancel) {
        if (isCancel) {
            return;
        }
        PatientInfoCellTypeModel * model = weakSelf.viewModel.modelsArray[0];
        model.detail = index == 0 ? @"男" : @"女";
        [weakSelf.tableView reloadRowsAtIndexPaths:@[[NSIndexPath indexPathForRow:0 inSection:0]] withRowAnimation:UITableViewRowAnimationNone];
    }];
    [[[UIApplication sharedApplication].delegate window] addSubview:sheetView];
    [sheetView showAnim];
}

- (void)showCardTypeSelectedPanel {
    ReferralActionSheetView * sheetView = [[ReferralActionSheetView alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 150) titles:@[@"社保卡",@"医保卡"]];
    MJWeakSelf
    [sheetView setClickBlock:^(NSInteger index, BOOL isCancel) {
        if (isCancel) {
            return;
        }
        PatientInfoCellTypeModel * model = weakSelf.viewModel.modelsArray[2];
        model.detail = index == 0 ? @"社保卡" : @"医保卡";
        [weakSelf.tableView reloadRowsAtIndexPaths:@[[NSIndexPath indexPathForRow:2 inSection:0]] withRowAnimation:UITableViewRowAnimationNone];
        [weakSelf checkDataCorrectWithShowHud:NO];
    }];
    [[[UIApplication sharedApplication].delegate window] addSubview:sheetView];
    [sheetView showAnim];
}

- (void)showAgeSelectedPanel {
    NSMutableArray * temp = [NSMutableArray array];
    for (NSInteger i = 0; i <= 100; i ++) {
        [temp addObject:[NSString stringWithFormat:@"%ld", i]];
    }
    MJWeakSelf
    RJDataPicker * picker = [[RJDataPicker alloc] initWithData:temp];
    [picker setSelectedBlock:^(NSString *data) {
        PatientInfoCellTypeModel * model = weakSelf.viewModel.modelsArray[1];
        model.detail = data;
        [weakSelf.tableView reloadRowsAtIndexPaths:@[[NSIndexPath indexPathForRow:1 inSection:0]] withRowAnimation:UITableViewRowAnimationNone];
    }];
    [[[UIApplication sharedApplication].delegate window] addSubview:picker];
    [picker build];
}

- (BOOL)checkDataCorrectWithShowHud:(BOOL)isShow {
    NSString * errorMsg = [_viewModel checkModelImportantFullWithErrorString];
    BOOL successBtn = [errorMsg isEqualToString:@""];
    
    [_nextButton setBackgroundColor:successBtn ? RGBA_COLOR(46, 122, 240, 1) : RGBA_COLOR(46, 122, 240, 0.5)];
    
    if (!successBtn) {
        if (isShow) {
            [MBProgressHUDHelper showHudWithText:errorMsg];
        }
        return NO;
    }
    return YES;
}

#pragma mark - delegate
#pragma mark - tableView delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (_viewModel.type == ReferralTypeOutpatient) {
        if ((indexPath.row >= 4)) {
            return;
        }
        if (indexPath.row == 2) {
            [self showCardTypeSelectedPanel];
        }
    } else {
        if (indexPath.row >= 2) {
            return;
        }
    }
    
    if (indexPath.row == 0) {
        [self showSexSelectedPanel];
    } else if (indexPath.row == 1) {
        [self showAgeSelectedPanel];
    }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _viewModel.modelsArray.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    PatientInfoCellTypeModel * model = _viewModel.modelsArray[indexPath.row];
    if (model.type == PatientInfoCellSelected) {
        PatientSelectCell * cell = [tableView dequeueReusableCellWithIdentifier:@"PatientSelectCellID" forIndexPath:indexPath];
        [cell setTitles:_viewModel.urgencyArray];
        [cell setSelectedBlock:^(NSInteger index, NSString *title) {
            model.detail = title;
        }];
        return cell;
    } else if (model.type == PatientInfoCellTextPanel){
        PatientDetailCell * cell = [tableView dequeueReusableCellWithIdentifier:@"PatientDetailCellID" forIndexPath:indexPath];
        [cell.titleLabel setText:model.title];
        [cell.textView setText:model.detail];
        cell.textView.placeholder = model.placeHolder;
        [cell setEditBlock:^(BOOL isEdit, NSString *string, UITableViewCell *cell) {
            model.detail = string;
        }];
        return cell;
    } else {
        return [self getInfoBaseCellWitthIndexPath:indexPath tableView:tableView];
    }
    return nil;
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {

}

- (CGFloat)tableView:(UITableView *)tableView estimatedHeightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 165;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return UITableViewAutomaticDimension;
}
#pragma mark - notification
- (void)keyboardDidShow:(NSNotification *)notification {
    if (!_editTextView) {
        return;
    }
    CGFloat height = [[notification.userInfo objectForKey:UIKeyboardFrameEndUserInfoKey] CGRectValue].size.height;
    
    UIWindow * window = [[UIApplication sharedApplication].delegate window];
    CGRect rect = [_editTextView convertRect:_editTextView.frame toView:window];
    CGFloat bottomY = rect.origin.y + rect.size.height;
    
    CGFloat value = bottomY - (SCREEN_HEIGHT - height);
    if (value > 0) {
        CGFloat y = _tableView.contentOffset.y + value;
        [_tableView setContentOffset:CGPointMake(0, y) animated:YES];
    }
}

- (void)keyboardWillHide:(NSNotification *)notification {
    _editTextView = nil;
    CGFloat bottomY = _tableView.contentSize.height - _tableView.frame.size.height;
    if (_tableView.contentOffset.y > bottomY) {
        [_tableView setContentOffset:CGPointMake(0, bottomY)];
    }
}
#pragma mark - setter

#pragma mark - getter
- (PatientInfoBaseCell *)getInfoBaseCellWitthIndexPath:(NSIndexPath *)indexPath
                                            tableView:(UITableView *)tableView {
    
    PatientInfoCellTypeModel * model = _viewModel.modelsArray[indexPath.row];
    
    PatientInfoBaseCell * cell = nil;
    MJWeakSelf
    if (model.type == PatientInfoCellNormal) {
        cell = [tableView dequeueReusableCellWithIdentifier:PatientInfoBaseNormalCellID forIndexPath:indexPath];
        [cell.textField setText:model.detail];
        [cell setEditBlock:^(BOOL isEdit, NSString *string, UITableViewCell *cell) {
            model.detail = string;
            [weakSelf checkDataCorrectWithShowHud:NO];
        }];
    } else {
        if (model.type == PatientInfoCellTextField) {
            cell = [tableView dequeueReusableCellWithIdentifier:PatientInfoBaseNormalCellID forIndexPath:indexPath];
            [cell.textField setPlaceholder:model.placeHolder];
            [cell.textField setText:model.detail];
            [cell setEditBlock:^(BOOL isEdit, NSString *string, UITableViewCell *cell) {
                model.detail = string;
                [weakSelf checkDataCorrectWithShowHud:NO];
            }];

            if (_viewModel.type == ReferralTypeOutpatient) {
                if (indexPath.row == 4) {
                    cell.textField.keyboardType = UIKeyboardTypePhonePad;
                } else if (indexPath.row == 3) {
                    cell.textField.keyboardType = UIKeyboardTypeASCIICapable;
                }
            } else {
                if (indexPath.row == 2) {
                    cell.textField.keyboardType = UIKeyboardTypePhonePad;
                }
            }

        }
        else if (model.type == PatientInfoCellTextView) {
            cell = [tableView dequeueReusableCellWithIdentifier:PatientInfoTextViewCellID forIndexPath:indexPath];
            [cell reSetTextView];
            cell.textView.placeholder = model.placeHolder;
            [cell.textView setText:model.detail];

            [cell setEditBlock:^(BOOL isEdit, NSString *string,UITableViewCell * cell) {
                weakSelf.isEditAddress = isEdit;
                model.detail = string;
                [weakSelf checkDataCorrectWithShowHud:NO];
                NSIndexPath * indexPath = [tableView indexPathForCell:cell];
                [tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
            }];
        }
    }
    [cell.titleLabel setText:model.title];
    [cell setImportant:model.isImportant];
    [cell showTopBlank:indexPath.row == 0];
    cell.textField.userInteractionEnabled = (model.type == PatientInfoCellTextField);
    return cell;
}

- (UITableView *)getTableView {
    if (!_tableView) {
        _tableView = [[UITableView alloc] init];
        [_tableView setBackgroundColor:RGB_COLOR(246, 246, 246)];
        [_tableView setDelegate:self];
        [_tableView setDataSource:self];
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        
        [_tableView registerClass:[PatientDetailCell class] forCellReuseIdentifier:PatientDetailCellID];
        [_tableView registerClass:[PatientSelectCell class] forCellReuseIdentifier:PatientSelectCellID];
        [_tableView registerClass:[PatientInfoBaseNormalCell class] forCellReuseIdentifier:PatientInfoBaseNormalCellID];
        [_tableView registerClass:[PatientInfoTextFieldCell class] forCellReuseIdentifier:PatientInfoTextFieldCellID];
        [_tableView registerClass:[PatientInfoTextViewCell class] forCellReuseIdentifier:PatientInfoTextViewCellID];
        _tableView.mj_header = [UIUtility headerRefreshTarget:self action:@selector(doRequest)];
        [self.view addSubview:_tableView];
    }
    return _tableView;
}

- (void)configNextButton {
    _nextButton = [[UIButton alloc] init];
    [_nextButton setBackgroundColor:RGB_COLOR(46, 122, 240)];
    [_nextButton setTitle:@"下一步" forState:UIControlStateNormal];
    [_nextButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_nextButton.titleLabel setFont:[UIFont fontWithName:@"PingFangSC-Regular" size:16]];

    [self.view addSubview:_nextButton];
}


@end
