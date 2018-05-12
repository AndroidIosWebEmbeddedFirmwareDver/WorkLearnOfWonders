//
//  UserDefaults.m
//  EyeProtection
//
//  Created by Jam on 15/11/25.
//  Copyright © 2015年 Jam. All rights reserved.
//

#import "UserDefaults.h"

@implementation UserDefaults

+ (NSUserDefaults *)shareDefaults {
    static UserDefaults *instance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = (UserDefaults *)[NSUserDefaults standardUserDefaults];
    });
    return instance;
}

- (void)setObject:(id)value forKey:(NSString *)defaultName {
    [super setObject: value forKey: defaultName];
    [self synchronize];
}


- (void)removeObjectForKey:(NSString *)defaultName {
    [super removeObjectForKey: defaultName];
    [self synchronize];
}


@end
