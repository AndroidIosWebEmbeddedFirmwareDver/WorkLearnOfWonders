//
//  SCSettingViewController.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "SCSettingViewModel.h"
#import "SCSettingModel.h"
#import "SCSettingAccountCell.h"
#import "SCAccountSetViewController.h"
@interface SCSettingViewController : BaseViewController <UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong) SCSettingViewModel *viewModel;
@property (nonatomic,strong) UITableView        *tableView;
@property (nonatomic,strong) NSArray            *modelArray;
@end
