//
//  WCNearbyHospitalViewModel.h
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "BaseViewModel.h"

@interface SCNearbyHospitalViewModel : BaseViewModel

@property (nonatomic, strong) NSMutableArray *dataArray;

- (void)getNearbyHospitalListWithParams:(NSDictionary *)params Success: (void(^)(void))success failure:(void (^)(void))failure;
//通过新的坐标请求新的列表，原来的清空
- (void)getNearbyHospitalListWithNewLocationParams:(NSDictionary *)params Success: (void(^)(void))success failure:(void (^)(void))failure;
//通过城市获取列表
- (void)getNearbyHospitalListWithNewCityParams:(NSDictionary *)params Success: (void(^)(void))success failure:(void (^)(void))failure;

@end
