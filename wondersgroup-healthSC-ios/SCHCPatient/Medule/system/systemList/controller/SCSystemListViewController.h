//
//  ReportNoticeViewController.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "SCSystemListViewModel.h"
#import "SCSystemViewModel.h"
@interface SCSystemListViewController : BaseViewController<UITableViewDelegate, UITableViewDataSource>
@property(nonatomic ,strong) UITableView *mTableview;
@property(nonatomic ,strong) SCSystemListViewModel * viewModel;


@property(nonatomic ,strong) SCSystemViewModel * updateMessageViewmodel;

@end
