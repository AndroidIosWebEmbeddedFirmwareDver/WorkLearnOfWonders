//
//  SCMyCityDao.h
//  SCHCPatient
//
//  Created by 黄天乐 on 2016/11/15.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DBManager.h"
#import "SCMyCityModel.h"

@interface SCMyCityDao : DBManager

+ (SCMyCityDao *)sharedInstance;

- (void)insertMyCity:(SCMyCityModel *)myCity;
- (void)updateMyCity:(SCMyCityModel *)myCity withUId:(NSString *)uid;
- (NSArray *)getMyCityWithUid:(NSString *)uid;

@end
