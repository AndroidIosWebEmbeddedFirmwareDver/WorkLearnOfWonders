//
//  PayNoticeViewController.h
//  SCHCPatient
//
//  Created by luzhongchang on 16/11/4.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "PayNoticeViewModel.h"
#import "SCSystemViewModel.h"
@interface PayNoticeViewController : BaseViewController<UITableViewDelegate,UITableViewDataSource>
@property(nonatomic ,strong) UITableView * mTableview;
@property(nonatomic ,strong) PayNoticeViewModel * viewModel;
@property(nonatomic ,strong) SCSystemViewModel * updateMessageViewmodel;
@end
