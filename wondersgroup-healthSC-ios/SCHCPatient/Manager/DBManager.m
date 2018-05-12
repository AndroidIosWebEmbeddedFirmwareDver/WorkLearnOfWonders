//
//  DBManager.m
//  VaccinePatient
//
//  Created by Jam on 16/5/12.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "DBManager.h"
#import "SCSearchModel.h"

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
    result =[dbHelper getTableCreatedWithClass: [SCMyCityModel class]];
    result =[dbHelper getTableCreatedWithClass: [SCHospitalModel class]];
    result =[dbHelper getTableCreatedWithClass: [HomePageModel class]];

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

-(void)setMySearchHistory:(SCSearchModel * )model{
    
    BOOL result = [dbHelper isExistsClass:[SCSearchModel class] where:@{@"cat_name":model.cat_name}];
    
    if (result) {
        [dbHelper deleteWithClass:[SCSearchModel class] where:@{@"cat_name":model.cat_name}];
        [dbHelper  insertToDB:model];
    }else{
        [dbHelper insertToDB:model];
    }
    
}
-(NSArray * )getMySearchHistory{
    return [dbHelper search:[SCSearchModel class] where:nil orderBy:@"date desc" offset:0 count:5];
    
}
- (void)clearAllSearchHistory{
    [dbHelper deleteWithClass:[SCSearchModel class] where:nil];
    
}

//保存搜索医院历史
- (void)saveSearchHistoryWithHospital:(SearchHistoryWithHospitalModel * )model{
    
    switch (model.type) {
        case SearchTypeContrller_NearBy:
            //附近就医
        {
        
            BOOL result = [dbHelper isExistsClass:[SearchHistoryWithHospitalModel class] where:@{@"cat_name":model.cat_name}];
            
            if (result) {
                [dbHelper deleteWithClass:[SearchHistoryWithHospitalModel class] where:@{@"cat_name":model.cat_name}];
                [dbHelper  insertToDB:model];
            }else{
                [dbHelper insertToDB:model];
            }
        
        
        }
            break;
        case SearchTypeContrller_Appointment:
            //预约挂好
        {
            BOOL result = [dbHelper isExistsClass:[SearchHistoryWithHospitalModel class] where:@{@"cat_name":model.cat_name}];
            
            if (result) {
                [dbHelper deleteWithClass:[SearchHistoryWithHospitalModel class] where:@{@"cat_name":model.cat_name}];
                [dbHelper  insertToDB:model];
            }else{
                [dbHelper insertToDB:model];
            }
        }
        case SearchTypeController_payOnLine:
            //在线支付
        {
            BOOL result = [dbHelper isExistsClass:[SearchHistoryWithHospitalModel class] where:@{@"userID":[[UserManager manager] uid] ,@"cat_name":model.cat_name}];
            
            if (result) {
                
                [dbHelper deleteWithClass:[SearchHistoryWithHospitalModel class] where:@{@"userID":[[UserManager manager] uid] ,@"cat_name":model.cat_name}];
                [dbHelper  insertToDB:model];
            }else{
                [dbHelper insertToDB:model];
            }
        }
            break;
            
        default:
            break;
    }
   

}
//获取搜索医院历史
- (NSArray *)getSearchHistoryWithHospitalWithType:(ViewContrllerType)type{
    
    switch (type) {
        case SearchTypeController_payOnLine:
            return [dbHelper search:[SearchHistoryWithHospitalModel class] where:@{@"userID":[[UserManager manager] uid],@"type":@(type)} orderBy:@"date desc" offset:0 count:5];
        default:
            return [dbHelper search:[SearchHistoryWithHospitalModel class] where:@{@"type":@(type)} orderBy:@"date desc" offset:0 count:5];
    }
//
}
//删除所有搜索医院历史
- (void)clearHospitalSearchHistoryWithType:(ViewContrllerType)type{
    switch (type) {
        case SearchTypeController_payOnLine:{
            [dbHelper deleteWithClass:[SearchHistoryWithHospitalModel class] where:@{@"userID":[[UserManager manager] uid],@"type":@(type)}];
            break;
        }
        default:
            [dbHelper deleteWithClass:[SearchHistoryWithHospitalModel class] where:@{@"type":@(type)}];
    }
}

