//
//  SCSettingViewModel.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSettingViewModel.h"

@implementation SCSettingViewModel

-(void)reloadDatas {
    SCSettingModel *model = [SCSettingModel new];
    model.content = @"图文咨询新消息提示";
    model.isHaveArrow = NO;
    model.isOn = YES;
    
    SCSettingModel *model1 = [SCSettingModel new];
    model1.content = @"功能介绍";
    model1.isHaveArrow = YES;

    self.modelArray = @[model,model1];
}

@end
