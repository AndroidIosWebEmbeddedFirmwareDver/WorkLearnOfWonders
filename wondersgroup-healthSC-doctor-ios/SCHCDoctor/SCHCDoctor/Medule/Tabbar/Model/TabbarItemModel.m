//
//  TabbarItemModel.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "TabbarItemModel.h"

@implementation TabbarItemModel

- (instancetype)init {
    self = [super init];
    if (self) {
       self.isBundleImage    = YES;
       self.downloadComplete = NO;

    }
    return self;
}


@end
