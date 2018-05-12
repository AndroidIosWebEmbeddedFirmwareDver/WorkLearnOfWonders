//
//  SCSettingModel.m
//  SCHCPatient
//
//  Created by wanda on 16/11/3.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCSettingModel.h"

@implementation SCSettingModel
- (id)initWithtype:(SettingType)type
{
    self = [super init];
    if (self) {
        self.type = type;
    }
    return self;
}
@end
