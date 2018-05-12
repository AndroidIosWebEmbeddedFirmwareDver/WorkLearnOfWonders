//
//  SCSettingViewModel.h
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "SCSettingModel.h"

@interface SCSettingViewModel : BaseViewModel

@property (nonatomic,strong) NSArray *modelArray;

-(void)reloadDatas;

@end
