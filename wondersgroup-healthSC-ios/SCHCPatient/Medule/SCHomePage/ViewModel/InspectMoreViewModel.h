//
//  InspectMoreViewModel.h
//  SCHCPatient
//
//  Created by Wonders_iOS on 2016/11/13.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseViewModel.h"
#import "HospitalsModel.h"
#import "DoctorsModel.h"
#import "ArticlesModel.h"
@interface InspectMoreViewModel : BaseViewModel
@property (nonatomic,strong) NSArray * dataArray;                //医院列表数据

@property (nonatomic,strong) NSArray * dataDoctorArray;          //医生列表数据

@property (nonatomic,strong) NSArray * dataArticleArray;         //文章列表数据

//搜索 --1.获取医院列表  2.加载医院列表
- (void)getAllHospitalsList:(NSString * )keyword   success:(void(^)(void))success
                    failure: (void (^)(NSError *error))failure;
- (void)getMoreHospitalsList:(NSString * )keyword  success:(void(^)(void))success
                    failure: (void(^)(NSError *error))failure;

//搜索 --1.获取医生列表  2.加载医生列表
- (void)getAllDoctorsList:(NSString * )keyword  success:(void(^)(void))success
                   failure: (void(^)(NSError *error))failure;
- (void)getMoreDoctorsList:(NSString * )keyword  success:(void(^)(void))success
                     failure: (void(^)(NSError *error))failure;

//搜索 --1.获取文章列表  2.加载文章列表
- (void)getAllAritlesList:(NSString * )keyword  success:(void(^)(void))success
                   failure: (void(^)(NSError *error))failure;
- (void)getMoreAritlesList:(NSString * )keyword  success:(void(^)(void))success
                  failure: (void(^)(NSError *error))failure;


@end
