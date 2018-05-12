//
//  DBManager.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DBManager.h"

@interface DBManager () {
    LKDBHelper *dbHelper;
}

@end

@implementation DBManager
- (LKDBHelper *)dbHelper
{
    return dbHelper;
}
+ (DBManager *)manager;{
    static dispatch_once_t onceToken;
    static DBManager *shared = nil;
    dispatch_once(&onceToken, ^{
        shared = [[DBManager alloc] init];
    });
    return shared;
}

-(instancetype)init {
    self = [super init];
    if (self) {
        dbHelper = [[LKDBHelper alloc] initWithDBPath: [DB_PATH stringByAppendingPathComponent: DB_NAME]];
        NSLog(@"%@", DB_PATH);
    }
    return self;
}

- (BOOL)loadDatabase {
    //创建表
    BOOL result = YES;
    
    result = [dbHelper getTableCreatedWithClass: [UserModel class]];
    result = [dbHelper getTableCreatedWithClass: [UserInfoModel class]];

    return result;
}

//清空对所有表
- (BOOL)clearAllDatabase {
    BOOL result = YES;
    //删除所有数据库表
    
    [dbHelper deleteWithClass: [UserModel class] where:nil];
    [dbHelper deleteWithClass: [UserInfoModel class] where: nil];
    return result;
}

//清空对应Model的表
- (BOOL)clearDatabase:(Class)class {
    BOOL result = YES;
    //删除传入的Model Class的表
    if ([class isKindOfClass: [UserModel class]]) {
        result = [dbHelper deleteWithClass: class where:nil];
        result = [dbHelper deleteWithClass: [UserInfoModel class] where: nil];
    }
    else {
        result = [dbHelper deleteWithClass: class where:nil];
    }
    return result;
}

//获取本地用户
- (UserModel *)getUser {
    return [dbHelper searchSingle:[UserModel class] where: nil orderBy: nil];
}
//更新本地用户
- (BOOL)upDateUser:(UserModel *)userModel  {
    BOOL result = YES;
    result = [dbHelper deleteWithClass:[UserModel class] where:@"1=1"];
    result = [dbHelper insertToDB: userModel];

    return result;
}


#pragma mark - 全局接口
//获取本地全局配置数据
- (AppConfigModel *)getLocalAppConfig
{
    return [dbHelper searchSingle:[AppConfigModel class] where: nil orderBy: nil];
}
//保存全局配置到本地
- (BOOL)updateLocalAppConfig:(AppConfigModel *)appConfig
{
    BOOL result = YES;
    result = [dbHelper deleteWithClass:[AppConfigModel class] where:@"1=1"];
    result = [dbHelper insertToDB: appConfig];
    
    return result;
}


@end
