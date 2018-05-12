//
//  SCChangePassWordViewController.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "SCOldpasswordCell.h"
#import "SCNewPasswprdCell.h"
#import "SCSurePassWordCell.h"
#import "SCUserViewModel.h"
@interface SCChangePassWordViewController : BaseViewController<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong) UITableView     *tableView;
@property (nonatomic,strong) SCUserViewModel *viewModel;
@property (nonatomic,copy  ) NSString        *oldPasswrodStr;
@property (nonatomic,copy  ) NSString        *passwordStrNew;
@property (nonatomic,copy  ) NSString        *surewordStr;
@property (nonatomic,strong) UIButton        *handlebutton;
@property (nonatomic,strong) UILabel         *alertLabel;
@end
