//
//  SCBindingIphoneViewController.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "SCphoneNumberCell.h"
#import "SCVerificationCodeCell.h"
#import "SCUserViewModel.h"
@interface SCBindingIphoneViewController : BaseViewController<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong) UITableView     *tableView;
@property (nonatomic,copy  ) NSString        *phoneNumStr;
@property (nonatomic,copy  ) NSString        *VerificationCode;
@property (nonatomic,strong) SCUserViewModel *viewModel;
@end
