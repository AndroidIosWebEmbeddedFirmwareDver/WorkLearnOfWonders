//
//  ProfileFunctionView.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BaseTableViewCell.h"
#import "ProfileModel.h"

typedef void(^FunctionButtonBlock)(NSInteger type);//type: 200:签约数 201:我的团队

@interface ProfileFunctionView : UITableViewCell

@property (nonatomic, strong) ProfileModel *model;

@property (nonatomic, copy) FunctionButtonBlock functionButtonBlock;

@end
