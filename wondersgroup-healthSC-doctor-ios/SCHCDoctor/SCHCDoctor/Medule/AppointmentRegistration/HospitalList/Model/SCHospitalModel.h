//
//  WCNearbyHospitalModel.h
//  HCPatient
//
//  Created by Gu Jiajun on 16/11/1.
//  Copyright © 2016年 ZJW. All rights reserved.
//

#import "BaseModel.h"

typedef enum{
    HospitalSearchTypeOnline,//在线支付
    HospitalSearchTypeExtract,//提取报告
} HospitalSearchType;

@interface SCHospitalModel : BaseModel<NSMutableCopying>

/// 医院id
@property (nonatomic, copy) NSString *hospitalId;
/// 医院代码
@property (nonatomic, copy) NSString *hospitalCode;
/// 医院名称
@property (nonatomic, copy) NSString *hospitalName;
/// 医院等级
@property (nonatomic, copy) NSString *hospitalGrade;
/// 医院纬度
@property (nonatomic, strong) NSNumber *hospitalLatitude;
/// 医院经度
@property (nonatomic, strong) NSNumber *hospitalLongitude;
/// 医院地址
@property (nonatomic, copy) NSString *hospitalAddress;
/// 医院简介
@property (nonatomic, copy) NSString *hospitalDesc;
/// 医院电话
@property (nonatomic, copy) NSString *hospitalTel;
/// 缩略图
@property (nonatomic, copy) NSString *receiveThumb;
/// 医院图片
@property (nonatomic, copy) NSString *hospitalPhoto;
/// 预约量
@property (nonatomic, copy) NSString *receiveCount;
/// 0-未关注，1-已关注
@property (nonatomic, assign) BOOL concern;

@property (nonatomic, copy) NSNumber *searchType;

@property (nonatomic, copy) NSString *userId;

@end
