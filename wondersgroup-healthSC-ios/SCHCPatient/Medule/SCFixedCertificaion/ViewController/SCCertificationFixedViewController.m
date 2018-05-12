//
//  SCtestViewController.m
//  HCPatient
//
//  Created by wanda on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "SCCertificationFixedViewController.h"
#import "SCCertificationFixedNameCell.h"
#import "SCCertificationFixedIDcardCell.h"
#import "WDAlertView.h"
#import "SCActionSheet.h"
#import "WDBaseWebViewController.h"
#import "SCCertificationResultVC.h"
@interface SCCertificationFixedViewController ()

@end

@implementation SCCertificationFixedViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initViewModel];
    [self setupview];
}

- (void)initViewModel
{
    _viewModel = [[SCCertificationViewModel alloc] init];
}
- (void)setupview
{
    self.title = @"实名认证";
    self.view.backgroundColor = [UIColor whiteColor];
    self.tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    self.tableView.bounces = NO;
    self.tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:self.tableView];
    [self.tableView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.bottom.left.right.equalTo(self.view);
    }];
    [self setupFootView];
}

- (void)setupFootView
{
    WS(weakSelf)
    UIView *footView = [[UIView alloc] init];
    footView.userInteractionEnabled = YES;
    footView.backgroundColor = [UIColor whiteColor];
    footView.frame = CGRectMake(0,0,SCREEN_WIDTH,364);
    _tableView.tableFooterView = footView;
    
    UIView *topView = [[UIView alloc] init];
    topView.backgroundColor = [UIColor bc2Color];
    [footView addSubview:topView];
    [topView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.left.right.equalTo(footView);
        make.height.mas_equalTo(64);
    }];
    
    _alertLabel = [[UILabel alloc] init];
    _alertLabel.font = [UIFont systemFontOfSize:12.0];
    _alertLabel.textColor = [UIColor stc2Color];
    [footView addSubview:_alertLabel];
    [_alertLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(footView).offset(15);
        make.right.equalTo(footView).offset(-15);
        make.top.equalTo(footView);
        make.height.equalTo(@25);
    }];
    
    UILabel *textLabel = [[UILabel alloc] init];
    textLabel.textColor = [UIColor tc3Color];
    textLabel.font = [UIFont systemFontOfSize:12];
    textLabel.text = @"请务必填写真实信息,您的信息我们将严格保密";
    [topView addSubview:textLabel];
    [textLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.left.equalTo(topView).offset(15);
        make.top.equalTo(_alertLabel.mas_bottom).offset(5);
        make.right.equalTo(topView).offset(-15);
    }];
    
    UIView *bottomView = [[UIView alloc] init];
    bottomView.backgroundColor = [UIColor whiteColor];
    [footView addSubview:bottomView];
    [bottomView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(topView.mas_bottom);
        make.left.bottom.right.equalTo(footView);
    }];
    
    UILabel *titleLbel = [[UILabel alloc] init];
    titleLbel.textColor = [UIColor tc2Color];
    titleLbel.font = [UIFont systemFontOfSize:14];
    titleLbel.text = @"请上传手持身份证照片";
    [bottomView addSubview:titleLbel];
    [titleLbel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(bottomView).offset(15);
        make.left.equalTo(bottomView).offset(15);
        make.right.equalTo(topView).offset(-15);
    }];
    
    UILabel *demoLabel = [[UILabel alloc] init];
    demoLabel.userInteractionEnabled = YES;
    demoLabel.textColor = [UIColor tc5Color];
    demoLabel.textAlignment = NSTextAlignmentRight;
    demoLabel.font = [UIFont systemFontOfSize:14];
    demoLabel.text = @"示例";
    UITapGestureRecognizer *demoLabelTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(demoLabelClicked)];
    [demoLabel addGestureRecognizer:demoLabelTap];
    [bottomView addSubview:demoLabel];
    [demoLabel mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(bottomView).offset(15);
        make.right.equalTo(bottomView).offset(-15);
        make.height.equalTo(@30);
    }];
    
    _uploadImageView = [[UIImageView alloc] init];
    [bottomView addSubview:_uploadImageView];
    _uploadImageView.image = [UIImage imageNamed:@"img添加身份证"];
    _uploadImageView.userInteractionEnabled = YES;
    _uploadImageView.backgroundColor = [UIColor clearColor];
    UITapGestureRecognizer *behindTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(uploadViewClicked)];
    [_uploadImageView addGestureRecognizer:behindTap];
    [_uploadImageView mas_makeConstraints:^(MASConstraintMaker *make) {
        make.width.mas_equalTo(140);
        make.centerX.equalTo(bottomView);
        make.height.equalTo(@130);
        make.top.equalTo(titleLbel.mas_bottom).offset(25);
    }];
    
    UILabel *textLabel2 = [[UILabel alloc] init];
    textLabel2.userInteractionEnabled = YES;
    textLabel2.textColor = [UIColor tc5Color];
    textLabel2.textAlignment = NSTextAlignmentCenter;
    textLabel2.font = [UIFont systemFontOfSize:14];
    textLabel2.text = @"点击查看实名认证责任条款";
    UITapGestureRecognizer *textLabelTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(textLabelClicked)];
    [textLabel2 addGestureRecognizer:textLabelTap];
    [bottomView addSubview:textLabel2];
    [textLabel2 mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(_uploadImageView.mas_bottom).offset(25);
        make.centerX.equalTo(bottomView);
        make.left.right.equalTo(bottomView);
    }];
    
    _uploadButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [bottomView addSubview:_uploadButton];
    [_uploadButton mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(textLabel2.mas_bottom).offset(25);
        make.left.equalTo(bottomView).offset(15);
        make.right.equalTo(bottomView).offset(-15);
        make.height.mas_equalTo(44);
    }];
    _uploadButton.layer.cornerRadius = 5;
    _uploadButton.layer.borderColor = [[UIColor bc7Color] CGColor];
    _uploadButton.layer.borderWidth = 0.5;
    _uploadButton.titleLabel.font = [UIFont systemFontOfSize:16.0];
    self.uploadButton.backgroundColor = [UIColor bc7Color];
    _uploadButton.layer.borderColor = [[UIColor bc7Color] CGColor];
    [_uploadButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_uploadButton setTitle:@"提交" forState:UIControlStateNormal];
    [[_uploadButton rac_signalForControlEvents:UIControlEventTouchUpInside] subscribeNext:^(id x){
        if (self.nameStr.length == 0) {
            _alertLabel.text = @"姓名与身份证为必填信息";
            return ;
        } else {
            _alertLabel.text = @"";
        }
        
        if (self.idCardStr.length == 0) {
            _alertLabel.text = @"姓名与身份证为必填信息";
            return ;
        } else {
            _alertLabel.text = @"";
        }
        if(![RegexKit validateIdentityCard:self.idCardStr]) {
             _alertLabel.text = @"身份证号输入有误";
            return;
        }else {
             _alertLabel.text = @"";
        }
        if (self.viewModel.photo == nil) {
            [MBProgressHUDHelper showHudWithText:@"请上传身份证图片"];
            return ;
        }
        [MBProgressHUDHelperFloatView showFloatView :self.view content:@"上传中" type:FloatViewTypeIng];
//        [LoadingView showLoadingInView:self.view ];
        [self.viewModel verifyCertification:^(BOOL isSuccess){
           [[MBProgressHUDHelperFloatView defaultHelper] hideNow];
//            [LoadingView hideLoadinForView:self.view ];
            if (isSuccess == YES) {
                WDAlertView *alertView = [[WDAlertView alloc]initWithNavigationController:weakSelf.navigationController withType:WDAlertViewTypeOne];
                [alertView reloadTitle:@"您的认证信息已提交" content:@"请等待工作人员审核"];
                alertView.titleLabel.textColor = [UIColor tc2Color];
                alertView.titleLabel.font = [UIFont systemFontOfSize:15.0];
                alertView.contentLabel.font = [UIFont systemFontOfSize:15.0];
                alertView.contentLabel.textColor = [UIColor tc2Color];
                [alertView.cancelBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
                alertView.cancelBtn.titleLabel.font = [UIFont systemFontOfSize:15.0];
                [alertView.cancelBtn setTitle: @"知道了" forState: UIControlStateNormal];
                alertView.cancelBlock = ^(WDAlertView *view) {
                    [view dismiss];
                    if (weakSelf.formFail) {
                      [weakSelf.navigationController popToRootViewControllerAnimated:YES];

                    } else {
                        [weakSelf.navigationController popViewControllerAnimated:YES];
                    }
                };
    
                [alertView showViewWithHaveBackAction:NO withHaveBackView:YES];
            }
        }];
    }];

}

