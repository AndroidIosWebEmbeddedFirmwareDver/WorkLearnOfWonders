//
//  DBManager.h
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <LKDBHelper/LKDBHelper.h>
#import "UserModel.h"
#import "AppConfigModel.h"

#define DB_NAME @"SCHCDoctor.db"
#define DB_PATH [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex: 0]


@interface DBManager : NSObject

+ (DBManager *)manager;

- (LKDBHelper *)dbHelper;

//创建表
- (BOOL)loadDatabase;
//删除所有表
- (BOOL)clearAllDatabase;

//删除对应Class的表
- (BOOL)clearDatabase:(Class)class;

//获取本地用户
- (UserModel *)getUser;

//更新本地用户
- (BOOL)upDateUser:(UserModel *)userModel;


//获取本地全局配置数据
- (AppConfigModel *)getLocalAppConfig;
    
//保存全局配置到本地
- (BOOL)updateLocalAppConfig:(AppConfigModel *)appConfig;



@end
