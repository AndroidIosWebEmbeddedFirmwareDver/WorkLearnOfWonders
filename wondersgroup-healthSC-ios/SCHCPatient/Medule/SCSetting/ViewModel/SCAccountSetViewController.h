//
//  SCAccountSetViewController.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "SCAccountSetCell.h"
#import "SCChangePasswordCell.h"
#import "SCChangePassWordViewController.h"
#import "SCBindingIphoneViewController.h"
@interface SCAccountSetViewController : BaseViewController<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong) UITableView    *tableView;

@end