- (void)popBack
{
    WS(weakSelf);
    [self.view endEditing:YES];
    if (self.nameStr.length == 0 && self.idCardStr.length == 0 && self.viewModel.photo == nil) {
        [weakSelf.navigationController popViewControllerAnimated:YES];
    } else {
        WDAlertView *alert = [[WDAlertView alloc]initWithNavigationController:self.navigationController withType:WDAlertViewTypeTwo];
        [alert reloadTitle:@"" content:@"是否放弃实名认证"];
        [alert.cancelBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        [alert.submitBtn setTitleColor:[UIColor tc5Color] forState:UIControlStateNormal];
        [alert.submitBtn setTitle:@"确定" forState:UIControlStateNormal];
        [alert.cancelBtn setTitle:@"取消" forState:UIControlStateNormal];
        alert.cancelBlock = ^(WDAlertView *view) {
            [view dismiss];
        };
        alert.submitBlock = ^(WDAlertView *view) {
            [view dismiss];
            [weakSelf.navigationController popViewControllerAnimated:YES];
        };
        [alert showViewWithHaveBackAction:YES withHaveBackView:YES];
    }
}

- (void)demoLabelClicked
{
    [self.view endEditing:YES];
    WS(weakSelf);
    self.viewPwdBgView.hidden = NO;
    [DSAlert ds_showCustomView:self.viewPwdBgView configuration:^(DSAlert *tempView) {
        tempView.isTouchEdgeHide = NO;
//      tempView.animatingStyle = DSAlertAnimatingStyleFall;
        weakSelf.customAlertView = tempView;
    }];
}

- (UIView *)viewPwdBgView
{
    if (!_viewPwdBgView)
    {
        _viewPwdBgView = [UIView new];
        _viewPwdBgView.frame = CGRectMake(45/2,20, SCREEN_WIDTH - 22,360);
        _viewPwdBgView.backgroundColor  = [UIColor whiteColor];
        
        UILabel *titleLabel = [[UILabel alloc] init];
        titleLabel.numberOfLines = 0;
        titleLabel.font = [UIFont systemFontOfSize:14.0];
        titleLabel.textColor = [UIColor tc2Color];
        titleLabel.text = @"拍照时请把双手手指,肩部,头部在镜头里显示出来";
        [_viewPwdBgView addSubview:titleLabel];
        [titleLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(_viewPwdBgView).offset(15);
            make.right.equalTo(_viewPwdBgView).offset(-15);
            make.top.equalTo(_viewPwdBgView).offset(15);
        }];
        
        UIImageView *demoImageView = [[UIImageView alloc] init];
        [_viewPwdBgView addSubview:demoImageView];
        demoImageView.backgroundColor = [UIColor clearColor];
        demoImageView.image = [UIImage imageNamed:@"img身份证示例"];
        [demoImageView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(_viewPwdBgView);
            make.top.equalTo(titleLabel.mas_bottom).offset(30);
            make.size.mas_equalTo(demoImageView.image.size);
        }];
        
        UIView *bottomView = [[UIView alloc] init];
        [_viewPwdBgView addSubview:bottomView];
        [bottomView mas_makeConstraints:^(MASConstraintMaker *make) {
            make.bottom.equalTo(_viewPwdBgView);
            make.left.right.equalTo(_viewPwdBgView);
            make.height.equalTo(@44);
        }];
        bottomView.userInteractionEnabled = YES;
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(closeView)];
        [bottomView addGestureRecognizer:tap];
        
        UILabel *lineLabel = [[UILabel alloc] init];
        lineLabel.backgroundColor = [UIColor bc3Color];
        [bottomView addSubview:lineLabel];
        [lineLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.left.equalTo(bottomView);
            make.right.equalTo(bottomView);
            make.top.equalTo(bottomView);
            make.height.mas_equalTo(0.5);
        }];
        
        UILabel *closeLabel = [[UILabel alloc] init];
        [bottomView addSubview:closeLabel];
        closeLabel.textAlignment = NSTextAlignmentCenter;
        closeLabel.font = [UIFont systemFontOfSize:14.0];
        closeLabel.textColor = [UIColor tc5Color];
        closeLabel.text = @"关闭";
        [closeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
            make.centerX.equalTo(bottomView);
            make.centerY.equalTo(bottomView);
        }];
        
    }
    return _viewPwdBgView;
}

