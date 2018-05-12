//
//  WDPersonalIformationViewController.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "WDPersonalIformationViewController.h"
#import "WDPersonalIformationTableViewCell.h"
#import "SCCertificationFixedViewController.h"
#import "SCActionSheet.h"
#import "UserService.h"
#import "SCCertificationResultVC.h"
#import "WDPersonalIformationViewModel.h"
#import "WDChangeNameViewController.h"
#import "PickerSelectedView.h"

static NSString *const PERSONAL_INFORMATION_TABLEVIEWCELL = @"PERSONAL_INFORMATION_TABLEVIEWCELL";

@interface WDPersonalIformationViewController () <UITableViewDataSource, UITableViewDelegate, UIActionSheetDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate> {
        UIImagePickerController *imagePicker;
}

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UIView *searchHeadView;
@property (nonatomic, strong) WDPersonalIformationViewModel *viewModel;

@end

@implementation WDPersonalIformationViewController

- (WDPersonalIformationViewModel *)viewModel {
    if (!_viewModel) {
        _viewModel = [WDPersonalIformationViewModel new];
    }
    return _viewModel;
}

- (UIView *)searchHeadView {
    if (!_searchHeadView) {
        _searchHeadView = [UIView new];
        _searchHeadView.backgroundColor = [UIColor bc1Color];
    }
    return _searchHeadView;
}

- (UITableView *)tableView {
    if (!_tableView) {
        _tableView = [UITableView new];
        _tableView.dataSource = self;
        _tableView.delegate = self;
        _tableView.backgroundColor = [UIColor clearColor];
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView registerClass:[WDPersonalIformationTableViewCell class] forCellReuseIdentifier:PERSONAL_INFORMATION_TABLEVIEWCELL];
    }
    return _tableView;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupView];
}

- (void)viewWillAppear:(BOOL)animated {
//    [super viewWillAppear:animated];
//    if ([UserManager manager].isLogin) {
//        [LoadingView showLoadingInView:self.view];
//        [[UserService service] requestTureNameType:^(SCTrueNameModel *tureNameModel) {
//            [LoadingView hideLoadinForView:self.view];
//            [_tableView reloadData];
//        } failure:^(NSError *error) {
//            [LoadingView hideLoadinForView:self.view];
//        }];
//    }
    
    if ([UserManager manager].isLogin) {
        [[UserService service] refreshLastUserInfoComplete:^(UserInfoModel *model) {
//            [LoadingView hideLoadinForView:self.view];
            [_tableView reloadData];
        }];
    }
}

