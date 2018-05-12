//
//  TaskManager.h
//  VaccinePatient
//
//  Created by Jam on 16/5/14.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ViewModelIMPL.h"
#import "AppConfigModel.h"

@interface TaskManager : NSObject
@property(nonatomic, strong)AppConfigModel *appConfig; //全局配置
@property (nonatomic, copy) NSString *qnToken;  //七牛的Token
@property (nonatomic, copy) NSString *domain;

//@property (nonatomic,copy) NSString * cityCode;//区域代码
+ (TaskManager *)manager;


- (void)setMyCityCode:(NSString *)cityCode andCityName:(NSString * )cicyName;//保存城市代码
- (NSString *)getMyCityCode; //获取城市代码
-(NSString *)getCityName;

@end
