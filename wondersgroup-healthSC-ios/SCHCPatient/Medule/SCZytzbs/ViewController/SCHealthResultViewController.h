//
//  HealthResultViewController.h
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SCHealthIdentifyViewModel.h"
#import "SCHealthIdentifyModel.h"

@interface SCHealthResultViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>
@property (strong, nonatomic  ) UITableView               *tableView;
@property (weak, nonatomic    ) UILabel                   *noDataLabel;
@property (weak, nonatomic    ) UIButton                  *againBtn;
@property (strong, nonatomic  ) NSString                  *stringMessage;
@property (nonatomic, assign  ) BOOL                      isMine;
@property (nonatomic, assign  ) BOOL                      isLifeStyle;
@property (nonatomic, strong  ) NSArray                   *array;//数据数组
@property (nonatomic,strong   ) SCHealthIdentifyViewModel *viewModel;
@property (nonatomic,strong   ) UIView                    *lineView;
@property (nonatomic,strong   ) UIImageView               *healthimageView;

@end
