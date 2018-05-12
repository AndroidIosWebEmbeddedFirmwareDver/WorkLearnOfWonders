//
//  HomeViewModel.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/5.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"
#import "HomeFunctionModel.h"
#import "HomeRedTipModel.h"

@interface HomeViewModel : BaseViewModel<HomeIMPL>

@property (nonatomic, strong) NSArray *datas;

@end
