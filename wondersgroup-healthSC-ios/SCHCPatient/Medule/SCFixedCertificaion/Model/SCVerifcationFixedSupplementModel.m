//
//  SCVerifcationFixedSupplementModel.m
//  SCHCPatient
//
//  Created by wanda on 16/11/2.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCVerifcationFixedSupplementModel.h"

@implementation SCVerifcationFixedSupplementModel
- (id)initWithtitle:(NSString *)title type:(VerifcationFixedType )type
{
    self = [super init];
    if (self) {
        self.title = title;
        self.type = type;
    }
    return self;
}

@end
