//
//  BFShareInstance.m
//  localSocketDemo
//
//  Created by maorenchao on 16/7/19.
//  Copyright © 2016年 boreanfrog. All rights reserved.
//

#import "BFShareInstance.h"

@implementation BFShareInstance
+ (instancetype)shareInstance
{
    static id instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[self alloc] init];
    });
    return instance;
}

+ (instancetype)allocWithZone:(struct _NSZone *)zone
{
    static id instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [super allocWithZone:zone];
    });
    return instance;
}

- (instancetype)init
{
    static id instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [super init];
        
    });
    return instance;
}
@end
