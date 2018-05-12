//
//  BaseViewModel.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"


@implementation BaseViewModel


- (id)init {
    self = [super init];
    if (self) {
        [self initializeBase];
        
    }
    return self;
}

- (ResponseAdapter *)adapter {
    return [ResponseAdapter sharedInstance];
}



- (void)initializeBase {
    self.moreParams = nil;
}





@end
