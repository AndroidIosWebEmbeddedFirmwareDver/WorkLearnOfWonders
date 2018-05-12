//
//  UIVIew+BFEvent.m
//  MakeFun
//
//  Created by maorenchao on 16/6/28.
//  Copyright © 2016年 boreanfrog. All rights reserved.
//

#import "UIVIew+BFEvent.h"
#import <objc/runtime.h>

static const NSString* eventNameKey = @"EventNameKey";
static const NSString* eventRecognizerKey = @"EventRecognizerKey";

@implementation UIView(UIVIew_BFEvent)
- (void)addBFTapEventName:(NSString *)eventname
{
    
    [self removeBFTapEvent];
   
    
    UITapGestureRecognizer *recognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(bfEventTaped)];
    [self addGestureRecognizer:recognizer];
    
    [self setBFEventRecognizer:nil];
    [self setBFEventname:eventname];
}
- (void)removeBFTapEvent
{
    if ([self bfEventRecognizer]) {
        [self removeGestureRecognizer:[self bfEventRecognizer]];
        [self setBFEventRecognizer:nil];
        [self setBFEventname:nil];
    }
}

- (void)bfEventTaped
{
    [self bfEventWithEventName:[self bfEventname] userInfo:nil];
}

- (UITapGestureRecognizer *)bfEventRecognizer {
    return objc_getAssociatedObject(self, (__bridge const void *)(eventRecognizerKey));
}

- (void)setBFEventRecognizer:(UITapGestureRecognizer *)bfEventRecognizer {
    objc_setAssociatedObject(self, (__bridge const void *)(eventRecognizerKey), bfEventRecognizer, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}


- (NSString *)bfEventname {
    return objc_getAssociatedObject(self, (__bridge const void *)(eventNameKey));
}

- (void)setBFEventname:(NSString *)eventname {
    objc_setAssociatedObject(self, (__bridge const void *)(eventNameKey), eventname, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

@end