//- (void)insertMyCity:(SCMyCityModel *)myCity{
//    BOOL result = [dbHelper insertToDB:myCity];
//    NSLog(@"result = %d",result);
//}
- (void)saveHomePageDatas:(HomePageModel * )model{
//    NSArray <BannersModel *>* bannersArr = model.banners;
//    NSArray <FunctionModel *>* functionArr = model.functionIcons;
//    NSArray <ArticlessModel *>* newsArr = model.news;
//    if (bannersArr) {
//        for (BannersModel * model in bannersArr) {
//            BOOL result = [dbHelper isExistsClass:[BannersModel class] where:@{@"hoplink":model.hoplink,@"imgUrl":model.imgUrl}];
//            
//            if (result) {
//                [dbHelper deleteWithClass:[BannersModel class] where:@{@"hoplink":model.hoplink,@"imgUrl":model.imgUrl}];
//                
//                [dbHelper  insertToDB:model];
//            }else{
//                [dbHelper insertToDB:model];
//            }
//        }
//    }
//    
//    if (functionArr) {
//        for (FunctionModel * model in functionArr) {
//            BOOL result = [dbHelper isExistsClass:[FunctionModel class] where:@{@"imgUrl":model.imgUrl,@"mainTitle":model.mainTitle,@"subTitle":model.subTitle,@"hoplink":model.hoplink}];
//            
//            if (result) {
//                [dbHelper deleteWithClass:[FunctionModel class] where:@{@"imgUrl":model.imgUrl,@"mainTitle":model.mainTitle,@"subTitle":model.subTitle,@"hoplink":model.hoplink}];
//                
//                [dbHelper  insertToDB:model];
//            }else{
//                [dbHelper insertToDB:model];
//            }
//        }
//    }
//    
//    if (newsArr) {
//        for (ArticlessModel * model in newsArr) {
//            BOOL result = [dbHelper isExistsClass:[ArticlessModel class] where:@{@"articleID":model.articleID,@"thumb":model.thumb,@"title":model.title,@"desc":model.desc,@"pv":model.pv,@"url":model.url}];
//            
//            if (result) {
//                [dbHelper deleteWithClass:[ArticlessModel class] where:@{@"articleID":model.articleID,@"thumb":model.thumb,@"title":model.title,@"desc":model.desc,@"pv":model.pv,@"url":model.url}];
//                
//                [dbHelper  insertToDB:model];
//            }else{
//                [dbHelper insertToDB:model];
//            }
//        }
//    }
    BOOL result = YES;
    
    result = [dbHelper deleteWithClass:[HomePageModel class] where:@"1=1"];
    result = [dbHelper insertToDB: model];
    
    NSLog(@"result = %d",result);

}
-(HomePageModel * )getHomePageDatas{
    
// NSArray <BannersModel *>* bannersArr = [dbHelper search:[BannersModel class] where:nil orderBy:@"date desc" offset:0 count:10];
// NSArray <FunctionModel *>* functionsArr = [dbHelper search:[FunctionModel class] where:nil orderBy:@"date desc" offset:0 count:10];
// NSArray <ArticlessModel *>* newsArr = [dbHelper search:[ArticlessModel class] where:nil orderBy:@"date desc" offset:0 count:10];
//    
//    HomePageModel * model = [HomePageModel new];
//    model.banners = bannersArr;
//    model.functionIcons = functionsArr;
//    model.news = newsArr;
    return [dbHelper searchSingle:[HomePageModel class] where:nil orderBy: nil];
}
- (void)updateMyCity:(SCMyCityModel *)myCity {
    BOOL result = YES;
    
    result = [dbHelper deleteWithClass:[SCMyCityModel class] where:@"1=1"];
    result = [dbHelper insertToDB: myCity];

    NSLog(@"result = %d",result);
}

- (SCMyCityModel *)getMyCityWithUid:(NSString *)uid {
    return [dbHelper searchSingle:[SCMyCityModel class] where:@{@"uid":uid} orderBy: nil];
}


//保存提取报告中搜索医院
- (void)saveTakeReportHospital:(SCHospitalModel *)model withUserid:(NSString *)userId withType:(HospitalSearchType)searchType{
    SCHospitalModel *teModel = [model mutableCopy];
    teModel.userId = userId;
    teModel.searchType = [NSNumber numberWithInt:searchType];
    
    BOOL result = [dbHelper isExistsClass:[SCHospitalModel class] where:@{@"userId":userId,@"searchType":[NSNumber numberWithInt:searchType],@"hospitalId":model.hospitalId}];
    
    if (result) {
        [dbHelper deleteWithClass:[SCHospitalModel class] where:@{@"userId":userId,@"searchType":[NSNumber numberWithInt:searchType],@"hospitalId":model.hospitalId}];
        [dbHelper  insertToDB:teModel];
    }else{
        [dbHelper insertToDB:teModel];
    }
}
//获取提取报告中搜索医院
- (NSArray *)getTakeReportHospitalWithUserid:(NSString *)userId withType:(HospitalSearchType)searchType {
    return [dbHelper search:[SCHospitalModel class] where:@{@"userId":userId,@"searchType":[NSNumber numberWithInt:searchType]} orderBy:nil offset:0 count:0];

}

#pragma mark - 健康页缓存数据

- (void)saveHealthHomeDataWithModel:(HealthHomeDBModel *)model {
    
//    [dbHelper deleteWithClass:[HealthHomeDBModel class] where:@"1=1"];
//    [dbHelper insertToDB: model];
//    
    if ([dbHelper searchSingle:[HealthHomeDBModel class] where:nil orderBy:nil]) {
        [dbHelper updateToDB:model where:@"1=1"];
    }
    else {
        [dbHelper insertToDB:model];
    }
}

- (HealthHomeDBModel *)getHealthHomeData {
    
    return [dbHelper searchSingle:[HealthHomeDBModel class] where:nil orderBy: nil];
}


@end
