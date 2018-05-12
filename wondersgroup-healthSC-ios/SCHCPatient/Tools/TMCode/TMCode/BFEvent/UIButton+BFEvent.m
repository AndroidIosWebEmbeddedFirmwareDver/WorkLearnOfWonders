//
//  UIButton+BFEvent.m
//  MakeFun
//
//  Created by maorenchao on 16/6/28.
//  Copyright © 2016年 boreanfrog. All rights reserved.
//

#import "UIButton+BFEvent.h"
#import <objc/runtime.h>

//private key
static const NSString* eventNameKey = @"EventNameKey";

@implementation UIButton(UIButton_BFEvent)

- (void)addBFEventName:(NSString *)eventname forControlEvents:(UIControlEvents)controlEvent
{
    [self setBFEventname:eventname];
    [self addTarget:self action:@selector(bfbuttonEvent) forControlEvents:controlEvent];
}

- (void)removeBFEventName:(NSString *)eventname forControlEvents:(UIControlEvents)controlEvent
{
    [self setBFEventname:@""];
    [self removeTarget:self action:@selector(bfbuttonEvent) forControlEvents:controlEvent];
}

- (void)bfbuttonEvent
{
    [self bfEventWithEventName:[self bfEventname] userInfo:nil];
}

- (NSString *)bfEventname {
    return objc_getAssociatedObject(self, (__bridge const void *)(eventNameKey));
}

- (void)setBFEventname:(NSString *)eventname {
    objc_setAssociatedObject(self, (__bridge const void *)(eventNameKey), eventname, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

@end