- (void)closeView
{
    [_customAlertView ds_dismissAlertView];
}


- (void)textLabelClicked
{
    WDBaseWebViewController *webViewVC = [[WDBaseWebViewController alloc]initWithURL:[TaskManager manager].appConfig.common.realNameRule];
    webViewVC.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:webViewVC animated:YES];
    
//        SCCertificationResultVC *certificationFixed = [[SCCertificationResultVC alloc] init];
//        certificationFixed.type = CertificationFail;
//        certificationFixed.hidesBottomBarWhenPushed = YES;
//        [self.navigationController pushViewController:certificationFixed animated:NO];
//        certificationFixed.navigationController.navigationBar.hidden = NO;
}


- (void)uploadViewClicked
{
    [self.view endEditing:YES];
    [self pickImage];
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
        if (image.size.height > self.uploadImageView.size.height || image.size.width > self.uploadImageView.self.width) {
            self.uploadImageView.contentMode = UIViewContentModeScaleToFill;
        }
        else
        {
            self.uploadImageView.contentMode = UIViewContentModeCenter;
        }
        self.uploadImageView.image = image;
        self.viewModel.photo = image;
    }
    [self dismissViewControllerAnimated:YES completion:^{
        imagePicker = nil;
    }];
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    
    return 10.0;
}

