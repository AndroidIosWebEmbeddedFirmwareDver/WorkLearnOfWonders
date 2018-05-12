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
#import "SCSearchModel.h"
#import "SCMyCityModel.h"
#import "HomeSearchViewController.h"
#import "SearchHistoryWithHospitalModel.h"
#import "HomePageModel.h"
#import "HealthHomeDBModel.h"
#import "SCHospitalModel.h"

#define DB_NAME @"SCHCPatient.db"
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

//首页保存搜索历史
- (void)setMySearchHistory:(SCSearchModel * )model;
//首页获取搜索历史
- (NSArray *)getMySearchHistory;
//删除首页所有搜索历史
- (void)clearAllSearchHistory;


//保存搜索医院历史
- (void)saveSearchHistoryWithHospital:(SearchHistoryWithHospitalModel * )model ;
//获取搜索医院历史
- (NSArray *)getSearchHistoryWithHospitalWithType:(ViewContrllerType)type;
//删除所有搜索医院历史
- (void)clearHospitalSearchHistoryWithType:(ViewContrllerType)type;


//已选择地址
- (void)updateMyCity:(SCMyCityModel *)myCity;
- (SCMyCityModel *)getMyCityWithUid:(NSString *)uid;

//保存首页数据
- (void)saveHomePageDatas:(HomePageModel * )model;
//获取首页数据
- (HomePageModel *)getHomePageDatas;


//保存提取报告中搜索医院
- (void)saveTakeReportHospital:(SCHospitalModel *)model withUserid:(NSString *)userId withType:(HospitalSearchType)searchType;
//获取提取报告中搜索医院
- (NSArray *)getTakeReportHospitalWithUserid:(NSString *)userId withType:(HospitalSearchType)searchType;

#pragma mark - 健康页数据

- (void)saveHealthHomeDataWithModel:(HealthHomeDBModel *)model;

- (HealthHomeDBModel *)getHealthHomeData;


@end
