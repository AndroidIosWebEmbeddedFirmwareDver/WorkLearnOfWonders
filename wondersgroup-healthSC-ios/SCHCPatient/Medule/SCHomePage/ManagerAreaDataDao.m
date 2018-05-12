//
//  ManagerAreaDataDao.m
//  HCPatient
//
//  Created by luzhongchang on 16/8/28.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "ManagerAreaDataDao.h"
#import "DBManager.h"



@implementation BaseAreaModel
+ (NSDictionary *)JSONKeyPathsByPropertyKey {
    return @{ @"name":@"name",
              @"code":@"code"};
    
}

@end

@implementation ManagerAreaDataDao
+(instancetype) shareInstance
{
    static ManagerAreaDataDao *shareInstance;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        shareInstance = [ManagerAreaDataDao new];
        [shareInstance initDatabase];
    });
    return shareInstance;
    
}

-(void)initDatabase
{
    LKDBHelper *globalHelper = [[DBManager  manager] dbHelper];
        //创建表
    BOOL result;
    result = [globalHelper getTableCreatedWithClass:[ManagerAreaDataDao class]];
    
}


-(NSMutableArray *) getnearestData
{
     return  [[[DBManager  manager] dbHelper]search:[LocationModel class] column:nil where:nil orderBy:@"date desc" offset:0 count:1];
}

-(NSMutableArray * ) getNearyThreeeData
{
     return  [[[DBManager  manager] dbHelper]search:[LocationModel class] column:nil where:nil orderBy:@"date desc" offset:0 count:3];
}
-(void)insertdata:( LocationModel *)model
{

    
    NSMutableDictionary *dic = [[NSMutableDictionary alloc] init];
    [dic setObject:model.areaName forKey:@"areaName"];
    [dic setObject:model.areaCode forKey:@"areaCode"];
    LocationModel * modelsa = [self findemodel:dic];
    if(modelsa)
    {
        [self deleteToDB:dic];
    
    
    
    
    }
    
    
    
    [[[DBManager  manager] dbHelper] insertToDB:model];
}

-(LocationModel *) findemodel : (NSDictionary *)dic
{
    return [[[DBManager manager]dbHelper] searchSingle:[LocationModel class] where:dic orderBy:nil];
}

-(BOOL) deleteToDB:(NSDictionary * )dic
{
    return [[[DBManager manager] dbHelper] deleteWithClass:[LocationModel class] where:dic];
}
@end