- (void)tableView:(UITableView *)tableView willDisplayHeaderView:(UIView *)view forSection:(NSInteger)section {
    if ([view isKindOfClass: [UITableViewHeaderFooterView class]]) {
        UITableViewHeaderFooterView* castView = (UITableViewHeaderFooterView*) view;
        UIView* content = castView.contentView;
        UIColor* color = [UIColor bc2Color];
        content.backgroundColor = color;
    }
}

#pragma mark - tableView delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    WS(weakSelf);
    static NSString *identifier = @"CertificationFixedCellIdentifier";
    if (indexPath.row == 0) {
        SCCertificationFixedNameCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCCertificationFixedNameCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.nameBlock = ^(NSString *name){
            //NSLog(@"当前的 %@",name);
            //NSLog(@"长度－－ %lu",(unsigned long)name.length);
            weakSelf.nameStr = name;
            weakSelf.viewModel.userName = name;
        };
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
    } else if (indexPath.row == 1) {
        SCCertificationFixedIDcardCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
        if (!cell) {
            cell = [[SCCertificationFixedIDcardCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.idcardBlock = ^(NSString *idcard){
            weakSelf.idCardStr = idcard;
            weakSelf.viewModel.idCard = idcard;
        };
        cell.backgroundColor = [UIColor whiteColor];
        return cell;
    }
    return nil;
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}


@end
