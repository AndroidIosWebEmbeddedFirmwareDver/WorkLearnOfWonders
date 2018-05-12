//
//  BadgeModel.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BadgeModel.h"

@implementation BadgeModel

-(instancetype)init {
    self = [super init];
    if (self) {
        self.badgeIndex = 0;
        self.badgeNum = 0;
    }
    return self;
}


@end
