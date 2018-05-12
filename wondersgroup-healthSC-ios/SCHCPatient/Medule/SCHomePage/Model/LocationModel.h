//
//  LocationModel.h
//  HCPatient
//
//  Created by luzhongchang on 16/8/22.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "BaseModel.h"

@interface LocationModel : BaseModel
@property(nonatomic ,strong) NSString * latitude;
@property(nonatomic ,strong) NSString * longitude;
@property(nonatomic ,strong) NSString * areaCode;//定位的城市Code
@property(nonatomic ,strong) NSString * areaName;//定位的城市名字
@property(nonatomic ,strong) NSString * blishName;
@property(nonatomic ,strong) NSString * date;


@property(nonatomic ,strong) NSString * showAreaCode;
@property(nonatomic ,strong) NSString * showAreaName;

+(LocationModel * )Instance;

@end
