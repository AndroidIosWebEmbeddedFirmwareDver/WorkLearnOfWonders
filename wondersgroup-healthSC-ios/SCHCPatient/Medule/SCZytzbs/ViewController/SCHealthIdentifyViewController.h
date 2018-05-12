//
//  HealthIdentifyViewController.h
//  HCPatient
//
//  Created by guowenke1 on 15/3/10.
//  Copyright (c) 2015年 wonders. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SCHealthIdentifyViewController : BaseViewController <UITableViewDataSource, UITableViewDelegate>
@property (nonatomic,strong ) UITableView *tableview;
@property (nonatomic, assign) BOOL        isMine;//是否是本人


@end