- (void)setupView {
    WS(ws)
    
    self.title = @"个人信息";
    
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.edges.equalTo(ws.view);
    }];
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 10;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    return [UIView new];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 0) {
        return 5;
    }
    
    if (section == 1) {
        return 1;
    }
    
    return 0;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    WDPersonalIformationTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:PERSONAL_INFORMATION_TABLEVIEWCELL forIndexPath:indexPath];
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            cell.cellType = WDInformationCellHead;
            cell.title = @"头像";
            cell.head = [UserManager manager].avatar;
        }
        
        if (indexPath.row == 1) {
            cell.cellType = WDInformationCellDefault;
            cell.title = @"姓名";
            if ([UserManager manager].verificationStatus == 3) {
                cell.content = [UserManager manager].name;
            } else {
                cell.content = [UserManager manager].nickname;
            }
        }
        
        if (indexPath.row == 2) {
            cell.cellType = WDInformationCellPhone;
            cell.title = @"账号";
            NSString *phone = [UserManager manager].mobile;
            cell.phone = [NSString stringWithFormat:@"%@****%@", [phone substringToIndex:3], [phone substringFromIndex:7]];
        }
        
        if (indexPath.row == 3) {
            cell.cellType = WDInformationCellDefault;
            cell.title = @"性别";
            if([UserManager manager].gender == 2){
                cell.content = @"女";
            }else{
                 cell.content = @"男";
            }
        }
        
        if (indexPath.row == 4) {
            cell.cellType = WDInformationCellDefault;
            if(![UserManager manager].age){
                cell.content = @"28岁";
            }else{
                  cell.content = [NSString stringWithFormat:@"%@岁", [UserManager manager].age];
            }
          
            cell.title = @"年龄";
            cell.isLast = YES;
        }
    }
    
    if (indexPath.section == 1) {
        cell.cellType = WDInformationCellStyle;
        cell.title = @"实名认证";
        cell.style = [UserManager manager].verificationStatus;
        cell.isLast = YES;
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            [self pickImage];
        }
        
        else if (indexPath.row == 1) {
            if ([UserManager manager].verificationStatus == 0 || [UserManager manager].verificationStatus == 1) {
                WDChangeNameViewController *vc = [WDChangeNameViewController new];
                vc.content = [UserManager manager].name? [UserManager manager].nickname: [UserManager manager].name;
                [self.navigationController pushViewController:vc animated:YES];
            }
        }
        
        else if (indexPath.row == 3) {
            if ([UserManager manager].verificationStatus == 0 || [UserManager manager].verificationStatus == 1) {
                [self selectSex];
            }
        }
        
        else if (indexPath.row == 4) {
            if ([UserManager manager].verificationStatus == 0 || [UserManager manager].verificationStatus == 1) {
                [self selectBirthday];
            }
        }
        
    }
    
    else if (indexPath.section == 1) {
        
    
        
        if ([UserManager manager].isLogin) {
            if (![Global global].networkReachable) {
                [MBProgressHUDHelper showHudWithText:@"网络连接异常，请稍后再试"];
                return;
            }
            [LoadingView showLoadingInView:self.view];
            [[UserService service] requestTureNameType:^(SCTrueNameModel *tureNameModel) {
                [LoadingView hideLoadinForView:self.view];
                if ([tureNameModel.status isEqualToString:@"0"]) {
                    
//                    // 认证失败
//                    SCCertificationResultVC *certificationFixed = [[SCCertificationResultVC alloc] init];
//                    certificationFixed.type = CertificationFail;
//                    certificationFixed.model = tureNameModel;
//                    certificationFixed.hidesBottomBarWhenPushed = YES;
//                    [self.navigationController pushViewController:certificationFixed animated:NO];
//                    certificationFixed.navigationController.navigationBar.hidden = NO;
                    
                    SCCertificationFixedViewController *certificationFixed = [[SCCertificationFixedViewController alloc] init];
                    certificationFixed.hidesBottomBarWhenPushed = YES;
                    [self.navigationController pushViewController:certificationFixed animated:YES];
                    certificationFixed.navigationController.navigationBar.hidden = NO;
                }
                if ([tureNameModel.status isEqualToString:@"1"]) {
                    // 认证失败
                    SCCertificationResultVC *certificationFixed = [[SCCertificationResultVC alloc] init];
                    certificationFixed.type = CertificationFail;
                    certificationFixed.model = tureNameModel;
                    certificationFixed.hidesBottomBarWhenPushed = YES;
                    [self.navigationController pushViewController:certificationFixed animated:NO];
                    certificationFixed.navigationController.navigationBar.hidden = NO;
                }
                if ([tureNameModel.status isEqualToString:@"2"]) {
                    SCCertificationResultVC *certificationFixed = [[SCCertificationResultVC alloc] init];
                    certificationFixed.type = CertificationIng;
                    certificationFixed.model = tureNameModel;
                    certificationFixed.hidesBottomBarWhenPushed = YES;
                    [self.navigationController pushViewController:certificationFixed animated:YES];
                    certificationFixed.navigationController.navigationBar.hidden = NO;
                }
                if ([tureNameModel.status isEqualToString:@"3"]) {
                    SCCertificationResultVC *certificationFixed = [[SCCertificationResultVC alloc] init];
                    certificationFixed.model = tureNameModel;
                    certificationFixed.type = CertificationPass;
                    certificationFixed.hidesBottomBarWhenPushed = YES;
                    [self.navigationController pushViewController:certificationFixed animated:YES];
                    certificationFixed.navigationController.navigationBar.hidden = NO;
                }
            } failure:^(NSError *error) {
                [LoadingView hideLoadinForView:self.view];
            }];
        }
    }
}

#pragma mark - pick image
- (void)pickImage {
    
    SCActionSheet *actionSheet= [[SCActionSheet alloc] initWithFrame:CGRectMake(0,0, SCREEN_WIDTH, SCREENHEIGHT+64) titleArr:@[@"取消",@"新拍摄一张",@"从相册选择"]];
    actionSheet.tag = 1516;
    __weak typeof(actionSheet) weakA = actionSheet;
    actionSheet.Click = ^(NSInteger clickIndex) {
        switch (clickIndex) {
            case 0:
                NSLog(@"取消");
                break;
            case 1:
                [self showImagePicker: UIImagePickerControllerSourceTypeCamera];
                break;
            case 2:
                [self showImagePicker: UIImagePickerControllerSourceTypePhotoLibrary];
                break;
            default:
                break;
        }
        [weakA hiddenSheet];
    };
    [self.view.window addSubview:actionSheet];
    
}

