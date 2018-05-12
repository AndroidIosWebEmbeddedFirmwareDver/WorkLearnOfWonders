//
//  SCMyCityDao.m
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "SCMyCityDao.h"

@implementation SCMyCityDao

+ (SCMyCityDao *)sharedInstance{
    static dispatch_once_t pred;
    static SCMyCityDao *shared = nil;
    dispatch_once(&pred, ^{
        shared = [[SCMyCityDao alloc] init];
    });
    return shared;
}

- (void)insertMyCity:(SCMyCityModel *)myCity{
    BOOL result = [[DBManager getUsingLKDBHelper] insertToDB:myCity];
    NSLog(@"result = %d",result);
}

- (void)updateMyCity:(SCMyCityModel *)myCity withUId:(NSString *)uid{
    BOOL result = [[DBManager getUsingLKDBHelper] updateToDB:myCity where:[NSString stringWithFormat:@"uid='%@'",uid]];
    NSLog(@"result = %d",result);
}

- (NSArray *)getMyCityWithUid:(NSString *)uid{
    NSArray *array = [[DBManager getUsingLKDBHelper] search:[SCMyCityModel class] where:[NSString stringWithFormat:@"uid=%@",uid] orderBy:nil offset:0 count:999];
    return array;
}

@end
