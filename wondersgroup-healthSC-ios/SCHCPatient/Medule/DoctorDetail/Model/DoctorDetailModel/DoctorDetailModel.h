//
//  DoctorDetailModel.h
//  SCHCPatient
//
//  Created by Li,Huanan on 16/11/8.
//  Copyright © 2016年 Jam. All rights reserved.
//

#import "BaseModel.h"
#import "DoctorDetailJudgeModel.h"


@interface DoctorDetailModel : BaseModel

@property (nonatomic, copy) NSString *mid;                  //医生id
@property (nonatomic, copy) NSString *hosOrgCode;           //医院代码
@property (nonatomic, copy) NSString *hosDeptCode;          //科室代码
@property (nonatomic, copy) NSString *hosDoctCode;          //医生代码
@property (nonatomic, copy) NSString *headphoto;            //医生头像
@property (nonatomic, strong) NSNumber *gender;               //1-男，2-女
@property (nonatomic, copy) NSString *hosName;              //医院详情
@property (nonatomic, copy) NSString *doctorName;           //医生名称
@property (nonatomic, copy) NSString *doctorTitle;          //职称
@property (nonatomic, copy) NSString *doctorDesc;           //医生简介
@property (nonatomic, copy) NSString *expertin;             //擅长
@property (nonatomic, strong) NSNumber *orderCount;           //接诊量
@property (nonatomic, copy) NSString *isFull;               //
@property (nonatomic, copy) NSString *evaluateCount;        //评价量
@property (nonatomic, copy) NSString *concern;              //0-未关注，1-已关注

@property (nonatomic, copy) NSString *hospitalDesc;         //医院详情

//评价model 数组
@property (nonatomic, strong) NSMutableArray <DoctorDetailJudgeModel *> *evaluList;


@end
