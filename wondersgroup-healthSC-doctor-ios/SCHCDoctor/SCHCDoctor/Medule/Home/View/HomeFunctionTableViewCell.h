//
//  HomeFunctionTableViewCell.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/6.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HomeFunctionModel.h"

typedef void(^FunctionButtonBlock)(NSInteger type);//

@interface HomeFunctionTableViewCell : UITableViewCell

@property (nonatomic, strong) NSArray *datas;

@property (nonatomic, copy) FunctionButtonBlock functionButtonBlock;



@end
