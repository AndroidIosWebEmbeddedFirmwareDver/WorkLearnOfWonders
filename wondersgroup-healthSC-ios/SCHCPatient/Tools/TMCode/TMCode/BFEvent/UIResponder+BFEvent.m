//
//  UIResponder+BFEvent.m
//  MakeFun
//
//  Created by maorenchao on 16/6/28.
//  Copyright © 2016年 boreanfrog. All rights reserved.
//

#import "UIResponder+BFEvent.h"

@implementation UIResponder(UIResponder_BFEvent)

- (void)bfEventWithEventName:(NSString *)eventname userInfo:(id)userInfo
{
    if (self.nextResponder) {
        [self.nextResponder bfEventWithEventName:eventname userInfo:userInfo];
    }
}
@end
