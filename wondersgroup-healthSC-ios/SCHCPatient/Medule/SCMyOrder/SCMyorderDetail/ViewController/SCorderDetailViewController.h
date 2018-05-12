//
//  SCorderDetailViewController.h
//  HCPatient
//
//  Created by wanda on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "BaseViewController.h"
#import "SCOrderDetailCell.h"
@interface SCorderDetailViewController : BaseViewController<UITableViewDelegate,UITableViewDataSource>
@property (nonatomic,strong) UIView      *headView;
@property (nonatomic,strong) UIView      *footView;
@property (nonatomic,strong) UITableView *tableview;
@property (nonatomic,strong) NSArray     *titleArray;
@end