#pragma mark - UIImagePickerControllerDelegate
- (void)showImagePicker:(UIImagePickerControllerSourceType)sourceType
{
    if(sourceType == UIImagePickerControllerSourceTypeCamera) {
        
        if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
            float sysVersion = [[[UIDevice currentDevice] systemVersion] floatValue];
            if(sysVersion >= 7.0) {
                AVAuthorizationStatus authStatus = [AVCaptureDevice authorizationStatusForMediaType:AVMediaTypeVideo];
                if(authStatus == AVAuthorizationStatusDenied){
                    //应该是这个，如果不允许的话
                    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"请在设备的\"设置-隐私-相机\"中允许访问相机。"delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil];
                    [alert show];
                    return;
                }
            }
        } else {
            //系统相机不可用
            return;
        }
    }
    
    if (!imagePicker) {
        imagePicker = [[UIImagePickerController alloc] init];
        imagePicker.delegate = self;
    }
    imagePicker.allowsEditing = YES;
    imagePicker.sourceType = sourceType;
    
    [self presentViewController: imagePicker animated:YES completion:^{
        [[UIApplication sharedApplication] setStatusBarStyle: UIStatusBarStyleDefault];
    }];
}

#pragma mark - UIImagePickerControllerDelegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    if ([info objectForKey:UIImagePickerControllerEditedImage]) {
        UIImage *image = [info objectForKey:UIImagePickerControllerEditedImage];
//        if (image.size.height > self.uploadImageView.size.height || image.size.width > self.uploadImageView.self.width) {
//            self.uploadImageView.contentMode = UIViewContentModeScaleToFill;
//        }
//        else
//        {
//            self.uploadImageView.contentMode = UIViewContentModeCenter;
//        }
//        self.uploadImageView.image = image;
//        self.viewModel.photo = image;
//        [self checkUpload];
        NSData *imageData = UIImagePNGRepresentation(image);
        NSString *fileName = [NSString stringWithFormat:@"%@.png", [[NSUUID UUID] UUIDString]];
        UploadModel *uploadModel = [[UploadModel alloc] init:FileType_Image fileData:imageData fileName:fileName];
        [[UserService service] userEditIcon:uploadModel complete:^{
            [_tableView reloadData];
        }];
        
    }
  //  [[UIApplication sharedApplication] setStatusBarStyle: UIStatusBarStyleLightContent];
    [self dismissViewControllerAnimated:YES completion:^{
        imagePicker = nil;
    }];
}

#pragma mark - timeSelect
- (void)selectBirthday {
    NSDate *nowDate = [NSDate date];
    NSDate *defaultDate = [nowDate mt_dateYearsBefore: 28];
    if ([UserManager manager].birthday) {
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"yyyy-MM-dd"];
        defaultDate = [dateFormatter dateFromString:[UserManager manager].birthday];
    }
    NSDate *minDate = [nowDate mt_dateYearsBefore: 100];
    

    PickerSelectedView *datePicker = [[PickerSelectedView alloc] init];
    [datePicker showDatePickerWithModel: UIDatePickerModeDate
                               withDate: [defaultDate mt_stringFromDateWithFormat:@"yyyy-MM-dd" localized: NO]
                              withTitle: @""
                            withMaxDate: nowDate
                            withMinDate: minDate];
    
    @weakify(datePicker);
    //保存日期操作
    datePicker.saveBlock = ^(NSDate *birthday) {
        @strongify(datePicker);
        //关闭选择界面
        [datePicker dissmiss];
        NSString *birthdayStr = [birthday mt_stringFromDateWithFormat:@"yyyy-MM-dd" localized: NO];
        NSDictionary *dic = @{
                              @"uid" : [UserManager manager].uid,
                              @"birthday" : birthdayStr
                              };
        
        [[UserService service] userEditInfo:dic complete:^{
            [_tableView reloadData];
        }];
    };

}

#pragma mark - 性别
- (void)selectSex {
    PickerSelectedView *sexPicker = [[PickerSelectedView alloc] init];
    [sexPicker showGenderPickerWithModel:@"男" withTitle:@""];
    
    @weakify(sexPicker);
    //保存日期操作
    sexPicker.saveBlock = ^(NSString *gender) {
        @strongify(sexPicker);
        //关闭选择界面
        [sexPicker dissmiss];
        
        NSDictionary *dic = @{
                              @"uid" : [UserManager manager].uid,
                              @"gender" :  [gender isEqualToString:@"男"]? @"1" : @"2"
                              };
        
        [[UserService service] userEditInfo:dic complete:^{
            [_tableView reloadData];
        }];
    };
}



@end
