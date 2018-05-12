//
//  FunctionIntroductionViewModel.h
//  SCHCDoctor
//
//  Created by ZJW on 2017/6/12.
//  Copyright © 2017年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"

@interface FunctionIntroductionViewModel : BaseViewModel

@property (nonatomic, strong) NSArray *images;

-(void)reloadDatas;

@end
