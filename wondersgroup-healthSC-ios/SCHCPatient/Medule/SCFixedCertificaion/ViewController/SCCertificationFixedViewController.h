//
//  SCtestViewController.h
//  HCPatient
//
//  Created by wanda on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "BaseViewController.h"
#import <AVFoundation/AVFoundation.h>
#import "SCCertificationViewModel.h"
#import "DSAlert.h"
@interface SCCertificationFixedViewController : BaseViewController<UIActionSheetDelegate,UIImagePickerControllerDelegate,UINavigationControllerDelegate,UITableViewDelegate,UITableViewDataSource>
{
    UIImagePickerController *imagePicker;
}
@property (nonatomic,strong ) UITableView              *tableView;
@property (nonatomic,strong ) UILabel                  *textLabel;
@property (nonatomic,strong ) UIImageView              *IDcardDemoImageview;
@property (nonatomic,strong ) UIView                   *IDcardview;
@property (nonatomic,strong ) UIImageView              *uploadImageView;
@property (nonatomic,strong ) UIButton                 *uploadButton;
@property (nonatomic, strong) SCCertificationViewModel *viewModel;
@property (nonatomic,strong ) NSString                 *nameStr;
@property (nonatomic,strong ) NSString                 *idCardStr;
@property (nonatomic, strong) UIView                   *viewPwdBgView;
@property (nonatomic, strong) DSAlert                  *customAlertView;
@property (nonatomic,strong ) UILabel                  *alertLabel;
@property (nonatomic        ) BOOL                     formFail;
@end
