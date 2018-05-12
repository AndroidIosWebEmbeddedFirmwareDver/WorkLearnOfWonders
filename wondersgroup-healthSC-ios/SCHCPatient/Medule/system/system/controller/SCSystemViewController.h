//
//  SystemViewController.h
//  VaccinePatient
//
//  Created by luzhongchang on 16/10/31.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewController.h"
#import "SCSystemViewModel.h"
@interface SCSystemViewController : BaseViewController<UITableViewDelegate,UITableViewDataSource>
@property(nonatomic ,strong) UITableView * mTableview;
@property(nonatomic ,strong) SCSystemViewModel * viewModel;
@end
